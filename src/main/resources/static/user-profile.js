$(function(){
    $("#header").load("header.html");
});



    $(function() {


    $("#changeNameForm").on("submit", function (e){
        e.preventDefault();
        var firstname = $("#inputFirstName").val();
        var lastname = $("#inputLastName").val();
        $.when($.get("http://localhost:8080/getID").then (function(data){
            $.ajax({
                type:"PUT",
                url: "http://localhost:8080/api/usernameUpdate/" + data + "?firstname=" + firstname + "&lastname=" + lastname,
                success: function (response){
                    location.reload()
                    alert(JSON.stringify(response));
                },
                error: function(xhr) {
                    alert(JSON.stringify(xhr));

                }
            })
        }))
    })
});

    $(function(){
    $("#changeUsernameForm").on("submit", function (e){
        e.preventDefault();
        var username = $("#inputUsername").val();
        $.when($.get("http://localhost:8080/getID").then (function(data){
            $.ajax({
                type:"PUT",
                url: "http://localhost:8080/api/userUsernameUpdate/" + data + "?username=" + username,
                success: function (response){
                    location.reload()
                    alert(JSON.stringify(response));
                },
                error: function(xhr) {
                    alert(JSON.stringify(xhr));

                }
            })
        }))
    })
})

    $(function(){
    $("#changePasswordForm").on("submit", function (e){
        e.preventDefault();
        var password = $("#inputPassword").val();
        $.when($.get("http://localhost:8080/getID").then (function(data){
            $.ajax({
                type:"PUT",
                url: "http://localhost:8080/api/changepassword/" + data + "?new_password=" + password,
                success: function (response){
                    location.reload()
                    alert(JSON.stringify(response));
                },
                error: function(xhr) {
                    alert(JSON.stringify(xhr));

                }
            })
        }))
    })
})

    function passwordToggler(){
    var x = document.getElementById("inputPassword");
    if (x.type === "password") {
    x.type = "text";
} else {
    x.type = "password";
    x.type = "password";
}
}

    $(function(){
    $("#changeShopForm").on("submit", function (e){
        e.preventDefault();
        var shopName = $("#inputShopName").val();
        $.when($.get("http://localhost:8080/getID").then (function(data){
            $.when($.get("http://localhost:8080/api/seller/storeGetByUser/" + data).then (function(listStore) {
                $.ajax({
                    type: "PUT",
                    url: " http://localhost:8080/api/seller/storeUpdate/" + listStore[0].id,
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify({"storeName": shopName}),
                    success: function (response) {
                        location.reload()
                        alert(JSON.stringify(response))
                    },
                    error: function (res) {
                        alert(JSON.stringify(res))
                    }
                })
            }))
        }))
    })
})

    $(function(){
    $("#createShopForm").on("submit", function (e){
        e.preventDefault();
        var shopName = $("#inputShopNameCreate").val();
        $.when($.get("http://localhost:8080/getID").then (function(data){
            $.ajax({
                type: "POST",
                url: " http://localhost:8080/api/seller/storeAdd/" + data + "?store_name=" + shopName,
                contentType: "application/json; charset=utf-8",
                success: function (response) {
                    location.reload();
                    alert(JSON.stringify(response))
                },
                error: function (res) {
                    alert(JSON.stringify(res))
                }
            })
        }))
    })
})



    $(function (){
    $.when($.get("http://localhost:8080/getID").then (function(data) {
        $.get("http://localhost:8080/api/" + data + "/checkPhoto",function (res){
            if (res.message === "true"){
                $("#avatarImg").attr("src",  ".././user-photos/" + data + "/avatar.jpg");
                $("#avatarImg").attr("src",  ".././user-photos/" + data + "/avatar.png");
            } else {
                $("#avatarImg").attr("src",  "../static/default-avatar.png");
            }
        })
    }))
})

    $(function(){
    $("#changeAvatarForm").on("submit", function (e){
        e.preventDefault();
        var formData = new FormData();
        var filename = $("#inputAvatar").val();
        var aux = filename.split('.');
        var extension = aux[aux.length -1].toUpperCase();
        console.log(extension);


        formData.append('image', $('input[type=file]')[0].files[0]);

        $.when($.get("http://localhost:8080/getID").then (function(data){
            $.ajax({
                type:"PUT",
                url: "http://localhost:8080/api/user/" + data + "/photo",
                data: formData,
                contentType: false,
                processData: false,

                success: function (response){

                    $.when($.get("http://localhost:8080/getID").then (function(data) {
                        if (extension === "PNG"){
                            $("#avatarImg").attr("src",  ".././user-photos/" + data + "/avatar.png");
                        } else if (extension === "JPEG" || extension === "JPG"){
                            $("#avatarImg").attr("src",  ".././user-photos/" + data + "/avatar.jpg");

                        }

                    }));

                    alert(JSON.stringify(response));
                },
                error: function(xhr) {
                    alert(JSON.stringify(xhr));

                }
            })
        }))
    })
})


    $(function(){
    $("#deleteShopForm").on("submit", function (e){
        e.preventDefault();
        $.when($.get("http://localhost:8080/getID").then (function(data){
            $.when($.get("http://localhost:8080/api/seller/storeGetByUser/" + data).then (function(listStore) {
                $.ajax({
                    type: "DELETE",
                    url: " http://localhost:8080/api/seller/storeDelete/" + listStore[0].id,
                    success: function (response) {
                        location.reload();
                        alert(JSON.stringify(response))
                    },
                    error: function (res) {
                        alert(JSON.stringify(res))
                    }
                })
            }))
        }))
    })
})

    $(function (){
    $.when($.get("http://localhost:8080/getID").then (function(data){
        $.when($.get("http://localhost:8080/api/seller/storeGetByUser/" + data).then (function(listStore){
            if (listStore.length < 1){
                $(".store-exist").hide();
            }
            else {
                $(".store-exist-not").hide();
            }
        }));
    }));
});



    $(function(){
    $.get("http://localhost:8080/username",function(data){
        let userAuthentication = JSON.parse(data);
        $.map(userAuthentication.authorities, function(elem, index) {

            if (elem.authority == "SHOP_PRIVILEGE"){
                $(".list-group").append("<li class=\"list-group-item\">My shop</li>")
                $("#userAction").append("<a href=\"seller-orders\" class=\"card-link\">See my order</a>")

            } else if (elem.authority == "USER_PRIVILEGE"){
                $(".list-group").append( "<li class=\"list-group-item\">My cart</li>");
                $("#userAction").append("<a href=\"cart\" class=\"card-link\">See my cart</a>")
                $(".shop-name-block").hide();


            }
        });
    });
});


    $(function(){
    $.get("http://localhost:8080/getCurrentUser",function(data){
        let obj = JSON.parse(data);
        $("#nameField").prepend(obj.firstName +" " + obj.lastName + " ");
        let password = obj.password;
        let hidePassword = password.substring(1,password.length);
        let mask="";
        for (let char in hidePassword) {
            mask  += '*';
        }
        password =  password.charAt(0) + mask;
        $("#passwordField").prepend(password + " ");

        $("#usernameField").prepend(obj.username + " ");
    });
    $.when($.get("http://localhost:8080/getID").then (function(data){
    $.when($.get("http://localhost:8080/api/seller/storeGetByUser/" + data).then (function(listStore){
    $("#shopField").prepend(listStore[0].storeName + " ");
}));
}));
});

