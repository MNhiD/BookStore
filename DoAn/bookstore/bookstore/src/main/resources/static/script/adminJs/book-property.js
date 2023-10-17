
/* =================EVENT BUTTON ADD Category============== */
const btnAdd = $(".btn__add--book-propterty");
const frmAdd = $(".from__add--book-propterty");

btnAdd.click((e) => {
	clearnField();//clear ô input
	frmAdd.toggleClass("d-none");// hiển thị form
	
	//kiểm tra có phải icon đóng ko
	const checkChange=(btnAdd.html()=='<i class="far fa-times-circle"></i> Close');
	
	//nếu là icon Đóng thì chuyển sang icon Add và ngược lại
	btnAdd.html((checkChange)?'<i class="fas fa-plus"></i> Add new':'<i class="far fa-times-circle"></i> Close')
	
	btnAdd.toggleClass("btn-success")//nếu là màu xanh lá thì bỏ hoặc ngược lại là set màu xanh lá
	btnAdd.toggleClass("btn-danger")//nếu là màu xanh lá thì bỏ hoặc ngược lại là set màu xanh lá
	
});


/* =================EVENT BUTTON EDIT Category============== */

$(".btn__edit--book-propterty").click((e) => {
	clearnField();//clear ô input
	
	var btnEdit = $(e.target);/* btn edit  */
	if(btnEdit.attr('class')=="far fa-edit" || btnEdit.attr('class')=="far fa-times-circle"){// nếu đang trỏ đến icon trong btn thì cho nó trỏ lại về btn
		btnEdit=$(e.target).parents('button');
	}
   	const frmEdit = $(e.target).parents("tr").find("form");// lấy form tương ứng với btn đc nhấn
   	const eleContent=$(e.target).parents("tr").find(".content")// lấy dự liệu tương ứng với btn đc nhấn
   	eleContent.toggleClass("d-none")//ẩn dữ liệu đi
   	
	
	frmEdit.toggleClass("d-none");// hiển thị form nếu đang đóng và ngược lại đóng nếu đang mở
	
	//nếu là icon Đóng thì chuyển sang icon Edit và ngược lại
	const checkChange=(btnEdit.html()=='<i class="far fa-times-circle"></i>');
	btnEdit.html((checkChange)?' <i class="far fa-edit"></i>':'<i class="far fa-times-circle"></i>')
	btnEdit.toggleClass("btn-warning")
	btnEdit.toggleClass("btn-danger")
});



/* =================EVENT BUTTON DELETE Category============== */
const btnDelete = $(".btn__delete--book-propterty");/* btn Add new book */
const diaDelete = $(".from__confirm");/* Form add new book */
const inputDelete = $(".input-delete");

btnDelete.click((e) => {
	$(".main__overlay").css("display", "block");
	let targetClass=$(e.target)
	if(targetClass.attr('class')=="far fa-trash-alt"){
		targetClass=targetClass.parent();
	}
	
	diaDelete.find("#form-content").text($(e.target).parents("tr").find(".content").text())
	inputDelete.val(targetClass.attr("id"))
	console.log(targetClass.attr("id"));
	diaDelete.css("display", "block");
	
	/* EVENT CLOSE FORM */
	$(".btn-cancel").click((e) => {
		$(".main__overlay").css("display", "none");
		diaDelete.css("display", "none");
	})
});

function clearnField(){
	$('.addId').val(0);
	$('.input-add').val("");
	$(".form-message.invalid").removeClass("invalid")
}



