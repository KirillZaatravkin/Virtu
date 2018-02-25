package source.system.dao;


import org.springframework.stereotype.Repository;
import source.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by кирюха on 13.02.2018.
 */
@Repository
public class CurrentDate {

    public static String CurYear ()
    {
        Date d = new Date();
        SimpleDateFormat format2 = new SimpleDateFormat(" YYYY год ");
        String dd=format2.format(d);
        return dd;
    }

    public static String CurMonth ()
    {
        Date d = new Date();
        SimpleDateFormat format2 = new SimpleDateFormat("Месяц № MM ");
        String dd=format2.format(d);
        return dd;
    }

    public static String LastLoad ()
    {   String dd = null;
        Connection connection = DbConnect.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = connection.prepareStatement("select max(datecreate) as date_L from ap_ovd  limit 1");
            rs = statement.executeQuery();
            while (rs.next()) {

                 Date d = rs.getDate("date_L");
                SimpleDateFormat format2 = new SimpleDateFormat("dd. MM . YYYY ");
                dd=format2.format(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        DbConnect.close(connection);
        return dd;
    }
}

