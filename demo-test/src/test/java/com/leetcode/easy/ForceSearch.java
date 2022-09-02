/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

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
        info(forceSearch("BBC ABCDAB ABCDABCDABDE", "ABCDABCD"));// 11
    }

    public int forceSearch(String txt, String pat) {
        return -1;
    }

}
















/**
// 方法1：
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
*/