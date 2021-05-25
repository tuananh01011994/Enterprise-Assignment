
$(function(){
    $("#header").load("header.html");
    $("#footer").load("footer.html");

});


const formatter = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 2
})
$.getJSON('http://localhost:8080/api/seller/productGetAll', function(data) {

    data.forEach((product)=>{
        var div = document.querySelector('#products');
        div.innerHTML =
            div.innerHTML +
            `
<div class="col-md-4 mb-3">
    <div class="card h-100" id="${product.id}">
              <div class="wrapper">
                <img class="card-img-top img-fluid" onclick="viewProduct(${product.id})" src="./product-photos/${product.id+"/"+product.photos}" alt="">
              </div>
              <div class="card-body" >
                   <h4 class="card-title">${product.productName}</h4>
                   <h6 class="card-subtitle mb-2 text-muted">Stock: ${product.quantity} </h6>
                    <p class="card-text">${product.productDescription}</p>
                    <div class="form-group glyphicon-option-horizontal d-flex flex-fill" >
                      <select id="q-${product.id}" name="q-${product.id}" class="mr-1 custom-select">
                           <option value="1">1</option>
                           <option value="2">2</option>
                           <option value="3">3</option>
                      </select>

                    </div>
                    <div class="buy d-flex justify-content-between align-items-center" id="${product.store.id}">
                        <div class="text-success"><h5 class="mt-4 price">${formatter.format(parseInt(product.productPrice))}</h5></div>
                        <a href="#0" onclick="reply_click(${product.id})"  class="btn btn-danger mt-3"><i class="fa fa-shopping-cart"></i>Add to Cart</a>
                    </div>
              </div>
    </div>
</div>
    `;
    });
});


function viewProduct(product_id){

    $.get("http://localhost:8080/username",function (data){

        let userAuthentication = JSON.parse(data);
        $.map(userAuthentication.authorities, function(elem, index) {

            if (elem.authority === "USER_PRIVILEGE" || elem.authority === "SHOP_PRIVILEGE"){
                localStorage.removeItem('product-detail');
                var qselector = "#" + product_id.toString() + " " + ".custom-select";
                var pnselector = "#" + product_id.toString() + " " + ".card-title";
                var pselector = "#" + product_id.toString() + " " + ".price";
                var imgselector = "#" + product_id.toString() + " " + ".card-img-top";
                var desselector = "#" + product_id.toString() + " " + ".card-text";
                var btnselector = "#" + product_id.toString() + " " + ".buy";
                var stockselector = "#" + product_id.toString() + " " + ".card-subtitle";
                console.log(btnselector);
                // save the value of #checkbox-1 as a variable,
                var productName = $(pnselector).text();
                var quantity = $(qselector).val();
                var price = $(pselector).text();
                var photo = $(imgselector).attr('src');
                var store = $(btnselector).attr('id');
                var description = $(desselector).text();
                var stock = $(stockselector).text();
                console.log(store);
                // and test it to confirm it worked.
                const jsonData = {
                    id: product_id.toString(),
                    productName: productName.toString(),
                    quantity: quantity.toString(),
                    price: price.toString(),
                    stock: stock.toString(),
                    photo: photo.toString(),
                    description: description.toString(),
                    store_id: store.toString()
                };
                localStorage.setItem('product-detail', JSON.stringify(jsonData));
                window.location.href = "product-detail";
            }
        });
    })
}

function reply_click(clicked_id)
{
    $.get("http://localhost:8080/username",function (data){

        let userAuthentication = JSON.parse(data);
        $.map(userAuthentication.authorities, function(elem, index) {

            if (elem.authority === "USER_PRIVILEGE"){
                $.get("http://localhost:8080/getCurrentUser", function (data){
                    var currentuser;
                    const object = JSON.parse(data);
                    currentuser = object.id;
                    // alert(currentuser);
                    var qselector = "#" + clicked_id.toString() + " " + ".custom-select";
                    var pnselector = "#" + clicked_id.toString() + " " + ".card-title";
                    var pselector = "#" + clicked_id.toString() + " " + ".price";
                    var imgselector = "#" + clicked_id.toString() + " " + ".card-img-top";
                    var btnselector = "#" + clicked_id.toString() + " " + ".buy";
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
                    if(!exist) {list.push(jsonData); alert("Successfully added!");}
                    else{
                        alert("Your cart is updated");
                    }

                    localStorage.setItem(currentuser, JSON.stringify(list));

                });
            }
        });
    })

}