package com.ivo.utils;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import com.ivo.beans.ResultBean;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 *
 * @author IOAdmin
 */
public class ExportToWord {

    private List<ResultBean> results;

    public ExportToWord(List<ResultBean> resultBean) {
        if (resultBean == null) {
            throw new NullPointerException("resultBean not be null");
        }
        this.results = resultBean;
    }
    
    
    public void out() throws RuntimeException {
        try {
            // создаем модель docx документа, 
            // к которой будем прикручивать наполнение (колонтитулы, текст)
            XWPFDocument docxModel = new XWPFDocument();
            
            setHeaderAboutMethod(docxModel);
            setDataSimplex(docxModel);
            
            saveFile(docxModel); 
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void setHeaderAboutMethod(XWPFDocument docxModel) {
        // создаем обычный параграф, который будет расположен слева,
        // будет синим курсивом со шрифтом 20 размера
        XWPFParagraph bodyParagraph = docxModel.createParagraph();
        bodyParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun paragraphConfig = bodyParagraph.createRun();
        paragraphConfig.setFontSize(20);
        // HEX цвет без решетки #
        paragraphConfig.setColor("06357a");
        paragraphConfig.setText("Подробное решение симплекс-метода");
        // отступ
        addBreak(docxModel);
    }
    
    private void setDataSimplex(XWPFDocument docxModel) {
        for (ResultBean bean : results) {
            createDescription(bean.getDescription(), docxModel);
            createTable(bean.getIterResults(), docxModel);
        }
    }
    
    private void createTable(String[][] iterResults, XWPFDocument docxModel) {
        XWPFTable table = docxModel.createTable();
        setAutoColumnWidth(table);
        
        XWPFParagraph paragraph;
        
        XWPFTableRow row = table.getRow(0);
        paragraph = row.getCell(0).addParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        setRun(paragraph.createRun(), "000000", "Базис", true, false);
        
        // создаем по вертикали названия переменных
        for (int i = 1; i < iterResults.length; i++) {
            row = table.createRow();
            paragraph = row.getCell(0).addParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            setRun(paragraph.createRun(), "000000", iterResults[i][0], true, false);
        }
        
        // создаем по горизонтали названия переменных
        row = table.getRow(0);
        // создаем ячейку для названия B
        // костыль потому что подругому никак
        row.addNewTableCell();
        for (int i = 2; i < iterResults[0].length; i++) {
            paragraph = row.addNewTableCell().addParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            setRun(paragraph.createRun(), "000000", iterResults[0][i], true, false);
        }
        // устанавливаем в эту ячейку значение
        paragraph = row.getCell(1).addParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        setRun(paragraph.createRun(), "000000", "B", true, false);

        for (int i = 1; i < iterResults.length; i++) {
            row = table.getRow(i);
            for (int j = 1; j < iterResults[0].length; j++) {
                paragraph = row.addNewTableCell().addParagraph();
                paragraph.setAlignment(ParagraphAlignment.CENTER);
                setRun(paragraph.createRun(), "000000", iterResults[i][j], false, false);
            }
        }

        addBreak(docxModel);
    }

    private void setAutoColumnWidth(XWPFTable table) {
        table.getCTTbl().addNewTblPr().addNewTblW().setW(BigInteger.valueOf(9000));
    }
    
    private void createDescription(String description, XWPFDocument docxModel) {
        XWPFParagraph paragraph = docxModel.createParagraph();
        setRun(paragraph.createRun(), "111111", "=================", true, true);
        paragraph = docxModel.createParagraph();
        setRun(paragraph.createRun(), "111111", description, false, true);
        paragraph = docxModel.createParagraph();
        setRun(paragraph.createRun(), "111111", "=================", true, true);
    }

    private void addBreak(XWPFDocument docxModel) {
        // отступ
        XWPFParagraph paragraph = docxModel.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        setRun(paragraph.createRun(), "000000", "", false, false);
    }
    
    private void setRun(XWPFRun run, String colorRGB, String text, boolean bold, boolean addBreak) {
        run.setColor(colorRGB);
        run.setText(text);
        run.setBold(bold);
        if (addBreak) run.addBreak();
    }


    private void saveFile(XWPFDocument docxModel) throws RuntimeException {
        String getPath = "ПУТЬ ПЕРЕДАВАТЬ В ПАРАМЕТР В КОНСТРУКТОР"
                + "ОН БУДЕТ БРАТЬСЯ ИЗ СЕРВЛЕТА CONTROLLERA";
        try (FileOutputStream outputStream = new FileOutputStream("D:/Simplex.docx");) {
            docxModel.write(outputStream);
        } catch (IOException ex) {
            throw new RuntimeException("Error save docx");
        }
    } 
}
