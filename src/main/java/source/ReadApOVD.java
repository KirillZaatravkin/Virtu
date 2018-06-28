package source;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import source.system.model.ApOVD;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import static jdk.nashorn.internal.objects.NativeString.toUpperCase;


public class ReadApOVD {
    public static final int FirstNameCell = 3;
    public static final int LastNameCell = 4;
    public static final int MiddleNameCell = 5;
    public static final int BirthDayCell = 6;
    public static final int FacktAddrCell = 11;
    public static final int ResAddrCell = 12;
    public static final int ArrticleCell = 13;
    public static final int CactCell = 14;
    public static final int DatePCell = 0;
    public static final int DateZakCell = 1;
    public static final int OrganCell = 2;
    public static final int PasportSCell = 7;
    public static final int PasportNCell = 8;
    private Connection connection;

    public ReadApOVD() {
        connection = DbConnect.getConnection();
    }

    public List<ApOVD> ReadExel(String path) throws IOException {
        List<ApOVD> listapOVD = new ArrayList<ApOVD>();
        File file = new File(path);
        POIFSFileSystem fs = new POIFSFileSystem(file);
        HSSFWorkbook workBook = new HSSFWorkbook(fs);
        HSSFSheet sheet = workBook.getSheetAt(0);
        Iterator<Row> rows = sheet.rowIterator();
        if (rows.hasNext()) {
            rows.next();
        }
        int i = 0;
        while (rows.hasNext()) {
            HSSFRow row = (HSSFRow) rows.next();
            ApOVD apOVD = new ApOVD();
            apOVD.setFirstName((row.getCell(FirstNameCell).getStringCellValue()));
            apOVD.setLastName(row.getCell(LastNameCell).getStringCellValue());
            apOVD.setMiddleName(row.getCell(MiddleNameCell).getStringCellValue());

            int birthDayType = row.getCell(BirthDayCell).getCellType();
            if (birthDayType == 0) {
                Date birthDaySTR = row.getCell(BirthDayCell).getDateCellValue();
                apOVD.setBirthday(birthDaySTR );
            }

            int DateZakType = row.getCell(DateZakCell).getCellType();
            if (DateZakType == 0) {
                Date dateZak = row.getCell(DateZakCell).getDateCellValue();
                System.out.println(dateZak);
                apOVD.setDateZak(dateZak);
            }

            int datePType = row.getCell(DatePCell).getCellType();
            if (datePType == 0) {
                Date datePSTR = row.getCell(DatePCell).getDateCellValue();
                apOVD.setDateP(datePSTR);
            }
            apOVD.setFacktAddr(row.getCell(FacktAddrCell).getStringCellValue());
            apOVD.setResAddr(row.getCell(ResAddrCell).getStringCellValue());
            apOVD.setOrgan(row.getCell(OrganCell).getStringCellValue());
            apOVD.setArticle(row.getCell(ArrticleCell).getStringCellValue());
            try{
              apOVD.setPasportS(row.getCell(PasportSCell).getStringCellValue());
              apOVD.setPasportN(row.getCell(PasportNCell).getStringCellValue());
            }
            catch (java.lang.NullPointerException e)
            {

            }
            try{
                {
                    apOVD.setCact(row.getCell(CactCell).getStringCellValue());
                }

            }
            catch (java.lang.NullPointerException e)
            {

            }

            listapOVD.add(apOVD);
            i++;
        }
        System.out.println(i);
        fs.close();
        return listapOVD;
    }

    public int WriteToBD(List<ApOVD> apOVDList) {

        PreparedStatement ps = null;

        int j = 0;
        for (int i = 0; i < apOVDList.size(); i++) {
            j++;
            try {
                ps = connection.prepareStatement("insert into ap_ovd(lastname,firstname,middlename,facktaddr,resaddr,article,birthday, datep,pasports,pasportn,cact,organ,datezak ) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                ps.setString(1, toUpperCase(apOVDList.get(i).getFirstName()));
                ps.setString(2, toUpperCase(apOVDList.get(i).getLastName()));
                ps.setString(3, toUpperCase(apOVDList.get(i).getMiddleName()));
                ps.setString(4, toUpperCase(apOVDList.get(i).getFacktAddr()));
                ps.setString(5, toUpperCase(apOVDList.get(i).getResAddr()));
                ps.setString(6, apOVDList.get(i).getArticle());


                if (apOVDList.get(i).getBirthDay() != null) {
                    ps.setDate(7, new java.sql.Date(apOVDList.get(i).getBirthDay().getTime()));
                } else {
                    ps.setNull(7, Types.DATE);
                }
               if (apOVDList.get(i).getDateP() != null) {
                    ps.setDate(8, new java.sql.Date(apOVDList.get(i).getDateP().getTime()));
                } else {
                    ps.setNull(8, Types.DATE);
                }
                ps.setString(9, apOVDList.get(i).getPasportS());
                ps.setString(10, apOVDList.get(i).getPasportN());
                ps.setString(11,apOVDList.get(i).getCact());
                ps.setString(12,apOVDList.get(i).getOrgan());

                if (apOVDList.get(i).getDateZak() != null) {
                    ps.setDate(13, new java.sql.Date(apOVDList.get(i).getDateZak().getTime()));
                } else {
                    ps.setNull(13, Types.DATE);
                }
                ps.executeUpdate();


                if(true) {

                    if (apOVDList.get(i).getArticle().equals("12.8") || apOVDList.get(i).getArticle().equals("12.26")  ) {
                        ps = connection.prepareStatement("insert into st_12_8_st_12_6 (lastname,firstname,middlename,facktaddr,article,birthday, datep,pasports,pasportn,cact,organ,datezak, resaddr ) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else  if (apOVDList.get(i).getArticle().equals("6.1.1")  ) {
                        ps = connection.prepareStatement("insert into st_6_1_1 (lastname,firstname,middlename,facktaddr,article,birthday, datep,pasports,pasportn,cact,organ,datezak, resaddr ) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else if (apOVDList.get(i).getArticle().equals("14.16") && apOVDList.get(i).getCact().equals("2.1")) {
                        ps = connection.prepareStatement("insert into st_14_16 (lastname,firstname,middlename,facktaddr,article,birthday, datep,pasports,pasportn,cact,organ,datezak, resaddr ) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else if (apOVDList.get(i).getArticle().equals("5.35.1")) {
                        ps = connection.prepareStatement("insert into st_5_35_1(lastname,firstname,middlename,facktaddr,article,birthday, datep,pasports,pasportn,cact,organ,datezak, resaddr ) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else if (apOVDList.get(i).getArticle().equals("7.27") && apOVDList.get(i).getCact().equals("2")) {
                        ps = connection.prepareStatement("insert into st_7_27(lastname,firstname,middlename,facktaddr,article,birthday, datep,pasports,pasportn,cact,organ,datezak , resaddr) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                   else  if (apOVDList.get(i).getArticle().equals("14.17.1")) {
                        ps = connection.prepareStatement("insert into st_14_17_1(lastname,firstname,middlename,facktaddr,article,birthday, datep,pasports,pasportn,cact,organ,datezak , resaddr) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else if (apOVDList.get(i).getArticle().equals("20.2")) {
                        ps = connection.prepareStatement("insert into st_20_2(lastname,firstname,middlename,facktaddr,article,birthday, datep,pasports,pasportn,cact,organ,datezak , resaddr) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else if (apOVDList.get(i).getArticle().equals("20.17")) {
                        ps = connection.prepareStatement("insert into st_20_17(lastname,firstname,middlename,facktaddr,article,birthday, datep,pasports,pasportn,cact,organ,datezak , resaddr) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                   else  if (apOVDList.get(i).getArticle().equals("20.33")) {
                        ps = connection.prepareStatement("insert into st_20_33(lastname,firstname,middlename,facktaddr,article,birthday, datep,pasports,pasportn,cact,organ,datezak , resaddr) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else
                    {
                        continue;
                    }

                    ps.setString(1, toUpperCase(apOVDList.get(i).getFirstName()));
                    ps.setString(2, toUpperCase(apOVDList.get(i).getLastName()));
                    ps.setString(3, toUpperCase(apOVDList.get(i).getMiddleName()));
                    ps.setString(4, toUpperCase(apOVDList.get(i).getFacktAddr()));
                    ps.setString(5, apOVDList.get(i).getArticle());


                    if (apOVDList.get(i).getBirthDay() != null) {
                        ps.setDate(6, new java.sql.Date(apOVDList.get(i).getBirthDay().getTime()));
                    } else {
                        ps.setNull(6, Types.DATE);
                    }
                    if (apOVDList.get(i).getDateP() != null) {
                        ps.setDate(7, new java.sql.Date(apOVDList.get(i).getDateP().getTime()));
                    } else {
                        ps.setNull(7, Types.DATE);
                    }
                    ps.setString(8, apOVDList.get(i).getPasportS());
                    ps.setString(9, apOVDList.get(i).getPasportN());
                    ps.setString(10,apOVDList.get(i).getCact());
                    ps.setString(11,apOVDList.get(i).getOrgan());

                    if (apOVDList.get(i).getDateZak() != null) {
                        ps.setDate(12, new java.sql.Date(apOVDList.get(i).getDateZak().getTime()));
                    } else {
                        ps.setNull(12, Types.DATE);
                    }
                    ps.setString(13, toUpperCase(apOVDList.get(i).getResAddr()));
                    ps.executeUpdate();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        DbConnect.close(connection);
        return j;
    }
}