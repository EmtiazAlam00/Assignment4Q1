# Assignment4Q1
COMP3005 Assignment Four


Setup instructions for the database:

In PostgreSQL create a database called "A4SQLJDBC" or anything else, once created use the query tool and add the following to create the student schema and table with the initial data to populate the table.

CREATE TABLE students(
	student_id	SERIAL PRIMARY KEY,
	first_name  TEXT NOT NULL,
	last_name	TEXT NOT NULL,
	email		TEXT NOT NULL UNIQUE,
	enrollment_date	DATE
);

INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES
('John', 'Doe', 'john.doe@example.com', '2023-09-01'),
('Jane', 'Smith', 'jane.smith@example.com', '2023-09-01'),
('Jim', 'Beam', 'jim.beam@example.com', '2023-09-02');

Steps to compile and run your application:
Copy the source file into an IDE using JAVA to compile and run the application.
-
Brief explanation of each function in the application:
- getAllStudents() function retrieves and displays all records from the student table, it will also update if called when a new student is added to the student table by SQL or using JDBC. The function starts by trying to connect to the database with the credentials. A statement is created using SELECT to select all info of all students to be displayed.
- addStudent() function has 4 arguments to be passed, those arguments are needed to successfully add a new Student into the student table database. The function starts by trying to connect to the database with the credentials. Then a statement is created INSERT INTO students with the given arguments. A print statement saying the student added successfully, then using the getAllStudents() function to print the updated student list.
- updateStudentEmail function updates the email address for a student with the specified student_id. The function connects to the database, then a SQL statement is created to update the email given the student_id, printing out if the email is updated or a specified student id was not found. 
- deleteStudent function takes the student ID and deletes all of its info from the student table. Once connected to the database, the SQL statement DELETE selects the student where it equals to student_id. Two print statements for if the student is deleted or the student was not found.

- ---
