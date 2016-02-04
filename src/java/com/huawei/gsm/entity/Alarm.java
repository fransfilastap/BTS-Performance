/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.entity;

import java.sql.Timestamp;


/**
 *
 * @author fransfilastap
 */
public class Alarm {
    
    private int Id;
    
    private Timestamp lastOccurence;

    private String node;
    
    private String nodeAlias;
    
    private String parentNode;

    private String AlarmName;
    
    private Severity Severity;
    
    private String ticketId;
    
    private String EMSAlarmId;
    
    private String siteName;
    
    private String summary;
    
    private String siteId;
    
    private String cellId;
    
    private String ticketStatus;
    
    private Site site;
    
    private Cell cell;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public Timestamp getLastOccurence() {
        return lastOccurence;
    }

    public void setLastOccurence(Timestamp lastOccurence) {
        this.lastOccurence = lastOccurence;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getNodeAlias() {
        return nodeAlias;
    }

    public void setNodeAlias(String nodeAlias) {
        this.nodeAlias = nodeAlias;
    }

    public String getParentNode() {
        return parentNode;
    }

    public void setParentNode(String parentNode) {
        this.parentNode = parentNode;
    }

    public String getAlarmName() {
        return AlarmName;
    }

    public void setAlarmName(String AlarmName) {
        this.AlarmName = AlarmName;
    }

    public Severity getSeverity() {
        return Severity;
    }

    public void setSeverity(Severity Severity) {
        this.Severity = Severity;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getEMSAlarmId() {
        return EMSAlarmId;
    }

    public void setEMSAlarmId(String EMSAlarmId) {
        this.EMSAlarmId = EMSAlarmId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Cell getCell() {
        return cell;
    }
    
    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
    
    
    
}
