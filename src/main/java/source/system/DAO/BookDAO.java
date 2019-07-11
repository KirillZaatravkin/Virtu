package source.system.DAO;

import org.springframework.stereotype.Repository;
import source.system.model.BookModel;

import java.util.ArrayList;

/**
 * Created by кирюха on 12.07.2019.
 */
@Repository
public class BookDAO {
    public void addTestBook (BookModel bookModel)
    {
        String k="Иванов И.И.";
        ArrayList<String> v= new ArrayList<>();
        v.add("+8 800 2000 500");
        v.add("+8 800 200 600");
        bookModel.getBookMap().put(k,v);

        String k1="Петров П.П.";
        ArrayList<String> v1= new ArrayList<>();
        v1.add("+8 800 2000 7000");
        bookModel.getBookMap().put(k1,v1);

        String k2="Сидоров С.С.";
        ArrayList<String> v2= new ArrayList<>();
        v2.add("+8 800 2000 800");
        v2.add("+8 800 2000 900");
        v2.add("+8 800 2000 000");
        bookModel.getBookMap().put(k2,v2);
    }

}
