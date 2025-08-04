#!/bin/bash

# JVM内存监控工具启动脚本

echo "🔍 启动JVM内存监控工具"
echo "📁 工作目录: $(pwd)"

# 检查Java版本
java_version=$(java -version 2>&1 | head -n 1)
echo "☕ Java版本: $java_version"

# JVM参数设置（为了更好地监控自身）
JVM_OPTS="-Xmx1g \
          -Xms256m \
          -XX:+UseG1GC \
          -XX:MaxGCPauseMillis=200 \
          -Djava.awt.headless=false \
          -Dfile.encoding=UTF-8 \
          -Dsun.java2d.opengl=true \
          -Dswing.aatext=true \
          -Dawt.useSystemAAFontSettings=on"

echo "🔧 JVM参数已设置"
echo "💾 最大堆内存: 1GB"
echo "🎨 启用图形渲染优化"

# 检查是否已编译
if [ ! -d "target/classes" ]; then
    echo "🔨 编译项目..."
    mvn compile -q
    
    if [ $? -ne 0 ]; then
        echo "❌ 编译失败，请检查代码"
        exit 1
    fi
    echo "✅ 编译成功"
else
    echo "✅ 使用已编译的类文件"
fi

echo "🎯 启动JVM内存监控工具..."
echo "📊 监控功能："
echo "   - 实时显示堆内存使用情况"
echo "   - 重点监控CodeCache使用率"
echo "   - 显示元空间和压缩类空间"
echo "   - 垃圾回收统计信息"
echo "   - 系统资源使用情况"

# 启动应用
java $JVM_OPTS -cp target/classes com.leetcode.tools.JVMMemoryMonitor

echo "👋 JVM内存监控工具已退出"