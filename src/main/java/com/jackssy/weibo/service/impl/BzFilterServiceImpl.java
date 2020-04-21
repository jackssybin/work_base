package com.jackssy.weibo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jackssy.weibo.entity.BzFilter;
import com.jackssy.weibo.mapper.BzFilterMapper;
import com.jackssy.weibo.service.BzFilterService;
import com.jackssy.weibo.util.SensitiveWordInit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lh
 * @description
 * @date 2020/3/29 0029 20:00
 */
@Service
@Slf4j
@Transactional
public class BzFilterServiceImpl extends ServiceImpl<BzFilterMapper, BzFilter> implements BzFilterService {

    @Autowired
    SensitiveWordInit sensitiveWordInit;

    private static int minMatchTYpe = 1;      //最小匹配规则
    private static int maxMatchType = 2;      //最大匹配规则

    @Override
    public List<BzFilter> listIsUse() {
        QueryWrapper<BzFilter> wrapper = new QueryWrapper();
        wrapper.eq("is_use",1);
        return this.list(wrapper);
    }

    @Override
    public List<String> getContainsFilterContent(String text) {
        Map sensitiveWordMap = sensitiveWordInit.initKeyWord();
        List<String> sensitiveWordList = getSensitiveWord(text, 2,sensitiveWordMap);
        return sensitiveWordList;
    }

    /**
     * 获取文字中的敏感词
     * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     */
    public List<String> getSensitiveWord(String txt , int matchType,Map sensitiveWordMap){
        List<String> sensitiveWordList = new ArrayList<>();
        for(int i = 0 ; i < txt.length() ; i++){
            int length = CheckSensitiveWord(txt, i, matchType,sensitiveWordMap);    //判断是否包含敏感字符
            if(length > 0){    //存在,加入list中
                sensitiveWordList.add(txt.substring(i, i+length));
                i = i + length - 1;    //减1的原因，是因为for会自增
            }
        }
        return sensitiveWordList;
    }
    @SuppressWarnings({ "rawtypes"})
    public int CheckSensitiveWord(String txt,int beginIndex,int matchType,Map sensitiveWordMap){
        boolean  flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
        int matchFlag = 0;     //匹配标识数默认为0
        char word = 0;
        Map<String,Object> nowMap = sensitiveWordMap;
        for(int i = beginIndex; i < txt.length() ; i++){
            word = txt.charAt(i);
            nowMap = (Map) nowMap.get(word);     //获取指定key
            if(nowMap != null){     //存在，则判断是否为最后一个
                matchFlag++;     //找到相应key，匹配标识+1
                if("1".equals(nowMap.get("isEnd"))){       //如果为最后一个匹配规则,结束循环，返回匹配标识数
                    flag = true;       //结束标志位为true
                    if(minMatchTYpe == matchType){    //最小规则，直接返回,最大规则还需继续查找
                        break;
                    }
                }
            }
            else{     //不存在，直接返回
                break;
            }
        }
        if(matchFlag < 2 || !flag){        //长度必须大于等于1，为词
            matchFlag = 0;
        }
        return matchFlag;
    }




}
