package source;


/**
 * Created by кирюха on 22.11.2017.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;


public class CreateWord {

    public void ApOVDWord(List<ApOVD> apOVDs) {
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
        paragraphConfig.setText(lastname + firstname + middlename + birthday);

        XWPFTable table = document.createTable();


        CTP ctpHeaderModel = createHeaderModel( lastname + firstname + middlename + birthday);

        XWPFParagraph headerParagraph = new XWPFParagraph(ctpHeaderModel, document);
        headerFooterPolicy.createHeader(
                XWPFHeaderFooterPolicy.DEFAULT,
                new XWPFParagraph[]{headerParagraph}
        );


        XWPFTableRow tableRowOne = table.getRow(0);
        tableRowOne.getCell(0).setText("№");
        tableRowOne.addNewTableCell().setText("Статья КоАП");
        tableRowOne.addNewTableCell().setText("Дата АП");
        tableRowOne.addNewTableCell().setText("Место регестрации");
        tableRowOne.addNewTableCell().setText("Место проживания");
        tableRowOne.addNewTableCell().setText("Дата занесения в БД");


        for (int i = 0; i < apOVDs.size(); i++) {
            XWPFTableRow tableRow = table.createRow();
            tableRow.getCell(0).setText(String.valueOf(i));
            tableRow.getCell(1).setText(apOVDs.get(i).getArticle());
            tableRow.getCell(2).setText(String.valueOf(apOVDs.get(i).getDateP()));
            tableRow.getCell(3).setText(apOVDs.get(i).getResAddr());
            tableRow.getCell(4).setText(apOVDs.get(i).getFacktAddr());
            tableRow.getCell(5).setText(String.valueOf(apOVDs.get(i).getDateCreate()));
        }
        try {
            Service service =new Service();
            Settings sett=new Settings();
           sett= service.getSetting(2);


            FileOutputStream outputStream = new FileOutputStream(sett.getSettings()+lastname+".docx");
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


    public void Resediv (List<ApOVDStat> apOVDs) {


        XWPFDocument document = new XWPFDocument();
        CTSectPr ctSectPr = document.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(document, ctSectPr);

        XWPFParagraph bodyParagraph = document.createParagraph();
        bodyParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun paragraphConfig = bodyParagraph.createRun();
        paragraphConfig.setItalic(true);
        paragraphConfig.setFontSize(25);
        paragraphConfig.setColor("06357a");
        paragraphConfig.setText( "Отчет");

        XWPFTable table = document.createTable();





        XWPFTableRow tableRowOne = table.getRow(0);
        tableRowOne.getCell(0).setText("№");
        tableRowOne.addNewTableCell().setText("Фамилия");
        tableRowOne.addNewTableCell().setText("Имя");
        tableRowOne.addNewTableCell().setText("Отчество");
        tableRowOne.addNewTableCell().setText("Дата рождения");
        tableRowOne.addNewTableCell().setText("Статья КоАП");
        tableRowOne.addNewTableCell().setText("Кол-во АП");





        for (int i = 0; i < apOVDs.size(); i++) {
            XWPFTableRow tableRow = table.createRow();
            tableRow.getCell(0).setText(String.valueOf(i));
            tableRow.getCell(1).setText(apOVDs.get(i).getLastName());
            tableRow.getCell(2).setText(String.valueOf(apOVDs.get(i).getFirstName()));
            tableRow.getCell(3).setText(apOVDs.get(i).getMiddleName());
            tableRow.getCell(4).setText(String.valueOf(apOVDs.get(i).getBirthDay()));
            tableRow.getCell(5).setText(apOVDs.get(i).getArticle());
            tableRow.getCell(6).setText(String.valueOf(apOVDs.get(i).getKol()));
        }
        try {
            Service service =new Service();
            Settings sett=new Settings();
            sett= service.getSetting(2);
            Calendar cal=Calendar.getInstance();
            long d =cal.getTimeInMillis();




            FileOutputStream outputStream = new FileOutputStream(sett.getSettings()+d+".docx");
            document.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
