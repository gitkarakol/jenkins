package utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.Getter;
import pojo.CustomResponse;

import static utilities.CashwiseAuthorization.getToken;

public class APIRunner {
    private static CustomResponse customResponse;
    private static CustomResponse[] customResponseArray;


    /** Day_5 APIRunner (Description about this class)
     * APIRunner class contains custom methods for CRUD commands
     * With help of this class we can focus on test logic, instead of automation
     * script
     */

    public static CustomResponse runGET( String path  ){
        // step - 1
        String  url =Config.getProperty("baseUrl") + path;
        // step - 2
        Response response = RestAssured.given()
                .auth().oauth2(   getToken()    )
                .get( url );

        // step - 3
        ObjectMapper mapper = new ObjectMapper();

        // step -4
        try {
            customResponse = mapper.readValue(response.asString(), CustomResponse.class ) ;

        } catch (JsonProcessingException e) {

            System.out.println( " This is a list response ");
            try {
                customResponse = mapper.readValue(response.asString(), CustomResponse.class);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        }

        return customResponse;

    }


    // I can read value of my private variable with help of getter method
    public static CustomResponse getCustomResponse(){
        return customResponse;
    }


    public static CustomResponse[] getCustomResponseArray(){
        return customResponseArray;
    }

//    public static CustomResponse getCustomResponse() {
//        return customResponse;
//    }
    }
