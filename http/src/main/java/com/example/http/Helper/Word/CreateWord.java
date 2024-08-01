package com.example.http.Helper.Word;

import com.example.http.Model.Book;
import com.example.http.Repository.BookRepository;
import com.itextpdf.text.Element;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CreateWord {
    @Autowired
    private BookRepository bookRepository;

    public static String convertTextFileToString(String fileName) {
        try (Stream<String> stream
                     = Files.lines(Paths.get(ClassLoader.getSystemResource(fileName).toURI()))) {

            return stream.collect(Collectors.joining(" "));
        } catch (IOException | URISyntaxException e) {
            return null;
        }
    }

    public String createWordBySource(Book book) throws Exception {
        if (book == null) {
            return "Book not found";
        }
        String title = book.getTitle();
        String author = book.getAuthor();

        XWPFDocument document = new XWPFDocument();
        XWPFTable table = document.createTable(3,1);
        table.removeBorders();
        table.setWidth("100%");

        //cell1
        XWPFTableCell cell1 = table.getRow(0).getCell(0);
        cell1.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);

        XWPFParagraph paragraph1 = cell1.addParagraph();
        paragraph1.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run1 = paragraph1.createRun();
        run1.setText(author);
        run1.setFontFamily("Times New Roman");
        run1.setFontSize(14);
        run1.setItalic(true);

        XWPFTableCell cell2 = table.getRow(1).getCell(0);
        cell1.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.TOP);

        XWPFParagraph paragraph2 = cell2.addParagraph();
        paragraph2.setVerticalAlignment(TextAlignment.CENTER);
        paragraph2.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run2 = paragraph2.createRun();
        run2.addBreak();
        run2.addBreak();
        run2.addBreak();
        run2.setText(title);
        run2.setFontSize(40);
        run2.setBold(true);


        XWPFTableCell cell3 = table.getRow(2).getCell(0);
        cell3.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.BOTTOM);

        XWPFParagraph paragraph3 = cell3.addParagraph();
        paragraph3.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run3 = paragraph3.createRun();
        run3.addBreak();
        run3.addBreak();
        run3.addBreak();
        run3.addBreak();
        run3.addBreak();
        run3.addBreak();
        run3.addBreak();
        run3.addBreak();
        run3.addBreak();
        run3.addBreak();
        run3.addBreak();



        run3.setText("\uD835\uDC01\uD835\uDC04\uD835\uDC13\uD835\uDC13\uD835\uDC04\uD835\uDC11 \uD835\uDC15\uD835\uDC04\uD835\uDC11\uD835\uDC12\uD835\uDC08\uD835\uDC0E\uD835\uDC0D – Cùng bạn trở thành phiên bản tốt hơn của bạn mỗi ngày.");
        run3.addBreak();
        run3.setText("Theo dõi kênh tại: https://beacons.ai/betterversion.vn");
        run3.addBreak();
        run3.setText("➤ Podcasts: https://podcasts.apple.com/us/podcast/better-version/id1588566083");
        run3.addBreak();
        run3.setText("➤ Spotify: https://open.spotify.com/show/63dtoBJnfoF9qHMRYyIqhU");
        run3.addBreak();
        run3.setText("➤ Instagram: https://www.instagram.com/better.version__/");


        // output a string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.write(baos);
        document.close();

        return Base64.getEncoder().encodeToString(baos.toByteArray());

    }

    public void convert(String base64WordString) {
        byte[] excelBytes = Base64.getDecoder().decode(base64WordString);

        // Đường dẫn đến nơi bạn muốn lưu tệp Excel trên máy tính
        String filePath = "src/main/java/com/example/http/Helper/Word/output.docx";

        // Ghi các byte vào một tệp trên máy tính
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(excelBytes);
            System.out.println("File saved successfully at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createRawWordByFile() throws IOException {
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun titleRun = title.createRun();
        titleRun.setText("Love yourself is the beginning of a romantic relationship");
        titleRun.setBold(true);
        titleRun.setFontSize(20);
        titleRun.setFontFamily("Times New Roman");

        XWPFParagraph subTitle = document.createParagraph();
        subTitle.setAlignment(ParagraphAlignment.CENTER);

        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(1000);
        XWPFRun run = paragraph.createRun();
        run.setText("Love is a beautiful thing");


        FileOutputStream out = new FileOutputStream("src/main/java/com/example/http/Helper/Word/haha.docx");
        document.write(out);
        out.close();
        document.close();
        return "src/main/java/com/example/http/Helper/Word/haha.docx";
    }

    public String createRawWord() throws IOException {
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun titleRun = title.createRun();
        titleRun.setText("Love yourself is the beginning of a romantic relationship");
        titleRun.setBold(true);
        titleRun.setFontSize(20);
        titleRun.setFontFamily("Times New Roman");

        XWPFParagraph subTitle = document.createParagraph();
        subTitle.setAlignment(ParagraphAlignment.CENTER);

        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(1000);
        XWPFRun run = paragraph.createRun();
        run.setText("Love is a beautiful thing");


        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.write(out);
        document.close();
        return Base64.getEncoder().encodeToString(out.toByteArray());
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

        XWPFTable table = document.createTable(1, 3);
        table.removeBorders();
//        table.getRow(0).getCell(0).setText("ID");
//        table.getRow(0).getCell(1).setText("Name");
//        table.getRow(0).getCell(2).setText("Description");
        XWPFTableCell cell1 = table.getRow(0).getCell(0);
        cell1.setWidth("400");
        XWPFParagraph paragraph1 = cell1.addParagraph();
        XWPFRun run1 = paragraph1.createRun();
        run1.setText("ID");

        XWPFTableCell cell2 = table.getRow(0).getCell(1);
        cell1.setWidth("400");
        XWPFParagraph paragraph2 = cell2.addParagraph();
        XWPFRun run2 = paragraph2.createRun();
        run2.setText("Name");

        XWPFTableCell cell3 = table.getRow(0).getCell(2);
        cell1.setWidth("400");
        XWPFParagraph paragraph3 = cell3.addParagraph();
        XWPFRun run3 = paragraph3.createRun();
        run3.setText("Description");

        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(1000);
        XWPFRun run = paragraph.createRun();
        run.setText("Love is a beautiful thing");

        String output = "src/main/java/com/example/http/Helper/Word/hihi.docx";
        FileOutputStream out = new FileOutputStream(output);
        document.write(out);
        out.close();
        document.close();

    }
}
