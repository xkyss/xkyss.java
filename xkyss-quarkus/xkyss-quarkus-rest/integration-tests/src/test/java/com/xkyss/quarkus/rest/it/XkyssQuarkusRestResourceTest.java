package com.xkyss.quarkus.rest.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class XkyssQuarkusRestResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/xkyss-quarkus-rest")
                .then()
                .statusCode(200)
                .body(is("Hello xkyss-quarkus-rest"));
    }
}
