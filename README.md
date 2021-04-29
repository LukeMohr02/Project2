# FishBrook Backend
RESTful API used to expose resources through endpoints

### Tech Stack
* __Java 8__
* __Apache Maven__
* __Spring Boot__
* __Spring MVC__

## Endpoints

### Angler
| Endpoint | HTTP Verb | Usage |
| --------- | ----------- | ----- |
| /angler    | GET | returns a page of all anglers  |
| /angler/{username}      | GET  | returns an angler with the given username  |
| /angler    | POST  | persists angler passed in request body |
| /angler    | PUT |  updates angler passed in request body  |
| /angler/{username}    | DELETE |  deletes angler with the given username  |
