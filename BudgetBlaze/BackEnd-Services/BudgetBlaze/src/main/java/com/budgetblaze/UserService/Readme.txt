------------------ User Service ----------->

•	User Management:
    Core Functions:
    o	Sign Up: User registration with email verification.
    o	Login: Secure login via username/email and password or social login (e.g., Google, Facebook).
    o	Password Recovery: Password reset functionality via email. [optional]
    o	Profile Management: Ability to update personal details and preferences.

•	Responsibilities:
        o	User registration and authentication (via JWT or OAuth).
        o	Profile management (name, email, phone, etc.).
        o	Password management and reset.
•	Technology:
        o	Spring Boot (Java) / Express.js (Node.js) / Django (Python).
        o	Authentication using JWT (JSON Web Tokens) or OAuth 2.0.
        o	Database: Relational DB like PostgreSQL or NoSQL (MongoDB)

•	Components:
    o	Sign Up:
        	Form with fields: Name, Email, Password, Confirm Password.
        	Backend API: POST /api/auth/signup
        	Input validation and user creation.
        	Email verification using a token sent to the user’s email.

    o	Login:
        	Form with fields: Email, Password.
        	Backend API: POST /api/auth/login
        	Validate credentials and return JWT token.
    o	Profile Management:
        	API to get user details: GET /api/user/profile
        	API to update user details: PUT /api/user/profile
        •	Database Design:
    o	User table: id, email, password_hash, name, created_at, updated_at


//Sign Up
    -validate the RegPayload
    - Validate the Regpayload objects present in Db.
    - if yes,
        - Rteurn UserAlreadyRegisterdEcxception
    -else,
        - Save the details and return state.

// Customer Profile
//Get Details
    - Validate the UserId
    - Validate the UserId present in Db.
    - If yes,
        - Return the user Details
    - else,
        - Throws User Not Found Exception.
    
// update Details
    - Validate the UserId and customerProfileUpdateDto
    - Validate the UserId present in Db.
    - If yes,
        - Update the Details
        - Return the user Details update status
    - else,
        - Throw User Not Found Exception.
    





