/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    884. 两句话中的不常见单词
        句子是一串由空格分隔的单词。每个单词仅由小写字母组成。
        如果某个单词在其中一个句子中恰好出现一次，在另一个句子中却没有出现，
        那么这个单词就是不常见的。给你两个句子s1和s2，
        返回所有不常用单词的列表。
        返回列表中单词可以按任意顺序组织。
    示例 1：
        输入：s1 = "this apple is sweet", s2 = "this apple is sour"
        输出：["sweet", "sour"]
    示例 2：
        输入：s1 = "apple apple", s2 = "banana"
        输出：["banana"]
    提示：
        1 <= s1.length, s2.length <= 200
        s1 和 s2 由小写英文字母和空格组成
        s1 和 s2 都不含前导或尾随空格
        s1 和 s2 中的所有单词间均由单个空格分隔
*/
public class NO884_E_UncommonFromSentences_x2 {

    @Test
    public void test() {
        assertArrayEquals(new String[]{"sweet","sour"},
                uncommonFromSentences("this apple is sweet", "this apple is sour"));
        assertArrayEquals(new String[]{"banana"},
                uncommonFromSentences("apple apple", "banana"));
    }

    public String[] uncommonFromSentences(String A, String B) {
        return null;
    }

}


















/**
// 方法1：
public String[] uncommonFromSentences(String A, String B) {
    //思路:遍历AB，map统计每个词的次数;遍历map，次数为1的为不常见单词
    Map<String, Integer> map = new HashMap<>();
    List<String> r = new ArrayList<>();
    for (String word : A.split(" "))
        map.put(word, map.getOrDefault(word, 0) + 1);

    for (String word : B.split(" "))
        map.put(word, map.getOrDefault(word, 0) + 1);

    // word只出现了一次
    for (String word : map.keySet())
        if(map.get(word) == 1)
            r.add(word);

    return r.toArray(new String[r.size()]);
}
*/