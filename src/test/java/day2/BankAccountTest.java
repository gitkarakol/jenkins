package day2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import utilities.CashwiseAuthorization;
import utilities.Config;

import java.util.HashMap;
import java.util.Map;

public class BankAccountTest {
    @Test
    public void test_1_token_generator() {
        //create new bank account
        //first thing get your token

        String url = "https://backend.cashwise.us/api/myaccount/auth/login";

        String requestBody = "{\n" +
                "    \"email\": \"cashwise1992@gmail.com\", \n" +
                "    \"password\" : \"apple12\"\n" +
                "}";

        Response response = RestAssured.given().contentType(ContentType.JSON).body(requestBody).post(url);
        System.out.println("Status code: " + response.statusCode());
        response.prettyPrint();

        String token = response.jsonPath().getString("jwt_token");
        System.out.println("My token " + token);
    }


    @Test
    public void test_2_create_new_bankAccount() {
        //create new bank account
        //first thing get your token
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjYXNod2lzZTE5OTJAZ21haWwuY29tIiwiZXhwIjoxNzA3MTgzOTI0LCJpYXQiOjE3MDY1NzkxMjR9.jHa1TcgI5oa1FS0OuG228cPUceyUx-s_NhtPtwpVATkJvQJWyCcMJxuPGOvi_4ajYA_u3zissx3HTAG2cGzQrA";
        String url = "https://backend.cashwise.us/api/myaccount/bankaccount";
//        String requestBody = "{\n" +
//                "    \"type_of_pay\": \"CASH\",\n" +
//                "    \"bank_account_name\": \"Post man Bank\",\n" +
//                "    \"description\": \"Fin Comp\",\n" +
//                "    \"balance\": 1300000\n" +
//                "  }";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("type_of_pay", "CASH");
        requestBody.put("bank_account_name", "Intellij BankTD");
        requestBody.put("description", "Financial companyTD");
        requestBody.put("balance", 905);
        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON).body(requestBody)
                .post(url);
        System.out.println(response.statusCode());


    }


    @Test
    public void test_3_getListOfBankAccount() {

        String token = CashwiseAuthorization.getToken();
        System.out.println(token);


        String url = Config.getProperty("baseUrl") + "/api/myaccount/bankaccount";
        System.out.println(url);
        Response response = RestAssured.given()
                .auth().oauth2(token)
                .get(url);
        System.out.println("My status code: " + response.statusCode());
        //   response.prettyPrint();

        int sizeOfBankAccounts = response.jsonPath().getList("").size();
        System.out.println(sizeOfBankAccounts);


        for (int i = 0; i < sizeOfBankAccounts; i++) {
            System.out.println("----------------");

            String id = response.jsonPath().getString("[" + i + "].id");
            String bankAccountName = response.jsonPath().getString("[" + i + "].bank_account_name");
            String description = response.jsonPath().getString("[" + i + "].description");
            String typeOfPay = response.jsonPath().getString("[" + i + "].type_of_pay");
            String balance = response.jsonPath().getString("[" + i + "].balance");


            System.out.println("Bank id: " + id);
            System.out.println("Bank account name: " + bankAccountName);
            System.out.println("Bank description: " + description);
            System.out.println("Type of pay: " + typeOfPay);
            System.out.println("Balance: " + balance);

            Assert.assertNotNull("ID is null ", id);
            Assert.assertNotNull("Bank account is null ", bankAccountName);
            Assert.assertNotNull("Description is null ", description);
            Assert.assertNotNull("Type is null ", typeOfPay);
            Assert.assertNotNull("Balance is null ", balance);

            System.out.println("------------------------------------------------------------");

        }
    }
    @Test
    public void test_4_getSingleBankAccount(){
        String url = Config.getProperty("baseUrl")+ "/api/myaccount/bankaccount/987";
        String token = CashwiseAuthorization.getToken();


        Response response = RestAssured.given()
                .auth().oauth2(  token  )
                .get( url );
        response.prettyPrint();

        String bankAcc = response.jsonPath().getString("bank_account_name" );




    }

    }
