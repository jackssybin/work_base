package com.jackssy.weibo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jackssy.admin.controller.BaseController;
import com.jackssy.common.annotation.SysLog;
import com.jackssy.common.base.PageData;
import com.jackssy.common.config.RedisClient;
import com.jackssy.common.util.Constants;
import com.jackssy.common.util.ResponseEntity;
import com.jackssy.weibo.common.Constant;
import com.jackssy.weibo.entity.BzAccount;
import com.jackssy.weibo.entity.BzTask;
import com.jackssy.weibo.entity.dto.BzAccountDto;
import com.jackssy.weibo.entity.dto.BzTaskDto;
import com.jackssy.weibo.enums.AccountStatusEnums;
import com.jackssy.weibo.enums.CommentTypeEnums;
import com.jackssy.weibo.enums.StatusNameEnums;
import com.jackssy.weibo.service.BzAccountService;
import com.jackssy.weibo.service.BzTagsService;
import com.jackssy.weibo.service.BzTaskService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.Account;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
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
@RequestMapping("/bzAccount")
public class BzAccountController extends BaseController {


    @Autowired
    BzAccountService bzAccountService;


    @GetMapping("list")
    @SysLog("跳转账号列表页面")
    public String list(){
        return "weibo/account/list";
    }


    @GetMapping("importSet")
    @SysLog("跳转导入设置页面")
    public String importSet(){
        return "weibo/account/importSet";
    }

    /**
     * 账号列表
     * @param page 页数
     * @param limit 每页条数
     * @param request 请求
     * @return 账号列表 BzAccountDto
     */
    @PostMapping("list")
    @ResponseBody
    @SysLog("查询账号列表")
    public PageData<BzAccountDto> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                    @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                    ServletRequest request){
        Map map = WebUtils.getParametersStartingWith(request, Constant.ACCOUNT_PREX);
        PageData<BzAccountDto> accountPageData = new PageData<>();
        QueryWrapper<BzAccount> accountWapper = new QueryWrapper<>();
        if(!map.isEmpty()){
            String keys = (String) map.get("key");
            if(StringUtils.isNotBlank(keys)) {
                accountWapper.and(wrapper ->
                        wrapper.like("account_user", keys));
            }
        }
        IPage<BzAccount> accountIPage = bzAccountService.page(new Page<>(page,limit),accountWapper);

        accountPageData.setCount(accountIPage.getTotal());
        accountPageData.setData(accountIPage.getRecords().stream().map(xx ->{
                BzAccountDto dto = new BzAccountDto();
                BeanUtils.copyProperties(xx,dto);
                dto.setStatusName(AccountStatusEnums.getStatusByValue(xx.getStatus()));
                return dto;
        }).collect(Collectors.toList()));
        return accountPageData;
    }

    /**
     *根据id删除账号
     * @param id 要删除的账号id
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除账号数据")
    public ResponseEntity delete(@RequestParam(value = "id",required = false)Integer id){
        if(null ==id){
            return ResponseEntity.failure("参数错误");
        }
        BzAccount task = bzAccountService.getById(id);
        if(task == null){
            return ResponseEntity.failure("任务不存在");
        }
        bzAccountService.removeById(id);
        return ResponseEntity.success("操作成功");
    }

}

