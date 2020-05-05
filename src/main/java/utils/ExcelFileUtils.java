package utils;

import com.relevantcodes.extentreports.ExtentTest;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ExcelFileUtils {

    static String projectDir = System.getProperty("user.dir");

    /**
     * This method is used to get the value of a column from excel sheet
     *
     * @param fileName
     * @param sheetName
     * @param rowNum
     * @param columnName
     * @return
     */
    public String getValueForColumn(String fileName, String sheetName, int rowNum, String columnName) {
        String userDirector = System.getProperty("user.dir");
        try {

            File src = new File(userDirector + "/Resources/" + fileName);
            FileInputStream fis = new FileInputStream(src);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sh1 = wb.getSheet(sheetName);
            int i = 0;
            for (i = 0; i < sh1.getRow(0).getLastCellNum(); i++) {
                if (sh1.getRow(0).getCell(i).getStringCellValue().contentEquals(columnName)) {
                    break;
                }
            }

            return sh1.getRow(rowNum).getCell(i).getStringCellValue();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * This method is used to read the data from excel sheet
     *
     * @param fileName-Name  of the file including its adddress
     * @param sheetName-Name of the sheet
     * @return-two dimensional array containing row and column data
     */
    public String[][] getExcelData(String fileName, String sheetName) {
        String[][] arrayExcelData = null;
        org.apache.poi.ss.usermodel.Workbook tempWB;

        try {

            if (fileName.contains(".xlsx")) {
                tempWB = new XSSFWorkbook(fileName);
            } else {
                InputStream inp = new FileInputStream(fileName);
                tempWB = new HSSFWorkbook(new POIFSFileSystem(inp));
            }

            org.apache.poi.ss.usermodel.Sheet sheet = tempWB.getSheet(sheetName);

            // Total rows counts the top heading row
            int totalNoOfRows = sheet.getLastRowNum();
            //System.out.println("The total rows are : " + totalNoOfRows);
            Row row = sheet.getRow(0);
            int totalNoOfCols = row.getLastCellNum();
            //System.out.println("The total colums are : " + totalNoOfCols);

            arrayExcelData = new String[totalNoOfRows][totalNoOfCols];

            try {
                for (int i = 1; i < totalNoOfRows + 1; i++) {

                    for (int j = 0; j < totalNoOfCols; j++) {
                        try {
                            arrayExcelData[i - 1][j] = sheet.getRow(i).getCell(j).toString().trim();
                        } catch (Exception e) {
                            arrayExcelData[i - 1][j] = "";
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
        return arrayExcelData;
    }

    /**
     * This method is used to get excelsheet path
     *
     * @param logger
     * @return
     */
    public String getExcelSheetPath() {
        PropertyFileUtils propertyFileUtils = new PropertyFileUtils();
        return projectDir + propertyFileUtils.getPropertyValue( "Configuration", "ExcelSheetRelativePath");
    }


    public Map<String, Map<String, String>> getExcelDataAsMap(String path, String sheetName) throws IOException {


        FileInputStream fis = new FileInputStream(path);

        Workbook workbook = new XSSFWorkbook(fis);

        Sheet sheet = workbook.getSheet(sheetName);

        int lastRow = sheet.getLastRowNum();

        Map<String, Map<String, String>> excelFileMap = new HashMap<String, Map<String, String>>();

        Map<String, String> dataMap = new HashMap<String, String>();

        //Looping over entire row
        for (int i = 0; i <= lastRow; i++) {

            Row row = sheet.getRow(i);

            //1st Cell as Value
            Cell valueCell = row.getCell(1);

            //0th Cell as Key
            Cell keyCell = row.getCell(0);

            String value = valueCell.getStringCellValue().trim();
            String key = keyCell.getStringCellValue().trim();

            //Putting key & value in dataMap
            dataMap.put(key, value);

            //Putting dataMap to excelFileMap
            excelFileMap.put("DataSheet", dataMap);
        }

        //Returning excelFileMap
        return excelFileMap;

    }


}

