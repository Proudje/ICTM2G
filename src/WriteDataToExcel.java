// Java program to write data in excel sheet using java code

import java.io.File;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class WriteDataToExcel {

    // any exceptions need to be caught
    public WriteDataToExcel(ArrayList<Location> locations) throws IOException {

        // workbook object
        XSSFWorkbook workbook = new XSSFWorkbook();

        // spreadsheet object
        XSSFSheet spreadsheet
                = workbook.createSheet("Pakketen");

        // creating a r ow object
        XSSFRow row;

        // This data needs to be written (Object[])
        Map<String, Object[]> ordersData
                = new TreeMap<String, Object[]>();

        ordersData.put(
                "1",
                new Object[]{"Tussenstop", "Adres", "Naam", "Telefoonnummer"});
        int i = 2;
        int tussenstop = 1;
        for (Location location : locations) {
            if (i == 2) {
            } else {
                ordersData.put(String.valueOf(i), new Object[]{String.valueOf(tussenstop), location.getCustomer().getAddress(), location.getCustomer().getName(), location.getCustomer().getPhonenumber()});
                tussenstop++;
            }
            i++;
        }

        Set<String> keyid = ordersData.keySet();

        int rowid = 0;

        // writing the data into the sheets...

        for (String key : keyid) {

            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = ordersData.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String) obj);
            }
        }

        // .xlsx is the format for Excel Sheets...
        // writing the workbook into the file...
        FileOutputStream out = new FileOutputStream(
                //new File("/Users/boverzaal/GFGsheet.xlsx"));
                // Edit to your own pathname
                new File("C:/savedexcel/Route1.xlsx"));

        workbook.write(out);
        out.close();
    }
}

