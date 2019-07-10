package edu.lionmsg;

import com.rabbitmq.client.ConnectionFactory;

public class MessageClient {
    private final String EXCHANGE_NAME = "students";
    private final String NEW_STUDENT_TOPIC = "student.new";

    public MessageClient() {

    }

    private void initializeRabbitMQ(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
    }

    public void notifyNewStudent(Student student){

    }


}
