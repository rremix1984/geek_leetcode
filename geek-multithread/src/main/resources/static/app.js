const state = {
  difficulty: "all",
  keyword: "",
  problems: [],
  selectedId: "",
  selectedProblem: null,
  loading: false,
  animationTimer: null,
  animationData: null,
  animationIndex: 0,
};

function $(id) {
  return document.getElementById(id);
}

function asNumber(value, fallback = 0) {
  const n = Number(value);
  return Number.isFinite(n) ? n : fallback;
}

async function fetchJson(url, options) {
  const res = await fetch(url, options);
  const text = await res.text();
  let data = null;
  try {
    data = text ? JSON.parse(text) : null;
  } catch (e) {
    data = null;
  }
  if (!res.ok) {
    throw new Error((data && data.error) || `${res.status} ${res.statusText}`);
  }
  return data;
}

function escapeHtml(input) {
  const map = { "&": "&amp;", "<": "&lt;", ">": "&gt;" };
  return (input || "").replace(/[&<>]/g, (c) => map[c]);
}

function renderProblemDescription(description) {
  const text = (description || "").replace(/\r/g, "").trim();
  if (!text) {
    return `<div class="desc-empty">暂无描述</div>`;
  }
  const lines = text.split("\n").map((line) => line.trim()).filter(Boolean);
  if (!lines.length) {
    return `<div class="desc-empty">暂无描述</div>`;
  }
  const model = parseDescriptionModel(lines);
  const title = model.title || lines[0];
  const summary = model.summary ? `<div class="desc-summary">${escapeHtml(model.summary)}</div>` : "";
  const cards = buildDescriptionCards(model.cards);
  const body = model.rest.map((line) => formatDescriptionLine(line)).join("");
  return `
    <div class="desc-title">${escapeHtml(title)}</div>
    ${summary}
    ${cards}
    <div class="desc-body">${body || ""}</div>
  `;
}

function parseDescriptionModel(lines) {
  const model = {
    title: "",
    summary: "",
    cards: [],
    rest: [],
  };
  lines.forEach((line, idx) => {
    const parsed = parseLabelLine(line);
    if (parsed && parsed.label === "题目") {
      model.title = parsed.value || line;
      return;
    }
    if (parsed && parsed.label === "说明") {
      model.summary = parsed.value || "";
      return;
    }
    if (parsed && (parsed.label === "输入" || parsed.label === "输出" || parsed.label === "示例")) {
      model.cards.push(parsed);
      return;
    }
    if (idx !== 0) {
      model.rest.push(line);
    }
  });
  if (!model.title) {
    const fallback = parseLabelLine(lines[0]);
    model.title = fallback ? fallback.value || lines[0] : lines[0];
  }
  return model;
}

function parseLabelLine(line) {
  const idx = Math.max(line.indexOf("："), line.indexOf(":"));
  if (idx <= 0) {
    return null;
  }
  const label = line.slice(0, idx).trim();
  const value = line.slice(idx + 1).trim();
  return { label, value };
}

function buildDescriptionCards(cards) {
  if (!cards || !cards.length) {
    return "";
  }
  const classMap = {
    输入: "desc-card-input",
    输出: "desc-card-output",
    示例: "desc-card-example",
  };
  const html = cards.map((card) => {
    const cls = classMap[card.label] || "desc-card-default";
    return `
      <article class="desc-card ${cls}">
        <div class="desc-card-head">${escapeHtml(card.label)}</div>
        <div class="desc-card-body">${escapeHtml(card.value || "暂无内容")}</div>
      </article>
    `;
  }).join("");
  return `<section class="desc-card-grid">${html}</section>`;
}

function formatDescriptionLine(line) {
  const escaped = escapeHtml(line);
  if (/^(示例|输入|输出|解释|提示|进阶|约束|限制)/.test(line)) {
    const splitIndex = line.indexOf("：");
    if (splitIndex > 0) {
      const head = escapeHtml(line.slice(0, splitIndex + 1));
      const tail = escapeHtml(line.slice(splitIndex + 1).trim());
      return `<div class="desc-line desc-line-key"><span class="desc-keyword">${head}</span><span class="desc-value">${tail}</span></div>`;
    }
    return `<div class="desc-line desc-line-key"><span class="desc-keyword">${escaped}</span></div>`;
  }
  if (/^\d+(\.|、)/.test(line)) {
    return `<div class="desc-line desc-line-step">${escaped}</div>`;
  }
  if (/^[•·\-]/.test(line)) {
    return `<div class="desc-line desc-line-bullet">${escaped.replace(/^[•·\-]\s*/, "• ")}</div>`;
  }
  return `<div class="desc-line">${escaped}</div>`;
}

function diffClass(diff) {
  const lower = (diff || "").toLowerCase();
  if (lower === "easy") return "tag tag-easy";
  if (lower === "normal" || lower === "medium") return "tag tag-normal";
  if (lower === "hard") return "tag tag-hard";
  return "tag";
}

function setHealthBadge(ok, text) {
  const el = $("healthBadge");
  el.textContent = text;
  el.classList.remove("ok", "err");
  el.classList.add(ok ? "ok" : "err");
}

function renderProblemList() {
  const root = $("problemList");
  root.innerHTML = "";
  if (!state.problems.length) {
    root.innerHTML = `<div class="problem-item">没有匹配题目</div>`;
    return;
  }
  state.problems.forEach((problem) => {
    const active = problem.id === state.selectedId ? " active" : "";
    const el = document.createElement("div");
    el.className = `problem-item${active}`;
    el.innerHTML = `
      <div class="problem-item-title">${escapeHtml(problem.name)}</div>
      <div class="problem-item-meta">
        <span class="${diffClass(problem.difficulty)}">${escapeHtml(problem.difficulty || "-")}</span>
        <span>${escapeHtml(problem.technique || "-")}</span>
        <span>${problem.solved ? "✅ 已通过" : "⏳ 未通过"}</span>
      </div>
    `;
    el.onclick = () => selectProblem(problem.id);
    root.appendChild(el);
  });
}

function setLoading(loading) {
  state.loading = loading;
  $("runBtn").disabled = loading || !state.selectedProblem;
  $("launchBtn").disabled = loading || !state.selectedProblem;
  $("submitBtn").disabled = loading || !state.selectedProblem;
}

function fillProblem(problem) {
  state.selectedProblem = problem;
  $("problemTitle").textContent = problem ? problem.name : "请选择题目";
  $("difficultyTag").textContent = (problem && problem.difficulty) || "-";
  $("difficultyTag").className = diffClass(problem && problem.difficulty);
  $("techniqueTag").textContent = (problem && problem.technique) || "技巧";
  $("categoryTag").textContent = (problem && problem.categoryPath) || "分类";
  $("problemDesc").innerHTML = renderProblemDescription(problem && problem.description);
  $("codeEditor").value = (problem && problem.code) || "";
  $("runBtn").disabled = !problem;
  $("launchBtn").disabled = !problem;
  $("submitBtn").disabled = !problem;
  setSolvedTag(problem && problem.solved);
  renderProgress(problem && problem.progress);
  resetAnimationCanvas();
}

async function loadProblems() {
  const params = new URLSearchParams();
  if (state.difficulty !== "all") params.set("difficulty", state.difficulty);
  if (state.keyword) params.set("q", state.keyword);
  state.problems = await fetchJson(`/api/problems?${params.toString()}`);
  renderProblemList();
}

async function selectProblem(id) {
  if (!id) return;
  state.selectedId = id;
  renderProblemList();
  setLoading(true);
  try {
    const detail = await fetchJson(`/api/problems/${encodeURIComponent(id)}`);
    fillProblem(detail);
    $("consoleOutput").textContent = "等待运行...";
  } catch (e) {
    $("consoleOutput").textContent = `加载题目失败: ${e.message}`;
  } finally {
    setLoading(false);
  }
}

async function runCode() {
  if (!state.selectedProblem) {
    $("consoleOutput").textContent = "请先选择题目后再运行。";
    return;
  }
  const code = $("codeEditor").value || "";
  setLoading(true);
  $("consoleOutput").textContent = "编译并运行中...";
  try {
    const result = await fetchJson("/api/run", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ code }),
    });
    if (result.success) {
      $("consoleOutput").textContent = result.output || "(无输出)";
    } else {
      $("consoleOutput").textContent = result.error || "运行失败";
    }
  } catch (e) {
    $("consoleOutput").textContent = `请求失败: ${e.message}`;
  } finally {
    setLoading(false);
  }
}

async function submitCode() {
  if (!state.selectedProblem) {
    $("submitVerdict").textContent = "请先选择题目";
    $("submitCases").textContent = "请先从左侧题目列表选择一道题后再提交。";
    return;
  }
  const code = $("codeEditor").value || "";
  setLoading(true);
  $("submitVerdict").textContent = "判题中...";
  $("submitVerdict").className = "submit-verdict";
  try {
    const result = await fetchJson("/api/submit", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ id: state.selectedProblem.id, code }),
    });
    if (!result.ok) {
      $("submitVerdict").textContent = "提交失败";
      $("submitCases").textContent = result.error || "未知错误";
      return;
    }
    const accepted = !!result.accepted;
    $("submitVerdict").textContent = accepted ? "Accepted" : "Wrong Answer";
    $("submitVerdict").className = `submit-verdict ${accepted ? "accepted" : "wrong"}`;
    const passRate = asNumber(result.passRate, 0);
    const passCount = asNumber(result.passCount, 0);
    const totalCases = asNumber(result.totalCases, 0);
    $("submitPassRate").textContent = `通过率: ${passRate.toFixed(2)}% (${passCount}/${totalCases})`;
    $("submitRuntime").textContent = `耗时: ${asNumber(result.runtimeMs, 0)} ms`;
    $("submitMemory").textContent = `内存: ${asNumber(result.memoryKb, 0)} KB`;
    const lines = [];
    if (result.error) {
      lines.push(`编译/执行错误: ${result.error}`);
    }
    (result.cases || []).forEach((c) => {
      lines.push(`Case #${c.index} | ${c.passed ? "PASS" : "FAIL"} | ${c.runtimeMs || 0}ms | ${c.memoryKb || 0}KB`);
      lines.push(`  input: ${String(c.input || "").replace(/\n/g, " | ")}`);
      lines.push(`  expected: ${c.expected || ""}`);
      lines.push(`  actual: ${c.actual || ""}`);
      if (c.error) {
        lines.push(`  error: ${c.error}`);
      }
    });
    $("submitCases").textContent = lines.join("\n") || "无明细";
    if (result.progress) {
      state.selectedProblem.solved = !!result.progress.accepted;
      state.selectedProblem.progress = result.progress;
      setSolvedTag(state.selectedProblem.solved);
      renderProblemList();
    }
  } catch (e) {
    $("submitVerdict").textContent = "提交失败";
    $("submitCases").textContent = `请求失败: ${e.message}`;
  } finally {
    setLoading(false);
  }
}

async function launchAnimation() {
  if (!state.selectedProblem) return;
  setLoading(true);
  try {
    const data = await fetchJson(`/api/visualize/${encodeURIComponent(state.selectedProblem.id)}`);
    startAnimationPlayback(data);
    const panel = document.querySelector(".animation-panel");
    if (panel) {
      panel.scrollIntoView({ behavior: "smooth", block: "center" });
    }
    $("consoleOutput").textContent = "前端动画播放中。";
  } catch (e) {
    $("consoleOutput").textContent = `播放动画失败: ${e.message}`;
  } finally {
    setLoading(false);
  }
}

function setSolvedTag(solved) {
  const el = $("solvedTag");
  const yes = !!solved;
  el.textContent = yes ? "已通过" : "未通过";
  el.className = `tag ${yes ? "tag-solved" : "tag-unsolved"}`;
}

function renderProgress(progress) {
  const p = progress || {};
  $("submitVerdict").textContent = p.accepted ? "Accepted" : "等待提交";
  $("submitVerdict").className = `submit-verdict ${p.accepted ? "accepted" : ""}`;
  const attempts = Number.isFinite(p.attempts) ? p.attempts : 0;
  $("submitPassRate").textContent = `提交次数: ${attempts}`;
  $("submitRuntime").textContent = `最佳耗时: ${p.bestRuntimeMs || 0} ms`;
  $("submitMemory").textContent = `最佳内存: ${p.bestMemoryKb || 0} KB`;
  const extra = [];
  if (p.lastSubmittedAt) extra.push(`最近提交: ${p.lastSubmittedAt}`);
  if (p.lastAcceptedAt) extra.push(`最近通过: ${p.lastAcceptedAt}`);
  $("submitCases").textContent = extra.join("\n") || "点击 Submit 后展示多测试用例明细。";
}

function resetAnimationCanvas() {
  stopAnimationPlayback();
  state.animationData = null;
  state.animationIndex = 0;
  const canvas = $("animationCanvas");
  const ctx = canvas.getContext("2d");
  resizeCanvas(canvas);
  ctx.fillStyle = "#0f172a";
  ctx.fillRect(0, 0, canvas.width, canvas.height);
  ctx.fillStyle = "#93c5fd";
  ctx.font = "16px sans-serif";
  ctx.fillText("点击“播放动画”开始可视化", 20, 40);
  $("animationDesc").textContent = "点击“播放动画”开始可视化";
}

function resizeCanvas(canvas) {
  const cssWidth = canvas.clientWidth;
  const cssHeight = canvas.clientHeight;
  if (cssWidth && cssHeight && (canvas.width !== cssWidth || canvas.height !== cssHeight)) {
    canvas.width = cssWidth;
    canvas.height = cssHeight;
  }
}

function startAnimationPlayback(data) {
  stopAnimationPlayback();
  state.animationData = data || null;
  state.animationIndex = 0;
  const frames = (data && data.frames) || [];
  if (!frames.length) {
    $("animationDesc").textContent = "没有可播放的动画帧";
    return;
  }
  drawFrame(frames[0], data);
  state.animationTimer = window.setInterval(() => {
    state.animationIndex += 1;
    if (state.animationIndex >= frames.length) {
      stopAnimationPlayback();
      return;
    }
    drawFrame(frames[state.animationIndex], data);
  }, (data && data.intervalMs) || 1000);
}

function stopAnimationPlayback() {
  if (state.animationTimer) {
    window.clearInterval(state.animationTimer);
    state.animationTimer = null;
  }
}

function drawFrame(frame, animationData) {
  const canvas = $("animationCanvas");
  const ctx = canvas.getContext("2d");
  resizeCanvas(canvas);
  ctx.fillStyle = "#0f172a";
  ctx.fillRect(0, 0, canvas.width, canvas.height);
  const type = (animationData && animationData.type) || "array";
  if (type === "tree") {
    drawTreeFrame(ctx, canvas, frame);
  } else if (type === "graph") {
    drawGraphFrame(ctx, canvas, frame);
  } else if (frame && frame.sqrtMode) {
    drawSqrtFrame(ctx, canvas, frame);
  } else if (type === "backtracking") {
    drawBacktrackingFrame(ctx, canvas, frame, animationData);
  } else if (type === "hash") {
    drawHashFrame(ctx, canvas, frame);
  } else if (type === "dp") {
    drawDpFrame(ctx, canvas, frame);
  } else if (type === "two-pointers" || type === "heap" || type === "stack") {
    drawArrayFrame(ctx, canvas, frame);
  } else {
    drawArrayFrame(ctx, canvas, frame);
  }

  const desc = (frame && frame.description) || "";
  const title = (animationData && animationData.title) || "";
  const technique = (animationData && animationData.technique) || "";
  $("animationDesc").textContent = `${title} | ${technique} | Step ${state.animationIndex + 1}/${((animationData && animationData.frames) || []).length} | ${desc}`;

  const width = canvas.width;
  ctx.fillStyle = "#cbd5e1";
  ctx.font = "12px sans-serif";
  if (frame.target !== undefined) {
    ctx.fillText(`target: ${frame.target}`, width - 180, 24);
  }
  if (Array.isArray(frame.mapValues)) {
    ctx.fillText(`map: [${frame.mapValues.join(", ")}]`, 20, 24);
  }
  if (frame.profit !== undefined) {
    ctx.fillText(`maxProfit: ${frame.profit}`, width - 180, 24);
  }
  if (frame.maxReach !== undefined) {
    ctx.fillText(`maxReach: ${frame.maxReach}`, width - 180, 42);
  }
  if (frame.steps !== undefined) {
    ctx.fillText(`steps: ${frame.steps}`, width - 180, 60);
  }
}

function drawSqrtFrame(ctx, canvas, frame) {
  const arr = (frame && frame.array) || [];
  const width = canvas.width;
  const height = canvas.height;
  const x = Number(frame && frame.target);
  const left = Number.isInteger(frame && frame.left) ? frame.left : -1;
  const right = Number.isInteger(frame && frame.right) ? frame.right : -1;
  const mid = Number.isInteger(frame && frame.mid) ? frame.mid : -1;
  const result = Number.isInteger(frame && frame.result) ? frame.result : -1;
  const usable = Math.max(1, arr.length - 1);
  const startX = 40;
  const endX = width - 280;
  const lineY = Math.max(130, Math.floor(height * 0.52));
  ctx.strokeStyle = "#334155";
  ctx.lineWidth = 2;
  ctx.beginPath();
  ctx.moveTo(startX, lineY);
  ctx.lineTo(endX, lineY);
  ctx.stroke();
  arr.forEach((value, idx) => {
    const ratio = idx / usable;
    const px = startX + (endX - startX) * ratio;
    ctx.strokeStyle = "#475569";
    ctx.beginPath();
    ctx.moveTo(px, lineY - 10);
    ctx.lineTo(px, lineY + 10);
    ctx.stroke();
    ctx.fillStyle = "#94a3b8";
    ctx.font = "12px monospace";
    ctx.fillText(String(value), px - 7, lineY + 30);
  });
  if (left >= 0 && left < arr.length) {
    const px = startX + ((endX - startX) * left) / usable;
    ctx.fillStyle = "#22c55e";
    ctx.beginPath();
    ctx.arc(px, lineY, 9, 0, Math.PI * 2);
    ctx.fill();
    ctx.fillStyle = "#d1fae5";
    ctx.font = "12px sans-serif";
    ctx.fillText(`L=${left}`, px - 14, lineY - 18);
  }
  if (right >= 0 && right < arr.length) {
    const px = startX + ((endX - startX) * right) / usable;
    ctx.fillStyle = "#16a34a";
    ctx.beginPath();
    ctx.arc(px, lineY, 9, 0, Math.PI * 2);
    ctx.fill();
    ctx.fillStyle = "#dcfce7";
    ctx.font = "12px sans-serif";
    ctx.fillText(`R=${right}`, px - 14, lineY - 34);
  }
  if (mid >= 0 && mid < arr.length) {
    const px = startX + ((endX - startX) * mid) / usable;
    ctx.fillStyle = "#f97316";
    ctx.beginPath();
    ctx.arc(px, lineY, 11, 0, Math.PI * 2);
    ctx.fill();
    ctx.fillStyle = "#ffedd5";
    ctx.font = "12px sans-serif";
    ctx.fillText(`M=${mid}`, px - 14, lineY + 52);
  }
  if (result >= 0 && result < arr.length) {
    const px = startX + ((endX - startX) * result) / usable;
    ctx.fillStyle = "#ef4444";
    ctx.beginPath();
    ctx.arc(px, lineY - 36, 10, 0, Math.PI * 2);
    ctx.fill();
    ctx.fillStyle = "#fee2e2";
    ctx.font = "12px sans-serif";
    ctx.fillText(`答案=${result}`, px - 20, lineY - 52);
  }
  const panelX = width - 250;
  ctx.fillStyle = "#93c5fd";
  ctx.font = "15px sans-serif";
  ctx.fillText("平方根状态", panelX, 54);
  ctx.font = "13px sans-serif";
  ctx.fillText(`x = ${Number.isFinite(x) ? x : "-"}`, panelX, 82);
  const candidate = Number.isInteger(frame && frame.mid) ? frame.mid : -1;
  const square = Number(frame && frame.square);
  ctx.fillText(`候选 mid = ${candidate >= 0 ? candidate : "-"}`, panelX, 106);
  ctx.fillText(`mid² = ${Number.isFinite(square) ? square : "-"}`, panelX, 130);
  const relation = Number.isFinite(square) && Number.isFinite(x) ? (square > x ? ">" : square < x ? "<" : "=") : "?";
  ctx.fillText(`比较: mid² ${relation} x`, panelX, 154);
  const floorHint = Number.isFinite(x) ? `⌊√${x}⌋` : "⌊√x⌋";
  ctx.fillStyle = "#fef08a";
  ctx.fillText(`目标: ${floorHint}`, panelX, 182);
  if (left >= 0 && right >= 0) {
    ctx.fillStyle = "#cbd5e1";
    ctx.fillText(`当前搜索区间: [${left}, ${right}]`, 40, 48);
  }
}

function drawArrayFrame(ctx, canvas, frame) {
  if (frame && frame.mode === "sorted-squares") {
    drawSortedSquaresFrame(ctx, canvas, frame);
    return;
  }
  const arr = (frame && frame.array) || [];
  const width = canvas.width;
  const height = canvas.height;
  const padding = 26;
  const barWidth = arr.length ? Math.max(34, Math.min(86, (width - padding * 2) / arr.length - 8)) : 40;
  const gap = 8;
  const maxValue = arr.reduce((m, n) => Math.max(m, Math.abs(n || 0)), 1) || 1;
  const centers = [];
  arr.forEach((value, i) => {
    const x = padding + i * (barWidth + gap);
    centers.push(x + barWidth / 2);
    const normalized = Math.abs(value || 0) / maxValue;
    const h = Math.max(20, normalized * 108);
    const y = height - 48 - h;
    let color = "#475569";
    if (frame.mid === i) color = "#f97316";
    else if (frame.left === i || frame.right === i) color = "#22c55e";
    else if (frame.active === i) color = "#3b82f6";
    if (frame.buyIndex === i) color = "#06b6d4";
    if (frame.sellIndex === i) color = "#f43f5e";
    const result = frame.result;
    if (Array.isArray(result) && result.includes(i)) color = "#ef4444";
    if (result === i) color = "#ef4444";
    ctx.fillStyle = color;
    ctx.fillRect(x, y, barWidth, h);
    ctx.fillStyle = "#e2e8f0";
    ctx.font = "13px monospace";
    ctx.fillText(String(value), x + 8, y - 8);
    ctx.fillStyle = "#94a3b8";
    ctx.fillText(String(i), x + 8, height - 24);
  });
  const hasJump = frame && frame.jumpFrom !== undefined && frame.jumpTo !== undefined;
  if (hasJump && centers.length) {
    const from = Number(frame.jumpFrom);
    const to = Number(frame.jumpTo);
    const progressRaw = Number(frame.jumpProgress);
    const progress = Number.isFinite(progressRaw) ? Math.max(0, Math.min(1, progressRaw)) : 0;
    if (from >= 0 && from < centers.length && to >= 0 && to < centers.length) {
      const fromX = centers[from];
      const toX = centers[to];
      const baseY = height - 170;
      const peak = Math.max(42, Math.min(84, Math.abs(toX - fromX) * 0.28));
      const controlX = (fromX + toX) / 2;
      const controlY = baseY - peak;
      const drawToT = 0.2 + progress * 0.8;
      ctx.strokeStyle = "rgba(251, 191, 36, 0.9)";
      ctx.lineWidth = 2;
      ctx.beginPath();
      for (let t = 0; t <= drawToT; t += 0.02) {
        const x = (1 - t) * (1 - t) * fromX + 2 * (1 - t) * t * controlX + t * t * toX;
        const y = (1 - t) * (1 - t) * baseY + 2 * (1 - t) * t * controlY + t * t * baseY;
        if (t === 0) {
          ctx.moveTo(x, y);
        } else {
          ctx.lineTo(x, y);
        }
      }
      ctx.stroke();
      const t = progress;
      const playerX = (1 - t) * (1 - t) * fromX + 2 * (1 - t) * t * controlX + t * t * toX;
      const playerY = (1 - t) * (1 - t) * baseY + 2 * (1 - t) * t * controlY + t * t * baseY;
      ctx.fillStyle = "#ef4444";
      ctx.beginPath();
      ctx.arc(playerX, playerY, 10, 0, Math.PI * 2);
      ctx.fill();
      ctx.fillStyle = "#fef2f2";
      ctx.font = "11px sans-serif";
      ctx.textAlign = "center";
      ctx.textBaseline = "middle";
      ctx.fillText("J", playerX, playerY + 0.5);
      ctx.textAlign = "left";
      ctx.textBaseline = "alphabetic";
    }
  }
}

function drawSortedSquaresFrame(ctx, canvas, frame) {
  const source = Array.isArray(frame && frame.source) ? frame.source : [];
  const result = Array.isArray(frame && frame.array) ? frame.array : [];
  const width = canvas.width;
  const left = Number.isInteger(frame && frame.left) ? frame.left : -1;
  const right = Number.isInteger(frame && frame.right) ? frame.right : -1;
  const active = Number.isInteger(frame && frame.active) ? frame.active : -1;
  const leftSquare = Number(frame && frame.leftSquare);
  const rightSquare = Number(frame && frame.rightSquare);
  const count = Math.max(source.length, result.length, 1);
  const gap = 10;
  const cell = Math.max(40, Math.min(68, (width - 80 - gap * (count - 1)) / count));
  const rowX = 28;
  const sourceY = 80;
  const resultY = 220;
  ctx.fillStyle = "#93c5fd";
  ctx.font = "14px sans-serif";
  ctx.fillText("原始有序数组 nums", rowX, sourceY - 16);
  ctx.fillText("结果数组 result（从右向左填充）", rowX, resultY - 16);
  for (let i = 0; i < count; i++) {
    const x = rowX + i * (cell + gap);
    const sourceValue = source[i];
    const resultValue = result[i];
    ctx.fillStyle = i === left || i === right ? "#0ea5e9" : "#334155";
    ctx.fillRect(x, sourceY, cell, 42);
    ctx.fillStyle = "#e2e8f0";
    ctx.font = "13px monospace";
    ctx.fillText(sourceValue === undefined ? "" : String(sourceValue), x + 10, sourceY + 26);
    ctx.fillStyle = "#94a3b8";
    ctx.fillText(String(i), x + Math.max(6, cell / 2 - 4), sourceY + 58);
    ctx.fillStyle = i === active ? "#22c55e" : "#475569";
    ctx.fillRect(x, resultY, cell, 42);
    ctx.fillStyle = "#f8fafc";
    ctx.fillText(resultValue === 0 ? "0" : resultValue ? String(resultValue) : "_", x + 10, resultY + 26);
    ctx.fillStyle = "#94a3b8";
    ctx.fillText(String(i), x + Math.max(6, cell / 2 - 4), resultY + 58);
  }
  if (left >= 0 && right >= 0) {
    const panelX = width - 250;
    ctx.fillStyle = "#93c5fd";
    ctx.font = "14px sans-serif";
    ctx.fillText("双指针状态", panelX, 92);
    ctx.fillStyle = "#cbd5e1";
    ctx.fillText(`left = ${left}，right = ${right}`, panelX, 118);
    ctx.fillText(`写入位置 = ${active >= 0 ? active : "-"}`, panelX, 144);
    ctx.fillText(`|nums[left]|² = ${Number.isFinite(leftSquare) ? leftSquare : "-"}`, panelX, 170);
    ctx.fillText(`|nums[right]|² = ${Number.isFinite(rightSquare) ? rightSquare : "-"}`, panelX, 196);
  }
}

function drawTreeFrame(ctx, canvas, frame) {
  const nodes = (frame && frame.array) || [];
  const width = canvas.width;
  const active = frame && frame.active;
  const radius = 16;
  const points = nodes.map((_, i) => {
    const level = Math.floor(Math.log2(i + 1));
    const levelStart = 2 ** level - 1;
    const offset = i - levelStart;
    const count = 2 ** level;
    const x = ((offset + 1) * width) / (count + 1);
    const y = 50 + level * 48;
    return { x, y };
  });
  for (let i = 0; i < points.length; i++) {
    const left = i * 2 + 1;
    const right = i * 2 + 2;
    ctx.strokeStyle = "#334155";
    if (left < points.length) {
      ctx.beginPath();
      ctx.moveTo(points[i].x, points[i].y);
      ctx.lineTo(points[left].x, points[left].y);
      ctx.stroke();
    }
    if (right < points.length) {
      ctx.beginPath();
      ctx.moveTo(points[i].x, points[i].y);
      ctx.lineTo(points[right].x, points[right].y);
      ctx.stroke();
    }
  }
  points.forEach((p, i) => {
    ctx.beginPath();
    ctx.fillStyle = i === active ? "#f97316" : "#1d4ed8";
    ctx.arc(p.x, p.y, radius, 0, Math.PI * 2);
    ctx.fill();
    ctx.fillStyle = "#ffffff";
    ctx.font = "12px sans-serif";
    ctx.fillText(String(nodes[i]), p.x - 8, p.y + 4);
  });
}

function drawGraphFrame(ctx, canvas, frame) {
  if (Array.isArray(frame && frame.grid) && frame.grid.length) {
    drawGridGraphFrame(ctx, canvas, frame);
    return;
  }
  const width = canvas.width;
  const height = canvas.height;
  const nodes = [0, 1, 2, 3, 4, 5];
  const positions = [
    [width * 0.15, height * 0.55],
    [width * 0.32, height * 0.32],
    [width * 0.32, height * 0.78],
    [width * 0.52, height * 0.55],
    [width * 0.72, height * 0.32],
    [width * 0.72, height * 0.78],
  ];
  const edges = [
    [0, 1],
    [0, 2],
    [1, 3],
    [2, 3],
    [3, 4],
    [3, 5],
  ];
  ctx.strokeStyle = "#334155";
  edges.forEach(([a, b]) => {
    ctx.beginPath();
    ctx.moveTo(positions[a][0], positions[a][1]);
    ctx.lineTo(positions[b][0], positions[b][1]);
    ctx.stroke();
  });
  nodes.forEach((n) => {
    const active = frame && frame.active === n;
    ctx.beginPath();
    ctx.fillStyle = active ? "#f97316" : "#0ea5e9";
    ctx.arc(positions[n][0], positions[n][1], 18, 0, Math.PI * 2);
    ctx.fill();
    ctx.fillStyle = "#ffffff";
    ctx.font = "12px sans-serif";
    ctx.fillText(String(n), positions[n][0] - 4, positions[n][1] + 4);
  });
}

function drawGridGraphFrame(ctx, canvas, frame) {
  const grid = frame.grid || [];
  if (!grid.length || !grid[0].length) return;
  const rows = grid.length;
  const cols = grid[0].length;
  const width = canvas.width;
  const height = canvas.height;
  const panelWidth = Math.min(300, Math.floor(width * 0.38));
  const usableWidth = width - panelWidth - 64;
  const usableHeight = height - 70;
  const cell = Math.max(18, Math.min(60, Math.floor(Math.min(usableWidth / cols, usableHeight / rows))));
  const boardWidth = cell * cols;
  const boardHeight = cell * rows;
  const startX = 28;
  const startY = Math.max(18, Math.floor((height - boardHeight) / 2));
  const activeRow = Number.isInteger(frame.activeRow) ? frame.activeRow : -1;
  const activeCol = Number.isInteger(frame.activeCol) ? frame.activeCol : -1;
  const neighborRow = Number.isInteger(frame.neighborRow) ? frame.neighborRow : -1;
  const neighborCol = Number.isInteger(frame.neighborCol) ? frame.neighborCol : -1;
  const visited = Array.isArray(frame.visited) ? frame.visited : [];
  const marks = Array.isArray(frame.islandMarks) ? frame.islandMarks : [];
  for (let r = 0; r < rows; r++) {
    for (let c = 0; c < cols; c++) {
      const x = startX + c * cell;
      const y = startY + r * cell;
      const isLand = Number(grid[r][c]) === 1;
      const visitedCell = visited[r] && Number(visited[r][c]) === 1;
      const markId = marks[r] ? Number(marks[r][c] || 0) : 0;
      let fill = isLand ? "#334155" : "#0f172a";
      if (visitedCell) {
        fill = "#0ea5e9";
      }
      if (markId > 0) {
        fill = `hsl(${(markId * 67) % 360}, 70%, 52%)`;
      }
      if (r === activeRow && c === activeCol) {
        fill = "#f97316";
      }
      if (r === neighborRow && c === neighborCol) {
        fill = "#22c55e";
      }
      ctx.fillStyle = fill;
      ctx.fillRect(x, y, cell - 1, cell - 1);
      ctx.strokeStyle = "#1e293b";
      ctx.strokeRect(x, y, cell, cell);
      ctx.fillStyle = "#e2e8f0";
      ctx.font = `${Math.max(10, Math.floor(cell * 0.26))}px monospace`;
      ctx.fillText(`${r},${c}`, x + 4, y + 13);
    }
  }
  if (activeRow >= 0 && activeCol >= 0) {
    const x = startX + activeCol * cell;
    const y = startY + activeRow * cell;
    const right = x + cell;
    const bottom = y + cell;
    ctx.strokeStyle = "rgba(251, 191, 36, 0.95)";
    ctx.lineWidth = 3;
    ctx.strokeRect(x + 1, y + 1, cell - 2, cell - 2);
    ctx.lineWidth = 1;
    if (frame.edgeAdded !== undefined) {
      const dir = frame.direction || "";
      ctx.strokeStyle = Number(frame.edgeAdded) > 0 ? "#f59e0b" : "#38bdf8";
      ctx.lineWidth = 4;
      if (dir === "右") {
        ctx.beginPath();
        ctx.moveTo(right, y + 4);
        ctx.lineTo(right, bottom - 4);
        ctx.stroke();
      } else if (dir === "下") {
        ctx.beginPath();
        ctx.moveTo(x + 4, bottom);
        ctx.lineTo(right - 4, bottom);
        ctx.stroke();
      } else if (dir === "左") {
        ctx.beginPath();
        ctx.moveTo(x, y + 4);
        ctx.lineTo(x, bottom - 4);
        ctx.stroke();
      } else if (dir === "上") {
        ctx.beginPath();
        ctx.moveTo(x + 4, y);
        ctx.lineTo(right - 4, y);
        ctx.stroke();
      }
      ctx.lineWidth = 1;
    }
  }

  const panelX = startX + boardWidth + 24;
  ctx.fillStyle = "#93c5fd";
  ctx.font = "15px sans-serif";
  ctx.fillText("网格算法状态", panelX, startY + 20);
  ctx.font = "13px sans-serif";
  let infoY = startY + 48;
  if (frame.perimeter !== undefined) {
    ctx.fillText(`当前总周长: ${frame.perimeter}`, panelX, infoY);
    infoY += 24;
  }
  if (frame.cellContribution !== undefined) {
    ctx.fillText(`当前格贡献: ${frame.cellContribution}`, panelX, infoY);
    infoY += 24;
  }
  if (frame.edgeAdded !== undefined) {
    ctx.fillText(`当前边贡献: +${frame.edgeAdded}`, panelX, infoY);
    infoY += 24;
  }
  if (frame.islandCount !== undefined) {
    ctx.fillText(`已发现岛屿: ${frame.islandCount}`, panelX, infoY);
    infoY += 24;
  }
  if (activeRow >= 0 && activeCol >= 0) {
    ctx.fillText(`当前坐标: (${activeRow}, ${activeCol})`, panelX, infoY);
  }
}

function isNQueensAnimation(animationData) {
  const id = ((animationData && animationData.id) || "").toLowerCase();
  const title = ((animationData && animationData.title) || "").toLowerCase();
  return id === "no-51-n皇后" || id === "no-52-n皇后-ii" || title.includes("n皇后");
}

function drawBacktrackingFrame(ctx, canvas, frame, animationData) {
  if (isNQueensAnimation(animationData)) {
    drawNQueensFrame(ctx, canvas, frame);
    return;
  }
  const choices = (frame && frame.array) || [];
  const path = (frame && frame.result) || [];
  const active = frame && frame.active;
  choices.forEach((value, idx) => {
    const x = 30 + idx * 80;
    const y = 50;
    ctx.fillStyle = idx === active ? "#f97316" : "#1f2937";
    ctx.fillRect(x, y, 64, 34);
    ctx.fillStyle = "#e2e8f0";
    ctx.font = "14px sans-serif";
    ctx.fillText(String(value), x + 26, y + 22);
  });
  ctx.fillStyle = "#93c5fd";
  ctx.font = "14px sans-serif";
  ctx.fillText("当前路径", 30, 130);
  path.forEach((value, idx) => {
    const x = 30 + idx * 90;
    const y = 150;
    ctx.fillStyle = "#16a34a";
    ctx.fillRect(x, y, 72, 36);
    ctx.fillStyle = "#ffffff";
    ctx.fillText(String(value), x + 30, y + 24);
  });
}

function drawNQueensFrame(ctx, canvas, frame) {
  const path = Array.isArray(frame && frame.result) ? frame.result : [];
  const boardSize = Math.max(4, ((frame && frame.array) || []).length || 0, path.length);
  const activeRowRaw = Number.isInteger(frame && frame.active) ? frame.active : path.length - 1;
  const activeRow = Math.max(0, Math.min(boardSize - 1, activeRowRaw));
  const boardWidth = Math.min(320, canvas.height - 56, canvas.width * 0.6);
  const cell = Math.floor(boardWidth / boardSize);
  const actualBoard = cell * boardSize;
  const startX = 28;
  const startY = Math.floor((canvas.height - actualBoard) / 2);
  for (let row = 0; row < boardSize; row++) {
    for (let col = 0; col < boardSize; col++) {
      const x = startX + col * cell;
      const y = startY + row * cell;
      ctx.fillStyle = (row + col) % 2 === 0 ? "#e2e8f0" : "#94a3b8";
      if (row === activeRow) {
        ctx.fillStyle = (row + col) % 2 === 0 ? "#fed7aa" : "#fdba74";
      }
      ctx.fillRect(x, y, cell, cell);
    }
  }
  for (let row = 0; row < path.length; row++) {
    const col = Number(path[row]);
    if (!Number.isFinite(col) || col < 0 || col >= boardSize) {
      continue;
    }
    const x = startX + col * cell + cell / 2;
    const y = startY + row * cell + cell / 2;
    ctx.fillStyle = row === activeRow ? "#ea580c" : "#1e3a8a";
    ctx.font = `${Math.max(22, Math.floor(cell * 0.62))}px sans-serif`;
    ctx.textAlign = "center";
    ctx.textBaseline = "middle";
    ctx.fillText("♛", x, y + 1);
  }
  ctx.textAlign = "left";
  ctx.textBaseline = "alphabetic";
  ctx.fillStyle = "#93c5fd";
  ctx.font = "14px sans-serif";
  ctx.fillText(`棋盘: ${boardSize} x ${boardSize}`, startX + actualBoard + 26, startY + 18);
  ctx.fillText(`已放置: ${path.length} / ${boardSize}`, startX + actualBoard + 26, startY + 46);
  const top = startY + 78;
  for (let i = 0; i < Math.min(path.length, boardSize); i++) {
    const col = Number(path[i]);
    const rowText = `第${i + 1}行 -> 第${col + 1}列`;
    ctx.fillStyle = i === activeRow ? "#f97316" : "#e2e8f0";
    ctx.fillText(rowText, startX + actualBoard + 26, top + i * 24);
  }
  if (frame && frame.profit !== undefined) {
    ctx.fillStyle = "#22c55e";
    ctx.fillText(`当前解数量: ${frame.profit}`, startX + actualBoard + 26, top + (boardSize + 1) * 24);
  }
}

function drawHashFrame(ctx, canvas, frame) {
  const nums = (frame && frame.array) || [];
  const active = Number.isInteger(frame && frame.active) ? frame.active : -1;
  const target = Number(frame && frame.target);
  if (nums.length) {
    nums.forEach((value, idx) => {
      const x = 30 + idx * 86;
      const y = 44;
      ctx.fillStyle = idx === active ? "#ef4444" : "#334155";
      ctx.fillRect(x, y, 72, 30);
      ctx.fillStyle = "#e2e8f0";
      ctx.font = "13px sans-serif";
      ctx.fillText(String(value), x + 28, y + 20);
      ctx.fillStyle = "#94a3b8";
      ctx.font = "11px monospace";
      ctx.fillText(`i=${idx}`, x + 24, y + 44);
    });
    if (active >= 0 && active < nums.length && Number.isFinite(target)) {
      const current = Number(nums[active]);
      const need = target - current;
      ctx.fillStyle = "#93c5fd";
      ctx.font = "14px sans-serif";
      ctx.fillText(`当前值: ${current}，需要匹配: ${need}，目标: ${target}`, 30, 108);
    }
  }
  const values = (frame && frame.mapValues) || [];
  ctx.fillStyle = "#93c5fd";
  ctx.font = "14px sans-serif";
  ctx.fillText("HashMap 桶视图", 30, 134);
  for (let i = 0; i < 6; i++) {
    const y = 148 + i * 28;
    ctx.strokeStyle = "#475569";
    ctx.strokeRect(30, y, 240, 22);
    ctx.fillStyle = "#94a3b8";
    ctx.font = "12px monospace";
    ctx.fillText(`bucket ${i}`, 284, y + 15);
  }
  values.forEach((v, idx) => {
    const bucket = Math.abs(Number(v) || 0) % 6;
    const y = 148 + bucket * 28;
    const x = 36 + idx * 34;
    ctx.fillStyle = "#22c55e";
    ctx.fillRect(x, y + 3, 28, 16);
    ctx.fillStyle = "#0f172a";
    ctx.font = "11px monospace";
    ctx.fillText(String(v), x + 7, y + 15);
  });
}

function drawDpFrame(ctx, canvas, frame) {
  const arr = (frame && frame.array) || [];
  const active = frame && frame.active;
  arr.forEach((value, i) => {
    const x = 24 + i * 82;
    const y = 70;
    ctx.fillStyle = i === active ? "#f97316" : "#334155";
    ctx.fillRect(x, y, 74, 44);
    ctx.fillStyle = "#e2e8f0";
    ctx.font = "13px monospace";
    ctx.fillText(`dp[${i}]`, x + 15, y + 16);
    ctx.fillText(String(value), x + 26, y + 33);
  });
  if (frame && frame.profit !== undefined) {
    ctx.fillStyle = "#22c55e";
    ctx.font = "15px sans-serif";
    ctx.fillText(`当前最优: ${frame.profit}`, 24, 150);
  }
}

function bindEvents() {
  document.querySelectorAll(".difficulty-tab").forEach((btn) => {
    btn.addEventListener("click", async () => {
      document.querySelectorAll(".difficulty-tab").forEach((el) => el.classList.remove("active"));
      btn.classList.add("active");
      state.difficulty = btn.dataset.diff || "all";
      await loadProblems();
      if (state.problems.length) {
        await selectProblem(state.problems[0].id);
      } else {
        fillProblem(null);
      }
    });
  });

  $("searchInput").addEventListener("input", async (e) => {
    state.keyword = (e.target.value || "").trim();
    await loadProblems();
    if (state.selectedId && !state.problems.find((p) => p.id === state.selectedId)) {
      if (state.problems.length) {
        await selectProblem(state.problems[0].id);
      } else {
        state.selectedId = "";
        fillProblem(null);
      }
    } else {
      renderProblemList();
    }
  });

  $("runBtn").addEventListener("click", runCode);
  $("launchBtn").addEventListener("click", launchAnimation);
  $("submitBtn").addEventListener("click", submitCode);
}

async function init() {
  bindEvents();
  try {
    const health = await fetchJson("/api/health");
    setHealthBadge(true, `Backend: ${health.status}`);
  } catch (e) {
    setHealthBadge(false, "Backend Down");
  }

  try {
    await loadProblems();
    if (state.problems.length) {
      await selectProblem(state.problems[0].id);
    } else {
      fillProblem(null);
    }
  } catch (e) {
    $("consoleOutput").textContent = `初始化失败: ${e.message}`;
  }
  resetAnimationCanvas();
}

init();
