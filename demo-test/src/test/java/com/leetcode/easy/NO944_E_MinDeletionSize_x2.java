/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    944. 删列造序
        给你由 n 个小写字母字符串组成的数组 strs，其中每个字符串长度相等。
        这些字符串可以每个一行，排成一个网格。例如，strs = ["abc", "bce", "cae"] 可以排列为：
            abc
            bce
            cae
        你需要找出并删除 不是按字典序升序排列的 列。在上面的例子（下标从 0 开始）中，
        列 0（'a', 'b', 'c'）和列 2（'c', 'e', 'e'）都是按升序排列的
        ，而列 1（'b', 'c', 'a'）不是，所以要删除列 1 。
        返回你需要删除的列数。
    示例 1：
        输入：strs = ["cba","daf","ghi"]
        输出：1
        解释：网格示意如下：
            cba
            daf
            ghi
            列 0 和列 2 按升序排列，但列 1 不是，所以只需要删除列 1 。
    示例 2：
        输入：strs = ["a","b"]
        输出：0
        解释：网格示意如下：
            a
            b
            只有列 0 这一列，且已经按升序排列，所以不用删除任何列。
    示例 3：
        输入：strs = ["zyx","wvu","tsr"]
        输出：3
        解释：网格示意如下：
            zyx
            wvu
            tsr
            所有 3 列都是非升序排列的，所以都要删除。
*/
public class NO944_E_MinDeletionSize_x2 {

    @Test
    public void test() {
        assert 1 == minDeletionSize(new String[]{"cba", "daf", "ghi"});
        assert 0 == minDeletionSize(new String[]{"a", "b"});
        assert 3 == minDeletionSize(new String[]{"zyx", "wvu", "tsr"});
    }

    public int minDeletionSize(String[] strs) {
        int ans = 0;
        return ans;
    }

}



















/**
// 方法1：
public int minDeletionSize(String[] strs) {
    int ans = 0;
    for (int j = 0; j < strs[0].length(); j++)
        for (int i = 1; i < strs.length; i++)
            // 出现逆序 就结束
            if (strs[i - 1].charAt(j) > strs[i].charAt(j)) {
                ans++;
                break;
            }
    return ans;
}
*/