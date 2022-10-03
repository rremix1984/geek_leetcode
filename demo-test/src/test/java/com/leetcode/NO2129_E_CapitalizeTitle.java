/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
    (简单)
    2129. 将标题首字母大写
        给你一个字符串 title ，它由单个空格连接一个或多个单词组成，每个单词都只包含英文字母。请你按以下规则将每个单词的首字母 大写 ：
        如果单词的长度为 1 或者 2 ，所有字母变成小写。
        否则，将单词首字母大写，剩余字母变成小写。
        请你返回 大写后 的 title 。
    示例 1：
        输入：title = "capiTalIze tHe titLe"
        输出："Capitalize The Title"
        解释：由于所有单词的长度都至少为 3 ，将每个单词首字母大写，剩余字母变为小写。
    示例 2：
        输入：title = "First leTTeR of EACH Word"
        输出："First Letter of Each Word"
        解释：单词 "of" 长度为 2 ，所以它保持完全小写。
             其他单词长度都至少为 3 ，所以其他单词首字母大写，剩余字母小写。
    示例 3：
        输入：title = "i lOve leetcode"
        输出："i Love Leetcode"
        解释：单词 "i" 长度为 1 ，所以它保留小写。
             其他单词长度都至少为 3 ，所以其他单词首字母大写，剩余字母小写。
*/
public class NO2129_E_CapitalizeTitle {

    @Test
    public void test() {
        assertEquals("Capitalize The Title", capitalizeTitle("capiTalIze tHe titLe"));
        assertEquals("First Letter of Each Word", capitalizeTitle("First leTTeR of EACH Word"));
        assertEquals("i Love Leetcode", capitalizeTitle("i lOve leetcode"));
    }

    public String capitalizeTitle(String title){
        char[] c = title.toCharArray();
        return new String(c);
    }

}















/**
// 方法1：
public String capitalizeTitle(String title){
    char[] c = title.toCharArray();
    int first = 0;
    int count = 0;
    for (int i = 0; i < c.length; i++) {
        //判断首字母是否要转换为大写
        if (c[i] == ' ') {
            if (count > 2)
                c[first] = (char)(c[first] - 32);
            count = 0;
            first = i + 1;
        } else {
            //将所有字母转换成小写
            count++;
            if (c[i] < 'a')
                c[i] = (char)(c[i] + 32);
        }
        if (i == c.length - 1 && count > 2)
            c[first] = (char)(c[first] - 32);
    }
    return new String(c);
}
*/