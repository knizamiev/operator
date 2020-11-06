package operator.controller;
import operator.model.Phone;
import operator.util.ParcerCsv;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
public class Controller {
    private ParcerCsv parcerCsv = new ParcerCsv();
    private Pattern pattern = Pattern.compile("^\\d{11}$"); //only digit, count 11
    private Map <Integer, List<Phone>> listPhones = parcerCsv.reader();

    @RequestMapping("/operator")
    @ResponseBody
    public ResponseEntity getOperatot (@RequestParam("phone") String phone) {
        Matcher matcher = pattern.matcher(phone);
        boolean flag = matcher.find();
        if (flag){

            String code = phone.substring(1,4);
            String other = phone.substring(4);

            List<Phone> result = listPhones.get(Integer.parseInt(code)).stream()
            .filter(line -> Integer.parseInt(other) >= line.getFrom()
            && Integer.parseInt(other) <= line.getBefore()).collect(Collectors.toList());

            Phone body = result.get(0);

            //body.getOperator() || body
            return ResponseEntity.ok(body);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not valid phone number");
        }
    }
}
