
/* =================EVENT BUTTON ADD BOOK============== */
const btnAddBook = $(".btn__add--book");/* btn Add new book */
const frmAddBook = $(".from__add--book");/* Form add new book */

btnAddBook.click(() => {
	frmAddBook.removeClass("bounceOutUp")
	$(".main__overlay").css("display", "block");//làm mờ background
	frmAddBook.css("display", "block");		// hiển thị form
	
	frmAddBook.find(".card-header:first").children("span").text("NEW BOOK") //đổi tiêu đề form
	frmAddBook.find(".card-header:first").css({"background-color": "#28A745","color": "white"})// đổi màu form
	
	/* EVENT CLOSE FORM ADD BOOK */
	$(".far.btn-close-from").click((e) => {
		
		frmAddBook.addClass("bounceOutUp")
		$(".form-message.invalid").removeClass("invalid")
		setTimeout(function(){
			frmAddBook.css("display", "none")
			$(".main__overlay").css("display", "none");
			clearnField();
		},500)
	})
});

/* =================EVENT BUTTON EDIT BOOK============== */
const btnEditBook = $(".btn__edit--book");/* btn edit  book */

btnEditBook.click(() => {
	frmAddBook.removeClass("bounceOutUp")
	$(".main__overlay").css("display", "block");
	frmAddBook.css("display", "block");
	//frmAddBook.attr("action","/admin/book/save")
	frmAddBook.find(".card-header:first").children("span").text("EDIT BOOK") 
	frmAddBook.find(".card-header:first").css({"background-color": "#FFC107","color": "black"})
	
	/* EVENT CLOSE FORM edit BOOK */
	$(".far.btn-close-from").click((e) => {
		frmAddBook.addClass("bounceOutUp")
		$(".form-message.invalid").removeClass("invalid")
		
		setTimeout(function(){
			frmAddBook.css("display", "none")
			$(".main__overlay").css("display", "none");
			clearnField();
		
		},500)
		
	})
});


/*======================FORM ADD BOOK HANDLE ===================*/
const selects = document.querySelectorAll('.box-options');
selects.forEach((select,index)=>{
	const options = Array.from(select.options);
	const input = select.previousElementSibling;
	input.autocomplete="off";
	/*EVENT SHOW SECLECT BOX */
	input.onfocus=(e)=>{
		select.style.display="block";
	};
	
	/*EVENTS CLOSE SECLECT BOX */
	select.onchange=(e)=>{
		select.style.display="none";
		input.value=(select.options.item(select.selectedIndex).text)
		input.parentElement.querySelector('.form-message').classList.remove('invalid');   
		
	}
	input.onkeypress=(e)=>{
	  	if(e.which==13){
			select.style.display="none";
		} 	
	};
	$("form.from__add--book").click((e)=>{
			const classTarger=e.target.parentElement.id;
			if(classTarger!==select.parentElement.id){
				select.style.display="none";
			}
	})
  
	/*FILLER SELECT OPTION*/
  	function findMatches (search, options) {
  	  return options.filter(option => {
	      const regex = new RegExp(search, 'gi');
	      	return option.text.match(regex);
    	});
  	}
 	function filterOptions () {
		options.forEach(option => { 
	   		option.remove();
	    	option.selected = false;
	   });
    	const matchArray = findMatches(this.value, options);
    	select.append(...matchArray);
  	}
  	input.onchange=(filterOptions);
  	input.addEventListener('keyup', filterOptions);
})
/*-----LOAD IMAGE-------*/
const loadFile = function(event) {
	let image = document.getElementById('img-output');
	image.src = URL.createObjectURL(event.target.files[0]);
};


/* =================EVENT BUTTON DELETE BOOK============== */
const btnDeleteBook = $(".btn__delete--book");/* btn Add new book */
const diaDeleteBook = $(".from__confirm");/* Form add new book */
const inputDelete = $(".input-delete");

btnDeleteBook.click((e) => {
	$(".main__overlay").css("display", "block");
	let targetClass=$(e.target)
	if(targetClass.attr('class')=="far fa-trash-alt"){
		targetClass=targetClass.parent();
	}
	diaDeleteBook.find("#book-id").text(targetClass.attr("id"))
	inputDelete.val(targetClass.attr("id"))
	console.log(targetClass.attr("id"));
	diaDeleteBook.css("display", "block");
	
	/* EVENT CLOSE FORM */
	$(".btn-cancel").click((e) => {
		$(".main__overlay").css("display", "none");
		diaDeleteBook.css("display", "none");
	})
});

function clearnField(){
	$('.form-row #idBook').val(0);
	$('#book-name').val("");
	$('#author').val("");
	$('#company').val("");
	$('#datepickerfrom').val("");
	$('#price').val("");
	$('#img-output').attr("src","/image/choosing-img-icon.png");
	$('#bookcategory').val("");
	$('#total-quantity').val("");
	$('#describe-book').val("");
	$('.option-input').each((i,input)=>{
		input.onchange();
	})
}



