package com.d.clinic.excel;

import com.d.clinic.entity.Patient;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class KunlikExcel {


    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Patient> listMalumot;

    public KunlikExcel(List<Patient> listMalumot) {
        super();
        this.listMalumot = listMalumot;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Ism", style);
        createCell(row, 1, "Familiya", style);
        createCell(row, 2, "Manzil", style);
        createCell(row, 3, "Tugulgan kun", style);
        createCell(row, 4, "To'lov", style);
        createCell(row, 5, "Qabul Soni", style);
        createCell(row, 6, "Telefon", style);


    }
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Float) {
            cell.setCellValue((Float) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        }
        else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Patient m : listMalumot) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, m.getName(), style);
            createCell(row, columnCount++, m.getSurname(), style);
            createCell(row, columnCount++, m.getAddress(), style);
            createCell(row, columnCount++, m.getDate(), style);
            createCell(row, columnCount++, m.getPayment(), style);
            createCell(row, columnCount++, m.getCameNumber(), style);
            createCell(row, columnCount++, m.getPhone(), style);


        }
    }
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }


}
