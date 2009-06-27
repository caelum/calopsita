<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<div id="timeline">
  <div id="start_and_today">
    <div id="start" class="date_and_title" >
      <div id="start_title" class="title_date title">Start</div>
      <div id="start_date" class="date">
        <div id="start_year" class="year">${iteration.startDate.year }</div>
        <div id="start_day" class="day"><joda:format value="${iteration.startDate }" pattern="dd"/></div>
        <div id="start_month" class="month"><joda:format value="${iteration.startDate}" pattern="MMM" /></div>
      </div>
      <div class="title_date title"></div>
    </div>
  
    <hr class="line" id="start_today_line" />
    
    <div id="today_start" class="date_and_title" >
      <div class="title_date title" id="today_start_title"></div>
      <div id="today_start_date" class="date today" >
        <div id="today_year" class="year">${today.year }</div>
        <div id="today_day" class="day"><joda:format value="${today}" pattern="dd"/></div>
        <div id="today_month" class="month"><joda:format value="${today}" pattern="MMM" /></div>
      </div>
      <div class="title_date title">Today</div>
    </div>
  </div>
    
  <hr class="line" id="start_end_line" />
    
  <div id="today_and_end">
    <div id="today_end" class="date_and_title" >
      <div class="title_date title"></div>
      <div id="today_end_date" class="date today" >
        <div id="today_year" class="year">${today.year }</div>
        <div id="today_day" class="day"><joda:format value="${today }" pattern="dd"/></div>
        <div id="today_month" class="month"><joda:format value="${today}" pattern="MMM" /></div>
      </div>
      <div class="title_date title">Today</div>
    </div>

    <hr class="line" id="today_end_line" />
          
    <div id="end" class="date_and_title" >
      <div class="title_date title">End</div>
      <div id="end_date" class="date" >
        <div id="end_year" class="year">${iteration.endDate.year }</div>
        <div id="end_day" class="day"><joda:format value="${iteration.endDate }" pattern="dd"/></div>
        <div id="end_month" class="month"><joda:format value="${iteration.endDate}" pattern="MMM" /></div>
      </div>
      <div class="title_date title"></div>
    </div>
  </div>
</div>