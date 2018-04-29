$(document).ready(function() {
	/* Initialize modal */
	$("#modal-prompt-model-message").text("Are you sure to remove Masterpass pairing?");
	
	
	var uri = URI(window.location);
	if (uri.query() != null) {
		var tab = URI.parseQuery(uri.query()).tab;
		$("#" + tab).trigger("click");
	}
});

function pair() {
	masterpass.pair({
		"checkoutId" : checkoutId, // This is a unique, 32-character
									// alphanumeric identifier generated
									// by Masterpass, which identifies
									// your settings during a checkout.
									// You can find this value in your
									// Mastercard Developers account.
		"userId" : userId
	// The unique consumer user identifier from the Merchant site.
	});
}

function show(id){
	/* Invisible all */
	$("#modal-loading").removeClass("d-block").addClass("d-none");
	$("#modal-success").removeClass("d-block").addClass("d-none");
	$("#modal-error").removeClass("d-block").addClass("d-none");
	$("#save-changes-error").removeClass("d-block").addClass("d-none");
	$("#modal-footer").removeClass("d-block").addClass("d-none");
	
	/* Visible */
	$(id).removeClass("d-none").addClass("d-block");
	if (id == "#modal-success"){
		$("#modal-footer").removeClass("d-none").addClass("d-block");
	}
}

function precheckoutSuccess(data) {
	console.log("Response : ", JSON.stringify(data));

	var card = "";
	card = '<div class="card bg-light">';
	card += '<div class="card-body">';
	card += '<div class="custom-control custom-radio">';
	card += '<input type="radio" name="radio-card" class="custom-control-input" id="{0}" checked="{6}">';
	card += '<label class="custom-control-label" for="{0}">';
	card += '<h5 class="card-title">Card ending in <span id="{0}-masterpass-card-last4">{1}</span></h5>';
	card += '<p class="card-text">{2} issued to {3} untill {4}. month of {5}</p>';
	card += '<span class="d-none" id="{0}-masterpass-card-id">{7}</span>';
	card += '</label></input></div></div></div>';
	var cardsHtml = "";

	/* populate cards */
	data.cards.forEach(function(item) {
		var id = item.cardId;
		var last4 = item.lastFour;
		var brand = item.brandName;
		var cardHolder = item.cardHolderName;
		var month = item.expiryMonth;
		var year = item.expiryYear;
		var isDefault = item.default;
		var cardId = item.cardId;
		cardsHtml += card.format([ id, last4, brand, cardHolder, month,
				year, isDefault, cardId ]);
	});
	$("#cards").html(cardsHtml);
	
	var address = "";
	address = '<div class="card bg-light">';
	address += '<div class="card-body">';
	address += '<div class="custom-control custom-radio">';
	address += '<input type="radio" name="radio-address" class="custom-control-input" id="{0}" checked="{7}">';
	address += '<label class="custom-control-label" for="{0}">';
	address += '<h5 class="card-title" id="{0}-masterpass-recipient-name">{1}</h5>';
	address += '<p class="card-text">{3}<br />{4}{5}, {6}<br /><span id="{0}-masterpass-recipient-phone">{2}</span></p>';
	address += '<span class="d-none" id="{0}-masterpass-address-id">{8}</span>';
	address += '<span class="d-none" id="{0}-masterpass-address">{9}</span>';
	address += '</label></input></div></div></div>';
	var addressesHtml = "";

	/* populate shipping addresses */
	data.shippingAddresses.forEach(function(item) {
		var id = item.addressId;
		var recipent = item.recipientInfo.recipientName;
		var phone = item.recipientInfo.recipientPhone;
		var line1 = item.line1;
		var line2 = item.line2 == null ? "" : item.line2 + "<br />";
		var city = item.city;
		var postal = item.postalCode;
		var isDefault = item.default;
		var addressId = item.addressId;
		
		var addressLine = "";
		addressLine += item.line1;
		if (item.line2 != null) {
			addressLine += " " + item.line2;
		}
		addressLine += " " + item.city;
		addressLine += " " + item.postalCode;
		
		addressesHtml += address.format([ id, recipent, phone, line1, line2,
			city, postal, isDefault, addressId, addressLine ]);
	});
	$("#shipping-addresses").html(addressesHtml);

	/* Visible only success */
	show("#modal-success");
	precheckoutLoaded = true;
}

function error(e) {
	show("#modal-error");
	precheckoutLoaded = false;
}

function getMasterpassPrecheckout() {
	if (precheckoutLoaded) {
		return;
	}
	show("#modal-loading");
	
	/* Error message setting */
	$("#modal-error-message").html("We cannot fetch your card details!");
	
	var request = {};
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/masterpass/precheckout",
		data : JSON.stringify(request),
		dataType : "json",
		cache : false,
		timeout : 30000,
		success : precheckoutSuccess,
		error : error
	});
}

function saveChangesSuccess(){
	$("#modal-card-and-ahipping-address").modal("hide");
	location.reload();
}

function saveChanges(){
	show("#modal-loading");
	
	var selectedCardId = $("input[name=radio-card]:checked")[0].id;
	var selectedAddressId = $("input[name=radio-address]:checked")[0].id;
	
	console.log("#" + selectedAddressId + "-masterpass-recipient-name");
	console.log("#" + selectedCardId + "-masterpass-recipient-name");
	
	var request = {
			recipientName: $("#" + selectedAddressId + "-masterpass-recipient-name").html(),
			recipientPhone: $("#" + selectedAddressId + "-masterpass-recipient-phone").html(),
			cardId: $("#" + selectedCardId + "-masterpass-card-id").html(),
			cardLast4: $("#" + selectedCardId + "-masterpass-card-last4").html(),
			addressId: $("#" + selectedAddressId + "-masterpass-address-id").html(),
			address: $("#" + selectedAddressId + "-masterpass-address").html()
	};
	
	console.log(request);
	
	/* Error message setting */
	$("#modal-error-message").html("We cannot perform your operation!");
	
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/masterpass/save-precheckout",
		data : JSON.stringify(request),
		dataType : 'json',
		cache : false,
		timeout : 30000,
		success : saveChangesSuccess,
		error : error
	});
}

function promptModalYesButtonClicked(){
	window.location.href="/masterpass/remove-pairing";	
}