function Validator(option){
    var select=document.querySelector(option.form)
    //Hàm thực hiện validate: báo lỗi nhập
    function validate(inputEle,ruleEle, check={}){
        var errorEle= inputEle.parentElement.querySelector('.form-message');           
        var errorMess=ruleEle.test(inputEle.value);
        if(errorMess){
            errorEle.innerText=errorMess
            errorEle.classList.add('invalid') 
            check.isValid=false;
        }else {
            //errorEle.innerText='';
            errorEle.classList.remove('invalid')
        }
    }
    if(select){
        option.rules.forEach(function(ruleEle){
            var inputEle=select.querySelector(ruleEle.selector);
            

            if(inputEle){
                
                inputEle.onblur=function(){
                    //console.log(ruleEle.test(inputEle.value))
                    validate(inputEle,ruleEle);
                }

                inputEle.oninput=function(){
                    var errorEle= inputEle.parentElement.querySelector('.form-message');           
                    //errorEle.innerText='';
                    errorEle.classList.remove('invalid')
                }
            }
        })
        select.onsubmit=function(){
			let checkValid={isValid:true};
			option.rules.forEach(function(ruleEle){
	            var inputEle=select.querySelector(ruleEle.selector);
	            if(inputEle){
	                validate(inputEle,ruleEle,checkValid);
	            }
	        })
	        return checkValid.isValid;
		}
    }
}
Validator.isRequired=function(selector,mess='Vui lòng nhập trường này!!'){
    return{
        selector: selector,
        test: function(value) {
            return value? undefined:mess;
        }
    }
}
Validator.isEmail=function(selector){
    return {
        selector: selector,
        test: function(value){
            var regex=/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/
            return (value=="" || regex.test(value))?undefined:'Email không hợp lệ'
        }
    }
}
Validator.requiredLeng=function(selector, num){
    return {
        selector: selector,
        test: function(value){
            return (value.length==num)? undefined:('Nhập đủ '+num+' số')
        }
    }
}

Validator.confirmMes=function(selector,selector2, message){
    return{
        selector:selector,
        test: function(value){
            var selectPas=document.querySelector(selector)
            var selectPasfirm=document.querySelector(selector2)
            return (selectPas.value==selectPasfirm.value)?undefined : message
        }
    }
}
Validator.isNum=function(selector,num){
    return {
        selector: selector,
        test: function(value){
            var regex=/^[0-9]\d*$/
            return ((!num || value.length==num) && regex.test(value))?undefined:'Vui lòng nhập đúng '+num+' số'
        }
    }
}

Validator.checkAge=function(selector,num){
    return {
        selector: selector,
        test: function(value){
            return (value>=num)?undefined:'Tuổi không hợp lệ, cần phải >= '+num
        }
    }
}