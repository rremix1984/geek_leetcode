/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;

/**
    (简单)
    1933. 判断字符串是否可分解为值均等的子串
    示例 1：
        输入: s = "000111000"
        输出: false
        解释:  s只能被分解长度为3的等值子字符串。
    示例 2：
        输入: s = "00011111222"
        输出: true
        解释: s 能被分解为 ["000","111","11","222"].
    示例 3：
        输入: s = "01110002223300"
        输出: false
        解释: 一个不能被分解的原因是在开头有一个0.
    提示:
        1 <= s.length <= 1000
        s 仅包含数字。
*/
public class NO1933_E_IsDecomposable {

    @Test
    public void test() {
        assert !isDecomposable("000111000");
        assert isDecomposable("00011111222");
        assert !isDecomposable("01110002223300");
    }

    public boolean isDecomposable(String s) {
        if (s.length() % 3 != 2)
            return false;

        int cnt = 0;
        for (int i = 0; i < s.length(); i++) {
            int j = i;
            while (j < s.length() && s.charAt(j) == s.charAt(i))
                j++;

            if ((j - i) % 3 == 1)
                return false;

            if ((j - i) % 3 == 2)
                cnt++;

            if (cnt > 1)
                return false;

            i = j - 1;
        }
        return cnt == 1;
    }

}
