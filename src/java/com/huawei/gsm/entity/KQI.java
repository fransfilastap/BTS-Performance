/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.entity;

/**
 *
 * @author Administrator
 */
public class KQI {
    
    private VideoStreamingKQI videoStreamingKQI;
    private PageBrowsingKQI pageBrowsingKQI;
    private Cell cell;

    public KQI(VideoStreamingKQI videoStreamingKQI, PageBrowsingKQI pageBrowsingKQI, Cell cell) {
        this.videoStreamingKQI = videoStreamingKQI;
        this.pageBrowsingKQI = pageBrowsingKQI;
        this.cell = cell;
    }

    
    public KQI(VideoStreamingKQI videoStreamingKQI, PageBrowsingKQI pageBrowsingKQI) {
        this.videoStreamingKQI = videoStreamingKQI;
        this.pageBrowsingKQI = pageBrowsingKQI;
    }

    public KQI() {
    }

    public VideoStreamingKQI getVideoStreamingKQI() {
        return videoStreamingKQI;
    }

    public void setVideoStreamingKQI(VideoStreamingKQI videoStreamingKQI) {
        this.videoStreamingKQI = videoStreamingKQI;
    }

    public PageBrowsingKQI getPageBrowsingKQI() {
        return pageBrowsingKQI;
    }

    public void setPageBrowsingKQI(PageBrowsingKQI pageBrowsingKQI) {
        this.pageBrowsingKQI = pageBrowsingKQI;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
    
    
    //inner class for KQI value holder
    public static class VideoStreamingKQI{
        
        private double videoStreamingSuccessRate;
        private double videoStreamingStartDelay;
        private double videoStreamingSuccessRateBaseLine;
        private double videoStreamingStartDelayBaseLine;

        public VideoStreamingKQI() {
            init();
        }
        
        private void init(){
            videoStreamingStartDelayBaseLine = KQIBaselineConstant.VIDEO_STREAMING_START_DELAY;
            videoStreamingSuccessRateBaseLine = KQIBaselineConstant.VIDEO_STREAMING_SUCCESS_RATE;
        }

        public VideoStreamingKQI(double videoStreamingSuccessRate, double videoStreamingStartDelay) {
            this.videoStreamingSuccessRate = videoStreamingSuccessRate;
            this.videoStreamingStartDelay = videoStreamingStartDelay;
            init();
        }

        public double getVideoStreamingSuccessRate() {
            return videoStreamingSuccessRate;
        }

        public void setVideoStreamingSuccessRate(double videoStreamingSuccessRate) {
            this.videoStreamingSuccessRate = videoStreamingSuccessRate;
        }

        public double getVideoStreamingStartDelay() {
            return videoStreamingStartDelay;
        }

        public void setVideoStreamingStartDelay(double videoStreamingStartDelay) {
            this.videoStreamingStartDelay = videoStreamingStartDelay;
        }

        public double getVideoStreamingSuccessRateBaseLine() {
            return videoStreamingSuccessRateBaseLine;
        }

        public void setVideoStreamingSuccessRateBaseLine(double videoStreamingSuccessRateBaseLine) {
            this.videoStreamingSuccessRateBaseLine = videoStreamingSuccessRateBaseLine;
        }

        public double getVideoStreamingStartDelayBaseLine() {
            return videoStreamingStartDelayBaseLine;
        }

        public void setVideoStreamingStartDelayBaseLine(double videoStreamingStartDelayBaseLine) {
            this.videoStreamingStartDelayBaseLine = videoStreamingStartDelayBaseLine;
        }
        
        
        
    }
    
    public static class PageBrowsingKQI{
        private double pageBrowsingSuccessRate;
        private double pageBrowsingDelay;
        private double pageBrowsingSuccessRateBaseLine;
        private double pageBrowsingDelayBaseLine;

        public PageBrowsingKQI(double pageBrowsingSuccessRate, double pageBrowsingDelay) {
            this.pageBrowsingSuccessRate = pageBrowsingSuccessRate;
            this.pageBrowsingDelay = pageBrowsingDelay;
            init();
        }
        
        private void init(){
            pageBrowsingDelayBaseLine = KQIBaselineConstant.PAGE_BROWSING_DELAY;
            pageBrowsingSuccessRateBaseLine = KQIBaselineConstant.PAGE_BROWSING_SUCCESS_RATE;
        }

        public PageBrowsingKQI() {
            init();
        }

        public double getPageBrowsingSuccessRate() {
            return pageBrowsingSuccessRate;
        }

        public void setPageBrowsingSuccessRate(double pageBrowsingSuccessRate) {
            this.pageBrowsingSuccessRate = pageBrowsingSuccessRate;
        }

        public double getPageBrowsingDelay() {
            return pageBrowsingDelay;
        }

        public void setPageBrowsingDelay(double pageBrowsingDelay) {
            this.pageBrowsingDelay = pageBrowsingDelay;
        }

        public double getPageBrowsingSuccessRateBaseLine() {
            return pageBrowsingSuccessRateBaseLine;
        }

        public void setPageBrowsingSuccessRateBaseLine(double pageBrowsingSuccessRateBaseLine) {
            this.pageBrowsingSuccessRateBaseLine = pageBrowsingSuccessRateBaseLine;
        }

        public double getPageBrowsingDelayBaseLine() {
            return pageBrowsingDelayBaseLine;
        }

        public void setPageBrowsingDelayBaseLine(double pageBrowsingDelayBaseLine) {
            this.pageBrowsingDelayBaseLine = pageBrowsingDelayBaseLine;
        }

        
        
    }
    
}
