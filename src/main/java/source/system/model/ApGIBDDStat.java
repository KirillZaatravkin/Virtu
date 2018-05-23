package source.system.model;


import java.util.Comparator;
import java.util.Date;

/**
 * Created by кирюха on 17.05.2018.
 */
public class ApGIBDDStat {   

        String LastName;
        String FirstName;
        String MiddleName;
        String Article;
        String Cact;
        String VodUd;   
        Date BirthDay, DateP, DateP2;
        Integer Kol;

        public String getVodUd() {
            return VodUd;
        }

        public String getCact() {
            return Cact;
        }

        public void setCact(String Cact) {
            this.Cact = Cact;
        }

        public void setVodUd(String  VodUd) {
            this.VodUd= VodUd;
        }

        public void setFirstName(String FirstName) {
            this.FirstName = (FirstName);
        }

        public void setLastName(String LastName) {
            this.LastName = LastName;
        }


        public void setMiddleName(String MiddleName) {
            this.MiddleName = MiddleName;
        }

        public void setBirthday(Date BirthDay) {
            this.BirthDay = BirthDay;
        }

        public void setDateP2(Date DateP2) {
            this.DateP2 = DateP2;
        }

        public void setDateP(Date DateP) {
            this.DateP = DateP;
        }

        public void setArticle(String Article) {
            this.Article = Article;
        }

        public void setKol(Integer Kol) {
            this.Kol = Kol;
        }

        public String getLastName() {
            return LastName;
        }

        public String getFirstName() {
            return FirstName;
        }

        public String getMiddleName() {
            return MiddleName;
        }

        public String getArticle() {
            return Article;
        }

        public Date getBirthDay() {
            return BirthDay;
        }

        public Date getDateP() {
            return DateP;
        }

        public Integer getKol() {
            return Kol;
        }

        public Date getDateP2() {
            return DateP2;
        }


        public static Comparator<ApGIBDDStat> CompKol1 = new Comparator<ApGIBDDStat>() {
            @Override
            public int compare(ApGIBDDStat o1, ApGIBDDStat o2) {
                return (int) o1.getKol() - o2.getKol();
            }
        };

        public static Comparator<ApGIBDDStat> CompLastName1 = new Comparator<ApGIBDDStat>() {
            @Override
            public int compare(ApGIBDDStat o1, ApGIBDDStat o2) {
                return o1.getLastName().compareTo(o2.getLastName());
            }

        };

        public static Comparator<ApGIBDDStat> CompDateP1 = new Comparator<ApGIBDDStat>() {

            @Override
            public int compare(ApGIBDDStat o1, ApGIBDDStat o2) {
                if (o1.getDateP().compareTo(o2.getDateP()) == 1) {
                    return -1;
                } else if (o1.getDateP().equals(o2.getDateP())) {
                    return 0;
                } else {
                    return 1;
                }
            }
        };

        public static Comparator<ApGIBDDStat> CompArticle1 = new Comparator<ApGIBDDStat>() {
            @Override
            public int compare(ApGIBDDStat o1, ApGIBDDStat o2) {
                return o1.getArticle().compareTo(o2.getArticle());
            }
        }; 
}
