/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [STRING]
    (简单)
    1576. 替换所有的问号
        给你一个仅包含小写英文字母和 '?' 字符的字符串 s，请你将所有的 '?'
        转换为若干小写字母，使最终的字符串不包含任何【连续重复】的字符。
        注意：你 不能 修改非 '?' 字符。
        题目测试用例保证 除 '?' 字符 之外，不存在连续重复的字符。
        在完成所有转换（可能无需转换）后返回最终的字符串。如果有多个解决方案，
        请返回其中任何一个。
        可以证明，在给定的约束条件下，答案总是存在的。
    示例 1：
        输入：s = "?zs"
        输出："azs"
        解释：该示例共有 25 种解决方案，从 "azs" 到 "yzs" 都是符合题目要求的。
             只有 "z" 是无效的修改，因为字符串 "zzs" 中有连续重复的两个 'z' 。
    示例 2：
        输入：s = "ubv?w"
        输出："ubvaw"
        解释：该示例共有 24 种解决方案，只有替换成 "v" 和 "w" 不符合题目要求。
             因为 "ubvvw" 和 "ubvww" 都包含连续重复的字符。
*/
public class NO1576_E_ModifyString_x2 {

    @Test
    public void test() {
        assert "azs".equals(modifyString("?zs"));
        assert "ubvaw".equals(modifyString("ubv?w"));
        assert "jaqgacb".equals(modifyString("j?qg??b"));
    }

    public String modifyString(String s) {
        char[] arr = s.toCharArray();
        return new String(arr);
    }

}

















/**
// 方法1：
public String modifyString(String s) {
    char[] arr = s.toCharArray();
    for (int i = 0; i < s.length(); i++) {
        if (arr[i] != '?')
            continue;

        // 任选 3 个连续的字符，就足够可以让'?'两侧不连续重复
        for (char ch = 'd'; ch <= 'f'; ch++) {
            // 当ch与【前一个】或【后一个】相同时，进行下一次判断
            if ((i > 0 && arr[i - 1] == ch)
                    || (i < s.length() - 1 && arr[i + 1] == ch))
                continue;

            // 替换值
            arr[i] = ch;
            break;
        }
    }
    return new String(arr);
}
*/