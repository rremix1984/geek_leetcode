/**
 * copyright 2022/1/19
 */
package com.leetcode.interval;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
  （简单）
   字符串匹配的 KMP 算法
   字符串（"BBC ABCDAB ABCDABCDABDE"）是否包含（"ABCDABD"）
 */
public class ForceSearch {

    @Test
    public void test() {
        assert 11 == forceSearch("BBC ABCDAB ABCDABCDABDE", "ABCDABCD");// 11
        assert 7 == forceSearch("ABCABCAABCABCD","ABCABCD");
    }

    public int forceSearch(String txt, String pat) {
        int M = txt.length();
        int N = pat.length();
        for (int i = 0; i <= M - N; i++) {
            int j = 0;
            while (j < N) {
                if (txt.charAt(i + j) != pat.charAt(j))
                    break;
                j++;
            }

            if (j == N)
                return i;
        }
        return -1;
    }
}
















/**
// 方法1：暴力算法
public int forceSearch(String txt, String pat) {
    int M = txt.length();
    int N = pat.length();
    for (int i = 0; i <= M - N; i++) {
        int j = 0;
        while (j < N) {
            if (txt.charAt(i + j) != pat.charAt(j))
                break;
            j++;
        }

        if (j == N)
            return i;
    }
    return -1;
}


// 方法2：KMP算法（有瑕疵，不正确）
public int forceSearch(String txt, String pat) {
    int[] next = new int[pat.length()];
    getNext(pat.toCharArray(), next);
    return search(txt.toCharArray(), pat.toCharArray(), next);
}

public int search(char[] str, char[] pattern, int[] next) {
    int i = 0, j = 0;
    while (i < str.length && j < pattern.length) {
        if (j == -1 || str[i] == pattern[j]) {
            i++;
            j++;
        } else
            j = next[j];
    }
    if (j == pattern.length)
        return i - j;
    else
        return -1;
}

public void getNext(char[] pattern, int[] next) {
    next[0] = -1;
    int i = 0, j = -1;
    while (i < pattern.length) {
        if (j == -1) {
            i++;
            j++;
        } else if (pattern[i] == pattern[j]) {
            i++;
            j++;
            next[i] = j;
        } else {
            j = next[j];
        }
    }
}
*/