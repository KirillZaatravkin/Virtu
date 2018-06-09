package source;


import source.system.model.ApPristStat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static jdk.nashorn.internal.objects.NativeString.toUpperCase;
import static source.system.model.ApPristStat.*;


/**
 * Created by кирюха on 19.11.2017.
 */
public class StatResedivPrist {


    private Connection connection;

    private static final int threadCount = 3;

    public List<ApPristStat> FilterStat(String article, java.sql.Date d1, java.sql.Date d2, String sorts, String interval, String cact, String regionMask) {

        long curr = System.currentTimeMillis();

        connection = DbConnect.getConnection();

        List<ApPristStat> apPristStats = new ArrayList<ApPristStat>();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            if (interval.equals("found")) {
                if (article == null) {
                    statement = connection.prepareStatement("select   cact,  count(id) as kol, max(facktaddr) as facktaddr,lastname, firstname, birthday, middlename, article, max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from ap_prist where facktaddr LIKE ? and datep BETWEEN  ? and ?  group by lastname, firstname,cact, middlename, birthday, article    order by kol DESC ;");
                    statement.setString(1, regionMask);
                    statement.setDate(2, d1);
                    statement.setDate(3, d2);
                }

                if (article != null) {
                    statement = connection.prepareStatement("select count(id) as kol,cact, max(facktaddr) as facktaddr, lastname, firstname, birthday, middlename, article , max(datep) as md ,  max(datep) - INTERVAL '365 day' as md2 from ap_prist WHERE facktaddr LIKE ? and  cact like ? and datep BETWEEN  ? and ? and article =?  group by lastname, cact, firstname, middlename, birthday, article  ;");

                    statement.setString(1, regionMask);
                    statement.setString(2, cact);
                    System.out.println("ghg");
                    System.out.println(cact);
                    statement.setDate(3, d1);
                    statement.setDate(4, d2);
                    statement.setString(5, article);
                }
            }

            if (interval.equals("year")) {
                if (article == null) {
                    statement = connection.prepareStatement("select count(id) as kol, cact,max(facktaddr) as facktaddr, lastname, firstname, birthday, middlename, articlen max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from ap_prist where EXTRACT (YEAR from datep)= EXTRACT (YEAR FROM now())  group by lastname,cact, firstname, middlename, birthday, article  ;");

                }
                if (article != null) {
                    statement = connection.prepareStatement("select count(id) as kol,cact, max(facktaddr) as facktaddr, lastname,  firstname, birthday, middlename, article , max(datep) as md ,  max(datep) - INTERVAL '365 day' as md2 from ap_prist where EXTRACT (YEAR from datep)= EXTRACT (YEAR FROM now()) and article=? group by cact,lastname, firstname, middlename, birthday, article ;");
                    statement.setString(1, article);

                }
            }
            if (interval.equals("month")) {
                if (article == null) {
                    statement = connection.prepareStatement("select count(id) as kol, cact, max(facktaddr) as facktaddr, lastname, firstname, birthday, middlename, article max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from ap_prist where EXTRACT (MONTH from datep)= EXTRACT (MONTH FROM now())  group by lastname,cact, firstname, middlename, birthday, article    ;");

                }
                if (article != null) {
                    statement = connection.prepareStatement("select count(id) as kol, cact,max(facktaddr) as facktaddr,  lastname, firstname, birthday, middlename, article , max(datep) as md ,  max(datep) - INTERVAL '365 day' as md2 from ap_prist WHERE EXTRACT (MONTH from datep)= EXTRACT (MONTH FROM now()) and article=? group by cact, lastname, firstname, middlename, birthday, article ;");
                    statement.setString(1, article);

                }
            }
            if (interval.equals("lastload")) {

                if (article == null) {
                    statement = connection.prepareStatement("select count(id) as kol,cact, max(facktaddr) as facktaddr, lastname, firstname, birthday, middlename, article , max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from ap_prist where datecreate=(select max(datecreate) from ap_gibdd)  group by lastname, firstname, cact,middlename, birthday, article   ;");

                }
                if (article != null) {
                    statement = connection.prepareStatement("select count(id) as kol, cact, max(facktaddr) as facktaddr,   lastname, firstname, birthday, middlename, article , max(datep) as md ,  max(datep) - INTERVAL '365 day' as md2 from ap_prist WHERE datecreate=(select max(datecreate) from ap_gibdd)  and article=? group by cact,  lastname, firstname, middlename, birthday, article    ;");
                    statement.setString(1, article);

                }
            }

            rs = statement.executeQuery();
            while (rs.next()) {

                ApPristStat apPristStat = new ApPristStat();
                apPristStat.setLastName(toUpperCase(rs.getString("lastname")));
                apPristStat.setFirstName(toUpperCase(rs.getString("firstname")));
                apPristStat.setMiddleName(toUpperCase(rs.getString("middlename")));
                apPristStat.setBirthday(rs.getDate("birthday"));
                apPristStat.setKol(rs.getInt("kol"));
                apPristStat.setCact(rs.getString("cact"));
                apPristStat.setFacktAddr(rs.getString("facktaddr"));
                apPristStat.setArticle(rs.getString("article"));
                apPristStat.setDateP(rs.getDate("md"));
                apPristStat.setDateP2(rs.getDate("md2"));
                apPristStats.add(apPristStat);
                System.out.println(apPristStats.size());
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

        CountDownLatch countDownLatch = new CountDownLatch(threadCount);


        DbConnect.close(connection);

        ThreadOne[] threadPool = new ThreadOne[threadCount];

        for (int i = 0; i < threadCount; i++) {
            threadPool[i] = new ThreadOne(apPristStats, i, threadCount, countDownLatch);
            threadPool[i].start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<ApPristStat> result = new ArrayList<ApPristStat>();

        for (ThreadOne threadOne : threadPool) {
            result.addAll(threadOne.getResultList());
        }

        System.out.println("Total time ms: " + (System.currentTimeMillis() - curr));


        if (sorts.equals("datep")) {
            Collections.sort(result, CompDateP1);
        } else if (sorts.equals("lastname")) {
            Collections.sort(result, CompLastName1);
            System.out.print("77777777777");
        } else if (sorts.equals("kol")) {
            Collections.sort(result, CompKol1);
        } else {
            Collections.sort(result, CompArticle1);
        }

        return result;
    }


    class ThreadOne extends Thread {
        private List<ApPristStat> apPrist;
        private volatile List<ApPristStat> ResultList;
        private int StartInd;
        private int increment;
        private CountDownLatch latch;

        ThreadOne(List<ApPristStat> apPristStats, int StartInd, int increment, CountDownLatch latch) {

            this.apPrist = apPristStats;
            this.StartInd = StartInd;
            this.increment = increment;
            this.latch = latch;
        }

        @Override
        public void run() {

            List<ApPristStat> apPristStats = new ArrayList<ApPristStat>();
            connection = DbConnect.getConnection();

            PreparedStatement statement2 = null;
            ResultSet rs2 = null;
            try {
                statement2 = connection.prepareStatement("select count(id) as kol, lastname, firstname, birthday, middlename, article, cact  from ap_prist WHERE  datep BETWEEN ? and ? and article =?  and cact=? and lastname=? and firstname=? and middlename=? and birthday=? group by cact, lastname, firstname, middlename, birthday, article HAVING count(id)>= 2 order by kol dESC ;");
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }
            for (int i = StartInd; i < apPrist.size(); i += increment) {
                System.out.println(i);
                try {


                    statement2.setDate(1, (java.sql.Date) apPrist.get(i).getDateP2());
                    statement2.setDate(2, (java.sql.Date) apPrist.get(i).getDateP());
                    statement2.setString(3, apPrist.get(i).getArticle());
                    statement2.setString(4, apPrist.get(i).getCact());
                    statement2.setString(5, apPrist.get(i).getLastName());
                    statement2.setString(6, apPrist.get(i).getFirstName());
                    statement2.setString(7, apPrist.get(i).getMiddleName());
                    statement2.setDate(8, (java.sql.Date) apPrist.get(i).getBirthDay());
                    rs2 = statement2.executeQuery();
                    System.out.println(apPrist.get(i).getDateP2());
                    System.out.println(apPrist.get(i).getDateP());
                    while (rs2.next()) {
                        ApPristStat apPristStat = new ApPristStat();
                        apPristStat.setLastName(toUpperCase(rs2.getString("lastname")));
                        apPristStat.setFirstName(toUpperCase(rs2.getString("firstname")));
                        apPristStat.setMiddleName(toUpperCase(rs2.getString("middlename")));
                        apPristStat.setBirthday(rs2.getDate("birthday"));
                        apPristStat.setKol(rs2.getInt("kol"));
                        apPristStat.setDateP(apPrist.get(i).getDateP());
                        apPristStat.setFacktAddr(apPrist.get(i).getFacktAddr());
                        apPristStat.setArticle(rs2.getString("article"));
                        apPristStat.setCact(rs2.getString("cact"));
                        System.out.println(rs2.getString("lastname"));


                        apPristStats.add(apPristStat);
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

            }
            if (statement2 != null) {
                try {
                    statement2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DbConnect.close(connection);
            ResultList = apPristStats;
            latch.countDown();
        }

        public List<ApPristStat> getResultList() {
            return ResultList;
        }
    }


    public int KolNarush(String article, String mask, java.sql.Date d1, java.sql.Date d2, boolean flag) {

        connection = DbConnect.getConnection();
        int kol = 0;

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            if (article == null && flag == false) {
                statement = connection.prepareStatement("select count(id) as kol from ap_prist where facktaddr like ? and datep BETWEEN  ? and ?   ;");
                statement.setString(1, mask);
                statement.setDate(2, d1);
                statement.setDate(3, d2);

            }
            if (article != null && flag == false) {
                statement = connection.prepareStatement("select count(id) as kol from ap_prist WHERE facktaddr like ? and datep BETWEEN  ? and ? and article =? ;");
                statement.setString(1, mask);
                statement.setDate(2, d1);
                statement.setDate(3, d2);
                statement.setString(4, article);
            }
            if (article == null && flag == true) {
                statement = connection.prepareStatement("select count(id) as kol from ap_prist where EXTRACT (YEAR from datep)= EXTRACT (YEAR FROM now())   ;");


            }
            if (article != null && flag == true) {
                statement = connection.prepareStatement("select count(id) as kolfrom ap_prist where EXTRACT (YEAR from datep)= EXTRACT (YEAR FROM now())and article =?  ;");
                statement.setString(1, article);


            }
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
            if (article == null && flag == false) {
                statement = connection.prepareStatement("select count(DISTINCT(lastname,firstname,middlename,birthday)) as kf from ap_prist where facktaddr like ? and datep BETWEEN  ? and ?   ;");
                statement.setString(1, mask);
                statement.setDate(2, d1);
                statement.setDate(3, d2);

            }
            if (article != null && flag == false) {
                statement = connection.prepareStatement("select count(DISTINCT(lastname,firstname,middlename,birthday)) as kf from ap_prist WHERE facktaddr like ? and  datep BETWEEN  ? and ? and article =? ;");
                statement.setString(1, mask);
                statement.setDate(2, d1);
                statement.setDate(3, d2);
                statement.setString(4, article);
            }
            if (article == null && flag == true) {
                statement = connection.prepareStatement("select count(DISTINCT(lastname,firstname,middlename,birthday)) as kf from ap_prist where EXTRACT (YEAR from datep)= EXTRACT (YEAR FROM now())   ;");


            }
            if (article != null && flag == true) {
                statement = connection.prepareStatement("select count(DISTINCT(lastname,firstname,middlename,birthday)) as kf ap_prist where EXTRACT (YEAR from datep)= EXTRACT (YEAR FROM now())and article =?  ;");
                statement.setString(1, article);
            }
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