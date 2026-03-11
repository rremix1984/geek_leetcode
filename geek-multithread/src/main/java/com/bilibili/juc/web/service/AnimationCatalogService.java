package com.bilibili.juc.web.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.animation.launcher.AnimationRegistry;
import com.bilibili.juc.web.model.CatalogItem;

@Service
public class AnimationCatalogService {

    private final AnimationRegistry registry = new AnimationRegistry();
    private final Map<String, String> displayNameToLaunchName = buildDisplayNameToLaunchName();

    public List<CatalogItem> curatedCatalog() {
        List<CatalogItem> items = new ArrayList<>();

        add(items, "Easy/数组算法", "NO.001 两数之和", "NO.001 两数之和", "Easy", "哈希表");
        add(items, "Easy/数组算法", "NO.088 合并两个有序数组", "NO.088 合并两个有序数组", "Easy", "双指针");
        add(items, "Easy/数组算法", "NO.977 有序数组的平方", "NO.977 有序数组的平方", "Easy", "双指针");
        add(items, "Easy/数组算法", "NO.026 删除有序数组中的重复项", "NO.026 删除有序数组中的重复项", "Easy", "双指针");
        add(items, "Easy/数组算法", "NO.283 移动零", "NO.283 移动零", "Easy", "双指针");

        add(items, "Easy/数据结构设计", "NO.225 用队列实现栈", "NO.225 用队列实现栈", "Easy", "队列模拟栈");
        add(items, "Easy/数据结构设计", "NO.234 回文链表", "NO.234 回文链表", "Easy", "快慢指针");
        add(items, "Easy/数据结构设计", "NO.705 设计哈希集合", "NO.705 设计哈希集合", "Easy", "数组+链表");
        add(items, "Easy/数据结构设计", "NO.706 设计哈希映射", "NO.706 设计哈希映射", "Easy", "数组+链表");
        add(items, "Easy/数据结构设计", "NO.703 数据流中的第K大元素", "NO.703 数据流中的第K大元素", "Easy", "堆/优先队列");

        add(items, "Easy/搜索算法", "NO.035 搜索插入位置", "NO.035 搜索插入位置", "Easy", "二分查找");
        add(items, "Easy/搜索算法", "NO.704 二分查找", "NO.704 二分查找", "Easy", "二分查找");

        add(items, "Easy/数学算法", "NO.009 回文数", "NO.009 回文数", "Easy", "数学处理");
        add(items, "Easy/数学算法", "NO.066 加一", "NO.066 加一", "Easy", "数组处理");
        add(items, "Easy/数学算法", "NO.069 x的平方根", "NO.069 x的平方根", "Easy", "二分查找");
        add(items, "Easy/数学算法", "NO.1266 访问所有点的最小时间", "NO.1266 访问所有点的最小时间", "Easy", "曼哈顿距离");

        add(items, "Easy/动态规划", "NO.118 杨辉三角", "NO.118 杨辉三角", "Easy", "动态规划");
        add(items, "Easy/动态规划", "NO.121 买卖股票的最佳时机", "NO.121 买卖股票的最佳时机", "Easy", "动态规划/贪心");

        add(items, "Easy/贪心算法", "NO.055 跳跃游戏", "NO.055 跳跃游戏", "Easy", "贪心算法");
        add(items, "Easy/贪心算法", "NO.045 跳跃游戏 II", "NO.045 跳跃游戏 II", "Normal", "贪心算法");

        add(items, "Easy/字符串算法", "NO.1002 查找常用字符", "NO.1002 查找常用字符", "Easy", "哈希表");

        add(items, "Easy/位运算", "NO.136 只出现一次的数字", "NO.136 只出现一次的数字", "Easy", "异或运算");
        add(items, "Easy/位运算", "NO.190 颠倒二进制位", "NO.190 颠倒二进制位", "Easy", "位操作");

        add(items, "Easy/图论算法", "NO.463 岛屿的周长", "NO.463 岛屿的周长", "Easy", "DFS/BFS");
        add(items, "Easy/图论算法", "NO.200 岛屿数量", "NO.200 岛屿数量", "Normal", "DFS/BFS");
        add(items, "Easy/图论算法", "图的深度优先搜索(DFS)", "图的深度优先搜索(DFS)", "Easy", "图遍历");
        add(items, "Easy/图论算法", "图的广度优先搜索(BFS)", "图的广度优先搜索(BFS)", "Easy", "图遍历");
        add(items, "Easy/图论算法", "Dijkstra最短路径算法", "Dijkstra最短路径算法", "Easy", "最短路径");
        add(items, "Easy/图论算法", "Kruskal最小生成树算法", "Kruskal最小生成树算法", "Easy", "最小生成树");
        add(items, "Easy/图论算法", "拓扑排序算法", "拓扑排序算法", "Easy", "拓扑排序");

        add(items, "Easy/树算法", "NO.144 二叉树的前序遍历", "NO.144 二叉树的前序遍历", "Easy", "递归/迭代");
        add(items, "Easy/树算法", "NO.094 二叉树的中序遍历", "NO.094 二叉树的中序遍历", "Easy", "递归/迭代");
        add(items, "Easy/树算法", "NO.145 二叉树的后序遍历", "NO.145 二叉树的后序遍历", "Easy", "递归/迭代");
        add(items, "Easy/树算法", "NO.100 相同的树", "NO.100 相同的树", "Easy", "DFS");
        add(items, "Easy/树算法", "NO.101 对称二叉树", "NO.101 对称二叉树", "Easy", "DFS/BFS");
        add(items, "Easy/树算法", "NO.104 二叉树的最大深度", "NO.104 二叉树的最大深度", "Easy", "DFS/BFS");
        add(items, "Easy/树算法", "NO.108 将有序数组转换为二叉搜索树", "NO.108 将有序数组转换为二叉搜索树", "Easy", "递归构建");
        add(items, "Easy/树算法", "NO.199 二叉树的右视图", "NO.199 二叉树的右视图", "Normal", "层序遍历");
        add(items, "Easy/树算法", "二叉树的顶层视图", "二叉树的顶层视图", "Easy", "层序遍历");

        add(items, "Easy/DFS算法", "NO.1306 跳跃游戏 III", "NO.1306 跳跃游戏 III", "Normal", "DFS+记忆化");
        add(items, "Easy/BFS算法", "NO.1654 到家的最少跳跃次数", "NO.1654 到家的最少跳跃次数", "Normal", "BFS+状态");

        add(items, "Normal/中等算法", "NO.208 实现 Trie", "NO.208 实现 Trie", "Normal", "Trie + 字符串处理");
        add(items, "Normal/中等算法", "NO.211 添加与搜索单词", "NO.211 添加与搜索单词", "Normal", "Trie + 通配符搜索");
        add(items, "Normal/中等算法", "NO.284 窥探迭代器", "NO.284 窥探迭代器", "Normal", "迭代器设计 + 缓存");
        add(items, "Normal/中等算法", "NO.764 最大加号标志", "NO.764 最大加号标志", "Normal", "动态规划 + 网格处理");
        add(items, "Normal/中等算法", "NO.915 分割数组", "NO.915 分割数组", "Normal", "数组分割 + 双指针");
        add(items, "Normal/中等算法", "NO.005 最长回文子串", "NO.005 最长回文子串", "Normal", "动态规划 + 中心扩展");
        add(items, "Normal/中等算法", "NO.015 三数之和", "NO.015 三数之和", "Normal", "双指针 + 排序");
        add(items, "Normal/中等算法", "NO.053 最大子数组和", "NO.053 最大子数组和", "Normal", "动态规划 + Kadane算法");
        add(items, "Normal/中等算法", "NO.046 全排列", "NO.046 全排列", "Normal", "回溯算法 + 递归");
        add(items, "Normal/中等算法", "NO.322 零钱兑换", "NO.322 零钱兑换", "Normal", "动态规划 + 完全背包");
        add(items, "Normal/中等算法", "NO.139 单词拆分", "NO.139 单词拆分", "Normal", "动态规划 + 字符串匹配");

        add(items, "Hard/困难算法", "NO.51 N皇后", "NO.51 N皇后", "Hard", "回溯算法");
        add(items, "Hard/困难算法", "NO.403 青蛙过河", "NO.403 青蛙过河", "Hard", "动态规划 + 集合");
        add(items, "Hard/困难算法", "NO.023 合并K个升序链表", "NO.023 合并K个升序链表", "Hard", "优先队列 + 链表");
        add(items, "Hard/困难算法", "NO.52 N皇后 II", "NO.52 N皇后 II", "Hard", "回溯算法");

        add(items, "Interval/面试题", "面试题 04.05. 合法二叉搜索树", resolveLaunchName("面试题 04.05. 合法二叉搜索树"), "Interval",
                "二叉搜索树 + DFS验证");
        add(items, "Interval/面试题", "面试题 04.10. 检查子树", resolveLaunchName("面试题 04.10. 检查子树"), "Interval", "树遍历 + 字符串匹配");
        add(items, "Interval/面试题", "面试题 04.06. 后继者", resolveLaunchName("面试题 04.06. 后继者"), "Interval", "二叉搜索树 + 中序遍历");
        add(items, "Interval/面试题", "面试题 08.09. 括号", resolveLaunchName("面试题 08.09. 括号"), "Interval", "回溯算法 + 括号生成");
        add(items, "Interval/面试题", "面试题 04.12. 求和路径", resolveLaunchName("面试题 04.12. 求和路径"), "Interval", "树遍历 + 路径统计");

        for (CatalogItem item : items) {
            String launchName = resolveLaunchName(item.getLaunchName());
            item.setLaunchName(launchName);
            item.setLaunchable(launchName != null && registry.hasAnimation(launchName));
        }

        return Collections.unmodifiableList(items);
    }

    public List<String> allAnimationNames() {
        List<String> names = new ArrayList<>(registry.getAnimations().keySet());
        names.sort(Comparator.naturalOrder());
        return Collections.unmodifiableList(names);
    }

    public boolean hasAnimation(String name) {
        String resolved = resolveLaunchName(name);
        return resolved != null && registry.hasAnimation(resolved);
    }

    public Runnable getLauncher(String name) {
        String resolved = resolveLaunchName(name);
        if (resolved == null) {
            return null;
        }
        return registry.getAnimations().get(resolved);
    }

    public Map<String, Object> summary() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("totalAnimations", registry.size());
        return map;
    }

    private void add(List<CatalogItem> items, String categoryPath, String name, String launchName, String difficulty,
            String technique) {
        items.add(new CatalogItem(categoryPath, name, launchName, difficulty, technique, false));
    }

    private String resolveLaunchName(String input) {
        if (input == null) {
            return null;
        }
        String raw = input.trim();
        if (raw.isEmpty()) {
            return null;
        }
        if (registry.hasAnimation(raw)) {
            return raw;
        }
        String mapped = displayNameToLaunchName.get(raw);
        if (mapped != null && registry.hasAnimation(mapped)) {
            return mapped;
        }
        String normalized = raw.replaceAll("\\s+", " ");
        if (registry.hasAnimation(normalized)) {
            return normalized;
        }
        String upperNo = normalized.replace("NO.", "NO.").toUpperCase(Locale.ROOT);
        if (registry.hasAnimation(upperNo)) {
            return upperNo;
        }
        return displayNameToLaunchName.getOrDefault(raw, null);
    }

    private Map<String, String> buildDisplayNameToLaunchName() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("面试题 04.05. 合法二叉搜索树", "NO.04.05 验证BST");
        map.put("面试题 04.10. 检查子树", "NO.04.10 检查子树");
        map.put("面试题 04.06. 后继者", "NO.04.06 中序后继");
        map.put("面试题 08.09. 括号", "NO.08.09 括号生成");
        map.put("面试题 04.12. 求和路径", "NO.04.12 路径总和");
        return map;
    }
}
