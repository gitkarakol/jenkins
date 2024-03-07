package pojo;


import lombok.Data;

@Data //Automatically will generate getter and setter method for me
public class RequestBody {

    private String email;
    private String password;


    private String category_title;
    private String category_description;
    private boolean flag;


    //bank account request body varibles
    private String type_of_pay;
    private String bank_account_name;
    private String description;
    private double balance;


    // Seller controller RequestBody variables
    private String company_name;
    private String seller_name;
    //private String email;
    private String phone_number;
    private String address;

    /*
    {
    "type_of_pay": "CASH",
    "bank_account_name": "Post man Bank",
    "description": "Fin Comp",
    "balance": 1300000
  }
     */

    //product Request body variable
    private String product_title;
    private double product_price ;
    private int service_type_id;
    private int category_id;
    private String product_description;


}
