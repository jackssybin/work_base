package com.jackssy.admin.entity.vo;

import com.jackssy.admin.entity.BzProductShort;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class BzProductShortVo extends BzProductShort {
    private MultipartFile phoneFile;

    public MultipartFile getPhoneFile() {
        return phoneFile;
    }

    public void setPhoneFile(MultipartFile phoneFile) {
        this.phoneFile = phoneFile;
    }
}
