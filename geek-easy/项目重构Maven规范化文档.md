# 项目重构Maven规范化文档

## 项目概述

本文档记录了将LeetCode算法动画项目从非标准目录结构重构为Maven标准目录结构的过程。

## 重构目标

1. **遵循Maven标准目录结构**：将源代码和编译文件分离
2. **提高项目规范性**：符合Java项目最佳实践
3. **便于维护和扩展**：标准化的目录结构便于团队协作
4. **支持IDE集成**：更好地支持现代IDE的功能

## 重构前后对比

### 重构前目录结构
```
geek-easy/
├── pom.xml
└── src/
    └── test/
        └── java/
            └── com/
                └── leetcode/
                    └── animation/
                        ├── *.java (源文件)
                        ├── *.class (编译文件)
                        └── README.md
```

### 重构后目录结构
```
geek-easy/
├── pom.xml
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── leetcode/
│   │               └── animation/
│   │                   ├── *.java (源文件)
│   │                   └── README.md
│   └── test/
│       ├── java/
│       └── resources/
└── target/
    ├── classes/
    │   └── com/
    │       └── leetcode/
    │           └── animation/
    │               └── *.class (编译文件)
    ├── generated-sources/
    ├── generated-test-sources/
    ├── maven-status/
    └── test-classes/
```

## 重构步骤

### 1. 创建Maven标准目录结构
```bash
mkdir -p src/main/java/com/leetcode/animation
```

### 2. 移动源文件
```bash
# 复制Java源文件到main目录
cp src/test/java/com/leetcode/animation/*.java src/main/java/com/leetcode/animation/

# 复制文档文件
cp src/test/java/com/leetcode/animation/README.md src/main/java/com/leetcode/animation/
```

### 3. 清理旧目录
```bash
# 删除test目录下的动画文件
rm -rf src/test/java/com/leetcode/animation
```

### 4. 使用Maven编译
```bash
# 清理并编译项目
mvn clean compile
```

### 5. 更新启动方式
```bash
# 新的启动命令
java -cp target/classes com.leetcode.animation.AlgorithmAnimationLauncher
```

## 技术实现细节

### Maven配置
项目使用标准的Maven配置：
- **Java版本**：JDK 8
- **编译目标**：target/classes
- **依赖管理**：通过pom.xml管理

### 编译输出
- **源文件位置**：`src/main/java/com/leetcode/animation/`
- **编译文件位置**：`target/classes/com/leetcode/animation/`
- **内部类文件**：自动生成在同一目录下

### 文件清单

#### 动画源文件（21个）
1. AlgorithmAnimationLauncher.java - 动画启动器主类
2. InsertionSortAnimation.java - 插入排序动画
3. NO001_E_TwoSum_Animation.java - 两数之和动画
4. NO088_E_MergeSortedArray_Animation.java - 合并两个有序数组动画
5. NO118_E_Generate_Animation.java - 杨辉三角动画
6. NO190_E_ReverseBits_Animation.java - 颠倒二进制位动画
7. NO225_E_MyStack_Animation.java - 用队列实现栈动画
8. NO463_E_IslandPerimeter_Animation.java - 岛屿的周长动画
9. NO703_E_KthLargest_Animation.java - 数据流中的第K大元素动画
10. NO704_E_BinarySearch_Animation.java - 二分查找动画
11. NO705_E_MyHashSet_Animation.java - 设计哈希集合动画
12. NO706_E_MyHashMap_Animation.java - 设计哈希映射动画
13. NO977_E_SortedSquares_Animation.java - 有序数组的平方动画
14. NO1002_E_CommonChars_Animation.java - 查找常用字符动画
15. NO1005_E_LargestSumAfterKNegations_Animation.java - K次取反后最大化数组和动画
16. NO1051_E_HeightChecker_Animation.java - 高度检查器动画
17. NO2553_E_SeparateDigits_Animation.java - 分隔数字动画
18. NO2558_E_PickGifts_Animation.java - 礼品堆动画
19. NO2570_E_MergeArrays_Animation.java - 合并数组动画
20. NO2706_E_BuyChoco_Animation.java - 购买巧克力动画
21. NO2824_E_CountPairs_Animation.java - 统计对数动画

## 运行方式更新

### 推荐方式（Maven）
```bash
# 编译项目
mvn clean compile

# 启动动画系统
java -cp target/classes com.leetcode.animation.AlgorithmAnimationLauncher
```

### 传统方式（javac）
```bash
# 编译到target目录
javac -cp "." src/main/java/com/leetcode/animation/*.java -d target/classes

# 启动动画系统
java -cp target/classes com.leetcode.animation.AlgorithmAnimationLauncher
```

## 优势与收益

### 1. 标准化
- 符合Maven标准目录结构
- 遵循Java项目最佳实践
- 便于团队协作和维护

### 2. 清晰的文件分离
- 源文件和编译文件完全分离
- 避免版本控制中的class文件污染
- 便于清理和重新编译

### 3. IDE支持
- 更好的IDE识别和支持
- 自动补全和重构功能增强
- 调试和测试更加便捷

### 4. 构建自动化
- 支持Maven生命周期管理
- 便于集成CI/CD流程
- 依赖管理更加规范

## 注意事项

1. **编译路径变更**：启动命令从 `java -cp ".:src/test/java"` 变更为 `java -cp target/classes`
2. **文档更新**：README.md中的路径和命令已同步更新
3. **版本控制**：建议将target目录添加到.gitignore中
4. **依赖管理**：所有外部依赖应通过pom.xml管理

## 后续计划

1. **测试用例分离**：将测试相关代码移动到src/test/java目录
2. **资源文件管理**：创建src/main/resources目录管理配置文件
3. **打包配置**：配置Maven插件支持可执行jar包生成
4. **文档完善**：补充更详细的开发和部署文档

## 总结

通过本次重构，项目结构更加规范化和标准化，为后续的功能扩展和团队协作奠定了良好的基础。所有动画功能保持不变，但项目的可维护性和扩展性得到了显著提升。