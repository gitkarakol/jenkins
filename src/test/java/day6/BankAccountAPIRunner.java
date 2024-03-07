package day6;

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
import utilities.APIRunner;
import utilities.Config;

import static utilities.CashwiseAuthorization.getToken;

public class BankAccountAPIRunner {

    @Test
    public void tes1_getListOfBankAccount() throws JsonProcessingException {
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
    public  void test2_APIRUNNER() {
        String path = "/api/myaccount/bankaccount";
        APIRunner.runGET(path);
        CustomResponse[] customResponses = APIRunner.getCustomResponseArray();

        int sizeOfArray = customResponses.length;


        for(int i = 0; i <sizeOfArray; i++) {
            System.out.println("id" + customResponses[i].getId());
            Assert.assertNotNull(customResponses[i].getId());

            System.out.println("bank accout name" + customResponses[i].getBank_account_name());
            Assert.assertNotNull(customResponses[i].getBank_account_name());
        }
    }
    @Test
    public void test_3_createNewBankAccount() throws JsonProcessingException {
        Faker faker = new Faker();
        // https://backend.cashwise.us   /api/myaccount/bankaccount    // STEP -==> set up your URL
        String url = Config.getProperty("baseUrl") + "/api/myaccount/bankaccount";


        RequestBody requestBody = new RequestBody();
        requestBody.setType_of_pay("CASH");
        requestBody.setBank_account_name(  faker.company().name()+ " bank"  );
        requestBody.setDescription( faker.commerce().department()+ " company" );
        requestBody.setBalance( faker.number().numberBetween(200, 15000)  );

        Response response = RestAssured.given()
                .auth().oauth2(   getToken()  )
                .contentType( ContentType.JSON )
                .body( requestBody )
                .post(url );

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(  response.asString(), CustomResponse.class  );

        String bankId = customResponse.getId();
        response.prettyPrint();

    }


}
