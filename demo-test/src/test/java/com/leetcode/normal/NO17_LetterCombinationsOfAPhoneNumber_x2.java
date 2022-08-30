/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.leetcode.util.LogUtil.info;

/**
    （中等）
    17. 电话号码的字母组合
        给定一个仅包含数字2-9的字符串，返回所有它能表示的字母组合。
        答案可以按 任意顺序 返回。给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
    示例 1：
        输入：digits = "23"
        输出：["ad", "ae", "af", "bd",
              "be", "bf", "cd", "ce", "cf"]
    示例 2：
        输入：digits = ""
        输出：[]
*/
@SuppressWarnings("all")
public class NO17_LetterCombinationsOfAPhoneNumber_x2 {

    @Test
    public void test() {
//        info(letterCombinations("23")); // ["ad","ae","af","bd","be","bf","cd","ce","cf"]
//        info(letterCombinations("1234")); // ["ad","ae","af","bd","be","bf","cd","ce","cf"]
        info(letterCombinations(""));// []
    }

    String[] map = { " ", "*", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };

    List<String> res = new ArrayList<>();

    // 用回溯法解决
    public List<String> letterCombinations(String str) {
        return res;
    }

}















/*
// 方案1 回溯法
String[] map = { " ", "*", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };
List<String> res = new ArrayList<>();

public List<String> letterCombinations(String str) {
    dfs(str, new StringBuilder(), 0);
    return res;
}

void dfs(String str, StringBuilder curStr, int index) {
    if (str == null || str.length() == 0)
        return;

    if (index == str.length()) {
        res.add(curStr.toString());
        return;
    }
    int pos = str.charAt(index) - '0';
    String map_string = map[pos];
    for (int i = 0; i < map_string.length(); i++) {
        curStr.append(map_string.charAt(i));
        dfs(str, curStr, index + 1);
        curStr.deleteCharAt(curStr.length() - 1);
    }
}
*/