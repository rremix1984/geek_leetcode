package com.leetcode.util;

import lombok.NoArgsConstructor;

import java.util.*;

import static com.leetcode.util.LogUtil.info;
import static java.lang.Integer.MIN_VALUE;
import static java.util.Arrays.copyOf;
import static lombok.AccessLevel.PRIVATE;

/**
 * 工具类
 */
@NoArgsConstructor(access = PRIVATE)
public class MathUtils {

    /**
     * 三个值取最大
     */
    public static int maxs(int... a) {
        if (a == null || a.length == 0) {
            return -1;
        }

        if (a.length == 1) {
            return a[0];
        }

        return getmax(a);
    }

    /**
     * 三个值取最小
     */
    public static int mins(int... a) {
        if (a == null || a.length == 0)
            return -1;

        if (a.length == 1)
            return a[0];

        return getmin(a);
    }

    public static int max(int... input) {
        int max = MIN_VALUE;
        for (int element : input) {
            if (element > max) {
                max = element;
            }
        }
        return max;
    }

    public static int rand7() {
        return new Random().nextInt(7) + 1;
    }

    private static int getmax(int[] a) {
        return getmax(a, MIN_VALUE);
    }

    private static int getmin(int[] a) {
        return getmin(a, Integer.MAX_VALUE);
    }

    /**
     * 递归方法 找最大值
     */
    private static int getmax(int[] a, int max) {
        int lastindex = a.length-1;
        int last = a[lastindex];
        if (a.length == 1) {
            return max(last, max);
        }
        //每次数组缩短一个元素，最后一个元素与缩短的数组进行 getmax 操作
        return getmax(copyOf(a, lastindex), max(max, last));
    }

    /**
     * 递归方法 找最小值
     */
    private static int getmin(int[] a, int min) {
        int lastindex = a.length - 1;
        int last = a[lastindex];
        if (a.length == 1)
            return min(last, min);

        //每次数组缩短一个元素，最后一个元素与缩短的数组进行 getmax 操作
        return getmin(copyOf(a, lastindex), min(min, last));
    }

    private static int max(int i, int j) {
        if (i >= j)
            return i;
        return j;
    }

    private static int min(int i, int j) {
        if (i <= j)
            return i;
        return j;
    }

    private static long max(long i, long j) {
        if (i >= j)
            return i;
        return j;
    }

    private static long min(long i, long j) {
        if (i <= j)
            return i;
        return j;
    }

    public static int bin2Dec(String binaryString){
        int sum = 0;
        for(int i = 0;i < binaryString.length();i++){
            char ch = binaryString.charAt(i);
            if(ch > '2' || ch < '0')
                throw new NumberFormatException(String.valueOf(i));
            sum = sum * 2 + (binaryString.charAt(i) - '0');
        }
        return sum;
    }

    public static String binaryString(int num) {
        StringBuilder result = new StringBuilder();
        int flag = 1 << 7;
        for (int i = 0; i < 8; i++) {
            int val = (flag & num) == 0 ? 0 : 1;
            result.append(val);
            num <<= 1;
        }
        return result.toString();
    }

    public static List<Integer> getArray() {
        return new ArrayList<>();
    }

    public static String reverse(StringBuilder sb) {
        return sb.reverse().toString();
    }

    public static ArrayList<ArrayList<Object>> getArray(Object[]... arr) {
        ArrayList<ArrayList<Object>> res = new ArrayList<>();
        for (Object[] ints : arr) {
            ArrayList<Object> inner = new ArrayList<>();
            int len = ints.length;
            for (int j = 0; j < len; j++) {
                inner.add(ints[j]);
            }
            res.add(new ArrayList<>(inner));
        }
        return res;
    }

    public static ArrayList<ArrayList<Integer>> getArray(int[]... arr) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for (int[] ints : arr) {
            ArrayList<Integer> inner = new ArrayList<>();
            int len = ints.length;
            for (int j = 0; j < len; j++)
                inner.add(ints[j]);

            res.add(new ArrayList<>(inner));
        }
        return res;
    }

    // 双指针判断回文字符
    public static boolean isHuiwen(char[] chars,int start, int end){
        for (int i = start, j = end; i < j; i++, j--)
            if (chars[i] != chars[j])
                return false;
        return true;
    }

    public static ArrayList<Integer> getArray(int... arr) {
        ArrayList<Integer> inner = new ArrayList<>();
        for (int j = 0; j < arr.length; j++) {
            inner.add(arr[j]);
        }
        return inner;
    }

    public static int[] getArrays(int... arr) {
        int[] inner = new int[arr.length];
        System.arraycopy(arr, 0, inner, 0, arr.length);
        return inner;
    }

    public static String[] getArrays(String... arr) {
        String[] inner = new String[arr.length];
        System.arraycopy(arr, 0, inner, 0, arr.length);
        return inner;
    }

    public static ArrayList<String> getArray(String... arr) {
        ArrayList<String> inner = new ArrayList<>();
        Collections.addAll(inner, arr);
        return inner;
    }

    public static TreeNode createFullTree(Integer... args) {
        int rootIndex = 0;
        List<Integer> arr = Arrays.asList(args);
        return createFullTree(rootIndex, arr);
    }

    public static TreeNode createFullTree(int rootIndex, List<Integer> values) {
        if (rootIndex >= values.size())
            return null;

        if (values.get(rootIndex) != null) {
            TreeNode rootNode = new TreeNode();
            rootNode.val = values.get(rootIndex);
            rootNode.left = createFullTree(2 * rootIndex + 1, values);
            rootNode.right = createFullTree(2 * rootIndex + 2, values);
            return rootNode;
        }
        return null;
    }

    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null)
            return list2;

        if (list2 == null)
            return list1;

        ListNode dummy = new ListNode(-1);
        ListNode temp = dummy;
        ListNode l1 = list1;
        ListNode l2 = list2;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                temp.next = l1;
                l1 = l1.next;
            } else {
                temp.next = l2;
                l2 = l2.next;
            }
            temp = temp.next;
        }
        temp.next = l1 == null ? l2 : l1;
        return dummy.next;
    }

    public static ListNode[] getListNodes() {
        return new ListNode[]{new ListNode()};
    }

    public static ListNode[] getListNodes(int[][] arr) {
        if (arr == null ||arr.length == 0 || arr[0] == null)
            return new ListNode[]{new ListNode()};

        ListNode[] res = new ListNode[arr.length];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length == 0)
                res[i] = new ListNode();
            else
                res[i] = new ListNode(arr[i]);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(maxs(0, 6, 3, 41111, 5, 2, 5, 8, 109));
        System.out.println(mins(0, 6, 3));
        System.out.println(maxs(0, -6, -3, -41111, -5, -2, -5, -8, -109));
    }
}
