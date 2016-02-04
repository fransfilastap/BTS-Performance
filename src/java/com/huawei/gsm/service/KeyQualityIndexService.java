/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.service;

import com.huawei.gsm.entity.AccessType;
import com.huawei.gsm.entity.Cell;
import com.huawei.gsm.entity.KQI;
import com.huawei.gsm.entity.KQIType;
import com.huawei.gsm.entity.WorstCell;
import com.huawei.gsm.repository.KeyQualityIndexRepositoryContract;
import com.huawei.gsm.util.response.gis.LineChart;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
@Service
public class KeyQualityIndexService implements KeyQualityIndexServiceContract{

    private @Autowired KeyQualityIndexRepositoryContract kqiRepository;
    
    @Override
    public KQI getGoldenSiteKQI() {
        return kqiRepository.getGoldenSiteKQI();
    }

    @Override
    public KQI getCellKQI(Cell cell) {
        return kqiRepository.getCellKQI(cell);
    }

    @Override
    public List<WorstCell> getTop5PageBrowsingSuccessRate(AccessType accessType) {
        return kqiRepository.getTop5PageBrowsingSuccessRate(accessType);
    }

    @Override
    public List<WorstCell> getTop5PageBrowsingDelay(AccessType accessType) {
        return kqiRepository.getTop5PageBrowsingDelay(accessType);
    }

    @Override
    public List<WorstCell> getTop5VideoStreamingStartSuccessRate(AccessType accessType) {
        return kqiRepository.getTop5VideoStreamingStartSuccessRate(accessType);
    }

    @Override
    public List<WorstCell> getTop5VideoStreamingStartDelay(AccessType accessType) {
        return kqiRepository.getTop5VideoStreamingStartDelay(accessType);
    }

    @Override
    public Map<KQIType, LineChart> getSectorKQI(List<String> cells) {
        return kqiRepository.getSectorKQI( listToString(cells) );
    }
    private String listToString(List<String> cells){
        StringBuilder builder = new StringBuilder();
        Iterator<String> cellIter = cells.iterator();
        
        while ( cellIter.hasNext() ) {
            builder.append("\""+cellIter.next()+"\",");
        }
        
        String scells = builder.deleteCharAt( builder.length()-1 ).toString();
        
        return scells;
    }

    @Override
    public LineChart getCellKQI(List<String> cells, KQIType type) {
        return kqiRepository.getCellKQI( listToString(cells), type  );
    }
    
}
