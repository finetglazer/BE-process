package com.example.http.helper;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class CreateBuiltInForm {
    public static void main(String[] args) {
        // Create a new document
        XWPFDocument document = new XWPFDocument();

        // Add a title paragraph
        XWPFParagraph titleParagraph = document.createParagraph();
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = titleParagraph.createRun();
        titleRun.setText("BIỂU MẪU THỐNG KÊ BÁO CÁO");
        titleRun.setBold(true);
        titleRun.setFontSize(16);

        // Add a subtitle paragraph
        XWPFParagraph subtitleParagraph = document.createParagraph();
        subtitleParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun subtitleRun = subtitleParagraph.createRun();
        subtitleRun.setText("(Form Title)");
        subtitleRun.setFontSize(14);
        subtitleRun.setItalic(true);

        // Add an empty paragraph for spacing
        document.createParagraph();

        // Add a table
        XWPFTable table = document.createTable();

        // Create the first row
        XWPFTableRow row1 = table.getRow(0);
        row1.getCell(0).setText("STT");
        row1.addNewTableCell().setText("Nội dung");
        row1.addNewTableCell().setText("Số liệu");

        // Create the second row
        XWPFTableRow row2 = table.createRow();
        row2.getCell(0).setText("1");
        row2.getCell(1).setText("Nội dung mẫu");
        row2.getCell(2).setText("Số liệu mẫu");

        // More rows can be added as needed
        for (int i = 2; i <= 10; i++) {
            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(String.valueOf(i));
            row.getCell(1).setText("Nội dung " + i);
            row.getCell(2).setText("Số liệu " + i);
        }

        // Add a paragraph with the footer text
        XWPFParagraph footerParagraph = document.createParagraph();
        footerParagraph.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun footerRun = footerParagraph.createRun();
        footerRun.setText("Footer Text Example");
        footerRun.setFontSize(12);
        footerRun.setItalic(true);

        // Save the document
        try (FileOutputStream out = new FileOutputStream("src/main/java/com/example/http/repository/hihi.docx")) {
            document.write(out);
            System.out.println("Document created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
