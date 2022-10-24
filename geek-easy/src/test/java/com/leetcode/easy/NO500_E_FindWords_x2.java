/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.*;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    500. 键盘行
        给你一个字符串数组 words ，只返回可以使用在【美式键盘】同一行的字母打印出来的单词。
        键盘如下图所示。美式键盘中：
            第一行由字符 "qwertyuiop" 组成。
            第二行由字符 "asdfghjkl" 组成。
            第三行由字符 "zxcvbnm" 组成。
    示例 1：
        输入：words = {"Hello", "Alaska", "Dad", "Peace"}
        输出：{"Alaska", "Dad"}
    示例 2：
        输入：words = {"omk"}
        输出：{}
    示例 3：
        输入：words = {"adsdf", "sfd"}
        输出：{"adsdf", "sfd"}
    提示：
        1 <= words.length <= 20
        1 <= words[i].length <= 100
        words[i] 由英文字母（小写和大写字母）组成
*/
public class NO500_E_FindWords_x2 {

    @Test
    public void test() {
        assertArrayEquals(new String[]{"Alaska", "Dad"},
                findWords(new String[]{"Hello", "Alaska", "Dad", "Peace"}));
        assertArrayEquals(new String[]{},
                findWords(new String[]{"omk"}));
        assertArrayEquals(new String[]{"adsdf", "sfd"},
                findWords(new String[]{"adsdf", "sfd"}));
    }

    static String s1 = "qwertyuiop";
    static String s2 = "asdfghkjl";
    static String s3 = "zxcvbnm";
    public String[] findWords(String[] words) {
        List<String> list = new ArrayList<>();
        return list.toArray(new String[0]);
    }

}


















/**
// 方法1：
public String[] findWords(String[] words) {
    List<String> list = new ArrayList<>();
    for (String word : words) {
        int row = map.get(word.toLowerCase().charAt(0));
        for (int i = 0; i < word.length(); i++) {
            if (row != map.get(word.toLowerCase().charAt(i)))
                break;

            if (i == word.length() - 1)
                list.add(word);
        }
    }
    return list.toArray(new String[0]);
}
*/