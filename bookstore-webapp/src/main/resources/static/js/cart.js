document.addEventListener('alpine:init', () => {
    Alpine.data('initData', () => ({
        cart: { items: [], totalAmount: 0 },
        orderForm: {
            customer: {
                name: "Safi",
                email: "safi@gmail.com",
                phone: "999999999999"
            },
            deliveryAddress: {
                addressLine1: "Cantt",
                addressLine2: "Estate",
                city:"Shahjahanpur",
                state: "UP",
                zipCode: "242001",
                country: "India"
            }
        },

        init() {
            updateCartItemCount();
            this.loadCart();
            this.cart.totalAmount = getCartTotal();
        },
        loadCart() {
            this.cart = getCart()
        },
        updateItemQuantity(code, quantity) {
            updateProductQuantity(code, quantity);
            this.loadCart();
            this.cart.totalAmount = getCartTotal();
        },
        removeCart() {
            deleteCart();
        },
        createOrder() {
            let order = Object.assign({}, this.orderForm, {items: this.cart.items});
            //console.log("Order ", order);

            $.ajax ({
                url: '/api/orders',
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                data : JSON.stringify(order),
                xhrFields: {
                    withCredentials: false  // 👈 prevent CORS error due to credentials
                },
                success: (resp) => {
                    //console.log("Order Resp:", resp)
                    this.removeCart();
                    // alert("Order placed successfully")
                    window.location = "/orders/"+resp.orderNumber;
                }, error: (err) => {
                    console.log("Order Creation Error:", err)
                    alert("Order creation failed")
                }
            });
        },
    }))
});