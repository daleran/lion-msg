package edu.lionmsg;

/**
 * SWENG 568
 * StudentController.java
 * Purpose: A simulated controller for entry of new
 * students into the Student Roster System
 *
 * @author Sean Davis
 */
public class StudentController {
    public static void main(String[] args) {

        //Simulate retrieving the student from a UI layer
        Student student = postNewStudent();

        // Simulate adding the student to the Oracle DB
        addStudentToDB(student);

        //Create a new message client and send the student object
        MessageClient client = new MessageClient();
        client.notifyNewStudent(student);
    }

    /**
     * Method for simulating new student data being
     * entered into the system from an imaginary UI layer
     *
     * @return a new Student object
     */
    public static Student postNewStudent(){
        Student student = new Student();

        //Set the member variables to the test data
        student.stuID = 1111;
        student.name = "Bob Smith";
        student.ssn = "222-333-1111";
        student.emailAddress = "bsmith@yahoo.com";
        student.homePhone = "215-777-8888";
        student.homeAddr = "123 Tulip Road, Ambler, PA 19002";
        student.localAddr = "321 Maple Avenue, Lion Town, PA 16800";
        student.emergencyContact = "John Smith (215-222-6666)";
        student.programID = 206;
        student.paymentID = "1111-206";
        student.academicStatus = 1;

        System.out.println(student.name + " record received from UI.");

        return student;
    }

    // Dummy method for adding the new student to the database
    private static void addStudentToDB(Student student) {
        System.out.println(student.name + " record added to OracleDB");
    }
}
