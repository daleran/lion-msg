const Client = require('./rabbitClient')

// Convert a Roster System student object to a Course Managment student object
const toCourseManagmentStudent = (student) => {
  console.log('Converting to a Course Mangement Student')
  let converted = { }
  converted.studentID = student.stuID
  converted.studentName = student.name
  converted.studentSsn = student.ssn
  converted.studentEmail = student.emailAddress
  converted.studentPhone = student.homePhone
  return converted
}

// Callback to handle the message recieved from RabbitMQ
const handleMessage = (message) => {
  try {
    console.log('Data recieved.')

    // Parse the message into a JS object from JSON
    const rosterStudent = JSON.parse(message.content.toString())
    console.log(rosterStudent)

    // Convert the Roster Stuent into a Course Management Student
    const courseMgmtStudent = toCourseManagmentStudent(rosterStudent)
    console.log(courseMgmtStudent)
  } catch (error) {
    console.error('Unable to Parse Message into a JS Object')
  }
}

// Async function to listen for an traffic on the student.new topic
const listenForNewStudents = async () => {
  try {
    // Create a new RabbitMQ client
    const client = new Client()

    // Connect to the localhose RabbitMQ service
    const channel = await client.connect('amqp://localhost')

    // Listen on the student's exchange for student.new topics
    await client.listen(channel, 'students', 'student.new', handleMessage)
  } catch (error) {
    console.error('Error: Unable to set up client for new students.')
    process.exit(1)
  }
}
// Call the listen for new student function
listenForNewStudents()
