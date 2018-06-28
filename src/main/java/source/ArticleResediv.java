package source;

import source.system.model.ApGIBDDStat;
import source.system.model.ArticleStat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static jdk.nashorn.internal.objects.NativeString.toUpperCase;

/**
 * Created by кирюха on 13.06.2018.
 */
public class ArticleResediv {

    private Connection connection;

    public List<ArticleStat> FilterStat(String article, java.sql.Date d1, java.sql.Date d2, String sorts, String interval, String cact, String regionMask, int volume) {

        long curr = System.currentTimeMillis();

        connection = DbConnect.getConnection();

        List<ArticleStat> stat = new ArrayList<ArticleStat>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {

            if (article.equals("5.35.1")) {
                statement = connection.prepareStatement("select    MAX(facktaddr) as facktaddr, count(id) as kol, lastname, firstname, birthday, middlename,  max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from st_5_35_1 where  facktaddr LIKE ? and datep BETWEEN  ? and ? group by lastname, firstname, middlename, birthday   order by kol DESC ;");
            } else if (article.equals("7.27")) {
               statement = connection.prepareStatement("select    MAX(facktaddr) as facktaddr, count(id) as kol, lastname, firstname, birthday, middlename, max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from st_7_27  where facktaddr LIKE ? and datep BETWEEN  ? and ?  group by lastname, firstname, middlename, birthday  order by kol DESC ;");
            } else if (article.equals("14.16")) {
                statement = connection.prepareStatement("select   MAX(facktaddr) as facktaddr, count(id) as kol, lastname, firstname, birthday, middlename, max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from st_14_16 where facktaddr LIKE ? and datep BETWEEN  ? and ?  group by lastname, firstname, middlename, birthday    order by kol DESC ;");
            } else if (article.equals("12.8_12.6")) {
                statement = connection.prepareStatement("select   MAX(facktaddr) as facktaddr, count(id) as kol, lastname, firstname, birthday, middlename,  max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from st_12_8_st_12_6 where facktaddr LIKE ? and datep BETWEEN  ? and ?  group by lastname, firstname, middlename, birthday    order by kol DESC ;");
            } else if (article.equals("6.1.1")) {
                statement = connection.prepareStatement("select    MAX(facktaddr) as facktaddr, count(id) as kol, lastname, firstname, birthday, middlename, max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from st_6_1_1 where facktaddr LIKE ? and datep BETWEEN  ? and ?   group by lastname, firstname, middlename, birthday    order by kol DESC ;");
            } else if (article.equals("14.17.1")) {
                statement = connection.prepareStatement("select    MAX(facktaddr) as facktaddr, count(id) as kol, lastname, firstname, birthday, middlename,  max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from st_14_17_1 where facktaddr LIKE ? and datep BETWEEN  ? and ?   group by lastname, firstname, middlename, birthday  order by kol DESC ;");
            } else if (article.equals("20.2")) {
                statement = connection.prepareStatement("select MAX(facktaddr) as facktaddr, count(id) as kol, lastname, firstname, birthday, middlename,  max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from st_20_2 where facktaddr LIKE ? and datep BETWEEN  ? and ? group by lastname, firstname, middlename, birthday   order by kol DESC ;");
            } else if (article.equals("20.17")) {
                statement = connection.prepareStatement("select MAX(facktaddr) as facktaddr, count(id) as kol, lastname, firstname, birthday, middlename,  max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from st_20_17 where facktaddr LIKE ? and datep BETWEEN  ? and ?   group by lastname, firstname, middlename, birthday    order by kol DESC ;");
            } else if (article.equals("20.33")) {
                statement = connection.prepareStatement("select  MAX(facktaddr) as facktaddr, count(id) as kol, lastname, firstname, birthday, middlename,  max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from st_20_33 where facktaddr LIKE ? and datep BETWEEN  ? and ?  group by lastname, firstname, middlename, birthday order by kol DESC ;");
            }

            statement.setString(1, regionMask);
            statement.setDate(2, d1);
            statement.setDate(3, d2);


            rs = statement.executeQuery();
            while (rs.next()) {

                ArticleStat articleStat = new ArticleStat();
                articleStat.setLastName(toUpperCase(rs.getString("lastname")));
                articleStat.setFirsName(toUpperCase(rs.getString("firstname")));
                articleStat.setMiddleName(toUpperCase(rs.getString("middlename")));
                articleStat.setBirthday(rs.getDate("birthday"));
                articleStat.setKol(rs.getInt("kol"));
                articleStat.setFacktAddr(rs.getString("facktaddr"));
                articleStat.setDateP(rs.getDate("md"));
                articleStat.setDateP2(rs.getDate("md2"));
                System.out.println(rs.getString("facktaddr"));
                stat.add(articleStat);

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

        PreparedStatement statement2 = null;
        ResultSet rs2 = null;
        ArrayList<ArticleStat> stats = new ArrayList<ArticleStat>();
        try {
            if (article.equals("5.35.1")) {
                statement2 = connection.prepareStatement("select count(id) as kol, lastname, firstname, birthday, middlename  from st_5_35_1 WHERE  datep BETWEEN ? and ? and lastname=? and firstname=? and middlename=? and birthday=? group by lastname, firstname, middlename, birthday HAVING count(id)>= ? order by kol dESC ;");
            } else if (article.equals("7.27")) {
                statement2 = connection.prepareStatement("select count(id) as kol, lastname, firstname, birthday, middlename  from st_7_27 WHERE  datep BETWEEN ? and ? and lastname=? and firstname=? and middlename=? and birthday=?  group by lastname, firstname, middlename, birthday HAVING count(id)>= ? order by kol dESC ;");
            } else if (article.equals("14.16")) {
                statement2 = connection.prepareStatement("select count(id) as kol, lastname, firstname, birthday, middlename from st_14_16 WHERE  datep BETWEEN ? and ? and lastname=? and firstname=? and middlename=? and birthday=? group by  lastname, firstname, middlename, birthday HAVING count(id)>= ? order by kol dESC ;");
            } else if (article.equals("12.8_12.6")) {
                statement2 = connection.prepareStatement("select count(id) as kol, lastname, firstname, birthday, middlename from st_12_8_st_12_6 WHERE  datep BETWEEN ? and ? and lastname=? and firstname=? and middlename=? and birthday=? group by  lastname, firstname, middlename, birthday HAVING count(id)>= ? order by kol dESC ;");
            }else if (article.equals("6.1.1")) {
                statement2 = connection.prepareStatement("select count(id) as kol, lastname, firstname, birthday, middlename from st_6_1_1 WHERE  datep BETWEEN ? and ? and lastname=? and firstname=? and middlename=? and birthday=? group by  lastname, firstname, middlename, birthday HAVING count(id)>= ? order by kol dESC ;");
            }else if (article.equals("14.17.1")) {
                statement2 = connection.prepareStatement("select count(id) as kol, lastname, firstname, birthday, middlename from st_14_17_1 WHERE  datep BETWEEN ? and ? and lastname=? and firstname=? and middlename=? and birthday=? group by  lastname, firstname, middlename, birthday HAVING count(id)>= ? order by kol dESC ;");
            }else if (article.equals("20.2")) {
                statement2 = connection.prepareStatement("select count(id) as kol, lastname, firstname, birthday, middlename from st_20_2 WHERE  datep BETWEEN ? and ? and lastname=? and firstname=? and middlename=? and birthday=? group by  lastname, firstname, middlename, birthday HAVING count(id)>= ? order by kol dESC ;");
            }else if (article.equals("20.17")) {
                statement2 = connection.prepareStatement("select count(id) as kol, lastname, firstname, birthday, middlename from st_20_17 WHERE  datep BETWEEN ? and ? and lastname=? and firstname=? and middlename=? and birthday=? group by  lastname, firstname, middlename, birthday HAVING count(id)>= ? order by kol dESC ;");
            }else if (article.equals("20.33")) {
                statement2 = connection.prepareStatement("select count(id) as kol, lastname, firstname, birthday, middlename from st_20_33 WHERE  datep BETWEEN ? and ? and lastname=? and firstname=? and middlename=? and birthday=? group by  lastname, firstname, middlename, birthday HAVING count(id)>= ? order by kol dESC ;");
            }
            for (int i = 0; i < stat.size(); i++) {
                System.out.println(i);
                statement2.setDate(1, (java.sql.Date) stat.get(i).getDateP2());
                statement2.setDate(2, (java.sql.Date) stat.get(i).getDateP());
                statement2.setString(3, stat.get(i).getLastName());
                statement2.setString(4, stat.get(i).getFirsName());
                statement2.setString(5, stat.get(i).getMiddleName());
                statement2.setDate(6, (java.sql.Date) stat.get(i).getBirthday());
                statement2.setInt(7,volume);


                rs2 = statement2.executeQuery();
                while (rs2.next()) {
                    ArticleStat arStat = new ArticleStat();
                    arStat.setLastName(toUpperCase(rs2.getString("lastname")));
                    arStat.setFirsName(toUpperCase(rs2.getString("firstname")));
                    arStat.setMiddleName(toUpperCase(rs2.getString("middlename")));
                    arStat.setBirthday(rs2.getDate("birthday"));
                    arStat.setKol(rs2.getInt("kol"));
                    arStat.setDateP(stat.get(i).getDateP());
                    arStat.setFacktAddr(stat.get(i).getFacktAddr());
                    System.out.println(rs2.getString("lastname"));
                    stats.add(arStat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs2 != null) {
                try {
                    rs2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        if (statement2 != null) {
            try {
                statement2.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DbConnect.close(connection);
        return stats;
    }


    public int KolNarush(String article, String mask, java.sql.Date d1, java.sql.Date d2, boolean flag) {

        connection = DbConnect.getConnection();
        int kol = 0;

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            if (article.equals("5.35.1")) {
                statement = connection.prepareStatement("select count(id) as kol from st_5_35_1 where facktaddr like ? and datep BETWEEN  ? and ?   ;");
            } else if (article.equals("7.27")) {
                statement = connection.prepareStatement("select count(id) as kol from st_7_27 where facktaddr like ? and datep BETWEEN  ? and ?   ;");
            } else if (article.equals("14.16")) {
                statement = connection.prepareStatement("select count(id) as kol from st_14_16 where facktaddr like ? and datep BETWEEN  ? and ?   ;");
            } else if (article.equals("12.8_12.6")) {
                statement = connection.prepareStatement("select count(id) as kol from st_12_8_st_12_6 where facktaddr like ? and datep BETWEEN  ? and ?   ;");
            }else if (article.equals("6.1.1")) {
                statement = connection.prepareStatement("select count(id) as kol from st_6_1_1 where facktaddr like ? and datep BETWEEN  ? and ?   ;");
            }else if (article.equals("20.2")) {
                statement = connection.prepareStatement("select count(id) as kol from st_20_2 where facktaddr like ? and datep BETWEEN  ? and ?   ;");
            }else if (article.equals("20.17")) {
                statement = connection.prepareStatement("select count(id) as kol from st_20_17 where facktaddr like ? and datep BETWEEN  ? and ?   ;");
            }else if (article.equals("20.33")) {
                statement = connection.prepareStatement("select count(id) as kol from st_20_33 where facktaddr like ? and datep BETWEEN  ? and ?   ;");
            }else if (article.equals("14.17.1")) {
                statement = connection.prepareStatement("select count(id) as kol from st_14_17_1 where facktaddr like ? and datep BETWEEN  ? and ?   ;");
            }

            statement.setString(1, mask);
            statement.setDate(2, d1);
            statement.setDate(3, d2);

            rs = statement.executeQuery();

            while (rs.next()) {
                kol = (rs.getInt("kol"));

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
        return kol;
    }

    public int KolFace(String article, String mask, java.sql.Date d1, java.sql.Date d2, boolean flag) {

        connection = DbConnect.getConnection();
        int kf = 0;

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            if (article.equals("5.35.1")) {
                statement = connection.prepareStatement("select count(DISTINCT(lastname,firstname,middlename,birthday)) as kf from st_5_35_1 where facktaddr like ? and datep BETWEEN  ? and ?   ;");
            } else if (article.equals("7.27")) {
                statement = connection.prepareStatement("select count(DISTINCT(lastname,firstname,middlename,birthday)) as kf from st_7_27 where facktaddr like ? and datep BETWEEN  ? and ?   ;");
            } else if (article.equals("14.16")) {
                statement = connection.prepareStatement("select count(DISTINCT(lastname,firstname,middlename,birthday)) as kf from st_14_16 where facktaddr like ? and datep BETWEEN  ? and ?   ;");
            } else if (article.equals("12.8_12.6")) {
                statement = connection.prepareStatement("select count(DISTINCT(lastname,firstname,middlename,birthday)) as kf from st_12_8_st_12_6 where facktaddr like ? and datep BETWEEN  ? and ?   ;");
            } else if (article.equals("6.1.1")) {
                statement = connection.prepareStatement("select count(DISTINCT(lastname,firstname,middlename,birthday)) as kf from st_6_1_1 where facktaddr like ? and datep BETWEEN  ? and ?   ;");
            } else if (article.equals("14.17.1")) {
                statement = connection.prepareStatement("select count(DISTINCT(lastname,firstname,middlename,birthday)) as kf from st_14_17_1 where facktaddr like ? and datep BETWEEN  ? and ?   ;");
            } else if (article.equals("20.2")) {
                statement = connection.prepareStatement("select count(DISTINCT(lastname,firstname,middlename,birthday)) as kf from st_20_2 where facktaddr like ? and datep BETWEEN  ? and ?   ;");
            } else if (article.equals("20.17")) {
                statement = connection.prepareStatement("select count(DISTINCT(lastname,firstname,middlename,birthday)) as kf from st_20_17 where facktaddr like ? and datep BETWEEN  ? and ?   ;");
            } else if (article.equals("20.33")) {
                statement = connection.prepareStatement("select count(DISTINCT(lastname,firstname,middlename,birthday)) as kf from st_20_33 where facktaddr like ? and datep BETWEEN  ? and ?   ;");
            }
            statement.setString(1, mask);
            statement.setDate(2, d1);
            statement.setDate(3, d2);


            rs = statement.executeQuery();

            while (rs.next()) {
                kf = (rs.getInt("kf"));
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
        return kf;
    }


}
