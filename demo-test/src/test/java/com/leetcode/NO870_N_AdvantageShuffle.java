/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import java.util.Arrays;
import java.util.Comparator;
import static org.junit.Assert.assertArrayEquals;

/**
    (中等)
    870. 优势洗牌
        给定两个大小相等的数组 nums1 和 nums2，nums1 相对于 nums 的优势可以用满足 nums1[i] > nums2[i] 的索引 i 的数目来描述。
        返回 nums1 的任意排列，使其相对于 nums2 的优势最大化。
    示例 1：
        输入：nums1 = [2,7,11,15], nums2 = [1,10,4,11]
        输出：[2,11,7,15]
    示例 2：
        输入：nums1 = [12,24,8,32], nums2 = [13,25,32,11]
        输出：[24,32,8,12]
*/
public class NO870_N_AdvantageShuffle {

    @Test
    public void test() {
        assertArrayEquals(new int[]{2, 11, 7, 15},
                advantageCount(new int[]{2, 7, 11, 15}, new int[]{1, 10, 4, 11}));
        assertArrayEquals(new int[]{24, 32, 8, 12},
                advantageCount(new int[]{12, 24, 8, 32}, new int[]{13, 25, 32, 11}));
    }

    public int[] advantageCount(int[] nums1, int[] nums2) {
        int n = nums2.length;
        int[] ans = new int[n];
        return ans;
    }

}

















/**
// 方法1：
public int[] advantageCount(int[] nums1, int[] nums2) {
    Arrays.sort(nums1);
    int n = nums2.length;
    int[] ans = new int[n];
    int[][] nums = new int[n][2];
    for(int i = 0;i<n;i++){
        nums[i][0] = nums2[i];
        nums[i][1] = i;
    }
    Arrays.sort(nums, Comparator.comparingInt(a -> a[0]));
    int start = 0;
    int end = n-1;
    for(int i = 0;i<n;i++){
        if(nums1[i] <= nums[start][0]){//最小值小于对面最小值，和对面最强的一换一,先不减支
            int index = nums[end][1];
            ans[index] = nums1[i];
            end--;
        }else{
            int index = nums[start][1];
            ans[index] = nums1[i];
            start++;
        }
    }
    return ans;
}
*/