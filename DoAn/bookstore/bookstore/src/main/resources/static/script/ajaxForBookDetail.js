$(".rating-submit").click(function() {
		var rating = parseFloat($('input[name="rating"]:checked').val());
		var comment = $("#comment").val(); 
		var bookId =  $(this).attr("data-bookId"); 
		
	 	
	$.ajax({
		
		type: "POST",
        url: "http://localhost:8080/review",
		data: {
			idBook: bookId, 
			star : rating, 
			comment: comment
		},
		success: function(value) {
			
			location.reload(); 
		},error: () => {
		console.log('Error');
	}

	})
})

$("#btn-review-again").click(function() {
		$("#inform-review-message").hide(); 
		$("#review-again-block").show(); 
})

































