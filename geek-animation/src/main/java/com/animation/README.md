# LeetCode算法动画演示系统

## 📁 目录说明

本目录 `com.leetcode.animation` 专门用于存放算法动画演示相关的文件，与算法实现代码分离，保持项目结构的清晰性。

## 🎯 目录结构

```
src/main/java/com/leetcode/animation/
├── AlgorithmAnimationLauncher.java          # 动画启动器主类
├── InsertionSortAnimation.java              # 插入排序动画
├── NO001_E_TwoSum_Animation.java            # 两数之和动画
├── NO088_E_MergeSortedArray_Animation.java  # 合并两个有序数组动画
├── NO118_E_Generate_Animation.java          # 杨辉三角动画
├── NO225_E_MyStack_Animation.java           # 用队列实现栈动画
├── NO704_E_BinarySearch_Animation.java      # 二分查找动画
├── NO977_E_SortedSquares_Animation.java     # 有序数组的平方动画
├── NO2553_E_SeparateDigits_Animation.java   # 分隔数字动画
├── NO2558_E_PickGifts_Animation.java        # 礼品堆动画
├── NO2570_E_MergeArrays_Animation.java      # 合并数组动画
├── NO2706_E_BuyChoco_Animation.java         # 购买巧克力动画
├── NO2824_E_CountPairs_Animation.java       # 统计对数动画
├── NO2859_E_SumIndicesWithKSetBits_Animation.java # 计算K个置位位数的下标对应元素的和动画
├── NO2942_E_FindWordsContaining_Animation.java # 查找包含指定字符的字符串动画
├── NO2733_E_FindNonMinOrMax_Animation.java  # 找出不是最小或最大的数动画
├── NO2828_E_IsAcronym_Animation.java        # 判别首字母缩略词动画
├── NO1002_E_CommonChars_Animation.java      # NO.1002 查找常用字符动画
├── NO463_E_IslandPerimeter_Animation.java   # NO.463 岛屿的周长动画
├── NO190_E_ReverseBits_Animation.java  # 颠倒二进制位动画
├── NO1051_E_HeightChecker_Animation.java    # NO.1051 高度检查器动画
├── NO703_E_KthLargest_Animation.java        # NO.703 数据流中的第K大元素动画
├── NO705_E_MyHashSet_Animation.java         # NO.705 设计哈希集合动画
├── NO706_E_MyHashMap_Animation.java         # NO.706 设计哈希映射动画
├── NO1005_E_LargestSumAfterKNegations_Animation.java # NO.1005 K次取反后最大化数组和动画
└── README.md                           # 本文档
```

编译后的class文件位于：
```
target/classes/com/leetcode/animation/
├── AlgorithmAnimationLauncher.class
├── [其他动画类的.class文件]
└── [内部类的.class文件]
```

## 🚀 快速开始

### 方法一：使用Maven编译运行（推荐）
```bash
# 使用Maven编译项目
mvn clean compile

# 运行统一启动器
java -cp target/classes com.leetcode.animation.AlgorithmAnimationLauncher
```

### 方法二：直接运行单个动画
```bash
# 使用Maven编译后运行特定动画（以两数之和为例）
mvn clean compile
java -cp target/classes com.leetcode.animation.NO001_E_TwoSum_Animation
```

### 方法三：传统javac编译方式
```bash
# 编译所有动画文件
javac -cp "." src/main/java/com/leetcode/animation/*.java -d target/classes

# 运行统一启动器
java -cp target/classes com.leetcode.animation.AlgorithmAnimationLauncher
```

### 方法四：一键启动脚本
```bash
# Linux/macOS用户
cd src/test/java/com/leetcode/animation
./start_animation.sh

# Windows用户
cd src/test/java/com/leetcode/animation
start_animation.bat
```

## 📋 已实现的动画

| 题号 | 题目名称 | 动画文件 | 核心算法 |
|------|----------|----------|----------|
| 001 | 两数之和 | NO001_E_TwoSum_Animation.java | HashMap查找 |
| 088 | 合并两个有序数组 | NO088_E_MergeSortedArray_Animation.java | 双指针合并 |
| 118 | 杨辉三角 | NO118_E_Generate_Animation.java | 动态规划 |
| 225 | 用队列实现栈 | NO225_E_MyStack_Animation.java | 队列操作 |
| 704 | 二分查找 | NO704_E_BinarySearch_Animation.java | 二分查找 |
| 977 | 有序数组的平方 | NO977_E_SortedSquares_Animation.java | 双指针技术 |
| 2553 | 分割数组中数字的数位 | NO2553_E_SeparateDigits_Animation.java | 数字处理 |
| 2558 | 从数量最多的堆取走礼物 | NO2558_E_PickGifts_Animation.java | 优先队列 |
| 2570 | 合并两个二维数组 | NO2570_E_MergeArrays_Animation.java | 数组操作 |
| 2706 | 购买两块巧克力 | NO2706_E_BuyChoco_Animation.java | 贪心算法 |
| 2824 | 统计和小于目标的下标对数目 | NO2824_E_CountPairs_Animation.java | 双指针 |
| 2859 | 计算K个置位位数的下标对应元素的和 | NO2859_E_SumIndicesWithKSetBits_Animation.java | 位运算 |
| 2942 | 查找包含指定字符的字符串 | NO2942_E_FindWordsContaining_Animation.java | 字符串搜索 |
| 2733 | 找出不是最小或最大的数 | NO2733_E_FindNonMinOrMax_Animation.java | 数组分析 |
| 2828 | 判别首字母缩略词 | NO2828_E_IsAcronym_Animation.java | 字符串处理 |
| 1002 | 查找常用字符 | NO1002_E_CommonChars_Animation.java | 字符频次统计 |
| 463 | 岛屿的周长 | NO463_E_IslandPerimeter_Animation.java | 网格遍历 |
| 190 | 颠倒二进制位 | NO190_E_ReverseBits_Animation.java | 位运算 |
| 1051 | 高度检查器 | NO1051_E_HeightChecker_Animation.java | 排序比较 |
| 703 | 数据流中的第K大元素 | NO703_E_KthLargest_Animation.java | 堆/优先队列 |
| 705 | 设计哈希集合 | NO705_E_MyHashSet_Animation.java | 哈希表 |
| 706 | 设计哈希映射 | NO706_E_MyHashMap_Animation.java | 哈希表 |
| 1005 | K次取反后最大化数组和 | NO1005_E_LargestSumAfterKNegations_Animation.java | 贪心算法 |
| - | 插入排序 | InsertionSortAnimation.java | 插入排序 |

## 🎮 功能特性

- **可视化演示**：直观展示算法执行过程
- **交互式操作**：支持手动输入数据和参数
- **步骤控制**：可以逐步执行或自动播放
- **实时反馈**：显示当前执行状态和结果
- **多算法支持**：涵盖多种经典算法
- **详细题目描述**：每个算法都有清晰的问题描述和解题思路
- **便捷导航**：每个动画都有"返回首页"按钮，方便切换
- **一键启动**：提供跨平台启动脚本，无需手动编译
- **统一界面**：集中式启动器，所有算法动画一目了然

## 🔧 技术实现

- **框架**：Java Swing + AWT
- **动画**：Timer控制帧率
- **设计模式**：工厂模式、观察者模式
- **包结构**：`com.leetcode.animation`

## 📖 相关文档

- [详细动画说明](README_Animation.md)
- [算法动画系统设计文档](算法动画系统设计文档.md)

## 🤝 贡献指南

1. 新增动画请遵循命名规范：`NO{题号}_E_{题目名}_Animation.java`
2. 在 `AlgorithmAnimationLauncher.java` 中注册新动画
3. 更新相关文档和README
4. 确保代码通过编译和测试

## 📞 联系方式

如有问题或建议，请通过项目仓库提交Issue。

---
**最后更新**：2024年  
**维护状态**：活跃开发中