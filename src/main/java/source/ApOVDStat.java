package source;

import org.apache.poi.hssf.usermodel.HSSFCell;
import sun.util.locale.LocaleUtils;

import java.util.Date;

/**
 * Created by кирюха on 13.11.2017.
 */
public class ApOVDStat {

    String LastName, FirstName, MiddleName, Article;
    Date BirthDay, DateP, DateP2;
    Integer Kol;


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



}