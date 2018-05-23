package source;


/**
 * Created by кирюха on 22.11.2017.
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import source.system.dao.SettingsDao;
import source.system.model.ApGIBDD;
import source.system.model.ApGIBDDStat;
import source.system.model.Settings;


public class CreateWord {

    public void WordFindFace(List<ApOVD> apOVDs,List<ApGIBDD> apGIBDDs) {
        String lastname = apOVDs.get(0).getLastName();
        String firstname = apOVDs.get(0).getFirstName();
        String middlename = apOVDs.get(0).getMiddleName();
        String birthday = String.valueOf(apOVDs.get(0).getBirthDay());

        XWPFDocument document = new XWPFDocument();

        CTSectPr ctSectPr = document.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(document, ctSectPr);

        XWPFParagraph bodyParagraph = document.createParagraph();
        bodyParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun paragraphConfig = bodyParagraph.createRun();
        paragraphConfig.setItalic(true);
        paragraphConfig.setFontSize(25);
        paragraphConfig.setColor("06357a");
        paragraphConfig.setText(lastname + " "+firstname +" "+ middlename + " "+birthday);

        XWPFTable table = document.createTable();


        CTP ctpHeaderModel = createHeaderModel( lastname + " "+firstname +" "+ middlename +" "+ birthday);

        XWPFParagraph headerParagraph = new XWPFParagraph(ctpHeaderModel, document);
        headerFooterPolicy.createHeader(
                XWPFHeaderFooterPolicy.DEFAULT,
                new XWPFParagraph[]{headerParagraph}
        );


        XWPFTableRow tableRowOne = table.getRow(0);
        tableRowOne.getCell(0).setText("№");
        tableRowOne.addNewTableCell().setText("Статья КоАП");
        tableRowOne.addNewTableCell().setText("Часть статьи");
        tableRowOne.addNewTableCell().setText("Дата постановления об АП");
        tableRowOne.addNewTableCell().setText("Место регистрации");
        tableRowOne.addNewTableCell().setText("Место проживания");
        tableRowOne.addNewTableCell().setText("Дата занесения в БД");
        tableRowOne.addNewTableCell().setText("Примечание");

        int i;
        for (  i = 0; i < apOVDs.size(); i++) {
            XWPFTableRow tableRow = table.createRow();
            tableRow.getCell(0).setText(String.valueOf(i+1));
            tableRow.getCell(1).setText(apOVDs.get(i).getArticle());
            tableRow.getCell(2).setText(apOVDs.get(i).getCact());
            tableRow.getCell(3).setText(String.valueOf(apOVDs.get(i).getDateP()));
            tableRow.getCell(4).setText(apOVDs.get(i).getResAddr());
            tableRow.getCell(5).setText(apOVDs.get(i).getFacktAddr());
            tableRow.getCell(6).setText(String.valueOf(apOVDs.get(i).getDateCreate()));
            tableRow.getCell(7).setText("Согласно данным ОВД");
        }



        for (int j = 0; j < apGIBDDs.size(); j++) {
            XWPFTableRow tableRow = table.createRow();
            tableRow.getCell(0).setText(String.valueOf(i+1));
            tableRow.getCell(1).setText(apGIBDDs.get(j).getArticle());
            tableRow.getCell(2).setText(apGIBDDs.get(j).getCact());
            tableRow.getCell(3).setText(String.valueOf(apGIBDDs.get(j).getDateP()));
            tableRow.getCell(5).setText(apGIBDDs.get(j).getFacktAddr());
            tableRow.getCell(6).setText(String.valueOf(apGIBDDs.get(j).getDateCreate()));
            tableRow.getCell(7).setText("Согласно данным ГИБДД");
        }

        try {
            SettingsDao settingsDao =new SettingsDao();
            Settings sett=new Settings();
           sett= settingsDao.getSetting(2);


            FileOutputStream outputStream = new FileOutputStream(sett.getSettings()+lastname + " "+firstname +" "+ middlename +" "+ birthday+".docx");
            document.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static CTP createFooterModel(String footerContent) {
        CTP ctpFooterModel = CTP.Factory.newInstance();
        CTR ctrFooterModel = ctpFooterModel.addNewR();
        CTText cttFooter = ctrFooterModel.addNewT();
        cttFooter.setStringValue(footerContent);
        return ctpFooterModel;
    }

    private static CTP createHeaderModel(String headerContent) {
        CTP ctpHeaderModel = CTP.Factory.newInstance();
        CTR ctrHeaderModel = ctpHeaderModel.addNewR();
        CTText cttHeader = ctrHeaderModel.addNewT();
        cttHeader.setStringValue(headerContent);
        return ctpHeaderModel;
    }


    public void Resediv (List<ApOVDStat> apOVDs,   List<ApGIBDDStat> apGIBDDs, int kolNar, int kolFace, int kolRes) {

        Date date=new Date();
        DateFormat dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT);
        String textDate = dateFormat.format(date);
        Date  currentDate = new Date();
        Long longDate = currentDate.getTime();
        XWPFDocument document = new XWPFDocument();
        CTSectPr ctSectPr = document.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(document, ctSectPr);

        XWPFParagraph bodyParagraph = document.createParagraph();
        bodyParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun paragraphConfig = bodyParagraph.createRun();
        paragraphConfig.setItalic(true);
        paragraphConfig.setFontSize(25);
        paragraphConfig.setColor("06357a");
        paragraphConfig.setText( "Отчет. Дата составления отчета: "+textDate);

        XWPFTable table1 = document.createTable();
        XWPFTableRow tableRowOne1 = table1.getRow(0);
        tableRowOne1.getCell(0).setText("Общее кол-во правонарушений на текущий год");
        tableRowOne1.addNewTableCell().setText("Общее кол-во лиц, привлеченных к ответственности за текущий год");
        tableRowOne1.addNewTableCell().setText("Выявлено правонарушителей-рецидивистов по заданному поиску");
        XWPFTableRow tableRow1 = table1.createRow();
        tableRow1.getCell(0).setText(String.valueOf(kolNar));
        tableRow1.getCell(1).setText(String.valueOf(kolFace));
        tableRow1.getCell(2).setText(String.valueOf(kolRes));

        XWPFParagraph bodyParagraph1 = document.createParagraph();
        bodyParagraph1.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun paragraphConfig1 = bodyParagraph1.createRun();
        paragraphConfig1.setItalic(true);
        paragraphConfig1.setFontSize(25);
        paragraphConfig1.setColor("06357a");
        paragraphConfig1.setText( "Список рецидивистов");

        XWPFTable table = document.createTable();
        XWPFTableRow tableRowOne = table.getRow(0);
        tableRowOne.getCell(0).setText("№");
        tableRowOne.addNewTableCell().setText("Фамилия");
        tableRowOne.addNewTableCell().setText("Имя");
        tableRowOne.addNewTableCell().setText("Отчество");
        tableRowOne.addNewTableCell().setText("Дата рождения");
        tableRowOne.addNewTableCell().setText("Статья КоАП");
        tableRowOne.addNewTableCell().setText("Часть статьи");
        tableRowOne.addNewTableCell().setText("Дата последнего постановления");
        tableRowOne.addNewTableCell().setText("Кол-во АП");
        tableRowOne.addNewTableCell().setText("Примичание");

        for (int i = 0; i < apOVDs.size(); i++) {
            String s="Согласно базам ОВД";
            XWPFTableRow tableRow = table.createRow();
            tableRow.getCell(0).setText(String.valueOf(i+1));
            tableRow.getCell(1).setText(apOVDs.get(i).getLastName());
            tableRow.getCell(2).setText(String.valueOf(apOVDs.get(i).getFirstName()));
            tableRow.getCell(3).setText(apOVDs.get(i).getMiddleName());
            tableRow.getCell(4).setText(String.valueOf(apOVDs.get(i).getBirthDay()));
            tableRow.getCell(5).setText(apOVDs.get(i).getArticle());
            tableRow.getCell(6).setText(apOVDs.get(i).getCact());
            tableRow.getCell(7).setText(String.valueOf(apOVDs.get(i).getDateP()));
            tableRow.getCell(8).setText(String.valueOf(apOVDs.get(i).getKol()));
            tableRow.getCell(9).setText(s);
        }
        for (int i = 0; i < apGIBDDs.size(); i++) {
            String s="Согласно базам ГИБДД";
            XWPFTableRow tableRow = table.createRow();
            tableRow.getCell(0).setText(String.valueOf(i+1));
            tableRow.getCell(1).setText(apGIBDDs.get(i).getLastName());
            tableRow.getCell(2).setText(String.valueOf(apGIBDDs.get(i).getFirstName()));
            tableRow.getCell(3).setText(apGIBDDs.get(i).getMiddleName());
            tableRow.getCell(4).setText(String.valueOf(apGIBDDs.get(i).getBirthDay()));
            tableRow.getCell(5).setText(apGIBDDs.get(i).getArticle());
            tableRow.getCell(6).setText(apGIBDDs.get(i).getCact());
            tableRow.getCell(7).setText(String.valueOf(apGIBDDs.get(i).getDateP()));
            tableRow.getCell(8).setText(String.valueOf(apGIBDDs.get(i).getKol()));
            tableRow.getCell(9).setText(s);
        }



        try {
            SettingsDao settingsDao =new SettingsDao();
            Settings sett=new Settings();
            sett= settingsDao.getSetting(2);



            FileOutputStream outputStream = new FileOutputStream(sett.getSettings()+textDate+""+longDate+".docx");
            document.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
