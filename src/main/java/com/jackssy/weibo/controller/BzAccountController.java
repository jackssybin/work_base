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
import java.util.ArrayList;
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

    @Autowired
    BzRegionService bzRegionService;

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
        QueryWrapper<BzRegion> regionQueryWrapper = new QueryWrapper<>();
        regionQueryWrapper.and(wrapper -> wrapper.eq("is_use",1));
        List<BzRegion> regionList = bzRegionService.list(regionQueryWrapper);
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

    @GetMapping("exportExcel")
    @SysLog("导出账号模板")
    public  String exportExcel(HttpServletResponse response)throws IOException{
            ExcelWriter writer = null;
            OutputStream outputStream = response.getOutputStream();
            try {
                //添加响应头信息
                response.setHeader("Content-disposition", "attachment; filename=" + "template.xls");
                response.setContentType("application/msexcel;charset=UTF-8");//设置类型
                response.setHeader("Pragma", "No-cache");//设置头
                response.setHeader("Cache-Control", "no-cache");//设置头
                response.setDateHeader("Expires", 0);//设置日期头
                //实例化 ExcelWriter
                writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);

                //实例化表单
                Sheet sheet = new Sheet(1, 0,AccountExcel.class);
                writer.write(new ArrayList<AccountExcel>(),sheet);
                sheet.setSheetName("目录");
                writer.finish();
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    response.getOutputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "index";
    }
}

