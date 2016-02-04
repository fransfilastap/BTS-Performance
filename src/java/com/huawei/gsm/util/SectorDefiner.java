/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.util;

import com.huawei.gsm.entity.Frequency;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author Administrator
 */
@Component
public class SectorDefiner {
    
    private Map<Integer,Integer> GSM_AZIMUTS;
    private Map<Integer,Integer> GSM_SECTORS;
    private Map<Integer,Integer> UMTS_LTE_AZIMUTS;
    private Map<Integer,Integer> UMTS_LTE_SECTORS;
    
    
    public SectorDefiner(){
        GSM_AZIMUTS = new HashMap<>();
        GSM_AZIMUTS.put(1, 10);
        GSM_AZIMUTS.put(2, 40);
        GSM_AZIMUTS.put(3, 90);
        GSM_AZIMUTS.put(4, 115);
        GSM_AZIMUTS.put(5, 180);
        GSM_AZIMUTS.put(6, 220);
        GSM_AZIMUTS.put(7, 280);
        GSM_AZIMUTS.put(8, 310);

        GSM_SECTORS = new HashMap<>();
        GSM_SECTORS.put(10, 1);
        GSM_SECTORS.put(40 ,2 );
        GSM_SECTORS.put(90,3 );
        GSM_SECTORS.put(115,4 );
        GSM_SECTORS.put(180,5 );
        GSM_SECTORS.put(220,6 );
        GSM_SECTORS.put(280,7 );
        GSM_SECTORS.put(310,8 );
        
        UMTS_LTE_SECTORS = new HashMap<>();
        UMTS_LTE_SECTORS.put(120,1);
        UMTS_LTE_SECTORS.put(240,2);
        UMTS_LTE_SECTORS.put(360,3);    
        
        UMTS_LTE_AZIMUTS = new HashMap<>();
        UMTS_LTE_AZIMUTS.put(1, 120);
        UMTS_LTE_AZIMUTS.put(2, 240);
        UMTS_LTE_AZIMUTS.put(3, 360);    
        
        
    }
    
    
    public int defineAzimut(String cellname,Frequency ne){
        
        int sector = 1;
        int azimut = 0;
        
        if( cellname.isEmpty() || cellname.equalsIgnoreCase("") ){
            return sector;
        }
        
        if( ne == Frequency.GSM ){
           
            String rawAzimut = cellname.substring( cellname.length()-2, cellname.length() );
            rawAzimut = rawAzimut.replaceFirst("^0+(?!$)", "").replaceAll("[a-z_A-Z]", "");
            int azimutRange = 0;
            if( rawAzimut.matches("[a-zA-Z]") ){
                rawAzimut = rawAzimut.substring(0,1);
            }
            
            if( Integer.parseInt( rawAzimut ) > 8 ){
                if( Integer.parseInt( rawAzimut ) * 10 > 360 ){
                    azimutRange = Integer.parseInt( rawAzimut );
                }
                else
                {
                    azimutRange = Integer.parseInt( rawAzimut ) * 10;
                }
                
            }
            else
            {
                azimutRange = Integer.parseInt( rawAzimut );           
            }
            
            if( ( azimutRange >= 0 && azimutRange <= 22 ) || ( azimutRange >= 338 && azimutRange <= 360 ) ){
                sector = 1;
            }
            else if( azimutRange >= 23 && azimutRange <= 67 ) {
                sector = 2;
            }
            else if( azimutRange >= 68 && azimutRange <= 112  ){
                sector = 3;
            }
            else if( azimutRange >= 113 && azimutRange <= 157 ){
                sector = 4;
            }
            else if( azimutRange >= 158 && azimutRange <= 202 ){
                sector = 5;
            }
            else if( azimutRange >= 203 && azimutRange <= 247 ){
                sector = 6;
            }
            else if( azimutRange >= 248 && azimutRange <= 292 ){
                sector = 7;
            }
            else if( azimutRange >= 293 && azimutRange <= 337 ){
                sector = 8;
            }else{
                sector = 1;
            }
        }
        else{
            String azz = cellname.substring(cellname.length()-2, cellname.length());
            azz = azz.replaceFirst("^0+(?!$)", "").replaceAll("[a-z_A-Z]", "");
            
            int azzint = Integer.parseInt(azz);
            
            if( azzint > 9 ){
               String temp = String.valueOf( azzint ).substring(0,1);
               azzint = Integer.parseInt(temp);
            }
            
            int[] arr = new int[]{ 1,2,3 };
            while( isInArray( arr, azzint ) == false ){
                azzint = azzint - 3;
            }
           sector = azzint;
        }
        
        return sector;
    }
    
    private static boolean isInArray(int[] arr,int i){
        boolean isExists = false;
        for (int j = 0; j < arr.length; j++) {
            if( i == arr[j] ){
                isExists = true;
                break;
            }
        }
        return isExists;
    }

    public Map<Integer, Integer> getGSM_SECTORS() {
        return GSM_SECTORS;
    }

    public void setGSM_SECTORS(Map<Integer, Integer> GSM_SECTORS) {
        this.GSM_SECTORS = GSM_SECTORS;
    }

    public Map<Integer, Integer> getUMTS_LTE_SECTORS() {
        return UMTS_LTE_SECTORS;
    }

    public void setUMTS_LTE_SECTORS(Map<Integer, Integer> UMTS_LTE_SECTORS) {
        this.UMTS_LTE_SECTORS = UMTS_LTE_SECTORS;
    }

    public Map<Integer, Integer> getGSM_AZIMUTS() {
        return GSM_AZIMUTS;
    }

    public void setGSM_AZIMUTS(Map<Integer, Integer> GSM_AZIMUTS) {
        this.GSM_AZIMUTS = GSM_AZIMUTS;
    }

    public Map<Integer, Integer> getUMTS_LTE_AZIMUTS() {
        return UMTS_LTE_AZIMUTS;
    }

    public void setUMTS_LTE_AZIMUTS(Map<Integer, Integer> UMTS_LTE_AZIMUTS) {
        this.UMTS_LTE_AZIMUTS = UMTS_LTE_AZIMUTS;
    }
    
    
    
    
}
