package com.leetcode.todo;

import static com.leetcode.util.MathUtils.getLinkedList;
import org.junit.Test;
import java.util.LinkedList;
import java.util.List;
import static com.leetcode.util.SystemUtil.printArr;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY] |
    （简单）
    NO.3069 将元素分配到两个数组中 I
    给你一个下标从1开始、包含不同整数的数组nums，数组长度为n。
    你需要通过n次操作，将nums中的所有元素分配到两个数组arr1和arr2中。
    在第一次操作中，将nums[1]追加到arr1。在第二次操作中，将nums[2]追加到arr2。
    之后，在第i次操作中：
      1）如果arr1的最后一个元素大于arr2的最后一个元素，就将nums[i]追加到arr1。
      2）否则，将nums[i]追加到arr2。
      3）通过连接数组 arr1 和 arr2 形成数组 result 。
    例如，如果arr1==[1, 2, 3]且arr2==[4,5,6]，那么result=[1,2,3,4,5,6]。
    返回数组result。

    示例 1：
        输入：nums = [2, 1, 3]
        输出：[2, 3, 1]
        解释：在前两次操作后，arr1 = [2] ，arr2 = [1] 。
        在第 3次操作中，由于arr1的最后一个元素大于arr2的最后一个元素（2 > 1），
            将nums[3]追加到arr1。
            3次操作后，arr1=[2,3]，arr2=[1]。
        因此，连接形成的数组 result 是 [2, 3, 1] 。
    示例 2：
        输入：nums = [5, 4, 3, 8]
        输出：[5, 3, 4, 8]
        解释：在前两次操作后，arr1 = [5] ，arr2 = [4] 。
        在第 3 次操作中，由于 arr1 的最后一个元素大于 arr2 的最后一个元素（5 > 4），将 nums[3] 追加到 arr1 ，因此 arr1 变为 [5,3] 。
        在第 4 次操作中，由于 arr2 的最后一个元素大于 arr1 的最后一个元素（4 > 3），将 nums[4] 追加到 arr2 ，因此 arr2 变为 [4,8] 。
        4 次操作后，arr1 = [5,3] ，arr2 = [4,8] 。
        因此，连接形成的数组 result 是 [5,3,4,8] 。
    提示：
        3 <= n <= 50
        1 <= nums[i] <= 100
        nums中的所有元素都互不相同。
    Related Topics:数组,模拟
*/
public class NO3069_E_ResultArray {

    @Test
    public void test() {
        assertArrayEquals(new int[]{2, 3, 1},
            resultArray(new int[]{2, 1, 3}));
        assertArrayEquals(new int[]{5, 3, 4, 8},
            resultArray(new int[]{5, 4, 3, 8}));
    }

    public int[] resultArray(int[] nums) {
        // 2024/3/11 NO.1
        return null;
    }

}















/*
// 方法1：
public int[] resultArray(int[] nums) {
    // 1、定义子数组
    // 子数组arr1
    List<Integer> l1 = new LinkedList<>();
    l1.add(nums[0]);
    int last1 = nums[0];

    // 子数组arr2
    List<Integer> l2 = new LinkedList<>();
    l2.add(nums[1]);
    int last2 = nums[1];

    // 2、依题意放置
    for (int i = 2; i < nums.length; i++) {
        if (last1 > last2) {
            l1.add(nums[i]);
            last1 = nums[i];
        } else {
            l2.add(nums[i]);
            last2 = nums[i];
        }
    }

    // 3、合并结果
    int[] resultArray = new int[nums.length];
    int i = 0;
    for (int num : l1) {
        resultArray[i] = num;
        i++;
    }
    for (int num : l2) {
        resultArray[i] = num;
        i++;
    }
    return resultArray;
}

// 方法2：
public int[] resultArray(int[] nums) {
    // 2024/3/11 NO.1
    int last1 = nums[0];
    List<Integer> l1 = getLinkedList(last1);

    int last2 = nums[1];
    List<Integer> l2 = getLinkedList(last2);

    for (int i = 2; i < nums.length; i++) {
        last1 = l1.get(l1.size() - 1);
        last2 = l2.get(l2.size() - 1);
        if (last1 > last2)
            l1.add(nums[i]);
        else
            l2.add(nums[i]);
    }

    // 3、合并结果
    l1.addAll(l2);
    return l1.stream().mapToInt(a -> a).toArray();
}
*/