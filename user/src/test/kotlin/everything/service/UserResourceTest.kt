package everything.service

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.RestAssured.responseSpecification
import io.restassured.parsing.Parser
import jakarta.inject.Inject
import jakarta.persistence.OrderBy
import jakarta.transaction.Transactional

import org.junit.jupiter.api.Test

import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response.Status
import org.hamcrest.CoreMatchers.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order

@QuarkusTest

class UserResourceTest {

    val endpoint = "/api/users/"

    @Inject
    lateinit var userRepository: UserRepository

    @BeforeEach
    @Transactional
    fun cleanUp() {
        userRepository.deleteAll()
        RestAssured.defaultParser = Parser.JSON
        userRepository.persist(
            User(name = "bob", email = "bob@bob.com", password = "12345678")
        )
    }


    @Test
    @Transactional
    fun `test if user is created and returned by the api`() {
        val max = User(name = "max", email = "max@max.com", password = "12345678")
        given().contentType(MediaType.APPLICATION_JSON)
            .body(max)
            .post(endpoint)
            .then()
            .assertThat()

            .body("name", equalTo("max"))
            .body("email", equalTo("max@max.com"))
            .body("password", nullValue())
            .contentType(MediaType.APPLICATION_JSON)
            .statusCode(Status.CREATED.statusCode)
    }


    @Test
    @Transactional
    fun `test if the provided user is returned by the endpoint`() {


        given()
            .contentType(MediaType.APPLICATION_JSON)
            .pathParam("id", 1)
            .get("$endpoint{id}")
            .then()
            .body(not(""))
            .body("id", equalTo(1))
            .body("name", equalTo("bob"))
            .body("email", equalTo("bob@bob.com"))
            .body("password", nullValue())
            .contentType(MediaType.APPLICATION_JSON)
            .statusCode(Status.OK.statusCode)


    }
}