
/*************************************************************************************/
// -->Template Name: Bootstrap Press Admin
// -->Author: Themedesigner
// -->Email: niravjoshi87@gmail.com
// -->File: datatable_basic_init
/****************************************
 *       1. Notice Table                   *
 ****************************************/
$('#notice_config').DataTable({
	// 화면 크기에 따라 컬럼 width 자동 조절
	 "responsive": true,
	 // 컬럼 width 비율 조절
	 "columnDefs": [
        { "width": "5%", "targets": 0 },
        { "width": "5%", "targets": 1 },
        { "width": "10%", "targets": 2 },
        { "width": "50%", "targets": 3 },
        { "width": "10%", "targets": 4 },
        { "width": "20%", "targets": 5 }
    ],
	"order": [[1, "desc"]], 
	// 정보 표시 해제
	info: false, 
	// DataTables의 DOM 구조를 재정의
	// 표시건수, 검색, 테이블, 페이징의 위치 재설정
	// (정보 표시 부분을 제외)
	"sDom": '<"row view-filter"<"col-sm-12"<"pull-left"l><"pull-right"f><"clearfix">>>t<"row view-pager"<"col-sm-12"<ip>>>',
	// 페이지네이션 버튼을 전체 숫자와 함께 표시
	pagingType: 'full_numbers',
	// 페이지당 항목 수를 선택할 수 있는 옵션
	lengthChange: false,
	// 기본 페이지당 항목 수
	pageLength: 10,

	// 페이징 관련 설정
	drawCallback: function(settings) {
	  	var api = this.api();  // DataTables API 객체
	  	var info = api.page.info();  // 현재 페이지 정보
	  	// 전체 페이지 수
	  	var totalPages = info.pages;
	  	// 현재 페이지 번호
	  	var currentPage = info.page;
	  	// 표시할 페이지 버튼 수
	  	var numButtons = 5;
	  	// 시작 페이지와 종료 페이지 번호 계산
	  	var startPage = Math.max(currentPage - Math.floor(numButtons / 2), 0);
	  	var endPage = Math.min(startPage + numButtons - 1, totalPages - 1);
	  	// endPage가 최대값에 도달한 경우 startPage 조정
	  	if (endPage - startPage < numButtons - 1) {
	      	startPage = Math.max(endPage - numButtons + 1, 0);
	  	}
	  	// 사용자 정의 페이지네이션 HTML 생성
	  	var paginationHtml = '<ul class="pagination">';
	  	paginationHtml += '<li class="paginate_button page-item ' + (currentPage === 0 ? 'disabled' : '') + '"><a class="page-link" href="#" tabindex="0"><<</a></li>';
	  	paginationHtml += '<li class="paginate_button page-item ' + (currentPage === 0 ? 'disabled' : '') + '"><a class="page-link" href="#" tabindex="0"><</a></li>';
	  	for (var i = startPage; i <= endPage; i++) {
	      	var isActive = i === currentPage ? 'active' : '';  // 현재 페이지에 'active' 클래스 적용
	      	paginationHtml += '<li class="paginate_button page-item ' + isActive + '"><a class="page-link" href="#" tabindex="0">' + (i + 1) + '</a></li>';
	  	}
	  	paginationHtml += '<li class="paginate_button page-item ' + (currentPage === totalPages - 1 ? 'disabled' : '') + '"><a class="page-link" href="#" tabindex="0">></a></li>';
	  	paginationHtml += '<li class="paginate_button page-item ' + (currentPage === totalPages - 1 ? 'disabled' : '') + '"><a class="page-link" href="#" tabindex="0">>></a></li>';
	  	paginationHtml += '</ul>';
	  	// 페이지네이션 컨테이너 업데이트
	  	$(api.table().container()).find('.dataTables_paginate').html(paginationHtml);
	  	// 클릭 이벤트 핸들러 추가
	  	$(api.table().container()).find('.paginate_button').on('click', function(e) {
	      	e.preventDefault();  // 기본 링크 동작 방지
	      	if ($(this).hasClass('disabled')) return;  // 비활성화된 버튼 클릭 방지
	      	var idx = $(this).find('a').text();  // 클릭된 버튼의 텍스트 가져오기
	      	if (idx === '<<') {
	          	api.page('first').draw('page');  // 첫 페이지로 이동
	      	} else if (idx === '<') {
	          	api.page('previous').draw('page');  // 이전 페이지로 이동
	      	} else if (idx === '>') {
	          	api.page('next').draw('page');  // 다음 페이지로 이동
	      	} else if (idx === '>>') {
	          	api.page('last').draw('page');  // 마지막 페이지로 이동
	      	} else {
	          	api.page(parseInt(idx) - 1).draw('page');  // 선택된 페이지로 이동
	      	}
	  	});
	},
	"initComplete": function () {
	       // 검색창을 "삭제" 및 "등록" 버튼 왼쪽으로 이동
	       var searchBox = $('div.dataTables_filter');
	       var buttonGroup = $('div.d-flex.justify-content-end');

	       // 검색창을 버튼 그룹 앞에 추가
	       searchBox.prependTo(buttonGroup);
		   
		   // "Search" 텍스트 제거
		   searchBox.find('label').contents().filter(function() {
		       return this.nodeType === 3; // 텍스트 노드를 선택
		   }).remove();
		   
		   // 검색 input을 수직 가운데 정렬
			searchBox.css({
				'display': 'flex',
				'align-items': 'center',
				'justify-content': 'flex-end',
				'gap': '8px',
				'margin-top':'8px',
				'margin-right':'30px',
			});
			
			// 검색 input에 높이 설정
			searchBox.find('input[type="search"]').css({
			    'height': '46px', // 검색 input 필드의 높이 설정
			    'padding': '8px 12px', // input 패딩 조정
			    'box-sizing': 'border-box', // 박스 크기 계산 방식 설정
				'width': '300px'
			});
			
			// 검색 input에 placeholder 추가
			searchBox.find('input[type="search"]').attr('placeholder', '검색어를 입력해주세요');
	   }
});

/****************************************
 * 2. holiday_config : 각자의 테이블 옵션 설정하기 *
 ****************************************/
$('#holiday_config').DataTable({
	 // 화면 크기에 따라 컬럼 width 자동 조절
	 "responsive": true,
	 // 컬럼 width 비율 조절
	 "columnDefs": [
	    { "width": "5%", "targets": 0, "className": "text-center", "orderable": false},
		{ "width": "7%", "targets": 1 },
	    { "width": "53%", "targets": 2 },
	    { "width": "30%", "targets": 3 },
	    { "width": "10%", "targets": 4, "orderable": false }
	],
	"order": [[1, "desc"]], // 여기서 2는 'start_date'가 위치한 컬럼 인덱스
	// 정보 표시 해제
	info: false, 
	// DataTables의 DOM 구조를 재정의
	// 표시건수, 검색, 테이블, 페이징의 위치 재설정
	// (정보 표시 부분을 제외)
	"sDom": '<"row view-filter"<"col-sm-12"<"pull-left"l><"pull-right"f><"clearfix">>>t<"row view-pager"<"col-sm-12"<ip>>>',
	// 페이지네이션 버튼을 전체 숫자와 함께 표시
	pagingType: 'full_numbers',
	// 페이지당 항목 수를 선택할 수 있는 옵션
	lengthMenu: [10, 25, 50, 100],
	// 기본 페이지당 항목 수
	pageLength: 10,

	// 페이징 관련 설정
	drawCallback: function(settings) {
	  	var api = this.api();  // DataTables API 객체
	  	var info = api.page.info();  // 현재 페이지 정보
	  	// 전체 페이지 수
	  	var totalPages = info.pages;
	  	// 현재 페이지 번호
	  	var currentPage = info.page;
	  	// 표시할 페이지 버튼 수
	  	var numButtons = 5;
	  	// 시작 페이지와 종료 페이지 번호 계산
	  	var startPage = Math.max(currentPage - Math.floor(numButtons / 2), 0);
	  	var endPage = Math.min(startPage + numButtons - 1, totalPages - 1);
	  	// endPage가 최대값에 도달한 경우 startPage 조정
	  	if (endPage - startPage < numButtons - 1) {
	      	startPage = Math.max(endPage - numButtons + 1, 0);
	  	}
	  	// 사용자 정의 페이지네이션 HTML 생성
	  	var paginationHtml = '<ul class="pagination">';
	  	paginationHtml += '<li class="paginate_button page-item ' + (currentPage === 0 ? 'disabled' : '') + '"><a class="page-link" href="#" tabindex="0"><<</a></li>';
	  	paginationHtml += '<li class="paginate_button page-item ' + (currentPage === 0 ? 'disabled' : '') + '"><a class="page-link" href="#" tabindex="0"><</a></li>';
	  	for (var i = startPage; i <= endPage; i++) {
	      	var isActive = i === currentPage ? 'active' : '';  // 현재 페이지에 'active' 클래스 적용
	      	paginationHtml += '<li class="paginate_button page-item ' + isActive + '"><a class="page-link" href="#" tabindex="0">' + (i + 1) + '</a></li>';
	  	}
	  	paginationHtml += '<li class="paginate_button page-item ' + (currentPage === totalPages - 1 ? 'disabled' : '') + '"><a class="page-link" href="#" tabindex="0">></a></li>';
	  	paginationHtml += '<li class="paginate_button page-item ' + (currentPage === totalPages - 1 ? 'disabled' : '') + '"><a class="page-link" href="#" tabindex="0">>></a></li>';
	  	paginationHtml += '</ul>';
	  	// 페이지네이션 컨테이너 업데이트
	  	$(api.table().container()).find('.dataTables_paginate').html(paginationHtml);
	  	// 클릭 이벤트 핸들러 추가
	  	$(api.table().container()).find('.paginate_button').on('click', function(e) {
	      	e.preventDefault();  // 기본 링크 동작 방지
	      	if ($(this).hasClass('disabled')) return;  // 비활성화된 버튼 클릭 방지
	      	var idx = $(this).find('a').text();  // 클릭된 버튼의 텍스트 가져오기
	      	if (idx === '<<') {
	          	api.page('first').draw('page');  // 첫 페이지로 이동
	      	} else if (idx === '<') {
	          	api.page('previous').draw('page');  // 이전 페이지로 이동
	      	} else if (idx === '>') {
	          	api.page('next').draw('page');  // 다음 페이지로 이동
	      	} else if (idx === '>>') {
	          	api.page('last').draw('page');  // 마지막 페이지로 이동
	      	} else {
	          	api.page(parseInt(idx) - 1).draw('page');  // 선택된 페이지로 이동
	      	}
	  	});
	},
	"initComplete": function () {
	       // 검색창을 "삭제" 및 "등록" 버튼 왼쪽으로 이동
	       var searchBox = $('div.dataTables_filter');
	       var buttonGroup = $('div.d-flex.justify-content-end');

	       // 검색창을 버튼 그룹 앞에 추가
	       searchBox.prependTo(buttonGroup);
		   
		   // "Search" 텍스트 제거
		   searchBox.find('label').contents().filter(function() {
		       return this.nodeType === 3; // 텍스트 노드를 선택
		   }).remove();
		   
		   // 검색 input을 수직 가운데 정렬
			searchBox.css({
				'display': 'flex',
				'align-items': 'center',
				'justify-content': 'flex-end',
				'gap': '8px',
				'margin-top':'8px',
				'margin-right':'30px',
			});
			
			// 검색 input에 높이 설정
			searchBox.find('input[type="search"]').css({
			    'height': '46px', // 검색 input 필드의 높이 설정
			    'padding': '8px 12px', // input 패딩 조정
			    'box-sizing': 'border-box', // 박스 크기 계산 방식 설정
				'width': '300px'
			});
			
			// 검색 input에 placeholder 추가
			searchBox.find('input[type="search"]').attr('placeholder', '검색어를 입력해주세요');
	   }
});

/******************************************
 * 			3. address_book Table
 * ****************************************/
$('#address_book').DataTable({
  info: false,  // 테이블의 정보 표시를 비활성화합니다.
  "sDom": '<"row view-filter"<"col-sm-12"<"pull-left"l><"pull-right"f><"clearfix">>>t<"row view-pager"<"col-sm-12"<ip>>>',
  pagingType: 'full_numbers',  // 페이지네이션 버튼을 전체 숫자와 함께 표시합니다.
  lengthMenu: [10, 25, 50, 100],  // 페이지당 항목 수를 선택할 수 있는 옵션
  pageLength: 10,  // 기본 페이지당 항목 수
  drawCallback: function(settings) {
      var api = this.api();  // DataTables API 객체를 가져옵니다.
      var info = api.page.info();  // 현재 페이지 정보 가져오기
      
      // 전체 페이지 수를 가져옵니다.
      var totalPages = info.pages;
      
      // 현재 페이지 번호를 가져옵니다.
      var currentPage = info.page;
      
      // 표시할 페이지 버튼 수
      var numButtons = 5;
      
      // 시작 페이지와 종료 페이지 번호를 계산합니다.
      var startPage = Math.max(currentPage - Math.floor(numButtons / 2), 0);
      var endPage = Math.min(startPage + numButtons - 1, totalPages - 1);
      
      // endPage가 최대값에 도달한 경우 startPage 조정
      if (endPage - startPage < numButtons - 1) {
          startPage = Math.max(endPage - numButtons + 1, 0);
      }

      // 사용자 정의 페이지네이션 HTML 생성
      var paginationHtml = '<ul class="pagination">';
      paginationHtml += '<li class="paginate_button page-item ' + (currentPage === 0 ? 'disabled' : '') + '"><a class="page-link" href="#" tabindex="0"><<</a></li>';
      paginationHtml += '<li class="paginate_button page-item ' + (currentPage === 0 ? 'disabled' : '') + '"><a class="page-link" href="#" tabindex="0"><</a></li>';
      
      for (var i = startPage; i <= endPage; i++) {
          var isActive = i === currentPage ? 'active' : '';  // 현재 페이지에 'active' 클래스 적용
          paginationHtml += '<li class="paginate_button page-item ' + isActive + '"><a class="page-link" href="#" tabindex="0">' + (i + 1) + '</a></li>';
      }
      
      paginationHtml += '<li class="paginate_button page-item ' + (currentPage === totalPages - 1 ? 'disabled' : '') + '"><a class="page-link" href="#" tabindex="0">></a></li>';
      paginationHtml += '<li class="paginate_button page-item ' + (currentPage === totalPages - 1 ? 'disabled' : '') + '"><a class="page-link" href="#" tabindex="0">>></a></li>';
      paginationHtml += '</ul>';

      // 페이지네이션 컨테이너를 업데이트합니다.
      $(api.table().container()).find('.dataTables_paginate').html(paginationHtml);
      
      // 클릭 이벤트 핸들러 추가
      $(api.table().container()).find('.paginate_button').on('click', function(e) {
          e.preventDefault();  // 기본 링크 동작 방지
          if ($(this).hasClass('disabled')) return;  // 비활성화된 버튼 클릭 방지
          var idx = $(this).find('a').text();  // 클릭된 버튼의 텍스트 가져오기
          if (idx === '<<') {
              api.page('first').draw('page');  // 첫 페이지로 이동
          } else if (idx === '<') {
              api.page('previous').draw('page');  // 이전 페이지로 이동
          } else if (idx === '>') {
              api.page('next').draw('page');  // 다음 페이지로 이동
          } else if (idx === '>>') {
              api.page('last').draw('page');  // 마지막 페이지로 이동
          } else {
              api.page(parseInt(idx) - 1).draw('page');  // 선택된 페이지로 이동
          }
      });
  }
});

/******************************************
 * 			4. Approve Table
 * ****************************************/

$('#approve_config').DataTable({

 // 화면 크기에 따라 컬럼 width 자동 조절
	 "responsive": true,
	 // 컬럼 width 비율 조절
	 "columnDefs": [
        { "width": "60%", "targets": 0 },
        { "width": "20%", "targets": 1 },
        { "width": "10%", "targets": 2 },
        { "width": "10%", "targets": 3 }
    ],
    // 정보 표시 해제
	info: false, 
	// DataTables의 DOM 구조를 재정의
	// 표시건수, 검색, 테이블, 페이징의 위치 재설정
	// (정보 표시 부분을 제외)
	"sDom": '<"row view-filter"<"col-sm-12"<"pull-left"l><"pull-right"f><"clearfix">>>t<"row view-pager"<"col-sm-12"<ip>>>',
	// 페이지네이션 버튼을 전체 숫자와 함께 표시
  	pagingType: 'full_numbers',
  	// 페이지당 항목 수를 선택할 수 있는 옵션
  	lengthMenu: [10, 25, 50, 100],
  	// 기본 페이지당 항목 수
  	pageLength: 10,
  	
  	// 페이징 관련 설정
  	drawCallback: function(settings) {
      	var api = this.api();  // DataTables API 객체
      	var info = api.page.info();  // 현재 페이지 정보
      	// 전체 페이지 수
      	var totalPages = info.pages;
      	// 현재 페이지 번호
      	var currentPage = info.page;
      	// 표시할 페이지 버튼 수
      	var numButtons = 5;
      	// 시작 페이지와 종료 페이지 번호 계산
      	var startPage = Math.max(currentPage - Math.floor(numButtons / 2), 0);
      	var endPage = Math.min(startPage + numButtons - 1, totalPages - 1);
      	// endPage가 최대값에 도달한 경우 startPage 조정
      	if (endPage - startPage < numButtons - 1) {
          	startPage = Math.max(endPage - numButtons + 1, 0);
      	}
      	// 사용자 정의 페이지네이션 HTML 생성
      	var paginationHtml = '<ul class="pagination">';
      	paginationHtml += '<li class="paginate_button page-item ' + (currentPage === 0 ? 'disabled' : '') + '"><a class="page-link" href="#" tabindex="0"><<</a></li>';
      	paginationHtml += '<li class="paginate_button page-item ' + (currentPage === 0 ? 'disabled' : '') + '"><a class="page-link" href="#" tabindex="0"><</a></li>';
      	for (var i = startPage; i <= endPage; i++) {
          	var isActive = i === currentPage ? 'active' : '';  // 현재 페이지에 'active' 클래스 적용
          	paginationHtml += '<li class="paginate_button page-item ' + isActive + '"><a class="page-link" href="#" tabindex="0">' + (i + 1) + '</a></li>';
      	}
      	paginationHtml += '<li class="paginate_button page-item ' + (currentPage === totalPages - 1 ? 'disabled' : '') + '"><a class="page-link" href="#" tabindex="0">></a></li>';
      	paginationHtml += '<li class="paginate_button page-item ' + (currentPage === totalPages - 1 ? 'disabled' : '') + '"><a class="page-link" href="#" tabindex="0">>></a></li>';
      	paginationHtml += '</ul>';
      	// 페이지네이션 컨테이너 업데이트
      	$(api.table().container()).find('.dataTables_paginate').html(paginationHtml);
      	// 클릭 이벤트 핸들러 추가
      	$(api.table().container()).find('.paginate_button').on('click', function(e) {
          	e.preventDefault();  // 기본 링크 동작 방지
          	if ($(this).hasClass('disabled')) return;  // 비활성화된 버튼 클릭 방지
          	var idx = $(this).find('a').text();  // 클릭된 버튼의 텍스트 가져오기
          	if (idx === '<<') {
              	api.page('first').draw('page');  // 첫 페이지로 이동
          	} else if (idx === '<') {
              	api.page('previous').draw('page');  // 이전 페이지로 이동
          	} else if (idx === '>') {
              	api.page('next').draw('page');  // 다음 페이지로 이동
          	} else if (idx === '>>') {
              	api.page('last').draw('page');  // 마지막 페이지로 이동
          	} else {
              	api.page(parseInt(idx) - 1).draw('page');  // 선택된 페이지로 이동
          	}
      	});
  	}
});

/******************************************
 * 			5. class_by_year
 * ****************************************/
$('#class_by_year').DataTable({
  info: false,  // 테이블의 정보 표시를 비활성화합니다.
  "sDom": '<"row view-filter"<"col-sm-12"<"pull-left"l><"pull-right"f><"clearfix">>>t<"row view-pager"<"col-sm-12"<ip>>>',
  pagingType: 'full_numbers',  // 페이지네이션 버튼을 전체 숫자와 함께 표시합니다.
  lengthMenu: [10, 25, 50, 100],  // 페이지당 항목 수를 선택할 수 있는 옵션
  pageLength: 10,  // 기본 페이지당 항목 수
  drawCallback: function(settings) {
      var api = this.api();  // DataTables API 객체를 가져옵니다.
      var info = api.page.info();  // 현재 페이지 정보 가져오기
      
      // 전체 페이지 수를 가져옵니다.
      var totalPages = info.pages;
      
      // 현재 페이지 번호를 가져옵니다.
      var currentPage = info.page;
      
      // 표시할 페이지 버튼 수
      var numButtons = 5;
      
      // 시작 페이지와 종료 페이지 번호를 계산합니다.
      var startPage = Math.max(currentPage - Math.floor(numButtons / 2), 0);
      var endPage = Math.min(startPage + numButtons - 1, totalPages - 1);
      
      // endPage가 최대값에 도달한 경우 startPage 조정
      if (endPage - startPage < numButtons - 1) {
          startPage = Math.max(endPage - numButtons + 1, 0);
      }

      // 사용자 정의 페이지네이션 HTML 생성
      var paginationHtml = '<ul class="pagination">';
      paginationHtml += '<li class="paginate_button page-item ' + (currentPage === 0 ? 'disabled' : '') + '"><a class="page-link" href="#" tabindex="0"><<</a></li>';
      paginationHtml += '<li class="paginate_button page-item ' + (currentPage === 0 ? 'disabled' : '') + '"><a class="page-link" href="#" tabindex="0"><</a></li>';
      
      for (var i = startPage; i <= endPage; i++) {
          var isActive = i === currentPage ? 'active' : '';  // 현재 페이지에 'active' 클래스 적용
          paginationHtml += '<li class="paginate_button page-item ' + isActive + '"><a class="page-link" href="#" tabindex="0">' + (i + 1) + '</a></li>';
      }
      
      paginationHtml += '<li class="paginate_button page-item ' + (currentPage === totalPages - 1 ? 'disabled' : '') + '"><a class="page-link" href="#" tabindex="0">></a></li>';
      paginationHtml += '<li class="paginate_button page-item ' + (currentPage === totalPages - 1 ? 'disabled' : '') + '"><a class="page-link" href="#" tabindex="0">>></a></li>';
      paginationHtml += '</ul>';

      // 페이지네이션 컨테이너를 업데이트합니다.
      $(api.table().container()).find('.dataTables_paginate').html(paginationHtml);
      
      // 클릭 이벤트 핸들러 추가
      $(api.table().container()).find('.paginate_button').on('click', function(e) {
          e.preventDefault();  // 기본 링크 동작 방지
          if ($(this).hasClass('disabled')) return;  // 비활성화된 버튼 클릭 방지
          var idx = $(this).find('a').text();  // 클릭된 버튼의 텍스트 가져오기
          if (idx === '<<') {
              api.page('first').draw('page');  // 첫 페이지로 이동
          } else if (idx === '<') {
              api.page('previous').draw('page');  // 이전 페이지로 이동
          } else if (idx === '>') {
              api.page('next').draw('page');  // 다음 페이지로 이동
          } else if (idx === '>>') {
              api.page('last').draw('page');  // 마지막 페이지로 이동
          } else {
              api.page(parseInt(idx) - 1).draw('page');  // 선택된 페이지로 이동
          }
      });
  }
});

/******************************************
 * 			Custom Table
 * ****************************************/

$('#custom_config').DataTable({
    // 필요 옵션 입력
    // 없으면 기본 설정된 값으로 생성됩니다.
});
