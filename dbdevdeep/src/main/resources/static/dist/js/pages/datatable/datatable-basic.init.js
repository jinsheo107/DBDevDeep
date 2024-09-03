/*************************************************************************************/
// -->Template Name: Bootstrap Press Admin
// -->Author: Themedesigner
// -->Email: niravjoshi87@gmail.com
// -->File: datatable_basic_init
/*************************************************************************************/

/****************************************
 *       Basic Table                   *
 ****************************************/
$('#zero_config').DataTable();

/****************************************
 *       Default Order Table           *
 ****************************************/
$('#default_order').DataTable({
    "order": [
        [3, "desc"]
    ]
});

/****************************************
 *       Multi-column Order Table      *
 ****************************************/
$('#multi_col_order').DataTable({
    columnDefs: [{
        targets: [0],
        orderData: [0, 1]
    }, {
        targets: [1],
        orderData: [1, 0]
    }, {
        targets: [4],
        orderData: [4, 0]
    }]
});


/******************************************
 * 			Custom Table
 * ****************************************/
$('#address_book').DataTable({
  info: false,  // 테이블의 정보 표시를 비활성화합니다.
  "sDom": '<"row view-filter"<"col-sm-12"<"pull-left"l><"pull-right"f><"clearfix">>>t<"row view-pager"<"col-sm-12"<ip>>>',
  pagingType: 'full_numbers',  // 페이지네이션 버튼을 전체 숫자와 함께 표시합니다.
  lengthMenu: [1, 25, 50, 100],  // 페이지당 항목 수를 선택할 수 있는 옵션
  pageLength: 1,  // 기본 페이지당 항목 수
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