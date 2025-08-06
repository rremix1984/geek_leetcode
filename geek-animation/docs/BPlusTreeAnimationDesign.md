# B+树动画设计文档

## 1. 概述

本文档旨在设计一个B+树的动画，以可视化的方式展示其核心操作，包括插入、删除和查找。该动画将作为`geek-animation`项目的一部分，帮助用户理解数据库索引背后的基本原理。

## 2. 功能需求

- **B+树结构可视化**：清晰地展示B+树的节点（内部节点和叶子节点）、键和指针。
- **插入操作动画**：
    - 展示新键值如何找到正确的叶子节点进行插入。
    - 演示叶子节点满时如何分裂。
    - 演示内部节点满时如何分裂，以及树高度如何增加。
- **查找操作动画**：
    - 高亮显示从根节点到目标叶子节点的搜索路径。
- **删除操作动画**：
    - 展示如何从叶子节点删除一个键值。
    - 演示节点在删除后键数量少于阈值时如何从兄弟节点借用键。
    - 演示节点在无法借用时如何与兄弟节点合并。
- **用户交互**：
    - 用户可以输入要插入、删除或查找的数值。
    - 提供播放、暂停、单步执行等动画控制功能。

## 3. 技术选型

- **语言**：Java
- **UI框架**：Swing
- **绘图**：使用`java.awt.Graphics2D`进行自定义绘图。

## 4. 动画实现步骤

1.  **B+树数据结构**：实现一个`BPlusTree`类，包含`Node`（内部节点和叶子节点的基类）、`InternalNode`和`LeafNode`等内部类。该数据结构将是动画的核心逻辑基础。
2.  **动画面板**：创建一个`BPlusTreeAnimationPanel`类，继承自`JPanel`，负责绘制整个B+树。它将持有一个`BPlusTree`实例，并根据其状态进行渲染。
3.  **操作逻辑与动画步骤**：
    - 对于每个操作（插入、删除、查找），在`BPlusTree`类中实现相应的方法。这些方法不仅执行数据结构的操作，还会生成一个动画步骤列表（`List<AnimationStep>`）。
    - `AnimationStep`对象将封装每一步的动画信息，例如当前高亮的节点、移动的键、状态文本等。
4.  **动画控制器**：使用`javax.swing.Timer`来驱动动画播放。Timer的每个tick都会处理下一个`AnimationStep`，并调用`BPlusTreeAnimationPanel`的`repaint()`方法来更新UI。
5.  **主窗口**：创建一个`BPlusTreeAnimation`类，继承自`JFrame`，作为动画的主窗口。它将包含`BPlusTreeAnimationPanel`以及用于用户输入的文本框和控制按钮。

## 5. 文件结构

```
geek-animation/
└── src/
    └── main/
        └── java/
            └── com/
                └── animation/
                    └── tree/
                        ├── BPlusTree.java
                        ├── BPlusTreeAnimation.java
                        └── BPlusTreeAnimationPanel.java
```

## 6. 集成计划

完成B+树动画后，会将其添加到`AlgorithmTreeLauncher`的“数据库”类别下，以便用户可以从主界面启动它。