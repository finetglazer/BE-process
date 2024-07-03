package com.example.http.helper;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class createWord {
    public static String convertTextFileToString(String fileName) {
        try (Stream<String> stream
                     = Files.lines(Paths.get(ClassLoader.getSystemResource(fileName).toURI()))) {

            return stream.collect(Collectors.joining(" "));
        } catch (IOException | URISyntaxException e) {
            return null;
        }
    }

    public static void main(String[] args) throws Exception{
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun titleRun = title.createRun();
        titleRun.setText("Build Your Rest API with Spring");
        titleRun.setColor("009933");
        titleRun.setBold(true);
        titleRun.setFontSize(20);
        titleRun.setFontFamily("Courier");

        XWPFParagraph subTitle = document.createParagraph();
        subTitle.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun subTitleRun = subTitle.createRun();
        subTitleRun.setText("Create a new REST API using Spring Boot");
        subTitleRun.setColor("00CC44");
        subTitleRun.setFontSize(16);

        XWPFParagraph image = document.createParagraph();
        image.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun imageRun = image.createRun();
        imageRun.setTextPosition(20);

        String imagePath = "src/main/java/com/example/http/image/Frame 1000003688.png";

        FileInputStream is = new FileInputStream(imagePath);
        imageRun.addBreak();
        imageRun.addPicture(is, XWPFDocument.PICTURE_TYPE_PNG, imagePath, Units.toEMU(200), Units.toEMU(200));
        is.close();


        String output = "src/main/java/com/example/http/repository/hihi.docx";
        FileOutputStream out = new FileOutputStream(output);
        document.write(out);
        out.close();
        document.close();

    }
}
