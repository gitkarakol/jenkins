package day5_pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import utilities.APIRunner;

public class BankAccountWithApiRunner {

@Test
    public  void test1_getSingleBankAccount() throws JsonProcessingException {
    //1290
    //// https://backend.cashwise.us         /api/myaccount/bankaccount/1208

    String path = " /api/myaccount/bankaccount/1290";
    APIRunner.runGET(path);
    String bankAccountName = APIRunner.getCustomResponse().getBank_account_name();

    System.out.println("bank account name " + bankAccountName);

}
}
