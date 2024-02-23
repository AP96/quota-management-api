Overview

This Spring Boot application is designed to efficiently manage Web API usage, preventing abuse through excessive requests by implementing a robust access-limiting mechanism. 
It ensures optimal performance and resource utilization by limiting users to a specified number of API requests.

Quota Management: Each user is allowed up to X API requests (configurable, e.g., 5 requests per user). Beyond this quota, the user is blocked from making further requests.
Data Sources: User information is fetched from MySQL during the day (9:00 - 17:00 UTC) and from Elastic during the night (17:01 - 8:59 UTC).
CRUD Operations: Supports creating, retrieving, updating, and deleting user information without affecting their quota.


Quota Tracking: Includes endpoints for consuming a user's quota and checking all users' quota statuses.
CRUD for User Model (Not subject to quota limits):
POST /users - Create a new user.
GET /users/{userId} - Retrieve a user's details.
PUT /users/{userId} - Update user details.
DELETE /users/{userId} - Delete a user.

Quota Management:
POST /users/{userId}/consumeQuota - Consumes one quota from the specified user.
GET /users/quota - Retrieves all users and their remaining quota.

Unit Testing: Run mvn test
