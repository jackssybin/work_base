package com.jackssy.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jackssy.admin.entity.BzProductShort;
import org.springframework.web.multipart.MultipartFile;

public interface BzProductShortService extends IService<BzProductShort> {
    void batchTest(BzProductShort bzProductShort);

    String getProductUrl(Integer productId);

    String getProductName(Integer productId);

    Integer getProductCount(Integer productId);

    String savePhoneFile(MultipartFile file);

    void produceQueue(BzProductShort bzProductShort);

    void consumerQueue();




//    void batchShortUrl();
}
