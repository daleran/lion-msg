module.exports = class Rabbit {
  connect (hostname) {
    return new Promise(function (resolve, reject) {
      require('amqplib').connect(hostname)
        .then((connection) => {
          return connection.createChannel()
        })
        .then((channel) => {
          console.log(`Connected to RabbitMQ on ${hostname}`)
          resolve(channel)
        })
        .catch((error) => {
          console.error(`Error: Unable to connect to ${hostname}`)
          reject(error)
        })
    })
  }

  listen (channel, exchange, topic, handler) {
    return new Promise(function (resolve, reject) {
      channel.assertExchange(exchange, 'topic', { durable: false })
      channel.assertQueue('', { exclusive: true })
        .then((q) => {
          console.log(`Listening for ${topic} on ${exchange}. Press CTRL+C to exit.`)
          channel.bindQueue(q.queue, exchange, topic)
          channel.consume(q.queue, handler, { noAck: true })
        })
        .then(() => { resolve() })
        .catch((error) => {
          console.error(`Error: Unable to listen on ${exchange}`)
          reject(error)
        })
    })
  }
}
