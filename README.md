# Lottery System

##### Lottery System Application using Spring Boot

- The in-memory database used is H2.
- RESTful APIs are exposed to front-end application. 

##### Test the Lottery System APIs using postman collection or cURLs

**Postman collection:**

[https://www.getpostman.com/collections/475d3dfdd7c9d2561ad2](https://www.getpostman.com/collections/475d3dfdd7c9d2561ad2)

**Create a user with details pay load.**

	curl --location --request POST 'http://localhost:8080/user' \
	--header 'Content-Type: application/json' \
	--data-raw '{
    	"userName": "panda",
    	"email": "panda@gmail.com"
	}'

**User gets the Active draws that is eligible to participate.**

	curl --location --request GET 'http://localhost:8080/draws?status=ACTIVE'

**User buys the ticket for the active draw.**

	curl --location --request POST 'http://localhost:8080/ticket' \
	--header 'Content-Type: application/json' \
	--data-raw '{
    "drawId": "db0cb80d-058e-46c8-afc0-dce5b0b7aa15",
    "userId": "2d0eea66-667e-404b-9aa6-abf323ae6268"
	}'
	
**Once the draw is completed, user can get the draw details of the winning status.**
	
	curl --location --request GET 'http://localhost:8080/draw?drawId=ca7fbabe-9e2f-4072-b1a6-1ed2c762ebd9'
	
**User gets the Completed draws to check the winner details.**

	curl --location --request GET 'http://localhost:8080/draws?status=COMPLETED'
	
**User can get the draws participated.**

	curl --location --request GET 'http://localhost:8080/user/draws?userId=2d0eea66-667e-404b-9aa6-abf323ae6268'
	
### Schedulers:

- Draw scheduler is running for creating draws continuously and periodically every 60 seconds cron expression defined in properties file.
- Draw winner scheduler to pick 1 winner randomly from the active draw and the end date less than equal to current time runs every 20 seconds cron expression defined in properties file

## Ways to run the application:

##### 1. Run the application as Spring Boot

	$ mvn spring-boot:run

##### 2. Run the application on Docker Compose

	$ docker-compose up
	$ docker-compose down

##### 3. Run the application on Kubernetes

Deploy the services on defaut namespace
	
	kubectl apply -f lottery-service.yml
	
**Limitations and improvements of the application:**

	1. As-is: To get the Winner information, RESTful API is used.
	   Could-be: Implement Message queue for publishing the winner to all the user participated in the draw.

	2. As-is: The application is not configured to handle stress.
	   Could-be: Implement to handle scheduler with more than 1 replica set.

	3. As-is: Cannot be scaled with schedulers for more than 1 replica set.
	   Could-be: Implement Master-Slave executor pattern.