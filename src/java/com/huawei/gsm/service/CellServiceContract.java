/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.service;

import com.huawei.gsm.entity.Cell;
import com.huawei.gsm.entity.Site;
import java.util.List;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public interface CellServiceContract {
    
    public List<Cell> getAllCell();
    public List<Cell> getCellBySite(Site site);
    public int saveCell(Cell cell);
    public int updateCell(Cell cell);
    public int deleteCell(Cell cell);
    
}
