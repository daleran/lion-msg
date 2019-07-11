const Client = require('./rabbitClient')

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

const handleMessage = (message) => {
  try {
    console.log('Data recieved.')
    const rosterStudent = JSON.parse(message.content.toString())
    console.log(rosterStudent)
    const courseMgmtStudent = toCourseManagmentStudent(rosterStudent)
    console.log(courseMgmtStudent)
  } catch (error) {
    console.error('Unable to Parse Message into a JS Object')
  }
}

const listenForNewStudents = async () => {
  try {
    const client = new Client()
    const channel = await client.connect('amqp://localhost')
    await client.listen(channel, 'students', 'student.new', handleMessage)
  } catch (error) {
    console.error('Error: Unable to set up client for new students.')
    process.exit(1)
  }
}

listenForNewStudents()
