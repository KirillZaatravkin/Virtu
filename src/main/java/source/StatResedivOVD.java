package source;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

;
import static jdk.nashorn.internal.objects.NativeString.toUpperCase;
import static source.ApOVDStat.*;


/**
 * Created by кирюха on 19.11.2017.
 */
public class StatResedivOVD {


    private Connection connection;

    private static final int threadCount = 1;

    public List<ApOVDStat> FilterStat(String article, java.sql.Date d1, java.sql.Date d2, String sorts, String interval,String cact, String regionMask)
    {

        long curr = System.currentTimeMillis();

        connection = DbConnect.getConnection();

        List<ApOVDStat> apOVDstats = new ArrayList<ApOVDStat>();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            if(interval.equals("found")){
            if (article == null) {
                statement = connection.prepareStatement("select pasportn, cact, pasports, count(id) as kol, lastname, firstname, birthday, middlename, article, max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from ap_ovd where resaddr LIKE ? and datep BETWEEN  ? and ?  group by lastname, firstname,cact, middlename, birthday, article , pasports,pASPORTN   order by kol DESC ;");
                statement.setString(1, regionMask);
                statement.setDate(2, d1);
                statement.setDate(3, d2);



            } if (article != null) {
                statement = connection.prepareStatement("select count(id) as kol,cact, pasportn,pasports,lastname, firstname, birthday, middlename, article , max(datep) as md ,  max(datep) - INTERVAL '365 day' as md2 from ap_ovd WHERE resaddr LIKE ? and  cact like ? and datep BETWEEN  ? and ? and article =?  group by lastname, cact, firstname, middlename, birthday, article , pasports,pasportn  ;");statement.setString(1, regionMask);
                    statement.setString(1, regionMask);
                statement.setString(2, cact);
                statement.setDate(3, d1);
                statement.setDate(4, d2);
                statement.setString(5, article);

            }
            }

             if(interval.equals("year")){
            if (article == null) {
                statement = connection.prepareStatement("select count(id) as kol, cact, lastname, firstname, birthday, middlename, article,pasportn,pasports, max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from ap_ovd where EXTRACT (YEAR from datep)= EXTRACT (YEAR FROM now())  group by lastname,cact, firstname, middlename, birthday, article ,pasportn,pasports  ;");

            }if (article != null) {
                statement = connection.prepareStatement("select count(id) as kol,cact, lastname,  firstname, birthday, middlename, article , max(datep) as md ,  max(datep) - INTERVAL '365 day' as md2 pasports, pasportn from ap_ovd where EXTRACT (YEAR from datep)= EXTRACT (YEAR FROM now()) and article=? group by cact,lastname, firstname, middlename, birthday, article, pasportn, pasports   ;");
                statement.setString(1, article);

            }
             }
            if(interval.equals("month")) {
                if (article == null) {
                    statement = connection.prepareStatement("select count(id) as kol, cact, lastname, firstname, birthday, middlename, article,pasportn,pasports, max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from ap_ovd where EXTRACT (MONTH from datep)= EXTRACT (MONTH FROM now())  group by lastname,cact, firstname, middlename, birthday, article ,pasportn,pasports  ;");

                }
                if (article != null) {
                    statement = connection.prepareStatement("select count(id) as kol, cact, lastname, firstname, birthday, middlename, article , max(datep) as md ,  max(datep) - INTERVAL '365 day' as md2, pasports, pasportn from ap_ovd WHERE EXTRACT (MONTH from datep)= EXTRACT (MONTH FROM now()) and article=? group by cact, lastname, firstname, middlename, birthday, article, pasportn, pasports   ;");
                    statement.setString(1, article);

                }
            }
            if(interval.equals("lastload")) {

                if (article == null ) {
                    statement = connection.prepareStatement("select count(id) as kol,cact, lastname, firstname, birthday, middlename, article,pasportn,pasports, max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from ap_ovd where datecreate=(select max(datecreate) from ap_ovd)  group by lastname, firstname, cact,middlename, birthday, article ,pasportn,pasports  ;");

                }
                if (article != null ) {
                    statement = connection.prepareStatement("select count(id) as kol, cact,   lastname, firstname, birthday, middlename, article , max(datep) as md ,  max(datep) - INTERVAL '365 day' as md2, pasports, pasportn from ap_ovd WHERE datecreate=(select max(datecreate) from ap_ovd)  and article=? group by cact,  lastname, firstname, middlename, birthday, article, pasportn, pasports   ;");
                    statement.setString(1, article);

                }
            }

            rs = statement.executeQuery();
            while (rs.next()) {

                ApOVDStat apOVDstat = new ApOVDStat();
                apOVDstat.setLastName(toUpperCase(rs.getString("lastname")));
                apOVDstat.setFirstName(toUpperCase(rs.getString("firstname")));
                apOVDstat.setMiddleName(toUpperCase(rs.getString("middlename")));
                apOVDstat.setBirthday(rs.getDate("birthday"));
                apOVDstat.setKol(rs.getInt("kol"));
                apOVDstat.setCact(rs.getString("cact"));
                apOVDstat.setArticle(rs.getString("article"));
                apOVDstat.setDateP(rs.getDate("md"));
                apOVDstat.setDateP2(rs.getDate("md2"));
                apOVDstats.add(apOVDstat);
                System.out.println(apOVDstats.size());
                System.out.print(rs.getDate("md2"));


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

        CountDownLatch countDownLatch=new CountDownLatch(threadCount);


        DbConnect.close(connection);

        ThreadOne[] threadPool = new ThreadOne[threadCount];

        for (int i = 0; i < threadCount; i++) {
            threadPool[i] = new ThreadOne(apOVDstats, i, threadCount, countDownLatch);
            threadPool[i].start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<ApOVDStat> result = new ArrayList<ApOVDStat>();

        for (ThreadOne threadOne : threadPool) {
            result.addAll(threadOne.getResultList());
        }

        System.out.println("Total time ms: " + (System.currentTimeMillis() - curr));


        if(sorts.equals("datep"))
        {
            Collections.sort(result,CompDateP);
        }
        else if(sorts.equals("lastname"))
        {
            Collections.sort(result, CompLastName);
            System.out.print("77777777777");
        }
        else if (sorts.equals("kol"))
        {
            Collections.sort(result, CompKol);
        }
        else
        {
            Collections.sort(result, CompArticle);
        }

        return result;
    }


    class ThreadOne extends Thread {
        private List<ApOVDStat> apOVD;
        private volatile List<ApOVDStat> ResultList;
        private int StartInd;
        private int increment;
        private CountDownLatch latch;

        ThreadOne(List<ApOVDStat> apOVDStats, int StartInd, int increment, CountDownLatch latch) {

            this.apOVD = apOVDStats;
            this.StartInd = StartInd;
            this.increment = increment;
            this.latch = latch;
        }

        @Override
        public void run() {

            List<ApOVDStat> apOVDstats = new ArrayList<ApOVDStat>();
            connection = DbConnect.getConnection();
            System.out.print("gj");

            PreparedStatement statement2 = null;
            ResultSet rs2 = null;
            try {
                statement2 = connection.prepareStatement("select count(id) as kol, lastname, firstname, birthday, middlename, article, cact  from ap_ovd WHERE  datep BETWEEN ? and ? and article =?  and cact=? and lastname=? and firstname=? and middlename=? and birthday=? group by cact, lastname, firstname, middlename, birthday, article HAVING count(id)>=2 order by kol dESC ;");
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }
                for (int i = StartInd; i < apOVD.size(); i += increment) {
                System.out.println(i);
                try {


                    statement2.setDate(1, (java.sql.Date) apOVD.get(i).getDateP2());
                    statement2.setDate(2, (java.sql.Date) apOVD.get(i).getDateP());
                    statement2.setString(3, apOVD.get(i).getArticle());
                    statement2.setString(4, apOVD.get(i).getCact());
                    statement2.setString(5, apOVD.get(i).getLastName());
                    statement2.setString(6, apOVD.get(i).getFirstName());
                    statement2.setString(7, apOVD.get(i).getMiddleName());
                    statement2.setDate(8, (java.sql.Date) apOVD.get(i).getBirthDay());
                    rs2 = statement2.executeQuery();
                    System.out.println(apOVD.get(i).getDateP2());
                    System.out.println(apOVD.get(i).getDateP());
                    while (rs2.next()) {
                        ApOVDStat apOVDstat = new ApOVDStat();
                        apOVDstat.setLastName(toUpperCase(rs2.getString("lastname")));
                        apOVDstat.setFirstName(toUpperCase(rs2.getString("firstname")));
                        apOVDstat.setMiddleName(toUpperCase(rs2.getString("middlename")));
                        apOVDstat.setBirthday(rs2.getDate("birthday"));
                        apOVDstat.setKol(rs2.getInt("kol"));
                        apOVDstat.setDateP(apOVD.get(i).getDateP());
                        apOVDstat.setArticle(rs2.getString("article"));
                        apOVDstat.setCact(rs2.getString("cact"));
                        System.out.println(rs2.getString("lastname"));


                        apOVDstats.add(apOVDstat);
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
            ResultList = apOVDstats;
            latch.countDown();
        }


        public List<ApOVDStat> getResultList() {
            return ResultList;
        }
    }




public int KolNarush(String article, String mask, java.sql.Date d1, java.sql.Date d2,boolean flag) {

        connection = DbConnect.getConnection();
         int kol=0;

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            if (article == null  && flag== false) {
                statement = connection.prepareStatement("select count(id) as kol from ap_ovd where facktaddr like ? and  datep BETWEEN  ? and ?   ;");
                statement.setString(1,mask);
                statement.setDate(2, d1);
                statement.setDate(3, d2);

            }  if(article != null  && flag== false){
                statement = connection.prepareStatement("select count(id) as kol from ap_ovd WHERE facktaddr like ? and datep BETWEEN  ? and ? and article =? ;");
                statement.setString(1,mask);
                statement.setDate(2, d1);
                statement.setDate(3, d2);
                statement.setString(4, article);
            }
            if (article == null  && flag== true) {
                statement = connection.prepareStatement("select count(id) as kol from ap_ovd where EXTRACT (YEAR from datep)= EXTRACT (YEAR FROM now())   ;");


            }
            if (article != null  && flag== true) {
                statement = connection.prepareStatement("select count(id) as kolfrom ap_ovd where EXTRACT (YEAR from datep)= EXTRACT (YEAR FROM now())and article =?  ;");
                statement.setString(1,article);


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



    public int KolFace(String article, String mask, java.sql.Date d1, java.sql.Date d2,boolean flag) {

        connection = DbConnect.getConnection();
        int kf=0;

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            if (article == null  && flag== false) {
                statement = connection.prepareStatement("select count(DISTINCT(pasportn,pasports)) as kf from ap_ovd where  facktaddr like ? and datep BETWEEN  ? and ?   ;");
                statement.setString(1,mask);
                statement.setDate(2, d1);
                statement.setDate(3, d2);

            }  if(article != null  && flag== false){
                statement = connection.prepareStatement("select count(DISTINCT(pasportn,pasports)) as kf from ap_ovd WHERE facktaddr like ? and datep BETWEEN  ? and ? and article =? ;");
                statement.setString(1,mask);
                statement.setDate(2, d1);
                statement.setDate(3, d2);
                statement.setString(4, article);
            }
            if (article == null  && flag== true) {
                statement = connection.prepareStatement("select count(DISTINCT(pasportn,pasports)) as kf from ap_ovd where EXTRACT (YEAR from datep)= EXTRACT (YEAR FROM now())   ;");


            }
            if (article != null  && flag== true) {
                statement = connection.prepareStatement("select count(DISTINCT(pasportn,pasports)) as kf ap_ovd where EXTRACT (YEAR from datep)= EXTRACT (YEAR FROM now())and article =?  ;");
                statement.setString(1,article);            }
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