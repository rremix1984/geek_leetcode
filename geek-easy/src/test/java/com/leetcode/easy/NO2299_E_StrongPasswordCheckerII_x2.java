/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static java.lang.Character.*;

/**
    [STRING]
    (简单)
    2299. 强密码检验器 II
        如果一个密码满足以下所有条件，我们称它是一个 强 密码：
        它有至少 8 个字符。
            至少包含 一个小写英文 字母。
            至少包含 一个大写英文 字母。
            至少包含 一个数字 。
            至少包含 一个特殊字符 。特殊字符为："!@#$%^&*()-+" 中的一个。
            它不包含2个连续相同的字符（比方说 "aab" 不符合该条件，但是 "aba" 符合该条件）。
        给你一个字符串 password ，如果它是一个 强 密码，返回 true，否则返回 false 。
    示例 1：
        输入：password = "IloveLe3tcode!"
        输出：true
        解释：密码满足所有的要求，所以我们返回 true 。
    示例 2：
        输入：password = "Me+You--IsMyDream"
        输出：false
        解释：密码不包含数字，且包含 2 个连续相同的字符。所以我们返回 false 。
    示例 3：
        输入：password = "1aB!"
        输出：false
        解释：密码不符合长度要求。所以我们返回 false 。
*/
@SuppressWarnings("all")
public class NO2299_E_StrongPasswordCheckerII_x2 {

    @Test
    public void test() {
        assert strongPasswordCheckerII("IloveLe3tcode!");
        assert !strongPasswordCheckerII("Me+You--IsMyDream");
        assert !strongPasswordCheckerII("1aB!");
    }

    public boolean strongPasswordCheckerII(String password) {
        if (password.length() < 8)
            return false; // 至少8字符

        int ans = 0;
        for (int i = 0; i < password.length(); i++) {
            // 连续2个，前后不相等
            if (i > 0 && password.charAt(i) == password.charAt(i - 1))
                return false;

            if (isDigit(password.charAt(i)))// 数字
                ans |= 1;
            else if (isLowerCase(password.charAt(i)))// 小写
                ans |= 2;
            else if (isUpperCase(password.charAt(i)))// 大写
                ans |= 4;
            else // 特殊字符
                ans |= 8;
        }
        return ans == 15;
    }

}
