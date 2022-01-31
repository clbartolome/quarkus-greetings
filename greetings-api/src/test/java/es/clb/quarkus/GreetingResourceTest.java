package es.clb.quarkus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.junit.QuarkusTest;


@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GreetingResourceTest {

    @Test
    @Order(1)
    public void test_getGreetings_OK() {
      given()
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
      .when()
        .get("/greeting")
      .then()
        .statusCode(200)
        .body("size()", equalTo(2))
        .body("id", hasItems(1, 2));
    }

}