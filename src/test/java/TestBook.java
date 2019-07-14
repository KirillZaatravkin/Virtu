import org.junit.Test;
import source.system.service.BookService;

import java.util.ArrayList;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;


public class TestBook {

    @Test(timeout = 1000)
    public void testBook() {
        BookService  bookService=new BookService();
        ArrayList<String> telArray=new ArrayList<>();
        HashMap<String, ArrayList<String>> book=new HashMap<>();
        int i=bookService.addTestBook(book);
        telArray.add("+8 800 2000 7000");
        assertEquals(1,i);
        assertEquals(telArray,bookService.findTel(book, "Петров П.П."));
    }
}
