package source.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import source.system.DAO.BookDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by кирюха on 12.07.2019.
 */
@Service
public class BookService {
    @Autowired
    private BookDAO bookDAO;

    public ArrayList<String> findTel(HashMap<String, ArrayList<String>> bookMap, String person)
    {
         ArrayList<String> result=bookMap.get(person);
         return result;
    }

    public int addTestBook (HashMap<String, ArrayList<String>> bookMap){return bookDAO.addTestBook(bookMap);}


}
