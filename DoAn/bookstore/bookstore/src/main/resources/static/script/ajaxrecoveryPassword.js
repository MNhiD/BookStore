
$(".recoverpass_btn").click(function() {
		$("#verify-password-invalid").hide();
		$("#verify-length-password").hide();
		let check = true; 
	 	var password = $("#password").val(); 
		var verifyPassword = $("#verifyPassword").val(); 
		if(password.length<5){
			$("#verify-length-password").show();
			check = false; 
		}
		if(password!=verifyPassword){
			$("#verify-password-invalid").show(); 
			check = false; 
		}
		if(check !=true) return ; 
	
	$.ajax({
		
		type: "POST",
        url: "http://localhost:8080/verify/update",
		data: {
			password: password
		},
		success: function(value) {
			
			if (value == "true") {
				alert("Cập nhật mật khẩu thành công!"); 
				window.location.replace("/account/login");
			}else{
				alert("Cập nhật thất bại!");  
			}
		},error: () => {
		console.log('Error');
	}

	})
	
})