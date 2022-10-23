/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.LogUtil;
import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    (简单)
    1047. 删除字符串中的所有相邻重复项
        给出由小写字母组成的字符串 S，重复项删除操作会选择两个相邻且相同的字母，并删除它们。
        在 S 上反复执行重复项删除操作，直到无法继续删除。
        在完成所有重复项删除操作后返回最终的字符串。答案保证唯一。
        示例：
            输入："abbaca"
            输出："ca"
        解释：
            例如，在 "abbaca" 中，我们可以删除 "bb" 由于两字母相邻且相同，这是此时唯一可以执行删除操作的重复项。
            之后我们得到字符串 "aaca"，其中又只有 "aa" 可以执行重复项删除操作，所以最后的字符串为 "ca"。
*/
public class NO1047_E_RemoveDuplicates_x2 {

    @Test
    public void test() {
        info(removeDuplicates("abbaca"));
        assert "ca".equals(removeDuplicates("abbaca"));
    }

    public String removeDuplicates(String s) {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

}

















/**
// 方法3：
public String removeDuplicates(String s) {
    StringBuilder sb = new StringBuilder();
    Deque<Character> stack = new LinkedList<>();
    for (char c : s.toCharArray())
        if (!stack.isEmpty() && c == stack.peek()) {
            stack.pop();
            sb.deleteCharAt(sb.length() - 1);
        } else {
            stack.push(c);
            sb.append(c);
        }

    return sb.toString();
}

// 方法2：
public String removeDuplicates(String s) {
    StringBuilder sb = new StringBuilder();
    int top = -1;
    for (char ch : s.toCharArray())
        if (top >= 0 && sb.charAt(top) == ch) {
            sb.deleteCharAt(top);
            --top;
        } else {
            sb.append(ch);
            ++top;
        }
    return sb.toString();
}
*/