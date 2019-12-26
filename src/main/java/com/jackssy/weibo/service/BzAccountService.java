package com.jackssy.weibo.service;

import com.jackssy.weibo.entity.AccountExcel;
import com.jackssy.weibo.entity.BzAccount;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jackssy.weibo.entity.BzRegion;
import com.jackssy.weibo.entity.BzTags;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jackssy
 * @since 2019-11-26
 */
public interface BzAccountService extends IService<BzAccount> {

    void importExcel(MultipartFile file, Map param) throws Exception;

    List<BzRegion> queryRegionList();

    List<BzTags> queryTagGroupList();

    void exportExcel(HttpServletResponse response, List<AccountExcel> accountList,String name)throws IOException;
}
