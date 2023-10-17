/* =================EVENT BUTTON ADD USER ADDRESS============== */
const btnAddUserAddress = $(".btn__add--userAddress");/* btn Add new book */
const frmAddUserAddress = $(".from__add--userAddress");/* Form add new book */

btnAddUserAddress.click(() => {
	frmAddUserAddress.removeClass("bounceOutUp")
	$(".main__overlay").css("display", "block");//làm mờ background
	clearnField();
	frmAddUserAddress.css("display", "block");		// hiển thị form
	
	//frmAddUserAddress.find(".card-header:first").children("span").text("NEW USER ADDRESS") //đổi tiêu đề form
	frmAddUserAddress.find(".card-header:first").css({"background-color": "#28A745","color": "white"})// đổi màu form
	
	/* EVENT CLOSE FORM ADD BOOK */
	$(".far.btn-close-from").click((e) => {
		frmAddUserAddress.addClass("bounceOutUp")
		
		$(".form-message.invalid").removeClass("invalid")
		setTimeout(function(){
			frmAddUserAddress.css("display", "none");
			$(".main__overlay").css("display", "none");
		},500)
		clearnField();
		
	})
});

/* =================EVENT BUTTON EDIT ADDRESS============== */
const btnEditUserAddress = $(".btn__edit--userAddress");/* btn edit  user */

btnEditUserAddress.click(() => {
	frmAddUserAddress.removeClass("bounceOutUp")
	$(".main__overlay").css("display", "block");
	frmAddUserAddress.css("display", "block");
	frmAddUserAddress.find(".card-header:first").children("span").text("EDIT USER ADDRESS") 
	frmAddUserAddress.find(".card-header:first").css({"background-color": "#FFC107","color": "black"})
	
	/* EVENT CLOSE FORM ADD BOOK */
	$(".far.btn-close-from").click((e) => {
		frmAddUserAddress.addClass("bounceOutUp")
		$(".form-message.invalid").removeClass("invalid")
		setTimeout(function(){
			frmAddUserAddress.css("display", "none");
			$(".main__overlay").css("display", "none");
		},500)
		//clearnField();
		
	})
});



/* =================EVENT BUTTON DELETE ADDRESS============== */
const btnDeleteUserAddress = $(".btn__delete--userAddress");/* btn Add new book */
const diaDeleteUserAddress = $(".from__confirm");/* Form add new book */
const inputDelete = $(".input-delete");

btnDeleteUserAddress.click((e) => {
	$(".main__overlay").css("display", "block");
	let targetClass=$(e.target)
	if(targetClass.attr('class')=="far fa-trash-alt"){
		targetClass=targetClass.parent();
	}
	diaDeleteUserAddress.find("#userAddress-id").text(targetClass.attr("id"))
	inputDelete.val(targetClass.attr("id"))
	console.log(targetClass.attr("id"));
	diaDeleteUserAddress.css("display", "block");
	
	/* EVENT CLOSE FORM */
	$(".btn-cancel").click((e) => {
		$(".main__overlay").css("display", "none");
		diaDeleteUserAddress.css("display", "none");
	})
});

function clearnField(){
	$('.card-body #addressId').val(0);
	$('#province option').removeAttr('selected').filter('[0]').attr('selected', true)
	$('#district option').removeAttr('selected').filter('[0]').attr('selected', true)
	$('#village option').removeAttr('selected').filter('[0]').attr('selected', true)
    $("#addressName").val("")
}