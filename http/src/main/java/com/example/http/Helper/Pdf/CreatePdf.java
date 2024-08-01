package com.example.http.Helper.Pdf;


import com.example.http.Model.Book;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Base64;


@Component
public class CreatePdf {
    private static byte[] readAllBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos.toByteArray();
    }
    public void convert(String base64WordString) {
        byte[] excelBytes = Base64.getDecoder().decode(base64WordString);

        // Đường dẫn đến nơi bạn muốn lưu tệp Excel trên máy tính
        String filePath = "src/main/java/com/example/http/Helper/Pdf/output.pdf";

        // Ghi các byte vào một tệp trên máy tính
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(excelBytes);
            System.out.println("File saved successfully at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createPdfString() throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();
        Paragraph paragraph = new Paragraph();
        paragraph.add("Hello, World!");
        paragraph.setAlignment(Element.ALIGN_LEFT);
        document.add(paragraph);


        PdfPTable table = new PdfPTable(3);
        PdfPCell cell0 = new PdfPCell(new Paragraph("I am here"));
        PdfPCell cell1 = new PdfPCell(new Paragraph("you are there"));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Can I come close to you?"));

        table.addCell(cell0);
        table.addCell(cell1);
        table.addCell(cell2);
        document.add(table);
        //close the document
        document.close();

        return Base64.getEncoder().encodeToString(baos.toByteArray());

    }

    public String createPdfBySource(Book book) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        // create a new font
        InputStream fontStreamBold = CreatePdf.class.getClassLoader().getResourceAsStream("font/timesbd.ttf");
        if (fontStreamBold == null) {
            throw new IOException("Font file not found in resources");
        }
        InputStream fontStream = CreatePdf.class.getClassLoader().getResourceAsStream("font/times.ttf");
        if (fontStream == null) {
            throw new IOException("Font file not found in resources");
        }
        byte[] fontBytes = readAllBytes(fontStream);
        byte[] fontBytesBold = readAllBytes(fontStreamBold);
        // Create font
        BaseFont baseFont = BaseFont.createFont("times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, fontBytes, null);
        Font vietNaneseFont = new Font(baseFont,12);
        BaseFont baseFontBold = BaseFont.createFont("timesbd.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, fontBytesBold, null);

        //para 1
        Paragraph paragraph1 = new Paragraph();
        paragraph1.setAlignment(Element.ALIGN_CENTER);
        paragraph1.setFont(new Font(Font.FontFamily.HELVETICA, 16, Font.ITALIC));
        paragraph1.add(book.getAuthor());
        paragraph1.add("\n");
        paragraph1.add("\n");
        paragraph1.add("\n");
        paragraph1.add("\n");
        paragraph1.add("\n");
        paragraph1.add("\n");
        paragraph1.add("\n");
        paragraph1.add("\n");
        paragraph1.add("\n");
        paragraph1.add("\n");
        paragraph1.add("\n");
        paragraph1.add("\n");
        paragraph1.add("\n");
        paragraph1.add("\n");
        paragraph1.add("\n");
        paragraph1.add("\n");
        paragraph1.add("\n");
        paragraph1.add("\n");
        paragraph1.add("\n");
        paragraph1.add("\n");


        document.add(paragraph1);
        //para 2
        Paragraph paragraph2 = new Paragraph();
        paragraph2.setAlignment(Element.ALIGN_CENTER);
        paragraph2.setFont(new Font(Font.FontFamily.HELVETICA, 40, Font.BOLD));
        paragraph2.add(book.getTitle());
        paragraph2.add("\n");
        paragraph2.add("\n");
        paragraph2.add("\n");
        paragraph2.add("\n");
        paragraph2.add("\n");
        paragraph2.add("\n");
        paragraph2.add("\n");
        paragraph2.add("\n");
        paragraph2.add("\n");
        paragraph2.add("\n");
        paragraph2.add("\n");
        paragraph2.add("\n");
        paragraph2.add("\n");
        paragraph2.add("\n");
        paragraph2.add("\n");
        paragraph2.add("\n");
        paragraph2.add("\n");
        paragraph2.add("\n");
        paragraph2.add("\n");
        paragraph2.add("\n");
        paragraph2.add("\n");
        paragraph2.add("\n");

        document.add(paragraph2);
        //para 3
        Paragraph paragraph3 = new Paragraph();
        paragraph3.setAlignment(Element.ALIGN_CENTER);
        Font blackFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
        Font blueFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLUE);

        paragraph3.setFont(vietNaneseFont);
        paragraph3.add("– Cùng bạn trở thành phiên bản tốt hơn của bạn mỗi ngày.\n");
        paragraph3.add("Theo dõi kênh tại:\n ");
        paragraph3.setFont(blackFont);
        paragraph3.add("➤ Youtube: ");
        paragraph3.setFont(blueFont);
        paragraph3.add("https://www.youtube.com/channel/UC9Z0q1J0f8Z1Z8J5e9QbJ9A\n");
        paragraph3.setFont(blackFont);
        paragraph3.add("➤ Podcasts: ");
        paragraph3.setFont(blueFont);
        paragraph3.add("https://podcasts.apple.com/us/podcast/better-version/id1588566083\n");
        paragraph3.setFont(blackFont);
        paragraph3.add("➤ Spotify: ");
        paragraph3.setFont(blueFont);
        paragraph3.add("https://open.spotify.com/show/63dtoBJnfoF9qHMRYyIqhU\n");
        paragraph3.setFont(blackFont);
        paragraph3.add("➤ Instagram: ");
        paragraph3.setFont(blueFont);
        paragraph3.add("https://www.instagram.com/better.version__/");

        document.add(paragraph3);
        //close
        document.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

//    public static void main(String[] args) throws Exception {
//        Document document = new Document();
//        PdfWriter.getInstance(document, new FileOutputStream("src/main/java/com/example/http/Helper/Pdf/keke.pdf"));
//        document.open();
//        Paragraph paragraph = new Paragraph();
//        paragraph.add("Hello, World!");
//        paragraph.setAlignment(Element.ALIGN_LEFT);
//        document.add(paragraph);
//
//
//        PdfPTable table = new PdfPTable(3);
//        PdfPCell cell0 = new PdfPCell(new Paragraph("I am here"));
//        PdfPCell cell1 = new PdfPCell(new Paragraph("you are there"));
//        PdfPCell cell2 = new PdfPCell(new Paragraph("Can I come close to you?"));
//
//        table.addCell(cell0);
//        table.addCell(cell1);
//        table.addCell(cell2);
//        document.add(table);
//        document.close();
//
//
//
//    }
}
