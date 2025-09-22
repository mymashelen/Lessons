import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class PostmanEchoTest {
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://postman-echo.com";
    }

    @Test
    public void testGetRequest() {
        Response response = given()
                .when()
                .get("/get?foo1=bar1&foo2=bar2")
                .then()
                .extract()
                .response();

        assertEquals(200, response.getStatusCode());

        assertEquals("https://postman-echo.com/get?foo1=bar1&foo2=bar2",
                response.jsonPath().getString("url"));

        assertEquals("bar1", response.jsonPath().getString("args.foo1"));
        assertEquals("bar2", response.jsonPath().getString("args.foo2"));

        assertNotNull(response.jsonPath().getString("headers"));
        assertNotNull(response.jsonPath().getString("headers.host"));
    }

    @Test
    public void testPostRawText() {
        String requestBody = "This is raw text data";

        Response response = given()
                .header("Content-Type", "text/plain")
                .body(requestBody)
                .when()
                .post("/post")
                .then()
                .extract()
                .response();

        assertEquals(200, response.getStatusCode());

        assertEquals("https://postman-echo.com/post", response.jsonPath().getString("url"));
        assertEquals("This is raw text data", response.jsonPath().getString("data"));
    }

    @Test
    public void testPostFormData() {
        Response response = given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("key1", "value1")
                .formParam("key2", "value2")
                .when()
                .post("/post")
                .then()
                .extract()
                .response();

        assertEquals(200, response.getStatusCode());

        assertEquals("https://postman-echo.com/post", response.jsonPath().getString("url"));
        assertEquals("value1", response.jsonPath().getString("form.key1"));
        assertEquals("value2", response.jsonPath().getString("form.key2"));
    }

    @Test
    public void testPutRequest() {
        String requestBody = "{\"name\":\"John\", \"age\":\"30\"}";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .put("/put")
                .then()
                .extract()
                .response();

        assertEquals(200, response.getStatusCode());

        assertEquals("https://postman-echo.com/put", response.jsonPath().getString("url"));
        assertEquals("John", response.jsonPath().getString("json.name"));
        assertEquals("30", response.jsonPath().getString("json.age"));
    }

    @Test
    public void testPatchRequest() {
        String requestBody = "{\"update\":\"partial data\"}";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .patch("/patch")
                .then()
                .extract()
                .response();

        assertEquals(200, response.getStatusCode());

        assertEquals("https://postman-echo.com/patch", response.jsonPath().getString("url"));
        assertEquals("partial data", response.jsonPath().getString("json.update"));
    }

    @Test
    public void testDeleteRequest() {
        Response response = given()
                .when()
                .delete("/delete")
                .then()
                .extract()
                .response();

        assertEquals(200, response.getStatusCode());

        assertEquals("https://postman-echo.com/delete", response.jsonPath().getString("url"));

        assertNull(response.jsonPath().get("json"));
    }
}
