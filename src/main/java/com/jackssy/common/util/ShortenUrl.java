package com.jackssy.common.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShortenUrl {

    private static final boolean DEBUG = true;


    public static void main(String[] args) {
//        testChanage();
        String url="https://qap.qdingnet.com/account/login";
        System.out.println(test(url));
    }

    private static void testChanage() {
        String url = "http://www.henshiyong.com/tools/sina-shorten-url.php";
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("url", "http://www.google.com");
        params.put("submit", "转换");

        String data = null;

        try {
            data = postUrl(url, params);
            if (DEBUG) {
                System.out.println(data);
            }
        } catch (IOException ex) {
        }

        if (data != null) {
            String shortUrl = getShortenUrl(data);
            if (DEBUG) {
                System.out.println(shortUrl);
            }
        }
    }

    public static String test(String longurl){
        BufferedReader reader = null;

        try {
            longurl = URLEncoder.encode(longurl, "GBK");
            URL url = new URL("http://api.t.sina.com.cn/short_url/shorten.json?source=2546260130&url_long=" + longurl);
            InputStream iStream = url.openStream();
            reader = new BufferedReader(new InputStreamReader(iStream));
            String jsonStr = reader.readLine();
            JSONObject jsonObj = JSONObject.parseArray(jsonStr).getJSONObject(0);
            return jsonObj.getString("url_short");
        } catch (Exception e) {
            return longurl;
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    public static String getFixShortUrl(String extid){
        String longUrl="https://qap.qdingnet.com/account/login?extid="+extid;
        return test(longUrl);
    }

    public static String getFixShortUrl(String longUrl,String productId,String phoneNumber){
        if(StringUtils.isEmpty(longUrl)){
            longUrl="https://localhost:8088/uv/{encryStr}";
        }
        longUrl=longUrl.replace("{encryStr}",phoneNumber+","+productId);
        return test(longUrl);
    }

    public static String getFixShortUrl(String longUrl,String encryStr){
        if(StringUtils.isEmpty(longUrl)){
            longUrl="https://localhost:8088/uv/{encryStr}";
        }
        longUrl=longUrl.replace("{encryStr}",encryStr);
        return test(longUrl);
    }

    public static String getShortenUrl(String content) {
        String url = null;
        List<String> resultList = getContext(content);

        for (Iterator<String> iterator = resultList.iterator(); iterator
                .hasNext();) {
            url = iterator.next();
        }

        return url;
    }

    /**
     * Extract "XXXX" from "<textarea>XXXX</textarea>"
     *
     * @param html
     * @return
     */
    public static List<String> getContext(String html) {
        List<String> resultList = new ArrayList<String>();
        Pattern p = Pattern.compile("<textarea>(.*)</textarea>");
        Matcher m = p.matcher(html);
        while (m.find()) {
            resultList.add(m.group(1));
        }
        return resultList;
    }

    public static class HttpException extends RuntimeException {

        private int errorCode;
        private String errorData;

        public HttpException(int errorCode, String errorData) {
            super("HTTP Code " + errorCode + " : " + errorData);
            this.errorCode = errorCode;
            this.errorData = errorData;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public String getErrorData() {
            return errorData;
        }

    }

    public static String postUrl(String url, Map<String, String> params)
            throws IOException {
        String data = "";
        for (String key : params.keySet()) {
            data += "&" + URLEncoder.encode(key, "UTF-8") + "="
                    + URLEncoder.encode(params.get(key), "UTF-8");
        }
        data = data.substring(1);
        // System.out.println(data);
        URL aURL = new URL(url);
        HttpURLConnection aConnection = (HttpURLConnection) aURL
                .openConnection();
        try {
            aConnection.setDoOutput(true);
            aConnection.setDoInput(true);
            aConnection.setRequestMethod("POST");
            // aConnection.setAllowUserInteraction(false);
            // POST the data
            OutputStreamWriter streamToAuthorize = new OutputStreamWriter(
                    aConnection.getOutputStream());
            streamToAuthorize.write(data);
            streamToAuthorize.flush();
            streamToAuthorize.close();

            // check error
            int errorCode = aConnection.getResponseCode();
            if (errorCode >= 400) {
                InputStream errorStream = aConnection.getErrorStream();
                try {
                    String errorData = streamToString(errorStream);
                    throw new HttpException(errorCode, errorData);
                } finally {
                    errorStream.close();
                }
            }

            // Get the Response
            InputStream resultStream = aConnection.getInputStream();
            try {
                String responseData = streamToString(resultStream);
                return responseData;
            } finally {
                resultStream.close();
            }
        } finally {
            aConnection.disconnect();
        }
    }

    private static String streamToString(InputStream resultStream)
            throws IOException {
        BufferedReader aReader = new BufferedReader(
                new InputStreamReader(resultStream));
        StringBuffer aResponse = new StringBuffer();
        String aLine = aReader.readLine();
        while (aLine != null) {
            aResponse.append(aLine + "\n");
            aLine = aReader.readLine();
        }
        return aResponse.toString();

    }
}