package com.jackssy.admin.weibo;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

/**
 * @author zhoubin
 * @company:北京千丁互联科技有限公司
 * @date 2020/3/18 16:53
 */
public class SeleniumTest {

    @Test
    public void getWeiBoContent(){
        long startTime = System.currentTimeMillis();
        WebDriver driver = new ChromeDriver();
        driver.get("https://weibo.com/1989660417/IwDNtmia2");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String title = driver.getTitle();
        System.out.printf(title);
        String content = ((ChromeDriver) driver).findElementByClassName("WB_text").getText();
        System.out.println(content);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);
        driver.close();
    }
}
