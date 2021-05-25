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
    async function load(){
    await   $.when($.get("http://localhost:8080/getCurrentUser").then(function (data){

        const object = JSON.parse(data);

        window.userinfo = object.id;

        $("#addressForm").on("submit", function (e){
            e.preventDefault();
        });

        var obj = JSON.parse(localStorage.getItem('product-detail'));
        window.obj = obj;
        // alert(JSON.stringify(obj));
        loadcart(obj);
        function loadcart(product) {
            var div = document.querySelector('#product');
            // data.forEach((product)=> {

            div.innerHTML =
                div.innerHTML +
                `<div class="card" id="${product.id}">
        <div class="row">
            <aside class="col-sm-5 border-right">
                <article class="gallery-wrap">
                    <div class="img-big-wrap">
                        <div> <a href="#"><img id="photo" src="${product.photo}"></a></div>
                    </div> <!-- slider-product.// -->
                    <div class="img-small-wrap">
                        <div class="item-gallery"> <img src="${product.photo}"> </div>
                        <div class="item-gallery"> <img src="${product.photo}"> </div>
                        <div class="item-gallery"> <img src="${product.photo}"> </div>
                        <div class="item-gallery"> <img src="${product.photo}"> </div>
                    </div> <!-- slider-nav.// -->
                </article> <!-- gallery-wrap .end// -->
            </aside>
            <aside class="col-sm-7">
                <article class="card-body p-5">
                    <h3 class="title mb-3" id="productName">${product.productName}</h3>

                    <p class="price-detail-wrap">
	<span class="price h3 text-warning">
		<span class="currency" id="price">${product.price}</span>
	</span>

                    </p> <!-- price-detail-wrap .// -->
                    <dl class="item-property">
                        <dt>Description</dt>
                        <dd><p id="productDescription">${product.description}</p></dd>
                    </dl>


                    <dl class="param param-feature">
                        <dt>Stock:</dt>
                        <dd>${product.stock}</dd>
                    </dl>  <!-- item-property-hor .// -->

                    <hr>
                    <div class="row">
                        <div class="col-sm-5">
                            <dl class="param param-inline">
                                <dt>Quantity: </dt>
                                <dd>
                                    <select id="quantity" class="form-control form-control-sm" style="width:70px;">
                                        <option> 1 </option>
                                        <option> 2 </option>
                                        <option> 3 </option>
                                    </select>
                                </dd>
                            </dl>  <!-- item-property .// -->
                        </div> <!-- col.// -->
                    </div> <!-- row.// -->
                    <hr>
                    <button data-toggle="modal" data-target="#addressPopUp" class="buynow btn btn-lg btn-primary text-uppercase"> Buy now </button>
                    <div class="modal fade" id="addressPopUp" tabindex="-1" role="dialog" aria-labelledby="addressPopUp" aria-hidden="true">
                        <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Set your delivery address:</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form id="addressForm" >
                                    <div class="form-group row">
                                        <label for="addressInput" class="col-md-4 col-form-label">Delivery address:</label>
                                        <div class="col-md-7">
                                            <input type="text" class="form-control" id="addressInput" placeholder="Enter your desired delivery address" required/>
                                        </div>
                                    </div>

                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                        <button type="submit" id="checkout" class="btn btn-primary">Save changes</button>
                                    </div>
                                </form>



                            </div>

                        </div>
                    </div>
                    </div>
                    <button onclick="addToCart(${product.id})" id="${product.store_id}" class="addproduct btn btn-lg btn-outline-primary text-uppercase"> <i class="fas fa-shopping-cart"></i> Add to cart </button>
                </article> <!-- card-body.// -->
            </aside> <!-- col.// -->
        </div> <!-- row.// -->
    </div>`;
            // });
        }


    }));
}



    load().then(function (){


        $("#checkout").click(function (e){

                        e.preventDefault();
                        const buyNow = [];
                        // if($("#addressInput").val()!== ""){
                        // for(var i = 0; i < window.obj.length; i++){


                        const product = {
                            "user": {"id": getq(window.userinfo)},
                            "product": {"id": getq(window.obj.id)},
                            "quantity": getq($("#quantity").val()),
                            "storeId": getq(window.obj.store_id),
                            "address": $("#addressInput").val(),
                            "time": year
                        };
                        // alert(window.obj[i].store_id);
                        buyNow.push(product);


                        $.ajax({
                            url: "http://localhost:8080/api/basketCheckout",
                            contentType: 'application/json ; charset = utf-8',
                            type: "POST",
                            data: JSON.stringify(buyNow),
                            success: function(e){
                                alert(JSON.stringify(e));

                                localStorage.removeItem(window.userinfo);
                                window.location.href = "profile";

                            },
                            error: function(jqXHR, textStatus, errorThrown) {
                                alert(JSON.stringify(jqXHR));
                            }
                        });

                        // }
                    });

    })


    function addToCart(clicked_id)
    {
        if (window.elem.authority === "USER_PRIVILEGE"){
            var currentuser = window.userinfo;
            var qselector = "#" + clicked_id.toString() + " " + "#quantity";
            var pnselector = "#" + clicked_id.toString() + " " + "#productName";
            var pselector = "#" + clicked_id.toString() + " " + "#price";
            var imgselector = "#" + clicked_id.toString() + " " + "#photo";
            var btnselector = "#" + clicked_id.toString() + " " + ".addproduct";
            console.log(btnselector);
            // save the value of #checkbox-1 as a variable,
            var productName = $(pnselector).text();
            var quantity = $(qselector).val();
            var price = $(pselector).text();
            var photo = $(imgselector).attr('src');
            var store = $(btnselector).attr('id');
            console.log(store);
            // and test it to confirm it worked.
            const jsonData = {
                id: clicked_id.toString(),
                productName: productName.toString(),
                quantity: quantity.toString(),
                price: price.toString(),
                photo: photo.toString(),
                store_id: store.toString()
            };
            if(!localStorage.getItem(currentuser)){
                localStorage.setItem(currentuser, "[]");
            }
            var list = JSON.parse(localStorage.getItem(currentuser));
            var exist = false;
            for(var i = 0; i < list.length; i++){
                if(list[i].id === jsonData.id) {
                    var newquantity = parseInt(jsonData.quantity) + parseInt(list[i].quantity);
                    list[i].quantity = newquantity.toString();
                    exist = true;
                    break;
                }
                else{


                }
            }

            if(!exist) {list.push(jsonData); alert("Successfully Add to Cart");}
            else{
                alert("Your Cart is Updated");
            }

            localStorage.setItem(currentuser, JSON.stringify(list));


        }


    }
