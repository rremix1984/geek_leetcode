/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import static com.leetcode.util.LogUtil.info;
import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;
import static org.junit.Assert.assertEquals;

/**
    (简单)
    2129. 将标题首字母大写
        给你一个字符串title，它由单个空格连接一个或多个单词组成，
        每个单词都只包含英文字母。请你按以下规则将每个单词的首字母大写：
        如果单词的长度为1或者2，所有字母变成小写。
        否则，将单词首字母大写，剩余字母变成小写。
        请你返回大写后的title。
    示例 1：
        输入：title = "capiTalIze tHe titLe"
        输出："Capitalize The Title"
        解释：由于所有单词的长度都至少为3，将每个单词首字母大写，剩余字母变为小写。
    示例 2：
        输入：title = "First leTTeR of EACH Word"
        输出："First Letter of Each Word"
        解释：单词"of"长度为2，所以它保持完全小写。
             其他单词长度都至少为3，所以其他单词首字母大写，剩余字母小写。
    示例 3：
        输入：title = "i lOve leetcode"
        输出："i Love Leetcode"
        解释：单词"i"长度为1，所以它保留小写。
             其他单词长度都至少为3，所以其他单词首字母大写，剩余字母小写。
    方法1：
       通过一个指针first标记字母的第一位，然后遍历整个字符数组并将其转换为小写，
    记录连续非空字符长度count，每当遇到‘ ’时，根据count的大小判断改字符串的
    首字母是否要大写，当遍历到数组的末尾也要判断该字符是否要大写。
*/
public class NO2129_E_CapitalizeTitle_x2 {

    @Test
    public void test() {
        assertEquals("Capitalize The Title",
                capitalizeTitle("capiTalIze tHe titLe"));
        assertEquals("First Letter of Each Word",
                capitalizeTitle("First leTTeR of EACH Word"));
        assertEquals("i Love Leetcode",
                capitalizeTitle("i lOve leetcode"));
    }

    public String capitalizeTitle(String title) {
        char[] arr = title.toCharArray();
        return new String(arr);
    }

}















/**
// 方法1：
public String capitalizeTitle(String title){
    char[] arr = title.toCharArray();
    int capital = 0;
    int len = 0;
    for (int i = 0; i < arr.length; i++) {
        // 1.遇到的不是空格
        if (arr[i] != ' ') {
            // 第一步，全部转转为小写字母
            arr[i] = toLowerCase(arr[i]);
            // 计数器自增
            len++;
            //判断首字母是否要转换为大写
            // 2.遇到空格下一个大写字符（first）就是 i + 1
        } else {
            // 如果连续字符长度，超过2
            // 首字符 capital 转大写
            if (len > 2)
                arr[capital] = toUpperCase(arr[capital]);
            // 计数器归0
            len = 0;
            // 重新计算 capital 的位置
            capital = i + 1;
        }

        // 最后一个
        if (i == arr.length - 1 && len > 2)
            arr[capital] = toUpperCase(arr[capital]);
    }
    return new String(arr);
}
*/