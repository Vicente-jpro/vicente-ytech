# vicente-ytech
 This is a simple exercise, a simple order manager. You should develop an API where users can create and manage orders. Items can be ordered and orders are automatically fulfilled as soon as the item stock allows it

## vicente-ytech challenge

This project was with `Spring Boot version 2.7.16, Spring Security, Token JWT` and `JDK 1.8`

### Development server
 
Navegate inside `/vicente-ytech` folder. Open your terminal and run this command do create
a `jar` file. <br/> 

Execute the jar file by running `java -jar ./target/vicente-ytech-0.0.1-SNAPSHOT.jar` then open your browser `http://localhost:8080/api/`. <br/>

### Database seed

Execute this code to in your database.

```sql
INSERT INTO items (name) VALUES ('Java');
INSERT INTO items (name) VALUES ('Ruby');
INSERT INTO items (name) VALUES ('Go');
INSERT INTO items (name) VALUES ('PHP');

INSERT INTO stock_movement (creation_date, quantity, item_id)
	VALUES ('2024-08-08', 9000, 1);
INSERT INTO stock_movement (creation_date, quantity, item_id)
	VALUES ('2024-09-08', 4000, 2);
INSERT INTO stock_movement (creation_date, quantity, item_id)
	VALUES ('2024-07-08', 98000, 2);
```

### Import colletion to Postman

Open your Postman Application, go to `import -> upload files` or `import -> select files`. <br/>

`Postman`
![alt text](https://github.com/Vicente-jpro/vicente-ytech/blob/main/images/import.PNG) <br/>

`Import file`
![alt text](https://github.com/Vicente-jpro/vicente-ytech/blob/main/images/upload_file.PNG) <br/>

`Create account`
![alt text](https://github.com/Vicente-jpro/vicente-ytech/blob/main/images/create_account.PNG) <br/>

`Authenticate account`
![alt text](https://github.com/Vicente-jpro/vicente-ytech/blob/main/images/authenticate_account.PNG) <br/> <br/>


After getting `token` , copy it. In Postman go to `Authorization` , select `Bearer Token` and past the  `token` to the feild. <br/>

![alt text](https://github.com/Vicente-jpro/vicente-ytech/blob/main/images/request.PNG) <br/> <br/>

Now you can make any request in this API by copy and past the token to the right plase.
