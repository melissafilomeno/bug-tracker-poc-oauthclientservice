OAuth Client Service


Pre-requisites :
* Java 17.0.12
* Maven 3.9.8
* Postman 11.19.0


Setup : 
* Create a .env file and define the following fields : (Refer to bug-tracker-poc for details)
- KEYCLOAK_CLIENT_ID
- KEYCLOAK_CLIENT_SECRET


Run :
* Start bug-tracker-poc
* Run mvn clean install
* Run mvn spring-boot:run


Verification:
* Import environment and postman_collection under postman/, select 'local' environment, set 'current value' from 'initial value', run the collection and verify
