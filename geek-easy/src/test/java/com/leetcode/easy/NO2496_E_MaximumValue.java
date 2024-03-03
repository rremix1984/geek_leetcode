package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY]
    （简单）
    NO.2496 数组中字符串的最大值
    一个由字母和数字组成的字符串的 值 定义如下：
    如果字符串 只 包含数字，那么值为该字符串在 10 进制下的所表示的数字。
    否则，值为字符串的 长度 。
    给你一个字符串数组 strs ，每个字符串都只由字母和数字组成，
    请你返回 strs 中字符串的 最大值 。

    示例 1：
        输入：strs = ["alic3","bob","3","4","00000"]
        输出：5
        解释：
            - "alic3" 包含字母和数字，所以值为长度 5 。
            - "bob" 只包含字母，所以值为长度 3 。
            - "3" 只包含数字，所以值为 3 。
            - "4" 只包含数字，所以值为 4 。
            - "00000" 只包含数字，所以值为 0 。
        所以最大的值为 5 ，是字符串 "alic3" 的值。

    示例 2：
        输入：strs = ["1","01","001","0001"]
        输出：1
        解释：
        数组中所有字符串的值都是 1 ，所以我们返回 1 。
        提示：
            1 <= strs.length <= 100
            1 <= strs[i].length <= 9
        strs[i] 只包含小写英文字母和数字。

    Related Topics: 数组,字符串
*/
public class NO2496_E_MaximumValue {

    @Test
    public void test() {
        assert 5 == maximumValue(
            new String[]{"alic3", "bob", "3", "4", "00000"});
    }

    public int maximumValue(String[] strs) {
        int i=0;
        int j=0;
        int max=0;
        for (i = 0; i < strs.length; i++) {
            for (j = 0; j < strs[i].length(); j++) {
                if(strs[i].charAt(j)<'0'||strs[i].charAt(j)>'9'){
                    if (strs[i].length()>max) {
                        max=strs[i].length();
                    }
                    break;
                }
            }
            if(j==strs[i].length()){
                int num=0;
                for(int k=0;k<strs[i].length();k++){
                    num=num*10+(int)(strs[i].charAt(k)-'0');
                }
                if(num>max){
                    max=num;
                }
            }
        }
        return max;
    }

}


















/*
// 方法1：
public int maximumValue(String[] strs) {
    int i=0;
    int j=0;
    int max=0;
    for (i = 0; i < strs.length; i++) {
        for (j = 0; j < strs[i].length(); j++) {
            if(strs[i].charAt(j)<'0'||strs[i].charAt(j)>'9'){
                if (strs[i].length()>max) {
                    max=strs[i].length();
                }
                break;
            }
        }
        if(j==strs[i].length()){
            int num=0;
            for(int k=0;k<strs[i].length();k++){
                num=num*10+(int)(strs[i].charAt(k)-'0');
            }
            if(num>max){
                max=num;
            }
        }
    }
    return max;
}
*/