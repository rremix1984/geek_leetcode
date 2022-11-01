/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;

/**
    (简单)
    1408. 数组中的字符串匹配
        给你一个字符串数组 words ，数组中的每个字符串都可以看作是一个单词。请你按 任意 顺序返回 words 中是其他单词的子字符串的所有单词。
        如果你可以删除 words[j] 最左侧和/或最右侧的若干字符得到 words[i] ，那么字符串 words[i] 就是 words[j] 的一个子字符串。
    示例 1：
        输入：words = {"mass","as","hero","superhero"}
        输出：{"as","hero"}
        解释："as" 是 "mass" 的子字符串，"hero" 是 "superhero" 的子字符串。
        {"hero","as"} 也是有效的答案。
    示例 2：
        输入：words = {"leetcode","et","code"}
        输出：{"et","code"}
        解释："et" 和 "code" 都是 "leetcode" 的子字符串。
    示例 3：
        输入：words = {"blue","green","bu"}
        输出：{}
*/
public class NO1408_E_StringMatching_x2 {

    @Test
    public void test() {
        assert getArray("as", "hero").equals(stringMatching(new String[]{"mass","as","hero","superhero"}));
        assert getArray("et", "code").equals(stringMatching(new String[]{"leetcode","et","code"}));
        assert Collections.emptyList().equals(stringMatching(new String[]{"blue","green","bu"}));
    }

    public List<String> stringMatching(String[] words) {
        List<String> ret = new ArrayList<>();
        return ret;
    }

}

















/**
public List<String> stringMatching(String[] words) {
    List<String> ret = new ArrayList<>();
    for (int i = 0; i < words.length; i++)
        for (int j = 0; j < words.length; j++)
            if (i != j && words[j].contains(words[i])) {
                ret.add(words[i]);
                break;
            }

    return ret;
}
*/