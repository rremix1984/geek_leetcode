# 动画类误置于test目录导致加载失败问题修复设计文档

## 1. 问题背景

在启动并运行算法动画演示系统后，当尝试加载“NO.23 合并K个升序链表”的动画时，系统弹窗提示“动画不可用”，原因为“动画类位于test目录下，无法正常加载”。

## 2. 根本原因分析

经过排查，发现 `NO023_H_MergeKSortedLists_Animation.java` 这个文件同时存在于 `geek-hard` 模块的 `src/main/java` 和 `src/test/java` 两个目录下。

- **`src/test/java` 下的版本**：是一个独立的 `JFrame` 实现，用于快速、隔离地测试动画逻辑，不符合主应用程序的 `JPanel` 插件式框架。
- **`src/main/java` 下的版本**：是一个功能更完善、与主系统集成的 `JPanel` 实现，是预期的动画类。

Maven在打包时，默认不会将 `src/test/java` 目录下的源文件和资源打包到最终的JAR文件中。然而，在开发环境中，类加载器可能会因为路径配置问题，错误地尝试加载 `test` 目录下的类，或者两个同名类的存在导致了加载冲突，最终使得主程序无法找到并实例化正确的动画 `JPanel`。

## 3. 解决方案

确认 `test` 目录下的 `NO023_H_MergeKSortedLists_Animation.java` 文件是冗余的、用于早期测试的遗留文件后，采取以下步骤进行修复：

1.  **删除冗余文件**：将 `geek-hard/src/test/java/com/leetcode/hard/NO023_H_MergeKSortedLists_Animation.java` 文件直接删除。
2.  **重新构建项目**：执行 `mvn clean install` 命令，重新编译并打包整个项目，确保旧的、错误的类文件被彻底清除。
3.  **重启应用验证**：停止正在运行的应用实例，并启动新打包的JAR文件，验证问题是否解决。

## 4. 修复结果

删除冗余的测试类后，类加载冲突问题被解决。应用程序现在能够正确地找到并加载 `src/main/java` 目录下的 `NO023_H_MergeKSortedLists_Animation` 类。再次点击“NO.23 合并K个升序链表”时，动画可以被正常启动和演示，系统功能恢复正常。