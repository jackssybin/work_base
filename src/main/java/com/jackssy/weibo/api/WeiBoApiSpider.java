package com.jackssy.weibo.api;

import com.jackssy.weibo.api.base.AbstractSpider;
import net.dongliu.requests.RawResponse;
import net.dongliu.requests.Requests;
import net.dongliu.requests.Session;

import java.util.HashMap;
import java.util.Map;

public class WeiBoApiSpider extends AbstractSpider {

    private static Session session = Requests.session();
    private static String username = "MTU4MTAzMDI1MTU=";
    private static String password = "zb-000000";


    public static String preLogin() {
        String rend="";
        try {
            Map<String, String> paras = new HashMap<>();
            RawResponse preLoginResponse = session.get("https://passport.weibo.cn/signin/login")
                    .timeout(15000).params(paras).send();
            String cookie_login=preLoginResponse.getCookie("login").getValue();


            Map<String, String> login_paramMap = new HashMap<>();
            login_paramMap.put("username","15810302515");
            login_paramMap.put("password","zb-000000");
            login_paramMap.put("savestate","1");
            login_paramMap.put("r","");
            login_paramMap.put("ec","0");
            login_paramMap.put("pagerefer","");
            login_paramMap.put("entry","mweibo");
            login_paramMap.put("wentry","mweibo");
            login_paramMap.put("loginfrom","");
            login_paramMap.put("client_id","");
            login_paramMap.put("code","");
            login_paramMap.put("qq","");
            login_paramMap.put("mainpageflag","1");
            login_paramMap.put("hff","");
            login_paramMap.put("hfp","");
//            username=username&password=password&savestate=1&r=&ec=0&pagerefer=&entry=mweibo
// &wentry=&loginfrom=&client_id=&code=&qq=&mainpageflag=1&hff=&hfp=
            RawResponse ssoLoginResponse = session.post("https://passport.weibo.cn/sso/login")
                    .timeout(15000).params(login_paramMap).send();
            String cookie_sub=ssoLoginResponse.getCookie("SUB").getValue();
            String cookie_suhb=ssoLoginResponse.getCookie("SUHB").getValue();
            String cookie_scf=ssoLoginResponse.getCookie("SCF").getValue();
            String cookie_ssoLoginState=ssoLoginResponse.getCookie("SSOLoginState").getValue();

            RawResponse mainIndexResponse = session.post("https://m.weibo.cn/")
                    .timeout(15000).params(paras).send();
            String cookie_T_M=mainIndexResponse.getCookie("_T_WM").getValue();

            RawResponse stOperateResponse = session.post("https://m.weibo.cn/status/4454167214791648")
                    .timeout(15000).params(paras).send();
            String cookie_st=stOperateResponse.getCookie("st").getValue();

//            id=4233098637958278&attitude=heart&st=5f5fcf

            Map<String, String> ssoParams = new HashMap<>(3);
            ssoParams.put("id", "4454167214791648");
            ssoParams.put("attitude", "heart");
            ssoParams.put("st", cookie_st);
            RawResponse raiseResponse = session.post("https://m.weibo.cn/api/attitudes/create")
                    .timeout(15000).params(ssoParams).send();
            System.out.println(raiseResponse);


            return rend;
        } catch (Exception e) {
            rend = "预登录出错了";
            e.printStackTrace();
            return rend;
        }
    }

    /**
     * 整个登录分三步1.预登录 2.登录 3.重定向 4.获取数据
     */
    public static void main(String[] args) {
        //访问登录页面获取cookie
        session.get("http://weibo.com/login.php").send().readToText();
        //1.开始预登录
        String preJsonStirng = preLogin();
        //2.登录
//        String successString = login(preJsonStirng);
//        //3.重定向
//        redirect(successString);
//        //4.获取数据
//        String data = getData();
//        //输出登录后的后台视频分析数据
//        System.out.println(data);

    }
}
