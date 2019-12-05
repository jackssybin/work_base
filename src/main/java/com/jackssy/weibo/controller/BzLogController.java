package com.jackssy.weibo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jackssy.common.annotation.SysLog;
import com.jackssy.common.base.PageData;
import com.jackssy.common.config.RedisClient;
import com.jackssy.weibo.common.Constant;
import com.jackssy.weibo.entity.BzAccount;
import com.jackssy.weibo.entity.BzLog;
import com.jackssy.weibo.entity.dto.BzAccountDto;
import com.jackssy.weibo.entity.dto.BzLogDto;
import com.jackssy.weibo.enums.AccountStatusEnums;
import com.jackssy.weibo.enums.CommonLogEnums;
import com.jackssy.weibo.service.BzAccountService;
import com.jackssy.weibo.service.BzLogService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.Account;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jackssy
 * @since 2019-11-26
 */
@Controller
@RequestMapping("/bzLog")
public class BzLogController {


    @Autowired
    BzLogService bzLogService;


    @GetMapping("list")
    @SysLog("跳转日志列表页面")
    public String list(){
        return "weibo/log/list";
    }


    /**
     * 日志列表
     * @param page 页数
     * @param limit 每页条数
     * @param request 请求
     * @return 日志列表 BzLogDto
     */
    @PostMapping("list")
    @ResponseBody
    @SysLog("查询账号列表")
    public PageData<BzLogDto> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                   @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                   ServletRequest request){
        Map map = WebUtils.getParametersStartingWith(request, Constant.LOG_PREX);
        PageData<BzLogDto> logPageData = new PageData<>();
        QueryWrapper<BzLog> logWapper = new QueryWrapper<>();
        if(!map.isEmpty()){
            String keys = (String) map.get("key");
            if(StringUtils.isNotBlank(keys)) {
                logWapper.and(wrapper ->
                        wrapper.like("account_id", keys)
                                .or().like("task_id",keys));
            }
        }
        logWapper.orderByDesc("task_id","create_date");
        IPage<BzLog> logIPage = bzLogService.page(new Page<>(page,limit),logWapper);

        logPageData.setCount(logIPage.getTotal());
        logPageData.setData(logIPage.getRecords().stream().map(xx ->{
            BzLogDto dto = new BzLogDto();
            BeanUtils.copyProperties(xx,dto);
            dto.setLoginSystemStatus(CommonLogEnums.getStatusByValue(xx.getLoginSystem()));
            dto.setCommentStatus(CommonLogEnums.getStatusByValue(xx.getComment()));
            dto.setFocusStatus(CommonLogEnums.getStatusByValue(xx.getFocus()));
            dto.setRaisesStatus(CommonLogEnums.getStatusByValue(xx.getRaises()));
            dto.setForwardStatus(CommonLogEnums.getStatusByValue(xx.getForward()));
            dto.setForwardCommentStatus(CommonLogEnums.getStatusByValue(xx.getForwardComment()));
            dto.setCollectStatus(CommonLogEnums.getStatusByValue(xx.getCollect()));
            return dto;
        }).collect(Collectors.toList()));
        return logPageData;
    }
}

