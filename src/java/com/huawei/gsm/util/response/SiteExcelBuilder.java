/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.util.response;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public class SiteExcelBuilder extends AbstractExcelView{

    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfw, HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        
        List<SiteExcelWrapper> data = (List<SiteExcelWrapper>) map.get("exportData");
        
        HSSFSheet sheet = hssfw.createSheet("SITES");
        int rowSize = data.size()+1;
        int columnSize = 9;
        String[] columns = new String[]{ "Site ID", "Site Name", "Address" , "latitude" , "longitude" , "Site Group" , "Cell Index", "Cell Name", "Frequency" };
        
        
        Row headerRow = sheet.createRow(0);
        
        for (int i = 0; i < columns.length; i++) {
            String column = columns[i];
            headerRow.createCell(i).setCellValue(column);
        }

                
        for (int i = 1; i <= data.size(); i++) {
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue( data.get((i-1)  ).getSiteId() );
            row.createCell(1).setCellValue( data.get((i-1)  ).getSiteName());
            row.createCell(2).setCellValue( data.get((i-1)  ).getAddress());
            row.createCell(3).setCellValue( data.get((i-1)  ).getLatitute());
            row.createCell(4).setCellValue( data.get((i-1)  ).getLongitude() );
            row.createCell(5).setCellValue( data.get((i-1)  ).getSiteGroup() );
            row.createCell(6).setCellValue( data.get((i-1)  ).getCellIndex() );
            row.createCell(7).setCellValue( data.get((i-1)  ).getCellName() );
            row.createCell(8).setCellValue( data.get((i-1)  ).getFrequency() );
        }
        
        
        
    }
    
}
