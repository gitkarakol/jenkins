package day5_pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.CustomResponse;
import pojo.RequestBody;
import utilities.Config;

import static utilities.CashwiseAuthorization.getToken;

public class Sellers_Practice {
    Faker faker = new Faker();
    static int seller_id = 0;

    @Test
    public void test1_createNewSeller() throws JsonProcessingException {
        //https://backend.cashwise.us/api/myaccount/sellers
        String url = Config.getProperty("baseUrl") + "/api/myaccount/sellers/";
        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name(faker.company().name());
        requestBody.setSeller_name(faker.commerce().productName());
        requestBody.setEmail(faker.internet().emailAddress());
        requestBody.setPhone_number(faker.phoneNumber().cellPhone());
        /*
        {
    "company_name": "CIBC",
    "seller_name": "NAME OF THE SELLER",
    "email": "HELLO222@GMAIL.COM",
    "phone_number": "43722505000",
    "address": "28 BYNG"
  }
         */
        Response response = RestAssured.given()
                .auth().oauth2(getToken())
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(url);


        System.out.println("My status code is not correct: " + response.statusCode());


        Assert.assertEquals(201, response.statusCode());


        System.out.println("---------------------------------response body------------");
        response.prettyPrint();

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        seller_id = customResponse.getSeller_id();
        System.out.println("My ID: " + seller_id);
    }

    @Test
    public void test_2_getSingleSeller() throws JsonProcessingException {

        String url = Config.getProperty("baseUrl") + "/api/myaccount/sellers/" + seller_id;

        Response response = RestAssured.given()
                .auth().oauth2(getToken())
                .get(url);

        System.out.println("My status code: " + response.statusCode());


        Assert.assertEquals(200, response.statusCode());


        ObjectMapper mapper = new ObjectMapper();


        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);


        System.out.println(customResponse.getSeller_id());
        System.out.println(customResponse.getSeller_name());
        System.out.println(customResponse.getEmail());
        System.out.println(customResponse.getAddress());


        Assert.assertNotNull(customResponse.getSeller_id());
        Assert.assertNotNull(customResponse.getSeller_name());



    }

    @Test

    public void test3_getAllbankAccounts() throws JsonProcessingException {
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


        //  customResponse[0].getId()


        //  response.prettyPrint();


    }

    @Test
    public void test_3_getListOfBanks() throws JsonProcessingException {

        String url = Config.getProperty("baseUrl") + "/api/myaccount/sellers/all";

        Response response = RestAssured.given().auth().oauth2(getToken()).get(url);
        System.out.println("my status code:"+response.statusCode());

        Assert.assertEquals(200, response.statusCode());
        System.out.println("========deserialization==========");

        ObjectMapper mapper =new ObjectMapper();

        CustomResponse [] customResponses = mapper.readValue(response.asString(), CustomResponse[].class);

        int sizeOfSellers = customResponses.length;


        for (int i = 0; i < sizeOfSellers; i++) {
            System.out.println("Sellers: "+ customResponses[i].getSeller_id());
            Assert.assertNotNull(customResponses[i].getSeller_name());
            Assert.assertNotNull(customResponses[i].getSeller_id());

        }


    }

    }



