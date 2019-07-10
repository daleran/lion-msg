const ampq = require('amqplib')

const initRabbitMQ = async () => {
  try {
    const connection = await ampq.connect('ampq://localhose')
    const channel = await connection.createChannel()
    const exchangeName = 'students'
    const topic = 'student.new'

    await channel.assertExchange(exchangeName, 'topic', { durable: false })
    const queue = await channel.assertQueue('', { exclusive: true })

    await channel.bindQueue(queue, exchangeName, topic)
    const msgBytes = await channel.consume(queue, exchangeName, topic)

    console.log(JSON.parse(msgBytes.content.toString()))

    process.on('beforeExit', () => {
      connection.close()
      channel.close()
    })
  } catch (error) {
    console.error(error)
    process.exit()
  }
}

initRabbitMQ()
