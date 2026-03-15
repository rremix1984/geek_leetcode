package com.bilibili.juc.web.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bilibili.juc.web.model.CatalogItem;

@Service
public class WebAnimationService {

    public Map<String, Object> buildAnimation(CatalogItem item) {
        String id = item == null || item.getId() == null ? "" : item.getId().trim().toLowerCase(Locale.ROOT);
        String technique = item == null || item.getTechnique() == null ? "" : item.getTechnique().toLowerCase(Locale.ROOT);
        String category = item == null || item.getCategoryPath() == null ? "" : item.getCategoryPath().toLowerCase(Locale.ROOT);
        if ("no001".equals(id)) {
            return buildTwoSumAnimation(item);
        }
        if ("no704".equals(id)) {
            return buildBinarySearchAnimation(item);
        }
        if ("no121".equals(id)) {
            return buildStockAnimation(item);
        }
        if ("no055".equals(id)) {
            return buildJumpGameAnimation(item);
        }
        if ("no045".equals(id)) {
            return buildJumpGameIIAnimation(item);
        }
        if ("no1306".equals(id)) {
            return buildJumpGameIIIAnimation(item);
        }
        if ("no463".equals(id)) {
            return buildIslandPerimeterAnimation(item);
        }
        if ("no200".equals(id)) {
            return buildNumberOfIslandsAnimation(item);
        }
        if ("no225".equals(id)) {
            return buildQueueStackAnimation(item);
        }
        if (isOneOf(id, "no703", "no023")) {
            return buildHeapAnimation(item);
        }
        if (isOneOf(id, "no705", "no706")) {
            return buildHashAnimation(item);
        }
        if (isOneOf(id, "no144", "no094", "no145", "no100", "no101", "no104", "no108", "no199",
                "面试题-04-05-合法二叉搜索树", "面试题-04-10-检查子树", "面试题-04-06-后继者", "面试题-04-12-求和路径", "二叉树的顶层视图")) {
            return buildTreeAnimation(item);
        }
        if (isOneOf(id, "no463", "no200", "no1306", "no1654")) {
            return buildGraphAnimation(item);
        }
        if (isOneOf(id, "no046", "no-51-n皇后", "no-52-n皇后-ii", "面试题-08-09-括号")) {
            return buildBacktrackingAnimation(item);
        }
        if (isOneOf(id, "no118", "no053", "no322", "no139", "no764", "no403", "no005", "no915")) {
            return buildDpAnimation(item);
        }
        if (isOneOf(id, "no009", "no066", "no1266", "no136", "no190", "no208", "no211", "no284")) {
            return buildSpecializedAnimation(item);
        }
        if (technique.contains("二分")) {
            return buildBinaryInsertionAnimation(item);
        }
        if (technique.contains("双指针") || technique.contains("快慢指针")) {
            return buildTwoPointersAnimation(item);
        }
        if (technique.contains("队列") && technique.contains("栈")) {
            return buildQueueStackAnimation(item);
        }
        if (technique.contains("堆") || technique.contains("优先队列")) {
            return buildHeapAnimation(item);
        }
        if (technique.contains("动态规划")) {
            return buildDpAnimation(item);
        }
        if (technique.contains("回溯")) {
            return buildBacktrackingAnimation(item);
        }
        if (category.contains("树")) {
            return buildTreeAnimation(item);
        }
        if (category.contains("图") || technique.contains("dfs") || technique.contains("bfs")) {
            return buildGraphAnimation(item);
        }
        if (technique.contains("哈希")) {
            return buildHashAnimation(item);
        }
        return buildGenericAnimation(item);
    }

    private Map<String, Object> buildTwoSumAnimation(CatalogItem item) {
        int[] nums = new int[] { 2, 7, 11, 15 };
        int target = 9;
        Map<Integer, Integer> map = new LinkedHashMap<>();
        List<Map<String, Object>> frames = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            int value = nums[i];
            int need = target - value;
            Map<String, Object> frame = baseFrame(nums);
            frame.put("description", "检查元素 " + value + "（索引 " + i + "），寻找 " + need);
            frame.put("active", i);
            frame.put("target", target);
            frame.put("mapValues", new ArrayList<>(map.keySet()));
            if (map.containsKey(need)) {
                frame.put("found", true);
                frame.put("result", list(map.get(need), i));
                frame.put("description", frame.get("description") + " -> 找到答案");
                frames.add(frame);
                break;
            } else {
                map.put(value, i);
                frame.put("found", false);
                frame.put("mapValues", new ArrayList<>(map.keySet()));
                frame.put("description", frame.get("description") + " -> 存入 HashMap");
                frames.add(frame);
            }
        }
        return response(item, "hash", 1200, frames);
    }

    private Map<String, Object> buildBinarySearchAnimation(CatalogItem item) {
        int[] nums = new int[] { -1, 0, 3, 5, 9, 12 };
        int target = 9;
        int left = 0;
        int right = nums.length - 1;
        List<Map<String, Object>> frames = new ArrayList<>();
        while (left <= right) {
            int mid = left + (right - left) / 2;
            Map<String, Object> frame = baseFrame(nums);
            frame.put("description", "left=" + left + ", right=" + right + ", mid=" + mid + ", nums[mid]=" + nums[mid]);
            frame.put("left", left);
            frame.put("right", right);
            frame.put("mid", mid);
            frame.put("target", target);
            if (nums[mid] == target) {
                frame.put("found", true);
                frame.put("result", mid);
                frame.put("description", frame.get("description") + " -> 找到目标值");
                frames.add(frame);
                break;
            }
            frame.put("found", false);
            if (nums[mid] < target) {
                frame.put("description", frame.get("description") + " -> 搜索右半部分");
            } else {
                frame.put("description", frame.get("description") + " -> 搜索左半部分");
            }
            frames.add(frame);
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return response(item, "array", 1300, frames);
    }

    private Map<String, Object> buildStockAnimation(CatalogItem item) {
        int[] prices = new int[] { 7, 1, 5, 3, 6, 4 };
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        int buyIndex = -1;
        int sellIndex = -1;
        List<Map<String, Object>> frames = new ArrayList<>();
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
                buyIndex = i;
            }
            int profit = prices[i] - minPrice;
            if (profit > maxProfit) {
                maxProfit = profit;
                sellIndex = i;
            }
            Map<String, Object> frame = baseFrame(prices);
            frame.put("description", "第 " + (i + 1) + " 天：最低价格=" + minPrice + "，今日利润=" + profit + "，最大利润=" + maxProfit);
            frame.put("active", i);
            if (buyIndex >= 0) {
                frame.put("buyIndex", buyIndex);
            }
            if (sellIndex >= 0) {
                frame.put("sellIndex", sellIndex);
            }
            frame.put("profit", maxProfit);
            frames.add(frame);
        }
        return response(item, "array", 1000, frames);
    }

    private Map<String, Object> buildJumpGameAnimation(CatalogItem item) {
        int[] nums = new int[] { 2, 3, 1, 1, 4, 2, 1 };
        List<Map<String, Object>> frames = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        int current = 0;
        path.add(current);
        int globalReach = 0;
        while (current < nums.length - 1) {
            int jumpLimit = nums[current];
            int rightBound = Math.min(nums.length - 1, current + jumpLimit);
            if (jumpLimit <= 0) {
                Map<String, Object> fail = baseFrame(nums);
                fail.put("active", current);
                fail.put("result", new ArrayList<>(path));
                fail.put("maxReach", globalReach);
                fail.put("found", false);
                fail.put("description", "位置 " + current + " 的最大步长为 0，无法继续前进");
                frames.add(fail);
                return response(item, "array", 260, frames);
            }
            Map<String, Object> range = baseFrame(nums);
            range.put("active", current);
            range.put("left", current + 1);
            range.put("right", rightBound);
            range.put("result", new ArrayList<>(path));
            range.put("maxReach", globalReach);
            range.put("description", "位置 " + current + " 可跳 1~" + jumpLimit + " 步，候选区间 [" + (current + 1) + ", " + rightBound + "]");
            frames.add(range);

            int bestNext = current + 1;
            int bestReach = bestNext + nums[bestNext];
            for (int candidate = current + 1; candidate <= rightBound; candidate++) {
                int candidateReach = candidate + nums[candidate];
                globalReach = Math.max(globalReach, candidateReach);
                Map<String, Object> inspect = baseFrame(nums);
                inspect.put("active", candidate);
                inspect.put("left", current + 1);
                inspect.put("right", rightBound);
                inspect.put("result", new ArrayList<>(path));
                inspect.put("maxReach", globalReach);
                inspect.put("description", "评估落点 " + candidate + "，下一步最远可达 " + candidateReach);
                frames.add(inspect);
                if (candidateReach > bestReach) {
                    bestReach = candidateReach;
                    bestNext = candidate;
                }
            }
            for (int i = 0; i <= 5; i++) {
                double progress = i / 5.0;
                Map<String, Object> jump = baseFrame(nums);
                jump.put("active", current);
                jump.put("result", new ArrayList<>(path));
                jump.put("jumpFrom", current);
                jump.put("jumpTo", bestNext);
                jump.put("jumpProgress", progress);
                jump.put("maxReach", Math.max(globalReach, bestReach));
                jump.put("description", "起跳 " + current + " -> " + bestNext + "，跳跃进度 " + Math.round(progress * 100) + "%");
                if (i == 5) {
                    path.add(bestNext);
                    jump.put("result", new ArrayList<>(path));
                    jump.put("active", bestNext);
                    jump.put("description", "落地到位置 " + bestNext + "，当前路径 " + path);
                }
                frames.add(jump);
            }
            current = bestNext;
        }
        Map<String, Object> done = baseFrame(nums);
        done.put("active", nums.length - 1);
        done.put("result", new ArrayList<>(path));
        done.put("found", true);
        done.put("maxReach", Math.max(globalReach, nums.length - 1));
        done.put("description", "到达终点，判定可达，路径 " + path);
        frames.add(done);
        return response(item, "array", 260, frames);
    }

    private Map<String, Object> buildJumpGameIIAnimation(CatalogItem item) {
        int[] nums = new int[] { 2, 3, 1, 1, 4, 2, 1 };
        List<Map<String, Object>> frames = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        int current = 0;
        int step = 0;
        int globalReach = 0;
        path.add(current);
        while (current < nums.length - 1) {
            int jumpLimit = nums[current];
            int rightBound = Math.min(nums.length - 1, current + jumpLimit);
            if (jumpLimit <= 0) {
                Map<String, Object> fail = baseFrame(nums);
                fail.put("active", current);
                fail.put("steps", step);
                fail.put("result", new ArrayList<>(path));
                fail.put("maxReach", globalReach);
                fail.put("found", false);
                fail.put("description", "第 " + step + " 步后停在位置 " + current + "，无法继续跳跃");
                frames.add(fail);
                return response(item, "array", 240, frames);
            }
            Map<String, Object> range = baseFrame(nums);
            range.put("active", current);
            range.put("left", current + 1);
            range.put("right", rightBound);
            range.put("steps", step);
            range.put("result", new ArrayList<>(path));
            range.put("maxReach", globalReach);
            range.put("description", "第 " + (step + 1) + " 步：从 " + current + " 评估候选区间 [" + (current + 1) + ", " + rightBound + "]");
            frames.add(range);

            int bestNext = current + 1;
            int bestReach = bestNext + nums[bestNext];
            for (int candidate = current + 1; candidate <= rightBound; candidate++) {
                int candidateReach = candidate + nums[candidate];
                globalReach = Math.max(globalReach, candidateReach);
                Map<String, Object> inspect = baseFrame(nums);
                inspect.put("active", candidate);
                inspect.put("left", current + 1);
                inspect.put("right", rightBound);
                inspect.put("steps", step);
                inspect.put("result", new ArrayList<>(path));
                inspect.put("maxReach", globalReach);
                inspect.put("description", "第 " + (step + 1) + " 步：落点 " + candidate + " 的后续最远可达 " + candidateReach);
                frames.add(inspect);
                if (candidateReach > bestReach) {
                    bestReach = candidateReach;
                    bestNext = candidate;
                }
            }
            step++;
            for (int i = 0; i <= 5; i++) {
                double progress = i / 5.0;
                Map<String, Object> jump = baseFrame(nums);
                jump.put("active", current);
                jump.put("steps", step);
                jump.put("result", new ArrayList<>(path));
                jump.put("jumpFrom", current);
                jump.put("jumpTo", bestNext);
                jump.put("jumpProgress", progress);
                jump.put("maxReach", Math.max(globalReach, bestReach));
                jump.put("description", "第 " + step + " 步：起跳 " + current + " -> " + bestNext + "，进度 " + Math.round(progress * 100) + "%");
                if (i == 5) {
                    path.add(bestNext);
                    jump.put("result", new ArrayList<>(path));
                    jump.put("active", bestNext);
                    jump.put("description", "第 " + step + " 步落地到 " + bestNext + "，当前路径 " + path);
                }
                frames.add(jump);
            }
            current = bestNext;
        }
        Map<String, Object> done = baseFrame(nums);
        done.put("active", nums.length - 1);
        done.put("steps", step);
        done.put("result", new ArrayList<>(path));
        done.put("found", true);
        done.put("maxReach", Math.max(globalReach, nums.length - 1));
        done.put("description", "到达终点，最少步数演示完成，步数 " + step + "，路径 " + path);
        frames.add(done);
        return response(item, "array", 240, frames);
    }

    private Map<String, Object> buildJumpGameIIIAnimation(CatalogItem item) {
        int[] nums = new int[] { 4, 2, 3, 0, 3, 1, 2 };
        int start = 5;
        List<Map<String, Object>> frames = new ArrayList<>();
        List<Integer> stack = new ArrayList<>();
        List<Integer> visitOrder = new ArrayList<>();
        boolean[] visited = new boolean[nums.length];
        stack.add(start);
        Map<String, Object> first = baseFrame(nums);
        first.put("active", start);
        first.put("result", new ArrayList<>(visitOrder));
        first.put("description", "从起点 " + start + " 开始 DFS 搜索值为 0 的位置");
        frames.add(first);

        while (!stack.isEmpty()) {
            int current = stack.remove(stack.size() - 1);
            if (visited[current]) {
                continue;
            }
            visited[current] = true;
            visitOrder.add(current);

            Map<String, Object> visit = baseFrame(nums);
            visit.put("active", current);
            visit.put("result", new ArrayList<>(visitOrder));
            visit.put("description", "访问位置 " + current + "，值为 " + nums[current]);
            frames.add(visit);
            if (nums[current] == 0) {
                Map<String, Object> found = baseFrame(nums);
                found.put("active", current);
                found.put("found", true);
                found.put("result", new ArrayList<>(visitOrder));
                found.put("description", "命中值为 0 的位置 " + current + "，判定可达");
                frames.add(found);
                return response(item, "array", 250, frames);
            }

            int forward = current + nums[current];
            int backward = current - nums[current];
            if (forward >= 0 && forward < nums.length && !visited[forward]) {
                for (int i = 0; i <= 5; i++) {
                    double progress = i / 5.0;
                    Map<String, Object> jump = baseFrame(nums);
                    jump.put("active", current);
                    jump.put("result", new ArrayList<>(visitOrder));
                    jump.put("jumpFrom", current);
                    jump.put("jumpTo", forward);
                    jump.put("jumpProgress", progress);
                    jump.put("description", "尝试向前跳： " + current + " -> " + forward + "，进度 " + Math.round(progress * 100) + "%");
                    frames.add(jump);
                }
                stack.add(forward);
            }
            if (backward >= 0 && backward < nums.length && !visited[backward]) {
                for (int i = 0; i <= 5; i++) {
                    double progress = i / 5.0;
                    Map<String, Object> jump = baseFrame(nums);
                    jump.put("active", current);
                    jump.put("result", new ArrayList<>(visitOrder));
                    jump.put("jumpFrom", current);
                    jump.put("jumpTo", backward);
                    jump.put("jumpProgress", progress);
                    jump.put("description", "尝试向后跳： " + current + " -> " + backward + "，进度 " + Math.round(progress * 100) + "%");
                    frames.add(jump);
                }
                stack.add(backward);
            }
        }
        Map<String, Object> fail = baseFrame(nums);
        fail.put("active", start);
        fail.put("found", false);
        fail.put("result", new ArrayList<>(visitOrder));
        fail.put("description", "搜索结束，未找到值为 0 的可达位置");
        frames.add(fail);
        return response(item, "array", 250, frames);
    }

    private Map<String, Object> buildBinaryInsertionAnimation(CatalogItem item) {
        List<Map<String, Object>> frames = new ArrayList<>();
        String id = problemId(item);
        int[] nums = new int[] { 1, 3, 5, 6 };
        int target = 2;
        if ("no069".equals(id)) {
            nums = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
            target = 8;
        } else if ("no035".equals(id)) {
            nums = new int[] { 1, 3, 5, 6 };
            target = 7;
        } else {
            nums = seededSortedArray(id, 6, 1, 18);
            target = nums[positiveModulo(id.hashCode() / 3 + 7, nums.length)];
        }
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            Map<String, Object> frame = baseFrame(nums);
            frame.put("description", "left=" + left + ", right=" + right + ", mid=" + mid + ", nums[mid]=" + nums[mid]);
            frame.put("left", left);
            frame.put("right", right);
            frame.put("mid", mid);
            frame.put("target", target);
            frames.add(frame);
            if ("no069".equals(id)) {
                int square = nums[mid] * nums[mid];
                frame.put("sqrtMode", true);
                frame.put("square", square);
                frame.put("description", "left=" + left + ", right=" + right + ", mid=" + mid + ", mid^2=" + square);
                if (square <= target && (mid == nums.length - 1 || nums[mid + 1] * nums[mid + 1] > target)) {
                    frame.put("result", mid);
                    frame.put("description", frame.get("description") + " -> floor(sqrt(" + target + ")) = " + mid);
                    return response(item, "array", 1200, frames);
                }
                if (square < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
                continue;
            }
            if (nums[mid] == target) {
                frame.put("result", mid);
                frame.put("description", frame.get("description") + " -> 找到目标");
                return response(item, "array", 1200, frames);
            }
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        Map<String, Object> frame = baseFrame(nums);
        frame.put("description", "循环结束，插入位置为 " + left);
        frame.put("target", target);
        frame.put("result", left);
        if ("no069".equals(id)) {
            frame.put("sqrtMode", true);
            frame.put("square", left * left);
        }
        frames.add(frame);
        return response(item, "array", 1200, frames);
    }

    private Map<String, Object> buildTwoPointersAnimation(CatalogItem item) {
        List<Map<String, Object>> frames = new ArrayList<>();
        String id = problemId(item);
        if ("no977".equals(id)) {
            return buildSortedSquaresAnimation(item);
        }
        if ("no026".equals(id)) {
            int[] nums = new int[] { 1, 1, 2, 2, 3, 4, 4 };
            int slow = 0;
            for (int fast = 1; fast < nums.length; fast++) {
                Map<String, Object> frame = baseFrame(nums);
                frame.put("left", slow);
                frame.put("right", fast);
                frame.put("active", fast);
                if (nums[fast] != nums[slow]) {
                    slow++;
                    nums[slow] = nums[fast];
                    frame.put("description", "fast=" + fast + " 发现新值 " + nums[fast] + "，写入 slow=" + slow);
                } else {
                    frame.put("description", "fast=" + fast + " 与 slow 重复，跳过");
                }
                frame.put("result", slow + 1);
                frames.add(frame);
            }
            return response(item, "array", 900, frames);
        }
        if ("no283".equals(id)) {
            int[] nums = new int[] { 0, 1, 0, 3, 12 };
            int slow = 0;
            for (int fast = 0; fast < nums.length; fast++) {
                Map<String, Object> frame = baseFrame(nums);
                frame.put("left", slow);
                frame.put("right", fast);
                frame.put("active", fast);
                if (nums[fast] != 0) {
                    int value = nums[fast];
                    nums[fast] = nums[slow];
                    nums[slow] = value;
                    frame.put("description", "fast=" + fast + " 发现非零值 " + nums[slow] + "，交换到 slow=" + slow);
                    slow++;
                } else {
                    frame.put("description", "fast=" + fast + " 是 0，继续扫描");
                }
                frame.put("array", Arrays.copyOf(nums, nums.length));
                frames.add(frame);
            }
            return response(item, "two-pointers", 900, frames);
        }
        if ("no234".equals(id)) {
            int[] values = new int[] { 1, 2, 3, 2, 1 };
            int slow = 0;
            int fast = 0;
            while (fast < values.length && fast + 1 < values.length) {
                Map<String, Object> frame = baseFrame(values);
                frame.put("left", slow);
                frame.put("right", fast);
                frame.put("active", slow);
                frame.put("description", "快慢指针推进：slow=" + slow + "，fast=" + fast);
                frames.add(frame);
                slow++;
                fast += 2;
            }
            int left = 0;
            int right = values.length - 1;
            while (left < right) {
                Map<String, Object> frame = baseFrame(values);
                frame.put("left", left);
                frame.put("right", right);
                frame.put("active", left);
                if (values[left] == values[right]) {
                    frame.put("description", "比较两端值 " + values[left] + " 和 " + values[right] + "，相同，继续");
                } else {
                    frame.put("description", "比较两端值 " + values[left] + " 和 " + values[right] + "，不相同");
                    frame.put("found", false);
                    frames.add(frame);
                    return response(item, "two-pointers", 850, frames);
                }
                frames.add(frame);
                left++;
                right--;
            }
            Map<String, Object> done = baseFrame(values);
            done.put("left", slow);
            done.put("active", slow);
            done.put("found", true);
            done.put("description", "比较完成，链表是回文结构");
            frames.add(done);
            return response(item, "two-pointers", 850, frames);
        }
        if ("no088".equals(id)) {
            int[] a = new int[] { 1, 2, 3, 0, 0, 0 };
            int[] b = new int[] { 2, 5, 6 };
            int i = 2;
            int j = 2;
            int k = 5;
            while (j >= 0) {
                Map<String, Object> frame = baseFrame(Arrays.copyOf(a, a.length));
                frame.put("left", i);
                frame.put("right", j);
                frame.put("active", k);
                if (i >= 0 && a[i] > b[j]) {
                    a[k] = a[i];
                    frame.put("description", "a[" + i + "]=" + a[k] + " 更大，放到位置 " + k);
                    i--;
                } else {
                    a[k] = b[j];
                    frame.put("description", "b[" + j + "]=" + a[k] + " 放到位置 " + k);
                    j--;
                }
                frame.put("array", Arrays.copyOf(a, a.length));
                frames.add(frame);
                k--;
            }
            return response(item, "two-pointers", 900, frames);
        }
        if ("no015".equals(id)) {
            int[] nums = new int[] { -4, -1, -1, 0, 1, 2 };
            for (int i = 0; i < nums.length - 2; i++) {
                int left = i + 1;
                int right = nums.length - 1;
                while (left < right) {
                    int sum = nums[i] + nums[left] + nums[right];
                    Map<String, Object> frame = baseFrame(nums);
                    frame.put("active", i);
                    frame.put("left", left);
                    frame.put("right", right);
                    frame.put("description", "固定 i=" + i + "，检查三元组和 " + nums[i] + "+" + nums[left] + "+" + nums[right]
                            + "=" + sum);
                    if (sum == 0) {
                        frame.put("result", list(i, left));
                        frame.put("description", frame.get("description") + " -> 找到一组解");
                        frames.add(frame);
                        left++;
                        right--;
                        break;
                    }
                    frames.add(frame);
                    if (sum < 0) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
            return response(item, "two-pointers", 900, frames);
        }
        int[] nums = seededSortedArray(id, 8, -8, 14);
        int left = 0;
        int right = nums.length - 1;
        int target = nums[left] + nums[right];
        while (left < right) {
            int sum = nums[left] + nums[right];
            Map<String, Object> frame = baseFrame(nums);
            frame.put("left", left);
            frame.put("right", right);
            frame.put("active", left);
            if (sum == target) {
                frame.put("found", true);
                frame.put("result", list(left, right));
                frame.put("description", "检查 pair(" + nums[left] + "," + nums[right] + ")，和为 " + sum + "，命中目标 " + target);
                frames.add(frame);
                break;
            }
            if (sum < target) {
                frame.put("description", "检查 pair(" + nums[left] + "," + nums[right] + ")，和为 " + sum + "，小于目标 " + target + "，left++");
                left++;
            } else {
                frame.put("description", "检查 pair(" + nums[left] + "," + nums[right] + ")，和为 " + sum + "，大于目标 " + target + "，right--");
                right--;
            }
            frames.add(frame);
        }
        if (frames.isEmpty()) {
            Map<String, Object> frame = baseFrame(nums);
            frame.put("description", "双指针演示初始化完成");
            frames.add(frame);
        }
        return response(item, "two-pointers", 850, frames);
    }

    private Map<String, Object> buildSortedSquaresAnimation(CatalogItem item) {
        int[] nums = new int[] { -7, -3, 2, 3, 11 };
        int[] result = new int[nums.length];
        List<Map<String, Object>> frames = new ArrayList<>();
        int left = 0;
        int right = nums.length - 1;
        for (int pos = nums.length - 1; pos >= 0; pos--) {
            int leftSquare = nums[left] * nums[left];
            int rightSquare = nums[right] * nums[right];
            Map<String, Object> frame = baseFrame(Arrays.copyOf(result, result.length));
            frame.put("source", Arrays.copyOf(nums, nums.length));
            frame.put("left", left);
            frame.put("right", right);
            frame.put("active", pos);
            frame.put("mode", "sorted-squares");
            frame.put("leftSquare", leftSquare);
            frame.put("rightSquare", rightSquare);
            if (leftSquare > rightSquare) {
                result[pos] = leftSquare;
                frame.put("description", "比较 |" + nums[left] + "|²=" + leftSquare + " 与 |" + nums[right] + "|²=" + rightSquare + "，填入 result[" + pos + "]");
                left++;
            } else {
                result[pos] = rightSquare;
                frame.put("description", "比较 |" + nums[left] + "|²=" + leftSquare + " 与 |" + nums[right] + "|²=" + rightSquare + "，填入 result[" + pos + "]");
                right--;
            }
            frame.put("array", Arrays.copyOf(result, result.length));
            frames.add(frame);
        }
        Map<String, Object> done = baseFrame(Arrays.copyOf(result, result.length));
        done.put("source", Arrays.copyOf(nums, nums.length));
        done.put("mode", "sorted-squares");
        done.put("result", Arrays.stream(result).boxed().collect(Collectors.toList()));
        done.put("found", true);
        done.put("description", "完成填充，得到有序平方数组");
        frames.add(done);
        return response(item, "two-pointers", 900, frames);
    }

    private Map<String, Object> buildDpAnimation(CatalogItem item) {
        List<Map<String, Object>> frames = new ArrayList<>();
        String id = problemId(item);
        if ("no118".equals(id)) {
            List<Integer> row = new ArrayList<>();
            row.add(1);
            for (int r = 1; r <= 6; r++) {
                List<Integer> next = new ArrayList<>();
                next.add(1);
                for (int i = 1; i < row.size(); i++) {
                    next.add(row.get(i - 1) + row.get(i));
                }
                next.add(1);
                int[] arr = next.stream().mapToInt(Integer::intValue).toArray();
                Map<String, Object> frame = baseFrame(arr);
                frame.put("active", r - 1);
                frame.put("profit", arr.length);
                frame.put("description", "第 " + r + " 行杨辉三角: " + next);
                frames.add(frame);
                row = next;
            }
            return response(item, "dp", 900, frames);
        }
        if ("no322".equals(id)) {
            int[] coins = new int[] { 1, 2, 5 };
            int amount = 11;
            int[] dp = new int[amount + 1];
            Arrays.fill(dp, amount + 1);
            dp[0] = 0;
            for (int i = 1; i <= amount; i++) {
                int bestCoin = -1;
                for (int coin : coins) {
                    Map<String, Object> frame = baseFrame(Arrays.copyOf(dp, dp.length));
                    frame.put("mode", "coin-change");
                    frame.put("coins", Arrays.stream(coins).boxed().collect(Collectors.toList()));
                    frame.put("amount", amount);
                    frame.put("active", i);
                    frame.put("activeCoin", coin);
                    frame.put("prevAmount", i - coin);
                    if (coin > i || dp[i - coin] > amount) {
                        frame.put("candidate", -1);
                        frame.put("description", "金额 " + i + " 尝试硬币 " + coin + "：无法组成有效状态");
                        frames.add(frame);
                        continue;
                    }
                    int candidate = dp[i - coin] + 1;
                    frame.put("candidate", candidate);
                    if (candidate < dp[i]) {
                        dp[i] = candidate;
                        bestCoin = coin;
                        frame.put("description", "金额 " + i + " 尝试硬币 " + coin + "：更新最优为 " + candidate);
                    } else {
                        frame.put("description", "金额 " + i + " 尝试硬币 " + coin + "：保持当前最优 " + dp[i]);
                    }
                    frame.put("array", Arrays.copyOf(dp, dp.length));
                    frame.put("pickedCoin", bestCoin);
                    frame.put("profit", dp[i] > amount ? -1 : dp[i]);
                    frames.add(frame);
                }
                Map<String, Object> settle = baseFrame(Arrays.copyOf(dp, dp.length));
                settle.put("mode", "coin-change");
                settle.put("coins", Arrays.stream(coins).boxed().collect(Collectors.toList()));
                settle.put("amount", amount);
                settle.put("active", i);
                settle.put("pickedCoin", bestCoin);
                settle.put("profit", dp[i] > amount ? -1 : dp[i]);
                settle.put("description", "完成金额 " + i + "：最少硬币数 = " + (dp[i] > amount ? "INF" : dp[i]));
                frames.add(settle);
            }
            Map<String, Object> done = baseFrame(Arrays.copyOf(dp, dp.length));
            done.put("mode", "coin-change");
            done.put("coins", Arrays.stream(coins).boxed().collect(Collectors.toList()));
            done.put("amount", amount);
            done.put("active", amount);
            done.put("result", dp[amount] > amount ? -1 : dp[amount]);
            done.put("pickedCoin", -1);
            done.put("found", dp[amount] <= amount);
            done.put("description", "兑换完成：金额 " + amount + " 的最少硬币数 = " + (dp[amount] > amount ? -1 : dp[amount]));
            frames.add(done);
            return response(item, "dp", 900, frames);
        }
        if ("no403".equals(id)) {
            int[] stones = new int[] { 0, 1, 3, 5, 6, 8, 12, 17 };
            int[][] jumps = new int[][] { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 3, 5 }, { 5, 6 }, { 6, 7 } };
            Map<String, Object> start = baseFrame(Arrays.copyOf(stones, stones.length));
            start.put("mode", "frog-jump");
            start.put("frogIndex", 0);
            start.put("lastJump", 0);
            start.put("description", "青蛙在石头 0，第一跳必须是 1");
            frames.add(start);
            for (int[] jump : jumps) {
                int from = jump[0];
                int to = jump[1];
                int step = stones[to] - stones[from];
                double[] progress = new double[] { 0.0, 0.35, 0.7, 1.0 };
                for (double p : progress) {
                    Map<String, Object> frame = baseFrame(Arrays.copyOf(stones, stones.length));
                    frame.put("mode", "frog-jump");
                    frame.put("frogIndex", p >= 1.0 ? to : from);
                    frame.put("jumpFrom", from);
                    frame.put("jumpTo", to);
                    frame.put("jumpProgress", p);
                    frame.put("lastJump", step);
                    frame.put("active", to);
                    frame.put("description", "从石头 " + stones[from] + " 跳到 " + stones[to] + "，步长 " + step);
                    frames.add(frame);
                }
            }
            Map<String, Object> done = baseFrame(Arrays.copyOf(stones, stones.length));
            done.put("mode", "frog-jump");
            done.put("frogIndex", stones.length - 1);
            done.put("lastJump", stones[stones.length - 1] - stones[stones.length - 2]);
            done.put("found", true);
            done.put("result", true);
            done.put("description", "成功到达最后一块石头，返回 true");
            frames.add(done);
            return response(item, "dp", 500, frames);
        }
        if ("no139".equals(id)) {
            String s = "leetcode";
            boolean[] ok = new boolean[s.length() + 1];
            ok[0] = true;
            for (int i = 1; i <= s.length(); i++) {
                if ((i == 4 && ok[0]) || (i == 8 && ok[4])) {
                    ok[i] = true;
                }
                int[] arr = new int[ok.length];
                for (int j = 0; j < ok.length; j++) {
                    arr[j] = ok[j] ? 1 : 0;
                }
                Map<String, Object> frame = baseFrame(arr);
                frame.put("active", i);
                frame.put("profit", ok[i] ? 1 : 0);
                frame.put("description", "前缀 [0," + i + ") 是否可拆分: " + ok[i]);
                frames.add(frame);
            }
            return response(item, "dp", 900, frames);
        }
        if ("no915".equals(id)) {
            int[] nums = new int[] { 5, 0, 3, 8, 6 };
            int leftMax = nums[0];
            int globalMax = nums[0];
            int partition = 0;
            for (int i = 1; i < nums.length; i++) {
                globalMax = Math.max(globalMax, nums[i]);
                if (nums[i] < leftMax) {
                    leftMax = globalMax;
                    partition = i;
                }
                Map<String, Object> frame = baseFrame(nums);
                frame.put("active", i);
                frame.put("left", partition);
                frame.put("profit", leftMax);
                frame.put("description", "扫描 i=" + i + "，当前分割点=" + partition + "，leftMax=" + leftMax);
                frames.add(frame);
            }
            return response(item, "dp", 900, frames);
        }
        int[] nums = seededArray(id, 9, -5, 9);
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int best = dp[0];
        Map<String, Object> first = baseFrame(Arrays.copyOf(dp, dp.length));
        first.put("active", 0);
        first.put("profit", best);
        first.put("description", "dp[0]=" + dp[0] + "，初始化");
        frames.add(first);
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
            best = Math.max(best, dp[i]);
            Map<String, Object> frame = baseFrame(Arrays.copyOf(dp, dp.length));
            frame.put("active", i);
            frame.put("profit", best);
            frame.put("description", "dp[" + i + "] = max(" + nums[i] + ", " + dp[i - 1] + "+" + nums[i] + ") = " + dp[i]);
            frames.add(frame);
        }
        return response(item, "dp", 900, frames);
    }

    private Map<String, Object> buildBacktrackingAnimation(CatalogItem item) {
        List<Map<String, Object>> frames = new ArrayList<>();
        String id = problemId(item);
        if ("面试题-08-09-括号".equals(id)) {
            String[] trace = new String[] { "(", "((", "(()", "(())", "(())(", "(())()" };
            for (int i = 0; i < trace.length; i++) {
                Map<String, Object> frame = baseFrame(new int[] { 1, 2, 3, 4 });
                frame.put("active", i % 4);
                frame.put("result", trace[i].chars().map(c -> c == '(' ? 1 : 2).boxed().collect(Collectors.toList()));
                frame.put("description", "构造括号串: " + trace[i]);
                frames.add(frame);
            }
            return response(item, "backtracking", 1000, frames);
        }
        if ("no-51-n皇后".equals(id) || "no-52-n皇后-ii".equals(id)) {
            int[] queenCols = new int[] { 1, 3, 0, 2 };
            List<Integer> path = new ArrayList<>();
            for (int row = 0; row < queenCols.length; row++) {
                path.add(queenCols[row]);
                Map<String, Object> frame = baseFrame(new int[] { 0, 1, 2, 3 });
                frame.put("active", row);
                frame.put("result", new ArrayList<>(path));
                frame.put("description", "在第 " + (row + 1) + " 行第 " + (queenCols[row] + 1) + " 列放置皇后");
                frames.add(frame);
            }
            if ("no-52-n皇后-ii".equals(id)) {
                Map<String, Object> frame = baseFrame(new int[] { 0, 1, 2, 3 });
                frame.put("active", 0);
                frame.put("profit", 2);
                frame.put("description", "统计完成，4 皇后共有 2 组解");
                frames.add(frame);
            }
            return response(item, "backtracking", 1000, frames);
        }
        if ("no046".equals(id)) {
            int[] nums = new int[] { 1, 2, 3 };
            List<Integer> path = new ArrayList<>();
            boolean[] used = new boolean[nums.length];
            List<List<Integer>> perms = new ArrayList<>();
            Map<String, Object> start = baseFrame(Arrays.copyOf(nums, nums.length));
            start.put("mode", "permutation-tree");
            start.put("active", -1);
            start.put("depth", 0);
            start.put("path", new ArrayList<>(path));
            start.put("used", new int[] { 0, 0, 0 });
            start.put("permutations", new ArrayList<>(perms));
            start.put("description", "从空路径开始，依次选择未使用数字");
            frames.add(start);
            for (int i = 0; i < nums.length; i++) {
                used[i] = true;
                path.add(nums[i]);
                Map<String, Object> choose1 = baseFrame(Arrays.copyOf(nums, nums.length));
                choose1.put("mode", "permutation-tree");
                choose1.put("active", i);
                choose1.put("depth", path.size());
                choose1.put("path", new ArrayList<>(path));
                choose1.put("used", toIntArray(used));
                choose1.put("permutations", perms.stream().map(ArrayList::new).collect(Collectors.toList()));
                choose1.put("description", "深度 " + path.size() + "：选择 " + nums[i]);
                frames.add(choose1);
                for (int j = 0; j < nums.length; j++) {
                    if (used[j]) {
                        continue;
                    }
                    used[j] = true;
                    path.add(nums[j]);
                    Map<String, Object> choose2 = baseFrame(Arrays.copyOf(nums, nums.length));
                    choose2.put("mode", "permutation-tree");
                    choose2.put("active", j);
                    choose2.put("depth", path.size());
                    choose2.put("path", new ArrayList<>(path));
                    choose2.put("used", toIntArray(used));
                    choose2.put("permutations", perms.stream().map(ArrayList::new).collect(Collectors.toList()));
                    choose2.put("description", "深度 " + path.size() + "：继续选择 " + nums[j]);
                    frames.add(choose2);
                    for (int k = 0; k < nums.length; k++) {
                        if (used[k]) {
                            continue;
                        }
                        used[k] = true;
                        path.add(nums[k]);
                        perms.add(new ArrayList<>(path));
                        Map<String, Object> full = baseFrame(Arrays.copyOf(nums, nums.length));
                        full.put("mode", "permutation-tree");
                        full.put("active", k);
                        full.put("depth", path.size());
                        full.put("path", new ArrayList<>(path));
                        full.put("used", toIntArray(used));
                        full.put("permutations", perms.stream().map(ArrayList::new).collect(Collectors.toList()));
                        full.put("profit", perms.size());
                        full.put("description", "得到一个全排列 " + path);
                        frames.add(full);
                        path.remove(path.size() - 1);
                        used[k] = false;
                        Map<String, Object> back3 = baseFrame(Arrays.copyOf(nums, nums.length));
                        back3.put("mode", "permutation-tree");
                        back3.put("active", k);
                        back3.put("depth", path.size());
                        back3.put("path", new ArrayList<>(path));
                        back3.put("used", toIntArray(used));
                        back3.put("permutations", perms.stream().map(ArrayList::new).collect(Collectors.toList()));
                        back3.put("description", "回溯撤销 " + nums[k] + "，返回上一层");
                        frames.add(back3);
                    }
                    path.remove(path.size() - 1);
                    used[j] = false;
                    Map<String, Object> back2 = baseFrame(Arrays.copyOf(nums, nums.length));
                    back2.put("mode", "permutation-tree");
                    back2.put("active", j);
                    back2.put("depth", path.size());
                    back2.put("path", new ArrayList<>(path));
                    back2.put("used", toIntArray(used));
                    back2.put("permutations", perms.stream().map(ArrayList::new).collect(Collectors.toList()));
                    back2.put("description", "回溯撤销 " + nums[j] + "，继续尝试同层其他数字");
                    frames.add(back2);
                }
                path.remove(path.size() - 1);
                used[i] = false;
                Map<String, Object> back1 = baseFrame(Arrays.copyOf(nums, nums.length));
                back1.put("mode", "permutation-tree");
                back1.put("active", i);
                back1.put("depth", path.size());
                back1.put("path", new ArrayList<>(path));
                back1.put("used", toIntArray(used));
                back1.put("permutations", perms.stream().map(ArrayList::new).collect(Collectors.toList()));
                back1.put("description", "撤销首层选择 " + nums[i] + "，切换到下一个起点");
                frames.add(back1);
            }
            Map<String, Object> done = baseFrame(Arrays.copyOf(nums, nums.length));
            done.put("mode", "permutation-tree");
            done.put("path", new ArrayList<>(path));
            done.put("used", new int[] { 0, 0, 0 });
            done.put("permutations", perms.stream().map(ArrayList::new).collect(Collectors.toList()));
            done.put("profit", perms.size());
            done.put("found", true);
            done.put("description", "搜索结束，共生成 " + perms.size() + " 个全排列");
            frames.add(done);
            return response(item, "backtracking", 1000, frames);
        }
        int[] choices = seededArray(id, 4, 1, 9);
        List<Integer> path = new ArrayList<>();
        for (int i = 0; i < choices.length; i++) {
            path.add(choices[i]);
            Map<String, Object> frame = baseFrame(choices);
            frame.put("active", i);
            frame.put("result", new ArrayList<>(path));
            frame.put("description", "选择 " + choices[i] + "，当前路径 " + path);
            frames.add(frame);
            if (path.size() >= 3) {
                int removed = path.remove(path.size() - 1);
                Map<String, Object> back = baseFrame(choices);
                back.put("active", i);
                back.put("result", new ArrayList<>(path));
                back.put("description", "回溯撤销 " + removed + "，恢复路径 " + path);
                frames.add(back);
            }
        }
        return response(item, "backtracking", 1000, frames);
    }

    private Map<String, Object> buildTreeAnimation(CatalogItem item) {
        List<Map<String, Object>> frames = new ArrayList<>();
        String id = problemId(item);
        int[] order;
        if ("no144".equals(id)) {
            order = new int[] { 8, 4, 2, 6, 12, 10, 14 };
        } else if ("no094".equals(id)) {
            order = new int[] { 2, 4, 6, 8, 10, 12, 14 };
        } else if ("no145".equals(id)) {
            order = new int[] { 2, 6, 4, 10, 14, 12, 8 };
        } else if ("no199".equals(id)) {
            order = new int[] { 8, 12, 14 };
        } else if ("no104".equals(id)) {
            order = new int[] { 1, 2, 3 };
        } else if ("no108".equals(id)) {
            order = new int[] { 0, -10, -3, 5, 9 };
        } else if ("面试题-04-05-合法二叉搜索树".equals(id)) {
            order = new int[] { 5, 1, 4, 3, 6 };
        } else if ("面试题-04-10-检查子树".equals(id)) {
            order = new int[] { 3, 4, 5, 1, 2 };
        } else if ("面试题-04-06-后继者".equals(id)) {
            order = new int[] { 5, 3, 2, 4, 6, 7 };
        } else if ("面试题-04-12-求和路径".equals(id)) {
            order = new int[] { 10, 5, -3, 3, 2, 11 };
        } else if ("二叉树的顶层视图".equals(id)) {
            order = new int[] { 1, 2, 3, 6 };
        } else {
            order = seededArray(id, 7, 1, 20);
        }
        for (int i = 0; i < order.length; i++) {
            Map<String, Object> frame = baseFrame(order);
            frame.put("active", i);
            frame.put("description", "访问节点 " + order[i] + "，遍历序列长度 " + (i + 1));
            frames.add(frame);
        }
        return response(item, "tree", 850, frames);
    }

    private Map<String, Object> buildGraphAnimation(CatalogItem item) {
        List<Map<String, Object>> frames = new ArrayList<>();
        String id = problemId(item);
        int[] nodes = new int[] { 0, 1, 2, 3, 4, 5 };
        if ("no200".equals(id)) {
            nodes = new int[] { 1, 2, 3, 4 };
        } else if ("no463".equals(id)) {
            nodes = new int[] { 0, 2, 4, 6, 8, 10 };
        } else if ("no1306".equals(id)) {
            nodes = new int[] { 5, 4, 1, 3, 0 };
        } else if ("no1654".equals(id)) {
            nodes = new int[] { 0, 3, 6, 8, 9, 11 };
        } else {
            nodes = seededArray(id, 6, 0, 12);
        }
        boolean bfs = item.getTechnique() != null && item.getTechnique().toLowerCase(Locale.ROOT).contains("bfs");
        for (int i = 0; i < nodes.length; i++) {
            Map<String, Object> frame = baseFrame(nodes);
            frame.put("active", i);
            if (bfs) {
                frame.put("description", "BFS 出队节点 " + nodes[i] + "，按层扩展邻接点");
            } else {
                frame.put("description", "DFS 深入节点 " + nodes[i] + "，继续递归");
            }
            frames.add(frame);
        }
        return response(item, "graph", 950, frames);
    }

    private Map<String, Object> buildIslandPerimeterAnimation(CatalogItem item) {
        int[][] grid = new int[][] {
                { 0, 1, 0, 0 },
                { 1, 1, 1, 0 },
                { 0, 1, 0, 0 },
                { 1, 1, 0, 0 }
        };
        int rows = grid.length;
        int cols = grid[0].length;
        int[] dr = new int[] { 0, 1, 0, -1 };
        int[] dc = new int[] { 1, 0, -1, 0 };
        String[] dirNames = new String[] { "右", "下", "左", "上" };
        List<Map<String, Object>> frames = new ArrayList<>();
        int perimeter = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Map<String, Object> scan = baseGridFrame(grid);
                scan.put("activeRow", r);
                scan.put("activeCol", c);
                scan.put("perimeter", perimeter);
                if (grid[r][c] == 0) {
                    scan.put("description", "扫描格子 (" + r + "," + c + ")：水域，跳过");
                    frames.add(scan);
                    continue;
                }
                scan.put("description", "扫描格子 (" + r + "," + c + ")：陆地，开始检查四条边");
                frames.add(scan);

                int cellContribution = 0;
                for (int d = 0; d < 4; d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];
                    boolean out = nr < 0 || nr >= rows || nc < 0 || nc >= cols;
                    boolean water = !out && grid[nr][nc] == 0;
                    int add = (out || water) ? 1 : 0;
                    cellContribution += add;
                    perimeter += add;

                    Map<String, Object> edge = baseGridFrame(grid);
                    edge.put("activeRow", r);
                    edge.put("activeCol", c);
                    edge.put("neighborRow", nr);
                    edge.put("neighborCol", nc);
                    edge.put("direction", dirNames[d]);
                    edge.put("edgeAdded", add);
                    edge.put("cellContribution", cellContribution);
                    edge.put("perimeter", perimeter);
                    if (out) {
                        edge.put("description", "检查" + dirNames[d] + "边：越界，周长 +1（累计 " + perimeter + "）");
                    } else if (water) {
                        edge.put("description", "检查" + dirNames[d] + "边：邻格是水，周长 +1（累计 " + perimeter + "）");
                    } else {
                        edge.put("description", "检查" + dirNames[d] + "边：邻格是陆地，周长 +0（累计 " + perimeter + "）");
                    }
                    frames.add(edge);
                }

                Map<String, Object> done = baseGridFrame(grid);
                done.put("activeRow", r);
                done.put("activeCol", c);
                done.put("cellContribution", cellContribution);
                done.put("perimeter", perimeter);
                done.put("description", "格子 (" + r + "," + c + ") 贡献周长 " + cellContribution + "，当前总周长 " + perimeter);
                frames.add(done);
            }
        }
        Map<String, Object> result = baseGridFrame(grid);
        result.put("perimeter", perimeter);
        result.put("result", perimeter);
        result.put("found", true);
        result.put("description", "遍历完成，岛屿总周长 = " + perimeter);
        frames.add(result);
        return response(item, "graph", 420, frames);
    }

    private Map<String, Object> buildNumberOfIslandsAnimation(CatalogItem item) {
        int[][] grid = new int[][] {
                { 1, 1, 1, 1, 0 },
                { 1, 1, 0, 1, 0 },
                { 1, 1, 0, 0, 0 },
                { 0, 0, 0, 0, 0 }
        };
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int[][] islandMarks = new int[rows][cols];
        int[] dr = new int[] { -1, 1, 0, 0 };
        int[] dc = new int[] { 0, 0, -1, 1 };
        List<Map<String, Object>> frames = new ArrayList<>();
        int islandCount = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Map<String, Object> scan = baseGridFrame(grid);
                scan.put("activeRow", r);
                scan.put("activeCol", c);
                scan.put("visited", copyVisited(visited));
                scan.put("islandMarks", copyMatrix(islandMarks));
                scan.put("islandCount", islandCount);
                if (grid[r][c] == 0) {
                    scan.put("description", "扫描 (" + r + "," + c + ")：水域，跳过");
                    frames.add(scan);
                    continue;
                }
                if (visited[r][c]) {
                    scan.put("description", "扫描 (" + r + "," + c + ")：已标记过，属于已有岛屿");
                    frames.add(scan);
                    continue;
                }
                islandCount++;
                scan.put("islandCount", islandCount);
                scan.put("description", "在 (" + r + "," + c + ") 发现第 " + islandCount + " 个岛屿，开始 DFS 标记");
                frames.add(scan);

                List<int[]> stack = new ArrayList<>();
                stack.add(new int[] { r, c });
                while (!stack.isEmpty()) {
                    int[] cell = stack.remove(stack.size() - 1);
                    int cr = cell[0];
                    int cc = cell[1];
                    if (cr < 0 || cr >= rows || cc < 0 || cc >= cols || grid[cr][cc] == 0 || visited[cr][cc]) {
                        continue;
                    }
                    visited[cr][cc] = true;
                    islandMarks[cr][cc] = islandCount;

                    Map<String, Object> mark = baseGridFrame(grid);
                    mark.put("activeRow", cr);
                    mark.put("activeCol", cc);
                    mark.put("visited", copyVisited(visited));
                    mark.put("islandMarks", copyMatrix(islandMarks));
                    mark.put("islandCount", islandCount);
                    mark.put("description", "DFS 访问陆地 (" + cr + "," + cc + ")，标记为岛屿 #" + islandCount);
                    frames.add(mark);

                    for (int d = 0; d < 4; d++) {
                        int nr = cr + dr[d];
                        int nc = cc + dc[d];
                        stack.add(new int[] { nr, nc });
                    }
                }
            }
        }
        Map<String, Object> done = baseGridFrame(grid);
        done.put("visited", copyVisited(visited));
        done.put("islandMarks", copyMatrix(islandMarks));
        done.put("islandCount", islandCount);
        done.put("result", islandCount);
        done.put("found", true);
        done.put("description", "扫描结束，岛屿总数 = " + islandCount);
        frames.add(done);
        return response(item, "graph", 420, frames);
    }

    private Map<String, Object> buildHashAnimation(CatalogItem item) {
        List<Map<String, Object>> frames = new ArrayList<>();
        String id = problemId(item);
        int[] values = new int[] { 7, 1, 5, 3, 6, 4 };
        if ("no705".equals(id)) {
            values = new int[] { 1, 2, 3, 2, 4, 1 };
        } else if ("no706".equals(id)) {
            values = new int[] { 10, 20, 10, 30, 20, 40 };
        } else {
            values = seededArray(id, 6, 1, 20);
        }
        Map<Integer, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < values.length; i++) {
            if ("no706".equals(id)) {
                map.put(values[i], values[i] + 100);
            } else {
                map.put(values[i], i);
            }
            Map<String, Object> frame = baseFrame(values);
            frame.put("active", i);
            frame.put("mapValues", new ArrayList<>(map.keySet()));
            if ("no705".equals(id) && i % 3 == 2) {
                frame.put("description", "检查元素 " + values[i] + " 是否存在，当前集合大小 " + map.size());
            } else {
                frame.put("description", "处理 key=" + values[i] + "，当前桶元素数 " + map.size());
            }
            frames.add(frame);
        }
        return response(item, "hash", 900, frames);
    }

    private Map<String, Object> buildQueueStackAnimation(CatalogItem item) {
        List<Map<String, Object>> frames = new ArrayList<>();
        List<Integer> stack = new ArrayList<>();
        int[] pushValues = new int[] { 1, 2, 3 };
        for (int value : pushValues) {
            stack.add(value);
            Map<String, Object> frame = baseFrame(stack.stream().mapToInt(Integer::intValue).toArray());
            frame.put("active", stack.size() - 1);
            frame.put("description", "push(" + value + ")：队列模拟入栈，当前栈顶为 " + stack.get(stack.size() - 1));
            frames.add(frame);
        }
        Map<String, Object> topFrame = baseFrame(stack.stream().mapToInt(Integer::intValue).toArray());
        topFrame.put("active", stack.size() - 1);
        topFrame.put("result", stack.get(stack.size() - 1));
        topFrame.put("description", "top()：读取栈顶元素 " + stack.get(stack.size() - 1));
        frames.add(topFrame);

        int popped = stack.remove(stack.size() - 1);
        Map<String, Object> popFrame = baseFrame(stack.stream().mapToInt(Integer::intValue).toArray());
        popFrame.put("active", stack.isEmpty() ? 0 : stack.size() - 1);
        popFrame.put("result", popped);
        popFrame.put("description", "pop()：弹出 " + popped + "，剩余元素 " + stack);
        frames.add(popFrame);

        Map<String, Object> emptyFrame = baseFrame(stack.stream().mapToInt(Integer::intValue).toArray());
        emptyFrame.put("found", stack.isEmpty());
        emptyFrame.put("description", "empty()：当前返回 " + stack.isEmpty());
        frames.add(emptyFrame);
        return response(item, "stack", 950, frames);
    }

    private Map<String, Object> buildHeapAnimation(CatalogItem item) {
        List<Map<String, Object>> frames = new ArrayList<>();
        String id = problemId(item);
        if ("no023".equals(id)) {
            int[][] lists = new int[][] { { 1, 4, 5 }, { 1, 3, 4 }, { 2, 6 } };
            int[] pointers = new int[] { 0, 0, 0 };
            List<int[]> heap = new ArrayList<>();
            List<Integer> merged = new ArrayList<>();
            Map<String, Object> start = baseFrame(new int[] {});
            start.put("mode", "merge-k-lists");
            start.put("lists", Arrays.stream(lists)
                    .map(arr -> Arrays.stream(arr).boxed().collect(Collectors.toList()))
                    .collect(Collectors.toList()));
            start.put("listPointers", Arrays.copyOf(pointers, pointers.length));
            start.put("heap", new ArrayList<Integer>());
            start.put("heapFrom", new ArrayList<Integer>());
            start.put("merged", new ArrayList<Integer>());
            start.put("description", "初始化：将每条链表头结点压入最小堆");
            frames.add(start);
            for (int i = 0; i < lists.length; i++) {
                heap.add(new int[] { lists[i][0], i });
                pointers[i] = 1;
                heap.sort((a, b) -> Integer.compare(a[0], b[0]));
                Map<String, Object> push = baseFrame(merged.stream().mapToInt(Integer::intValue).toArray());
                push.put("mode", "merge-k-lists");
                push.put("lists", Arrays.stream(lists)
                        .map(arr -> Arrays.stream(arr).boxed().collect(Collectors.toList()))
                        .collect(Collectors.toList()));
                push.put("listPointers", Arrays.copyOf(pointers, pointers.length));
                push.put("heap", heap.stream().map(a -> a[0]).collect(Collectors.toList()));
                push.put("heapFrom", heap.stream().map(a -> a[1]).collect(Collectors.toList()));
                push.put("merged", new ArrayList<>(merged));
                push.put("activeList", i);
                push.put("description", "压入链表 " + (i + 1) + " 的头结点 " + lists[i][0]);
                frames.add(push);
            }
            while (!heap.isEmpty()) {
                heap.sort((a, b) -> Integer.compare(a[0], b[0]));
                int[] node = heap.remove(0);
                int value = node[0];
                int from = node[1];
                merged.add(value);
                Map<String, Object> pop = baseFrame(merged.stream().mapToInt(Integer::intValue).toArray());
                pop.put("mode", "merge-k-lists");
                pop.put("lists", Arrays.stream(lists)
                        .map(arr -> Arrays.stream(arr).boxed().collect(Collectors.toList()))
                        .collect(Collectors.toList()));
                pop.put("listPointers", Arrays.copyOf(pointers, pointers.length));
                pop.put("heap", heap.stream().map(a -> a[0]).collect(Collectors.toList()));
                pop.put("heapFrom", heap.stream().map(a -> a[1]).collect(Collectors.toList()));
                pop.put("merged", new ArrayList<>(merged));
                pop.put("activeList", from);
                pop.put("result", value);
                pop.put("description", "弹出最小值 " + value + "，接到结果链表尾部");
                frames.add(pop);
                if (pointers[from] < lists[from].length) {
                    int next = lists[from][pointers[from]];
                    pointers[from]++;
                    heap.add(new int[] { next, from });
                    heap.sort((a, b) -> Integer.compare(a[0], b[0]));
                    Map<String, Object> refill = baseFrame(merged.stream().mapToInt(Integer::intValue).toArray());
                    refill.put("mode", "merge-k-lists");
                    refill.put("lists", Arrays.stream(lists)
                            .map(arr -> Arrays.stream(arr).boxed().collect(Collectors.toList()))
                            .collect(Collectors.toList()));
                    refill.put("listPointers", Arrays.copyOf(pointers, pointers.length));
                    refill.put("heap", heap.stream().map(a -> a[0]).collect(Collectors.toList()));
                    refill.put("heapFrom", heap.stream().map(a -> a[1]).collect(Collectors.toList()));
                    refill.put("merged", new ArrayList<>(merged));
                    refill.put("activeList", from);
                    refill.put("description", "将链表 " + (from + 1) + " 的下一个节点 " + next + " 压回最小堆");
                    frames.add(refill);
                }
            }
            Map<String, Object> done = baseFrame(merged.stream().mapToInt(Integer::intValue).toArray());
            done.put("mode", "merge-k-lists");
            done.put("lists", Arrays.stream(lists)
                    .map(arr -> Arrays.stream(arr).boxed().collect(Collectors.toList()))
                    .collect(Collectors.toList()));
            done.put("listPointers", Arrays.copyOf(pointers, pointers.length));
            done.put("heap", new ArrayList<Integer>());
            done.put("heapFrom", new ArrayList<Integer>());
            done.put("merged", new ArrayList<>(merged));
            done.put("result", new ArrayList<>(merged));
            done.put("found", true);
            done.put("description", "最小堆为空，合并完成");
            frames.add(done);
            return response(item, "heap", 900, frames);
        }
        int[] stream = new int[] { 4, 5, 8, 2, 3, 10, 9 };
        int k = 3;
        List<Integer> minHeap = new ArrayList<>();
        for (int value : stream) {
            minHeap.add(value);
            minHeap.sort(Integer::compareTo);
            while (minHeap.size() > k) {
                minHeap.remove(0);
            }
            int kth = minHeap.get(0);
            Map<String, Object> frame = baseFrame(minHeap.stream().mapToInt(Integer::intValue).toArray());
            frame.put("active", 0);
            frame.put("profit", kth);
            frame.put("description", "add(" + value + ") 后维护大小为 " + k + " 的最小堆，第 " + k + " 大是 " + kth);
            frames.add(frame);
        }
        return response(item, "heap", 900, frames);
    }

    private Map<String, Object> buildSpecializedAnimation(CatalogItem item) {
        List<Map<String, Object>> frames = new ArrayList<>();
        String id = problemId(item);
        if ("no009".equals(id)) {
            int value = 12321;
            String text = String.valueOf(value);
            int left = 0;
            int right = text.length() - 1;
            int[] digits = text.chars().map(c -> c - '0').toArray();
            while (left <= right) {
                Map<String, Object> frame = baseFrame(digits);
                frame.put("left", left);
                frame.put("right", right);
                frame.put("active", left);
                frame.put("description", "比较第 " + (left + 1) + " 位和倒数第 " + (text.length() - right) + " 位："
                        + text.charAt(left) + " vs " + text.charAt(right));
                frames.add(frame);
                left++;
                right--;
            }
            Map<String, Object> done = baseFrame(digits);
            done.put("found", true);
            done.put("result", true);
            done.put("description", value + " 正反读取一致，是回文数");
            frames.add(done);
            return response(item, "array", 900, frames);
        }
        if ("no066".equals(id)) {
            int[] digits = new int[] { 1, 2, 9 };
            Map<String, Object> first = baseFrame(Arrays.copyOf(digits, digits.length));
            first.put("active", digits.length - 1);
            first.put("description", "从末位开始处理进位");
            frames.add(first);
            int carry = 1;
            for (int i = digits.length - 1; i >= 0; i--) {
                int sum = digits[i] + carry;
                digits[i] = sum % 10;
                carry = sum / 10;
                Map<String, Object> frame = baseFrame(Arrays.copyOf(digits, digits.length));
                frame.put("active", i);
                frame.put("description", "处理下标 " + i + "：新值 " + digits[i] + "，进位 " + carry);
                frames.add(frame);
            }
            if (carry > 0) {
                int[] extended = new int[] { 1, digits[0], digits[1], digits[2] };
                Map<String, Object> frame = baseFrame(extended);
                frame.put("active", 0);
                frame.put("description", "最高位仍有进位，扩展数组");
                frame.put("result", Arrays.stream(extended).boxed().collect(Collectors.toList()));
                frame.put("found", true);
                frames.add(frame);
            } else {
                Map<String, Object> frame = baseFrame(Arrays.copyOf(digits, digits.length));
                frame.put("result", Arrays.stream(digits).boxed().collect(Collectors.toList()));
                frame.put("found", true);
                frame.put("description", "加一完成");
                frames.add(frame);
            }
            return response(item, "array", 900, frames);
        }
        if ("no1266".equals(id)) {
            int[][] points = new int[][] { { 1, 1 }, { 3, 4 }, { -1, 0 } };
            int total = 0;
            for (int i = 1; i < points.length; i++) {
                int dx = Math.abs(points[i][0] - points[i - 1][0]);
                int dy = Math.abs(points[i][1] - points[i - 1][1]);
                int step = Math.max(dx, dy);
                total += step;
                Map<String, Object> frame = baseFrame(new int[] { points[i - 1][0], points[i - 1][1], points[i][0], points[i][1], total });
                frame.put("active", i);
                frame.put("description", "从 (" + points[i - 1][0] + "," + points[i - 1][1] + ") 到 (" + points[i][0] + "," + points[i][1]
                        + ")：max(|dx|,|dy|)=" + step + "，累计 " + total);
                frames.add(frame);
            }
            Map<String, Object> done = baseFrame(new int[] { total });
            done.put("found", true);
            done.put("result", total);
            done.put("description", "所有点访问完成，最少时间 = " + total);
            frames.add(done);
            return response(item, "array", 950, frames);
        }
        if ("no136".equals(id)) {
            int[] nums = new int[] { 2, 2, 1, 4, 4 };
            int xor = 0;
            for (int i = 0; i < nums.length; i++) {
                xor ^= nums[i];
                Map<String, Object> frame = baseFrame(nums);
                frame.put("active", i);
                frame.put("profit", xor);
                frame.put("description", "累计异或到下标 " + i + "：xor=" + xor);
                frames.add(frame);
            }
            Map<String, Object> done = baseFrame(nums);
            done.put("result", xor);
            done.put("found", true);
            done.put("description", "异或收敛完成，只出现一次的数字是 " + xor);
            frames.add(done);
            return response(item, "array", 850, frames);
        }
        if ("no190".equals(id)) {
            int value = 0b00000010100101000001111010011100;
            int reversed = 0;
            for (int i = 0; i < 32; i++) {
                reversed <<= 1;
                reversed |= (value >>> i) & 1;
                Map<String, Object> frame = baseFrame(new int[] { i + 1, reversed });
                frame.put("active", 1);
                frame.put("description", "处理第 " + (i + 1) + " 位，当前反转结果（二进制低位截断）=" + Integer.toUnsignedString(reversed));
                frames.add(frame);
            }
            Map<String, Object> done = baseFrame(new int[] { reversed });
            done.put("found", true);
            done.put("result", Integer.toUnsignedString(reversed));
            done.put("description", "32 位反转完成");
            frames.add(done);
            return response(item, "array", 260, frames);
        }
        if ("no208".equals(id) || "no211".equals(id)) {
            String[] words = "no211".equals(id) ? new String[] { "bad", "dad", "mad" } : new String[] { "apple", "app" };
            List<String> trace = new ArrayList<>();
            for (int i = 0; i < words.length; i++) {
                trace.add(words[i]);
                Map<String, Object> frame = baseFrame(trace.stream().mapToInt(String::length).toArray());
                frame.put("active", i);
                frame.put("description", "插入单词 \"" + words[i] + "\"，Trie 节点逐字符扩展");
                frames.add(frame);
            }
            Map<String, Object> search = baseFrame(trace.stream().mapToInt(String::length).toArray());
            search.put("active", Math.max(0, trace.size() - 1));
            if ("no211".equals(id)) {
                search.put("description", "搜索模式 \".ad\"，通配符匹配成功");
                search.put("result", true);
            } else {
                search.put("description", "查询前缀 \"app\"，命中节点并返回 true");
                search.put("result", true);
            }
            search.put("found", true);
            frames.add(search);
            return response(item, "backtracking", 900, frames);
        }
        if ("no284".equals(id)) {
            int[] values = new int[] { 1, 2, 3 };
            for (int i = 0; i < values.length; i++) {
                Map<String, Object> peek = baseFrame(values);
                peek.put("active", i);
                peek.put("description", "peek() 读取 " + values[i] + "，迭代器位置保持不变");
                frames.add(peek);
                Map<String, Object> next = baseFrame(values);
                next.put("active", i);
                next.put("description", "next() 消费 " + values[i] + "，指针前进");
                frames.add(next);
            }
            Map<String, Object> done = baseFrame(values);
            done.put("found", true);
            done.put("result", false);
            done.put("description", "hasNext() 返回 false，迭代结束");
            frames.add(done);
            return response(item, "array", 850, frames);
        }
        return buildGenericAnimation(item);
    }

    private Map<String, Object> buildGenericAnimation(CatalogItem item) {
        List<Map<String, Object>> frames = new ArrayList<>();
        int seed = Math.abs((item == null || item.getId() == null ? "problem" : item.getId()).hashCode());
        int size = 5 + seed % 4;
        int[] values = new int[size];
        for (int i = 0; i < size; i++) {
            values[i] = 1 + (seed / (i + 3)) % 12;
        }
        for (int i = 0; i < size; i++) {
            Map<String, Object> frame = baseFrame(values);
            frame.put("active", i);
            frame.put("description", "步骤 " + (i + 1) + "：处理值 " + values[i] + "，累计进度 " + (i + 1) + "/" + size);
            frames.add(frame);
        }
        return response(item, "array", 1000, frames);
    }

    private Map<String, Object> response(CatalogItem item, String type, int intervalMs, List<Map<String, Object>> frames) {
        String id = item == null ? "" : item.getId();
        String title = item == null ? "算法动画" : item.getName();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", id);
        map.put("title", title);
        map.put("type", type);
        map.put("technique", item == null ? "" : item.getTechnique());
        map.put("intervalMs", intervalMs);
        map.put("frames", frames);
        return map;
    }

    private Map<String, Object> baseFrame(int[] arr) {
        Map<String, Object> frame = new LinkedHashMap<>();
        frame.put("array", arr);
        frame.put("found", false);
        return frame;
    }

    private Map<String, Object> baseGridFrame(int[][] grid) {
        Map<String, Object> frame = new LinkedHashMap<>();
        frame.put("grid", copyMatrix(grid));
        frame.put("found", false);
        return frame;
    }

    private int[][] copyMatrix(int[][] src) {
        int[][] copy = new int[src.length][];
        for (int i = 0; i < src.length; i++) {
            copy[i] = Arrays.copyOf(src[i], src[i].length);
        }
        return copy;
    }

    private int[][] copyVisited(boolean[][] src) {
        int[][] copy = new int[src.length][];
        for (int i = 0; i < src.length; i++) {
            copy[i] = new int[src[i].length];
            for (int j = 0; j < src[i].length; j++) {
                copy[i][j] = src[i][j] ? 1 : 0;
            }
        }
        return copy;
    }

    private List<Integer> list(int a, int b) {
        List<Integer> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        return list;
    }

    private boolean isOneOf(String id, String... ids) {
        if (id == null || ids == null) {
            return false;
        }
        for (String candidate : ids) {
            if (id.equals(candidate)) {
                return true;
            }
        }
        return false;
    }

    private String problemId(CatalogItem item) {
        return item == null || item.getId() == null ? "" : item.getId().trim().toLowerCase(Locale.ROOT);
    }

    private int positiveModulo(int value, int mod) {
        if (mod <= 0) {
            return 0;
        }
        int result = value % mod;
        return result < 0 ? result + mod : result;
    }

    private int[] toIntArray(boolean[] flags) {
        int[] values = new int[flags.length];
        for (int i = 0; i < flags.length; i++) {
            values[i] = flags[i] ? 1 : 0;
        }
        return values;
    }

    private int[] seededArray(String id, int size, int minInclusive, int maxInclusive) {
        int[] values = new int[Math.max(1, size)];
        int range = Math.max(1, maxInclusive - minInclusive + 1);
        int seed = Math.abs((id == null ? "seed" : id).hashCode()) + 17;
        for (int i = 0; i < values.length; i++) {
            int mix = seed / (i + 1) + i * 31;
            values[i] = minInclusive + positiveModulo(mix, range);
        }
        return values;
    }

    private int[] seededSortedArray(String id, int size, int minInclusive, int maxInclusive) {
        int[] values = seededArray(id, size, minInclusive, maxInclusive);
        Arrays.sort(values);
        return values;
    }
}
