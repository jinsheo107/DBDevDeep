package com.dbdevdeep.schedule.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dbdevdeep.schedule.domain.Schedule;
import com.dbdevdeep.schedule.repository.ScheduleRepository;

@Component
public class MyJob implements Job {
	private final ScheduleRepository scheduleRepository;
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(); // 필드로 선언
	
	@Autowired
	public MyJob(ScheduleRepository scheduleRepository) {
		this.scheduleRepository = scheduleRepository;
	}
	
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	
    	List<Schedule> schedule = scheduleRepository.findAll(); // 스케줄 데이터 조회

        for (Schedule s : schedule) {
            LocalDate startDate = s.getStartDate(); // DATE 타입
            LocalTime startTime = s.getStartTime(); // TIME 타입
            LocalDateTime startDateTime = startDate.atTime(startTime); // LocalDateTime으로 변환
            String alertType = s.getAlertType();

            LocalDateTime alertTime = null; // 초기화
            String msg = "";
            
            if ("ON".equals(alertType)) {
                alertTime = startDateTime; // 정각 알림
            } else if ("10".equals(alertType)) {
                alertTime = startDateTime.minusMinutes(10); // 10분 전 알림
                msg = "10분 후에 ";
            } else if ("30".equals(alertType)) {
                alertTime = startDateTime.minusMinutes(30); // 30분 전 알림
                msg = "30분 후에 ";
            } else if ("60".equals(alertType)) {
                alertTime = startDateTime.minusMinutes(60); // 60분 전 알림
                msg = "60분 후에 ";
            }

            // 알림 시간이 설정되었으면 알림 스케줄링
            if (alertTime != null) {
                scheduleAlarm(alertTime, s, msg);
            }
        }
    }
    
    private void scheduleAlarm(LocalDateTime alertTime, Schedule schedule, String msg) {
    	
    	 // 현재 시간과 alertTime의 차이를 계산
        long delay = Duration.between(LocalDateTime.now(), alertTime).toMillis();

        if (delay > 0) { // delay가 양수일 경우에만 스케줄링

            scheduler.schedule(() -> {
                String title = "일정";
                String message = schedule.getScheduleTitle() + " 일정이 " + msg + "시작됩니다.";
                String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm"));
                
                // 클라이언트에 알림을 요청하는 REST API 호출
                // URL을 구성
                String url = String.format("/schedule/notify?title=%s&message=%s&time=%s",
                                            URLEncoder.encode(title, StandardCharsets.UTF_8),
                                            URLEncoder.encode(message, StandardCharsets.UTF_8),
                                            URLEncoder.encode(time, StandardCharsets.UTF_8));

                // 이 URL을 클라이언트가 호출하도록 유도해야 함
                System.out.println("알림 전송 URL: " + url);
                
            }, delay, TimeUnit.MILLISECONDS);
        }
    }

}