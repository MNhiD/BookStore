$("#btn-verify-old-pass").click(function() {
		var password = $("#password").val();
	 
	$.ajax({
		
		type: "POST",
        url: "http://localhost:8080/verify-old-password",
		data: {
			password: password
		},
		success: function(value) {
			 
			if (value == "true") {
				$("#new-password-block").show();
				$("#verify-old-password-block").hide();
			}else{
				$("#result").text("Mật khẩu xác thực không chính xác!"); 
			}
		},error: () => {
		console.log('Error');
	}

	})
})




$("#btn-udpate-new-pass").click(function() {
		var password = $("#newPassword").val();
		var verifyPassword = $("#verifyNewPassword").val(); 
		$("#messageLengthPasswordFail").text(""); 
		$("#messageVerifyPassword").text("");
		$("#messageUpdateFail").text(""); 
	 
	$.ajax({
		
		type: "POST",
        url: "http://localhost:8080/update-new-password",
		data: {
			password: password,
			verifyPassword: verifyPassword
		},
		success: function(value) {
			 
			if (value == "0") {
				alert("Cập nhật mật khẩu thành công!"); 
				
				window.location.replace("/account/userInfo");
			}else if(value=="1"){
				$("#messageLengthPasswordFail").text("Mật khẩu phải có tối thiểu 5 ký tự"); 
			}else if(value=="2"){
				$("#messageVerifyPassword").text("Mật khẩu xác thực không trùng khớp"); 
			}else if(value=="3"){
				$("#messageUpdateFail").text("Cập nhật mật khẩu không thành công!"); 
			}
		},error: () => {
		console.log('Error');
	}

	})
})

























