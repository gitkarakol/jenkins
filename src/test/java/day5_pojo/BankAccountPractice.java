package day5_pojo;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import pojo.CustomResponse;
import pojo.RequestBody;
import utilities.CashwiseAuthorization;
import utilities.Config;

import static utilities.CashwiseAuthorization.getToken;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) //it will run in ascending order
public class BankAccountPractice {
    Faker faker = new Faker();
    static String bankId = "";

    @Test
    public void test_1_createNewBankAccount() throws JsonProcessingException {
        //getToken()

        //https://backend.cashwise.us   /api/myaccount/bankaccount/
        String url = Config.getProperty("baseUrl") + "/api/myaccount/bankaccount/";

        RequestBody requestBody = new RequestBody();
        requestBody.setType_of_pay("CASH");
        requestBody.setBank_account_name(faker.company().name() + " bank");
        requestBody.setDescription(faker.commerce().department() + " company");
        requestBody.setBalance(faker.number().numberBetween(20, 15000));

        Response response = RestAssured.given()
                .auth().oauth2(getToken())
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(url);


        System.out.println("My status code is not correct: " + response.statusCode());


        Assert.assertEquals(201, response.statusCode());


        System.out.println("---------------------------------response body------------");
        response.prettyPrint();

        /*
{
    "type_of_pay": "CASH",
    "bank_account_name": "Post man Bank",
    "description": "Fin Comp",
    "balance": 1300000
  }
 */


        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        bankId = customResponse.getId();
        System.out.println("My ID: " + bankId);


    }

    @Test
    public void test_2_getSingleBankAccount() throws JsonProcessingException {
        // https://backend.cashwise.us /api/myaccount/bankaccount/958
        // Step -1 GET request URL
        String url = Config.getProperty("baseUrl") + "/api/myaccount/bankaccount/" + bankId;

        // Step -2  Hit Get request with RestAssured
        Response response = RestAssured.given()
                .auth().oauth2(getToken())
                .get(url);

        // Step -3 print out status code
        System.out.println("My status code: " + response.statusCode());

        // Step -4 Assert status code
        Assert.assertEquals(200, response.statusCode());

        System.out.println("======================================");
        // Step -5  Create class ObjectMapper
        ObjectMapper mapper = new ObjectMapper();

        // Step - 6  create class CustomResponse and read data(fetch data)
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        System.out.println("=====TEST STARTED=================");
        System.out.println(customResponse.getId());
        System.out.println(customResponse.getBank_account_name());
        System.out.println(customResponse.getBalance());

        Assert.assertNotNull(customResponse.getId());
        Assert.assertNotNull(customResponse.getBank_account_name());
        Assert.assertNotNull(customResponse.getBalance());

        System.out.println("=====TEST PASSED=================");


        /**
         *    private String id;
         *     private String bank_account_name;
         *     private double balance;
         */


    }
    @Test
    public void test_3_getAllBankAccounts() throws JsonProcessingException {
        // https://backend.cashwise.us/api/myaccount/bankaccount

        String url = Config.getProperty("baseUrl") + "/api/myaccount/bankaccount";

        // Step -2  Hit Get request with RestAssured
        Response response = RestAssured.given()
                .auth().oauth2(getToken())
                .get(url);

        // Step -3 print out status code
        System.out.println("My status code: " + response.statusCode());

        // Step -4 Assert status code
        Assert.assertEquals(200, response.statusCode());

        System.out.println("=====DESERIALIZATION=================================");
        // Step-5 create ObjectMapper class
        ObjectMapper mapper = new ObjectMapper();
        // Step-5  Since we are getting list of banks we have declared Array of CustomResponse
        CustomResponse[] customResponses = mapper.readValue(response.asString(), CustomResponse[].class);

        // Step-6 Get size of Array
        int sizeOfBankAccounts = customResponses.length;

        // Step-6 Print out All bank accounts and Assert them
        for (int i = 0; i < sizeOfBankAccounts; i++) {

            System.out.println("Bank ID: " + customResponses[i].getId());
            Assert.assertNotNull(customResponses[i].getId());

            System.out.println(customResponses[i].getBank_account_name());
            Assert.assertNotNull(customResponses[i].getBank_account_name());

        }
    }

    @Test
    public void test_4_deleteRequest() throws JsonProcessingException {
        // https://backend.cashwise.us /api/myaccount/bankaccount/958
        // Step -1 GET request URL
        String url = Config.getProperty("baseUrl") + "/api/myaccount/bankaccount/" + bankId;

        // Step -2  Hit Get request with RestAssured
        Response response = RestAssured.given()
                .auth().oauth2(getToken())
                .delete(url);

        // Step -3 print out status code
        System.out.println("My status code: " + response.statusCode());

        // Step -4 Assert status code
        Assert.assertEquals(200, response.statusCode());

        System.out.println("======================================");

    }
}