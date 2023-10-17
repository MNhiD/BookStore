/**
 * 
 */
// SCRIPT HANDLE NAVIGATION
const listNav = document.querySelectorAll(".nav-style > .nav-item");
listNav.forEach((nav, index) => {
	nav.onclick = () => {
		$(".nav-link.active").classList.remove("active")
		nav.querySelector(".nav-link").classList.add("active");
	}
});