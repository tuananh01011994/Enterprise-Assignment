$(function(){
    $("#header").load("header.html");
    $("#footer").load("footer.html");
});

// Get the modal
var modal = document.getElementById("myModal")

// Get the button that opens the modal
var btn = document.getElementById("agreement");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal
btn.onclick = function () {
    modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
}


$(".my-login-validation").submit(function() {
    var form = $(this);
    if (form[0].checkValidity() === false) {
        event.preventDefault();
        event.stopPropagation();
    }
    form.addClass('was-validated');
});




'use strict';


$(function() {
    function urlregister(){

        switch ($("#role").val()){
            case 'user':
                return "http://localhost:8080/api/regular/user";
                break;
            case 'seller':
                return "http://localhost:8080/api/seller/user";
                break;
            default: break;
        }
    }
    $.fn.serializeObject = function() {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function() {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };
    $("#btnSubmit").click(function (e) {
        if(!($("#email").val()==="") && !($("#password").val()==="") && !($("#firstName").val()==="") && !($("#lastName").val()==="")&& !($("#username").val()==="") && !($("#role").val()==="") && ($('#agree').is(":checked"))) {
            e.preventDefault();

            var formData = $("#registerForm").serializeObject();
            alert(formData);
            var myjson = JSON.stringify(formData);
            console.log(myjson)
            $.ajax({
                url: urlregister(),
                contentType: 'application/json ; charset = utf-8',
                type: "POST",
                data: myjson,
                success: function(e){
                    alert(JSON.stringify(e));
                    window.location.href = "home";
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    alert(JSON.stringify(jqXHR));
                }
            });
        }



    });

    $("input[type='password'][data-eye]").each(function(i) {
        var $this = $(this),
            id = 'eye-password-' + i,
            el = $('#' + id);

        $this.wrap($("<div/>", {
            style: 'position:relative',
            id: id
        }));

        $this.css({
            paddingRight: 60
        });
        $this.after($("<div/>", {
            html: 'Show',
            class: 'btn-primary btn-sm',
            id: 'passeye-toggle-'+i,
        }).css({
            position: 'absolute',
            right: 10,
            top: ($this.outerHeight() / 2) - 12,
            padding: '2px 7px',
            fontSize: 12,
            cursor: 'pointer',
        }));

        $this.after($("<input/>", {
            type: 'hidden',
            id: 'passeye-' + i
        }));

        var invalid_feedback = $this.parent().parent().find('.invalid-feedback');

        if(invalid_feedback.length) {
            $this.after(invalid_feedback.clone());
        }

        $this.on("keyup paste", function() {
            $("#passeye-"+i).val($(this).val());
        });
        $("#passeye-toggle-"+i).on("click", function() {
            if($this.hasClass("show")) {
                $this.attr('type', 'password');
                $this.removeClass("show");
                $(this).removeClass("btn-outline-primary");
            }else{
                $this.attr('type', 'text');
                $this.val($("#passeye-"+i).val());
                $this.addClass("show");
                $(this).addClass("btn-outline-primary");
            }
        });
    });
});