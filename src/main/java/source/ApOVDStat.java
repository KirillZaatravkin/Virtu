package source;

import org.apache.poi.hssf.usermodel.HSSFCell;
import sun.util.locale.LocaleUtils;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by кирюха on 13.11.2017.
 */
public class ApOVDStat {


    String LastName;
    String FirstName;
    String MiddleName;
    String Article;
    String PaspotrS;
    String PasportN;
    Date BirthDay, DateP, DateP2;
    Integer Kol;

    public String getPaspotrS() {
        return PaspotrS;
    }

    public String getPasportN() {
        return PasportN;
    }

    public void setPaspotrS(String paspotrS) {
        PaspotrS = paspotrS;
    }

    public void setPasportN(String pasportN) {
        PasportN = pasportN;
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
    public String getArticle(){return Article;}

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


    public static Comparator<ApOVDStat> CompKol = new Comparator<ApOVDStat>()
    {
        @Override
        public int compare(ApOVDStat o1, ApOVDStat o2) {
            return (int) o1.getKol()-o2.getKol();
        }
    };

    public static Comparator<ApOVDStat> CompLastName = new Comparator<ApOVDStat>()
    {
        @Override
        public int compare(ApOVDStat o1, ApOVDStat o2) {
            return o1.getLastName().compareTo(o2.getLastName());
        }
    };

    public static Comparator<ApOVDStat> CompDateP= new Comparator<ApOVDStat>()
    {
        @Override
        public int compare(ApOVDStat o1, ApOVDStat o2) {


            if (o1.getDateP().compareTo(o2.getDateP())==1)
            {
                return -1;
            }
             else if (o1.getDateP().equals(o2.getDateP()))
            {
                return 0;
            }
            else
            {
                return  1;
            }
        }
    };


}

