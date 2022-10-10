/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    1859. 将句子排序
        一个 句子 指的是一个序列的单词用单个空格连接起来，且开头和结尾没有任何空格。每个单词都只包含小写或大写英文字母。
        我们可以给一个句子添加 从 1 开始的单词位置索引 ，并且将句子中所有单词 打乱顺序 。
        比方说，句子 "This is a sentence" 可以被打乱顺序得到 "sentence4 a3 is2 This1" 或者 "is2 sentence4 This1 a3" 。
        给你一个 打乱顺序 的句子 s ，它包含的单词不超过 9 个，请你重新构造并得到原本顺序的句子。
    示例 1：
        输入：s = "is2 sentence4 This1 a3"
        输出："This is a sentence"
        解释：将 s 中的单词按照初始位置排序，得到 "This1 is2 a3 sentence4" ，然后删除数字。
    示例 2：
        输入：s = "Myself2 Me1 I4 and3"
        输出："Me Myself and I"
        解释：将 s 中的单词按照初始位置排序，得到 "Me1 Myself2 and3 I4" ，然后删除数字。
*/
public class NO1859_E_SortSentence_x2 {

    @Test
    public void test() {
        assert "This is a sentence".equals(sortSentence("is2 sentence4 This1 a3"));
        assert "Me Myself and I".equals(sortSentence("Myself2 Me1 I4 and3"));
    }

    public String sortSentence(String s) {
        return null;
    }

}














/**
public String sortSentence(String s) {
    StringJoiner joiner = new StringJoiner(" ");
    for (int i = 0; i < s.length(); i++)
        for (String value : s.split(" "))
            // 找最后一位数字，匹配的字符串
            if ((value.charAt(value.length() - 1) - '0') == i)
                // 不带数字
                joiner.add(value.substring(0, value.length() - 1));

    return joiner.toString();
}
*/