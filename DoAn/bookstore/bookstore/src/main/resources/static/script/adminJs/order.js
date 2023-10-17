
 const frmOrderDetail = $(".container-order-detail");
 const btnEditOrder = $(".btn__edit--order");

/* =================EVENT BUTTON OPEN DETAIL============== */
btnEditOrder.click(() => {
	frmOrderDetail.removeClass("bounceOutUp")
	$(".main__overlay").css("display", "block");
	frmOrderDetail.css("display", "block");
	/* EVENT CLOSE FORM  */
	$(".btn.btn-close-from").click((e) => {
		$(".main__overlay").css("display", "none");
		frmOrderDetail.css("display", "none");
	})
});

function closeOrderDetail(){
	frmOrderDetail.addClass("bounceOutUp")
	
	setTimeout(function(){
		frmOrderDetail.css("display", "none");
		$(".main__overlay").css("display", "none");
		const detail = document.querySelector(".container-order-detail .card");
		detail.innerHTML = "";
	},500)
		
}

/* =================EVENT BUTTON CHANGE STATUS============== */
const btnChangeStatus = $(".btn__change--status");
const frmChangeStatus = $(".from__confirm");
btnChangeStatus.each((i,btn)=>{
	btn.onclick=(e) => {
		$(".main__overlay").css("display", "block");
		let targetClass=$(e.target)
		frmChangeStatus.find("#order-id").text("Bạn có muốn"+targetClass.text()+" đơn hàng có mã: "+targetClass.children().attr('id'))
		frmChangeStatus.find("#action-change").attr("href",targetClass.children().val())
		frmChangeStatus.css("display", "block");
		
		/* EVENT CLOSE FORM */
		$(".btn-cancel").click((e) => {
			$(".main__overlay").css("display", "none");
			frmChangeStatus.css("display", "none");
		})
	};
})


function setStatus(status){
	//Đã xác nhận
	if(status==0){
		$('.track .ready.step').addClass("active");
		$('.track .confirm.step').addClass("active");
		$('.track .done.step').addClass("active");
		
	}
	//Chờ xác nhận
	else if(status==1){
		$('.track .ready.step').addClass("active");
	}
	//Đã hủy
	else {
		$('.track .ready.step').addClass("active");
		$('.track .confirm.step').addClass("active");
		$('.track .done.step').addClass("active");
		$('.done.step > .icon i').attr("class","fas fa-times");
		$('.done.step  .text').text('Đã hủy');
	}
}