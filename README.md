# geek_leetcode

## 介绍

这是一个 Maven 多模块项目，包含：
- 算法题解与练习（easy/normal/hard 等模块）
- 算法动画可视化（Swing 桌面 UI）
- 多线程/并发相关示例（含 Spring Boot 模块）

## 统一启动脚本（运维命令行）

项目根目录提供了统一的命令行入口 `./start.sh`，把常见操作（编译、启动 UI、启动后端、健康检查、查看日志、停止服务等）汇总在一个脚本里，便于本地运维与快速验证。

### 前置条件

- JDK 8（项目编译目标为 Java 8）
- Maven（`mvn` 可用）
- macOS/Linux（脚本为 Bash；Windows 建议在 WSL 中运行）

### 快速开始

```bash
# 查看帮助（所有命令一览）
./start.sh help

# 编译（跳过测试）
./start.sh build

# 启动“前端页面”（算法动画桌面 UI）
./start.sh frontend:start

# 启动后端服务（Spring Boot，前台运行）
./start.sh backend:start
```

### 命令清单

#### 构建相关

```bash
./start.sh build            # 全项目编译打包（跳过测试）
./start.sh test-compile     # 全项目编译（含测试代码编译）
./start.sh test             # 运行测试（强制不跳过）
./start.sh clean            # 清理构建产物
```

#### 前端（桌面 UI / 动画系统）

说明：本项目“前端页面”是 Swing 桌面程序（算法动画启动器），不依赖 Node.js/npm。

```bash
./start.sh frontend:compile       # 编译 geek-animation 模块（会带上依赖模块）
./start.sh frontend:start         # 启动算法动画系统（树形分类界面）
./start.sh frontend:start-debug   # 以调试模式启动（远程调试端口 5005）
```

#### 后端（Spring Boot：geek-multithread）

```bash
./start.sh backend:start                # 前台启动（占用当前终端）
./start.sh backend:start --daemon       # 后台启动（写入 pid/log）
./start.sh backend:status               # 查看后台服务状态
./start.sh backend:logs                 # 查看后台服务日志（tail -f）
./start.sh backend:stop                 # 停止后台服务
```

#### 健康检查

```bash
./start.sh backend:health    # 默认检查 http://localhost:8080/
./start.sh health            # 汇总检查（前端脚本存在 + 后端可达）
```

### 重要说明（相当于“注释”）

1. **后端端口**
   - 默认端口为 `8080`（脚本按 `http://localhost:8080/` 做可达性检查）。
   - 你可以用环境变量覆盖端口：
     ```bash
     BACKEND_PORT=8081 ./start.sh backend:health
     ```

2. **后台模式的 pid/log 存放位置**
   - pid/log 会写到系统临时目录下的 `geek_leetcode_pids`，方便本机多项目共存：
     - macOS：通常在 `/var/folders/.../T/geek_leetcode_pids/`
     - Linux：通常在 `/tmp/geek_leetcode_pids/`

3. **关于 geek-animation/start.sh 的“编译入口”**
   - 在不改变原有启动逻辑的前提下，`geek-animation/start.sh` 额外支持：
     ```bash
     cd geek-animation
     ./start.sh compile
     ./start.sh test-compile
     ./start.sh package
     ```
   - 不带参数时仍按原逻辑：若未编译则先编译，然后启动动画界面。

## 参与贡献

1. Fork 本仓库
2. 新建分支
3. 提交代码
4. 发起 Pull Request
