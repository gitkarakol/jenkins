package day6;

import org.junit.jupiter.api.Test;
import pojo.CustomResponse;
import utilities.APIRunner;

public class SellerAPIRunner {
    @Test
    public void getListOfSellers(){
        String path = "/api/myaccount/sellers/all";
        APIRunner.runGET(path);
        CustomResponse[] customResponse = APIRunner.getCustomResponseArray();
        for (CustomResponse response: customResponse) {
            System.out.println(response.getSeller_id());
            System.out.println(response.getSeller_name());

        }
    }
}
