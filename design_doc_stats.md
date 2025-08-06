# “算法统计”功能设计文档

## 1. 需求背景

为了让用户更直观地了解当前算法库的整体情况，需要增加一个“算法统计”功能，用于展示算法的总数、按难度分布以及按技术标签分布的情况。

## 2. 设计方案

### 2.1 UI设计

- 在主界面的控制面板区域（“启动动画”按钮旁边）新增一个“统计”按钮(`statsButton`)。
- 按钮带有图标和提示文字“显示算法统计信息”。
- 点击按钮后，弹出一个信息对话框(`JOptionPane`)，展示统计结果。

### 2.2 功能实现

1.  **添加UI组件**:
    *   在 `AlgorithmTreeLauncher` 类中，增加一个新的 `JButton` 成员变量 `statsButton`。
    *   在 `initComponents` 方法中，对 `statsButton` 进行初始化，设置文本、提示、图标等属性。
    *   在 `setupLayout` 方法中，将 `statsButton` 添加到右下角的 `controlPanel` 中。

2.  **事件处理**:
    *   在 `setupEventHandlers` 方法中，为 `statsButton` 添加一个 `ActionListener`。
    *   该监听器调用一个新的私有方法 `showAlgorithmStats()` 来执行统计和展示逻辑。

3.  **统计逻辑 (`showAlgorithmStats` 方法)**:
    *   该方法负责遍历 `allAlgorithmNodes` 列表，该列表包含了所有的算法节点。
    *   创建两个 `Map`：`difficultyCount` 用于存储按难度分类的算法数量，`techniqueCount` 用于存储每个技术标签出现的次数。
    *   遍历过程中，对每个 `AlgorithmInfo` 对象：
        *   获取其难度(`difficulty`)，并在 `difficultyCount` 中累加计数。
        *   获取其技术标签(`technique`)，使用 `split("\\s*\\+\\s*")` 对技术栈进行拆分（例如 “双指针 + 哈希表”），并对每个技术点在 `techniqueCount` 中累加计数。
    *   统计完成后，使用 `StringBuilder` 构建一个HTML格式的字符串，用于在 `JOptionPane` 中美观地展示统计结果。
    *   统计信息包括：
        *   总算法数量。
        *   按难度（Easy, Normal, Hard等）分类的列表。
        *   按技术标签出现频率降序排列的列表。
    *   最后，调用 `JOptionPane.showMessageDialog` 显示统计信息。

## 3. 代码实现

具体的代码变更如下：

-   **`AlgorithmTreeLauncher.java`**
    -   添加 `statsButton` 成员变量。
    -   在 `initComponents` 中初始化 `statsButton`。
    -   在 `setupLayout` 中将 `statsButton` 添加到 `controlPanel`。
    -   在 `setupEventHandlers` 中为 `statsButton` 绑定 `showAlgorithmStats` 方法。
    -   实现 `showAlgorithmStats` 方法，包含完整的统计和显示逻辑。

## 4. 资源文件

-   需要一个用于统计按钮的图标，路径为 `/icons/statistics.png`。确保该图标资源存在于项目中。

## 5. 总结

通过以上步骤，我们为应用增加了一个简单而实用的统计功能，增强了用户对算法库的宏观认识，且代码改动对现有逻辑无侵入性，具有良好的可维护性。