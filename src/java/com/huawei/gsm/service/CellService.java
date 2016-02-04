/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.service;

import com.huawei.gsm.entity.Cell;
import com.huawei.gsm.entity.Site;
import com.huawei.gsm.repository.CellRepositoryContract;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
@Service
public class CellService implements CellServiceContract{

    @Autowired
    private CellRepositoryContract cellRepository;
    
    @Override
    public List<Cell> getAllCell() {
        return cellRepository.getAllCell();
    }

    @Override
    public List<Cell> getCellBySite(Site site) {
        return cellRepository.getAllCellBySite(site);
    }

    @Override
    public int saveCell(Cell cell) {
        return cellRepository.saveCell(cell);
    }

    @Override
    public int updateCell(Cell cell) {
        return cellRepository.updateCell(cell);
    }

    @Override
    public int deleteCell(Cell cell) {
        return cellRepository.deleteCell(cell);
    }
    
}
