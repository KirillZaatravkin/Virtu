package source.system.service;

import org.springframework.stereotype.Service;
import source.system.model.BookModel;

import java.util.ArrayList;

/**
 * Created by кирюха on 12.07.2019.
 */
@Service
public class BookService {

    public ArrayList<String> findTel(BookModel bookModel, String person)
    {
         ArrayList<String> result=bookModel.getBookMap().get(person);
         return result;
    }
}
