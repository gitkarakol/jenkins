package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomResponse {

    private int category_id;

    private String category_title;
    private String category_description;


    //BankAccount Response body variables
    private  String id;

    private  String bank_account_name;
    private double balance;




    // Sellers RESPONSE body variables
    private int  seller_id;
    private String seller_name;
    private String email;
    private String address;


    /**
     * "product_id": 719,
     *     "product_title": "Apple magic mouse",
     *     "product_price": 350.0,
     */
    private int product_id;
    private String product_title;
    private double product_price;
    private String product_description;






}
