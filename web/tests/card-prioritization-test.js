module("card-prioritization");
test("max_priority", function() {
	expect(2);
	
	equals(max_priority, 0, "max_priority should start with zero if there are no boards");
	$('<ul class="board"></ul>').appendTo('body');
	$('<ul class="board"></ul>').appendTo('body');
	$('<ul class="board"></ul>').appendTo('body');

	fixPriorityLevels();
	equals(max_priority, 2, "max_priority should be the number of boards minus one (lower priority)");
	
	$('.board').remove();
});

test("cards of zero priority level are infinity priority ", function() {
	expect(5);
	var form = $('<form id="prioritizationForm"></form>').appendTo('body');
	var div = $('<div id="test"><div id="level_0"></div><div id="title_0"></div></div>').appendTo('body');
	infinityText = "infinity";
	fixInfinityPriority();
	
	equals($('#test *').length, 0, "should move level_0");
	equals($('#prioritizationForm #level_0').length, 1, "should move to form");
	equals($('#prioritizationForm #title_0').length, 1, "should move to form");
	equals($('#level_0').attr('title'), "infinity", "should change level 0 title");
	equals($('#title_0').html(), "infinity", "should change title 0");
	
	form.remove();
	div.remove();
});

test("should fix array parameters", function() {
	expect(8);
	var card1 = $('<div class="card"><input id="first" name="abc[#]"/><input id="second" name="def[#]"/></div>').appendTo('body');
	var card2 = $('<div class="card"><input id="third" name="abc[#]"/><input id="forth" name="def[#]"/></div>').appendTo('body');
	var undo1 = $('<div class="undo"><input id="fifth" name="abc[#]"/><input id="sixth" name="def[#]"/></div>').appendTo('body');
	var undo2 = $('<div class="undo"><input id="seventh" name="abc[#]"/><input id="eighth" name="def[#]"/></div>').appendTo('body');
	
	fixParameters();
	equals($('#first').attr('name'), "abc[0]");
	equals($('#second').attr('name'), "def[0]");
	equals($('#third').attr('name'), "abc[1]");
	equals($('#forth').attr('name'), "def[1]");
	equals($('#fifth').attr('name'), "abc[0]");
	equals($('#sixth').attr('name'), "def[0]");
	equals($('#seventh').attr('name'), "abc[1]");
	equals($('#eighth').attr('name'), "def[1]");
});
