/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    1323. 6 和 9 组成的最大数字
        给你一个仅由数字 6 和 9 组成的正整数 num。
        你最多只能翻转一位数字，将 6 变成 9，或者把 9 变成 6 。
        请返回你可以得到的最大数字。
    示例 1：
        输入：num = 9669
        输出：9969
        解释：改变第一位数字可以得到 6669 。
             改变第二位数字可以得到 9969 。
             改变第三位数字可以得到 9699 。
             改变第四位数字可以得到 9666 。
             其中最大的数字是 9969 。
    示例 2：
        输入：num = 9996
        输出：9999
        解释：将最后一位从 6 变到 9，其结果 9999 是最大的数。
    示例 3：
        输入：num = 9999
        输出：9999
        解释：无需改变就已经是最大的数字了。

    解题思路
        根据题意,把左边出现的第一个6变成9就行啦.
*/
public class NO1323_E_Maximum69Number_x2 {

    @Test
    public void test() {
        assert 9969 == maximum69Number(9669);
        assert 9999 == maximum69Number(9996);
        assert 9999 == maximum69Number(9999);
    }

    public int maximum69Number(int num) {
        return -1;
    }

}
















/**
// 方法1：
public int maximum69Number(int num) {
    String s = Integer.toString(num);
    char[] ch = s.toCharArray();
    for(int i = 0; i < ch.length; i++){
        if(ch[i] == '6'){
            ch[i] = '9';
            break;
        }
    }
    return Integer.parseInt(new String(ch));
}


// 方法2:
public int maximum69Number(int num) {
    String s = String.valueOf(num);
    return Integer.parseInt(s.replaceFirst("6", "9"));
}
*/