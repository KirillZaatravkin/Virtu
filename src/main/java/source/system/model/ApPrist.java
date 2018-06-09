package source.system.model;

import java.util.Date;

/**
 * Created by кирюха on 17.05.2018.
 */
public class ApPrist {
    String LastName, FirstName, MiddleName, FacktAddr, Article, Cact, PasportS, PasportN, Nakaz;
    Date BirthDay, DateCreate, DateP, DateZak;

    public void setFirstName(String FirstName) {
        this.FirstName = (FirstName);
    }

    public void setCact(String Cact) {
        this.Cact = Cact;
    }

    public void setNakaz(String Nakaz) {
        this.Nakaz = Nakaz;
    }

    public void setDateZak(Date DateZak) {
        this.DateZak = DateZak;
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

    public String getPasportS() {
        return PasportS;
    }

    public String getPasportN() {
        return PasportN;
    }

    public String getCact() {
        return Cact;
    }

    public String getNakaz() { return Nakaz  ; }

    public Date getDateZak() { return DateZak; }

    public void setPasportS(String PasportS) {
        this.PasportS = PasportS;
    }

    public void setPasportN(String PasportN) {
        this.PasportN = PasportN;
    }

    public void setDaterCreate(Date DateCreate) {
        this.DateCreate = DateCreate;
    }

    public void setDateP(Date DateP) {
        this.DateP = DateP;
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

    public Date getDateCreate() {
        return DateCreate;
    }

    public Date getDateP() {
        return DateP;
    }
}
