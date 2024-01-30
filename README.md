# Product Service Project

Techcareer Start-to-Finish Back-End Boot Camp Project. By establishing a many to one relationship between category and product, category and product addition, deletion and listing operations were performed.

## Technologies

- Java 17
- Spring Boot
    - spring-boot-starter-data-jpa
    - spring-boot-starter-web
    - spring-boot-devtools
- PostgreSQL
- ModelMapper
- Springdoc OpenAPI Swagger
- SLF4J ve Lombok

## API Examples

### Postman

#### Save Product
- Endpoint: `POST http://localhost:8080/api/products`
  
![Save Product](https://github.com/muhammed-cetin/product-rest-api/blob/master/images/productpost.png)

#### Find All Products
- Endpoint: `GET http://localhost:8080/api/products`
  
![Find All Products](https://github.com/muhammed-cetin/product-rest-api/blob/master/images/productpost.png)

#### Find All By Id Product
- Endpoint: `GET http://localhost:8080/api/products/1`
  
![Find All By Id Product](https://github.com/muhammed-cetin/product-rest-api/blob/master/images/productget.png)

#### Update By Title Product
- Endpoint: `PUT http://localhost:8080/api/products/Monster Abra A5 V21.1.2`
  
![Update By Title Product](https://github.com/muhammed-cetin/product-rest-api/blob/master/images/productput.png)

#### Delete By Id Product
- Endpoint: `DELETE http://localhost:8080/api/products/4`
  
![Delete By Id Product](https://github.com/muhammed-cetin/product-rest-api/blob/master/images/product%20delete.png)

### Springdoc OpenAPI Swagger
- Endpoint: `http://localhost:8080/swagger-ui/index.html`
  
![Springdoc OpenAPI Swagger](https://github.com/muhammed-cetin/product-rest-api/blob/master/images/swagger.png)

