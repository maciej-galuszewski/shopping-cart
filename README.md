# Shopping Cart

Shopping Cart is a simple application to calculate the price of a shopping basket, given item type and quantity.

Items are presented one at a time in a list, identified by name.

Items are priced as follows:
- apples for 25 each
- oranges for 30 each
- bananas for 15 each
- papayas for 50 each, but are available as "three for the price of two"

## Technical description
Shopping Cart is a Spring Boot 2 application which exposes single endpoint (`/order`) to generate receipt.

Application uses h2 database which will be initialized on first use and stored in `h2.mv.db` file in project root directory. Database is initialized with product types as described above.
Flyway is used for database versioning.

The output can be returned either as a plaintext or JSON, depending on the `Accept` header in the request (`text/plain` for plaintext and `application/json` for JSON).

## Installation
Shopping Cart uses maven to build application.

To install application, run following command:
```
mvn clean install
```

This downloads all required dependencies and executes both unit and integration tests.

## Usage
To run application server, run following command:
```
mvn spring-boot:run
```

Application exposes single `/order` endpoint which accepts `POST` requests with order details. Endpoint returns receipt details. By default, server starts at `8080` port.

Depending on the `Accept` header, data is returned in JSON or plaintext format. JSON is returned when `Accept` header has `application/json` value (this type of content might be used for use by REST-compliant client). If `Accept` header has `text/plain` value, receipt data is returned as human-readable string.

Sample POST request looks as follows:
```
POST http://localhost:8080/order
    Headers:
        Accept          application/json
        Content-Type    application/json
    Body:
        {
           "orderRecords":[
              {
                 "productKey":"APPLE",
                 "quantity":20
              },
              {
                 "productKey":"BANANA",
                 "quantity":10
              }
           ]
        }
```
Following response will be returned as JSON:
```
{
    "records": [
        {
            "productKey": "APPLE",
            "productName": "Apple",
            "quantity": 20,
            "price": 25.00,
            "amount": 500.00
        },
        {
            "productKey": "BANANA",
            "productName": "Banana",
            "quantity": 10,
            "price": 15.00,
            "amount": 150.00
        }
    ],
    "totalAmount": 650.00
}
```

The same request, but with `Accept text/plain` header will return:
```
RECEIPT

Product name     Qty    Price       Amount

Apple            20     25.00       500.00
Banana           10     15.00       150.00

Total sum: 650.00
```

In the project root there is a Postman collection (`Shopping cart.postman_collection.json`) which contains sample requests.

## Author
Maciej Gałuszewski ([maciej.galuszewski@gmail.com](mailto:maciej.galuszewski@gmail.com))
