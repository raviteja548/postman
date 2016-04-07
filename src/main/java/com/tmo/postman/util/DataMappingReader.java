package com.tmo.postman.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.tmo.postman.model.RowData;

public class DataMappingReader {
	
	public List<RowData> readDataMappingFile(String filePath){
		List<RowData> rowDataList = new ArrayList<RowData>();
		int startRowNo = 1;
		int rows;
		try {
			FileInputStream file = new FileInputStream(new File(filePath));
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(0);
			rows = sheet.getLastRowNum() + 1;
			for (int i = startRowNo; i < rows; i++) {
				RowData rd = new RowData();
				String xpath = sheet.getRow(i).getCell(0).getStringCellValue();
				rd.setXpath(xpath);
				rd.setMinOcc(sheet.getRow(i).getCell(1).getStringCellValue());
				rd.setMaxOcc(sheet.getRow(i).getCell(2).getStringCellValue());
				rd.setTyp(sheet.getRow(i).getCell(3).getStringCellValue());
				
				Map<String,String> xpathSplittedMap = getLastAndLastButOneFromXPath(xpath);
				rd.setLastElement(xpathSplittedMap.get("last"));
				rd.setPreviousElement(xpathSplittedMap.get("previousElement"));
				rowDataList.add(rd);
			}

			file.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rowDataList;
	}
	
	public Map<String,String> getLastAndLastButOneFromXPath(String xpath){
		Map<String,String> map = new HashMap<String,String>();
		
		if(!xpath.contains("/")){
			map.put("last", xpath);
			map.put("previousElement", xpath);
		}else{
			
			String[] xpathArray = xpath.split("/");
			map.put("last", xpathArray[xpathArray.length-1]);
			map.put("previousElement", xpathArray[xpathArray.length-2]);
			
			
		}
		
		return map;
	}
	
}
