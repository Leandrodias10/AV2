package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class AcademicSystemSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080/api")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")
    .basicAuth("admin", "admin123")

  // Scenario 1: Get all students
  val getStudentsScenario = scenario("Get All Students")
    .repeat(10) {
      exec(http("GetStudents")
        .get("/students")
        .check(status.is(200)))
        .pace(1.seconds)
    }

  // Scenario 2: Create and read student
  val createStudentScenario = scenario("Create Student")
    .repeat(5) {
      exec(http("CreateStudent")
        .post("/students")
        .body(StringBody("""{"firstName":"John","lastName":"Doe","email":"john${randomInt(1000)}@example.com","registration":"REG${randomInt(9999)}","status":"ACTIVE"}"""))
        .check(status.is(201))
        .check(jsonPath("$.id").saveAs("studentId")))
        .pause(500.millis)
        .exec(http("GetCreatedStudent")
          .get("/students/${studentId}")
          .check(status.is(200)))
    }

  // Scenario 3: Get all courses
  val getCoursesScenario = scenario("Get All Courses")
    .repeat(10) {
      exec(http("GetCourses")
        .get("/courses")
        .check(status.is(200)))
        .pace(1.seconds)
    }

  // Scenario 4: Create course
  val createCourseScenario = scenario("Create Course")
    .repeat(3) {
      exec(http("CreateCourse")
        .post("/courses")
        .body(StringBody("""{"code":"CS${randomInt(999)}","name":"Computer Science ${randomInt(100)}","description":"Advanced CS Course","credits":4,"instructor":"Prof. Smith","status":"ACTIVE"}"""))
        .check(status.is(201)))
    }

  // Scenario 5: Get all enrollments
  val getEnrollmentsScenario = scenario("Get All Enrollments")
    .repeat(10) {
      exec(http("GetEnrollments")
        .get("/enrollments")
        .check(status.is(200)))
        .pace(1.seconds)
    }

  // Setup simulation with ramp-up
  setUp(
    getStudentsScenario.inject(
      rampUsers(10).during(5.seconds),
      constantUsersPerSec(5).during(10.seconds)
    ),
    getCoursesScenario.inject(
      rampUsers(8).during(5.seconds),
      constantUsersPerSec(3).during(10.seconds)
    ),
    createStudentScenario.inject(
      rampUsers(5).during(5.seconds)
    ),
    createCourseScenario.inject(
      rampUsers(3).during(5.seconds)
    ),
    getEnrollmentsScenario.inject(
      rampUsers(10).during(5.seconds),
      constantUsersPerSec(5).during(10.seconds)
    )
  ).protocols(httpProtocol)
    .assertions(
      global.successfulRequests.percent.greaterThan(95),
      global.responseTime.percentile1.lessThan(5000),
      global.responseTime.mean.lessThan(2000)
    )
}