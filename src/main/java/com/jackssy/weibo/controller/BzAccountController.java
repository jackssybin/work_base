package com.jackssy.weibo.controller;


import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jackssy.admin.controller.BaseController;
import com.jackssy.admin.excel.config.ExcelUtil;
import com.jackssy.admin.excel.controller.ExportInfo;
import com.jackssy.common.annotation.SysLog;
import com.jackssy.common.base.PageData;
import com.jackssy.common.util.ResponseEntity;
import com.jackssy.weibo.common.Constant;
import com.jackssy.weibo.entity.AccountExcel;
import com.jackssy.weibo.entity.BzAccount;
import com.jackssy.weibo.entity.BzRegion;
import com.jackssy.weibo.entity.BzTags;
import com.jackssy.weibo.entity.dto.BzAccountDto;
import com.jackssy.weibo.enums.AccountStatusEnums;
import com.jackssy.weibo.service.BzAccountService;
import com.jackssy.weibo.service.BzRegionService;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
    public String list(ModelMap map){
        QueryWrapper<BzTags> tagsQueryWrapper = new QueryWrapper<>();
        List<BzTags> tagsList =bzTagsService.list(tagsQueryWrapper);
        map.put("tagsList",tagsList);
        return "weibo/account/list";
    }


    @GetMapping("importSet")
    @SysLog("跳转导入设置页面")
    public String importSet(ModelMap map){

        List<BzTags> tagsList = bzAccountService.queryTagGroupList();
        List<BzRegion> regionList = bzAccountService.queryRegionList();
        map.put("regionList",regionList);
        map.put("tagsList",tagsList);
        return "weibo/account/importSet";
    }

    @RequestMapping(value = "exportOne", method = RequestMethod.GET)
    public void writeExcel(HttpServletResponse response) throws IOException {
        List<AccountExcel> list = new ArrayList<>();
        String fileName = "账号信息源文件";
        String sheetName = "账号信息";
        ExcelUtil.writeExcel(response,list,fileName,sheetName,new AccountExcel());
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
        QueryWrapper<BzAccount> accountWapper = getBzAccountQueryWrapper(request);
        PageData<BzAccountDto> accountPageData = new PageData<>();

        accountWapper.orderByDesc("update_date");
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
            return ResponseEntity.failure("账号不存在");
        }
        bzAccountService.removeById(id);
        return ResponseEntity.success("操作成功");
    }



    @PostMapping("updateStatus")
    @ResponseBody
    @SysLog("更新账号数据")
    public ResponseEntity updateStatus(@RequestParam(value = "id",required = false)Integer id,@RequestParam(value = "status",required = false)Integer status){
        if(null ==id){
            return ResponseEntity.failure("参数错误");
        }
        BzAccount task = bzAccountService.getById(id);
        if(task == null){
            return ResponseEntity.failure("账号不存在");
        }
        task.setStatus(status);
        task.setUpdateDate(LocalDateTime.now());
        bzAccountService.updateById(task);
        return ResponseEntity.success("操作成功");
    }


    @PostMapping("batchUpdateStatus")
    @ResponseBody
    @SysLog("更新账号数据")
    public ResponseEntity batchUpdateStatus(@RequestParam(value = "ids",required = false)String ids,@RequestParam(value = "status",required = false)Integer status){
        if(StringUtils.isBlank(ids)){
            return ResponseEntity.failure("参数错误");
        }
        String [] idList = ids.split(",");
        List<BzAccount> accountList = new ArrayList<>();
        Arrays.stream(idList).forEach(item ->{
            BzAccount ba = new BzAccount();
            ba.setId(Integer.parseInt(item));
            ba.setStatus(status);
            ba.setUpdateDate(LocalDateTime.now());
            accountList.add(ba);
        });
        bzAccountService.updateBatchById(accountList);
        return ResponseEntity.success("操作成功");
    }

    @PostMapping("batchDelete")
    @ResponseBody
    @SysLog("批量删除账号")
    public ResponseEntity batchDelete(@RequestParam(value = "ids",required = false)String ids
                                      ){
        if(StringUtils.isBlank(ids)){
            return ResponseEntity.failure("参数错误");
        }
        String [] idList = ids.split(",");
        List<Integer> accountList = new ArrayList<>();
        Arrays.stream(idList).forEach(item ->{
            accountList.add(Integer.parseInt(item));
        });
        bzAccountService.removeByIds(accountList);
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

    @GetMapping("exportExcel")
    @SysLog("导出账号模板")
    public  String exportExcel(HttpServletResponse response)throws IOException{
        bzAccountService.exportExcel(response,null,"template.xls");
            return "index";
    }

    @GetMapping("exportAccountList")
    @SysLog("导出账号模板")
    public  void exportAccountList(HttpServletResponse response,ServletRequest request)throws IOException{
        QueryWrapper<BzAccount> accountWapper = getBzAccountQueryWrapper(request);
        List<BzAccount> bzAccounts = this.bzAccountService.list(accountWapper);
        List<AccountExcel> accountlist = new ArrayList<>();
        bzAccounts.forEach(item ->{
            AccountExcel ae = new AccountExcel();
            BeanUtils.copyProperties(item,ae);
            accountlist.add(ae);
        });
            String fileName = "账号数据";
            String sheetName = "账号";
            ExcelUtil.writeExcel(response, accountlist, fileName, sheetName, new AccountExcel());
    }

    private QueryWrapper<BzAccount> getBzAccountQueryWrapper(ServletRequest request) {
        QueryWrapper<BzAccount> accountWapper = new QueryWrapper<>();
        Map map = WebUtils.getParametersStartingWith(request, Constant.ACCOUNT_PREX);
        if(!map.isEmpty()){
            String keys = (String) map.get("key");
            String status = (String) map.get("status");
            String tagGroup = (String) map.get("tagGroup");
            String userSource =(String) map.get("userSource");
            if(StringUtils.isNotBlank(keys)) {
                accountWapper.and(wrapper ->
                        wrapper.like("account_user", keys));
            }
            if(StringUtils.isNotBlank(status)){
                accountWapper.eq("status",status);
            }
            if(StringUtils.isNotBlank(tagGroup)){
                accountWapper.eq("tag_group",tagGroup);
            }
            if(StringUtils.isNotBlank(userSource)){
                accountWapper.eq("user_source",userSource);
            }
        }
        return accountWapper;
    }
}

