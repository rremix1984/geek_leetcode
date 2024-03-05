/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [STRING]
    (简单)
    925. 长按键入
        你的朋友正在使用键盘输入他的名字name。偶尔，在键入字符c时，
        按键可能会被长按，而字符可能被输入1次或多次。你将会检查键盘输入的字符typed。
        如果它对应的可能是你的朋友的名字（其中一些字符可能被长按），那么就返回 True。
    示例 1：
        输入：name = "alex", typed = "aaleex"
        输出：true
        解释：'alex' 中的 'a' 和 'e' 被长按。
    示例 2：
        输入：name = "saeed", typed = "ssaaedd"
        输出：false
        解释：'e' 一定需要被键入两次，但在 typed 的输出中不是这样。
    提示：
        1 <= name.length, typed.length <= 1000
        name 和 typed 的字符都是小写字母
*/
public class NO925_E_IsLongPressedName_x2 {

    @Test
    public void test() {
        assert isLongPressedName("alex","aaleex");
        assert !isLongPressedName("saeed","ssaaedd");
    }

    public boolean isLongPressedName(String name, String typed) {
        int l = 0;
        return l == name.length();
    }

}


















/**
// 方法1：
public boolean isLongPressedName(String name, String typed) {
    int l = 0;
    int r = 0;
    while (r < typed.length())
        // 1、如果两个值相等，那么快、慢指针一起向后走
        if (l < name.length() && name.charAt(l) == typed.charAt(r)) {
            l++;
            r++;
        // 2、如果当前值和上一个相等，快指针加一步，慢指针不动
        } else if (r > 0 && typed.charAt(r) == typed.charAt(r - 1)) {
            r++;
        // 两个值不相等，也跟上一个不相等，那代表不对
        } else {
            return false;
        }
    // 慢指针走到头了，代表结束了
    return l == name.length();
}*/
