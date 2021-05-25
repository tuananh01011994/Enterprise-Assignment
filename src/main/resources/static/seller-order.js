$(function(){
    $("#header").load("header.html");
});

$(function (){
    $.when($.get("http://localhost:8080/getID").then (function(data){
        $.when($.get("http://localhost:8080/api/seller/storeGetByUser/" + data).then (function(listStore){
            $.when($.get("http://localhost:8080/api/orderGetByStore/" + listStore[0].id). then(function(listOrder){
                var div = document.querySelector('#listOrder');
                listOrder.forEach((order)=> {
                    div.innerHTML =
                        div.innerHTML +
                        `<tr id="${listOrder.id}">
                        <td class=" text-right userMail">${order.user.email}</td>
                        <td class=" text-right productName">${order.product.productName}</td>
                        <td class=" text-right productName">${order.product.id}</td>
                        <td class="text-right  quantity">${order.quantity}</td>
                        <td class=" text-right address" >${order.address}</td>
                        <td class=" text-right time">${order.time}</td>
                    </tr>`;

                });
            }))
        }));
    }));
});