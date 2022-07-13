package library.RestAssured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import library.domain.Customer;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

public class BorrowRestAssuredTest {
  @BeforeClass
  public static void setup(){
    RestAssured.port = Integer.valueOf(9090);
    RestAssured.baseURI = "http://localhost";
    RestAssured.basePath = "";
  }

  @Test
  public void testGetOneCustomer() {
    // add the book to be fetched
    Customer customer = new Customer("EA001", "testUser@mail.com", "Harry", "Winks", "610114844", "1000 North 4th street", "Fairfield", "IOWA", 52557);
    given()
      .contentType("application/json")
      .body(customer)
      .when().post("/api/customers").then()
      .statusCode(201);
    // test getting the book
    given()
      .when()
      .get("/api/customers/EA001")
      .then()
      .contentType(ContentType.JSON)
      .and()
      .body("customerNumber",equalTo("EA001"))
      .body("email",equalTo("testUser@mail.com"));
    //cleanup
    given()
      .when()
      .delete("/api/customers/EA001");
  }

  @Test
  public void testDeleteCustomer() {
    // add the book to be deleted
    Customer customer = new Customer("EA001", "testUser@mail.com", "Harry", "Winks", "610114844", "1000 North 4th street", "Fairfield", "IOWA", 52557);
    given()
      .contentType("application/json")
      .body(customer)
      .when().post("/api/customers").then()
      .statusCode(201);

    given()
      .when()
      .delete("/api/customers/EA001");

    given()
      .when()
      .get("/api/customers/EA001")
      .then()
      .statusCode(404)
      .and()
      .body("message",equalTo("Invalid customer number"));
  }

  @Test
  public void testAddCustomer() {
    // add the customer
    Customer customer = new Customer("EA001", "testUser@mail.com", "Harry", "Winks", "610114844", "1000 North 4th street", "Fairfield", "IOWA", 52557);
    given()
      .contentType("application/json")
      .body(customer)
      .when().post("/api/customers").then()
      .statusCode(201);
    // get the customer and verify
    given()
      .when()
      .get("/api/customers/EA001")
      .then()
      .statusCode(200)
      .and()
      .body("customerNumber",equalTo("EA001"))
      .body("email",equalTo("testUser@mail.com"));
    //cleanup
    given()
      .when()
      .delete("/api/customers/EA001");
  }

  @Test
  public void testUpdateCustomer() {
    // add the customer
    Customer customer = new Customer("EA001", "testUser@mail.com", "Harry", "Winks", "610114844", "1000 North 4th street", "Fairfield", "IOWA", 52557);
    Customer updatedCustomer = new Customer("EA001", "testUser@mail.com", "Harry", "Links", "610114844", "1000 North 4th street", "Fairfield", "IOWA", 52557);

    given()
      .contentType("application/json")
      .body(customer)
      .when().post("/api/customers").then()
      .statusCode(201);
    //update customer
    given()
      .contentType("application/json")
      .body(updatedCustomer)
      .when().put("/api/customers/"+updatedCustomer.getCustomerNumber()).then()
      .statusCode(200);
    // get the customer and verify
    given()
      .when()
      .get("/api/customers/EA001")
      .then()
      .statusCode(200)
      .and()
      .body("customerNumber",equalTo("EA001"))
      .body("lastName",equalTo("Links"))
      .body("email",equalTo("testUser@mail.com"));
    //cleanup
    given()
      .when()
      .delete("/api/customers/EA001");
  }

  @Test
  public void testGetAllBooks() {
    // add the customers
    Customer customer1 = new Customer("EA001", "testUser@mail.com", "Harry", "Winks", "610114844", "1000 North 4th street", "Fairfield", "IOWA", 52557);
    Customer customer2 = new Customer("EA002", "testUser@mail.com", "Harry", "Links", "610114844", "1000 North 4th street", "Fairfield", "IOWA", 52557);

    given()
      .contentType("application/json")
      .body(customer1)
      .when().post("/api/customers").then()
      .statusCode(201);
    given()
      .contentType("application/json")
      .body(customer2)
      .when().post("/api/customers").then()
      .statusCode(201);

    // get all customers and verify
    given()
      .when()
      .get("/api/customers")
      .then()
      .statusCode(200)
      .and()
      .body("customers.customerNumber",hasItems("EA001", "EA002"));
    //cleanup
    given()
      .when()
      .delete("/api/customers/EA001");
    given()
      .when()
      .delete("/api/customers/EA002");
  }

  @Test
  public void testSearchBooks() {
    // add the customers
    Customer customer1 = new Customer("EA001", "testUser@mail.com", "Harry", "Winks", "610114844", "1000 North 4th street", "Fairfield", "IOWA", 52557);
    Customer customer2 = new Customer("EA002", "testUser@mail.com", "Harry", "Links", "610114844", "1000 North 4th street", "Fairfield", "IOWA", 52557);

    given()
      .contentType("application/json")
      .body(customer1)
      .when().post("/api/customers").then()
      .statusCode(201);
    given()
      .contentType("application/json")
      .body(customer2)
      .when().post("/api/customers").then()
      .statusCode(201);

    // get all customers from author and verify
    given()
      .when()
      .get("/api/customers/search/Harry")
      .then()
      .statusCode(200)
      .and()
      .body("customers.firstName",hasItems("Harry", "Harry"))
      .body("customers.customerNumber",hasItems("EA001", "EA002"));
    //cleanup
    given()
      .when()
      .delete("/api/customers/EA001");
    given()
      .when()
      .delete("/api/customers/EA002");
  }


}
