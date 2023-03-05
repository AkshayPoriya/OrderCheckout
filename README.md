# OrderCheckout
A service to handle abandon cart notification


**Order Create curl**
```
curl --location 'http://localhost:8080/api/v1/webhook/create-order/Shopify/' \
--header 'Content-Type: text/plain' \
--data '{
  "order": {
    "id": 15,
    "checkout_id": 122,
    "order_status_url": "https://jsmith.myshopify.com/548380009/orders/b1946ac92492d2347c6235b4d2611184/authenticate?key=imasecretipod",
    "fulfillments": [
      {
        "tracking_url": "https://tools.usps.com/go/TrackConfirmAction_input?qtc_tLabels1=1Z2345"
      }
    ]
  }
}'
```

**Abandon-Checkout curl**
```
curl --location 'http://localhost:8080/api/v1/webhook/abandon-checkout/Shopify/' \
--header 'Content-Type: text/plain' \
--data-raw '{
  "abandoned_checkout_url": "https://www.snowdevil.ca/14168/checkouts/0123456789abcdef0456456789abcdef/recover?key=6dacd6065bb80268bda857ee",
  "customer": {
    "email": "bob.norman@mail.example.com",
    "first_name": "Bob",
    "last_name": "Norman"
  },
  "id": 122,
  "phone": {
    "phone": "+13125551212"
  }
}'
```

**Send-Abandon-Notification curl**
```
curl --location --request POST 'http://localhost:8080/api/v1/checkout/send-abandon-notification/' \
--header 'Content-Type: text/plain'
```

![OrderCheckout](https://user-images.githubusercontent.com/53296002/222985300-818da339-de8f-4187-890e-6e11bda1cf03.png)

<img width="837" alt="checkout-collection" src="https://user-images.githubusercontent.com/53296002/222985367-808d096c-b213-4d38-a186-3b76d0cc5327.png">

<img width="812" alt="order-collection" src="https://user-images.githubusercontent.com/53296002/222985378-3e2bf693-70f3-473e-8a63-885ee6040fc8.png">

