package source.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import source.system.dao.MaskDAO;
import source.system.model.Mask;

import java.util.List;

/**
 * Created by кирюха on 29.03.2018.
 */
@Service
public class MaskService {
    @Autowired
    private MaskDAO maskDAO;

    public List<Mask> getAllMask(){return maskDAO.getAllMask();}

    public Mask getMask(int id){return maskDAO.getMask(id);}

    public void addMask(Mask mask){maskDAO.addMask(mask);}

    public Mask newMask (String mask, String title, int id){ return maskDAO.newMask(mask,title,id);}

    public void updateMask (Mask mask){maskDAO.updateMask(mask);}

}
