package source;

import java.util.Date;

public class User {
    String login, password, fio, groups;
    int id, delete;
    Date reg;

    public int getId() {
        return id;
    }
    public String getGroups (){ return groups;}

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFio() {
        return fio;
    }

    public Date getReg() {
        return reg;
    }

    public int getDelete() {
        return delete;
    }

    public int setId(int id) {
        this.id = id;
        return id;
    }

    public String setLogin(String login) {
        this.login = login;
        return login;
    }

    public String setPassword(String password) {
        this.password = password;
        return password;
    }

    public String setFio(String fio) {
        this.fio = fio;
        return fio;
    }

    public String setGroups (String groups){
        this.groups=groups;
        return groups;
    }

    public Date setReg(Date reg) {
        this.reg = reg;
        return reg;
    }

    public int setDelete(int delete) {
        this.delete = delete;
        return delete;
    }

}