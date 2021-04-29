# FishBrook Backend
RESTful API completely decoupled from frontend implementation. Used to expose resources through endpoints listed below.

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

### Catch
| Endpoint | HTTP Verb | Usage |
| --------- | ----------- | ----- |
| /catch    | GET | returns a page of all catches  |
| /catch/{id}      | GET  | returns a catch with the given id  |
| /catch    | POST  | persists catch passed in request body |
| /catch    | PUT |  updates catch passed in request body  |
| /catch/{id}    | DELETE |  deletes catch with the given id  |

### Fish
| Endpoint | HTTP Verb | Usage |
| --------- | ----------- | ----- |
| /fish    | GET | returns a page of all fish  |
| /fish/{id}      | GET  | returns a fish with the given id  |
| /fish    | POST  | persists fish passed in request body |
| /fish    | PUT |  updates fish passed in request body  |
| /fish/{id}    | DELETE |  deletes fish with the given id  |

### Gear
| Endpoint | HTTP Verb | Usage |
| --------- | ----------- | ----- |
| /gear    | GET | returns a page of all gear  |
| /gear/{id}      | GET  | returns an fish with the given id  |
| /gear    | POST  | persists gear passed in request body |
| /gear    | PUT |  updates gear passed in request body  |
| /gear/{id}    | DELETE |  deletes gear with the given id  |

### Group
| Endpoint | HTTP  Verb | Usage |
| --------- | ----------- | ----- |
| /group    | GET | returns a page of all groups  |
| /group/{id}      | GET  | returns a group with the given id  |
| /group    | POST  | persists group passed in request body |
| /group    | PUT |  updates group passed in request body  |
| /group/{group}/join    | PUT |  angler passed through request body with join the group with the group named passed through the URI |
| /group/{group}/leave    | PUT |  angler passed through request body with leave the group with the group named passed through the URI |
| /group/{id}    | DELETE |  deletes group with the given id  |
