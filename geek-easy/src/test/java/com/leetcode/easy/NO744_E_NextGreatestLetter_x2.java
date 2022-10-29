/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    744. 寻找比目标字母大的最小字母
        给你一个排序后的字符列表 letters ，列表中只包含小写英文字母。
        另给出一个目标字母 target，请你寻找在这一有序列表里比目标字母大的最小字母。
        在比较时，字母是依序循环出现的。举个例子：
        如果目标字母 target = 'z' 并且字符列表为 letters = {'a', 'b'}，则答案返回 'a'
    示例 1：
        输入: letters = {'c', 'f', 'j'}，target = 'a'
        输出: 'c'
    示例 2:
        输入: letters = {'c', 'f', 'j'}, target = 'c'
        输出: 'f'
    示例 3:
        输入: letters = {'c', 'f', 'j'}, target = 'd'
        输出: 'f'
    提示：
        2 <= letters.length <= 104
        letters[i] 是一个小写字母
        letters 按非递减顺序排序
        letters 最少包含两个不同的字母
        target 是一个小写字母
*/
public class NO744_E_NextGreatestLetter_x2 {

    @Test
    public void test() {
        assert 'c' == nextGreatestLetter(new char[]{'c','f','j'}, 'a');
        assert 'f' == nextGreatestLetter(new char[]{'c','f','j'}, 'c');
        assert 'f' == nextGreatestLetter(new char[]{'c','f','j'}, 'd');
    }

    public char nextGreatestLetter(char[] letters, char target) {
        return letters[0];
    }

}


















/**
// 方法1：
public char nextGreatestLetter(char[] letters, char target) {
    int length = letters.length;
    if (target >= letters[length - 1]) {
        return letters[0];
    }
    int low = 0, high = length - 1;
    while (low < high) {
        int mid = (high - low) / 2 + low;
        if (letters[mid] > target) {
            high = mid;
        } else {
            low = mid + 1;
        }
    }
    return letters[low];
}


// 方法2：
public char nextGreatestLetter(char[] letters, char target) {
    for (char letter : letters)
        if (letter > target)
            return letter;

    return letters[0];
}
*/