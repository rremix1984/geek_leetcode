package com.leetcode.normal;

import java.util.ArrayList;
import java.util.List;

/**
 * NO.784. 字母大小写全排列
 * 给定一个字符串S，通过将字符串S中的每个字母转变大小写，我们可以获得一个新的字符串。返回所有可能得到的字符串集合。
 */
public class NO784_N_LetterCasePermutation {

    public List<String> letterCasePermutation(String S) {
        List<String> result = new ArrayList<>();
        if (S == null) {
            return result;
        }
        backtrack(result, S.toCharArray(), 0);
        return result;
    }

    private void backtrack(List<String> result, char[] chars, int index) {
        if (index == chars.length) {
            result.add(new String(chars));
            return;
        }

        backtrack(result, chars, index + 1);

        if (Character.isLetter(chars[index])) {
            chars[index] = toggleCase(chars[index]);
            backtrack(result, chars, index + 1);
            chars[index] = toggleCase(chars[index]); // backtrack
        }
    }

    private char toggleCase(char c) {
        if (Character.isUpperCase(c)) {
            return Character.toLowerCase(c);
        } else {
            return Character.toUpperCase(c);
        }
    }
}