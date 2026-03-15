package com.bilibili.juc.web.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.animation.launcher.AnimationRegistry;
import com.bilibili.juc.web.model.CatalogItem;

@Service
public class AnimationCatalogService {

    private static final Pattern NO_PATTERN = Pattern.compile("NO\\.?\\s*(\\d{3,4})", Pattern.CASE_INSENSITIVE);
    private static final Pattern DOC_PATTERN = Pattern.compile("/\\*\\*([\\s\\S]*?)\\*/");

    private final AnimationRegistry registry = new AnimationRegistry();
    private final Map<String, String> displayNameToLaunchName = buildDisplayNameToLaunchName();
    private final Map<String, String> codeIndex = buildCodeIndex();
    private final List<CatalogItem> catalog = buildCatalog();

    public List<CatalogItem> curatedCatalog() {
        return catalog;
    }

    public List<CatalogItem> listProblems(String difficulty, String keyword) {
        String diff = difficulty == null ? "" : difficulty.trim().toLowerCase(Locale.ROOT);
        String kw = keyword == null ? "" : keyword.trim();
        return catalog.stream()
                .filter(item -> diff.isEmpty() || "all".equals(diff)
                        || (item.getDifficulty() != null && item.getDifficulty().toLowerCase(Locale.ROOT).equals(diff)))
                .filter(item -> kw.isEmpty() || contains(item.getName(), kw) || contains(item.getTechnique(), kw)
                        || contains(item.getCategoryPath(), kw))
                .collect(Collectors.toList());
    }

    public CatalogItem findProblemById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        String target = id.trim();
        for (CatalogItem item : catalog) {
            if (target.equals(item.getId())) {
                return item;
            }
        }
        return null;
    }

    public CatalogItem firstProblem() {
        return catalog.isEmpty() ? null : catalog.get(0);
    }

    private List<CatalogItem> buildCatalog() {
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
            item.setId(buildProblemId(item));
            String sourceCode = findSourceCode(item);
            item.setCode(buildStarterCode(item));
            item.setDescription(buildDescription(item, sourceCode));
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
        map.put("totalProblems", catalog.size());
        return map;
    }

    private void add(List<CatalogItem> items, String categoryPath, String name, String launchName, String difficulty,
            String technique) {
        items.add(new CatalogItem("", categoryPath, name, launchName, difficulty, technique, "", "", false));
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

    private String buildProblemId(CatalogItem item) {
        String launchName = item.getLaunchName() == null ? "" : item.getLaunchName();
        Matcher matcher = NO_PATTERN.matcher(item.getName() + " " + launchName);
        if (matcher.find()) {
            return "no" + matcher.group(1);
        }
        String raw = (item.getName() == null ? "" : item.getName()).toLowerCase(Locale.ROOT);
        String normalized = raw.replaceAll("[^a-z0-9\\u4e00-\\u9fa5]+", "-").replaceAll("(^-|-$)", "");
        return normalized.isEmpty() ? "problem-" + Math.abs(launchName.hashCode()) : normalized;
    }

    private String findSourceCode(CatalogItem item) {
        Matcher matcher = NO_PATTERN.matcher(item.getName() + " " + item.getLaunchName());
        if (matcher.find()) {
            String key = "NO" + matcher.group(1);
            String code = codeIndex.get(key);
            if (code != null && !code.trim().isEmpty()) {
                return code;
            }
        }
        return "";
    }

    private String buildStarterCode(CatalogItem item) {
        String method = buildMethodName(item);
        return "public class Solution {\n"
                + "    public static Object " + method + "() {\n"
                + "        return null;\n"
                + "    }\n\n"
                + "    public static void main(String[] args) {\n"
                + "        System.out.println(" + method + "());\n"
                + "    }\n"
                + "}\n";
    }

    private String buildMethodName(CatalogItem item) {
        String base = item.getName() == null ? "solve" : item.getName();
        String normalized = base.replaceAll("NO\\.?\\s*\\d{1,4}", "")
                .replaceAll("[^a-zA-Z0-9\\u4e00-\\u9fa5]+", " ").trim();
        if (normalized.isEmpty()) {
            return "solve";
        }
        String[] parts = normalized.split("\\s+");
        StringBuilder builder = new StringBuilder("solve");
        for (String part : parts) {
            if (part.isEmpty()) {
                continue;
            }
            builder.append(part.substring(0, 1).toUpperCase(Locale.ROOT));
            if (part.length() > 1) {
                builder.append(part.substring(1));
            }
            if (builder.length() > 36) {
                break;
            }
        }
        return builder.toString().replaceAll("[^a-zA-Z0-9_]", "");
    }

    private String buildDescription(CatalogItem item, String code) {
        String doc = extractDoc(code);
        if (!doc.isEmpty()) {
            return normalizeStructuredDescription(item, doc);
        }
        return normalizeStructuredDescription(item, "");
    }

    private String normalizeStructuredDescription(CatalogItem item, String doc) {
        List<String> lines = splitDocLines(doc);
        String title = compactText(item == null ? "" : item.getName(), 80);
        String statement = compactText(extractStatement(lines, item), 180);
        String input = compactText(extractSectionValue(lines, "输入"), 180);
        String output = compactText(extractSectionValue(lines, "输出"), 180);
        Map<String, String> example = extractExample(lines);

        if (statement.isEmpty()) {
            statement = compactText("分类：" + value(item == null ? null : item.getCategoryPath()) + "；技巧："
                    + value(item == null ? null : item.getTechnique()), 180);
        }
        if (input.isEmpty()) {
            input = "请参考题目中的参数定义";
        }
        if (output.isEmpty()) {
            output = "返回题目要求的结果";
        }
        String exampleInput = compactText(example.getOrDefault("input", ""), 120);
        String exampleOutput = compactText(example.getOrDefault("output", ""), 120);
        String exampleExplain = compactText(example.getOrDefault("explain", ""), 120);
        if (exampleInput.isEmpty()) {
            exampleInput = input;
        }
        if (exampleOutput.isEmpty()) {
            exampleOutput = output;
        }
        StringBuilder exampleLine = new StringBuilder("输入：").append(exampleInput).append("；输出：").append(exampleOutput);
        if (!exampleExplain.isEmpty()) {
            exampleLine.append("；解释：").append(exampleExplain);
        }
        return "题目：" + (title.isEmpty() ? "未命名题目" : title)
                + "\n说明：" + statement
                + "\n输入：" + input
                + "\n输出：" + output
                + "\n示例：" + exampleLine;
    }

    private List<String> splitDocLines(String doc) {
        if (doc == null || doc.trim().isEmpty()) {
            return Collections.emptyList();
        }
        String[] rawLines = doc.split("\\r?\\n");
        List<String> lines = new ArrayList<>();
        for (String raw : rawLines) {
            String line = raw == null ? "" : raw.trim();
            if (line.isEmpty()) {
                continue;
            }
            if (line.startsWith("[") && line.endsWith("]")) {
                continue;
            }
            if (line.startsWith("（") && line.endsWith("）") && line.length() <= 8) {
                continue;
            }
            if (line.startsWith("(") && line.endsWith(")") && line.length() <= 8) {
                continue;
            }
            if (line.matches("^NO\\.?\\s*\\d{1,4}.*$")) {
                continue;
            }
            lines.add(line);
        }
        return lines;
    }

    private String extractStatement(List<String> lines, CatalogItem item) {
        if (lines == null || lines.isEmpty()) {
            return "";
        }
        String name = item == null ? "" : value(item.getName());
        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            if (startsWithLabel(line, "输入") || startsWithLabel(line, "输出") || startsWithLabel(line, "示例")
                    || startsWithLabel(line, "提示") || startsWithLabel(line, "约束")) {
                break;
            }
            if (line.equals(name)) {
                continue;
            }
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(line);
            if (builder.length() >= 180) {
                break;
            }
        }
        return builder.toString().trim();
    }

    private String extractSectionValue(List<String> lines, String label) {
        if (lines == null || lines.isEmpty()) {
            return "";
        }
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!startsWithLabel(line, label)) {
                continue;
            }
            String direct = valueAfterLabel(line);
            if (!direct.isEmpty()) {
                return direct;
            }
            StringBuilder next = new StringBuilder();
            for (int j = i + 1; j < lines.size(); j++) {
                String follower = lines.get(j);
                if (isSectionAnchor(follower)) {
                    break;
                }
                if (next.length() > 0) {
                    next.append(" ");
                }
                next.append(follower);
                if (next.length() >= 180) {
                    break;
                }
            }
            return next.toString().trim();
        }
        return "";
    }

    private Map<String, String> extractExample(List<String> lines) {
        Map<String, String> result = new LinkedHashMap<>();
        if (lines == null || lines.isEmpty()) {
            return result;
        }
        int start = -1;
        for (int i = 0; i < lines.size(); i++) {
            if (startsWithLabel(lines.get(i), "示例")) {
                start = i;
                break;
            }
        }
        if (start < 0) {
            return result;
        }
        for (int i = start + 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if (startsWithLabel(line, "示例") || startsWithLabel(line, "提示") || startsWithLabel(line, "约束")) {
                break;
            }
            if (!result.containsKey("input") && startsWithLabel(line, "输入")) {
                result.put("input", valueAfterLabel(line));
                continue;
            }
            if (!result.containsKey("output") && startsWithLabel(line, "输出")) {
                result.put("output", valueAfterLabel(line));
                continue;
            }
            if (!result.containsKey("explain") && startsWithLabel(line, "解释")) {
                result.put("explain", valueAfterLabel(line));
            }
        }
        return result;
    }

    private boolean isSectionAnchor(String line) {
        return startsWithLabel(line, "输入") || startsWithLabel(line, "输出") || startsWithLabel(line, "示例")
                || startsWithLabel(line, "提示") || startsWithLabel(line, "约束") || startsWithLabel(line, "解释");
    }

    private boolean startsWithLabel(String line, String label) {
        if (line == null || label == null) {
            return false;
        }
        String normalized = line.trim();
        if (normalized.startsWith(label + "：") || normalized.startsWith(label + ":")) {
            return true;
        }
        if (normalized.equals(label)) {
            return true;
        }
        return normalized.matches("^" + Pattern.quote(label) + "\\s*\\d+.*$");
    }

    private String valueAfterLabel(String line) {
        if (line == null) {
            return "";
        }
        int idx = line.indexOf('：');
        if (idx < 0) {
            idx = line.indexOf(':');
        }
        if (idx < 0 || idx + 1 >= line.length()) {
            return "";
        }
        return line.substring(idx + 1).trim();
    }

    private String compactText(String input, int maxLen) {
        String text = value(input).replaceAll("\\s+", " ").trim();
        if (text.length() <= maxLen) {
            return text;
        }
        if (maxLen <= 1) {
            return text.substring(0, Math.max(0, maxLen));
        }
        return text.substring(0, maxLen - 1).trim() + "…";
    }

    private String value(String input) {
        return input == null ? "" : input.trim();
    }

    private String extractDoc(String code) {
        if (code == null || code.trim().isEmpty()) {
            return "";
        }
        Matcher matcher = DOC_PATTERN.matcher(code);
        String best = "";
        int bestScore = Integer.MIN_VALUE;
        while (matcher.find()) {
            String candidate = normalizeDocBlock(matcher.group(1));
            if (candidate.isEmpty()) {
                continue;
            }
            int score = scoreDoc(candidate);
            if (score > bestScore) {
                best = candidate;
                bestScore = score;
            }
        }
        return best;
    }

    private String normalizeDocBlock(String block) {
        if (block == null || block.trim().isEmpty()) {
            return "";
        }
        String[] rawLines = block.split("\\r?\\n");
        List<String> lines = new ArrayList<>();
        for (String raw : rawLines) {
            String value = raw.replaceFirst("^\\s*\\*\\s?", "").trim();
            if (value.isEmpty()) {
                continue;
            }
            String lower = value.toLowerCase(Locale.ROOT);
            if (lower.startsWith("copyright")) {
                continue;
            }
            if (lower.matches("^\\d{4}[/.-]\\d{1,2}[/.-]\\d{1,2}$")) {
                continue;
            }
            if (lower.startsWith("author") || lower.startsWith("@author") || lower.startsWith("@date")
                    || lower.startsWith("@since")) {
                continue;
            }
            lines.add(value);
        }
        int start = 0;
        while (start < lines.size() && isNoiseLine(lines.get(start))) {
            start++;
        }
        if (start >= lines.size()) {
            return "";
        }
        List<String> picked = new ArrayList<>();
        for (int i = start; i < lines.size(); i++) {
            picked.add(lines.get(i));
            if (picked.size() >= 16) {
                break;
            }
        }
        return String.join("\n", picked).trim();
    }

    private boolean isNoiseLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return true;
        }
        String lower = line.trim().toLowerCase(Locale.ROOT);
        if (lower.startsWith("package ") || lower.startsWith("import ")) {
            return true;
        }
        if (lower.matches("^\\[[^\\]]+\\].*$")) {
            return true;
        }
        if (lower.matches("^[\\W_]+$")) {
            return true;
        }
        if (lower.contains("copyright")) {
            return true;
        }
        return lower.matches("^\\d{4}[/.-]\\d{1,2}[/.-]\\d{1,2}.*$");
    }

    private int scoreDoc(String doc) {
        String lower = doc.toLowerCase(Locale.ROOT);
        int score = 0;
        if (lower.contains("题目") || lower.contains("算法描述")) {
            score += 14;
        }
        if (lower.contains("no.")) {
            score += 6;
        }
        if (lower.contains("给定")) {
            score += 10;
        }
        if (lower.contains("输入")) {
            score += 8;
        }
        if (lower.contains("输出")) {
            score += 8;
        }
        if (lower.contains("示例")) {
            score += 10;
        }
        if (lower.contains("解释")) {
            score += 6;
        }
        if (lower.contains("提示")) {
            score += 4;
        }
        if (lower.contains("复杂度")) {
            score += 4;
        }
        if (lower.contains("copyright")) {
            score -= 40;
        }
        if (doc.length() < 12) {
            score -= 8;
        }
        score += Math.min(20, doc.length() / 30);
        return score;
    }

    private Map<String, String> buildCodeIndex() {
        Map<String, String> index = new LinkedHashMap<>();
        Path root = detectProjectRoot();
        if (root == null) {
            return index;
        }
        List<Path> dirs = new ArrayList<>();
        dirs.add(root.resolve("geek-easy/src/test/java"));
        dirs.add(root.resolve("geek-easy/src/main/java"));
        dirs.add(root.resolve("geek-normal/src/test/java"));
        dirs.add(root.resolve("geek-normal/src/main/java"));
        dirs.add(root.resolve("geek-hard/src/test/java"));
        dirs.add(root.resolve("geek-hard/src/main/java"));
        for (Path dir : dirs) {
            index.putAll(scanDirectory(dir, index.keySet()));
        }
        return index;
    }

    private Map<String, String> scanDirectory(Path dir, Set<String> exists) {
        Map<String, String> map = new LinkedHashMap<>();
        if (dir == null || !Files.exists(dir)) {
            return map;
        }
        Set<String> seen = new LinkedHashSet<>(exists);
        try (Stream<Path> stream = Files.walk(dir)) {
            List<Path> files = stream.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".java"))
                    .collect(Collectors.toList());
            for (Path file : files) {
                String name = file.getFileName().toString();
                Matcher matcher = Pattern.compile("NO(\\d{3,4})", Pattern.CASE_INSENSITIVE).matcher(name);
                if (!matcher.find()) {
                    continue;
                }
                String key = "NO" + matcher.group(1);
                if (seen.contains(key)) {
                    continue;
                }
                String code = readFile(file);
                if (code.isEmpty()) {
                    continue;
                }
                map.put(key, code);
                seen.add(key);
            }
        } catch (IOException ignored) {
        }
        return map;
    }

    private Path detectProjectRoot() {
        Path current = Paths.get(System.getProperty("user.dir")).toAbsolutePath().normalize();
        for (int i = 0; i < 6 && current != null; i++) {
            if (Files.exists(current.resolve("geek-easy")) && Files.exists(current.resolve("geek-normal"))
                    && Files.exists(current.resolve("geek-hard"))) {
                return current;
            }
            current = current.getParent();
        }
        return null;
    }

    private String readFile(Path file) {
        try {
            byte[] bytes = Files.readAllBytes(file);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "";
        }
    }

    private boolean contains(String value, String keyword) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT));
    }
}
