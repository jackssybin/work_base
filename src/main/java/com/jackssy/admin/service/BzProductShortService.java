package com.jackssy.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jackssy.admin.entity.BzProductShort;

public interface BzProductShortService extends IService<BzProductShort> {
    void batchTest(BzProductShort bzProductShort);

    String getProductUrl(Integer productId);

    String getProductName(Integer productId);


//    void batchShortUrl();
}
