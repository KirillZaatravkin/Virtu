package source.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import source.system.DAO.InsuredsDAO;
import source.system.model.Insureds;

import java.util.List;

/**
 * Created by admin on 26.07.2019.
 */
@Service
public class InsuredsService {
    @Autowired
    InsuredsDAO insuredsDAO;


    public List<Insureds> findAll() {
        return insuredsDAO.findAll();
    }

    public List<Insureds> findInsureds(String firstName, String lastName, String middleName) {
        return insuredsDAO.findInsureds(firstName, lastName, middleName);
    }
}
