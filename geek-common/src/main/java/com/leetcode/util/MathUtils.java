package com.leetcode.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;

import static java.lang.Integer.MIN_VALUE;
import static java.time.LocalTime.now;
import static java.util.Arrays.copyOf;

/**
 * 工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MathUtils {

    public static final Integer MAX = Integer.MAX_VALUE;
    public static final Integer MIN = Integer.MIN_VALUE;

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
        if (a.length == 1)
            return max(last, max);

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

    public static int guess(int mid) {
        int hour = now().getHour() % 10;
        if (mid > hour)
            return -1;
        else if (mid < hour)
            return 1;
        return 0;
    }

    public static final int MOD = 1000000007;

    /**
     * n的阶乘
     */
    public static long factorial(int n) {
        long res = 1;
        for (int i = 1; i <= n; i++) {
            res *= i;
            res %= MOD;
        }
        return res;
    }

    public static boolean isPrime(int n) {
        if (n == 1)
            return false;

        for (int i = 2; i * i <= n; i++)
            // 如果是合数
            if (n % i == 0)
                return false;

        return true;
    }

    public static String[] WEEKS = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    public static int[] MONTHS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static boolean isLeap(int year) {
        return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);
    }

    public static final String[] MORSE = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.",
            "....", "..", ".---", "-.-", ".-..", "--", "-.",
            "---", ".--.", "--.-", ".-.", "...", "-", "..-",
            "...-", ".--", "-..-", "-.--", "--.."};

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

    public static List<Integer> emptyList() {
        return new ArrayList<>();
    }

    public static String reverse(StringBuilder sb) {
        return sb.reverse().toString();
    }

    public static ArrayList<ArrayList<Object>> getArray(Object[]... arr) {
        ArrayList<ArrayList<Object>> res = new ArrayList<>();
        for (Object[] ints : arr) {
            int len = ints.length;
            ArrayList<Object> inner = new ArrayList<>(Arrays.asList(ints).subList(0, len));
            res.add(new ArrayList<>(inner));
        }
        return res;
    }

    public static List<Integer> getLinkedList(int num) {
        List<Integer> l1 = new LinkedList<>();
        l1.add(num);
        return l1;
    }

    public static ArrayList<ArrayList<Integer>> getArray(int[]... arr) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for (int[] ints : arr) {
            ArrayList<Integer> inner = new ArrayList<>();
            int len = ints.length;
            for (int anInt : ints)
                inner.add(anInt);

            res.add(new ArrayList<>(inner));
        }
        return res;
    }

    public static List<List<String>> getArray(String[]... arr) {
        List<List<String>> res = new ArrayList<>();
        for (String[] ints : arr) {
            ArrayList<String> inner = new ArrayList<>();
            Collections.addAll(inner, ints);
            res.add(new ArrayList<>(inner));
        }
        return res;
    }

    public static ArrayList<Boolean> getArray(boolean... arr) {
        ArrayList<Boolean> res = new ArrayList<>();
        for (boolean bool : arr) {
            res.add(bool);
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

    public static boolean validPal(String s, int i, int j) {
        while (i < j) {
            if (s.charAt(i) != s.charAt(j))
                return false;
            i++;
            j--;
        }
        return true;
    }

    // 中序遍历
    public static void inOrder(TreeNode root, List<Integer> lst) {
        if (root == null)
            return;

        inOrder(root.left, lst);
        lst.add(root.val);
        inOrder(root.right, lst);
    }

    private boolean canBom(int[][] bombs, int i, int j){
        int[] b1 = bombs[i];
        int[] b2 = bombs[j];
        long x0 = b1[0], x1 = b2[0], y0 = b1[1], y1 = b2[1], r0 = b1[2];
        long len = (y1-y0)*(y1-y0) + (x1-x0)*(x1-x0);
        long r02 = r0 * r0;
        // 【两点距离的平方】(y1-y0)^2 + (x1-x0)^2 < 【引爆半径的平方】r0^2 则会被引爆
        return len <= r02;
    }

    public static ArrayList<Integer> getArray(int... arr) {
        ArrayList<Integer> inner = new ArrayList<>();
        for (int i : arr)
            inner.add(i);

        return inner;
    }

    public static ArrayList<Integer> getArray() {
        return new ArrayList<>();
    }

    public static ArrayList<Double> getArray(double... arr) {
        ArrayList<Double> inner = new ArrayList<>();
        for (double v : arr)
            inner.add(v);

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

    public static TreeNode cTree(Integer... args) {
        int rootIndex = 0;
        List<Integer> arr = Arrays.asList(args);
        return cTreeList(rootIndex, arr);
    }

    public static TreeNode cTreeList(int rootIndex, List<Integer> values) {
        if (rootIndex >= values.size())
            return null;

        if (values.get(rootIndex) != null) {
            TreeNode rootNode = new TreeNode();
            rootNode.val = values.get(rootIndex);
            rootNode.left = cTreeList(2 * rootIndex + 1, values);
            rootNode.right = cTreeList(2 * rootIndex + 2, values);
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

    public static boolean isVowel(char ch) {
        return "aeiouAEIOU".indexOf(ch) >= 0;
    }

    public static String[] MONTH_ENUM = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};;

    // 欧几里得法：计算最大公因数
    // 也叫辗转相除法
    public static int GCD(int d1, int d2) {
        int tmp = d1 % d2;
        while (tmp != 0) {
            d1 = d2;
            d2 = tmp;
            tmp = d1 % d2;
        }
        return d2;
    }

    public static void main(String[] args) {
//        System.out.println(maxs(0, 6, 3, 41111, 5, 2, 5, 8, 109));
//        System.out.println(mins(0, 6, 3));
//        System.out.println(maxs(0, -6, -3, -41111, -5, -2, -5, -8, -109));
    }
}
