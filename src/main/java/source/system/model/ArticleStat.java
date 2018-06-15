package source.system.model;

import java.util.Date;

/**
 * Created by кирюха on 13.06.2018.
 */
public class ArticleStat {
    String FirsName, LastName, MiddleName, FacktAddr, Cact;
    Date Birthday, DateP, DateP2;
    int kol;

    public Date getDateP() {
        return DateP;
    }

    public Date getDateP2() {
        return DateP2;
    }

    public int getKol() {
        return kol;
    }


    public void setDateP(Date dateP) {
        DateP = dateP;
    }

    public void setDateP2(Date dateP2) {
        DateP2 = dateP2;
    }

    public void setKol(int kol) {
        this.kol = kol;
    }

    public String getLastName() {

        return LastName;
    }

    public String getMiddleName() {
        return MiddleName;
    }


    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.MiddleName = middleName;
    }

    public void setFirsName(String firsName) {
        FirsName = firsName;
    }

    public void setFacktAddr(String facktAddr) {
        FacktAddr = facktAddr;
    }

    public void setCact(String cact) {
        Cact = cact;
    }

    public void setBirthday(Date birthday) {
        Birthday = birthday;
    }

    public String getFirsName() {

        return FirsName;
    }

    public String getFacktAddr() {
        return FacktAddr;
    }

    public String getCact() {
        return Cact;
    }

    public Date getBirthday() {
        return Birthday;
    }
}
