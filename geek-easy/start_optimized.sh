#!/bin/bash

# LeetCode算法动画演示系统优化启动脚本
# 解决JVM CodeCache和字体警告问题

echo "🚀 启动LeetCode算法动画演示系统..."
echo "📁 工作目录: $(pwd)"

# 检查Java版本
java_version=$(java -version 2>&1 | head -n 1)
echo "☕ Java版本: $java_version"

# 优化的JVM参数 - 解决CodeCache不足问题
JVM_OPTS="-XX:ReservedCodeCacheSize=4096m \
          -XX:InitialCodeCacheSize=512m \
          -XX:CodeCacheExpansionSize=64m \
          -XX:+UseCodeCacheFlushing \
          -XX:+SegmentedCodeCache \
          -Xmx8g \
          -Xms3g \
          -XX:+UseG1GC \
          -XX:G1HeapRegionSize=16m \
          -XX:+UseStringDeduplication \
          -XX:+UnlockExperimentalVMOptions \
          -XX:+UseJVMCICompiler \
          -XX:+EnableJVMCI \
          -Djava.awt.headless=false \
          -Dfile.encoding=UTF-8 \
          -Dsun.java2d.opengl=true \
          -Dswing.aatext=true \
          -Dawt.useSystemAAFontSettings=on"

echo "🔧 JVM优化参数已设置"
echo "💾 最大堆内存: 8GB"
echo "🗂️  代码缓存: 4096MB (初始512MB)"
echo "⚡ 启用分段代码缓存和自动清理"
echo "🎨 启用图形渲染优化"

# 编译项目
echo "🔨 编译项目..."
mvn compile -q

if [ $? -eq 0 ]; then
    echo "✅ 编译成功"
    echo "🎯 启动算法动画系统..."
    
    # 启动应用
    java $JVM_OPTS -cp target/classes com.leetcode.animation.AlgorithmTreeLauncher
else
    echo "❌ 编译失败，请检查代码"
    exit 1
fi