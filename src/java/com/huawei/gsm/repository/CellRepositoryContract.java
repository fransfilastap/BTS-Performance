/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.repository;

import com.huawei.gsm.entity.Cell;
import com.huawei.gsm.entity.Frequency;
import com.huawei.gsm.entity.Site;
import java.util.List;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public interface CellRepositoryContract {
    public List<Cell> getAllCell();
    public List<Cell> getAllCellBySite(Site site);
    //public List<Cell> getAllCellBySector(Sector sector);
    public List<Cell> getAllCellByFrequency(Frequency frequency);
    //public List<Cell> getAllCellBySectorAndFrequency(Sector,Frequency frequency);
    public Cell getCell(String id);
    public int updateCell(Cell cell);
    public int deleteCell(Cell cell);
    public int saveCell(Cell cell);
    public List<Cell> getTopWorstCell(Site site);
}
