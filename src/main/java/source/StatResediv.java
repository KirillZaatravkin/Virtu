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
public class StatResediv {


    private Connection connection;

    private static final int threadCount = 3;

    public List<ApOVDStat> FilterStat(String article, java.sql.Date d1, java.sql.Date d2, boolean flag, String sort, String interval)
    {

        long curr = System.currentTimeMillis();

        connection = DbConnect.getConnection();

        List<ApOVDStat> apOVDstats = new ArrayList<ApOVDStat>();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            if (article == null&& flag!=true) {
                statement = connection.prepareStatement("select pasportn, pasports, count(id) as kol, lastname, firstname, birthday, middlename, article, max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from ap_ovd where datep BETWEEN  ? and ?  group by lastname, firstname, middlename, birthday, article , pasports,pASPORTN   order by kol DESC ;");
                statement.setDate(1, d1);
                statement.setDate(2, d2);


            } if (article != null&& flag!=true) {
                statement = connection.prepareStatement("select count(id) as kol, pasportn, pasports,lastname, firstname, birthday, middlename, article , max(datep) as md ,  max(datep) - INTERVAL '365 day' as md2 from ap_ovd WHERE datep BETWEEN  ? and ? and article =?  group by lastname, firstname, middlename, birthday, article , pasports,pasportn  ;");
                statement.setDate(1, d1);
                statement.setDate(2, d2);
                statement.setString(3, article);
            }

             if(interval.equals("year")){
            if (article == null&& flag==true) {
                statement = connection.prepareStatement("select count(id) as kol, lastname, firstname, birthday, middlename, article,pasportn,pasports, max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from ap_ovd where EXTRACT (YEAR from datep)= EXTRACT (YEAR FROM now())  group by lastname, firstname, middlename, birthday, article ,pasportn,pasports  ;");

            }if (article != null&& flag==true) {
                statement = connection.prepareStatement("select count(id) as kol, lastname, firstname, birthday, middlename, article , max(datep) as md ,  max(datep) - INTERVAL '365 day' as md2 pasports, pasportn from ap_ovd where EXTRACT (YEAR from datep)= EXTRACT (YEAR FROM now()) and article=? group by lastname, firstname, middlename, birthday, article, pasportn, pasports   ;");
                statement.setString(1, article);

            }
             }
            if(interval.equals("month")) {
                if (article == null && flag == true) {
                    statement = connection.prepareStatement("select count(id) as kol, lastname, firstname, birthday, middlename, article,pasportn,pasports, max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from ap_ovd where EXTRACT (MONTH from datep)= EXTRACT (MONTH FROM now())  group by lastname, firstname, middlename, birthday, article ,pasportn,pasports  ;");

                }
                if (article != null && flag == true) {
                    statement = connection.prepareStatement("select count(id) as kol, lastname, firstname, birthday, middlename, article , max(datep) as md ,  max(datep) - INTERVAL '365 day' as md2, pasports, pasportn from ap_ovd WHERE EXTRACT (MONTH from datep)= EXTRACT (MONTH FROM now()) and article=? group by lastname, firstname, middlename, birthday, article, pasportn, pasports   ;");
                    statement.setString(1, article);

                }
            }
            if(interval.equals("lastload")) {

                if (article == null && flag == true) {
                    statement = connection.prepareStatement("select count(id) as kol, lastname, firstname, birthday, middlename, article,pasportn,pasports, max(datep) as md , max(datep) - INTERVAL '365 day' as md2 from ap_ovd where datecreate=(select max(datecreate) from ap_ovd)  group by lastname, firstname, middlename, birthday, article ,pasportn,pasports  ;");

                }
                if (article != null && flag == true) {
                    statement = connection.prepareStatement("select count(id) as kol, lastname, firstname, birthday, middlename, article , max(datep) as md ,  max(datep) - INTERVAL '365 day' as md2, pasports, pasportn from ap_ovd WHERE datecreate=(select max(datecreate) from ap_ovd)  and article=? group by lastname, firstname, middlename, birthday, article, pasportn, pasports   ;");
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
             
                apOVDstat.setArticle(rs.getString("article"));
                apOVDstat.setDateP(rs.getDate("md"));
                apOVDstat.setDateP2(rs.getDate("md2"));
                apOVDstats.add(apOVDstat);
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
        System.out.println(sort);

        Collections.sort(result,CompDateP);
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
                statement2 = connection.prepareStatement("select count(id) as kol, lastname, firstname, birthday, middlename, article  from ap_ovd WHERE  datep BETWEEN ? and ? and article =?  and lastname=? and firstname=? and middlename=? and birthday=? group by lastname, firstname, middlename, birthday, article HAVING count(id)>1 order by kol dESC ;");
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
                    statement2.setString(4, apOVD.get(i).getLastName());
                    statement2.setString(5, apOVD.get(i).getFirstName());
                    statement2.setString(6, apOVD.get(i).getMiddleName());
                    statement2.setDate(7, (java.sql.Date) apOVD.get(i).getBirthDay());
                    rs2 = statement2.executeQuery();
                    while (rs2.next()) {
                        ApOVDStat apOVDstat = new ApOVDStat();
                        apOVDstat.setLastName(toUpperCase(rs2.getString("lastname")));
                        apOVDstat.setFirstName(toUpperCase(rs2.getString("firstname")));
                        apOVDstat.setMiddleName(toUpperCase(rs2.getString("middlename")));
                        apOVDstat.setBirthday(rs2.getDate("birthday"));
                        apOVDstat.setKol(rs2.getInt("kol"));
                        apOVDstat.setDateP(apOVD.get(i).getDateP());
                        apOVDstat.setArticle(rs2.getString("article"));
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




public int KolNarush(String article, java.sql.Date d1, java.sql.Date d2,boolean flag) {

        connection = DbConnect.getConnection();
         int kol=0;

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            if (article == null  && flag== false) {
                statement = connection.prepareStatement("select count(id) as kol from ap_ovd where datep BETWEEN  ? and ?   ;");
                statement.setDate(1, d1);
                statement.setDate(2, d2);

            }  if(article != null  && flag== false){
                statement = connection.prepareStatement("select count(id) as kol from ap_ovd WHERE datep BETWEEN  ? and ? and article =? ;");
                statement.setDate(1, d1);
                statement.setDate(2, d2);
                statement.setString(3, article);
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



    public int KolFace(String article, java.sql.Date d1, java.sql.Date d2,boolean flag) {

        connection = DbConnect.getConnection();
        int kf=0;

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            if (article == null  && flag== false) {
                statement = connection.prepareStatement("select count(DISTINCT(pasportn,pasports)) as kf from ap_ovd where datep BETWEEN  ? and ?   ;");
                statement.setDate(1, d1);
                statement.setDate(2, d2);

            }  if(article != null  && flag== false){
                statement = connection.prepareStatement("select count(DISTINCT(pasportn,pasports)) as kf from ap_ovd WHERE datep BETWEEN  ? and ? and article =? ;");
                statement.setDate(1, d1);
                statement.setDate(2, d2);
                statement.setString(3, article);
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