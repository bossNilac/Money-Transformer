package app;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class XlsParser{
    static int[] linkNumbers = {12555,24317,33742};
    static int[] usedYear= {2012,2017,2020};
    public static ArrayList<Double> tableValues = new ArrayList<Double>();
    public static ArrayList<String> tableKeys = new ArrayList<String>();
    static String fileNameTemplate="xlsTemp.xls";
    static File[] files = new File[linkNumbers.length];
    static String url="";
    final static String tmpFolder="src/main/resources/tmp/";

    public static void main()  {
       downloadXls();
       parseXls();
       deleteFiles();
    }

    static void downloadXls(){
        try {
            for (int j = 0; j < linkNumbers.length; j++) {
                files[j] = new File(tmpFolder + usedYear[j] + fileNameTemplate);
                url = "https://www.bnr.ro/DocumentInformation.aspx?idDocument=" + linkNumbers[j] + "&directLink=1";
                MoneyTransformer.downloadFromUrl(url, files[j]);

            }
        } catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public static double ReadCellNumber(int vRow, int vColumn, String filePath) {
        double value;          //variable for storing the cell value
        Workbook wb=null;           //initialize Workbook null
        try {
            //reading data from a file in the form of bytes
            FileInputStream fis=new FileInputStream(filePath);
            //constructs an XSSFWorkbook object, by buffering the whole stream into the memory
            wb=new HSSFWorkbook(fis);
        } catch(IOException e) {
            e.printStackTrace();
        }
        Sheet sheet=wb.getSheetAt(0);   //getting the XSSFSheet object at given index
        Row row=sheet.getRow(vRow);           //returns the logical row
        Cell cell=row.getCell(vColumn);       //getting the cell representing the given column
        value=cell.getNumericCellValue();//getting cell value
        if(cell!=null) {
            return value;//returns the cell value
        }else{
            System.out.println("==============================================");
            System.out.println("Loop Broken,value:"+cell+"is empty");
            System.out.println("==============================================");
            return -1000;
        }
    }

    static void parseXls(){
        int count=0;

        System.out.println("From file :"+2012+fileNameTemplate);
        for(int j=6;j<67;j++){
            tableKeys.add(ReadCellDate(j,0,tmpFolder+2012+fileNameTemplate));
            tableValues.add(ReadCellNumber(j,1,tmpFolder+2012+fileNameTemplate));
            count++;
        }
        System.out.println("values count:"+count );

        System.out.println("From file :"+2017+fileNameTemplate);
        for(int j=6;j<54;j++){
            tableKeys.add(ReadCellDate(j,0,tmpFolder+2017+fileNameTemplate));
            tableValues.add(ReadCellNumber(j,1,tmpFolder+2017+fileNameTemplate));
            count++;
        }

        System.out.println("From file :"+2020+fileNameTemplate);
        for(int j=7;j<54;j++){
            tableKeys.add(ReadCellDate(j,0,tmpFolder+2020+fileNameTemplate));
            tableValues.add(ReadCellNumber(j,1,tmpFolder+2020+fileNameTemplate));
            count++;
        }
        System.out.println("values count"+count);
    }

    public static void deleteFiles() {
        for (int i = 0; i < files.length; i++){
            files[i].delete();
        }
    }

    public static String ReadCellDate(int vRow, int vColumn, String filePath) {
        String value="";          //variable for storing the cell value
        Workbook wb=null;           //initialize Workbook null
        try {
            //reading data from a file in the form of bytes
            FileInputStream fis=new FileInputStream(filePath);
            //constructs an XSSFWorkbook object, by buffering the whole stream into the memory
            wb=new HSSFWorkbook(fis);
        } catch(IOException e) {
            e.printStackTrace();
        }
        Sheet sheet=wb.getSheetAt(0);   //getting the XSSFSheet object at given index
        Row row=sheet.getRow(vRow);           //returns the logical row
        Cell cell=row.getCell(vColumn);       //getting the cell representing the given column
        if(cell!=null) {
            if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                value = cell.getStringCellValue() + "";
            }if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                value = cell.getDateCellValue() + "";
                DateFormat outputFormat = new SimpleDateFormat("MMM.yy");
                DateFormat inputFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
                Date date = null;
                try {
                    date = inputFormat.parse(value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                 value = outputFormat.format(date).toLowerCase();
            }
        }
        return value;
    }


}

