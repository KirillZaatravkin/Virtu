package source.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import source.system.dao.CurrentDate;
import source.system.dao.LoginAction;

/**
 * Created by кирюха on 23.02.2018.
 */

   @Service
public class IndexService {

       @Autowired
    private LoginAction loginAction;

       public String FlagLogin (String password, String login)
       {
           return loginAction.FlagLogin(password,login);
       }

    @Autowired
    private CurrentDate currentDate;

    public String CurYear()
    {
        return currentDate.CurYear();
    }

    public String CurMonth()
    {
        return  currentDate.CurMonth();
    }

    public String LastLoad()
    {
        return  currentDate.LastLoad();
    }

}

