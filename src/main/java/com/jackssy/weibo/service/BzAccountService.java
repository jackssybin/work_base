package com.jackssy.weibo.service;

import com.jackssy.weibo.entity.BzAccount;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
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

}
