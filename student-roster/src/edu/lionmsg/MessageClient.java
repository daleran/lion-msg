package edu.lionmsg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MessageClient {
    // Exchange and new student topic constants
    private final String EXCHANGE_NAME = "students";
    private final String NEW_STUDENT_TOPIC = "student.new";

    /**
     * Send a serialized JSON Byte array of the student to any
     * consumers listening to the student.new topic
     *
     * @param student the student to send to subscribers
     */
    public void notifyNewStudent(Student student){
        // Create a new connection factory and set it to the localhost
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        //Try to create the channel and connection
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
            //Declare the exchange as a RabbitMQ topic type exchange
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            // Transform the student object into a JSON byte array
            byte[] studentBytes = serializeToJsonByteArray(student);

            // Send the byte array to the students exchange with the topic student.new
            channel.basicPublish(EXCHANGE_NAME,NEW_STUDENT_TOPIC,null,studentBytes);
            System.out.println(student.name + " record send to consumers.");

        } catch (Exception error) {
            System.out.println("Error notifying consumers about "+student.name);
        }
    }

    private byte[] serializeToJsonByteArray(Student student) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(student);
    }
}
