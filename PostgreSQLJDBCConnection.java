package A4;
//Emtiaz Alam, 101114889

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;


public class PostgreSQLJDBCConnection {
	//Private variables so only functions inside the class has access to JDBC & Database credentials
	private static final String url = "jdbc:postgresql://localhost:5432/A4SQLJDBC"; //Change Database name "A4SQLJDBC" to your own.
	private static final String user = "postgres"; //Change user name if needed to your own.
	private static final String password = "postgres"; //Change password if needed to your own.
	
	public static void main(String[] args) {
		//call functions (CRUD) to run in Database, the order of execution matters if performing specific tasks.
		
		//getAllStudents();
		//addStudent("Lucky","Day","lucky@gmail.com","2006-04-06");
		//updateStudentEmail(2,"smith@gmail.com");
		//deleteStudent(1);

	}
	/*getAllStudents() function retrieves and displays all records from student table, it will also update if called when a new student is added to the student table by SQL or using JDBC.
	The function starts by trying to connect to the database with the credentials. A statement is created using SELECT to select all info of all students to be displayed. */
	 public static void getAllStudents() {
	        try {
	            Class.forName("org.postgresql.Driver");
	            // Connect to the database
	            try (Connection conn = DriverManager.getConnection(url, user, password)) {
	                if (conn != null) {
	                    // Create statement
	                    Statement stmt = conn.createStatement();
	                        String getStudents = "SELECT student_id, first_name, last_name, email, enrollment_date FROM students";
	                        ResultSet rs = stmt.executeQuery(getStudents);
	                            while (rs.next()) {
	                            	//Get the id, first, last names, email and enrollment date.
	                                int student_id = rs.getInt("student_id");
	                                String first_name = rs.getString("first_name");
	                                String last_name = rs.getString("last_name");
	                                String email = rs.getString("email");
	                                //Formatted the date to a string  
	                                Date enrollment_date = rs.getDate("enrollment_date");
	                                SimpleDateFormat Format = new SimpleDateFormat("YYYY-MM-DD");
	                                String enrollmentDate = Format.format(enrollment_date);
	                                //Display all student info in the console
	                                System.out.println("Student ID: " + student_id + ", First name: " + first_name + ", Last name: " + last_name + ", Email: " + email + ", Enrollment date: " + enrollmentDate);
	                            }
	                            //Close resources and connection when task is complete
	                            rs.close();
	        					stmt.close();
	                } else {
	                	//Print statement if no connection if found
	                    System.out.println("Failed to establish connection.");
	                }
	            }
	        } 
	        //Instead of an error message, it catches the exception object and prints a trace of the exception.
	        catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	        	}
	  }
	 
	 /*addStudent() function has 4 arguments to be passed, those arguments are needed to successfully add a new Student into the student table database.
	  The function starts by trying to connect to the database with the credentials. Then a statement is created INSERT INTO students with the given arguments. 
	  A print statement saying the student added successfully, than using the getAllStudents() function to print the updated student list. */
	 public static void addStudent(String first_name, String last_name, String email, String enrollment_date) {
		 try {
	            Class.forName("org.postgresql.Driver");
	            //Connect to the database
	            try (Connection conn = DriverManager.getConnection(url, user, password)) {
	                if (conn != null) {
	                	//Create statement
	                   String insertSQL = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";           
	                   try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
	                	   pstmt.setString(1, first_name);
	                	   pstmt.setString(2, last_name);
	                	   pstmt.setString(3, email);
	                	   // Parsed the enrollment_date string to java.util.Date
	                       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	                       Date parsedEnrollmentDate = new java.sql.Date(dateFormat.parse(enrollment_date).getTime());
	                       pstmt.setDate(4, (java.sql.Date) parsedEnrollmentDate);
	                	   pstmt.executeUpdate();
	                	   System.out.println("New Student added!");
	                   }
	                   //Close connection
	                   conn.close();                 
	                } else {
	                	//Print statement if no connection if found
	                    System.out.println("Failed to establish connection.");
	                }
	            }
	            //Display updated student list.
	            getAllStudents();
	        }  
		 	//Instead of an error messages, it catches the exception object and prints a trace of the exception.	
		 	catch (ClassNotFoundException | SQLException | ParseException e) {
	            // Checks for unique constraint violation, no duplicate email is created.
	            if (e instanceof SQLException && ((SQLException) e).getSQLState().equals("23505")) {
	                System.out.println("Error: Email already exists in the database.");
	            } else {
	                e.printStackTrace();
	            }
	        }
	 }
	 /*updateStudentEmail function updates the email address for a student with the specified student_id. The function connects to the database, 
	  * then a SQL statement is created to update the email given the student_id, printing out if email is updated or specified student id was not found.   */
	 public static void updateStudentEmail(int student_id, String new_email) {
		 try {
	            Class.forName("org.postgresql.Driver");
	            // Connect to the database
	            try (Connection conn = DriverManager.getConnection(url, user, password)) {
	                if (conn != null) {
	                    // Create statement
	                        String updateSQL = "UPDATE students SET email = ? WHERE student_id = ?";
	                        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)){
	                        	pstmt.setString(1, new_email);
	                        	pstmt.setInt(2, student_id);
	                        	int rowsAffected = pstmt.executeUpdate();
	                        	if (rowsAffected > 0) {
	                                System.out.println("Email updated!");
	                            } else {
	                                System.out.println("Warning: No student found with the specified ID!");
	                            	}	
	                        }
	                        //close connection when done
	                        conn.close();
	                } else {
	                	//Print statement if no connection if found
	                    System.out.println("Failed to establish connection.");
	                	}
	                //Display updated student list.
	                getAllStudents();
	            }
		 } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	      	}
	 }
	 
	 /*deleteStudent function takes the student id and deletes all of its info from the student table. Once connected to the database, the sql statement DELETE selects the student 
	  where it equals to student_id. Two print statements for if student is deleted or student was not found.*/
	 public static void deleteStudent(int student_id) {
		    try {
		        Class.forName("org.postgresql.Driver");
		        //Connect to the database
		        try (Connection conn = DriverManager.getConnection(url, user, password)) {
		            if (conn != null) {
		            	//Create Statement
		                String deleteSQL = "DELETE FROM students WHERE student_id = ?";
		                try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
		                    pstmt.setInt(1, student_id);
		                    int rowsAffected = pstmt.executeUpdate();  
		                    if (rowsAffected > 0) {
		                        System.out.println("Student ID: " + student_id + ", deleted successfully!");
		                    } else {
		                        System.out.println("No student found with the specified ID.");
		                    }
		                }
		                //Close connection
		                conn.close();
		            } else {
		            	////Print statement if no connection if found
		                System.out.println("Failed to establish connection.");
		            }  
		          //Display updated student list.
		            getAllStudents();
		        }
		    } 
		  //Instead of an error message, it catches the exception object and prints a trace of the exception.
		    catch (ClassNotFoundException | SQLException e) {
		        e.printStackTrace();
		    }
		}; 
}