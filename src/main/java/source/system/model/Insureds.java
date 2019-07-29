package source.system.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "insureds")
public class Insureds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firsName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "pasport_s")
    private String pasportS;

    @Column(name = "pasport_n")
    private String pasportN;

    @OneToMany(mappedBy = "insured", cascade = CascadeType.ALL)
    private List<Contract> contracts;

    public  Insureds () {

    }

    public Insureds (String lastName,String middleName, String firsName, String pasportN,String pasportS){
        this.firsName=firsName;
        this.lastName=lastName;
        this.middleName=middleName;
        this.pasportN=pasportN;
        this.pasportS=pasportS;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirsName() {
        return firsName;
    }

    public void setFirsName(String firsName) {
        this.firsName = firsName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPasportS() {
        return pasportS;
    }

    public void setPasportS(String pasportS) {
        this.pasportS = pasportS;
    }

    public String getPasportN() {
        return pasportN;
    }

    public void setPasportN(String pasportN) {
        this.pasportN = pasportN;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }
}