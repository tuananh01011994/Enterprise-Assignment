$(function(){
    $("#header").load("header.html");
});

    const formatter = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 2
})
    $(".my-product-validation").submit(function() {
    var form = $(this);
    if (form[0].checkValidity() === false) {
    event.preventDefault();
    event.stopPropagation();
}
    form.addClass('was-validated');
});

    $.when($.get("http://localhost:8080/getCurrentUser").then(function (data){
    const object = JSON.parse(data);
    var userid = object.id;
    window.userid = object.id;
    $.get("http://localhost:8080/api/seller/storeGetByUser/" + userid, function (storeinfo){

    storeinfo.forEach((store)=>{window.storeid = store.id});
    $.getJSON("http://localhost:8080/api/seller/productGetByStore/" + window.storeid , function (data){
    // window.products = JSON.parse(data);
    var div = document.querySelector('#listcart');
    data.forEach((product)=> {
    div.innerHTML =
    div.innerHTML +
    `<tr id="${product.id}">
                        <td class="w-25"><img class="img-fluid img-thumbnail photo" src="./product-photos/${product.id+"/"+product.photos}" /> </td>
                        <td><input class="form-control productName" type="text" value="${product.productName}" /></td>
                        <td><input class="form-control productDescription" type="text" value="${product.productDescription}" /></td>
                        <td><input class="form-control quantity" type="number" value="${product.quantity}" /></td>
                        <td><input class="form-control productPrice" type="text" value="${formatter.format(product.productPrice)}" /></td>
                        <td class="text-right"><button class="btn btn-sm btn-primary" onclick="updateProduct(${product.id})"><i class="fa fa-upload"></i> </button> <button class="btn btn-sm btn-danger" onclick="reply_click(${product.id})"><i class="fa fa-trash"></i> </button> </td>
                    </tr>`;

});

});
});
}));
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

    $("#registerProduct").click( function (e){

    if(($("#name").val()!== "") && ($("#description").val()!== "") && ($("#price").val()!== "") && ($("#quantity").val()!== "")){


    var product = {
    "productName": $("#name").val(),
    "productDescription": $("#description").val(),
    "productPrice": $("#price").val(),
    "quantity": getq($("#quantity").val())
}
    $.ajax({
    url: "http://localhost:8080/api/seller/productAdd/" + window.storeid,
    contentType: 'application/json',
    type: "POST",
    data: JSON.stringify(product),
    success: function(mess){
    $("#registerProduct").attr('disabled','disabled');
    $("#close2").attr('disabled','disabled');
    $("#close1").attr('disabled','disabled');
    window.mess = mess;

},
    error: function(jqXHR, textStatus, errorThrown) {
    alert(JSON.stringify(jqXHR));
}
});
    e.preventDefault();
}

});
    $('#image-upload-form').on('submit',(function(e) {
    e.preventDefault();
    var res = Object.values(window.mess);
    var formData = new FormData(this);

    $.ajax({
    type:'PUT',
    url: "http://localhost:8080/api/seller/productUpdate/" + res[1] +"/photo",
    data:formData,
    cache:false,
    contentType: false,
    processData: false,
    success: function(e){
    alert(JSON.stringify(e));
    window.location.href = "seller-product";
},
    error: function(jqXHR, textStatus, errorThrown) {
    alert(JSON.stringify(jqXHR));
}
});
}));
    $("#image").on("change", function() {

});
    $("#upPhoto").click(function (e){
    // console.log(window.mess);
    // // window.mess = JSON.stringify(window.mess);
    // var res = Object.values(window.mess);
    // console.log(res[1]);
    // $.ajax({
    //   url: "http://localhost:8080/api/seller/productUpdate/" + res[1] +"/photo?image",
    //   type: "PUT",
    //   success: function(e){
    //     alert(JSON.stringify(e));
    //   },
    //   error: function(jqXHR, textStatus, errorThrown) {
    //     alert(JSON.stringify(jqXHR));
    //   }
    // });

    $("#image-upload-form").submit();
    // e.preventDefault();
});

    function updateProduct(product_id)
    {

        var update = confirm("Do you want to UPDATE this product?");
        if(update){
        var productSel = "#"+product_id.toString();
        var productData= {
        productName: $(productSel + " "+ ".productName").val(),
        productDescription:  $(productSel + " "+ ".productDescription").val(),
        productPrice: getnum($(productSel + " "+ ".productPrice").val()).toString(),
        quantity: parseInt($(productSel + " "+ ".quantity").val())
    }
        alert( JSON.stringify(productData));
        $.ajax({
        url: "http://localhost:8080/api/seller/productUpdate/" + product_id.toString(),
        contentType: 'application/json',
        type: "PUT",
        data: JSON.stringify(productData),
        success: function(success){
        alert(JSON.stringify(success));
        location.reload();

    },
        error: function(jqXHR, textStatus, errorThrown) {
        alert(JSON.stringify(jqXHR));
    }
    });
    }

    }


    function reply_click(clicked_id)
    {
        var del = confirm("Do you want to REMOVE this product?");
        if(del){
        $.ajax({
        url: "http://localhost:8080/api/seller/productDelete/" + clicked_id.toString(),
        contentType: 'application/json',
        type: "DELETE",
        success: function(success){
        alert(JSON.stringify(success));
        location.reload();

    },
        error: function(jqXHR, textStatus, errorThrown) {
        alert(JSON.stringify(jqXHR));
    }
    });
    }

    }
