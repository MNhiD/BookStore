function toast({title='',mess='',type='info',duration=3000}){
    const main=document.getElementById('toast');
    if(main){
        const toast=document.createElement('div');

        const auto=setTimeout(function(){
            main.removeChild(toast);
        },duration+1000)

        toast.onclick=function(e){
            if(e.target.closest('.toast__close')){
                toast.remove();
                clearTimeout(auto);
            }
            // if(event.target==)
        }
        const icons={
            success:'fas fa-check-circle',
            info:'fas fa-info-circle',
            warning:'fas fa-exclamation-circle',
            error:'fas fa-exclamation-triangle',
        }

        toast.classList.add('toast',`toast--${type}`);
        toast.style.animation= `DisplayBox ease .3s, HideBox linear 1s ${(duration/1000).toFixed(2)}s forwards`;
        toast.innerHTML=`
            <div class="toast__icon">
                <i class="${icons[type]}"></i>
            </div>
            <div class="toast__body">
                <h3 class="toast__title">${title}</h3>
                <p class="toast__mess">${mess}</p>
            </div>
            <div class="toast__close">
                <i class="fas fa-times"></i>
            </div>`;
        main.appendChild(toast)

        
    }
}


function showSuccess(message){
    toast({
    title: "Success",
    mess: message,
    type:"success",
    duration: 2000
    });
}
function showError(message){
    toast({
    title: "Error",
    mess: message,
    type:"error",
    duration: 2000
    });
}

function checkPopup(successMes,erorrMes){
	if(successMes){
		showSuccess(successMes);
	}
	if(erorrMes){
		showError(erorrMes)
	}
}