package computerdynamics

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BasicSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")

  val scn = scenario("Basic API Load")
    .exec(http("Get Students").get("/api/students"))
    .pause(1)

  setUp(
    scn.inject(atOnceUsers(10))
  ).protocols(httpProtocol)
}
