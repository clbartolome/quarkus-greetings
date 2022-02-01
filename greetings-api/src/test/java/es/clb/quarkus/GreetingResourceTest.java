package es.clb.quarkus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;

import javax.json.Json;
import javax.json.JsonObject;
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
        .get("/greeting/")
        .then()
        .statusCode(200)
        .body("size()", equalTo(4))
        .body("id", hasItems(1, 2, 3, 4));
  }

  @Test
  @Order(1)
  public void test_getGreeting_OK() {
    given()
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
        .when()
        .get("/greeting/1")
        .then()
        .statusCode(200)
        .body("id", equalTo(1))
        .body("value", equalTo("Hi there!"));
  }

  @Test
  @Order(1)
  public void test_getGreeting_notFound() {
    given()
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
        .when()
        .get("/greeting/5")
        .then()
        .statusCode(404);
  }

  @Test
  @Order(2)
  public void test_createGreeting() {
    JsonObject jsonObject = Json.createObjectBuilder()
        .add("value", "Buenas!!")
        .add("amount", 5)
        .build();
    given()
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .body(jsonObject.toString())
        .when()
        .post("/greeting/")
        .then()
        .statusCode(201);
  }

  @Test
  @Order(2)
  public void test_updateGreetingName_OK() {
    JsonObject jsonObject = Json.createObjectBuilder()
        .add("value", "How're you doing!")
        .build();
    given()
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .body(jsonObject.toString())
        .when()
        .put("/greeting/1")
        .then()
        .statusCode(200);

    given()
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
        .when()
        .get("/greeting/1")
        .then()
        .statusCode(200)
        .body("id", equalTo(1))
        .body("value", equalTo("How're you doing!"));
  }

  @Test
  @Order(1)
  public void test_updateGreeting_notFound() {
    JsonObject jsonObject = Json.createObjectBuilder()
        .add("value", "How're you doing!")
        .build();
    given()
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .body(jsonObject.toString())
        .when()
        .put("/greeting/11")
        .then()
        .statusCode(404);
  }

  @Test
  @Order(2)
  public void test_deleteGreeting_OK() {

    given()
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
        .when()
        .delete("/greeting/3")
        .then()
        .statusCode(204);

    given()
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
        .when()
        .get("/greeting/3")
        .then()
        .statusCode(404);
  }

  @Test
  @Order(1)
  public void test_deleteGreeting_notFound() {

    given()
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
        .when()
        .delete("/greeting/31")
        .then()
        .statusCode(404);

  }

}