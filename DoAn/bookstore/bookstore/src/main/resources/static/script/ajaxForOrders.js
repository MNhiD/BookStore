
$(".btn-info-order").click(function() {


	var orderId = $(this).attr("data-order-id");




	$.ajax({

		type: "POST",
		url: "http://localhost:8080/order-detail",
		data: {
			orderId: orderId

		},
		success: function(value) {
			$("#order-detail-block").replaceWith(value);
			$("#order-detail-block").show();

		}, error: () => {
			console.log('Error');


		}
	})


})

$(".btn-cancle-order").click(function() {

	var r = confirm("Bạn có chắc chắn muốn xóa hủy đơn hàng? ");
	if (r == true) {
		var orderId = $(this).attr("data-order-id");
		var thisBlock = $(this);
		var orderStatus = $(this).parent("div").parent("div").find(".order-status");

	$.ajax({

		type: "POST",
		url: "http://localhost:8080/cancel-order",
		data: {
			orderId: orderId

		},
		success: function() {
			$(thisBlock).hide();
			$(orderStatus).html('<p class="order-address order-status" style="color: orange; padding-left: 20px" ><i class="fas fa-hourglass-half"></i> Trạng thái: Yêu cầu hủy</p>')
		}, error: () => {
			console.log('Error');


		}
	})

	} else {
		alert("Thao tác bị hủy"); 
	}
	

})
$("#btn-order-again").click(function() {

	alert("bạn đặt hàng lại à");

})












