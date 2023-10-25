
/* =================EVENT BUTTON ADD USER============== */
const btnAddUser = $(".btn__add--user");/* btn Add new book */
const frmAddUser = $(".from__add--user");/* Form add new book */

btnAddUser.click(() => {
	frmAddUser.removeClass("bounceOutUp")
	$(".main__overlay").css("display", "block");//làm mờ background
	clearnField();
	frmAddUser.css("display", "block");		// hiển thị form
	
	//frmAddUser.find(".card-header:first").children("span").text("ĐƠN NHẬP HÀNG") //đổi tiêu đề form
	frmAddUser.find(".card-header:first").css({"background-color": "#28A745","color": "white"})// đổi màu form
	
	/* EVENT CLOSE FORM ADD BOOK */
	$(".far.btn-close-from").click((e) => {
		$(".form-message.invalid").removeClass("invalid")
		frmAddUser.addClass("bounceOutUp")
		setTimeout(function(){
			frmAddUser.css("display", "none");
			$(".main__overlay").css("display", "none");
			clearnField();
		},500)
		
	})
});

/* =================EVENT BUTTON EDIT user============== */
const btnEditUser = $(".btn__edit--user");/* btn edit  user */

btnEditUser.click(() => {
	$(".main__overlay").css("display", "block");
	frmAddUser.css("display", "block");
	//frmAddBook.attr("action","/admin/book/save")
	frmAddUser.find(".card-header:first").children("span").text("EDIT USER") 
	frmAddUser.find(".card-header:first").css({"background-color": "#FFC107","color": "black"})
	
	/* EVENT CLOSE FORM ADD BOOK */
	$(".far.btn-close-from").click((e) => {
		$(".main__overlay").css("display", "none");
		frmAddUser.css("display", "none");
		$(".form-message.invalid").removeClass("invalid")
		clearnField();
		
	})
});



/* =================EVENT BUTTON DELETE USER============== */
const btnDeleteUser = $(".btn__delete--user");/* btn Add new book */
const diaDeleteUser = $(".from__confirm");/* Form add new book */
const inputDelete = $(".input-delete");

btnDeleteUser.click((e) => {
	$(".main__overlay").css("display", "block");
	let targetClass=$(e.target)
	if(targetClass.attr('class')=="far fa-trash-alt"){
		targetClass=targetClass.parent();
	}
	diaDeleteUser.find("#user-id").text(targetClass.attr("id"))
	inputDelete.val(targetClass.attr("id"))
	console.log(targetClass.attr("id"));
	diaDeleteUser.css("display", "block");
	
	/* EVENT CLOSE FORM */
	$(".btn-cancel").click((e) => {
		$(".main__overlay").css("display", "none");
		diaDeleteUser.css("display", "none");
	})
});

function clearnField(){
	$('.form-row #userId').val(0);
	$('#user-name').val("");
	$('#user-age').val("");
	$('#user-phone').val("");
	$('#user-email').val("");
	$('#user-pass').val("");
	$('#user-confirm').val("");
}

function addAndEditNewUser(title="NEW USER", color="#28A745")
{
	$(".main__overlay").css("display", "block");//làm mờ background
	//clearnField();
	frmAddUser.css("display", "block");		// hiển thị form
	
	frmAddUser.find(".card-header:first").children("span").text(title) //đổi tiêu đề form
	frmAddUser.find(".card-header:first").css({"background-color": color,"color": "white"})// đổi màu form
	
	/* EVENT CLOSE FORM ADD BOOK */
	$(".far.btn-close-from").click((e) => {
		$(".main__overlay").css("display", "none");
		frmAddUser.css("display", "none");
		$(".form-message.invalid").removeClass("invalid")
		clearnField();
		
	})
}



/* =================NHẬP HÀNG============== */
const showResult = function(str){
 if (str.length==0) {
    document.getElementById("livesearch").innerHTML="";
    document.getElementById("livesearch").style.border="0px";
    return;
  }
  if(str.length > 1){
  $.ajax({
  		type: "POST",
  		url: "http://localhost:8080/getBookValue",
  		data: {
  			strValue: str
  		},
  		success: function(value) {
  			 console.log(value);
  			 if(value.length > 0){
  			     document.getElementById("livesearch").innerHTML= value;
  			 }
  		}, error: () => {
  			console.log('Error');
  		}
  	})
  }
}

const AddItem = function(event){
    var currentItem = event.target;
    var objItem = {};
    objItem.Id = currentItem.getAttribute("id");
    objItem.Name = currentItem.innerText;
    objItem.Price = currentItem.getAttribute("price");
    objItem.Quantity = 1;
    objItem.TotalCost = objItem.Quantity * objItem.Price;
    AddToTable(objItem);
    showResult("")
    console.log(objItem);
}

var LstOrder = [];
const AddToTable = function(item){
    var myTable = document.getElementById("myTableOrder");

    var isExist = LstOrder.filter(x => x.Id == item.Id).length;
    if(isExist){
        LstOrder = LstOrder.map(function(order){
           if(order.Id == item.Id){
                order.Quantity++;
           }
           return order;
        });
    }else{
           LstOrder.push(item);
    }
    refreshTable();
}


const refreshTable = function(){

    var myTable = document.getElementById("myTableOrder");
    var TotalPrice = document.getElementById("TotalPrice");
    var TotalQuantity = document.getElementById("TotalQuantity");
      $("#myTableOrder").empty();
            var OrderIndex = LstOrder.length;
            LstOrder.forEach(Order => {
                   var row = myTable.insertRow(0);
                        var index = row.insertCell(0);
                        var id = row.insertCell(1);
                        var name = row.insertCell(2);
                        var price = row.insertCell(3);
                        var quantity = row.insertCell(4);
                        var totalcost = row.insertCell(5);
                        var edit = row.insertCell(6);

                        index.innerHTML = --OrderIndex + 1;
                        id.innerHTML = Order.Id;
                        name.innerHTML = Order.Name;

                        quantity.innerHTML = "<input type='number' value='"+Order.Quantity +"' onchange='quantityChange("+ Order.Id +",this)'/>"
                        price.innerHTML = "<input type='number' value='"+Order.Price +"' onchange='priceChange("+ Order.Id +",this)'/>"
                        totalcost.innerText = Order.TotalCost.toLocaleString('it-IT', {style : 'currency', currency : 'VND'});
                        edit.innerHTML = "<span style='border-bottom: 1px solid; color: blue' onclick='deleteItem("+Order.Id+")'>Xóa</span>"

            });

              TotalPrice.innerText = LstOrder.reduce((total, current) => {
                        return total + current.Quantity * current.Price;
                }, 0).toLocaleString('it-IT', {style : 'currency', currency : 'VND'});

                TotalQuantity.innerText = LstOrder.reduce((total, current) => {
                        return total + current.Quantity;
                }, 0);

}


const quantityChange = function(Id, _this){
    // cập nhật số lượng
    LstOrder.map(item => {
        if(item.Id == Id){
            item.Quantity = parseInt(_this.value) ;
            item.TotalCost = item.Quantity * item.Price;
        }
        return item;
    });
    refreshTable();

   // console.log(Order, Quantity);
}


const priceChange = function(Id, _this){
    // cập nhật cost
    LstOrder.map(item => {
        if(item.Id == Id){
            item.Price = parseInt(_this.value);
            item.TotalCost = item.Quantity * item.Price;

        }
        return item;
    });
    refreshTable();

   // console.log(Order, Quantity);
}

const AddProduct = function(){

    var ncc = document.getElementById("ncc"); // getElementById đề láy cái thẻ có id là ncc là thẻ select
    var id_ncc = parseInt(ncc.value); // lấy giá trị đc chọn
    console.log(ncc.value);
    if(id_ncc < 0){ // ktra có chọn hay chưa
        alert(MESSAGE_NOTIFY.VERIFY_SUPPLIER);
        return;
    }
    if(LstOrder.length > 0){

        // ly id ncc bỏ vào cái lstOrder của mình
        // chọn rồi thì bỏ vào list này . sau đó lên server lấy ra lưu
       LstOrder =  LstOrder.map(item => {
            item.id_ncc = id_ncc;
            return item;
        })

          $.ajax({

              		type: "POST",
              		url: "http://localhost:8080/savePurchasingOrder",
              		contentType: "application/json",
              		data:  JSON.stringify(LstOrder),
              		success: function(value) {
              		    if(value.length > 0){
              		        //alert("Lưu thành công");
              		        alert(MESSAGE_NOTIFY.SAVE_SUCCESS);
              		        location.reload();
              		    }else{
              		       alert(MESSAGE_NOTIFY.SAVE_FAILED);
              		    }
              			 console.log(value);

              		}, error: () => {
              			console.log('Error');


              		}
              	})
    }
    else{
        alert(MESSAGE_NOTIFY.VERIFY_PRODUCT);
    }

}
const deleteItem = function(Id){
    var isCheck = confirm(MESSAGE_NOTIFY.CONFIRM_PURCHASING);
     if(isCheck){
         LstOrder = LstOrder.filter(x => x.Id != Id);
         refreshTable();
    }
}
var LstOrder2 =[];
const viewDetail = function(_this){
    LstOrder2 = [];
    var id = _this.getAttribute("id");
    console.log(id);
      $.ajax({

                  		type: "POST",
                  		url: "http://localhost:8080/get-detail-purchasing",
                  		data:  {id : id},
                  		success: function(value) {
                            LstOrder2 = value;
                  			 console.log(value);
                            refreshTableV2();
                  		}, error: () => {
                  			console.log('Error');


                  		}
                  	})
}

const refreshTableV2 = function(){

    var myTable = document.getElementById("myTableOrder_View");
      $("#myTableOrder_View").empty();
            var OrderIndex = LstOrder2.length;
            LstOrder2.forEach(Order => {
                   var row = myTable.insertRow(0);
                        var index = row.insertCell(0);
                        var id = row.insertCell(1);
                        var name = row.insertCell(2);
                        var price = row.insertCell(3);
                        var quantity = row.insertCell(4);
                        var totalcost = row.insertCell(5);

                        index.innerHTML = --OrderIndex + 1;
                        id.innerHTML = Order.id_book;
                        name.innerHTML = Order.book_name;

                        quantity.innerHTML = Order.quantity
                        price.innerHTML = Order.price.toLocaleString('it-IT', {style : 'currency', currency : 'VND'});;
                        totalcost.innerText= (Order.quantity * Order.price).toLocaleString('it-IT', {style : 'currency', currency : 'VND'});;


            });



}
