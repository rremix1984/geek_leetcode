const state = {
  tab: "catalog",
  catalogItems: [],
  allNames: [],
  selected: null,
  activeNodeEl: null,
};

function $(id) {
  return document.getElementById(id);
}

function setHealth(text) {
  $("healthBox").textContent = text;
}

async function fetchJson(url, options) {
  const res = await fetch(url, options);
  const text = await res.text();
  let data = null;
  try {
    data = text ? JSON.parse(text) : null;
  } catch (e) {
    data = text;
  }
  if (!res.ok) {
    const msg = data && data.error ? data.error : `${res.status} ${res.statusText}`;
    throw new Error(msg);
  }
  return data;
}

function buildTree(items) {
  const root = { label: "root", children: new Map(), items: [] };

  for (const item of items) {
    const parts = (item.categoryPath || "").split("/").filter(Boolean);
    let node = root;
    for (const p of parts) {
      if (!node.children.has(p)) {
        node.children.set(p, { label: p, children: new Map(), items: [] });
      }
      node = node.children.get(p);
    }
    node.items.push(item);
  }

  return root;
}

function clearActiveNode() {
  if (state.activeNodeEl) {
    state.activeNodeEl.classList.remove("active");
    state.activeNodeEl = null;
  }
}

function setSelected(item) {
  state.selected = item;
  clearActiveNode();

  $("algoName").textContent = item ? item.name : "请选择一个算法";
  $("algoDiff").textContent = item && item.difficulty ? item.difficulty : "-";
  $("algoTech").textContent = item && item.technique ? item.technique : "-";
  $("algoLaunchable").textContent = item ? (item.launchable ? "是" : "否") : "-";
  $("launchBtn").disabled = !(item && item.launchable);
}

function normalizeSearchKeyword(raw) {
  return (raw || "").trim();
}

function matchesKeyword(text, keyword) {
  if (!keyword) return true;
  return (text || "").includes(keyword);
}

function renderCatalog(keyword) {
  const rootEl = $("listRoot");
  rootEl.innerHTML = "";

  const tree = buildTree(state.catalogItems);
  const kw = normalizeSearchKeyword(keyword);

  function renderNode(node) {
    const container = document.createElement("div");

    const childrenEntries = Array.from(node.children.entries()).sort((a, b) =>
      a[0].localeCompare(b[0])
    );

    for (const [label, child] of childrenEntries) {
      const header = document.createElement("div");
      header.className = "node";
      header.textContent = label;
      container.appendChild(header);

      const sub = renderNode(child);
      if (sub.childElementCount > 0) {
        sub.classList.add("sub");
        container.appendChild(sub);
      }
    }

    const sortedItems = [...node.items].sort((a, b) => a.name.localeCompare(b.name));
    for (const item of sortedItems) {
      const nameMatch = matchesKeyword(item.name, kw);
      const techniqueMatch = matchesKeyword(item.technique, kw);
      const diffMatch = matchesKeyword(item.difficulty, kw);
      if (!nameMatch && !techniqueMatch && !diffMatch) continue;

      const el = document.createElement("div");
      el.className = "node";
      if (!item.launchable) el.classList.add("dim");
      el.textContent = item.name;
      el.onclick = () => {
        setSelected(item);
        clearActiveNode();
        el.classList.add("active");
        state.activeNodeEl = el;
      };
      container.appendChild(el);
    }

    return container;
  }

  const rendered = renderNode(tree);
  rootEl.appendChild(rendered);
}

function renderAll(keyword) {
  const rootEl = $("listRoot");
  rootEl.innerHTML = "";

  const kw = normalizeSearchKeyword(keyword);
  const names = state.allNames.filter((n) => matchesKeyword(n, kw));

  for (const name of names) {
    const el = document.createElement("div");
    el.className = "node";
    el.textContent = name;
    el.onclick = () => {
      setSelected({ name, difficulty: "", technique: "", launchable: true });
      clearActiveNode();
      el.classList.add("active");
      state.activeNodeEl = el;
    };
    rootEl.appendChild(el);
  }
}

function renderList() {
  const keyword = $("searchInput").value;
  if (state.tab === "catalog") {
    renderCatalog(keyword);
  } else {
    renderAll(keyword);
  }
}

function setTab(tab) {
  state.tab = tab;
  $("tabCatalog").classList.toggle("active", tab === "catalog");
  $("tabAll").classList.toggle("active", tab === "all");
  setSelected(null);
  renderList();
}

async function launchSelected() {
  const item = state.selected;
  if (!item || !item.launchable) return;
  $("launchBtn").disabled = true;
  try {
    const launchName = item.launchName || item.name;
    await fetchJson("/api/launch", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name: launchName }),
    });
    $("algoLaunchable").textContent = "是";
  } catch (e) {
    alert(`启动失败：${e.message}`);
  } finally {
    $("launchBtn").disabled = !(state.selected && state.selected.launchable);
  }
}

async function init() {
  $("tabCatalog").onclick = () => setTab("catalog");
  $("tabAll").onclick = () => setTab("all");
  $("launchBtn").onclick = () => launchSelected();
  $("clearBtn").onclick = () => {
    $("searchInput").value = "";
    renderList();
  };
  $("searchInput").addEventListener("input", () => renderList());

  try {
    const [health, catalog, all] = await Promise.all([
      fetchJson("/api/health"),
      fetchJson("/api/catalog"),
      fetchJson("/api/animations"),
    ]);
    setHealth(JSON.stringify(health, null, 2));
    state.catalogItems = catalog || [];
    state.allNames = all || [];

    renderList();
  } catch (e) {
    setHealth(`加载失败：${e.message}`);
  }
}

init();
