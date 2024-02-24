/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import java.util.*;

import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.getArray;
import static java.lang.Character.isDigit;

/**
    [ARRAY] |
    (中等)
    784. 字母大小写全排列
        给定一个字符串 s ，通过将字符串 s 中的每个字母转变大小写，我们可以获得一个新的字符串。
        返回 所有可能得到的字符串集合 。以 任意顺序 返回输出。
    示例 1：
        输入：s = "a1b2"
        输出：["a1b2", "a1B2", "A1b2", "A1B2"]
    示例 2:
        输入: s = "3z4"
        输出: ["3z4","3Z4"]
    提示:
        1 <= s.length <= 12
        s 由小写英文字母、大写英文字母和数字组成
*/
public class NO784_N_LetterCasePermutation_x3 {

    @Test
    public void test() {
        assert getArray("a1b2", "a1B2", "A1b2", "A1B2")
                .containsAll(letterCasePermutation("a1b2"));
        assert getArray("3z4","3Z4")
                .containsAll(letterCasePermutation("3z4"));
    }

    public List<String> letterCasePermutation(String s) {
        // 2024/2/24 NO.3
        List<String> ans = new ArrayList<>();
        return ans;
    }

}


















/*
// 方法1：
public List<String> letterCasePermutation(String s) {
    List<String> ans = new ArrayList<>();
    Queue<StringBuilder> queue = new LinkedList<>();
    queue.offer(new StringBuilder());
    while (!queue.isEmpty()) {
        StringBuilder curr = queue.peek();
        if (curr.length() == s.length()) {
            ans.add(curr.toString());
            queue.poll();
        } else {
            int pos = curr.length();
            if (Character.isLetter(s.charAt(pos))) {
                StringBuilder next = new StringBuilder(curr);
                next.append((char) (s.charAt(pos) ^ 32));
                queue.offer(next);
            }
            curr.append(s.charAt(pos));
        }
    }
    return ans;
}

// 方法2：
public List<String> letterCasePermutation(String s) {
    List<String> ans = new ArrayList<>();
    dfs(s.toCharArray(), 0, ans);
    return ans;
}

public void dfs(char[] arr, int pos, List<String> res) {
    // 1. 先跳过所有的数字
    while (pos < arr.length && isDigit(arr[pos]))
        pos++;

    // 2. 如果已遍历完了，就把arr加到最终结果集上，并返回
    if (pos == arr.length) {
        res.add(new String(arr));
        return;
    }

    // 第一次异或相当于 + 加法，ASCII码中大、小写字母差32
    // 'a' ^ 32 == 'A'
    // 'A' ^ 32 == 'a'
    arr[pos] ^= 32;
    // 3. 先变换大小写，位置往后走一个，然后传入下一层
    dfs(arr, pos + 1, res);

    // 再次异或相当于 减法
    arr[pos] ^= 32;
    // 4. 还原回去，位置往后走一个，再传入下一层
    dfs(arr, pos + 1, res);
}
*/