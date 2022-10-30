/**
 * copyright 2022/1/19
 */
package com.leetcode.donnot;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.leetcode.util.MathUtils.getArray;
import static java.lang.Math.min;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    1002. 查找共用字符
        给你一个字符串数组words，请你找出所有在words的每个
        字符串中都出现的共用字符（包括重复字符），并以数组形式返回。
        你可以按任意顺序返回答案。
    示例 1：
        输入：words = ["bella", "label", "roller"]
        输出：["e", "l", "l"]
    示例 2：
        输入：words = ["cool", "lock", "cook"]
        输出：["c", "o"]
    提示：
        1 <= words.length <= 100
        1 <= words[i].length <= 100
        words[i] 由小写英文字母组成
*/
public class NO1002_E_CommonChars {

    @Test
    public void test() {
        assert getArray("e", "l", "l").equals(
                commonChars(new String[]{"bella", "label", "roller"}));
        assert getArray("c", "o").equals(
                commonChars(new String[]{"cool", "lock", "cook"}));
    }


    public List<String> commonChars(String[] A) {
        List<String> result = new ArrayList<>();
        if (A.length == 0)
            return result;

        // 用来统计所有字符串里字符出现的最小频率
        int[] hash = new int[26];

        // 用第一个字符串给hash初始化
        for (int i = 0; i < A[0].length(); i++)
            hash[A[0].charAt(i)- 'a']++;

        // 统计除第一个字符串外字符的出现频率
        for (int i = 1; i < A.length; i++) {
            int[] str = new int[26];
            for (int j = 0; j < A[i].length(); j++)
                str[A[i].charAt(j)- 'a']++;

            // 更新hash，保证hash里统计26个字符在所有字符串里出现的最小次数
            for (int k = 0; k < 26; k++)
                hash[k] = min(hash[k], str[k]);

        }
        // 将hash统计的字符次数，转成输出形式
        for (int i = 0; i < 26; i++)
            // 注意这里是while，多个重复的字符
            while (hash[i] != 0) {
                char c = (char) (i+'a');
                result.add(String.valueOf(c));
                hash[i]--;
            }

        return result;
    }

}


















/**
// 方法1：
public List<String> commonChars(String[] A) {
    List<String> result = new ArrayList<>();
    if (A.length == 0)
        return result;

    // 用来统计所有字符串里字符出现的最小频率
    int[] hash= new int[26];

    // 用第一个字符串给hash初始化
    for (int i = 0; i < A[0].length(); i++)
        hash[A[0].charAt(i)- 'a']++;

    // 统计除第一个字符串外字符的出现频率
    for (int i = 1; i < A.length; i++) {
        int[] hashOtherStr= new int[26];
        for (int j = 0; j < A[i].length(); j++)
            hashOtherStr[A[i].charAt(j)- 'a']++;

        // 更新hash，保证hash里统计26个字符在所有字符串里出现的最小次数
        for (int k = 0; k < 26; k++)
            hash[k] = Math.min(hash[k], hashOtherStr[k]);

    }
    // 将hash统计的字符次数，转成输出形式
    for (int i = 0; i < 26; i++)
        // 注意这里是while，多个重复的字符
        while (hash[i] != 0) {
            char c= (char) (i+'a');
            result.add(String.valueOf(c));
            hash[i]--;
        }

    return result;
}


// 方法2：
public List<String> commonChars(String[] A) {
    int[][] tab = new int[A.length][26];
    for (int i = 0; i < A.length; i++)
        for (int j = 0; j < A[i].length(); j++)
            tab[i][A[i].charAt(j) - 'a']++;

    List<String> ans = new ArrayList<>();
    for (int i = 0; i < 26; i++) {
        int min = A.length;
        for (int j = 0; j < A.length; j++)
            min = min(min, tab[j][i]);

        for (int k = 0; k < min; k++)
            ans.add("" + (char)(i + 'a'));
    }
    return ans;
}
*/