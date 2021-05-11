// Get the modal
var nav_element = document.getElementById("nav-ele");

// Get the button that opens the modal
var btn = document.getElementById("nav-btn");


// When the user clicks the button, open the modal
btn.onclick = function () {
    if(nav_element.style.display == "none"){
        nav_element.style.display = "block";
    }else{nav_element.style.display = "none";}

}
// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    // if (!nav_element.contains(event.target)) {
    //     nav_element.style.display = 'none';
    // }
    if (event.target == nav_element) {
        nav_element.style.display = "none";
    }
}