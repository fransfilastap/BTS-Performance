/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.controller;

import com.huawei.gsm.entity.Cell;
import com.huawei.gsm.entity.Site;
import com.huawei.gsm.entity.SiteGroup;
import com.huawei.gsm.repository.rowmapper.AuthenticatedUserService;
import com.huawei.gsm.service.CellService;
import com.huawei.gsm.service.SiteServiceContract;
import com.huawei.gsm.service.UserServiceContract;
import com.huawei.gsm.string.MessageConstant;
import com.huawei.gsm.string.UILang;
import com.huawei.gsm.util.requestholder.CellEditJson;
import com.huawei.gsm.util.requestholder.CellJson;
import com.huawei.gsm.util.datatable.DataTableRequestWrapper;
import com.huawei.gsm.util.requestholder.RequestStatus;
import com.huawei.gsm.util.requestholder.NormalSiteRequestWrapper;
import com.huawei.gsm.util.requestholder.NormalSiteResponseWrapper;
import com.huawei.gsm.util.requestholder.SiteEditJson;
import com.huawei.gsm.util.requestholder.SiteJson;
import com.huawei.gsm.util.datatable.SiteDataTable;
import com.huawei.gsm.util.response.SiteExcelWrapper;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
@Controller
public class AdminController {
    
    @RequestMapping(value={ "/admin","/admin/index.html" })
    public ModelAndView adminIndex( ModelAndView model){
        
        model.addObject("fullname", auService.getAuthenticatedUserFullname(userService));
        model.addObject("file", "admin_welcome.jsp");
        model.addObject("current", "index");
        model.setViewName("admin");
        return model;
    }     
    
    @RequestMapping(value={ "/admin/data/sites","/admin/data/sites.html" })
    public ModelAndView sites( ModelAndView model){
        model.addObject("fullname", auService.getAuthenticatedUserFullname(userService));
        model.addObject("file", "site.list.jsp");
        model.addObject("current", "sites");
        model.setViewName("admin");
        return model;
    }    
    
    @RequestMapping( value = { "/admin/data/sites/save" }, method = RequestMethod.POST, consumes = "application/json" )
    public @ResponseBody RequestStatus saveSite( @RequestBody NormalSiteRequestWrapper requestWrapper ){
        Site site = new Site();
        site.setSiteId( requestWrapper.getSite().getSiteId() );
        site.setSiteName( requestWrapper.getSite().getSiteName() );
        site.setAddress( requestWrapper.getSite().getAddress() );
        site.setLatitude( requestWrapper.getSite().getLatitude() );
        site.setLongitude( requestWrapper.getSite().getLongitude() );
        site.setGroup( (requestWrapper.getSite().getSite_group().equalsIgnoreCase("EVENT_SITE") ? SiteGroup.EVENT_SITE : SiteGroup.GOLDEN_SITE) );
        List<Cell> _cells  = new ArrayList<>();
        
        requestWrapper.getCells().stream().map((_cell) -> {
            Cell cell = new Cell();
            cell.setCellIndex( _cell.getCellIndex() );
            cell.setCellName( _cell.getCellName() );
            cell.setFrequency( ( _cell.getFrequency() ) );
            return cell;            
        }).forEach((cell) -> {
            _cells.add(cell);
        });

        site.setCells(_cells);
        
        RequestStatus rs = new RequestStatus();
        
       if(siteService.saveSite(site) > 0 ){
           rs.setStatus(true);
           rs.setMessage( MessageConstant.SITE_INSERT_SUCCESS );
           
           return rs;
       }
       
        rs.setStatus(true);
        rs.setMessage( MessageConstant.SITE_GENERAL_FAIL );

        return rs;       
        
    }
    
    @RequestMapping(value = {"/admin/data/sites/list"},method = RequestMethod.POST,consumes = {"application/json"})
    public @ResponseBody SiteDataTable siteList(@RequestBody DataTableRequestWrapper dataTableRequest, HttpServletRequest rq){
        
        //request
        String searchKeyword = dataTableRequest.getSearch().getValue();
        int start = dataTableRequest.getStart();
        int length = dataTableRequest.getLength();
        int draw = dataTableRequest.getDraw();
        String order = dataTableRequest.getOrder().get(0).getDir();
        int column = dataTableRequest.getOrder().get(0).getColumn();
        String columnName = dataTableRequest.getColumns().get( column ).getData();
        //</-- end of request variable -->
        
        //response
        int rDraw = draw;
        int recordsTotal = siteService.getTotalRecords();
        int recordsFiltered = siteService.getTotalFilteredRecords( searchKeyword );
        //DataTable
        SiteDataTable response = new SiteDataTable(); 
        //all sites
        List<Site> sites = siteService.getListByRequest(searchKeyword, start, length, columnName, order);
        //siteJson list
        List<SiteJson> siteObjects = new ArrayList<>();
        
        //baseurl
        String baseUrl = String.format("%s://%s:%d%s",rq.getScheme() , rq.getServerName(), rq.getServerPort(), rq.getContextPath());

        sites.stream().map((get) -> {
            SiteJson jsonObj;
            jsonObj = new SiteJson();
            jsonObj.setAddress(get.getAddress());
            jsonObj.setSite_group(get.getGroup().toString() );
            jsonObj.setLatitude( get.getLatitude() );
            jsonObj.setLongitude( get.getLongitude() );
            jsonObj.setSiteId( get.getSiteId() );
            jsonObj.setSiteName( get.getSiteName() );
            jsonObj.setAction("<div class='btn-group'>\n" +
"                      <button type='button' class='btn btn-success btn-xs'>"+UILang.BUTTON_CRUD+"</button>\n" +
"                      <button type='button' class='btn btn-success dropdown-toggle btn-xs' data-toggle='dropdown'>\n" +
"                        <span class='caret'></span>\n" +
"                        <span class='sr-only'>Toggle Dropdown</span>\n" +
"                      </button>\n" +
"                      <ul class='dropdown-menu' role='menu'>\n" +
"                        <li><a href='"+baseUrl+"/admin/data/site/get/"+get.getId()+"' class='detail'>"+UILang.BUTTON_CRUD_DETAIL+"</a></li>\n" +
"                        <li><a href='"+baseUrl+"/admin/data/site/get/"+get.getId()+"' class='edit'>"+UILang.BUTTON_CRUD_EDIT+"</a></li>\n" +                    
"                        <li class='divider'></li>\n" +
"                        <li><a href='"+baseUrl+"/admin/data/site/delete/"+get.getId()+"' class='delete-site'>"+UILang.BUTTON_CRUD_DELETE+"</a></li>\n" +
"                      </ul>\n" +
"                    </div>");
            return jsonObj;
        }).forEach((jsonObj) -> {
            siteObjects.add( jsonObj );
        });
        
        response.setData(siteObjects);
        response.setRecordsTotal(recordsTotal);
        response.setRecordsFiltered( recordsFiltered );
        response.setDraw( rDraw );
        
        return response;
        
    }
    
    @RequestMapping( value = {"/admin/data/site/get/{siteId}"}, method = RequestMethod.GET )
    public @ResponseBody NormalSiteResponseWrapper getSite( @PathVariable int siteId ){
        
        NormalSiteResponseWrapper siteWrapper = new NormalSiteResponseWrapper();
        Site site = siteService.getSite(siteId);
        SiteEditJson siteJson = new SiteEditJson();
        siteJson.setAddress( site.getAddress() );
        siteJson.setLatitude( site.getLatitude() );
        siteJson.setLongitude( site.getLongitude() );
        siteJson.setSiteName( site.getSiteName() );
        siteJson.setSite_group( site.getGroup().toString() );
        siteJson.setSiteId( site.getSiteId() );
        siteJson.setId(siteId);
        
        System.out.println( site );
        
        List<CellJson> cellJsons = new ArrayList<>();
        List<Cell> cells = cellService.getCellBySite(site);
        cells.stream().forEach((get) -> {
            CellEditJson json = new CellEditJson();
            json.setCellIndex(get.getCellIndex());
            json.setCellName( get.getCellName() );
            json.setFrequency( get.getFrequency() );
            json.setId( get.getId() );
            
            cellJsons.add(json);
        });
        
        siteWrapper.setId(siteId);
        siteWrapper.setSite(siteJson);
        siteWrapper.setCells(cellJsons);
       
        return siteWrapper;
    }
    
    @RequestMapping( value = {"/admin/data/site/delete/{siteId}"} , method = RequestMethod.GET )
    public @ResponseBody RequestStatus deleteSite( @PathVariable int siteId ){
        
        RequestStatus requestStatus = new RequestStatus();

        requestStatus.setStatus(false);
        requestStatus.setMessage( MessageConstant.SITE_GENERAL_FAIL );        
        
        if( siteService.deleteSite( siteService.getSite(siteId) ) > 0 ){
            requestStatus.setStatus(true);
            requestStatus.setMessage( MessageConstant.SITE_DELETE_SUCCESS );
        }
        
        
        return requestStatus;
    }
    
    @RequestMapping( value = {"/admin/data/site/update"}, method = RequestMethod.POST )
    public @ResponseBody RequestStatus updateSite( @RequestBody NormalSiteRequestWrapper siteCrudRequestWrapper ){
        
        RequestStatus requestStatus = new RequestStatus();
        
        
        
        return requestStatus;
    }
    
    @RequestMapping( value = {"/admin/data/site/upload"}, method = RequestMethod.POST )
    public @ResponseBody RequestStatus uploadExcel( @RequestParam("excel_file") MultipartFile file
            , HttpServletRequest request ) throws IOException{
        
        RequestStatus status = new RequestStatus();
        
        String filePath = env.getRequiredProperty("upload.dir");
        
        if( !file.isEmpty() ){
            //doprocess here
            byte[] bytes = file.getBytes();
            File sitesFile = new File( filePath+file.getOriginalFilename() );
            try (BufferedOutputStream stream = new BufferedOutputStream( new FileOutputStream( sitesFile )  )) {
                stream.write(bytes);
                try {
                    parseExcelFile(sitesFile);
                    status.setStatus(true);
                    status.setMessage("Upload success");
                } catch (InvalidFormatException ex) {
                    status.setStatus(false);
                    status.setMessage( ex.getMessage() ); 
                }
            }catch(IOException ex){
                status.setStatus(false);
                status.setMessage( ex.getMessage() );                
            }
        }
        else{
            status.setStatus(false);
            status.setMessage( "File is empty" );             
        }
        
        return status;
        
    };
    
    @RequestMapping( value = {"/admin/data/sites/export"}, method = RequestMethod.GET )
    public ModelAndView exportSiteToExcel(){
        List<SiteExcelWrapper> excelData = siteService.exportToExcel();
        return new ModelAndView("excelView","exportData", excelData);
    }
    
    
    
    private void parseExcelFile(File file) throws IOException, InvalidFormatException{
    
        XSSFWorkbook workbook = new XSSFWorkbook( file );
        XSSFSheet sheet = workbook.getSheetAt(0);
        
        Iterator<Row> rowIterator = sheet.iterator();
        
        Map<String,Site> sites = new HashMap<>(); 
        Set<Cell> cells = new HashSet<>();
        
        if( rowIterator.hasNext() ){
            rowIterator.next();
        }
        
        int i = 0;
        
        while (rowIterator.hasNext()){
            

            
            Row row = rowIterator.next();
            String siteId;
            String cellIndex;
            String cellId;
            
            try{
                row.getCell(1).getStringCellValue();
            }catch( NullPointerException ex ){
                break;
            }            
            
            if( row.getCell(0).getCellType() == XSSFCell.CELL_TYPE_NUMERIC ){
                double sId = row.getCell(0).getNumericCellValue();
                siteId = String.valueOf(sId).replaceAll("\\.?0*$", "");
            }else{
                siteId = row.getCell(0).getStringCellValue();
            }
            
            if( row.getCell(6).getCellType() == XSSFCell.CELL_TYPE_NUMERIC ){
                double cId = row.getCell(6).getNumericCellValue();
                cellIndex = String.valueOf(cId).replaceAll("\\.?0*$", "");
            }else{
                cellIndex = row.getCell(6).getStringCellValue();
            }
            
            if( row.getCell(8).getCellType() == XSSFCell.CELL_TYPE_NUMERIC ){
                double ceId = row.getCell(8).getNumericCellValue();
                cellId = String.valueOf( ceId ).replaceAll("\\.?0*$", "");
            }else{
                cellId = row.getCell(8).getStringCellValue();
            }
            
            Site site = new Site();
            site.setAddress(row.getCell(2).getStringCellValue());
            site.setGroup( ( row.getCell(5).getStringCellValue().toLowerCase().equalsIgnoreCase("GOLDEN_SITE") ? SiteGroup.GOLDEN_SITE : SiteGroup.EVENT_SITE ) );
            site.setSiteId(siteId);
            site.setSiteName(row.getCell(1).getStringCellValue());
            site.setLatitude(row.getCell(4).getNumericCellValue());
            site.setLongitude(row.getCell(3).getNumericCellValue());
            site.setCells(new ArrayList<>());
            
            sites.put(siteId, site);
            
            
            Cell cell = new Cell();
            cell.setCellId(cellId);
            cell.setCellIndex(cellIndex);
            cell.setCellName(row.getCell(7).getStringCellValue());
            cell.setFrequency(row.getCell(9).getStringCellValue());
            cell.setSite(site);
            
            cells.add(cell);
            
            
            
            i++;
            
        }
        
        
        Iterator<Site> iter = sites.values().iterator();
        while (iter.hasNext()) {
            Site site = iter.next();
            
            cells.stream().filter(( Cell s) -> {
                return s.getSite().getSiteId().equalsIgnoreCase( site.getSiteId() );
            } ).forEach(s->{
                site.getCells().add(s);
            });

        }
        
        List<Site> sitesX = new ArrayList<>(sites.values());
        
        siteService.saveSiteBatch(sitesX);
        
        
    }
    
    @Autowired
    private Environment env;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private UserServiceContract userService;
    @Autowired
    private AuthenticatedUserService auService;   
    @Autowired
    private CellService cellService;
    @Autowired
    private SiteServiceContract siteService;
    
}
