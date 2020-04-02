$(document).ready(function () {
    updateBasketItemsCount();
    fillCurrentUsername();
})

$(document)
    .on('click', 'form button[type=submit]', function (e) {
        switch (e.target.id) {
            case "saveCategory":
                validateCategoryForm(e);
                break;
            case "saveProduct":
                validateProductForm(e);
                break;
        }
    });

function validateCategoryForm(e) {
    var categoryNameText = $.trim($("#categoryName").val());
    var isValid = categoryNameText.length > 0 && categoryNameText.length < 257;
    if (!isValid) {
        e.preventDefault();
        $("#nameEmptyError").text("Please, enter valid name: not empty and no more than 256 chars!");
        $("#categoryName").css("border-color", "red");
    }
}

function validateProductForm(e) {
    var formIsValid = true;
    $(".product-field").each(function (i, field) {
        var fieldValue = field.children[0].value;
        if (!fieldValue) {
            fillErrorMessage(field);
            formIsValid = false;
        }
    });
    if (!formIsValid) {
        e.preventDefault();
    }
}

function fillErrorMessage(field) {
    var classList = field.parentElement.children[2].classList;
    if (classList.contains("error-hidden")) {
        field.parentElement.children[2].classList.remove("error-hidden");
        field.children[0].classList.add("error-border");
    }
}

function clearErrorMsg() {
    $("#nameEmptyError").text("");
    $("#categoryName").css("border-color", "");
}

function clearProductErrorMsg(item) {
    if (item.classList.contains("error-border")) {
        item.classList.remove("error-border");
    }
    item.parentElement.parentElement.children[2].classList.add("error-hidden");
}

function filterProducts(responseDisplayMode) {
    var categoryId = $("#categoryFilter option:selected").val();
    if (categoryId) {
        sendResponceForFilter('/filterByCategory/' + categoryId, responseDisplayMode);
    } else {
        sendResponceForFilter('/getAllProducts', responseDisplayMode);
    }
}

function sendResponceForFilter(url, responseDisplayMode) {
    $.ajax({
        type: "GET",
        url: url,
        success: function (response) {
            displayProducts(responseDisplayMode, response);
        }
    });
}

function displayProducts(displayMode, response) {
    switch (displayMode) {
        case "MANAGING_PRODUCTS":
            managingProductsDisplay(response);
            break;
        case "CATALOGUE":
            displayProductsForCatalogue(response);
            break;
    }
}

function managingProductsDisplay(response) {
    $("#productsTable tr").remove();
    response.forEach(function (value) {
        $("#productsTblBody").append(buildProductRow(value));
    });
}

function displayProductsForCatalogue(response) {
    $('#catalogueGrid').empty();
    response.forEach(function (value) {
        $('#catalogueGrid').append(buildCatalogueCard(value));
    });
    if (response.length == 0) {
        $('#catalogueGrid').append(buildEmptyResultCard());
    }
}

function buildProductRow(product) {
    return '<tr><th scope="row" class="text-center" width="15%"><span>' + product.name + '<span/></th>\n' +
        '            <td class="text-center" width="20%"><img class="product-img" src=' + product.photoPath +
        '               alt="No photo ¯\_(ツ)_/¯" /></td>\n' +
        '            <td class="text-center" width="10%"><span>' + product.price.toLocaleString("en", {minimumFractionDigits: 2}) +
        '               <span/></td>\n' +
        '            <td width="30%"><span>' + product.description + '<span/></td>\n' +
        '            <td class="text-center" width="15%"><span>' + (product.available ? 'Available' : 'On Order') + '<span/></td>\n' +
        '            <td class="text-center" width="20%">' +
        '            <a href="editProductForm/' + product.productId + '">' +
        '                <button class="btn btn-primary btn-xs" type="button">Edit</button>' +
        '            </a>' +
        '                <button class="btn btn-primary btn-xs" type="button">Delete</button>\n' +
        '            </td>\n' +
        '        </tr>'
}

function buildCatalogueCard(product) {
    return '        <div class="col-xs-6 col-sm-4 col-lg-3 text-center">\n' +
        '            <div class="thumbnail">\n' +
        '                <div class="caption">\n' +
        '                    <div style="height: 75%;">\n' +
        '                        <img class="catalogue-img" src=' + product.photoPath + '>\n' +
        '                    </div>\n' +
        '                    <h4>' + product.name + '<h4/>\n' +
        '                    <span class="flex-text text-muted">' +
        '                     ' + product.price.toLocaleString("en", {minimumFractionDigits: 2}) + '</span>\n' +
        '                    <p class="flex-text text-muted">' + (product.available ? 'Available' : 'On Order') + '</p>\n' +
        '                    <p><a class="btn btn-primary" href="' + '/getProductDetails/' + product.productId + '">Details</a></p>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '        </div>'
}

function buildEmptyResultCard() {
    return '        <div class="text-center m-auto">\n' +
        '            <h3 style="color: red;">O_ops! Products not found &#9785;</h3>\n' +
        '            <img src="/img/empty_result.jpg">\n' +
        '        </div>';
}

function changeProductAmount(productId, currProductRow) {
    var productAmount = currProductRow.value;
    $.ajax({
        type: "POST",
        url: "/basket/changeProductAmount",
        dataType: 'json',
        data: {
            "productId": productId,
            "productAmount": productAmount
        }
    });
    recalculateTotalPrice();
}

function deleteProductFromBasket(productId, currentRow) {
    $.ajax({
        type: "POST",
        url: "/basket/deleteFromBasket",
        dataType: 'json',
        data: {
            "productId": productId
        }
    });
    currentRow.parentElement.parentElement.remove();
    if ($("#basketTblBody")[0].children.length == 0) {
        $("#basketForm").remove();
        $("#basketContent").append(buildEmptyBasketDiv());
    } else {
        recalculateTotalPrice();
    }
    updateBasketItemsCount();
}

function recalculateTotalPrice() {
    var resultPrice = 0;
    $("#basketTblBody tr").each(function () {
        var price = parseFloat(this.children[2].children[0].textContent);
        var amount = parseInt(this.children[3].children[0].value);
        resultPrice += price * amount;
    });
    $("#totalPrice")[0].textContent = resultPrice.toLocaleString("en", {minimumFractionDigits: 2});
}

function buildEmptyBasketDiv() {
    return '<div id="emptyBasket" th:if="${selectedProductsMap == null or selectedProductsMap.isEmpty()}">' +
        '<div class="text-center m-auto">' +
        '<h3 style="color: red;">O_ops! The basket is empty again &#9785;</h3>' +
        '<img src="/img/empty_result.jpg">' +
        '</div>' +
        '</div>';
}

function updateBasketItemsCount() {
    $.ajax({
        type: "GET",
        url: "/basket/basketAmount",
        dataType: 'json',
        success: function (response) {
            if (response) {
                $('#itemCount').html(response).css('display', '');
            } else {
                $('#itemCount').html(response).css('display', 'none');
            }
        }
    });
}

function fillCurrentUsername() {
    $.ajax({
        type: "GET",
        url: "/getCurrentUser",
        dataType: 'text',
        success: function (data) {
            if (data) {
                $('#loggedInUsername').html("Current User: " + data);
            }
        }
    });
}

$(document).on("click", ".deleteCategory", function () {
    var selectedCategoryId = $(this)[0].parentElement.children[0].value;
    $(".modal-body #selectedCategoryId")[0].value = selectedCategoryId;
});

$(document).on("click", ".deleteProduct", function () {
    debugger;
    var selectedProductId = $(this)[0].parentElement.children[0].value;
    $(".modal-body #selectedProductId")[0].value = selectedProductId;
});