// Class to encapsulate the amqplib package for connecting and listening to topics
module.exports = class Rabbit {
  // Connect to a specific host
  connect (hostname) {
    // Wrap the ampq connection and channel creation in a promise
    return new Promise(function (resolve, reject) {
      // Connect to the RabbitMQ service at the specified hostname
      require('amqplib').connect(hostname)
      // Then create a chanel
        .then((connection) => {
          return connection.createChannel()
        })
        // Then log the connection and resolve the promise with the channel
        .then((channel) => {
          console.log(`Connected to RabbitMQ on ${hostname}`)
          resolve(channel)
        })
        // If there is an error, reject the promise and print an error
        .catch((error) => {
          console.error(`Error: Unable to connect to ${hostname}`)
          reject(error)
        })
    })
  }

  // Listen for messages on the specified exhange and topic
  listen (channel, exchange, topic, callback) {
    // Wrap exchange and queue binding in a promise
    return new Promise(function (resolve, reject) {
      // Assert a topic exchange exsists of the specified name
      channel.assertExchange(exchange, 'topic', { durable: false })
      // Assert an anynomous queue exsists for the channnel
      channel.assertQueue('', { exclusive: true })
      // Then bind the queue to the exchange and topic
        .then((q) => {
          console.log(`Listening for ${topic} on ${exchange}. Press CTRL+C to exit.`)
          channel.bindQueue(q.queue, exchange, topic)
          // Consume any messages in the queue and pass them to the callback
          channel.consume(q.queue, callback, { noAck: true })
        })
        // Then resolve the promise as a success
        .then(() => { resolve() })
        // Or reject the promise if there is an error and log it to the console
        .catch((error) => {
          console.error(`Error: Unable to listen on ${exchange}`)
          reject(error)
        })
    })
  }
}
