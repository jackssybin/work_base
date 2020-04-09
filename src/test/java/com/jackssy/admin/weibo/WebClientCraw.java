package com.jackssy.admin.weibo;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

/**
 * @author zhoubin
 * @company:北京千丁互联科技有限公司
 * @date 2020/3/18 16:00
 */
public class WebClientCraw {

    @Test
    public void testCraw(){
        try {
            WebClient webClient = getWebClient();
            String url = "https://weibo.com/1989660417/IwDNtmia2";
            HtmlPage page = webClient.getPage(url);//通过url获取整个页面
            System.out.println(page);
            webClient.waitForBackgroundJavaScript(30000);//异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束

            String pageXml = page.asXml();//直接将加载完成的页面转换成xml格式的字符串
            System.out.println(pageXml);
            Document document = Jsoup.parse(pageXml);//获取html文档
            Elements elements =document.getElementsByClass("WB_text ");
            System.out.println(elements);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private WebClient getWebClient() {
        WebClient webClient = new WebClient(BrowserVersion.CHROME  );//模拟创建打开一个谷歌浏览器窗口
        webClient.getOptions().setTimeout(15000);//设置网页响应时间
        webClient.getOptions().setUseInsecureSSL(true);//是否
        webClient.getOptions().setRedirectEnabled(true);//是否自动加载重定向
        webClient.getOptions().setThrowExceptionOnScriptError(false);//是否抛出页面javascript错误
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//是否抛出response的错误
        webClient.getOptions().setJavaScriptEnabled(true);// HtmlUnit对JavaScript的支持不好，关闭之
        webClient.getOptions().setCssEnabled(false);// HtmlUnit对CSS的支持不好，关闭之
        return webClient;
    }
}
