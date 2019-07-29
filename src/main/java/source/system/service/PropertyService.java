package source.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import source.system.DAO.PropertyDAO;
import source.system.model.Property;

import java.util.List;

@Service
public class PropertyService {
    @Autowired
    private PropertyDAO propertyDAO;

    public List<Property> findAllPropertys(){
        return propertyDAO.findAll();
    }
}
