package source;

import source.system.model.*;
import source.system.model.ApOVDStat;
import source.system.model.ApOVD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeString.toUpperCase;

/**
 * Created by кирюха on 14.11.2017.
 */
public class Filter {
    private Connection connection;

      public List<ApOVD> EchoFaceOVD(String lastName, String firstName, String middleName, Date birthDay)
      {
        lastName = toUpperCase(lastName);
        firstName = toUpperCase(firstName);
        middleName = toUpperCase(middleName);
        connection = DbConnect.getConnection();
        List<ApOVD> apOVDs = new ArrayList<ApOVD>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement("select * from ap_ovd where lastname=? and firstname=? and middlename=? and birthday=? order by datep DESC ");
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
                apOVD.setPasportN(rs.getString("pasportn"));
                apOVD.setPasportS(rs.getString("pasports"));
                apOVD.setFacktAddr(rs.getString("facktaddr"));
                apOVD.setResAddr(rs.getString("resaddr"));
                apOVD.setCact(rs.getString("cact"));
                apOVD.setArticle(rs.getString("article"));
                apOVD.setBirthday(rs.getDate("birthday"));
                apOVD.setDateP(rs.getDate("datep"));
                apOVD.setDateZak(rs.getDate("datezak"));
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

    public List<ApGIBDD> EchoFaceGIBDD(String lastName, String firstName, String middleName, Date birthDay) {
        lastName = toUpperCase(lastName);
        firstName = toUpperCase(firstName);
        middleName = toUpperCase(middleName);
        connection = DbConnect.getConnection();
        List<ApGIBDD> ListApGIBDD = new ArrayList<ApGIBDD>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement("select * from ap_gibdd where lastname=? and firstname=? and middlename=? and birthday=? order by datep DESC ");
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.setString(3, middleName);
            statement.setDate(4, birthDay);
            rs = statement.executeQuery();
            while (rs.next()) {
                ApGIBDD ApGIBDD = new ApGIBDD();
                ApGIBDD.setLastName(toUpperCase(rs.getString("lastname")));
                ApGIBDD.setFirstName(toUpperCase(rs.getString("firstname")));
                ApGIBDD.setMiddleName(toUpperCase(rs.getString("middlename")));
                ApGIBDD.setFacktAddr(rs.getString("facktaddr"));
                ApGIBDD.setCact(rs.getString("cact"));
                ApGIBDD.setVodUd(rs.getString("vodud"));
                ApGIBDD.setProtokolN(rs.getString("protokoln"));
                ApGIBDD.setNakaz(rs.getString("nakaz"));
                ApGIBDD.setArticle(rs.getString("article"));
                ApGIBDD.setBirthday(rs.getDate("birthday"));
                ApGIBDD.setDateP(rs.getDate("datep"));
                ApGIBDD.setDatePost(rs.getDate("datepost"));
                ApGIBDD.setDateZak(rs.getDate("datezak"));
                ApGIBDD.setDaterCreate(rs.getDate("datecreate"));
                ListApGIBDD.add(ApGIBDD);
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
        return ListApGIBDD;
    }

    public List<ApPrist> EchoFacePrist(String lastName, String firstName, String middleName, Date birthDay) {
        lastName = toUpperCase(lastName);
        firstName = toUpperCase(firstName);
        middleName = toUpperCase(middleName);
        connection = DbConnect.getConnection();
        List<ApPrist> ListApPrist = new ArrayList<ApPrist>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement("select * from ap_prist where lastname=? and firstname=? and middlename=? and birthday=? order by datep DESC ");
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.setString(3, middleName);
            statement.setDate(4, birthDay);
            rs = statement.executeQuery();
            while (rs.next()) {
                ApPrist Apprist = new ApPrist();
                Apprist.setLastName(toUpperCase(rs.getString("lastname")));
                Apprist.setFirstName(toUpperCase(rs.getString("firstname")));
                Apprist.setMiddleName(toUpperCase(rs.getString("middlename")));
                Apprist.setFacktAddr(rs.getString("facktaddr"));
                Apprist.setCact(rs.getString("cact"));
                Apprist.setArticle(rs.getString("article"));
                Apprist.setBirthday(rs.getDate("birthday"));
                Apprist.setDateP(rs.getDate("datep"));
                Apprist.setDateZak(rs.getDate("datezak"));
                Apprist.setPasportN(rs.getString("pasportn"));
                Apprist.setPasportS(rs.getString("pasports"));
                Apprist.setNakaz(rs.getString("nakaz"));
                Apprist.setDaterCreate(rs.getDate("datecreate"));
                ListApPrist.add(Apprist);
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
        return ListApPrist;
    }

    public List<ApOVDStat> FindFaceOVD(String lastName, String firstName, String middleName, Date birthDay) {
        lastName = toUpperCase(lastName);
        firstName = toUpperCase(firstName);
        middleName = toUpperCase(middleName);
        connection = DbConnect.getConnection();
        List<ApOVDStat>  apOVDstat=new ArrayList<ApOVDStat>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            if (birthDay!=null) {
                statement = connection.prepareStatement("select  count(id), lastname, firstname, middlename,birthday from ap_ovd where lastname like ? and firstname like ? and middlename like ? and birthday=? group by pasports,pasportn,lastname, firstname, middlename,birthday");
                statement.setString(1, lastName);
                statement.setString(2, firstName);
                statement.setString(3, middleName);
                statement.setDate(4, birthDay);
            }
            else {
                statement = connection.prepareStatement("select count(id), lastname, firstname, middlename,birthday  from ap_ovd where lastname like ? and firstname like ? and middlename like ? group by pasports,pasportn,lastname, firstname, middlename,birthday");
                statement.setString(1, lastName);
                statement.setString(2, firstName);
                statement.setString(3, middleName);


            }
            rs = statement.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("lastname"));
                ApOVDStat apOVD = new ApOVDStat();
                apOVD.setLastName(toUpperCase(rs.getString("lastname")));
                apOVD.setFirstName(toUpperCase(rs.getString("firstname")));
                apOVD.setMiddleName(toUpperCase(rs.getString("middlename")));
                apOVD.setBirthday(rs.getDate("birthday"));

                apOVDstat.add(apOVD);

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
        return apOVDstat;
    }
    public List<ApGIBDDStat> FindFaceGIBDD(String lastName, String firstName, String middleName, Date birthDay) {
        lastName = toUpperCase(lastName);
        firstName = toUpperCase(firstName);
        middleName = toUpperCase(middleName);
        connection = DbConnect.getConnection();
        List<ApGIBDDStat> apGIBDDStats=new ArrayList<ApGIBDDStat>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            if (birthDay!=null) {
                statement = connection.prepareStatement("select  count(id), lastname, firstname, middlename,birthday from ap_gibdd where lastname like ? and firstname like ? and middlename like ? and birthday=? group by lastname, firstname, middlename,birthday");
                statement.setString(1, lastName);
                statement.setString(2, firstName);
                statement.setString(3, middleName);
                statement.setDate(4, birthDay);
            }
            else {
                statement = connection.prepareStatement("select count(id), lastname, firstname, middlename,birthday  from ap_gibdd where lastname like ? and firstname like ? and middlename like ? group by  lastname, firstname, middlename,birthday");
                statement.setString(1, lastName);
                statement.setString(2, firstName);
                statement.setString(3, middleName);


            }
            rs = statement.executeQuery();

            while (rs.next()) {

                ApGIBDDStat apGIBDD = new ApGIBDDStat();
                apGIBDD.setLastName(toUpperCase(rs.getString("lastname")));
                apGIBDD.setFirstName(toUpperCase(rs.getString("firstname")));
                apGIBDD.setMiddleName(toUpperCase(rs.getString("middlename")));
                apGIBDD.setBirthday(rs.getDate("birthday"));

                apGIBDDStats.add(apGIBDD);

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
        return apGIBDDStats;
    }

    public List<ApPristStat> FindFacePrist(String lastName, String firstName, String middleName, Date birthDay) {
        lastName = toUpperCase(lastName);
        firstName = toUpperCase(firstName);
        middleName = toUpperCase(middleName);
        connection = DbConnect.getConnection();
        List<ApPristStat> apPristStats=new ArrayList<ApPristStat>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            if (birthDay!=null) {
                statement = connection.prepareStatement("select  count(id), lastname, firstname, middlename,birthday from ap_prist where lastname like ? and firstname like ? and middlename like ? and birthday=? group by lastname, firstname, middlename,birthday");
                statement.setString(1, lastName);
                statement.setString(2, firstName);
                statement.setString(3, middleName);
                statement.setDate(4, birthDay);
            }
            else {
                statement = connection.prepareStatement("select count(id), lastname, firstname, middlename,birthday  from ap_prist where lastname like ? and firstname like ? and middlename like ? group by  lastname, firstname, middlename,birthday");
                statement.setString(1, lastName);
                statement.setString(2, firstName);
                statement.setString(3, middleName);


            }
            rs = statement.executeQuery();

            while (rs.next()) {

                ApPristStat apPrist = new ApPristStat();
                apPrist.setLastName(toUpperCase(rs.getString("lastname")));
                apPrist.setFirstName(toUpperCase(rs.getString("firstname")));
                apPrist.setMiddleName(toUpperCase(rs.getString("middlename")));
                apPrist.setBirthday(rs.getDate("birthday"));

                apPristStats.add(apPrist);

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
        return apPristStats;
    }
}
