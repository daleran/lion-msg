package edu.lionmsg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * SWENG 568
 * MessageClient.java
 * Purpose: A RabbitMQ message client capable of seraizling
 * student objects and broadcasting them on the the
 * student.new topic in the students exchange
 *
 * @author Sean Davis
 */
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
            System.out.println(student.name + " record send to consumers at " + EXCHANGE_NAME + " topic " + NEW_STUDENT_TOPIC);

        } catch (Exception error) {
            System.out.println("Error notifying consumers about "+student.name);
        }
    }

    // Serialize the student object into a byte array of the JSON object
    private byte[] serializeToJsonByteArray(Student student) {
        //Create a new Jackson Object Mapper
        ObjectMapper mapper = new ObjectMapper();

        try {
            //Serialize the student into the JSON byte array
            return mapper.writeValueAsBytes(student);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
