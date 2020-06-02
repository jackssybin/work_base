package com.jackssy.weibo.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jackssy.admin.excel.config.ExcelUtil;
import com.jackssy.weibo.entity.AccountExcel;
import com.jackssy.weibo.entity.BzAccount;
import com.jackssy.weibo.entity.BzRegion;
import com.jackssy.weibo.entity.BzTags;
import com.jackssy.weibo.mapper.BzAccountMapper;
import com.jackssy.weibo.service.BzAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jackssy.weibo.service.BzRegionService;
import com.jackssy.weibo.service.BzTagsService;
import com.jackssy.weibo.util.WeiBoUtil;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.Account;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jackssy
 * @since 2019-11-26
 */
@Service
public class BzAccountServiceImpl extends ServiceImpl<BzAccountMapper, BzAccount> implements BzAccountService {

    @Autowired
    BzRegionService bzRegionService;

    @Autowired
    BzTagsService bzTagsService;

    @Override
    public void importExcel(MultipartFile file, Map param) throws Exception{

        List<BzTags> tagsList = this.queryTagGroupList();
        List<BzRegion> regionList = this.queryRegionList();

        AccountExcel ac = new AccountExcel();
        List<Object> aList = ExcelUtil.readExcel(file, ac,1);
        List<BzAccount> bzaList = new ArrayList<>();
        aList.forEach( item ->{
            QueryWrapper<BzAccount> accountWapper = new QueryWrapper<>();
            AccountExcel ae = (AccountExcel) item;
            accountWapper.and(wrapper ->
            wrapper.eq("account_user", ((AccountExcel) item).getAccountUser()));
            BzAccount bza = this.getOne(accountWapper);
            if(null == bza){
                bza = new BzAccount();
                BeanUtils.copyProperties(item,bza);

                if(StringUtils.isBlank(bza.getRegionId())){

                    try {
                        Random random = new Random();
                        int rn = random.nextInt(WeiBoUtil.regionMap.size());
                        int num =0;
                        for (String key : WeiBoUtil.regionMap.keySet()) {
                            if (rn ==num){
                                bza.setRegionId(key);
                                bza.setRegionName(WeiBoUtil.regionMap.get(key));
                            }
                            num++;
                        }
                    } catch (Exception e) {
                        bza.setRegionId((String)param.get("region_id"));
                        bza.setRegionName((String)param.get("region_name"));
                    }




                }else{
                    String regionName = getRegionNameById(regionList,bza.getRegionId());
                    if(StringUtils.isNotEmpty(regionName)){
                        bza.setRegionName(regionName);
                    }else{
                        bza.setRegionId((String)param.get("region_id"));
                        bza.setRegionName((String)param.get("region_name"));
                    }
                }
                if(StringUtils.isBlank(bza.getTagGroup()) &&StringUtils.isNotBlank((String)param.get("tags")) ){
                        bza.setTagGroup((String)param.get("tags"));
                }else if(StringUtils.isNotBlank(bza.getTagGroup()) ){
                    if(!isExistTagGroup(tagsList, bza.getTagGroup())  &&StringUtils.isNotBlank((String)param.get("tags"))){
                        bza.setTagGroup((String)param.get("tags"));
                    }
                }
                if(StringUtils.isNotBlank(bza.getUserSource())){
                    bza.setUserSource((String)param.get("source"));
                }

                bza.setStatus(1);
                bza.setCreateDate(LocalDateTime.now());
                bza.setUpdateDate(LocalDateTime.now());
                bzaList.add(bza);

                if(bzaList.size()>500){
                    this.saveBatch(bzaList);
                    bzaList.clear();
                }
            }
        });
        if(!bzaList.isEmpty()){
            this.saveBatch(bzaList);
        }
    }

    private boolean isExistTagGroup(List<BzTags> tagList,String tagGroup){
        boolean result = false;
        for (int i = 0; i < tagList.size(); i++) {
            if(tagList.get(i).getTagCode().equals(tagGroup)){
                result = true;
                break;
            }
        }
        return result;
    }

    private String getRegionNameById(List<BzRegion> regionList,String regionId){
        String regionName = "";
        for (int i = 0; i < regionList.size(); i++) {
            if(regionList.get(i).getRegionCode().equals(regionId)){
                regionName = regionList.get(i).getRegionName();
                break;
            }
        }
        return regionName;
    }




    public List<BzRegion> queryRegionList() {
        QueryWrapper<BzRegion> regionQueryWrapper = new QueryWrapper<>();
        regionQueryWrapper.and(wrapper -> wrapper.eq("is_use",1));
        return bzRegionService.list(regionQueryWrapper);
    }

    public  List<BzTags> queryTagGroupList() {
        QueryWrapper<BzTags> tagsQueryWrapper = new QueryWrapper<>();
        return bzTagsService.list(tagsQueryWrapper);
    }


    @Override
    public void exportExcel(HttpServletResponse response, List<AccountExcel> accountList,String name) throws IOException {
        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            //添加响应头信息
            response.setHeader("Content-disposition", "attachment; filename=" + name);
//            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头
            //实例化 ExcelWriter
            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);

            //实例化表单
            Sheet sheet = new Sheet(1, 0,AccountExcel.class);
            writer.write(accountList,sheet);
            sheet.setSheetName("列表");
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
    }
}
