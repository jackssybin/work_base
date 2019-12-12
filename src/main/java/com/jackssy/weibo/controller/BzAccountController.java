package com.jackssy.weibo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jackssy.admin.controller.BaseController;
import com.jackssy.common.annotation.SysLog;
import com.jackssy.common.base.PageData;
import com.jackssy.common.util.ResponseEntity;
import com.jackssy.weibo.common.Constant;
import com.jackssy.weibo.entity.BzAccount;
import com.jackssy.weibo.entity.BzTags;
import com.jackssy.weibo.entity.dto.BzAccountDto;
import com.jackssy.weibo.enums.AccountStatusEnums;
import com.jackssy.weibo.service.BzAccountService;
import com.jackssy.weibo.service.BzTagsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.io.IOException;
import java.util.List;
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
@RequestMapping("/bzAccount")
public class BzAccountController extends BaseController {


    @Autowired
    BzAccountService bzAccountService;

    @Autowired
    BzTagsService bzTagsService;

    @GetMapping("list")
    @SysLog("跳转账号列表页面")
    public String list(){
        return "weibo/account/list";
    }


    @GetMapping("importSet")
    @SysLog("跳转导入设置页面")
    public String importSet(ModelMap map){
        QueryWrapper<BzTags> tagsQueryWrapper = new QueryWrapper<>();
        List<BzTags> tagsList =bzTagsService.list(tagsQueryWrapper);
        map.put("tagsList",tagsList);
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
        accountWapper.orderByDesc("update_date","create_date");
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

    @PostMapping("uploadExcel")
    @ResponseBody
    @SysLog("导入账号")
    public ResponseEntity uploadExcel(ServletRequest request){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map map = WebUtils.getParametersStartingWith(request, Constant.ACCOUNT_PREX);
        try {
        MultipartFile multiFile = multipartRequest.getFile("file");
        if(multiFile.isEmpty()){
            return ResponseEntity.failure("文件为空");
        }
            bzAccountService.importExcel(multiFile,map);

        } catch (IOException e) {
            logger.info("上传异常:",e);
            return ResponseEntity.failure("上传异常");

        }catch (Exception e){
            logger.info("导入异常:",e);
            return ResponseEntity.failure("导入异常");
        }
        return ResponseEntity.success("success");
    }

}

