package com.jackssy.common.util;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

/**
 * JWT校验工具类
 * <ol>
 * <li>iss: jwt签发者</li>
 * <li>sub: jwt所面向的用户</li>
 * <li>aud: 接收jwt的一方</li>
 * <li>exp: jwt的过期时间，这个过期时间必须要大于签发时间</li>
 * <li>nbf: 定义在什么时间之前，该jwt都是不可用的</li>
 * <li>iat: jwt的签发时间</li>
 * <li>jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击</li>
 * </ol>
 */
public class JwtUtil {

    private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    /**
     * JWT 加解密类型
     */
    private static final SignatureAlgorithm JWT_ALG = SignatureAlgorithm.HS256;
    /**
     * JWT 生成密钥使用的密码
     */
    private static final String JWT_RULE = "qding.sso.jwt";

    /**
     * 使用指定密钥生成规则，生成JWT加解密密钥
     *
     * @param alg  加解密类型
     * @param rule 密钥生成规则
     * @return
     */
    public static SecretKey generateKey(SignatureAlgorithm alg, String rule) {
        // 将密钥生成键转换为字节数组
        byte[] bytes = Base64Util.decode(rule);

        // 根据指定的加密方式，生成密钥
        return new SecretKeySpec(bytes, alg.getJcaName());
    }


    /**
     * 解析JWT
     *
     * @param key       jwt 加密密钥
     * @param claimsJws jwt 内容文本
     * @return {@link Jws}
     * @throws Exception
     */
    public static Jws<Claims> parseJWT(Key key, String claimsJws) {

        // 解析 JWT 字符串
        return Jwts.parser().setSigningKey(key).parseClaimsJws(claimsJws);
    }

    public static Jws<Claims> parseJWT( String claimsJws) {
        SecretKey key = generateKey(JWT_ALG, JWT_RULE);
        return parseJWT(key,claimsJws);
    }

    /**
     * 校验JWT
     *
     * @param claimsJws jwt 内容文本
     * @return ture or false
     */
    public static Boolean checkJWT(String claimsJws) {
        boolean flag = false;
        try {
            SecretKey key = generateKey(JWT_ALG, JWT_RULE);
            // 获取 JWT 的 payload 部分
            flag = (parseJWT(key, claimsJws).getBody() != null);
        } catch (Exception e) {
            logger.warn("JWT验证出错，错误原因：{}", e.getMessage());
        }
        return flag;
    }







    public static void main(String[] args) throws Exception {
        String body ="ttt";
        String token = "";//JwtUtil.buildJWT(body);
        System.out.println(token);

        //过期测试
//        token ="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYWNrc3N5IiwianRpIjoiMzNlZGUzZGMtMjg3Zi00M2M4LTg5M2EtZWU2OWI3YjZkZDBmIiwiaWF0IjoxNTUyMzcxNzE2LCJleHAiOjE1NTIzODE3MTZ9.J7_-yJb_7_wvsuKnJc4Jozf5k6lvQTa6DY9egbzY1nw";
//        token ="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJhY2NvdW50SWRcIjpcIjIwMTgxMTIxMTUyMjEzMjk4NWFcIixcImFjY291bnRUeXBlXCI6XCIyXCIsXCJ0ZW5lbWVudElkXCI6XCIyMDE3MDgyMzA5NDE1OTAyOWFhXCIsXCJ1c2VyaWRcIjpcIjEzMTAwMDAwMDkyXCIsXCJ1c2VybmFtZVwiOlwi5rWL6K-VLea4qeaWh-engFwifSIsImp0aSI6IjAwMGNlOTk3LWEwYTUtNGZmYi04ODhhLTJhZTNlODZiMmRhYSIsImlhdCI6MTU1NTQ2MzQyMSwiZXhwIjoxNTU1NDczNDIxfQ.Q-lyGDyPS1xG1e3J4ijhYHyqw2xDNZXJxW3ZzIBwiUA";
//        Boolean verifyToken2 = JwtUtil.isTokenExpired(token);
//        System.out.println(verifyToken2);

//        token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJhY2NvdW50SWRcIjpcIjIwMTkwNDI5MjIxOTU4ODM3MDBcIixcImFjY291bnRUeXBlXCI6XCIyXCIsXCJ0ZW5lbWVudElkXCI6XCIyMDE5MDMyMjE1MDkzMzI2OGNhXCIsXCJ1c2VyaWRcIjpcIjE3ODAwMDAwMDAyXCIsXCJ1c2VybmFtZVwiOlwi5rWL6K-V6LSm5Y-3MlwifSIsImp0aSI6ImY0OTAyMDE0LTM5OWYtNDY2YS1hNTQyLTRkN2VkNjAxOGYzOCIsImlhdCI6MTU1NjU0ODc3MH0.C7hOOfR19FB59JbcqsjvHgOWVWTwd4smFodaccHRh90";
        //{"accountId":"2019042922195883700","accountType":"2","tenementId":"20190322150933268ca","userid":"17800000002","username":"测试账号2"}
        token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJhY2NvdW50SWRcIjpcIjIwMTkwNTA5MTYzNTUwMDg1MzdcIixcImFjY291bnRUeXBlXCI6XCIxXCIsXCJsb2dpblR5cGVcIjpcInBjXCIsXCJ0ZW5lbWVudElkXCI6XCIyMDE5MDIxMzE3NTIzOTE3NzUxXCIsXCJ1c2VyaWRcIjpcIjEzOTk2MDcwMTk2XCIsXCJ1c2VybmFtZVwiOlwi5byg6ZKw6IuRXCJ9IiwianRpIjoiMjJhOGY4YTctNzE1NS00NDlkLWEyODctZmJlYzY5MzExMjYwIiwiaWF0IjoxNTYwNzM1OTk1fQ.V8xW18C0vXo-SSZg-PWGefmy81ftXr-SzeOydN1r7o4";
        token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYWNrc3N5dXYiLCJqdGkiOiIyNjVmN2VlOC0xNWFjLTQ1YzctODcyOC1hNzBmMjQ3OGZjZjgiLCJpYXQiOjE1NjI4MzgxMzQsImV4cCI6MTU2MzcwMjEzNH0.U93-73saAo_-MEQ23Cj6AbNklZoYmvRULOPxRlvyJN0";
        //正常测试
        Boolean verifyToken = JwtUtil.checkJWT(token);
        System.out.println(verifyToken);


    }
}