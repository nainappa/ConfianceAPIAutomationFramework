package com.confiance.framework.api.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadFromXL {

  /**
   * This method will fetch the data from excel sheet on 2nd Column based on row name 1st column
   * will contain the column header
   * 
   * @param strSheetName Sheet Name in the DataSheet workbook
   * @param strRowdentifier Row Name in the sheet
   * @return String cell data
   */
  public static String getData(String strFilePath, String strSheetName, String strRowIdentifier) {
    Workbook objWorkBook;
    Sheet objCurrentSheet;
    String strAbsFilePath = getAbsolutePath(strFilePath);
    String strContent;
    try {
      objWorkBook = Workbook.getWorkbook(new File(strAbsFilePath));
      objCurrentSheet = objWorkBook.getSheet(strSheetName);
      int rowNum = getRowNumber(objCurrentSheet, strRowIdentifier);
      strContent = objCurrentSheet.getCell(1, rowNum).getContents();
      objWorkBook.close();
      return strContent;
    } catch (Exception e) {
      return null;
      // e.printStackTrace();
    }
  }


  /**
   * This method will be used get the column number based on column Identifier
   * 
   * @param strSheetName Sheet Name in the workbook
   * @param strColIdentifier Column Name in the sheet
   * @return int column number
   * @throws IOException
   */
  @SuppressWarnings("unused")
  private static int getColumnNumber(Sheet objCurrentSheet, String strColIdentifier)
      throws IOException {
    int col;
    try {
      col = objCurrentSheet.findCell(strColIdentifier).getColumn();
      return col;
    } catch (Exception e) {
      return -1;
    }
  }

  /**
   * This method will be used get the row number based on row Identifier
   * 
   * @param strSheetName Sheet Name in the workbook
   * @param strRowIdentifier Row Name in the sheet
   * @return int row number
   * @throws IOException
   */
  private static int getRowNumber(Sheet objCurrentSheet, String strRowIdentifier)
      throws IOException {
    int row;
    try {
      row = objCurrentSheet.findCell(strRowIdentifier).getRow();
      return row;
    } catch (Exception e) {
      return -1;
    }
  }

  /**
   * This method is used to fetch the absolute path of the given excel sheet
   * 
   * @param strFilePath
   * @return String full path of the file
   */
  private static String getAbsolutePath(String strFilePath) {
    try {
      if (strFilePath.contains("https")) {
        return strFilePath;
      } else {
        return new File(strFilePath).getAbsolutePath();
      }
    } catch (Exception objException) {
      return "";
    }
  }


  /**
   * This method returns excel data in the form of 2 dimensional array which can be put into data
   * provider method
   * 
   * @param strFileName
   * @param strSheetName
   * @return Data in 2d array
   */
  public String[][] getExcelData(String strFileName, String strSheetName) {
    String[][] arrayExcelData = null;
    try {
      FileInputStream fs = new FileInputStream(strFileName);
      Workbook wb = Workbook.getWorkbook(fs);
      Sheet sh = wb.getSheet(strSheetName);

      int totalNoOfCols = sh.getColumns();
      int totalNoOfRows = sh.getRows();

      arrayExcelData = new String[totalNoOfRows - 1][totalNoOfCols];

      for (int i = 1; i < totalNoOfRows; i++) {
        for (int j = 0; j < totalNoOfCols; j++) {
          arrayExcelData[i - 1][j] = sh.getCell(j, i).getContents();
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
      e.printStackTrace();
    } catch (BiffException e) {
      e.printStackTrace();
    }
    return arrayExcelData;
  }
}
