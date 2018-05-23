package source.system.model;

import java.util.Date;

/**
 * Created by кирюха on 17.05.2018.
 */
public class ApGIBDD {
    String LastName, FirstName, MiddleName, FacktAddr,Article,Cact;
    Date BirthDay, DateCreate, DateP;
    String VodUd;


    public void setFirstName(String FirstName) {
        this.FirstName = (FirstName);
    }
    public void setCact(String Cact)
    {
        this.Cact=Cact;
    }
    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public void setMiddleName(String MiddleName) {
        this.MiddleName = MiddleName;
    }

    public void setFacktAddr(String FacktAddr) {
        this.FacktAddr = FacktAddr;
    }

    public void setArticle(String Article) {
        this.Article = Article;
    }

    public void setBirthday(Date BirthDay) {
        this.BirthDay = BirthDay;
    }

    public String getVodUd() {
        return VodUd;
    }
    public String getCact() {
        return Cact;
    }

    public void setVodUd(String VodUd) {
        this.VodUd = VodUd;
    }

    public void setDaterCreate (Date DateCreate)
    {
        this.DateCreate=DateCreate;
    }
    public void setDateP(Date DateP)
    {
        this.DateP=DateP;
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

    public String getFacktAddr() {
        return FacktAddr;
    }



    public String getArticle() {
        return Article;
    }

    public Date getBirthDay() {
        return BirthDay;
    }

    public Date getDateCreate (){ return DateCreate;}

    public Date getDateP (){ return DateP;}
}
