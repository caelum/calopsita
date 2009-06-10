<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<div id="timeline">
  <div id="start_and_today">
    <div id="start" class="date_and_title" >
      <div class="title_date title">Start</div>
      <div id="start_date" class="date">
        <div class="year">${iteration.startDate.year }</div>
        <div class="day">${iteration.startDate.dayOfMonth }</div>
        <div class="month"><joda:format value="${iteration.startDate}" pattern="MMM" /></div>
      </div>
      <div class="title_date title"></div>
    </div>
  
    <hr class="line" id="start_today_line" />
    
    <div id="today_start" class="date_and_title" >
      <div class="title_date title"></div>
      <div id="today_start_date" class="date today" >
        <div class="year">${today.year }</div>
        <div class="day">${today.dayOfMonth }</div>
        <div class="month"><joda:format value="${today}" pattern="MMM" /></div>
      </div>
      <div class="title_date title">Today</div>
    </div>
  </div>
    
  <hr class="line" id="start_end_line" />
    
  <div id="today_and_end">
    <div id="today_end" class="date_and_title" >
      <div class="title_date title"></div>
      <div id="today_end_date" class="date today" >
        <div class="year">${today.year }</div>
        <div class="day">${today.dayOfMonth }</div>
        <div class="month"><joda:format value="${today}" pattern="MMM" /></div>
      </div>
      <div class="title_date title">Today</div>
    </div>

    <hr class="line" id="today_end_line" />
          
    <div id="end" class="date_and_title" >
      <div class="title_date title">End</div>
      <div id="end_date" class="date" >
        <div class="year">${iteration.endDate.year }</div>
        <div class="day">${iteration.endDate.dayOfMonth }</div>
        <div class="month"><joda:format value="${iteration.endDate}" pattern="MMM" /></div>
      </div>
      <div class="title_date title"></div>
    </div>
  </div>
</div>