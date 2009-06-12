<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="testrunner.js"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="testsuite.css" />
<script type="text/javascript">
$(document).ready(function(){
    
	test("a basic test example", function() {
	  ok( true, "this test is fine" );
	  var value = "hello";
	  equals( "hello", value, "We expect value to be hello" );
	});

	module("Module A");

	test("first test within module", function() {
	  ok( true, "all pass" );
	});

	test("second test within module", function() {
	  ok( true, "all pass" );
	});

	module("Module B");

	test("some other test", function() {
	  expect(2);
	  equals( true, false, "failing test" );
	  equals( true, true, "passing test" );
	});

	  });
	  </script>

</head>
<body>
<h1>Calopsita tests</h1>
<h2 id="banner"></h2>
<h2 id="userAgent"></h2>

<ol id="tests"></ol>

<div id="main"></div>

</body>
</html>