package source.system.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class BookService {


    public ArrayList<String> findTel(HashMap<String, ArrayList<String>> bookMap, String person)
    {
         ArrayList<String> result=bookMap.get(person);
         return result;
    }

    public int addTestBook(HashMap<String, ArrayList<String>> bookMap) {

        String k = "Иванов И.И.";  ;
        ArrayList<String> v = new ArrayList<>();
        v.add("+8 800 2000 500");
        v.add("+8 800 200 600");
        bookMap.put(k, v);

        String k1 = "Петров П.П.";
        ArrayList<String> v1 = new ArrayList<>();
        v1.add("+8 800 2000 7000");
        bookMap.put(k1, v1);

        String k2 = "Сидоров С.С.";
        ArrayList<String> v2 = new ArrayList<>();
        v2.add("+8 800 2000 800");
        v2.add("+8 800 2000 900");
        v2.add("+8 800 2000 000");

        bookMap.put(k2, v2);
        return 1;
    }


}
