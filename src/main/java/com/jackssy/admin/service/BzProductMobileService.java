package com.jackssy.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jackssy.admin.entity.BzProductMobile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface BzProductMobileService extends IService<BzProductMobile> {

    void exportProductExtTxt(HttpServletResponse response, String productIds, Map<String, String> productExtMap);

    Map<String, String> getProductExtMap(HttpServletResponse response,
                     Integer[] productIdArray)throws IOException;
}
