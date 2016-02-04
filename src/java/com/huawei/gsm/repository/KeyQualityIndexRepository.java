/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.repository;

import com.huawei.gsm.entity.AccessType;
import com.huawei.gsm.entity.Cell;
import com.huawei.gsm.entity.KQI;
import com.huawei.gsm.entity.KQIBaselineConstant;
import com.huawei.gsm.entity.KQIType;
import com.huawei.gsm.entity.WorstCell;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.huawei.gsm.repository.rowmapper.PageBrowsingKQIRowMapper;
import com.huawei.gsm.repository.rowmapper.VideoStreamingKQIRowMapper;
import com.huawei.gsm.repository.rowmapper.WorstCellRowMapper;
import com.huawei.gsm.util.response.gis.ChartValue;
import com.huawei.gsm.util.response.gis.LineChart;
import com.huawei.gsm.util.response.gis.LineChartSerie;
import java.sql.ResultSet;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 */

@Repository
public class KeyQualityIndexRepository implements KeyQualityIndexRepositoryContract{
    
    private @Autowired JdbcTemplate jdbcTemplate;
    
    private final static String KQI_GROUP_PAGE_BROWSING_SUCCESS_RATE_AND_DELAY = "SELECT\n" +
                                                                                "\n" +
                                                                                "SUM(PAGE_SUCCEED_TIMES) AS PAGE_SUCC_CNT,\n" +
                                                                                "SUM(FST_PAGE_REQ_NUM) AS PAGE_REQ_CNT,\n" +
                                                                                "\n" +
                                                                                "CASE WHEN SUM(FST_PAGE_REQ_NUM)=0 THEN 99999 ELSE CAST(CAST(SUM(PAGE_SUCCEED_TIMES) AS DECIMAL(20,2))/ SUM(FST_PAGE_REQ_NUM)*100 AS DECIMAL(20,2)) END AS PAGE_BROWSING_SUCC_RATE,\n" +
                                                                                "\n" +
                                                                                "SUM(PAGE_DELAY_MSEL) AS PAGE_DELAY_SUM_MS,\n" +
                                                                                "--SUM(PAGE_SUCCEED_TIMES) AS PAGE_SUCC_CNT,\n" +
                                                                                "CASE WHEN SUM(PAGE_SUCCEED_TIMES)=0 THEN -99999 ELSE CAST(CAST(SUM(PAGE_DELAY_MSEL) AS DECIMAL(20,2))/ SUM(PAGE_SUCCEED_TIMES) AS DECIMAL(20,0)) END AS PAGE_BROWSING_DELAY_MS\n" +
                                                                                "\n" +
                                                                                "\n" +
                                                                                "FROM `sdr_web_cus_area_cgisai_15min`\n" +
                                                                                "\n" +
                                                                                "\n" +
                                                                                "WHERE CUS_AREA_ID = 20 AND `STARTTIME` BETWEEN UNIX_TIMESTAMP(CURDATE()) AND UNIX_TIMESTAMP( DATE_ADD(CURDATE(),INTERVAL 1 DAY) )";
    
    private final static String KQI_GROUP_VIDEO_STREAMING_START_SUCCESS_RATE_AND_DELAY = "SELECT\n" +
                                                                                "\n" +
                                                                                "SUM(TCP_CONN_SUC) AS TCP_CONN_SUC_CNT,\n" +
                                                                                "SUM(TCP_CONN_REQ) AS TCP_CONN_REQ_CNT,\n" +
                                                                                "SUM(GET_SUC) AS GET_SUC_CNT,\n" +
                                                                                "SUM(GET_REQ) AS GET_REQ_CNT,\n" +
                                                                                "SUM(INTBUFFER_SUC) AS INTBUFFER_SUC_CNT,\n" +
                                                                                "SUM(INTBUFFER_REQ) AS INTBUFFER_REQ_CNT,\n" +
                                                                                "\n" +
                                                                                "CASE  WHEN SUM(TCP_CONN_REQ) = 0 THEN 99999 \n" +
                                                                                "WHEN SUM( GET_REQ) = 0 OR  SUM( INTBUFFER_REQ) = 0 THEN 0  \n" +
                                                                                "ELSE CAST((CAST(CAST(SUM(TCP_CONN_SUC) AS DECIMAL(20,2))/SUM(TCP_CONN_REQ) AS DECIMAL(20,4))) * (CAST(CAST(SUM(GET_SUC) AS DECIMAL(20,2))/SUM(GET_REQ) AS DECIMAL(20,4))) * (CAST(CAST(SUM(INTBUFFER_SUC) AS DECIMAL(20,2))/SUM(INTBUFFER_REQ) AS DECIMAL(20,4))) * 100 AS DECIMAL(20, 2)) END AS STREAMING_START_SUCC_RATE,\n" +
                                                                                " \n" +
                                                                                "\n" +
                                                                                "SUM(TCP_CONN_DELAY) AS TCP_CONN_DELAY_SUM_MS,\n" +
                                                                                "SUM(INTBUFFER_SUC) AS INTBUFFER_SUC_CNT_2,\n" +
                                                                                "SUM(GET_SUC_DELAY) AS GET_SUC_DELAY_SUM_MS,\n" +
                                                                                "SUM(INTBUFFER_SUC) AS INTBUFFER_SUC_CNT_3,\n" +
                                                                                "SUM(INTBUFFER_SUC_DELAY) AS INTBUFFER_SUC_DELAY_SUM_MS,\n" +
                                                                                "SUM(INTBUFFER_SUC) AS INTBUFFER_SUC_CNT_4,\n" +
                                                                                "\n" +
                                                                                "\n" +
                                                                                "CASE WHEN SUM(INTBUFFER_SUC) =0 THEN -99999  \n" +
                                                                                "ELSE CAST(CAST(CAST(SUM(TCP_CONN_DELAY) AS DECIMAL(20,2))/SUM(INTBUFFER_SUC) AS DECIMAL(20,0)) + CAST(CAST(SUM(GET_SUC_DELAY) AS DECIMAL(20,2))/SUM(INTBUFFER_SUC) AS DECIMAL(20,0)) + CAST(CAST(SUM(INTBUFFER_SUC_DELAY) AS DECIMAL(20,2))/SUM(INTBUFFER_SUC) AS DECIMAL(20,0)) AS DECIMAL(20,0)) END AS STREAMING_START_DEALY_MS\n" +
                                                                                "\n" +
                                                                                "\n" +
                                                                                "FROM `sdr_stream_cus_area_cgisai_15min`\n" +
                                                                                "\n" +
                                                                                "\n" +
                                                                                "WHERE CUS_AREA_ID = 20 AND `STARTTIME` BETWEEN UNIX_TIMESTAMP(CURDATE()) AND UNIX_TIMESTAMP( DATE_ADD(CURDATE(),INTERVAL 1 DAY) )";
    
    
    private final static String KQI_CELL_PAGE_BROWSING_SUCCESS_RATE_AND_DELAY = "SELECT \n" +
                                                                    "\n" +
                                                                    "SDR.CGISAI,\n" +
                                                                    "SUM(PAGE_SUCCEED_TIMES) AS PAGE_SUCC_CNT,\n" +
                                                                    "SUM(FST_PAGE_REQ_NUM) AS PAGE_REQ_CNT,\n" +
                                                                    "CASE WHEN SUM(FST_PAGE_REQ_NUM)=0 THEN 99999 ELSE CAST(CAST(SUM(PAGE_SUCCEED_TIMES) AS DECIMAL(20,2))/ SUM(FST_PAGE_REQ_NUM)*100 AS DECIMAL(20,2)) END AS PAGE_BROWSING_SUCC_RATE,\n" +
                                                                    "SUM(PAGE_DELAY_MSEL) AS PAGE_DELAY_SUM_MS,\n" +
                                                                    "--SUM(PAGE_SUCCEED_TIMES) AS PAGE_SUCC_CNT,\n" +
                                                                    "CASE WHEN SUM(PAGE_SUCCEED_TIMES)=0 THEN -99999 ELSE CAST(CAST(SUM(PAGE_DELAY_MSEL) AS DECIMAL(20,2))/ SUM(PAGE_SUCCEED_TIMES) AS DECIMAL(20,0)) END AS PAGE_BROWSING_DELAY_MS\n" +
                                                                    "\n" +
                                                                    "\n" +
                                                                    "FROM sdr_web_cgisai_15min SDR\n" +
                                                                    "\n" +
                                                                    "INNER JOIN\n" +
                                                                    "\n" +
                                                                    "    (\n" +
                                                                    "        SELECT\n" +
                                                                    "        ID,              \n" +
                                                                    "        CGISAI,\n" +
                                                                    "        CELL_NAME,\n" +
                                                                    "        CUS_AREA_ID,\n" +
                                                                    "        CUS_AREA_NAME,\n" +
                                                                    "        ACCESS_TYPE_ID,\n" +
                                                                    "        ACCESS_TYPE\n" +
                                                                    "        FROM DIM_LOC_CUSTOMAREA	\n" +
                                                                    "        WHERE CUS_AREA_ID = 20 AND CELL_NAME = ? \n" +
                                                                    "\n" +
                                                                    "    )DIM_CFG\n" +
                                                                    "ON SDR.CGISAI = DIM_CFG.CGISAI WHERE `STARTTIME` BETWEEN UNIX_TIMESTAMP(CURDATE()) AND UNIX_TIMESTAMP( DATE_ADD(CURDATE(),INTERVAL 1 DAY) )\n" +
                                                                    "\n" +
                                                                    "GROUP BY SDR.CGISAI";
    
    private final static String KQI_CELL_VIDEO_STREAMING_START_SUCCESS_RATE_AND_DELAY = "SELECT\n" +
                                                                        "\n" +
                                                                        "SDR.CGISAI,\n" +
                                                                        "CELL_NAME,\n" +
                                                                        "SUM(TCP_CONN_SUC) AS TCP_CONN_SUC_CNT,\n" +
                                                                        "SUM(TCP_CONN_REQ) AS TCP_CONN_REQ_CNT,\n" +
                                                                        "SUM(GET_SUC) AS GET_SUC_CNT,\n" +
                                                                        "SUM(GET_REQ) AS GET_REQ_CNT,\n" +
                                                                        "SUM(INTBUFFER_SUC) AS INTBUFFER_SUC_CNT,\n" +
                                                                        "SUM(INTBUFFER_REQ) AS INTBUFFER_REQ_CNT,\n" +
                                                                        "\n" +
                                                                        "CASE  WHEN SUM(TCP_CONN_REQ) = 0 THEN 99999 \n" +
                                                                        "WHEN SUM( GET_REQ) = 0 OR  SUM( INTBUFFER_REQ) = 0 THEN 0  \n" +
                                                                        "ELSE CAST((CAST(CAST(SUM(TCP_CONN_SUC) AS DECIMAL(20,2))/SUM(TCP_CONN_REQ) AS DECIMAL(20,4))) * (CAST(CAST(SUM(GET_SUC) AS DECIMAL(20,2))/SUM(GET_REQ) AS DECIMAL(20,4))) * (CAST(CAST(SUM(INTBUFFER_SUC) AS DECIMAL(20,2))/SUM(INTBUFFER_REQ) AS DECIMAL(20,4))) * 100 AS DECIMAL(20, 2)) END AS STREAMING_START_SUCC_RATE,\n" +
                                                                        " \n" +
                                                                        "\n" +
                                                                        "SUM(TCP_CONN_DELAY) AS TCP_CONN_DELAY_SUM_MS,\n" +
                                                                        "SUM(INTBUFFER_SUC) AS INTBUFFER_SUC_CNT_2,\n" +
                                                                        "SUM(GET_SUC_DELAY) AS GET_SUC_DELAY_SUM_MS,\n" +
                                                                        "SUM(INTBUFFER_SUC) AS INTBUFFER_SUC_CNT_3,\n" +
                                                                        "SUM(INTBUFFER_SUC_DELAY) AS INTBUFFER_SUC_DELAY_SUM_MS,\n" +
                                                                        "SUM(INTBUFFER_SUC) AS INTBUFFER_SUC_CNT_4,\n" +
                                                                        "\n" +
                                                                        "\n" +
                                                                        "CASE WHEN SUM(INTBUFFER_SUC) =0 THEN -99999  \n" +
                                                                        "ELSE CAST(CAST(CAST(SUM(TCP_CONN_DELAY) AS DECIMAL(20,2))/SUM(INTBUFFER_SUC) AS DECIMAL(20,0)) + CAST(CAST(SUM(GET_SUC_DELAY) AS DECIMAL(20,2))/SUM(INTBUFFER_SUC) AS DECIMAL(20,0)) + CAST(CAST(SUM(INTBUFFER_SUC_DELAY) AS DECIMAL(20,2))/SUM(INTBUFFER_SUC) AS DECIMAL(20,0)) AS DECIMAL(20,0)) END AS STREAMING_START_DEALY_MS\n" +
                                                                        "\n" +
                                                                        "\n" +
                                                                        "FROM sdr_stream_cgisai_15min SDR\n" +
                                                                        "\n" +
                                                                        "INNER JOIN\n" +
                                                                        "\n" +
                                                                        "    (\n" +
                                                                        "        SELECT\n" +
                                                                        "        ID,              \n" +
                                                                        "        CGISAI,\n" +
                                                                        "        CELL_NAME,\n" +
                                                                        "        CUS_AREA_ID,\n" +
                                                                        "        CUS_AREA_NAME,\n" +
                                                                        "        ACCESS_TYPE_ID,\n" +
                                                                        "        ACCESS_TYPE\n" +
                                                                        "        FROM DIM_LOC_CUSTOMAREA	\n" +
                                                                        "        WHERE CUS_AREA_ID = 20 AND CELL_NAME = ? \n" +
                                                                        "\n" +
                                                                        "    )DIM_CFG\n" +
                                                                        "ON SDR.CGISAI = DIM_CFG.CGISAI WHERE `STARTTIME` BETWEEN UNIX_TIMESTAMP(CURDATE()) AND UNIX_TIMESTAMP( DATE_ADD(CURDATE(),INTERVAL 1 DAY) ) \n" +
                                                                        "\n" +
                                                                        "GROUP BY SDR.CGISAI";

    private final static String TOP5_WORST_CELL_PAGE_BROWSING_DELAY = "SELECT \n" +
                                                                                "\n" +
                                                                                "SDR.CGISAI,\n" +
                                                                                "CELL_NAME,\n" +
                                                                                "SORT,\n" +
                                                                                "CASE WHEN SUM(PAGE_SUCCEED_TIMES)=0 THEN -99999 ELSE CAST(CAST(SUM(PAGE_DELAY_MSEL) AS DECIMAL(20,2))/ SUM(PAGE_SUCCEED_TIMES) AS DECIMAL(20,0)) END AS VALUE\n" +
                                                                                "\n" +
                                                                                "FROM `sdr_web_cgisai_15min` SDR\n" +
                                                                                "\n" +
                                                                                "INNER JOIN\n" +
                                                                                "(\n" +
                                                                                "	SELECT WORST_CELL.CGISAI,CELL_NAME,SORT FROM (\n" +
                                                                                "	SELECT CGISAI, SUM(PAGE_DELAY_MSEL) / SUM(PAGE_SUCCEED_TIMES) AS SORT  \n" +
                                                                                "	FROM `sdr_web_cgisai_15min` \n" +
                                                                                "	GROUP BY CGISAI \n" +
                                                                                "	ORDER BY sort DESC \n" +
                                                                                "	) WORST_CELL \n" +
                                                                                "	INNER JOIN      \n" +
                                                                                "	( \n" +
                                                                                "	    SELECT \n" +
                                                                                "	    ID,               \n" +
                                                                                "	    CGISAI, \n" +
                                                                                "	    CELL_NAME \n" +
                                                                                "	    FROM DIM_LOC_CUSTOMAREA	 \n" +
                                                                                "	    WHERE CUS_AREA_ID = 20 AND access_type = ?\n" +
                                                                                "\n" +
                                                                                "	)DIM_CFG  \n" +
                                                                                "	ON DIM_CFG.CGISAI = WORST_CELL.CGISAI \n" +
                                                                                "	LEFT JOIN ( SELECT cellid,cellindex,cellname,frequency,siteid FROM `cells`) AS cells \n" +
                                                                                "	ON cells.`cellname` = DIM_CFG.cell_name \n" +
                                                                                "	LIMIT 0,5\n" +
                                                                                ")DIM_CFG\n" +
                                                                                "ON \n" +
                                                                                "SDR.CGISAI = DIM_CFG.CGISAI WHERE `STARTTIME` BETWEEN UNIX_TIMESTAMP(CURDATE()) AND UNIX_TIMESTAMP( DATE_ADD(CURDATE(),INTERVAL 1 DAY) )\n" +
                                                                                "GROUP BY SDR.CGISAI\n" +
                                                                                "ORDER BY SORT DESC";
    
    private final static String TOP5_WORST_CELL_PAGE_BROWSING_SUCCESS_RATE = "SELECT \n" +
                                                                                "\n" +
                                                                                "SDR.CGISAI,\n" +
                                                                                "CELL_NAME,\n" +
                                                                                "SORT,\n" +
                                                                                "SUM(PAGE_SUCCEED_TIMES) AS PAGE_SUCC_CNT,\n" +
                                                                                "SUM(FST_PAGE_REQ_NUM) AS PAGE_REQ_CNT,\n" +
                                                                                "CASE WHEN SUM(FST_PAGE_REQ_NUM)=0 THEN 99999 ELSE CAST(CAST(SUM(PAGE_SUCCEED_TIMES) AS DECIMAL(20,2))/ SUM(FST_PAGE_REQ_NUM)*100 AS DECIMAL(20,2)) END AS VALUE\n" +
                                                                                "\n" +
                                                                                "FROM `sdr_web_cgisai_15min` SDR\n" +
                                                                                "\n" +
                                                                                "INNER JOIN\n" +
                                                                                "(\n" +
                                                                                "	SELECT WORST_CELL.CGISAI,CELL_NAME,SORT FROM \n" +
                                                                                "	(\n" +
                                                                                "	SELECT CGISAI, (SUM( `FST_PAGE_REQ_NUM` ) - SUM( `PAGE_SUCCEED_TIMES` )) AS SORT \n" +
                                                                                "	FROM `sdr_web_cgisai_15min`\n" +
                                                                                "	GROUP BY CGISAI\n" +
                                                                                "	ORDER BY sort DESC\n" +
                                                                                "	) WORST_CELL\n" +
                                                                                "	INNER JOIN     \n" +
                                                                                "	(\n" +
                                                                                "	SELECT\n" +
                                                                                "	ID,              \n" +
                                                                                "	CGISAI,\n" +
                                                                                "	CELL_NAME\n" +
                                                                                "	FROM DIM_LOC_CUSTOMAREA	\n" +
                                                                                "	WHERE CUS_AREA_ID = 20 AND access_type = ? \n" +
                                                                                "\n" +
                                                                                "	)DIM_CFG \n" +
                                                                                "	ON DIM_CFG.CGISAI = WORST_CELL.CGISAI\n" +
                                                                                "	LEFT JOIN ( SELECT cellid,cellindex,cellname,frequency,siteid FROM `cells`) AS cells\n" +
                                                                                "	ON cells.`cellname` = DIM_CFG.cell_name\n" +
                                                                                "	WHERE sort > 0\n" +
                                                                                "	LIMIT 0,5\n" +
                                                                                ")DIM_CFG\n" +
                                                                                "ON \n" +
                                                                                "SDR.CGISAI = DIM_CFG.CGISAI WHERE `STARTTIME` BETWEEN UNIX_TIMESTAMP(CURDATE()) AND UNIX_TIMESTAMP( DATE_ADD(CURDATE(),INTERVAL 1 DAY) ) \n" +
                                                                                "GROUP BY SDR.CGISAI\n" +
                                                                                "ORDER BY SORT DESC";
    
    private final static String TOP5_WORST_CELL_VIDEO_STREAMING_START_SUCCESS_RATE = "SELECT\n" +
                                                                                "CELL_NAME,\n" +
                                                                                "SORT,\n" +
                                                                                "CASE  WHEN SUM(TCP_CONN_REQ) = 0 THEN 99999 \n" +
                                                                                "WHEN SUM( GET_REQ) = 0 OR  SUM( INTBUFFER_REQ) = 0 THEN 0  \n" +
                                                                                "ELSE CAST((CAST(CAST(SUM(TCP_CONN_SUC) AS DECIMAL(20,2))/SUM(TCP_CONN_REQ) AS DECIMAL(20,4))) * (CAST(CAST(SUM(GET_SUC) AS DECIMAL(20,2))/SUM(GET_REQ) AS DECIMAL(20,4))) * (CAST(CAST(SUM(INTBUFFER_SUC) AS DECIMAL(20,2))/SUM(INTBUFFER_REQ) AS DECIMAL(20,4))) * 100 AS DECIMAL(20, 2)) END AS VALUE\n" +
                                                                                "\n" +
                                                                                "\n" +
                                                                                "FROM `sdr_stream_cgisai_15min` SDR\n" +
                                                                                "\n" +
                                                                                "INNER JOIN\n" +
                                                                                "(\n" +
                                                                                "	SELECT WORST_CELL.CGISAI,SORT,CELL_NAME FROM \n" +
                                                                                "	(\n" +
                                                                                "	SELECT CGISAI, SUM(INTBUFFER_REQ) -SUM(INTBUFFER_SUC)  AS SORT \n" +
                                                                                "	FROM `sdr_stream_cgisai_15min` \n" +
                                                                                "	GROUP BY CGISAI\n" +
                                                                                "	HAVING SUM(INTBUFFER_REQ) -SUM(INTBUFFER_SUC) > 0\n" +
                                                                                "	) WORST_CELL\n" +
                                                                                "	INNER JOIN     \n" +
                                                                                "	(\n" +
                                                                                "	    SELECT\n" +
                                                                                "	    ID,              \n" +
                                                                                "	    CGISAI,\n" +
                                                                                "	    CELL_NAME\n" +
                                                                                "	    FROM DIM_LOC_CUSTOMAREA	\n" +
                                                                                "	    WHERE CUS_AREA_ID = 20 AND access_type = ? \n" +
                                                                                "\n" +
                                                                                "	)DIM_CFG \n" +
                                                                                "	ON DIM_CFG.CGISAI = WORST_CELL.CGISAI\n" +
                                                                                "	LEFT JOIN ( SELECT cellid,cellindex,cellname,frequency,siteid FROM `cells`) AS cells\n" +
                                                                                "	ON cells.`cellname` = DIM_CFG.cell_name\n" +
                                                                                "	ORDER BY sort DESC\n" +
                                                                                "	LIMIT 0,5\n" +
                                                                                "                                                                                    \n" +
                                                                                "                                                                                    \n" +
                                                                                ")DIM_CFG\n" +
                                                                                "ON SDR.CGISAI = DIM_CFG.CGISAI WHERE `STARTTIME` BETWEEN UNIX_TIMESTAMP(CURDATE()) AND UNIX_TIMESTAMP( DATE_ADD(CURDATE(),INTERVAL 1 DAY) )\n" +
                                                                                "\n" +
                                                                                "GROUP BY SDR.CGISAI\n" +
                                                                                "ORDER BY SORT DESC";
    
    private final static String TOP5_WORST_CELL_VIDEO_STREAMING_START_DELAY = "SELECT\n" +
                                                                                "\n" +
                                                                                "SDR.CGISAI,\n" +
                                                                                "CELL_NAME,\n" +
                                                                                "SORT,\n" +
                                                                                "CASE WHEN SUM(INTBUFFER_SUC) =0 THEN -99999  \n" +
                                                                                "ELSE CAST(CAST(CAST(SUM(TCP_CONN_DELAY) AS DECIMAL(20,2))/SUM(INTBUFFER_SUC) AS DECIMAL(20,0)) + CAST(CAST(SUM(GET_SUC_DELAY) AS DECIMAL(20,2))/SUM(INTBUFFER_SUC) AS DECIMAL(20,0)) + CAST(CAST(SUM(INTBUFFER_SUC_DELAY) AS DECIMAL(20,2))/SUM(INTBUFFER_SUC) AS DECIMAL(20,0)) AS DECIMAL(20,0)) END AS VALUE\n" +
                                                                                "\n" +
                                                                                "\n" +
                                                                                "FROM `sdr_stream_cgisai_15min` SDR\n" +
                                                                                "\n" +
                                                                                "INNER JOIN\n" +
                                                                                "(\n" +
                                                                                "	SELECT WORST_CELL.CGISAI,CELL_NAME,SORT FROM \n" +
                                                                                "	(\n" +
                                                                                "	 SELECT CGISAI, SUM(INTBUFFER_SUC_DELAY)/SUM(INTBUFFER_SUC) AS SORT \n" +
                                                                                "	 FROM `sdr_stream_cgisai_15min`\n" +
                                                                                "	 GROUP BY CGISAI\n" +
                                                                                "	 ORDER BY sort DESC\n" +
                                                                                "	) WORST_CELL\n" +
                                                                                "	INNER JOIN     \n" +
                                                                                "	(\n" +
                                                                                "		SELECT\n" +
                                                                                "		ID,              \n" +
                                                                                "		CGISAI,\n" +
                                                                                "		CELL_NAME\n" +
                                                                                "		FROM DIM_LOC_CUSTOMAREA	\n" +
                                                                                "		WHERE CUS_AREA_ID = 20 AND access_type = ?\n" +
                                                                                "	)DIM_CFG \n" +
                                                                                "	ON DIM_CFG.CGISAI = WORST_CELL.CGISAI\n" +
                                                                                "	LEFT JOIN ( SELECT cellid,cellindex,cellname,frequency,siteid FROM `cells`) AS cells\n" +
                                                                                "	ON cells.`cellname` = DIM_CFG.cell_name\n" +
                                                                                "	LIMIT 0,5\n" +
                                                                                "                                                                                    \n" +
                                                                                ")DIM_CFG\n" +
                                                                                "ON SDR.CGISAI = DIM_CFG.CGISAI WHERE `STARTTIME` BETWEEN UNIX_TIMESTAMP(CURDATE()) AND UNIX_TIMESTAMP( DATE_ADD(CURDATE(),INTERVAL 1 DAY) )\n" +
                                                                                "\n" +
                                                                                "GROUP BY SDR.CGISAI\n" +
                                                                                "ORDER BY SORT DESC";
    
        

    public List<WorstCell> getTop5PageBrowsingSuccessRate(AccessType accessType) {

        return jdbcTemplate.query(TOP5_WORST_CELL_PAGE_BROWSING_SUCCESS_RATE, 
                                    new Object[]{ stringifyAccessType( accessType ) }  , 
                                    new WorstCellRowMapper());
    }

    @Override
    public List<WorstCell> getTop5PageBrowsingDelay(AccessType accessType) {
        return jdbcTemplate.query(TOP5_WORST_CELL_PAGE_BROWSING_DELAY, 
                                    new Object[]{ stringifyAccessType( accessType ) }  , 
                                    new WorstCellRowMapper());
    }

    @Override
    public List<WorstCell> getTop5VideoStreamingStartSuccessRate(AccessType accessType) {
        return jdbcTemplate.query(TOP5_WORST_CELL_VIDEO_STREAMING_START_SUCCESS_RATE, 
                                    new Object[]{ stringifyAccessType( accessType ) }  , 
                                    new WorstCellRowMapper());
    }

    @Override
    public List<WorstCell> getTop5VideoStreamingStartDelay(AccessType accessType) {
        return jdbcTemplate.query(TOP5_WORST_CELL_VIDEO_STREAMING_START_DELAY, 
                                    new Object[]{ stringifyAccessType( accessType ) }  , 
                                    new WorstCellRowMapper());
    }

    @Override
    public KQI getGoldenSiteKQI() {
        KQI.PageBrowsingKQI pageBrowsingKQI = jdbcTemplate
                                                .queryForObject(
                                                            KQI_GROUP_PAGE_BROWSING_SUCCESS_RATE_AND_DELAY, 
                                                            new PageBrowsingKQIRowMapper()
                                                );
        
        KQI.VideoStreamingKQI videoStreaminKQI = jdbcTemplate.queryForObject(
                                                    KQI_GROUP_VIDEO_STREAMING_START_SUCCESS_RATE_AND_DELAY, 
                                                    new  VideoStreamingKQIRowMapper());
        
        return new KQI(videoStreaminKQI, pageBrowsingKQI);
    }

    @Override
    public KQI getCellKQI(Cell cell) {
        KQI.PageBrowsingKQI pageBrowsingKQI = jdbcTemplate
                                                .queryForObject(
                                                            KQI_CELL_PAGE_BROWSING_SUCCESS_RATE_AND_DELAY, 
                                                            new Object[]{ cell.getCellName() },
                                                            new PageBrowsingKQIRowMapper()
                                                );
        
        KQI.VideoStreamingKQI videoStreaminKQI = jdbcTemplate.queryForObject(
                                                    KQI_CELL_VIDEO_STREAMING_START_SUCCESS_RATE_AND_DELAY, 
                                                    new Object[]{ cell.getCellName() },
                                                    new  VideoStreamingKQIRowMapper());
        
        return new KQI(videoStreaminKQI, pageBrowsingKQI);
    }
    
    private String stringifyAccessType(AccessType accessType){
        String accessTypeString = "3G";
        if( accessType == AccessType.AccessType_2G ){
            accessTypeString = "2G";
        }else if( accessType == AccessType.AccessType_3G ){
            accessTypeString = "3G";
        }else if( accessType == AccessType.AccessType_4G ){
            accessTypeString = "4G";
        }
        else{
            accessTypeString = "4G";
        }
        
        return accessTypeString;
    }

    @Override
    public Map<KQIType, LineChart> getSectorKQI(String cells) {
        
        String sqlVideo = "SELECT\n" +
                        "(SDR.`STARTTIME`*1000) AS STARTTIME,\n" +
                        "SDR.CGISAI,CELL_NAME,\n" +
                        "SUM(TCP_CONN_SUC) AS TCP_CONN_SUC_CNT,\n" +
                        "SUM(TCP_CONN_REQ) AS TCP_CONN_REQ_CNT,\n" +
                        "SUM(GET_SUC) AS GET_SUC_CNT,\n" +
                        "SUM(GET_REQ) AS GET_REQ_CNT,\n" +
                        "SUM(INTBUFFER_SUC) AS INTBUFFER_SUC_CNT,\n" +
                        "SUM(INTBUFFER_REQ) AS INTBUFFER_REQ_CNT,\n" +
                        "\n" +
                        "CASE  WHEN SUM(TCP_CONN_REQ) = 0 THEN 99999 \n" +
                        "WHEN SUM( GET_REQ) = 0 OR  SUM( INTBUFFER_REQ) = 0 THEN 0  \n" +
                        "ELSE CAST((CAST(CAST(SUM(TCP_CONN_SUC) AS DECIMAL(20,2))/SUM(TCP_CONN_REQ) AS DECIMAL(20,4))) * (CAST(CAST(SUM(GET_SUC) AS DECIMAL(20,2))/SUM(GET_REQ) AS DECIMAL(20,4))) * (CAST(CAST(SUM(INTBUFFER_SUC) AS DECIMAL(20,2))/SUM(INTBUFFER_REQ) AS DECIMAL(20,4))) * 100 AS DECIMAL(20, 2)) END AS STREAMING_START_SUCC_RATE,\n" +
                        " \n" +
                        "\n" +
                        "SUM(TCP_CONN_DELAY) AS TCP_CONN_DELAY_SUM_MS,\n" +
                        "SUM(INTBUFFER_SUC) AS INTBUFFER_SUC_CNT_2,\n" +
                        "SUM(GET_SUC_DELAY) AS GET_SUC_DELAY_SUM_MS,\n" +
                        "SUM(INTBUFFER_SUC) AS INTBUFFER_SUC_CNT_3,\n" +
                        "SUM(INTBUFFER_SUC_DELAY) AS INTBUFFER_SUC_DELAY_SUM_MS,\n" +
                        "SUM(INTBUFFER_SUC) AS INTBUFFER_SUC_CNT_4,\n" +
                        "\n" +
                        "\n" +
                        "CASE WHEN SUM(INTBUFFER_SUC) =0 THEN -99999  \n" +
                        "ELSE CAST(CAST(CAST(SUM(TCP_CONN_DELAY) AS DECIMAL(20,2))/SUM(INTBUFFER_SUC) AS DECIMAL(20,0)) + CAST(CAST(SUM(GET_SUC_DELAY) AS DECIMAL(20,2))/SUM(INTBUFFER_SUC) AS DECIMAL(20,0)) + CAST(CAST(SUM(INTBUFFER_SUC_DELAY) AS DECIMAL(20,2))/SUM(INTBUFFER_SUC) AS DECIMAL(20,0)) AS DECIMAL(20,0)) END AS STREAMING_START_DEALY_MS\n" +
                        "\n" +
                        "\n" +
                        "FROM `sdr_stream_cgisai_15min` SDR\n" +
                        "\n" +
                        "INNER JOIN\n" +
                        "\n" +
                        "    (\n" +
                        "        SELECT\n" +
                        "        ID,              \n" +
                        "        CGISAI,\n" +
                        "        CELL_NAME,\n" +
                        "        CUS_AREA_ID,\n" +
                        "        CUS_AREA_NAME,\n" +
                        "        ACCESS_TYPE_ID,\n" +
                        "        ACCESS_TYPE\n" +
                        "        FROM DIM_LOC_CUSTOMAREA	\n" +
                        "        WHERE CUS_AREA_ID = 20 AND cell_name IN("+cells+")\n" +
                        "    )DIM_CFG\n" +
                        "ON SDR.CGISAI = DIM_CFG.CGISAI WHERE DATE(FROM_UNIXTIME( SDR.`STARTTIME` )) = DATE(NOW()) \n" +
                        "GROUP BY SDR.CGISAI, SDR.STARTTIME ORDER BY SDR.STARTTIME ASC";
        
        String sqlBrowsing = "SELECT \n" +
                                "(SDR.`STARTTIME`*1000) AS `STARTTIME`,\n" +
                                "SDR.CGISAI,\n" +
                                "DIM_CFG.CELL_NAME,\n" +
                                "SUM(PAGE_SUCCEED_TIMES) AS PAGE_SUCC_CNT,\n" +
                                "SUM(FST_PAGE_REQ_NUM) AS PAGE_REQ_CNT,\n" +
                                "CASE WHEN SUM(FST_PAGE_REQ_NUM)=0 THEN 99999 ELSE CAST(CAST(SUM(PAGE_SUCCEED_TIMES) AS DECIMAL(20,2))/ SUM(FST_PAGE_REQ_NUM)*100 AS DECIMAL(20,2)) END AS PAGE_BROWSING_SUCC_RATE,\n" +
                                "SUM(PAGE_DELAY_MSEL) AS PAGE_DELAY_SUM_MS,\n" +
                                "CASE WHEN SUM(PAGE_SUCCEED_TIMES)=0 THEN -99999 ELSE CAST(CAST(SUM(PAGE_DELAY_MSEL) AS DECIMAL(20,2))/ SUM(PAGE_SUCCEED_TIMES) AS DECIMAL(20,0)) END AS PAGE_BROWSING_DELAY_MS\n" +
                                "\n" +
                                "\n" +
                                "FROM `sdr_web_cgisai_15min` SDR\n" +
                                "\n" +
                                "INNER JOIN\n" +
                                "\n" +
                                "    (\n" +
                                "        SELECT\n" +
                                "        ID,              \n" +
                                "        CGISAI,\n" +
                                "        CELL_NAME,\n" +
                                "        CUS_AREA_ID,\n" +
                                "        CUS_AREA_NAME,\n" +
                                "        ACCESS_TYPE_ID,\n" +
                                "        ACCESS_TYPE\n" +
                                "        FROM DIM_LOC_CUSTOMAREA	\n" +
                                "        WHERE CUS_AREA_ID = 20 AND CELL_NAME IN("+cells+")\n" +
                                "\n" +
                                "    )DIM_CFG\n" +
                                "ON SDR.CGISAI = DIM_CFG.CGISAI WHERE DATE(FROM_UNIXTIME( SDR.`STARTTIME` )) = DATE(NOW()) \n" + 
                                "\n" +
                                "GROUP BY `STARTTIME`,SDR.CGISAI\n" +
                                "ORDER BY `STARTTIME` ASC";
        
        Map<KQIType,LineChart> kqiCharts = new HashMap<>();
        
        LineChart videoStreamingStartSuccessRateLineChart = new LineChart();
        videoStreamingStartSuccessRateLineChart.setSeries( new ArrayList<>() );
        
        LineChart videoStreamingStartDelayLineChart = new LineChart();
        videoStreamingStartDelayLineChart.setSeries(new ArrayList<>());
        
        LineChart pageBrowsingSuccessRate = new LineChart();
        pageBrowsingSuccessRate.setSeries(new  ArrayList<>());
        
        LineChart pageBrowsingDelay = new LineChart();
        pageBrowsingDelay.setSeries(new ArrayList<>());
        
        kqiCharts.put(KQIType.VIDEO_STREAMING_START_SUCCESS_RATE, videoStreamingStartSuccessRateLineChart);
        kqiCharts.put(KQIType.PAGE_BROWSING_DELAY, pageBrowsingDelay);
        kqiCharts.put(KQIType.PAGE_BROWSING_SUCCESS_RATE, pageBrowsingSuccessRate);
        kqiCharts.put(KQIType.VIDEO_STREAMING_START_DELAY, videoStreamingStartDelayLineChart);
        
        Map<String,LineChartSerie> videoStreamingStartDelaySerie = new HashMap<>();
        Map<String,LineChartSerie> videoStreamingStartSuccessSerie = new HashMap<>();
        Map<String,LineChartSerie> pageBrowsingDelaySerie = new HashMap<>();
        Map<String,LineChartSerie> pageBrowsingSuccessRateSerie = new HashMap<>();

        //process video streaming first
        jdbcTemplate.query(sqlVideo , (ResultSet rs) -> {
            String cellName = rs.getString("CELL_NAME");
            if( videoStreamingStartDelaySerie.get( cellName ) == null ){
                LineChartSerie serie = new LineChartSerie();
                serie.setName( cellName );
                List<ChartValue> chartVals = new ArrayList<>();
                ChartValue val = new ChartValue();
                val.setTimestamp( rs.getLong("STARTTIME") );
                val.setValue( rs.getDouble("STREAMING_START_DEALY_MS") );
                chartVals.add(val);
                serie.setData(chartVals);
                videoStreamingStartDelaySerie.put( cellName, serie  );
            }else{
                ChartValue val = new ChartValue();
                val.setTimestamp( rs.getLong("STARTTIME") );
                val.setValue( rs.getDouble("STREAMING_START_DEALY_MS") );
                videoStreamingStartDelaySerie.get(cellName).getData().add(val);
            }
            
            if( videoStreamingStartSuccessSerie.get( cellName ) == null ){
                LineChartSerie serie = new LineChartSerie();
                serie.setName( cellName );
                List<ChartValue> chartVals = new ArrayList<>();
                ChartValue val = new ChartValue();
                val.setTimestamp( rs.getLong("STARTTIME") );
                val.setValue( rs.getDouble("STREAMING_START_SUCC_RATE") );
                chartVals.add(val);
                serie.setData(chartVals);
                videoStreamingStartSuccessSerie.put( cellName, serie  );
            }else{
                ChartValue val = new ChartValue();
                val.setTimestamp( rs.getLong("STARTTIME") );
                val.setValue( rs.getDouble("STREAMING_START_SUCC_RATE") );
                videoStreamingStartSuccessSerie.get(cellName).getData().add(val);
            }
        });

        //process page browsing then
        jdbcTemplate.query(sqlBrowsing , (ResultSet rs) -> {
            String cellName = rs.getString("CELL_NAME");
            if( pageBrowsingSuccessRateSerie.get( cellName ) == null ){
                LineChartSerie serie = new LineChartSerie();
                serie.setName( cellName );
                List<ChartValue> chartVals = new ArrayList<>();
                ChartValue val = new ChartValue();
                val.setTimestamp( rs.getLong("STARTTIME") );
                val.setValue( rs.getDouble("PAGE_BROWSING_SUCC_RATE") );
                chartVals.add(val);
                serie.setData(chartVals);
                pageBrowsingSuccessRateSerie.put( cellName, serie  );
            }else{
                ChartValue val = new ChartValue();
                val.setTimestamp( rs.getLong("STARTTIME") );
                val.setValue( rs.getDouble("PAGE_BROWSING_SUCC_RATE") );
                pageBrowsingSuccessRateSerie.get(cellName).getData().add(val);
            }
            
            if( pageBrowsingDelaySerie.get( cellName ) == null ){
                LineChartSerie serie = new LineChartSerie();
                serie.setName( cellName );
                List<ChartValue> chartVals = new ArrayList<>();
                ChartValue val = new ChartValue();
                val.setTimestamp( rs.getLong("STARTTIME") );
                val.setValue( rs.getDouble("PAGE_BROWSING_DELAY_MS") );
                chartVals.add(val);
                serie.setData(chartVals);
                pageBrowsingDelaySerie.put( cellName, serie  );
            }else{
                ChartValue val = new ChartValue();
                val.setTimestamp( rs.getLong("STARTTIME") );
                val.setValue( rs.getDouble("PAGE_BROWSING_DELAY_MS") );
                pageBrowsingDelaySerie.get(cellName).getData().add(val);
            }
        });    
        
        videoStreamingStartDelaySerie.entrySet().stream().map((entrySet) -> entrySet.getValue()).forEach((serie) -> {
            kqiCharts.get( KQIType.VIDEO_STREAMING_START_DELAY ).getSeries().add(serie);
        });
        
        videoStreamingStartSuccessSerie.entrySet().stream().map((entrySet) -> entrySet.getValue()).forEach((serie) -> {
            kqiCharts.get( KQIType.VIDEO_STREAMING_START_SUCCESS_RATE ).getSeries().add(serie);
        });
        pageBrowsingSuccessRateSerie.entrySet().stream().map((entrySet) -> entrySet.getValue()).forEach((serie) -> {
            kqiCharts.get( KQIType.PAGE_BROWSING_SUCCESS_RATE ).getSeries().add(serie);
        });        

        pageBrowsingDelaySerie.entrySet().stream().map((entrySet) -> entrySet.getValue()).forEach((serie) -> {
            kqiCharts.get( KQIType.PAGE_BROWSING_DELAY ).getSeries().add(serie);
        });  
        
        LineChartSerie baseLineVideoStreamingStartDelay = baselineSerieEngineering("Baseline ", KQIBaselineConstant.VIDEO_STREAMING_START_DELAY);
        LineChartSerie baseLineVideoStreamingSuccessRate = baselineSerieEngineering("Baseline", KQIBaselineConstant.VIDEO_STREAMING_SUCCESS_RATE);
        LineChartSerie baseLinePageBrowsingStartDelay = baselineSerieEngineering("Baseline", KQIBaselineConstant.PAGE_BROWSING_DELAY);
        LineChartSerie  baseLinePageBrowsingStartSuccessRate= baselineSerieEngineering("Baseline", KQIBaselineConstant.PAGE_BROWSING_SUCCESS_RATE);
        
        List<LineChartSerie> baselineListVideoStreamingStartDelay = new ArrayList<>();
        baselineListVideoStreamingStartDelay.add(baseLineVideoStreamingStartDelay);
        List<LineChartSerie> baselineListVideoStreamingStartSuccessRate = new ArrayList<>();
        baselineListVideoStreamingStartSuccessRate.add(baseLineVideoStreamingSuccessRate);
        List<LineChartSerie> baselineListPageBrowsingSuccessRate = new ArrayList<>();
        baselineListPageBrowsingSuccessRate.add(baseLinePageBrowsingStartSuccessRate);
        List<LineChartSerie> baselineListPageBrowsingDelay = new ArrayList<>();
        baselineListPageBrowsingDelay.add(baseLinePageBrowsingStartDelay);
        
        kqiCharts.get( KQIType.VIDEO_STREAMING_START_DELAY ).setBaseLines(baselineListVideoStreamingStartDelay);
        kqiCharts.get( KQIType.VIDEO_STREAMING_START_SUCCESS_RATE ).setBaseLines( baselineListVideoStreamingStartSuccessRate );
        kqiCharts.get( KQIType.PAGE_BROWSING_SUCCESS_RATE ).setBaseLines( baselineListPageBrowsingSuccessRate );
        kqiCharts.get( KQIType.PAGE_BROWSING_DELAY ).setBaseLines(baselineListPageBrowsingDelay);
        
        return kqiCharts;
    }

    @Override
    public LineChart getCellKQI(String cellName, KQIType type) {
        LineChart chart = new LineChart();
        LineChartSerie serie = new LineChartSerie();
        
        serie.setName(cellName);
        String sql = "";
        double baseLine = 0.00;
        if( type == KQIType.PAGE_BROWSING_DELAY ){
            sql = "SELECT \n" +
                "(`STARTTIME`*1000) AS starttime,\n" +
                "SDR.CGISAI,\n" +
                "CELL_NAME,\n" +
                "CASE WHEN SUM(PAGE_SUCCEED_TIMES)=0 THEN -99999 ELSE CAST(CAST(SUM(PAGE_DELAY_MSEL) AS DECIMAL(20,2))/ SUM(PAGE_SUCCEED_TIMES) AS DECIMAL(20,0)) END AS VALUE\n" +
                "\n" +
                "FROM `sdr_web_cgisai_15min` SDR\n" +
                "\n" +
                "INNER JOIN\n" +
                "(\n" +
                "        SELECT\n" +
                "        ID,              \n" +
                "        CGISAI,\n" +
                "        CELL_NAME,\n" +
                "        CUS_AREA_ID,\n" +
                "        CUS_AREA_NAME,\n" +
                "        ACCESS_TYPE_ID,\n" +
                "        ACCESS_TYPE\n" +
                "        FROM DIM_LOC_CUSTOMAREA	\n" +
                "        WHERE CUS_AREA_ID = 20 AND cell_name IN("+cellName+")\n" +
                ")DIM_CFG\n" +
                "ON \n" +
                "SDR.CGISAI = DIM_CFG.CGISAI WHERE `STARTTIME` BETWEEN UNIX_TIMESTAMP(CURDATE()) AND UNIX_TIMESTAMP( DATE_ADD(CURDATE(),INTERVAL 1 DAY) ) \n" +
                "GROUP BY starttime,SDR.CGISAI\n" +
                "ORDER BY starttime ASC";
            baseLine = KQIBaselineConstant.PAGE_BROWSING_DELAY;
        }
        else if( type == KQIType.PAGE_BROWSING_SUCCESS_RATE ){
            sql = "SELECT \n" +
                "(STARTTIME*1000) AS starttime,\n" +
                "SDR.CGISAI,\n" +
                "CELL_NAME,\n" +
                "CASE WHEN SUM(FST_PAGE_REQ_NUM)=0 THEN 99999 ELSE CAST(CAST(SUM(PAGE_SUCCEED_TIMES) AS DECIMAL(20,2))/ SUM(FST_PAGE_REQ_NUM)*100 AS DECIMAL(20,2)) END AS VALUE\n" +
                "\n" +
                "FROM `sdr_web_cgisai_15min` SDR\n" +
                "\n" +
                "INNER JOIN\n" +
                "(\n" +
                "        SELECT\n" +
                "        ID,              \n" +
                "        CGISAI,\n" +
                "        CELL_NAME,\n" +
                "        CUS_AREA_ID,\n" +
                "        CUS_AREA_NAME,\n" +
                "        ACCESS_TYPE_ID,\n" +
                "        ACCESS_TYPE\n" +
                "        FROM DIM_LOC_CUSTOMAREA	\n" +
                "        WHERE CUS_AREA_ID = 20 AND cell_name IN("+cellName+")\n" +
                ")DIM_CFG\n" +
                "ON WHERE `STARTTIME` BETWEEN UNIX_TIMESTAMP(CURDATE()) AND UNIX_TIMESTAMP( DATE_ADD(CURDATE(),INTERVAL 1 DAY) ) \n" +
                "SDR.CGISAI = DIM_CFG.CGISAI\n" +
                "GROUP BY starttime,SDR.CGISAI\n" +
                "ORDER BY starttime ASC";
            baseLine = KQIBaselineConstant.PAGE_BROWSING_SUCCESS_RATE;
        }
        else if( type == KQIType.VIDEO_STREAMING_START_DELAY ){
            sql = "SELECT\n" +
                    "(starttime*1000) AS starttime,\n" +
                    "SDR.CGISAI,\n" +
                    "CELL_NAME,\n" +
                    "CASE WHEN SUM(INTBUFFER_SUC) =0 THEN 0  \n" +
                    "ELSE CAST(CAST(CAST(SUM(TCP_CONN_DELAY) AS DECIMAL(20,2))/SUM(INTBUFFER_SUC) AS DECIMAL(20,0)) + CAST(CAST(SUM(GET_SUC_DELAY) AS DECIMAL(20,2))/SUM(INTBUFFER_SUC) AS DECIMAL(20,0)) + CAST(CAST(SUM(INTBUFFER_SUC_DELAY) AS DECIMAL(20,2))/SUM(INTBUFFER_SUC) AS DECIMAL(20,0)) AS DECIMAL(20,0)) END AS VALUE\n" +
                    "\n" +
                    "\n" +
                    "FROM `sdr_stream_cgisai_15min` SDR\n" +
                    "\n" +
                    "INNER JOIN\n" +
                    "(\n" +
                    "        SELECT\n" +
                    "        ID,              \n" +
                    "        CGISAI,\n" +
                    "        CELL_NAME,\n" +
                    "        CUS_AREA_ID,\n" +
                    "        CUS_AREA_NAME,\n" +
                    "        ACCESS_TYPE_ID,\n" +
                    "        ACCESS_TYPE\n" +
                    "        FROM DIM_LOC_CUSTOMAREA	\n" +
                    "        WHERE CUS_AREA_ID = 20 AND cell_name IN("+cellName+")                                                               \n" +
                    ")DIM_CFG\n" +
                    "ON SDR.CGISAI = DIM_CFG.CGISAI\n" +
                    "WHERE `STARTTIME` BETWEEN UNIX_TIMESTAMP(CURDATE()) AND UNIX_TIMESTAMP( DATE_ADD(CURDATE(),INTERVAL 1 DAY) ) \n" +
                    "GROUP BY starttime,SDR.CGISAI\n" +
                    "ORDER BY starttime ASC";
            baseLine = KQIBaselineConstant.VIDEO_STREAMING_START_DELAY;
        }
        else if( type == KQIType.VIDEO_STREAMING_START_SUCCESS_RATE ){
            sql = "SELECT\n" +
                    "(starttime*1000) AS starttime,\n" +
                    "SDR.CGISAI,\n" +
                    "CELL_NAME,\n" +
                    "CASE  WHEN SUM(TCP_CONN_REQ) = 0 THEN 99999 \n" +
                    "WHEN SUM( GET_REQ) = 0 OR  SUM( INTBUFFER_REQ) = 0 THEN 0  \n" +
                    "ELSE CAST((CAST(CAST(SUM(TCP_CONN_SUC) AS DECIMAL(20,2))/SUM(TCP_CONN_REQ) AS DECIMAL(20,4))) * (CAST(CAST(SUM(GET_SUC) AS DECIMAL(20,2))/SUM(GET_REQ) AS DECIMAL(20,4))) * (CAST(CAST(SUM(INTBUFFER_SUC) AS DECIMAL(20,2))/SUM(INTBUFFER_REQ) AS DECIMAL(20,4))) * 100 AS DECIMAL(20, 2)) END AS VALUE\n" +
                    "\n" +
                    "FROM `sdr_stream_cgisai_15min` SDR\n" +
                    "\n" +
                    "INNER JOIN\n" +
                    "(\n" +
                    "        SELECT\n" +
                    "        ID,              \n" +
                    "        CGISAI,\n" +
                    "        CELL_NAME,\n" +
                    "        CUS_AREA_ID,\n" +
                    "        CUS_AREA_NAME,\n" +
                    "        ACCESS_TYPE_ID,\n" +
                    "        ACCESS_TYPE\n" +
                    "        FROM DIM_LOC_CUSTOMAREA	\n" +
                    "        WHERE CUS_AREA_ID = 20 AND cell_name IN("+cellName+")                                                                      \n" +
                    "                                                                                    \n" +
                    ")DIM_CFG\n" +
                    "ON SDR.CGISAI = DIM_CFG.CGISAI \n" +
                    "WHERE `STARTTIME` BETWEEN UNIX_TIMESTAMP(CURDATE()) AND UNIX_TIMESTAMP( DATE_ADD(CURDATE(),INTERVAL 1 DAY) ) \n" +
                    "GROUP BY starttime,SDR.CGISAI\n" +
                    "ORDER BY starttime ASC";
            baseLine = KQIBaselineConstant.VIDEO_STREAMING_SUCCESS_RATE;
        }
        final double bLine = baseLine;
        List<ChartValue> vals = new ArrayList<>();
        List<ChartValue> bsVals = new ArrayList<>();
        jdbcTemplate.query(sql, ( ResultSet rs ) ->{
            ChartValue val = new ChartValue();
            val.setTimestamp( rs.getLong( "STARTTIME" ) );
            val.setValue( rs.getDouble("VALUE") );
            vals.add(val);
        });

        LineChartSerie bSerie = baselineSerieEngineering("Baseline",bLine);
        List<LineChartSerie> bSeries = new ArrayList<>();
        bSeries.add(bSerie);
        serie.setData(vals);
        bSerie.setName("Baseline");
        chart.setSeries(new ArrayList<>());
        chart.getSeries().add(serie);
        chart.setBaseLines(bSeries);
        return chart;
    }
    
    private LineChartSerie baselineSerieEngineering(String serieName,double baseline){
        List<ChartValue> bsVals = new ArrayList<>();
        LineChartSerie serie = new LineChartSerie();
        serie.setName(serieName);
        try {
            Date dt = Calendar.getInstance().getTime();

            long ONE_HOUR_IN_MILIS = (60000*60);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
            long tm = dt.getTime()+(7*ONE_HOUR_IN_MILIS);;
            String currentDte = format.format(dt);
            System.out.println( currentDte );
            Date now = format.parse(currentDte);
            long tmnow =  now.getTime()+(7*ONE_HOUR_IN_MILIS);

            for (; tmnow < tm; ) {
                ChartValue bVal = new ChartValue();
                bVal.setTimestamp(tmnow);
                bVal.setValue(baseline);
                bsVals.add(bVal);
                tmnow += ONE_HOUR_IN_MILIS;            
            }
        } catch (ParseException ex) {
            Logger.getLogger(KeyQualityIndexRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        serie.setData(bsVals);
        return serie;
    }
}
