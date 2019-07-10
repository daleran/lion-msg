package edu.lionmsg;

/**
 * SWENG 568
 * StudentController.java
 * Purpose: A simple application which simulates the entry of new
 * students into the Student Roster System
 *
 * @author Sean Davis
 */
public class StudentController {
   //Encryption variables
    private static String aesKey;
    private static String initVector;

    public static void main(String[] args) {

        //Parse the arguments to retrieve the student ID and security details
        //parseArguments(args);

        //Simulate retrieving the student from a UI layer
        Student student = postNewStudent();

        // Simulate adding the student to the Oracle DB
        addStudentToDB(student);

        //Create a new message client and send the student object
        MessageClient client = new MessageClient();
        client.notifyNewStudent(student);
    }

    public static void parseArguments(String[] args){
        //Retrieve the AES key and initialization vector
        if(args.length > 1) {
            try {
                aesKey = args[0];
                initVector = args[1];
            } catch (Exception err) {
                System.out.println("Unable to parse arguments");
            }
        } else {
            throw new IllegalArgumentException("All 2 arguments are required");
        }
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

        System.out.println(student.name + " record sent from UI.");

        return student;
    }

    /**
     * Method for simulating new student data being
     * entered into the system from an imaginary UI layer
     *
     * @return a new Student object
     */
    private static void addStudentToDB(Student student) {
        System.out.println(student.name + " record added to OracleDB");
    }
}
