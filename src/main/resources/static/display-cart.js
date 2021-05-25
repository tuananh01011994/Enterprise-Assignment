
$(function(){
    $("#header").load("header.html");
});
var time = new Date();
var year = time.toDateString();
function getnum(c){
    var t= Number(c.replace(/[^0-9.-]+/g,""));

    if(isNumeric(t)){
        return parseInt(t,10);
    }
    return 0;
    function isNumeric(n) {
        return !isNaN(parseFloat(n)) && isFinite(n);
    }
}
function getq(c){
    if(isNumeric(c)){
        return parseInt(c,10);
    }
    return 0;
    function isNumeric(n) {
        return !isNaN(parseFloat(n)) && isFinite(n);
    }
}

$.when($.get("http://localhost:8080/getCurrentUser").then(function (data){
    var userid;
    const object = JSON.parse(data);
    userid = object.id;
    window.userinfo = object.id;
    console.log(userid);

    $("#back").click(function(e){
        window.href.location = "home";
    });

    $("#addressForm").on("submit", function (e){
        e.preventDefault();
    });

    function checkRowCount(){
        let rowCount = $('table#cart tr:last').index() + 1 - 3;;
        if (rowCount <=0){
            $("#submit").attr("disabled", true);
        }
    };

    $(document).ready(function (){

        checkRowCount();
        $(".btn-danger").click(function(){
            $(this).parents('tr').remove();
            checkRowCount();
            // location.reload();
        })
    })

    const formatter = new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD',
        minimumFractionDigits: 2
    })
    var obj = JSON.parse(localStorage.getItem(userid));
    window.obj = obj;
    console.log(userid);
    loadcart(obj);
    function loadcart(data) {
        var div = document.querySelector('#listcart');
        console.log(userid);
        data.forEach((product)=> {

            div.innerHTML =
                div.innerHTML +
                `<tr id="${product.id}">
                        <td class="w-25"><img class="img-fluid img-thumbnail photo" src="${product.photo}" /> </td>
                        <td class="productName">${product.productName}</td>
                        <td>In stock</td>
                        <td><input class="form-control quantity" type="text" value="${product.quantity}" /></td>
                        <td class="text-right price">${formatter.format(getnum(product.price) * getq(product.quantity))}</td>
                        <td class="text-right"><button class="btn btn-sm btn-danger" onclick="reply_click(${product.id})"><i class="fa fa-trash"></i> </button> </td>
                    </tr>`;
        });
        div.innerHTML = div.innerHTML +
            `<tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>Sub-Total</td>
                        <td class="text-right"  id="sub_total"></td>
                        <td ></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>Shipping</td>
                        <td class="text-right" id="shipcost"></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td><strong>Total</strong></td>
                        <td class="text-right" id="total"><strong></strong></td>
                        <td> </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td class="text-right "><button class="btn update btn-lg btn-primary"><i class="fa fa-upload"></i> </button> </td>
                    </tr>
`;
    };




    async function computeSub_total(){
        var sub_total = 0;
        await $("#cart tr").each(function() {
            sub_total +=  getnum($(this).find(".price").text());


        });
        $("#sub_total").text(formatter.format(sub_total));
    }
    computeSub_total().then(function (){
        if(getnum($('#sub_total').text()) > 200 || getnum($('#sub_total').text()) === 0){
            $("#shipcost").text(formatter.format(0));
        }else $("#shipcost").text(formatter.format(5));

        var total = getnum($('#shipcost').text()) + getnum($('#sub_total').text());
        $("#total").text(formatter.format(total));





    })
    $(".update").click(function (){
        $("#cart tr").each(function() {
            var productid = $(this).closest('tr').attr('id');
            var currentquantity = getq($(this).find(".quantity").val());
            for(var i = 0; i < obj.length; i++){
                if(obj[i].id === productid) {
                    obj[i].quantity = currentquantity.toString();
                    break;
                }
            }
        });
        localStorage.setItem(userid, JSON.stringify(obj));
        location.reload();
    });
    $("#checkout").click(function (){
        window.order = [];
        if($("#addressInput").val()!== ""){
            for(var i = 0; i < window.obj.length; i++){

                var product = {};
                product={"user": {"id":getq( window.userinfo)}, "product": {"id":getq( window.obj[i].id)}, "quantity": window.obj[i].quantity, "storeId": getq(window.obj[i].store_id), "address":$("#addressInput").val(), "time":year};

                window.order.push(product);


            };
            console.log(JSON.stringify(window.order));
            $.ajax({
                url: "http://localhost:8080/api/basketCheckout",
                contentType: 'application/json ; charset = utf-8',
                type: "POST",
                data: JSON.stringify(window.order),
                success: function(e){
                    alert(JSON.stringify(e));

                    localStorage.removeItem(window.userinfo);
                    window.location.href = "profile";

                },
                error: function(jqXHR, textStatus, errorThrown) {
                    alert(JSON.stringify(jqXHR));
                }
            });
        }
    });

}));



function redirectToList(){
    window.location.href = "product-list";
}
function reply_click(clicked_id)
{
    // Parse json
    let notes = JSON.parse(localStorage.getItem(window.userinfo));
    // Get index of object
    const index = notes.findIndex(function(item){
        return item.id === clicked_id.toString();
    });
    // Splice the array at the index of your object
    notes.splice(index, 1);
    // Save back to localStorage

    localStorage.setItem(window.userinfo, JSON.stringify(notes));
    location.reload();
}