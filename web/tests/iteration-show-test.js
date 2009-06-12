module("iteration-show");
test("getParams", function() {
	expect(4);
	var div = $('<div class="outer"><div class="ui-selected"><span>1234</span></div></div>');
	div.appendTo('body');

	var params = get_params('div.outer', 'STATUS');
	ok(params['cards[0].id'], 'should create cards.id property');
	equals(1234, params['cards[0].id'], 'should set cards.id property with span content');
	ok(params['cards[0].status'], 'should create cards.status property');
	equals('STATUS', params['cards[0].status'], 'should set cards.status property with parameter');
	
	div.remove();
});
