package operator.util;
import operator.model.Phone;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.groupingBy;

@Service
public class ParcerCsv {
    public Map<Integer, List<Phone>> reader()  {
        BufferedReader reader = null;
        List<Phone> list = new ArrayList<>();
        Map<Integer, List<Phone>> map;
        try {
            String line = "";
            reader = new BufferedReader(new FileReader("DEF-9xx.csv"));
            reader.readLine();

            while ((line = reader.readLine()) != null){
                String[] fields = line.split(";");
                if (fields.length > 0){
                    Phone phone = new Phone();
                    phone.setCode(Integer.parseInt(fields[0]));
                    phone.setFrom(Integer.parseInt(fields[1]));
                    phone.setBefore(Integer.parseInt(fields[2]));
                    phone.setCount(Integer.parseInt(fields[3]));
                    phone.setOperator(fields[4]);
                    phone.setRegion(fields[5]);
                    list.add(phone);
                }
            }

        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        map = list.stream().collect(groupingBy(Phone::getCode));
        return map;
    }
}
