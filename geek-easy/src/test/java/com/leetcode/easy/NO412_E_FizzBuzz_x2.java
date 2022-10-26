/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.leetcode.util.MathUtils.getArray;

/**
    (简单)
    412. Fizz Buzz
        给你一个整数 n ，找出从 1 到 n 各个整数的 Fizz Buzz 表示，
    并用字符串数组 answer（下标从 1 开始）返回结果，其中：
        1）answer[i] == "FizzBuzz" 如果 i 同时是 3 和 5 的倍数。
        2）answer[i] == "Fizz" 如果 i 是 3 的倍数。
        3）answer[i] == "Buzz" 如果 i 是 5 的倍数。
        4）answer[i] == i （以字符串形式）如果上述条件全不满足。
    示例 1：
        输入：n = 3
        输出：{"1", "2", "Fizz"}
    示例 2：
        输入：n = 5
        输出：{"1", "2", "Fizz", "4", "Buzz"}
    示例 3：
        输入：n = 15
        输出：{"1", "2", "Fizz", "4", "Buzz", "Fizz", "7",
            "8", "Fizz", "Buzz", "11", "Fizz", "13", "14", "FizzBuzz"}
    提示：
        1 <= n <= 104
*/
public class NO412_E_FizzBuzz_x2 {

    @Test
    public void test() {
        assert getArray("1", "2", "Fizz").equals(fizzBuzz(3));
        assert getArray("1", "2", "Fizz", "4", "Buzz").equals(fizzBuzz(5));
        assert getArray("1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz",
                "11", "Fizz", "13", "14", "FizzBuzz").equals(fizzBuzz(15));
    }

    public List<String> fizzBuzz(int n) {
        List<String> list = new ArrayList<>();
        return list;
    }

}


















/**
// 方法1：
public List<String> fizzBuzz(int n) {
    List<String> list = new ArrayList<>();
    for (int i = 1; i <= n; i++) {
        String cur = "";
        if (i % 3 == 0)
            cur += "Fizz";

        if (i % 5 == 0)
            cur += "Buzz";

        if (cur.length() == 0)
            cur += i;

        list.add(cur);
    }
    return list;
}
*/