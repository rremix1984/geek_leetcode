#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PID_DIR="${TMPDIR:-/tmp}/geek_leetcode_pids"

mkdir -p "$PID_DIR"

red() { printf "\033[0;31m%s\033[0m\n" "$*"; }
green() { printf "\033[0;32m%s\033[0m\n" "$*"; }
yellow() { printf "\033[1;33m%s\033[0m\n" "$*"; }

die() { red "ERROR: $*"; exit 1; }

need_cmd() {
  command -v "$1" >/dev/null 2>&1 || die "缺少命令: $1"
}

pid_file() { printf "%s/%s.pid" "$PID_DIR" "$1"; }
log_file() { printf "%s/%s.log" "$PID_DIR" "$1"; }

is_pid_running() {
  local pid="$1"
  [[ -n "${pid:-}" ]] && kill -0 "$pid" >/dev/null 2>&1
}

read_pid() {
  local f="$1"
  [[ -f "$f" ]] || return 0
  tr -d '[:space:]' < "$f" || true
}

write_pid() {
  local f="$1" pid="$2"
  printf "%s\n" "$pid" > "$f"
}

rm_pid() {
  local f="$1"
  [[ -f "$f" ]] && rm -f "$f"
}

usage() {
  cat <<'EOF'
用法:
  ./start.sh <命令> [参数]

常用命令:
  help                           显示帮助
  build                          全项目编译(跳过测试)
  test-compile                   全项目编译(含测试代码编译)
  test                           运行测试(强制不跳过)
  clean                          清理构建产物

前端(桌面UI/动画系统):
  frontend:start                 启动算法动画系统(树形界面)
  frontend:start-debug           调试模式启动算法动画系统(端口5005)
  frontend:compile               编译 geek-animation 模块

后端(Spring Boot):
  backend:start                  前台启动 geek-multithread 服务
  backend:start --daemon         后台启动 geek-multithread 服务
  backend:stop                   停止后台启动的 geek-multithread 服务
  backend:status                 查看后台服务状态
  backend:logs                   查看后台服务日志(tail -f)
  backend:health                 健康检查(默认 http://localhost:8080/)

Web(浏览器页面):
  web:url                        页面地址(默认 http://localhost:8080/)
  web:start                      同 backend:start
  web:start --daemon             同 backend:start --daemon
  web:health                     健康检查(默认 http://localhost:8080/api/health)

运维:
  health                         汇总健康检查(前端/后端)
EOF
}

cmd_build() {
  need_cmd mvn
  (cd "$ROOT_DIR" && mvn -DskipTests=true clean package)
}

cmd_test_compile() {
  need_cmd mvn
  (cd "$ROOT_DIR" && mvn clean test-compile)
}

cmd_test() {
  need_cmd mvn
  (cd "$ROOT_DIR" && mvn -DskipTests=false test)
}

cmd_clean() {
  need_cmd mvn
  (cd "$ROOT_DIR" && mvn clean)
}

cmd_frontend_start() {
  need_cmd bash
  (cd "$ROOT_DIR/geek-animation" && bash ./start.sh)
}

cmd_frontend_start_debug() {
  need_cmd bash
  (cd "$ROOT_DIR/geek-animation" && bash ./start_debug.sh)
}

cmd_frontend_compile() {
  need_cmd mvn
  (cd "$ROOT_DIR" && mvn -pl geek-animation -am -DskipTests=true clean compile)
}

backend_port() { printf "%s" "${BACKEND_PORT:-8080}"; }
backend_url() { printf "http://localhost:%s/" "$(backend_port)"; }
backend_health_url() { printf "http://localhost:%s/api/health" "$(backend_port)"; }

cmd_backend_start_foreground() {
  need_cmd mvn
  (cd "$ROOT_DIR/geek-multithread" && mvn spring-boot:run)
}

cmd_backend_start_daemon() {
  need_cmd mvn
  local pf lf pid
  pf="$(pid_file backend)"
  lf="$(log_file backend)"
  pid="$(read_pid "$pf")"

  if is_pid_running "$pid"; then
    yellow "backend 已在运行(pid=$pid)，日志: $lf"
    return 0
  fi

  rm_pid "$pf"
  : > "$lf"

  (cd "$ROOT_DIR/geek-multithread" && nohup mvn spring-boot:run >"$lf" 2>&1 & echo $! >"$pf")

  pid="$(read_pid "$pf")"
  if ! is_pid_running "$pid"; then
    die "backend 启动失败，查看日志: $lf"
  fi
  green "backend 已后台启动(pid=$pid)，端口: $(backend_port)，日志: $lf"
}

cmd_backend_stop() {
  local pf pid
  pf="$(pid_file backend)"
  pid="$(read_pid "$pf")"

  if ! is_pid_running "$pid"; then
    rm_pid "$pf"
    yellow "backend 未在运行(未发现有效pid)"
    return 0
  fi

  kill "$pid" >/dev/null 2>&1 || true
  for _ in 1 2 3 4 5 6 7 8 9 10; do
    if ! is_pid_running "$pid"; then
      rm_pid "$pf"
      green "backend 已停止"
      return 0
    fi
    sleep 1
  done

  kill -9 "$pid" >/dev/null 2>&1 || true
  rm_pid "$pf"
  green "backend 已强制停止"
}

cmd_backend_status() {
  local pf pid
  pf="$(pid_file backend)"
  pid="$(read_pid "$pf")"

  if is_pid_running "$pid"; then
    green "backend running(pid=$pid) url=$(backend_url)"
  else
    yellow "backend not running"
  fi
}

cmd_backend_logs() {
  need_cmd tail
  local lf
  lf="$(log_file backend)"
  [[ -f "$lf" ]] || die "日志文件不存在: $lf"
  tail -f "$lf"
}

cmd_backend_health() {
  need_cmd curl
  local url code
  url="$(backend_health_url)"
  code="$(curl -sS -o /dev/null -m 3 --connect-timeout 1 -w "%{http_code}" "$url" || true)"
  if [[ -z "$code" || "$code" == "000" ]]; then
    die "backend 健康检查失败: $url 无法连接"
  fi
  green "backend 健康检查通过: $url http=$code"
}

cmd_web_url() {
  green "$(backend_url)"
}

cmd_health() {
  local ok=true

  if [[ -x "$ROOT_DIR/geek-animation/start.sh" ]]; then
    green "frontend: geek-animation/start.sh 存在"
  else
    yellow "frontend: 未找到 geek-animation/start.sh"
    ok=false
  fi

  if cmd_backend_health >/dev/null 2>&1; then
    green "backend: $(backend_url) 可达"
  else
    yellow "backend: $(backend_url) 不可达"
    ok=false
  fi

  $ok || exit 1
}

main() {
  local cmd="${1:-help}"
  shift || true

  case "$cmd" in
    help|-h|--help) usage ;;
    build) cmd_build ;;
    test-compile) cmd_test_compile ;;
    test) cmd_test ;;
    clean) cmd_clean ;;

    frontend:start) cmd_frontend_start ;;
    frontend:start-debug) cmd_frontend_start_debug ;;
    frontend:compile) cmd_frontend_compile ;;
    ui:start) cmd_frontend_start ;;
    ui:start-debug) cmd_frontend_start_debug ;;
    ui:compile) cmd_frontend_compile ;;

    backend:start)
      if [[ "${1:-}" == "--daemon" ]]; then
        shift || true
        cmd_backend_start_daemon "$@"
      else
        cmd_backend_start_foreground "$@"
      fi
      ;;
    backend:stop) cmd_backend_stop ;;
    backend:status) cmd_backend_status ;;
    backend:logs) cmd_backend_logs ;;
    backend:health) cmd_backend_health ;;

    web:url) cmd_web_url ;;
    web:start)
      if [[ "${1:-}" == "--daemon" ]]; then
        shift || true
        cmd_backend_start_daemon "$@"
      else
        cmd_backend_start_foreground "$@"
      fi
      ;;
    web:health) cmd_backend_health ;;

    health) cmd_health ;;
    *) die "未知命令: $cmd (运行 ./start.sh help 查看帮助)" ;;
  esac
}

main "$@"
