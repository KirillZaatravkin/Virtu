package source;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by кирюха on 13.02.2018.
 */
public class CurrentDate {

    public static void CurDate ()
    {
        Date d = new Date();
        SimpleDateFormat format2 = new SimpleDateFormat("Месяц № MM ");
        String dd=format2.format(d);
        System.out.print(dd);
    }
}

