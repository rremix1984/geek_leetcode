# 全排列算法动画设计文档

## 1. 概述

本文档旨在设计一个可扩展的Java Swing动画程序，用于可视化多种全排列生成算法。用户可以通过UI选择不同的算法，并观察其执行过程。

## 2. 功能需求

-   支持多种全排列算法，包括但不限于：
    -   回溯法 (Backtracking)
    -   字典序法 (Lexicographical Order)
    -   Heap's 算法
-   提供一个下拉菜单，允许用户选择要可视化的算法。
-   动画应清晰地展示算法的每一步操作，例如元素的交换、选择、回溯等。
-   实时显示当前生成的排列和所有已找到的排列。
-   提供播放、暂停、下一步和重置等动画控制功能。
-   代码结构应具有良好的扩展性，便于未来添加新的排列算法。

## 3. 架构设计

为了实现可扩展性，我们采用**策略模式（Strategy Pattern）**。每种全排列算法都将实现一个公共的 `PermutationAlgorithm` 接口。主程序 `PermutationsAnimation` 负责UI和动画控制，并根据用户的选择动态地切换算法策略。

### 3.1. 核心组件

-   **`PermutationsAnimation.java`**: 主类，负责创建和管理Swing窗口、UI组件（如按钮、下拉菜单、画布）和动画循环。
-   **`PermutationAlgorithm.java`**: 算法策略接口，定义了一个核心方法 `generateStates(int[] nums)`，该方法返回一个 `List<AnimationState>`，代表算法执行的所有步骤。
-   **`AnimationState.java`**: 数据类，用于封装动画在某一帧的状态。它包含当前数组状态、执行的动作、高亮索引以及用于特定算法的附加信息等。
-   **`PermutationPanel.java`** (作为 `PermutationsAnimation` 的内部类): 自定义 `JPanel`，负责根据当前的 `AnimationState` 绘制动画帧。
-   **算法实现类**:
    -   `BacktrackingPermutation.java`: 实现回溯法。
    -   `LexicographicalPermutation.java`: 实现字典序法。
    -   `HeapPermutation.java`: 实现Heap's算法。

### 3.2. 目录结构

```
com/animation/normal/
├── PermutationsAnimation.java
└── permutations/
    ├── AnimationState.java
    ├── PermutationAlgorithm.java
    ├── BacktrackingPermutation.java
    ├── LexicographicalPermutation.java
    └── HeapPermutation.java
```

## 4. 详细设计

### 4.1. `PermutationAlgorithm` 接口

```java
public interface PermutationAlgorithm {
    List<AnimationState> generateStates(int[] nums);
}
```

该接口是所有排列算法的核心抽象。任何新的算法只需实现此接口即可集成到动画程序中。

### 4.2. `AnimationState` 类

`AnimationState` 类用于捕获动画的快照。为了通用性，它包含一个 `details` 映射，用于存储特定于算法的数据。

```java
public class AnimationState {
    int[] nums;                     // 当前数组状态
    String action;                  // 当前动作 (e.g., "swap", "choose")
    List<String> foundPermutations; // 已找到的全排列
    Map<String, Object> details;    // 算法特定详情 (e.g., swap indices)
    // ... 其他字段和构造函数
}
```

### 4.3. `PermutationsAnimation` 主类

-   **UI初始化**: 创建一个 `JComboBox` 来列出所有可用的算法。算法名称通过字符串数组定义。
-   **算法选择**: 当用户从 `JComboBox` 中选择一个算法时，`getAlgorithm(String name)` 方法会根据选择的名称实例化对应的算法策略类。
-   **动画生成**: `generatePermutations()` 方法调用当前选定算法策略的 `generateStates()` 方法来获取动画帧列表。
-   **动画渲染**: `PermutationPanel` 的 `paintComponent` 方法会读取当前的 `AnimationState`，并根据 `action` 和 `details` 字段来决定如何渲染。例如：
    -   如果 `action` 是 `"swap"`，它会高亮显示 `details` 中指定的两个正在交换的元素。
    -   如果 `action` 是 `"reverse"`，它会高亮显示正在反转的子数组。
    -   对于回溯法，它会根据 `used` 数组的状态来区分元素的可用性。

## 5. 实现细节

-   **回溯法**: `BacktrackingPermutation` 会记录递归深度、当前路径和每个元素的使用状态 (`used` 数组)。
-   **字典序法**: `LexicographicalPermutation` 会在 `details` 中记录找到的第一个升序对的索引 (`p`) 和比它大的最右侧元素的索引 (`q`)，以及交换和反转的动作。
-   **Heap's 算法**: `HeapPermutation` 会在 `details` 中记录每次交换的索引。

## 6. 总结

通过采用策略模式和通用的 `AnimationState` 设计，我们成功构建了一个灵活且可扩展的全排列算法可视化工具。该架构使得添加新算法变得简单，只需实现 `PermutationAlgorithm` 接口并更新UI选项即可，无需修改核心动画逻辑。