package com.example.rahi.schedulemanagement.utility;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.rahi.schedulemanagement.ViewPDFActivity;
import com.example.rahi.schedulemanagement.database.DatabaseHelper;
import com.example.rahi.schedulemanagement.database.EmployeeInfoDataManager;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PDFTemplate {
    Activity mContext;
    EmployeeInfoDataManager employeeInfoDataManager;
    Cursor cursor;

    public PDFTemplate(Activity context) {
        this.mContext = context;
        employeeInfoDataManager = new EmployeeInfoDataManager(context);
    }

    public void createPdf(int vEmpId) throws FileNotFoundException, DocumentException {
        String dir = Environment.getExternalStorageDirectory() + File.separator + "SMReport";
        File folder = new File(dir);
        folder.mkdirs();

        File file = new File(dir, "EmpInfo.pdf");

        if (vEmpId == 0) {
            cursor = employeeInfoDataManager.getEmployeeData("SELECT * FROM employeeinfo WHERE id <> '0' ORDER BY id");
        } else {
            cursor = employeeInfoDataManager.getEmployeeData("SELECT * FROM employeeinfo WHERE id = '" + vEmpId + "' ORDER BY id");
        }

        Document document = new Document();  // create the document
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        PdfPTable pdfPTable = new PdfPTable(4);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setSpacingBefore(20);
        pdfPTable.setHorizontalAlignment(Element.ALIGN_CENTER);

        pdfPTable.addCell("ID");
        pdfPTable.addCell("Name");
        pdfPTable.addCell("Email");
        pdfPTable.addCell("User Name");

        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String email = cursor.getString(2);
            String userNmae = cursor.getString(3);

            pdfPTable.addCell(id);
            pdfPTable.addCell(name);
            pdfPTable.addCell(email);
            pdfPTable.addCell(userNmae);
        }

        Paragraph paragraph = new Paragraph();
        paragraph.add("Employee Information \n\n");
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        document.add(pdfPTable);
        document.addCreationDate();
        document.close();
    }

    public void createAssignDataToPdf(int vEmpId, String fromDate, String toDate) throws FileNotFoundException, DocumentException {
        String dir = Environment.getExternalStorageDirectory() + File.separator + "SMReport";
        File folder = new File(dir);
        folder.mkdirs();

        File file = new File(dir, "AssignedSchedule.pdf");
        Document document = new Document();  // create the document
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        PdfPTable pdfPTable = new PdfPTable(5);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setSpacingBefore(20);
        pdfPTable.setHorizontalAlignment(Element.ALIGN_CENTER);

        // pdfPTable.addCell("ID");
        // pdfPTable.addCell("Emp ID");
        pdfPTable.addCell("Shifting Date");
        pdfPTable.addCell("Shift 1");
        pdfPTable.addCell("Shift 2");
        pdfPTable.addCell("Shift 3");
        pdfPTable.addCell("Off Day");

        // For Assigned Schedule Info
        String vEmp_Id = "", vEmpName = "", vShiftDate = "", vShift1 = "", vShift2 = "", vShift3 = "", vOffDay = "";

        //        if (vEmpId == 0) {
        //            cursor = employeeInfoDataManager.getEmployeeData("SELECT a.EmpId,b.name,a.ShiftDate,a.Shift1,a.Shift2,a.Shift3,a.OffDay \n" +
        //                    "FROM scheduleassign as a LEFT OUTER JOIN\n" +
        //                    "employeeinfo as b on a.EmpId = b.id \n" +
        //                    "WHERE ShiftDate BETWEEN '" + fromDate + "' and '" + toDate + "'\n" +
        //                    "ORDER BY a.EmpId,a.ShiftDate");
        //        } else {
        cursor = employeeInfoDataManager.getEmployeeData("SELECT a.EmpId,b.name,a.ShiftDate,a.Shift1,a.Shift2,a.Shift3,a.OffDay \n" +
                "FROM scheduleassign as a LEFT OUTER JOIN\n" +
                "employeeinfo as b on a.EmpId = b.id \n" +
                "WHERE EmpId = '" + vEmpId + "' and ShiftDate BETWEEN '" + fromDate + "' and '" + toDate + "'\n" +
                "ORDER BY a.EmpId,a.ShiftDate");
        //        }

        while (cursor.moveToNext()) {
            vEmp_Id = cursor.getString(0);
            vEmpName = cursor.getString(1);
            vShiftDate = cursor.getString(2);
            vShift1 = cursor.getString(3);
            vShift2 = cursor.getString(4);
            vShift3 = cursor.getString(5);
            vOffDay = cursor.getString(6);

            // pdfPTable.addCell(id);
            // pdfPTable.addCell(empid);
            pdfPTable.addCell(vShiftDate);
            pdfPTable.addCell(vShift1);
            pdfPTable.addCell(vShift2);
            pdfPTable.addCell(vShift3);
            pdfPTable.addCell(vOffDay);
        }

        Paragraph paragraph = new Paragraph();
        paragraph.add("Assigned Schedule Information for - " + vEmp_Id + " : " + vEmpName + " \n");
        paragraph.setAlignment(Element.ALIGN_CENTER);

        // For Shifting Info
        String vSFD = "", vSTD = "", vSFT1 = "", vSTT1 = "", vSFT2 = "", vSTT2 = "", vSFT3 = "", vSTT3 = "";

        cursor = employeeInfoDataManager.getEmployeeData("SELECT FromDate,ToDate,ShiftFrom1,ShiftTo1,ShiftFrom2,ShiftTo2,ShiftFrom3,ShiftTo3 FROM scheduleinfo WHERE ('" + fromDate + "' BETWEEN FromDate and ToDate) and ('" + toDate + "' BETWEEN FromDate and ToDate)");

        while (cursor.moveToNext()) {
            vSFD = cursor.getString(0);
            vSTD = cursor.getString(1);
            vSFT1 = cursor.getString(2);
            vSTT1 = cursor.getString(3);
            vSFT2 = cursor.getString(4);
            vSTT2 = cursor.getString(5);
            vSFT3 = cursor.getString(6);
            vSTT3 = cursor.getString(7);
        }

        Paragraph paragraph2 = new Paragraph();
        paragraph2.add("Shifting Information for (" + vSFD + "-" + vSTD + ") - Shift 1 - " + vSFT1 + "-" + vSTT1 + ", Shift 2 - " + vSFT2 + "-" + vSTT2 + ", Shift 3 - " + vSFT3 + "-" + vSTT3 + " \n");
        paragraph2.setAlignment(Element.ALIGN_CENTER);

        document.add(paragraph);
        document.add(paragraph2);
        document.add(pdfPTable);
        document.addCreationDate();
        document.close();
    }

    public void viewPDF() {
        String dir = Environment.getExternalStorageDirectory() + File.separator + "SMReport" + File.separator + "AssignedSchedule.pdf";
        //File file = new File(dir, "AssignDataHistory.pdf");
        Intent intent = new Intent(mContext, ViewPDFActivity.class);
        intent.putExtra("path", dir);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    public void viewEmpPDF() {
        String dir = Environment.getExternalStorageDirectory() + File.separator + "SMReport" + File.separator + "EmpInfo.pdf";
        //File file = new File(dir, "AssignDataHistory.pdf");
        Intent intent = new Intent(mContext, ViewPDFActivity.class);
        intent.putExtra("path", dir);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
