package source;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import source.system.model.ApGIBDD;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeString.toUpperCase;


public class ReadGibdd {
    public static final int FirstNameCell = 1;
    public static final int LastNameCell = 0;
    public static final int MiddleNameCell = 2;
    public static final int BirthDayCell = 3;
    public static final int FacktAddrCell = 4;
    public static final int FacktAddrCell1 = 5;
    public static final int FacktAddrCell2 = 6;
    public static final int FacktAddrCell3 = 7;
    public static final int FacktAddrCell4 = 8;
    public static final int FacktAddrCell5 = 9;
    public static final int ArrticleCell = 10;
    public static final int DatePCell = 11;
    public static final int DatePostCell = 15;
    public static final int DateZakCell = 16;
    public static final int NakazCell = 17;
    public static final int ProtokolNCell = 12;
    public static final int VodUdCell = 13;

    private Connection connection;

    public ReadGibdd() {
        connection = DbConnect.getConnection();
    }

    public List<ApGIBDD> ReadExel(String path) throws IOException {
        List<ApGIBDD> listapGIBDD = new ArrayList<ApGIBDD>();
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
            if((!(row.getCell(FirstNameCell).getStringCellValue()).equals("НЕУСТАНОВЛЕНО") &&  !(row.getCell(LastNameCell).getStringCellValue()).equals("НЕУСТАНОВЛЕНО") && !(row.getCell(MiddleNameCell).getStringCellValue()).equals("НЕУСТАНОВЛЕНО")) || (!(row.getCell(FirstNameCell).getStringCellValue()).equals("") &&  !(row.getCell(LastNameCell).getStringCellValue()).equals("") && !(row.getCell(MiddleNameCell).getStringCellValue()).equals(""))  ) {
                ApGIBDD apGIBDD = new ApGIBDD();
                apGIBDD.setFirstName((row.getCell(FirstNameCell).getStringCellValue()));
                apGIBDD.setProtokolN(( row.getCell(ProtokolNCell).getStringCellValue()));
                System.out.println(apGIBDD.getFirstName());
                apGIBDD.setLastName(row.getCell(LastNameCell).getStringCellValue());
                apGIBDD.setMiddleName(row.getCell(MiddleNameCell).getStringCellValue());

                 String nakaz = "";
          try {
              int admVid = (int) (row.getCell(NakazCell).getNumericCellValue());
              {

                  switch (admVid) {
                      case 1:
                          nakaz = "Предупреждение";
                          break;
                      case 2:
                          nakaz = "Штраф";
                          break;
                      case 3:
                          nakaz = "Штраф+конфискация";
                          break;
                      case 4:
                          nakaz = "Лишение прав";
                          break;
                      case 5:
                          nakaz = "Адм. арест";
                          break;
                      case 6:
                          nakaz = "Лишение прав+штраф";
                          break;
                      case 7:
                          nakaz = "Лишение прав+ конфискация";
                          break;
                      case 8:
                          nakaz = "Дисквалификация";
                          break;
                  }
              }
          }
              catch(java.lang.IllegalStateException e)
              {

              }
                    apGIBDD.setNakaz(nakaz);


                try {
                    apGIBDD.setVodUd(row.getCell(VodUdCell).getStringCellValue());
                    System.out.println(apGIBDD.getVodUd());
                } catch (NullPointerException e) {
                    System.out.print("rrr");
                }

                int dateBirthDayCell = row.getCell(BirthDayCell).getCellType();
                if (dateBirthDayCell == 1) {
                    String sBirthDay = row.getCell(BirthDayCell).getStringCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    try {
                        Date BirthDay = sdf.parse(sBirthDay);
                        apGIBDD.setBirthday(BirthDay);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                int datePType = row.getCell(DatePCell).getCellType();
                if (datePType == 1) {
                    String sDateP = row.getCell(DatePCell).getStringCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    try {
                        Date DateP = sdf.parse(sDateP);
                        apGIBDD.setDateP(DateP);
                        System.out.println(DateP);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                int datePostType = row.getCell(DatePostCell).getCellType();
                if (datePostType == 1) {
                    String sDatePost = row.getCell(DatePostCell).getStringCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    try {
                        Date DatePost = sdf.parse(sDatePost);
                        apGIBDD.setDatePost(DatePost);
                        System.out.println(DatePost);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                int dateZakType = row.getCell(DateZakCell).getCellType();
                if (datePostType == 1) {
                    String sDateZaK = row.getCell(DateZakCell).getStringCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    try {
                        Date DateZak = sdf.parse(sDateZaK);
                        apGIBDD.setDateZak(DateZak);
                        System.out.println(DateZak);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                String SS = "";
                try {
                    String s = row.getCell(FacktAddrCell).getStringCellValue();
                    SS = SS + "," + s;
                } catch (NullPointerException e) {

                }
                try {
                    String s1 = row.getCell(FacktAddrCell1).getStringCellValue();
                    SS = SS + "," + s1;
                } catch (NullPointerException e) {

                }
                try {
                    String s2 = row.getCell(FacktAddrCell2).getStringCellValue();
                    SS = SS + "," + s2;
                } catch (NullPointerException e) {

                }
                try {
                    String s3 = row.getCell(FacktAddrCell3).getStringCellValue();
                    SS = SS + "," + s3;
                } catch (NullPointerException e) {

                }
                try {
                    String s4 = row.getCell(FacktAddrCell4).getStringCellValue();
                    SS = SS + "," + s4;
                } catch (NullPointerException e) {
                }
                try {
                    String s5 = row.getCell(FacktAddrCell5).getStringCellValue();
                    SS = SS + "," + s5;
                } catch (NullPointerException e) {
                }
                apGIBDD.setFacktAddr(SS);
                String article = "";
                String chact = " ";
                String arS = row.getCell(ArrticleCell).getStringCellValue();
                boolean flag_4 = false;
                for (int y = 0; y < arS.length(); y++) {

                    if (arS.charAt(y) == 'ч' || arS.charAt(y) == 'Ч') {
                        for (int q = 0; q < y; q++) {
                            article = article + arS.charAt(q);
                            System.out.println(article);
                        }

                        for (int w = y + 2; w < arS.length(); w++) {
                            chact = chact + arS.charAt(w);
                        }
                        flag_4 = true;
                        System.out.println(article);
                        apGIBDD.setArticle(article);
                        break;
                    }

                }
                if (!flag_4) {
                    apGIBDD.setArticle(arS);
                }

                if (!chact.equals("")) {
                    apGIBDD.setCact(chact);
                }


                listapGIBDD.add(apGIBDD);
            }
                i++;

        }
        System.out.println(i);
        fs.close();
        return listapGIBDD;
    }

    public int WriteToBD(List<ApGIBDD> apGIBDDList) {

        PreparedStatement ps = null;

        int j = 0;
        for (int i = 0; i < apGIBDDList.size(); i++) {
            j++;
            try {
                ps = connection.prepareStatement("insert into ap_gibdd(firstname,lastname,middlename, facktaddr, article,cact, birthday, datep, vodud, protokoln, nakaz, datezak, datepost) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                ps.setString(1, toUpperCase(apGIBDDList.get(i).getFirstName()));
                ps.setString(2, toUpperCase(apGIBDDList.get(i).getLastName()));
                ps.setString(3, toUpperCase(apGIBDDList.get(i).getMiddleName()));
                ps.setString(4,toUpperCase(apGIBDDList.get(i).getFacktAddr()));
                ps.setString(5, apGIBDDList.get(i).getArticle());
                ps.setString(6, apGIBDDList.get(i).getCact());


                if (apGIBDDList.get(i).getBirthDay() != null) {
                    ps.setDate(7, new java.sql.Date(apGIBDDList.get(i).getBirthDay().getTime()));
                } else {
                    ps.setNull(7, Types.DATE);
                }

                if (apGIBDDList.get(i).getDateP() != null) {
                    ps.setDate(8, new java.sql.Date(apGIBDDList.get(i).getDateP().getTime()));
                } else {
                    ps.setNull(8, Types.DATE);
                }
                ps.setString(9, apGIBDDList.get(i).getVodUd());
                ps.setString(10, apGIBDDList.get(i).getProtokolN());
                ps.setString(11, apGIBDDList.get(i).getNakaz());

                if (apGIBDDList.get(i).getDateZak() != null) {
                    ps.setDate(12, new java.sql.Date(apGIBDDList.get(i).getDateZak().getTime()));
                } else {
                    ps.setNull(12, Types.DATE);
                }

                if (apGIBDDList.get(i).getDatePost() != null) {
                    ps.setDate(13, new java.sql.Date(apGIBDDList.get(i).getDatePost().getTime()));
                } else {
                    ps.setNull(13, Types.DATE);
                }

                ps.executeUpdate();

                if(true)
                {
                    if (apGIBDDList.get(i).getArticle().equals("12.8") || apGIBDDList.get(i).getArticle().equals("12.26")  ) {
                        ps = connection.prepareStatement("insert into st_12_8_st_12_6 (lastname,firstname,middlename,facktaddr,article,cact,birthday, datep, vodud, protokoln, nakaz, datezak, datepost ) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else  if (apGIBDDList.get(i).getArticle().equals("6.1.1")  ) {
                        ps = connection.prepareStatement("insert into st_6_1_1 (lastname,firstname,middlename,facktaddr,article,cact,birthday, datep, vodud, protokoln, nakaz, datezak, datepost ) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else if (apGIBDDList.get(i).getArticle().equals("14.16") && apGIBDDList.get(i).getCact().equals("2.1")) {
                        ps = connection.prepareStatement("insert into st_14_16 (lastname,firstname,middlename,facktaddr,article,cact,birthday, datep, vodud, protokoln, nakaz, datezak, datepost ) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else if (apGIBDDList.get(i).getArticle().equals("5.35.1")) {
                        ps = connection.prepareStatement("insert into st_5_35_1(lastname,firstname,middlename,facktaddr,article,cact,birthday, datep, vodud, protokoln, nakaz, datezak, datepost )values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else if (apGIBDDList.get(i).getArticle().equals("7.27") && apGIBDDList.get(i).getCact().equals("2")) {
                        ps = connection.prepareStatement("insert into st_7_27(lastname,firstname,middlename,facktaddr,article,cact,birthday, datep, vodud, protokoln, nakaz, datezak, datepost ) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else  if (apGIBDDList.get(i).getArticle().equals("14.17.1")) {
                        ps = connection.prepareStatement("insert into st_14_17_1(lastname,firstname,middlename,facktaddr,article,cact,birthday, datep, vodud, protokoln, nakaz, datezak, datepost ) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else if (apGIBDDList.get(i).getArticle().equals("20.2")) {
                        ps = connection.prepareStatement("insert into st_20_2(lastname,firstname,middlename,facktaddr,article,cact,birthday, datep, vodud, protokoln, nakaz, datezak, datepost )values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else if (apGIBDDList.get(i).getArticle().equals("20.17")) {
                        ps = connection.prepareStatement("insert into st_20_17(lastname,firstname,middlename,facktaddr,article,cact,birthday, datep, vodud, protokoln, nakaz, datezak, datepost )values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else  if (apGIBDDList.get(i).getArticle().equals("20.33")) {
                        ps = connection.prepareStatement("insert into st_20_33(lastname,firstname,middlename,facktaddr,article,cact,birthday, datep, vodud, protokoln, nakaz, datezak, datepost ) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    }
                    else
                    {
                        continue;
                    }

                    ps.setString(1, toUpperCase(apGIBDDList.get(i).getLastName()));
                    ps.setString(2, toUpperCase(apGIBDDList.get(i).getFirstName()));
                    ps.setString(3, toUpperCase(apGIBDDList.get(i).getMiddleName()));
                    ps.setString(4,toUpperCase(apGIBDDList.get(i).getFacktAddr()));
                    ps.setString(5, apGIBDDList.get(i).getArticle());
                    ps.setString(6, apGIBDDList.get(i).getCact());


                    if (apGIBDDList.get(i).getBirthDay() != null) {
                        ps.setDate(7, new java.sql.Date(apGIBDDList.get(i).getBirthDay().getTime()));
                    } else {
                        ps.setNull(7, Types.DATE);
                    }

                    if (apGIBDDList.get(i).getDateP() != null) {
                        ps.setDate(8, new java.sql.Date(apGIBDDList.get(i).getDateP().getTime()));
                    } else {
                        ps.setNull(8, Types.DATE);
                    }
                    ps.setString(9, apGIBDDList.get(i).getVodUd());
                    ps.setString(10, apGIBDDList.get(i).getProtokolN());
                    ps.setString(11, apGIBDDList.get(i).getNakaz());

                    if (apGIBDDList.get(i).getDateZak() != null) {
                        ps.setDate(12, new java.sql.Date(apGIBDDList.get(i).getDateZak().getTime()));
                    } else {
                        ps.setNull(12, Types.DATE);
                    }

                    if (apGIBDDList.get(i).getDatePost() != null) {
                        ps.setDate(13, new java.sql.Date(apGIBDDList.get(i).getDatePost().getTime()));
                    } else {
                        ps.setNull(13, Types.DATE);
                    }

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