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
            ApGIBDD apGIBDD = new ApGIBDD();
            apGIBDD.setFirstName((row.getCell(FirstNameCell).getStringCellValue()));
            System.out.println(apGIBDD.getFirstName());
            apGIBDD.setLastName(row.getCell(LastNameCell).getStringCellValue());
            apGIBDD.setMiddleName(row.getCell(MiddleNameCell).getStringCellValue());
            try {
                apGIBDD.setVodUd(row.getCell(VodUdCell).getStringCellValue());
                System.out.println(apGIBDD.getVodUd());
            }catch ( NullPointerException e)
            {
                System.out.print("rrr");
            }

            int dateBirthDayCell = row.getCell(BirthDayCell).getCellType();
            if (dateBirthDayCell == 1) {
                String sBirthDay=row.getCell(BirthDayCell).getStringCellValue();
                String BirthDay1 =sBirthDay.substring(0,4);
                String BirthDay2 =sBirthDay.substring(5,6);
                String BirthDay3 =sBirthDay.substring(7,8);
                sBirthDay=BirthDay1+"."+BirthDay2+"."+BirthDay3;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                try {
                    Date BirthDay=sdf.parse(sBirthDay);
                    apGIBDD.setBirthday(BirthDay);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            int datePType = row.getCell(DatePCell).getCellType();
            if (datePType == 1) {
               String sDateP=row.getCell(DatePCell).getStringCellValue();
                String DateP1 =sDateP.substring(0,4);
                String DateP2 =sDateP.substring(5,6);
                String DateP3 =sDateP.substring(7,8);
                sDateP=DateP1+"."+DateP2+"."+DateP3;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                try {
                    Date DateP=sdf.parse(sDateP);
                    apGIBDD.setDateP(DateP);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            String SS="";
            try {
                String s = row.getCell(FacktAddrCell).getStringCellValue();
                SS= SS+","+s;
            }catch ( NullPointerException e)
            {
                System.out.print("sd");
            }
            try {
                String s1 = row.getCell(FacktAddrCell1).getStringCellValue();
                SS= SS+","+s1;
            }catch ( NullPointerException e)
            {
                System.out.print("sd");
            }
            try {
                String s2 = row.getCell(FacktAddrCell2).getStringCellValue();
                SS= SS+","+s2;
            }catch ( NullPointerException e)
            {
                System.out.print("sd");
            }
            try {
                String s3 = row.getCell(FacktAddrCell3).getStringCellValue();
                SS= SS+","+s3;
            }catch ( NullPointerException e)
            {
                System.out.print("sd");
            }
            try {
                String s4 = row.getCell(FacktAddrCell4).getStringCellValue();
                SS= SS+","+s4;
            }catch ( NullPointerException e)
            {
                System.out.print("sd");
            }
            try {
                String s5 = row.getCell(FacktAddrCell5).getStringCellValue();
                SS= SS+","+s5;
            }catch ( NullPointerException e)
            {
                System.out.print("sd");
            }
            apGIBDD.setFacktAddr(SS);
            String article="";
            String chact="";
            String arS=row.getCell(ArrticleCell).getStringCellValue();
            boolean flag_4=false;
            for (int y = 0;y < arS.length(); y++){

                if(arS.charAt(y)=='ч' || arS.charAt(y)=='Ч')
                {
                   for(int q=0; q<y; q++)
                   {
                       article=article+arS.charAt(q);
                       System.out.println(article);

                   }

                   for(int w= y+2; w<arS.length(); w++ )
                   {
                       chact=chact+arS.charAt(w);
                   }
                   flag_4=true;
                    System.out.println(article);
                   apGIBDD.setArticle(article);
                   break;
                }

            }
            if(!flag_4)
            {
                apGIBDD.setArticle(arS);
            }

            if (!chact.equals(""))
            {
                apGIBDD.setCact(chact);
            }


            listapGIBDD.add(apGIBDD);
            i++;
        }
        System.out.println(i);
        return listapGIBDD;
    }

    public int WriteToBD(List<ApGIBDD> apGIBDDList) {

        PreparedStatement ps = null;

        int j = 0;
        for (int i = 0; i < apGIBDDList.size(); i++) {
            j++;
            try {
                ps = connection.prepareStatement("insert into ap_gibdd(firstname,lastname,middlename, facktaddr, article,cact, birthday, datep, vodud) values (?,?,?,?,?,?,?,?,?)");
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
                //   java.util.Date udate= new java.util.Date();
                //  java.sql.Date sdate=new java.sql.Date(udate.getTime());
                // ps.setDate(7,sdate);

                if (apGIBDDList.get(i).getDateP() != null) {
                    ps.setDate(8, new java.sql.Date(apGIBDDList.get(i).getDateP().getTime()));
                } else {
                    ps.setNull(8, Types.DATE);
                }
                ps.setString(9, apGIBDDList.get(i).getVodUd());

                ps.executeUpdate();

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