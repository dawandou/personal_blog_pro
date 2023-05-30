package com.blog.personalblog.controller;

import com.blog.personalblog.annotation.OperationLogSys;
import com.blog.personalblog.annotation.OperationType;
import com.blog.personalblog.entity.Notice;
import com.blog.personalblog.service.NoticeService;
import com.blog.personalblog.service.StatisticsService;
import com.blog.personalblog.util.JsonResult;
import com.blog.personalblog.vo.StatisticsBaseCountVO;
import com.blog.personalblog.vo.StatisticsTagCountVO;
import com.blog.personalblog.vo.StatisticsTopCountVO;
import com.blog.personalblog.vo.StatisticsWordCloudVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * 首页统计
 *
 * @author: SuperMan
 * @create: 2023-05-20
 **/

@Api(tags = "首页统计")
@RestController
@RequestMapping("/index")
public class IndexController {

    @Resource
    private StatisticsService statisticsService;
    @Resource
    private NoticeService noticeService;


    /**
     * 顶部统计查询
     * @return
     */
    @ApiOperation(value = "首页顶部统计查询")
    @PostMapping("/getTopCount")
    @OperationLogSys(desc = "首页顶部统计查询", operationType = OperationType.SELECT)
    public JsonResult<Object> getTopCount() {
        StatisticsTopCountVO topCount = statisticsService.getTopCount();
        return JsonResult.success(topCount);
    }


    /**
     * 近一周发文的数量
     * @return
     */
    @ApiOperation(value = "近一周发文的数量")
    @PostMapping("/getWeekNum")
    @OperationLogSys(desc = "近一周发文的数量", operationType = OperationType.SELECT)
    public JsonResult<Object> getWeekNum() {
        List<StatisticsBaseCountVO> list = statisticsService.getArticleCount();
        return JsonResult.success(list);
    }

    /**
     * 在线人数
     * @return
     */
    @ApiOperation(value = "在线人数")
    @PostMapping("/getOnline")
    @OperationLogSys(desc = "在线人数", operationType = OperationType.SELECT)
    public JsonResult<Object> getOnline() {
        List<StatisticsBaseCountVO> online = statisticsService.getOnline();
        return JsonResult.success(online);
    }

    /**
     * 获取标签数据
     * @return
     */
    @ApiOperation(value = "获取标签数据")
    @PostMapping("/getTagCount")
    @OperationLogSys(desc = "获取标签数据", operationType = OperationType.SELECT)
    public JsonResult<Object> getTagCount() {
        List<StatisticsTagCountVO> tagCount = statisticsService.getTagCount();
        return JsonResult.success(tagCount);
    }

    /**
     * 获取词云数据
     * @return
     */
    @ApiOperation(value = "获取词云数据")
    @PostMapping("/getWordCloud")
    @OperationLogSys(desc = "获取词云数据", operationType = OperationType.SELECT)
    public JsonResult<Object> getWordCloud() {
        List<StatisticsWordCloudVO> wordCloud = statisticsService.getWordCloud();
        return JsonResult.success(wordCloud);
    }

    /**
     * 获取最新前5条公告
     * @return
     */
    @ApiOperation(value = "获取最新前5条公告")
    @PostMapping("/getNoticeList")
    @OperationLogSys(desc = "获取最新前5条公告", operationType = OperationType.SELECT)
    public JsonResult<Object> getNoticeList() {
        List<Notice> list = noticeService.getNoticeTopFive();
        return JsonResult.success(list);
    }

}
