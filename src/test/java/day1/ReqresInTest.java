package day1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ReqresInTest {

    @Test
    public void tes1_1_singleRequest() {

        String url = "https://reqres.in/api/users/2";

        Response response = RestAssured.get(url);

        int statusCode = response.statusCode();
        System.out.println("Status code " + statusCode);

        response.prettyPrint();

    }

    @Test
    public void test_2_getListOfUser() {
        // String url = "https://reqres.in/api/users?page=2";
        String url = "https://reqres.in/api/users";
        Map<String, Object> params = new HashMap<>();
        params.put("page", 2);

        Response response = RestAssured.given().params(params).get(url);

        response.prettyPrint();

        System.out.println("Status code " + response.statusCode());

    }


    @Test
    public void test_3_createUsers() {
        String url = "https://reqres.in/api/users";

        String requestbody = "{\n" +
                "    \"email\": \"cashwise1992@gmail.com\", \n" +
                "    \"password\" : \"apple12\"\n" +
                "}";

        Response response = RestAssured.given().
                contentType(ContentType.JSON). //
                        body(requestbody).post(url);

        System.out.println(response.statusCode());

        response.prettyPrint();
    }
}
