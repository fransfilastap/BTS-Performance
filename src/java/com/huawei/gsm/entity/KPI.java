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
public class KPI {
    
    private KPI2G kpi2g;
    private KPI3G kpi3g;
    private KPI4G kpi4g;

    public KPI(KPI2G kpi2g, KPI3G kpi3g, KPI4G kpi4g) {
        this.kpi2g = kpi2g;
        this.kpi3g = kpi3g;
        this.kpi4g = kpi4g;
    }

    public KPI() {
    }

    
    
    public KPI2G getKpi2g() {
        return kpi2g;
    }

    public void setKpi2g(KPI2G kpi2g) {
        this.kpi2g = kpi2g;
    }

    public KPI3G getKpi3g() {
        return kpi3g;
    }

    public void setKpi3g(KPI3G kpi3g) {
        this.kpi3g = kpi3g;
    }

    public KPI4G getKpi4g() {
        return kpi4g;
    }

    public void setKpi4g(KPI4G kpi4g) {
        this.kpi4g = kpi4g;
    }
    
    
    public static class KPI2G{
        
        private double RANCallSetupSuccessRate;
        private double CallMinutesBetweenDrop;
        private double baseLine1RANCallSetupSuccessRate;
        private double baseLine2RANCallSetupSuccessRate;
        private double baseLine1CallMinutesBetweenDrop;
        private double baseLine2CallMinutesBetweenDrop;
        private double csTraffic;
        private double psTraffic;
        
        public KPI2G() {
            initBaseLine();
        }
        
        private void initBaseLine(){
            this.baseLine1RANCallSetupSuccessRate = KPIBaseLineConstant.BASELINE2G_1_RAN_CALL_SETUP_SUCCESS_RATE;
            this.baseLine1CallMinutesBetweenDrop = KPIBaseLineConstant.BASELINE2G_1_CALL_MINUTES_BETWEEN_DROP;
            this.baseLine2CallMinutesBetweenDrop = KPIBaseLineConstant.BASELINE2G_2_CALL_MINUTES_BETWEEN_DROP;
            this.baseLine2RANCallSetupSuccessRate = KPIBaseLineConstant.BASELINE2G_2_RAN_CALL_SETUP_SUCCESS_RATE;
        }

        public KPI2G(double RANCallSetupSuccessRate, double CallMinutesBetweenDrop) {
            this.RANCallSetupSuccessRate = RANCallSetupSuccessRate;
            this.CallMinutesBetweenDrop = CallMinutesBetweenDrop;
            initBaseLine();
        }

        public double getRANCallSetupSuccessRate() {
            return RANCallSetupSuccessRate;
        }

        public void setRANCallSetupSuccessRate(double RANCallSetupSuccessRate) {
            this.RANCallSetupSuccessRate = RANCallSetupSuccessRate;
        }

        public double getCallMinutesBetweenDrop() {
            return CallMinutesBetweenDrop;
        }

        public void setCallMinutesBetweenDrop(double CallMinutesBetweenDrop) {
            this.CallMinutesBetweenDrop = CallMinutesBetweenDrop;
        }

        public double getBaseLine1RANCallSetupSuccessRate() {
            return baseLine1RANCallSetupSuccessRate;
        }

        public void setBaseLine1RANCallSetupSuccessRate(double baseLine1RANCallSetupSuccessRate) {
            this.baseLine1RANCallSetupSuccessRate = baseLine1RANCallSetupSuccessRate;
        }

        public double getBaseLine1CallMinutesBetweenDrop() {
            return baseLine1CallMinutesBetweenDrop;
        }

        public void setBaseLine1CallMinutesBetweenDrop(double baseLine1CallMinutesBetweenDrop) {
            this.baseLine1CallMinutesBetweenDrop = baseLine1CallMinutesBetweenDrop;
        }

        public double getBaseLine2RANCallSetupSuccessRate() {
            return baseLine2RANCallSetupSuccessRate;
        }

        public void setBaseLine2RANCallSetupSuccessRate(double baseLine2RANCallSetupSuccessRate) {
            this.baseLine2RANCallSetupSuccessRate = baseLine2RANCallSetupSuccessRate;
        }

        public double getBaseLine2CallMinutesBetweenDrop() {
            return baseLine2CallMinutesBetweenDrop;
        }

        public void setBaseLine2CallMinutesBetweenDrop(double baseLine2CallMinutesBetweenDrop) {
            this.baseLine2CallMinutesBetweenDrop = baseLine2CallMinutesBetweenDrop;
        }

        public double getCsTraffic() {
            return csTraffic;
        }

        public void setCsTraffic(double csTraffic) {
            this.csTraffic = csTraffic;
        }

        public double getPsTraffic() {
            return psTraffic;
        }

        public void setPsTraffic(double psTraffic) {
            this.psTraffic = psTraffic;
        }

        
    }
    
    public static class KPI3G extends KPI2G{
        
        private double RANPSAccessibility;
        private double RANMinutesBetweenPSAbnormalReleases;
        private double baseLine1RANPSAccessibility;
        private double baseLine2RANPSAccessibility;
        private double baseLine1RANMinutesBetweenPSAbnormalReleases;
        private double baseLine2RANMinutesBetweenPSAbnormalReleases ;

        public KPI3G() {
            init();
        }
        
        private void init(){
            super.baseLine1RANCallSetupSuccessRate = KPIBaseLineConstant.BASELINE3G_1_RAN_CALL_SETUP_SUCCESS_RATE;
            super.baseLine2RANCallSetupSuccessRate = KPIBaseLineConstant.BASELINE3G_2_RAN_CALL_SETUP_SUCCESS_RATE;
            super.baseLine1CallMinutesBetweenDrop = KPIBaseLineConstant.BASELINE3G_1_CALL_MINUTES_BETWEEN_DROP;
            super.baseLine2CallMinutesBetweenDrop = KPIBaseLineConstant.BASELINE3G_2_CALL_MINUTES_BETWEEN_DROP;  
            this.baseLine1RANMinutesBetweenPSAbnormalReleases = KPIBaseLineConstant.BASELINE3G_1_RAN_MINUTES_BETWEEN_PS_ABNORMAL_RELEASES;
            this.baseLine2RANMinutesBetweenPSAbnormalReleases = KPIBaseLineConstant.BASELINE3G_2_RAN_MINUTES_BETWEEN_PS_ABNORMAL_RELEASES;
            this.baseLine1RANPSAccessibility = KPIBaseLineConstant.BASELINE3G_1_RAN_PS_ACCESSIBILITY;
            this.baseLine2RANPSAccessibility = KPIBaseLineConstant.BASELINE3G_2_RAN_PS_ACCESSIBILITY;
        }

        public KPI3G(double RANPSAccessibility) {
            init();
            this.RANPSAccessibility = RANPSAccessibility;
        }

        public KPI3G(double RANPSAccessibility, double RANCallSetupSuccessRate, double CallMinutesBetweenDrop,double RANMinutesBetweenPSAbnormalReleases) {
            super(RANCallSetupSuccessRate, CallMinutesBetweenDrop);
            init();
            this.RANPSAccessibility = RANPSAccessibility;
            this.RANMinutesBetweenPSAbnormalReleases = RANMinutesBetweenPSAbnormalReleases;
        }

        public double getRANPSAccessibility() {
            return RANPSAccessibility;
        }

        public void setRANPSAccessibility(double RANPSAccessibility) {
            this.RANPSAccessibility = RANPSAccessibility;
        }

        public double getBaseLine1RANPSAccessibility() {
            return baseLine1RANPSAccessibility;
        }

        public void setBaseLine1RANPSAccessibility(double baseLine1RANPSAccessibility) {
            this.baseLine1RANPSAccessibility = baseLine1RANPSAccessibility;
        }

        public double getBaseLine2RANPSAccessibility() {
            return baseLine2RANPSAccessibility;
        }

        public void setBaseLine2RANPSAccessibility(double baseLine2RANPSAccessibility) {
            this.baseLine2RANPSAccessibility = baseLine2RANPSAccessibility;
        }

        public double getRANMinutesBetweenPSAbnormalReleases() {
            return RANMinutesBetweenPSAbnormalReleases;
        }

        public void setRANMinutesBetweenPSAbnormalReleases(double RANMinutesBetweenPSAbnormalReleases) {
            this.RANMinutesBetweenPSAbnormalReleases = RANMinutesBetweenPSAbnormalReleases;
        }

        public double getBaseLine1RANMinutesBetweenPSAbnormalReleases() {
            return baseLine1RANMinutesBetweenPSAbnormalReleases;
        }

        public void setBaseLine1RANMinutesBetweenPSAbnormalReleases(double baseLine1RANMinutesBetweenPSAbnormalReleases) {
            this.baseLine1RANMinutesBetweenPSAbnormalReleases = baseLine1RANMinutesBetweenPSAbnormalReleases;
        }

        public double getBaseLine2RANMinutesBetweenPSAbnormalReleases() {
            return baseLine2RANMinutesBetweenPSAbnormalReleases;
        }

        public void setBaseLine2RANMinutesBetweenPSAbnormalReleases(double baseLine2RANMinutesBetweenPSAbnormalReleases) {
            this.baseLine2RANMinutesBetweenPSAbnormalReleases = baseLine2RANMinutesBetweenPSAbnormalReleases;
        }
        
    }
    
    public static class KPI4G{
        private double establishmentSuccessRate;
        private double minutesBetweenERABAbnormalReleases;
        private double baseLine1EstablishmentSuccessRate = 98.5;
        private double baseLine2EstablishmentSuccessRate = 99;
        private double baseLine1MinutesBetweenERABAbnormalReleases = 130;
        private double baseLine2MinutesBetweenERABAbnormalReleases = 150;
        private double csTraffic;
        private double psTraffic;
        

        public KPI4G() {
            init();
        }

        
        private void init(){
            this.baseLine1EstablishmentSuccessRate = KPIBaseLineConstant.BASELINE4G_1_ESTABLISHMENT_SUCCESS_RATE;
            this.baseLine2EstablishmentSuccessRate = KPIBaseLineConstant.BASELINE4G_2_ESTABLISHMENT_SUCCESS_RATE;
            this.baseLine1MinutesBetweenERABAbnormalReleases = KPIBaseLineConstant.BASELINE4G_1_MINUTES_BETWEEN_ERAB_ABNORMAL_RELEASES;
            this.baseLine2MinutesBetweenERABAbnormalReleases = KPIBaseLineConstant.BASELINE4G_2_MINUTES_BETWEEN_ERAB_ABNORMAL_RELEASES;
        }
        
        public KPI4G(double establishmentSuccessRate, double minutesBetweenERABAbnormalReleases) {
            this.establishmentSuccessRate = establishmentSuccessRate;
            this.minutesBetweenERABAbnormalReleases = minutesBetweenERABAbnormalReleases;
            init();
        } 

        public double getEstablishmentSuccessRate() {
            return establishmentSuccessRate;
        }

        public void setEstablishmentSuccessRate(double establishmentSuccessRate) {
            this.establishmentSuccessRate = establishmentSuccessRate;
        }

        public double getMinutesBetweenERABAbnormalReleases() {
            return minutesBetweenERABAbnormalReleases;
        }

        public void setMinutesBetweenERABAbnormalReleases(double minutesBetweenERABAbnormalReleases) {
            this.minutesBetweenERABAbnormalReleases = minutesBetweenERABAbnormalReleases;
        }

        public double getBaseLine1EstablishmentSuccessRate() {
            return baseLine1EstablishmentSuccessRate;
        }

        public void setBaseLine1EstablishmentSuccessRate(double baseLine1EstablishmentSuccessRate) {
            this.baseLine1EstablishmentSuccessRate = baseLine1EstablishmentSuccessRate;
        }

        public double getBaseLine1MinutesBetweenERABAbnormalReleases() {
            return baseLine1MinutesBetweenERABAbnormalReleases;
        }

        public void setBaseLine1MinutesBetweenERABAbnormalReleases(double baseLine1MinutesBetweenERABAbnormalReleases) {
            this.baseLine1MinutesBetweenERABAbnormalReleases = baseLine1MinutesBetweenERABAbnormalReleases;
        }

        public double getBaseLine2EstablishmentSuccessRate() {
            return baseLine2EstablishmentSuccessRate;
        }

        public void setBaseLine2EstablishmentSuccessRate(double baseLine2EstablishmentSuccessRate) {
            this.baseLine2EstablishmentSuccessRate = baseLine2EstablishmentSuccessRate;
        }

        public double getBaseLine2MinutesBetweenERABAbnormalReleases() {
            return baseLine2MinutesBetweenERABAbnormalReleases;
        }

        public void setBaseLine2MinutesBetweenERABAbnormalReleases(double baseLine2MinutesBetweenERABAbnormalReleases) {
            this.baseLine2MinutesBetweenERABAbnormalReleases = baseLine2MinutesBetweenERABAbnormalReleases;
        }

        public double getCsTraffic() {
            return csTraffic;
        }

        public void setCsTraffic(double csTraffic) {
            this.csTraffic = csTraffic;
        }

        public double getPsTraffic() {
            return psTraffic;
        }

        public void setPsTraffic(double psTraffic) {
            this.psTraffic = psTraffic;
        }

        
        
        
    }
    
    
    
}
