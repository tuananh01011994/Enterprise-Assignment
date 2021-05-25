$(function(){
    $("#header").load("header.html");
});


$(function () {
    $('[data-toggle="tooltip"]').tooltip()
})
$(document).ready(function() {
    load();
});
// When the user scrolls the page, execute myFunction
window.onscroll = function() {myFunction()};

// Get the header
var header = document.getElementById("myHeader");



// Add the sticky class to the header when you reach its scroll position. Remove "sticky" when you leave the scroll position
function myFunction() {
    if (window.pageYOffset > sticky) {
        header.classList.add("sticky");
    } else {
        header.classList.remove("sticky");
    }
}

function load(){
    $.get("http://localhost:8080/username",function (data){
        const object = JSON.parse(data);
        $(".username").html(object.username);
        $(".username").html(object.name);
        if(object.authenticated !== false){
            $("#log-field").html("<a class=\"nav-link\" onclick='logout()' href=\"home\">Logout</a>");
        }
        else{
            $("#log-field").html("<a class=\"nav-link\" href=\"login\">Login</a>");
        }

    })
}

function getUserId(){
    $.get("http://localhost:8080/getID",function (data) {
        alert(data)
    })
}

function logout(){
    $.post("http://localhost:8080/logout");
}

$(function (){
    $('#people1').hover(function (e){
        $(this).attr('src', '../static/people1.jpg');
    })

    $('#people1').mouseleave(function (e){
        $(this).attr('src', '../static/mask.png');
    })


    $('#people2').hover(function (e){
        $(this).attr('src', '../static/people2.jpg');
    })

    $('#people2').mouseleave(function (e){
        $(this).attr('src', '../static/mask2.png');
    })

    $('#people3').hover(function (e){
        $(this).attr('src', '../static/people3.jpg');
    })

    $('#people3').mouseleave(function (e){
        $(this).attr('src', '../static/mask3.png');
    })

    $('#people4').hover(function (e){
        $(this).attr('src', '../static/people4.jpg');
    })

    $('#people4').mouseleave(function (e){
        $(this).attr('src', '../static/mask4.png');
    })

})