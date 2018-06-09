package source;

import source.system.model.ApGIBDDStat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static jdk.nashorn.internal.objects.NativeString.toUpperCase;
import static source.system.model.ApGIBDDStat.*;


/**
 * Created by кирюха on 19.11.2017.
 */
public class StatResedivGIBDD {


    private Connection connection;

    private static final int threadCount = 3;

    public List<ApGIBDDStat> FilterStat(String article, java.sql.Date d1, java.sql.Date d2, String sorts, String interval, String cact, String regionMask) {

        long curr = System.currentTimeMillis();

        connection = DbConnect.getConnection();

        List<ApGIBDDStat> apGIBDDstats = new ArrayList<ApGIBDDStat>();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            if (interval.equals("found")) {
                if (article == null) {
                    statement = connection.prepareStatement("select   cact, MAX(facktaddr) as facktaddr, count(id) as kol, lastname, firstname, birthday, middlename, article, max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from ap_gibdd where facktaddr LIKE ? and datep BETWEEN  ? and ?  group by lastname, firstname,cact, middlename, birthday, article    order by kol DESC ;");
                    statement.setString(1, regionMask);
                    statement.setDate(2, d1);
                    statement.setDate(3, d2);
                }

                if (article != null) {
                    statement = connection.prepareStatement("select count(id) as kol,cact, MAX(facktaddr) as facktaddr, lastname, firstname, birthday, middlename, article , max(datep) as md ,  max(datep) - INTERVAL '365 day' as md2 from ap_gibdd WHERE facktaddr LIKE ? and  cact like ? and datep BETWEEN  ? and ? and article =?  group by lastname, cact, firstname, middlename, birthday, article  ;");

                    statement.setString(1, regionMask);
                    statement.setString(2, cact);
                    statement.setDate(3, d1);
                    statement.setDate(4, d2);
                    statement.setString(5, article);
                }
            }

            if (interval.equals("year")) {
                if (article == null) {
                    statement = connection.prepareStatement("select count(id) as kol,MAX(facktaddr) as facktaddr, cact, lastname, firstname, birthday, middlename, articlen max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from ap_gibdd where EXTRACT (YEAR from datep)= EXTRACT (YEAR FROM now())  group by lastname,cact, firstname, middlename, birthday, article  ;");

                }
                if (article != null) {
                    statement = connection.prepareStatement("select count(id) as kol,MAX(facktaddr) as facktaddr,cact, lastname,  firstname, birthday, middlename, article , max(datep) as md ,  max(datep) - INTERVAL '365 day' as md2 from ap_gibdd where EXTRACT (YEAR from datep)= EXTRACT (YEAR FROM now()) and article=? group by cact,lastname, firstname, middlename, birthday, article ;");
                    statement.setString(1, article);

                }
            }
            if (interval.equals("month")) {
                if (article == null) {
                    statement = connection.prepareStatement("select count(id) as kol,MAX(facktaddr) as facktaddr, cact, lastname, firstname, birthday, middlename, article max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from ap_gibdd where EXTRACT (MONTH from datep)= EXTRACT (MONTH FROM now())  group by lastname,cact, firstname, middlename, birthday, article    ;");

                }
                if (article != null) {
                    statement = connection.prepareStatement("select count(id) as kol,MAX(facktaddr) as facktaddr, cact, lastname, firstname, birthday, middlename, article , max(datep) as md ,  max(datep) - INTERVAL '365 day' as md2 from ap_gibdd WHERE EXTRACT (MONTH from datep)= EXTRACT (MONTH FROM now()) and article=? group by cact, lastname, firstname, middlename, birthday, article ;");
                    statement.setString(1, article);

                }
            }
            if (interval.equals("lastload")) {

                if (article == null) {
                    statement = connection.prepareStatement("select count(id) as kol,MAX(facktaddr) as facktaddr,cact, lastname, firstname, birthday, middlename, article , max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from ap_gibdd where datecreate=(select max(datecreate) from ap_gibdd)  group by lastname, firstname, cact,middlename, birthday, article   ;");

                }
                if (article != null) {
                    statement = connection.prepareStatement("select count(id) as kol,MAX(facktaddr) as facktaddr, cact,   lastname, firstname, birthday, middlename, article , max(datep) as md ,  max(datep) - INTERVAL '365 day' as md2 from ap_gibdd WHERE datecreate=(select max(datecreate) from ap_gibdd)  and article=? group by cact,  lastname, firstname, middlename, birthday, article    ;");
                    statement.setString(1, article);
                }
            }

            rs = statement.executeQuery();
            while (rs.next()) {

                ApGIBDDStat apGibddstat = new ApGIBDDStat();
                apGibddstat.setLastName(toUpperCase(rs.getString("lastname")));
                apGibddstat.setFirstName(toUpperCase(rs.getString("firstname")));
                apGibddstat.setMiddleName(toUpperCase(rs.getString("middlename")));
                apGibddstat.setBirthday(rs.getDate("birthday"));
                apGibddstat.setKol(rs.getInt("kol"));
                apGibddstat.setCact(rs.getString("cact"));
                apGibddstat.setArticle(rs.getString("article"));
                apGibddstat.setFacktAddr(rs.getString("facktaddr"));
                System.out.println(apGibddstat.getFacktAddr());
                apGibddstat.setDateP(rs.getDate("md"));
                apGibddstat.setDateP2(rs.getDate("md2"));
                apGIBDDstats.add(apGibddstat);

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
            threadPool[i] = new ThreadOne(apGIBDDstats, i, threadCount, countDownLatch);
            threadPool[i].start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<ApGIBDDStat> result = new ArrayList<ApGIBDDStat>();

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
        private List<ApGIBDDStat> apGIBDD;
        private volatile List<ApGIBDDStat> ResultList;
        private int StartInd;
        private int increment;
        private CountDownLatch latch;

        ThreadOne(List<ApGIBDDStat> apGIBDDStats, int StartInd, int increment, CountDownLatch latch) {

            this.apGIBDD = apGIBDDStats;
            this.StartInd = StartInd;
            this.increment = increment;
            this.latch = latch;
        }

        @Override
        public void run() {

            List<ApGIBDDStat> apGIBDDStats = new ArrayList<ApGIBDDStat>();
            connection = DbConnect.getConnection();

            PreparedStatement statement2 = null;
            ResultSet rs2 = null;
            try {
                statement2 = connection.prepareStatement("select count(id) as kol, lastname, firstname, birthday, middlename, article, cact  from ap_gibdd WHERE  datep BETWEEN ? and ? and article =?  and cact=? and lastname=? and firstname=? and middlename=? and birthday=? group by cact, lastname, firstname, middlename, birthday, article HAVING count(id)>= 2 order by kol dESC ;");
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }
            for (int i = StartInd; i < apGIBDD.size(); i += increment) {
                System.out.println(i);
                try {

                    statement2.setDate(1, (java.sql.Date) apGIBDD.get(i).getDateP2());
                    statement2.setDate(2, (java.sql.Date) apGIBDD.get(i).getDateP());
                    statement2.setString(3, apGIBDD.get(i).getArticle());
                    statement2.setString(4, apGIBDD.get(i).getCact());
                    statement2.setString(5, apGIBDD.get(i).getLastName());
                    statement2.setString(6, apGIBDD.get(i).getFirstName());
                    statement2.setString(7, apGIBDD.get(i).getMiddleName());
                    statement2.setDate(8, (java.sql.Date) apGIBDD.get(i).getBirthDay());
                    rs2 = statement2.executeQuery();
                    System.out.println(apGIBDD.get(i).getDateP2());
                    System.out.println(apGIBDD.get(i).getDateP());
                    while (rs2.next())
                    {
                        ApGIBDDStat apGIBDDstat = new ApGIBDDStat();
                        apGIBDDstat.setLastName(toUpperCase(rs2.getString("lastname")));
                        apGIBDDstat.setFirstName(toUpperCase(rs2.getString("firstname")));
                        apGIBDDstat.setMiddleName(toUpperCase(rs2.getString("middlename")));
                        apGIBDDstat.setBirthday(rs2.getDate("birthday"));
                        apGIBDDstat.setKol(rs2.getInt("kol"));
                        apGIBDDstat.setDateP(apGIBDD.get(i).getDateP());
                        apGIBDDstat.setFacktAddr(apGIBDD.get(i).getFacktAddr());
                        apGIBDDstat.setArticle(rs2.getString("article"));
                        apGIBDDstat.setCact(rs2.getString("cact"));
                        System.out.println(rs2.getString("lastname"));

                        apGIBDDStats.add(apGIBDDstat);
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
            ResultList = apGIBDDStats;
            latch.countDown();
        }

        public List<ApGIBDDStat> getResultList() {
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
                statement = connection.prepareStatement("select count(id) as kol from ap_gibdd where facktaddr like ? and datep BETWEEN  ? and ?   ;");
                statement.setString(1, mask);
                statement.setDate(2, d1);
                statement.setDate(3, d2);

            }
            if (article != null && flag == false) {
                statement = connection.prepareStatement("select count(id) as kol from ap_gibdd WHERE facktaddr like ? and datep BETWEEN  ? and ? and article =? ;");
                statement.setString(1, mask);
                statement.setDate(2, d1);
                statement.setDate(3, d2);
                statement.setString(4, article);
            }
            if (article == null && flag == true) {
                statement = connection.prepareStatement("select count(id) as kol from ap_gibdd where EXTRACT (YEAR from datep)= EXTRACT (YEAR FROM now())   ;");


            }
            if (article != null && flag == true) {
                statement = connection.prepareStatement("select count(id) as kolfrom ap_gibdd where EXTRACT (YEAR from datep)= EXTRACT (YEAR FROM now())and article =?  ;");
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
                statement = connection.prepareStatement("select count(DISTINCT(lastname,firstname,middlename,birthday)) as kf from ap_gibdd where facktaddr like ? and datep BETWEEN  ? and ?   ;");
                statement.setString(1, mask);
                statement.setDate(2, d1);
                statement.setDate(3, d2);

            }
            if (article != null && flag == false) {
                statement = connection.prepareStatement("select count(DISTINCT(lastname,firstname,middlename,birthday)) as kf from ap_gibdd WHERE facktaddr like ? and  datep BETWEEN  ? and ? and article =? ;");
                statement.setString(1, mask);
                statement.setDate(2, d1);
                statement.setDate(3, d2);
                statement.setString(4, article);
            }
            if (article == null && flag == true) {
                statement = connection.prepareStatement("select count(DISTINCT(lastname,firstname,middlename,birthday)) as kf from ap_gibdd where EXTRACT (YEAR from datep)= EXTRACT (YEAR FROM now())   ;");


            }
            if (article != null && flag == true) {
                statement = connection.prepareStatement("select count(DISTINCT(lastname,firstname,middlename,birthday)) as kf ap_gibdd where EXTRACT (YEAR from datep)= EXTRACT (YEAR FROM now())and article =?  ;");
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