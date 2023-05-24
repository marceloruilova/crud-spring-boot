# Getting Started

### HOW TO START!
For starting the application you should create a table person on
a database of your preference. Add the field id, name, surname,
address and phone. Do not forget to use a incremental data type on
id. Dbeaver example:
#### CREATE TABLE public.person(
id serial4 NOT NULL,
name varchar NULL,
surname varchar NULL,
address varchar NULL,
phone varchar NULL,
CONSTRAINT person_pkey PRIMARY KEY (id)
);
#### Then right click on DemoCrudApplication and you can run the app.
Table of Contents
Authentication
Endpoints 
### User
####   1.1 Get User
#### Method: GET
#### URL: /api/v1/all
#### Description: Retrieves all .
#### Response Format: JSON
####   1.2 Create Person
#### Method: POST
#### URL: /api/v1/save
#### Description: Creates a new person.
#### Request Body: JSON
#### {
#### "name": "New Person",
#### "surname": "new@example.com",
#### "address": "Peru",
#### "phone": "099987797"
#### }
####   1.3 Update User
#### Method: POST
#### URL: /api/v1/save
#### Description: Updates a user.
#### Request Body: JSON
#### {
#### "id": 1,
#### "name": "Updated Name",
#### "surname": "john@example.com",
#### "address": "Peru",
#### "phone": "099987797"
#### }
####   1.4 Delete Person
#### Method: DELETE
#### URL: /api/v1/delete/{id}
#### Description: Deletes a person.
## If you want to review the behavior in a better way review the tests.

