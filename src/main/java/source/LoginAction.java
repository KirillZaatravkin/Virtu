package source;

/**
 * Created by кирюха on 21.11.2017.
 */


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
;

import static jdk.nashorn.internal.objects.NativeString.toUpperCase;


public class LoginAction  {
    private Connection connection;

    public String FlagLogin (String login, String password) {

        connection = DbConnect.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement("select *, count (*) as kol from users where login=? and password=? group by id");
            statement.setString(1, login);
            statement.setString(2, password);

            rs = statement.executeQuery();

            while (rs.next()) {

                String Login=rs.getString("login");
                String Groups =rs.getString("groups");
                int flag=rs.getInt("kol");
                if(flag ==1)
                {System.out.print(Groups);
                    System.out.print(Groups);
                    return Groups;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
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
        return "";
    }

   }