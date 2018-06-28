package source;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import source.system.model.ApPrist;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeString.toUpperCase;


public class ReadPrist {
    public static final int FirstNameCell = 2;
    public static final int NakazCell = 3;
    public static final int DateZakCell = 5;
    public static final int BirthDayCell = 6;
    public static final int FacktAddrCell = 7;
    public static final int ArrticleCell = 1;
    public static final int DatePCell = 0;
    public static final int PasportSCell = 8;
    public static final int PasportNCell = 10;

    private Connection connection;

    public ReadPrist() {
        connection = DbConnect.getConnection();
    }

    public List<ApPrist> ReadExel(String path) throws IOException {
        List<ApPrist> listapPrist = new ArrayList<ApPrist>();
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
          System.out.println(i++);
            HSSFRow row = (HSSFRow) rows.next();
            ApPrist apPrist = new ApPrist();
            String FIO = (row.getCell(FirstNameCell).getStringCellValue());
            String[] splitFIO = FIO.split(" ");
            if (splitFIO.length==3) {
                apPrist.setMiddleName(splitFIO[2]);
                apPrist.setFirstName(splitFIO[1]);
                apPrist.setLastName(splitFIO[0]);
            }
            else
            {
                apPrist.setFirstName(FIO);
                apPrist.setLastName("");
                apPrist.setMiddleName("");
            }
            int birthDayType = row.getCell(BirthDayCell).getCellType();
            if (birthDayType == 0) {
                Date birthDaySTR = row.getCell(BirthDayCell).getDateCellValue();
                apPrist.setBirthday(birthDaySTR);
            }
            int datePType = row.getCell(DatePCell).getCellType();
            if (datePType == 0) {
                Date dateP = row.getCell(DatePCell).getDateCellValue();
                apPrist.setDateP(dateP);
            }
            int dateZakType = row.getCell(DateZakCell).getCellType();
            if (dateZakType == 0) {
                Date dateZak = row.getCell(DateZakCell).getDateCellValue();
                apPrist.setDateZak(dateZak);
            }
            apPrist.setFacktAddr(row.getCell(FacktAddrCell).getStringCellValue());
            apPrist.setPasportN(row.getCell(PasportNCell).getStringCellValue());
            apPrist.setPasportS(row.getCell(PasportSCell).getStringCellValue());
            apPrist.setNakaz(row.getCell(NakazCell).getStringCellValue());
            String art=row.getCell(ArrticleCell).getStringCellValue();
            apPrist.setCact(String.valueOf(art.charAt(2)));
            String [] a=(art.split("ст."));
            apPrist.setArticle(a[1]);
            listapPrist.add(apPrist);
        }
        fs.close();
        return listapPrist;
    }



    public int WriteToBD(List<ApPrist> apPrists) {

        PreparedStatement ps = null;
        int j = 0;
        for (int i = 0; i < apPrists.size(); i++) {
            j++;
            try {
                ps = connection.prepareStatement("insert into ap_prist(firstname,lastname,middlename, facktaddr, article,cact, birthday, datep, pasportn,pasports, datezak, nakaz) values (?,?,?,?,?,?,?,?,?,?,?,?)");
                ps.setString(1, toUpperCase(apPrists.get(i).getFirstName()));
                ps.setString(2, toUpperCase(apPrists.get(i).getLastName()));
                ps.setString(3, toUpperCase(apPrists.get(i).getMiddleName()));
                ps.setString(4, toUpperCase(apPrists.get(i).getFacktAddr()));
                ps.setString(5, apPrists.get(i).getArticle());
                ps.setString(6, apPrists.get(i).getCact());

                if (apPrists.get(i).getBirthDay() != null) {
                    ps.setDate(7, new java.sql.Date(apPrists.get(i).getBirthDay().getTime()));
                } else {
                    ps.setNull(7, Types.DATE);
                }

                if (apPrists.get(i).getDateP() != null) {
                    ps.setDate(8, new java.sql.Date(apPrists.get(i).getDateP().getTime()));
                } else {
                    ps.setNull(8, Types.DATE);
                }
                ps.setString(9, apPrists.get(i).getPasportN());
                ps.setString(10, apPrists.get(i).getPasportS());

                if (apPrists.get(i).getDateZak() != null) {
                    ps.setDate(11, new java.sql.Date(apPrists.get(i).getDateZak().getTime()));
                } else {
                    ps.setNull(11, Types.DATE);
                }
                ps.setString(12, (apPrists.get(i).getNakaz()));
                ps.executeUpdate();

                if(true){
                    if (apPrists.get(i).getArticle().equals("12.8") || apPrists.get(i).getArticle().equals("12.26")  ) {
                        ps = connection.prepareStatement("insert into st_12_8_st_12_6(firstname,lastname,middlename, facktaddr, article,cact, birthday, datep, pasportn,pasports, datezak, nakaz) values (?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else  if (apPrists.get(i).getArticle().equals("6.1.1")  ) {
                        ps = connection.prepareStatement("insert into st_6_1_1(firstname,lastname,middlename, facktaddr, article,cact, birthday, datep, pasportn,pasports, datezak, nakaz) values (?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else if (apPrists.get(i).getArticle().equals("14.16") && apPrists.get(i).getCact().equals("2.1")) {
                        ps = connection.prepareStatement("insert into st_14_16(firstname,lastname,middlename, facktaddr, article,cact, birthday, datep, pasportn,pasports, datezak, nakaz) values (?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else if (apPrists.get(i).getArticle().equals("5.35.1")) {
                        ps = connection.prepareStatement("insert into st_5_35_1(firstname,lastname,middlename, facktaddr, article,cact, birthday, datep, pasportn,pasports, datezak, nakaz) values (?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else if (apPrists.get(i).getArticle().equals("7.27") && apPrists.get(i).getCact().equals("2")) {
                        ps = connection.prepareStatement("insert into st_7_27(firstname,lastname,middlename, facktaddr, article,cact, birthday, datep, pasportn,pasports, datezak, nakaz) values (?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else  if (apPrists.get(i).getArticle().equals("14.17.1")) {
                        ps = connection.prepareStatement("insert into st_14_17_1(firstname,lastname,middlename, facktaddr, article,cact, birthday, datep, pasportn,pasports, datezak, nakaz) values (?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else if (apPrists.get(i).getArticle().equals("20.2")) {
                        ps = connection.prepareStatement("insert into st_20_2(firstname,lastname,middlename, facktaddr, article,cact, birthday, datep, pasportn,pasports, datezak, nakaz) values (?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else if (apPrists.get(i).getArticle().equals("20.17")) {
                        ps = connection.prepareStatement("insert into st_20_17(firstname,lastname,middlename, facktaddr, article,cact, birthday, datep, pasportn,pasports, datezak, nakaz) values (?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else  if (apPrists.get(i).getArticle().equals("20.33")) {
                        ps = connection.prepareStatement("insert into st_20_33(firstname,lastname,middlename, facktaddr, article,cact, birthday, datep, pasportn,pasports, datezak, nakaz) values (?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else
                    {
                        continue;
                    }

                    ps.setString(1, toUpperCase(apPrists.get(i).getFirstName()));
                    ps.setString(2, toUpperCase(apPrists.get(i).getLastName()));
                    ps.setString(3, toUpperCase(apPrists.get(i).getMiddleName()));
                    ps.setString(4, toUpperCase(apPrists.get(i).getFacktAddr()));
                    ps.setString(5, apPrists.get(i).getArticle());
                    ps.setString(6, apPrists.get(i).getCact());

                    if (apPrists.get(i).getBirthDay() != null) {
                        ps.setDate(7, new java.sql.Date(apPrists.get(i).getBirthDay().getTime()));
                    } else {
                        ps.setNull(7, Types.DATE);
                    }

                    if (apPrists.get(i).getDateP() != null) {
                        ps.setDate(8, new java.sql.Date(apPrists.get(i).getDateP().getTime()));
                    } else {
                        ps.setNull(8, Types.DATE);
                    }
                    ps.setString(9, apPrists.get(i).getPasportN());
                    ps.setString(10, apPrists.get(i).getPasportS());

                    if (apPrists.get(i).getDateZak() != null) {
                        ps.setDate(11, new java.sql.Date(apPrists.get(i).getDateZak().getTime()));
                    } else {
                        ps.setNull(11, Types.DATE);
                    }
                    ps.setString(12, (apPrists.get(i).getNakaz()));
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