package source.system.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "contract")
public class Contract {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int year;
    private int sum;
    private int sq;
    private Date date1;
    private Date date2;
    @Column(name = "date_calc")
    private Date dateCalc;
    private double prize;
    @Column(name = "date_conc")
    private Date dateConc;
    private String number;
    private String land;
    private String oblast;
    private String raion;
    private String punkt;
    private String street;
    private String house;
    private String korpus;
    private String stroenie;
    private String room;

    @Column(name = "property_id")
    private int proper;


    @ManyToOne
    @JoinColumn(name = "insured_id")
    private Insureds insureds;





    public Contract(){

    }

    public Contract(int year, int sum, int sq, String number, int proper,   Date date1, Date date2, Date dateCalc, double prize, Date dateConc, String land, String oblast, String raion, String punkt, String street, String house, String korpus, String stroenie, String room) {
        this.year = year;
        this.sum = sum;
        this.sq = sq;
        this.number = number;
        this.date1 = date1;
        this.date2 = date2;
        this.dateCalc = dateCalc;
        this.prize = prize;
        this.dateConc = dateConc;
        this.land = land;
        this.oblast = oblast;
        this.raion = raion;
        this.punkt = punkt;
        this.street = street;
        this.house = house;
        this.korpus = korpus;
        this.stroenie = stroenie;
        this.room = room;
        this.proper=proper;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getSq() {
        return sq;
    }

    public void setSq(int sq) {
        this.sq = sq;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public Date getDateCalc() {
        return dateCalc;
    }

    public void setDateCalc(Date dateCalc) {
        this.dateCalc = dateCalc;
    }

    public double getPrize() {
        return prize;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public Date getDateConc() {
        return dateConc;
    }

    public void setDateConc(Date dateConc) {
        this.dateConc = dateConc;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getOblast() {
        return oblast;
    }

    public void setOblast(String oblast) {
        this.oblast = oblast;
    }

    public String getRaion() {
        return raion;
    }

    public void setRaion(String raion) {
        this.raion = raion;
    }

    public String getPunkt() {
        return punkt;
    }

    public void setPunkt(String punkt) {
        this.punkt = punkt;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getKorpus() {
        return korpus;
    }

    public void setKorpus(String korpus) {
        this.korpus = korpus;
    }

    public String getStroenie() {
        return stroenie;
    }

    public void setStroenie(String stroenie) {
        this.stroenie = stroenie;
    }

    public String getRoom() {
        return room;
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }

    public int getProper() {
        return proper;
    }

    public void setProper(int proper) {
        this.proper = proper;
    }

    public Insureds getInsured() {
        return insureds;
    }

    public void setInsured(Insureds insured) {
        this.insureds = insured;
    }
    public void setRoom(String room) {
        this.room = room;
    }
}
