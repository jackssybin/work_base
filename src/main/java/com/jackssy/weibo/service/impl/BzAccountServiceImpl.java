package com.jackssy.weibo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jackssy.admin.excel.config.ExcelUtil;
import com.jackssy.weibo.entity.AccountExcel;
import com.jackssy.weibo.entity.BzAccount;
import com.jackssy.weibo.mapper.BzAccountMapper;
import com.jackssy.weibo.service.BzAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.Account;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
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

    @Override
    public void importExcel(MultipartFile file, Map param) throws Exception{
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
                if(StringUtils.isNotBlank((String)param.get("region_id"))){
                    bza.setRegionId((String)param.get("region_id"));
                    bza.setRegionName((String)param.get("region_name"));
                }
                if(StringUtils.isNotBlank((String)param.get("source"))){
                    bza.setUserSource((String)param.get("source"));
                }
                if(StringUtils.isNotBlank((String)param.get("tags"))){
                    bza.setTagGroup((String)param.get("tags"));
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
}
