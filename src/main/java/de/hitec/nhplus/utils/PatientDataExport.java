package de.hitec.nhplus.utils;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class PatientDataExport {
    int i = 0;
    Connection connection;

    public PatientDataExport(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<String[]> getTreatmentDataById(long pID) {
        ArrayList<String[]> treatmentDataList = new ArrayList<>();
        final String SQL = "SELECT treatment_date, begin, end, description, remark FROM treatment WHERE pid = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(SQL);
            statement.setLong(1, pID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String[] rowData = new String[5];
                rowData[0] = resultSet.getString("treatment_date");
                rowData[1] = resultSet.getString("begin");
                rowData[2] = resultSet.getString("end");
                rowData[3] = resultSet.getString("description");
                rowData[4] = resultSet.getString("remark");
                treatmentDataList.add(rowData);
            }
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return treatmentDataList;
    }

    public ArrayList<String[]> getPatientDataById(long pID) {
        ArrayList<String[]> patientDataList = new ArrayList<>();
        final String SQL = "SELECT firstname, surname, dateOfBirth, carelevel, roomnumber FROM patient WHERE pid = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(SQL);
            statement.setLong(1, pID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String[] rowData = new String[5];
                rowData[0] = resultSet.getString("firstname");
                rowData[1] = resultSet.getString("surname");
                rowData[2] = resultSet.getString("dateOfBirth");
                rowData[3] = resultSet.getString("carelevel");
                rowData[4] = resultSet.getString("roomnumber");
                patientDataList.add(rowData);
            }
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patientDataList;
    }

    public void pdfExporter(ArrayList<String[]> patientData, ArrayList<String[]> treatmentData, String filePath) throws IOException {
        PdfWriter writer = new PdfWriter(filePath);

        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.addNewPage();

        Document document = new Document(pdfDoc);

        // Patient Data Table
        if (!patientData.isEmpty()) {
            Table patientTable = new Table(UnitValue.createPercentArray(5)).useAllAvailableWidth();
            String[] patientHeaders = {"Vorname", "Nachname", "Geburtsdatum", "Pflegestufe", "Zimmernummer"};
            for (String header : patientHeaders) {
                Cell headerCell = new Cell().add(new Paragraph(header)
                        .setBold()
                        .setFontSize(12)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .setBackgroundColor(ColorConstants.LIGHT_GRAY));
                patientTable.addHeaderCell(headerCell);
            }

            for (String[] row : patientData) {
                for (String cellData : row) {
                    Cell cell = new Cell().add(new Paragraph(cellData)
                            .setFontSize(10)
                            .setTextAlignment(TextAlignment.LEFT)
                            .setPadding(5));
                    patientTable.addCell(cell);
                }
            }

            document.add(patientTable);
        }

        document.add(new Paragraph("\n"));

        // Treatment Data Table
        Table treatmentTable = new Table(UnitValue.createPercentArray(5)).useAllAvailableWidth();
        String[] treatmentHeaders = {"Datum", "Beginn", "Ende", "Beschreibung", "Bemerkung"};
        for (String header : treatmentHeaders) {
            Cell headerCell = new Cell().add(new Paragraph(header)
                    .setBold()
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY));
            treatmentTable.addHeaderCell(headerCell);
        }

        for (String[] row : treatmentData) {
            for (String cellData : row) {
                Cell cell = new Cell().add(new Paragraph(cellData)
                        .setFontSize(10)
                        .setTextAlignment(TextAlignment.LEFT)
                        .setPadding(5));
                treatmentTable.addCell(cell);
            }
        }

        document.add(treatmentTable);
        document.close();
    }
}
