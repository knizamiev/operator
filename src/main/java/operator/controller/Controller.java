package operator.controller;
import operator.model.Phone;
import operator.util.ParcerCsv;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;

@RestController
public class Controller {
    private ParcerCsv parcerCsv = new ParcerCsv();
    private Pattern pattern = Pattern.compile("^\\d{11}$"); //only digit, count 11
    private List<Phone> listPhones = parcerCsv.reader();

    @RequestMapping("/operator")
    @ResponseBody
    public ResponseEntity getOperatot (@RequestParam("phone") String phone) {
        Matcher matcher = pattern.matcher(phone);
        boolean flag = matcher.find();
        if (flag){
            char[] chars = phone.toCharArray();
            String code = "" + chars[1] + chars[2] + chars[3];
            String other = "";

            for (int i = 4; i < chars.length; i++){
                other = other + "" +  chars[i];
            }

            String finalOther = other;
            List<Phone> result = listPhones.stream()
            .filter(line -> line.getCode() == Short.parseShort(code))
            .collect(Collectors.toList()).stream().filter(line -> Integer.parseInt(finalOther) >= line.getFrom()
            && Integer.parseInt(finalOther) <= line.getBefore()).collect(Collectors.toList());

            System.out.println(result.size());

            Phone body = result.get(0);

            //result.get(0).getOperator() || result.get
            return ResponseEntity.ok(body);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not valid phone number");
        }
    }
}
