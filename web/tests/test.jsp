<html>
<head>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<link rel="stylesheet" href="testsuite.css" type="text/css"
	media="screen" />

<script type="text/javascript" src="../javascript/jquery/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="testrunner.js"></script>
<script>
	$(document).ready( function() {
		module("duh");
		test("a basic test example", function() {
			ok(true, "this test is fine");
			var value = "hello";
			equals("hello", value, "We expect value to be hello");
		});

		module("Module A");

		test("first test within module", function() {
			ok(true, "all pass");
		});

		test("second test within module", function() {
			ok(true, "all pass");
		});

		module("Module B");

		test("some other test", function() {
			expect(2);
			equals(true, false, "failing test");
			equals(true, true, "passing test");
		});

	});
</script>

</head>
<body>

<h1>QUnit example</h1>
<h2 id="banner"></h2>
<h2 id="userAgent"></h2>

<ol id="tests"></ol>

<div id="main"></div>

</body>
</html>
