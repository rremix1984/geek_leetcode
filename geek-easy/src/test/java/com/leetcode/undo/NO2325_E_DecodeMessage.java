/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;

/**
    (简单)
    2325. 解密消息
        给你字符串 key 和 message ，分别表示一个加密密钥和一段加密消息。解密 message 的步骤如下：
        使用 key 中 26 个英文小写字母第一次出现的顺序作为替换表中的字母 顺序 。
        将替换表与普通英文字母表对齐，形成对照表。
        按照对照表 替换 message 中的每个字母。
        空格 ' ' 保持不变。
        例如，key = "happy boy"（实际的加密密钥会包含字母表中每个字母 至少一次），据此，
        可以得到部分对照表（'h' -> 'a'、'a' -> 'b'、'p' -> 'c'、'y' -> 'd'、'b' -> 'e'、'o' -> 'f'）。
        返回解密后的消息。
    示例 1：
        输入：key = "the quick brown fox jumps over the lazy dog", message = "vkbs bs t suepuv"
        输出："this is a secret"
        解释：对照表如上图所示。
        提取 "the quick brown fox jumps over the lazy dog" 中每个字母的首次出现可以得到替换表。
    示例 2：
        输入：key = "eljuxhpwnyrdgtqkviszcfmabo", message = "zwx hnfx lqantp mnoeius ycgk vcnjrdb"
        输出："the five boxing wizards jump quickly"
        解释：对照表如上图所示。
        提取 "eljuxhpwnyrdgtqkviszcfmabo" 中每个字母的首次出现可以得到替换表。
*/
public class NO2325_E_DecodeMessage {

    @Test
    public void test() {
        assert "this is a secret".equals(
                decodeMessage("the quick brown fox jumps over the lazy dog", "vkbs bs t suepuv"));
        assert "the five boxing wizards jump quickly".equals(
                decodeMessage("eljuxhpwnyrdgtqkviszcfmabo","zwx hnfx lqantp mnoeius ycgk vcnjrdb"));
    }

    public String decodeMessage(String key, String message) {
        char[] map  = new char[26];
        int p = 'a';
        for (char c : key.toCharArray()) {
            if (c == ' ')
                continue;

            if (map[c - 'a'] == 0)
                map[c - 'a'] = (char)(p++);
        }

        StringBuilder ans = new StringBuilder();
        for (char c : message.toCharArray()) {
            char t = c;
            if (c != ' ')
                t = map[t - 'a'];

            ans.append(t);
        }
        return ans.toString();
    }

}
