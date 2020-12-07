package uz.azon.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ExcelService {

    public File generateExel(List<Payload> userList, boolean read) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm");
        String format = simpleDateFormat.format(new Date());

        XSSFSheet sheet = workbook.createSheet(String.valueOf(format));

        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 6000);
        sheet.setColumnWidth(5, 2000);
        sheet.setColumnWidth(6, 4000);
        sheet.setColumnWidth(7, 7000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.JUSTIFY);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Т/р");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Исм");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Фамилия");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Шаҳар/Туман");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Телефон рақам");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Ёш");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(6);
        headerCell.setCellValue("Сана");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(7);
        headerCell.setCellValue("Ўқув даргоҳи:");
        headerCell.setCellStyle(headerStyle);

        int rowNumber = 1;

        for (Payload field : userList) {
            Row row = sheet.createRow(rowNumber);
            rowNumber++;

            Cell callText = row.createCell(0);
            callText.setCellValue(field.getTr());

            callText = row.createCell(1);
            callText.setCellValue(field.getFirstName());

            callText = row.createCell(2);
            callText.setCellValue(field.getLastName());

            callText = row.createCell(3);
            callText.setCellValue(field.getCity());

            callText = row.createCell(4);
            callText.setCellValue(field.getPhoneNumber());

            callText = row.createCell(5);
            callText.setCellValue(field.getAge());

            callText = row.createCell(6);
            callText.setCellValue(
                    simpleDateFormat.format(new Date(field.getDate().getTime()))
            );
            callText = row.createCell(7);
            callText.setCellValue(field.getEdu() != null && !field.getEdu().isEmpty() && field.getEdu().length() > 0 ? field.getEdu() : "йўқ");

        }
        File file = new File(read ? "Тиловат рўйҳат.xlsx" : "Ҳифз рўйҳат.xlsx");
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            workbook.write(outputStream);
        }
        return file;
    }
}
