package operatorApplication.restservice;
import operatorApplication.model.Phone;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class GreetingController {

    private ParcerCsv parcerCsv = new ParcerCsv();

    //only digit, count 11
    private Pattern pattern = Pattern.compile("^\\d{11}$");
    private List<Phone> listPhones = parcerCsv.reader();

    @RequestMapping("/operator")
    @ResponseBody
    public ResponseEntity sendViaResponseEntity (@RequestParam("phone") String phone) {

        Matcher matcher = pattern.matcher(phone);
        boolean flag = matcher.find();
        if (flag){
            char[] chars = phone.toCharArray();
            String code = "" + chars[1] + chars[2] + chars[3];
            String other = "";

            for (int i = 4; i < chars.length; i++){
                other = other + "" +  chars[i];
            }

            List<Phone> result = new ArrayList<>();
            for (Phone phone1: listPhones) {
                if (phone1.getCode() == Short.parseShort(code)){
                    result.add(phone1);
                }
            }

            Phone responseBody = new Phone();
            for (Phone resultPhone : result){
                if (Integer.parseInt(other) >= resultPhone.getFrom() && Integer.parseInt(other) <= resultPhone.getBefore()){
                    responseBody = resultPhone;
                }
            }
            //responseBody.getOperator() || responseBody
            return ResponseEntity.ok(responseBody.getOperator());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not valid phone number");
        }
    }
}
