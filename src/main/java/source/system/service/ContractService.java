package source.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import source.system.DAO.ContractDAO;
import source.system.model.Contract;

import java.util.List;

@Service
public class ContractService {

    @Autowired
    private ContractDAO contractDAO;

    public double calcPrize(int interval, int sum, int year, int property, int sq) {

        double kTN = 1.7;
        switch (property) {
            case 2:
                kTN = 1.5;
                break;
            case 3:
                kTN = 1.3;
                break;
        }
        double kY = 1.6;
        if (year < 2000) {
            kY = 1.3;
        } else if (year < 2015) {
            kY = 2;
        }
        double kS = 1.5;
        if (sq < 50) {
            kY = 1.2;
        } else if (sq > 100) {
            kY = 2;
        }
        return (sum / interval) * kTN * kY * kS;
    }

    public void saveContract(Contract contract) {
        contractDAO.save(contract);
    }


    public void updateContract(Contract contract) {
        contractDAO.update(contract);
    }

    public Contract findId(int id) {
        return contractDAO.findId(id);
    }

    public List<Contract> findAllContracts(){
        return contractDAO.findAll();
    }
}
