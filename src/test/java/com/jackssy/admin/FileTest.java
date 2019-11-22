package com.jackssy.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jackssy.admin.entity.Menu;
import org.junit.Test;

import java.io.File;

/**
 * @author zhoubin
 * @company:北京千丁互联科技有限公司
 * @date 2019/11/22 17:03
 */
public class FileTest {


    @Test
    public void test() {
        File file =new File("");
        String path = file.getAbsolutePath();
        System.out.println(path);

    }
}
