package source;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeString.toUpperCase;

/**
 * Created by кирюха on 14.11.2017.
 */
public class Filter {
    private Connection connection;

    public List<ApOVD> FilterApOVD(String lastName, String firstName, String middleName, Date birthDay) {
        lastName = toUpperCase(lastName);
        firstName = toUpperCase(firstName);
        middleName = toUpperCase(middleName);
        connection = DbConnect.getConnection();
        List<ApOVD> apOVDs = new ArrayList<ApOVD>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement("select * from ap_ovd where lastname=? and firstname=? and middlename=? and birthday=?");
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.setString(3, middleName);
            statement.setDate(4, birthDay);
            rs = statement.executeQuery();
            while (rs.next()) {
                ApOVD apOVD = new ApOVD();
                apOVD.setLastName(toUpperCase(rs.getString("lastname")));
                apOVD.setFirstName(toUpperCase(rs.getString("firstname")));
                apOVD.setMiddleName(toUpperCase(rs.getString("middlename")));
                apOVD.setFacktAddr(rs.getString("facktaddr"));
                apOVD.setResAddr(rs.getString("resaddr"));
                apOVD.setArticle(rs.getString("article"));
                apOVD.setBirthday(rs.getDate("birthday"));
                apOVD.setDateP(rs.getDate("datep"));
                apOVD.setDaterCreate(rs.getDate("datecreate"));
                apOVDs.add(apOVD);

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
        return apOVDs;
    }


}
