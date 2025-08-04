@echo off
chcp 65001 >nul

echo 🚀 正在启动算法动画系统...
echo 📁 工作目录: %cd%

REM 检查是否已编译
if not exist "target\classes" (
    echo ⚠️  未找到编译文件，正在编译项目...
    call mvn clean compile
    if errorlevel 1 (
        echo ❌ 编译失败，请检查项目配置
        pause
        exit /b 1
    )
)

echo ✅ 启动树形分类算法动画界面...
call mvn exec:java -Dexec.mainClass="com.animation.launcher.AlgorithmTreeLauncher"

echo 👋 算法动画系统已退出
pause