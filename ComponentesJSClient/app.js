const BASE = 'http://localhost:8080';

// ── Utilidades ──────────────────────────────────────────────────
function showAbout() { document.getElementById('about-modal').classList.add('open'); }
function hideAbout() { document.getElementById('about-modal').classList.remove('open'); }

function setMsg(id, text, ok) {
  const el = document.getElementById(id);
  el.textContent = text;
  el.className = 'msg ' + (ok ? 'ok' : 'err');
}

async function api(method, path, body) {
  const opts = { method, headers: { 'Content-Type': 'application/json' }, cache: 'no-store' };
  if (body) opts.body = JSON.stringify(body);
  const url = method === 'GET' ? `${BASE}${path}${path.includes('?') ? '&' : '?'}_=${Date.now()}` : BASE + path;
  const res = await fetch(url, opts);
  if (res.status === 404) return null;
  if (!res.ok) throw new Error(await res.text());
  return res.json();
}

function formatComponent(c, type) {
  if (!c) return 'Componente no encontrado.';
  let s = `ID:              ${c.id}\nMarca:           ${c.brand}\nEncapsulado:     ${c.packageType}\nVoltaje:         ${c.voltage} V\nCorriente:       ${c.current} A\nNum. Pines:      ${c.pinCount}\n`;
  if (type === 'passive')
    s += `Tolerancia:      \u00b1${c.tolerance}%\nMagnitud:        ${c.nominalMagnitude} ${c.nominalUnit}\n`;
  else
    s += `Factor Ganancia: ${c.gainFactor}\nPines:           ${(c.pinNames || []).join(', ')}\n`;
  s += `Fecha Registro:  ${c.registrationDate || 'N/A'}`;
  return s;
}

// ── Observer (cliente JS) ────────────────────────────────────────
class ListObserver {
  constructor(statusId) { this.el = document.getElementById(statusId); }
  onDataLoaded(count, filtered) {
    this.el.textContent = (filtered ? 'Filtro aplicado' : 'Todos los registros')
      + `: ${count} componente(s) encontrado(s).`;
  }
}
const passiveListObserver = new ListObserver('pl-status');
const activeListObserver  = new ListObserver('al-status');

// ── PASSIVE INSERT ───────────────────────────────────────────────
async function insertPassive(e) {
  e.preventDefault();
  try {
    await api('POST', '/api/passive', {
      id: +document.getElementById('pi-id').value,
      brand: document.getElementById('pi-brand').value.trim(),
      packageType: document.getElementById('pi-pkg').value.trim(),
      voltage: +document.getElementById('pi-voltage').value,
      current: +document.getElementById('pi-current').value,
      pinCount: +document.getElementById('pi-pins').value,
      tolerance: +document.getElementById('pi-tolerance').value,
      nominalMagnitude: +document.getElementById('pi-magnitude').value,
      nominalUnit: document.getElementById('pi-unit').value.trim()
    });
    setMsg('pi-msg', 'Componente pasivo insertado correctamente.', true);
    e.target.reset();
  } catch(err) { setMsg('pi-msg', err.message, false); }
}

// ── PASSIVE SEARCH ───────────────────────────────────────────────
async function searchPassive() {
  const id = document.getElementById('ps-id').value.trim();
  const brand = document.getElementById('ps-brand').value.trim();
  const q = id ? `?id=${id}` : brand ? `?brand=${encodeURIComponent(brand)}` : '';
  try {
    const c = await api('GET', '/api/passive/search' + q);
    document.getElementById('ps-result').textContent = formatComponent(c, 'passive');
  } catch(err) { document.getElementById('ps-result').textContent = err.message; }
}

// ── PASSIVE UPDATE ───────────────────────────────────────────────
let _passiveCurrent = null;
async function loadPassiveForUpdate() {
  const id = document.getElementById('pu-id').value;
  try {
    _passiveCurrent = await api('GET', `/api/passive/${id}`);
    if (!_passiveCurrent) { setMsg('pu-msg', 'No encontrado.', false); return; }
    const c = _passiveCurrent;
    document.getElementById('pu-brand').value     = c.brand;
    document.getElementById('pu-pkg').value       = c.packageType;
    document.getElementById('pu-voltage').value   = c.voltage;
    document.getElementById('pu-current').value   = c.current;
    document.getElementById('pu-pins').value      = c.pinCount;
    document.getElementById('pu-tolerance').value = c.tolerance;
    document.getElementById('pu-magnitude').value = c.nominalMagnitude;
    document.getElementById('pu-unit').value      = c.nominalUnit;
    document.getElementById('pu-form').style.display = 'flex';
    setMsg('pu-msg', '', true);
  } catch(err) { setMsg('pu-msg', err.message, false); }
}
async function updatePassive(e) {
  e.preventDefault();
  try {
    await api('PUT', `/api/passive/${_passiveCurrent.id}`, {
      ..._passiveCurrent,
      brand: document.getElementById('pu-brand').value.trim(),
      packageType: document.getElementById('pu-pkg').value.trim(),
      voltage: +document.getElementById('pu-voltage').value,
      current: +document.getElementById('pu-current').value,
      pinCount: +document.getElementById('pu-pins').value,
      tolerance: +document.getElementById('pu-tolerance').value,
      nominalMagnitude: +document.getElementById('pu-magnitude').value,
      nominalUnit: document.getElementById('pu-unit').value.trim()
    });
    setMsg('pu-msg', 'Componente actualizado correctamente.', true);
    document.getElementById('pu-form').style.display = 'none';
  } catch(err) { setMsg('pu-msg', err.message, false); }
}

// ── PASSIVE DELETE ───────────────────────────────────────────────
let _passiveToDelete = null;
async function loadPassiveForDelete() {
  const id = document.getElementById('pd-id').value;
  try {
    _passiveToDelete = await api('GET', `/api/passive/${id}`);
    const res = document.getElementById('pd-result');
    const btn = document.getElementById('pd-btn');
    if (!_passiveToDelete) {
      res.textContent = 'Componente no encontrado.';
      btn.style.display = 'none';
    } else {
      res.textContent = formatComponent(_passiveToDelete, 'passive');
      btn.style.display = 'inline-block';
    }
    setMsg('pd-msg', '', true);
  } catch(err) { setMsg('pd-msg', err.message, false); }
}
async function deletePassive() {
  if (!_passiveToDelete) return;
  if (!confirm(`\u00bfEliminar componente ID ${_passiveToDelete.id} (${_passiveToDelete.brand})?`)) return;
  try {
    await api('DELETE', `/api/passive/${_passiveToDelete.id}`);
    setMsg('pd-msg', 'Componente eliminado correctamente.', true);
    document.getElementById('pd-result').textContent = '';
    document.getElementById('pd-btn').style.display = 'none';
    _passiveToDelete = null;
  } catch(err) { setMsg('pd-msg', err.message, false); }
}

// ── PASSIVE LIST ─────────────────────────────────────────────────
let _passiveListInterval = null;

async function listPassive() {
  const brand = document.getElementById('pl-brand').value.trim();
  const pkg   = document.getElementById('pl-pkg').value.trim();
  const q = [brand && `brand=${encodeURIComponent(brand)}`, pkg && `packageType=${encodeURIComponent(pkg)}`]
    .filter(Boolean).join('&');
  try {
    const data = await api('GET', '/api/passive' + (q ? '?' + q : ''));
    document.getElementById('pl-body').innerHTML = data.map(c => `<tr>
      <td>${c.id}</td><td>${c.brand}</td><td>${c.packageType}</td>
      <td>${c.voltage}</td><td>${c.current}</td><td>${c.pinCount}</td>
      <td>\u00b1${c.tolerance}%</td><td>${c.nominalMagnitude}</td><td>${c.nominalUnit}</td>
      <td>${c.registrationDate || 'N/A'}</td>
    </tr>`).join('');
    passiveListObserver.onDataLoaded(data.length, !!(brand || pkg));
  } catch(err) { alert(err.message); }
}
function clearAndListPassive() {
  document.getElementById('pl-brand').value = '';
  document.getElementById('pl-pkg').value   = '';
  listPassive();
}

// ── ACTIVE INSERT ────────────────────────────────────────────────
async function insertActive(e) {
  e.preventDefault();
  try {
    await api('POST', '/api/active', {
      id: +document.getElementById('ai-id').value,
      brand: document.getElementById('ai-brand').value.trim(),
      packageType: document.getElementById('ai-pkg').value.trim(),
      voltage: +document.getElementById('ai-voltage').value,
      current: +document.getElementById('ai-current').value,
      gainFactor: +document.getElementById('ai-gain').value,
      pinNames: document.getElementById('ai-pins').value.split(',').map(p => p.trim())
    });
    setMsg('ai-msg', 'Componente activo insertado correctamente.', true);
    e.target.reset();
  } catch(err) { setMsg('ai-msg', err.message, false); }
}

// ── ACTIVE SEARCH ────────────────────────────────────────────────
async function searchActive() {
  const id = document.getElementById('as-id').value.trim();
  const brand = document.getElementById('as-brand').value.trim();
  const q = id ? `?id=${id}` : brand ? `?brand=${encodeURIComponent(brand)}` : '';
  try {
    const c = await api('GET', '/api/active/search' + q);
    document.getElementById('as-result').textContent = formatComponent(c, 'active');
  } catch(err) { document.getElementById('as-result').textContent = err.message; }
}

// ── ACTIVE UPDATE ────────────────────────────────────────────────
let _activeCurrent = null;
async function loadActiveForUpdate() {
  const id = document.getElementById('au-id').value;
  try {
    _activeCurrent = await api('GET', `/api/active/${id}`);
    if (!_activeCurrent) { setMsg('au-msg', 'No encontrado.', false); return; }
    const c = _activeCurrent;
    document.getElementById('au-brand').value   = c.brand;
    document.getElementById('au-pkg').value     = c.packageType;
    document.getElementById('au-voltage').value = c.voltage;
    document.getElementById('au-current').value = c.current;
    document.getElementById('au-gain').value    = c.gainFactor;
    document.getElementById('au-pins').value    = (c.pinNames || []).join(',');
    document.getElementById('au-form').style.display = 'flex';
    setMsg('au-msg', '', true);
  } catch(err) { setMsg('au-msg', err.message, false); }
}
async function updateActive(e) {
  e.preventDefault();
  try {
    await api('PUT', `/api/active/${_activeCurrent.id}`, {
      ..._activeCurrent,
      brand: document.getElementById('au-brand').value.trim(),
      packageType: document.getElementById('au-pkg').value.trim(),
      voltage: +document.getElementById('au-voltage').value,
      current: +document.getElementById('au-current').value,
      gainFactor: +document.getElementById('au-gain').value,
      pinNames: document.getElementById('au-pins').value.split(',').map(p => p.trim())
    });
    setMsg('au-msg', 'Componente actualizado correctamente.', true);
    document.getElementById('au-form').style.display = 'none';
  } catch(err) { setMsg('au-msg', err.message, false); }
}

// ── ACTIVE DELETE ────────────────────────────────────────────────
let _activeToDelete = null;
async function loadActiveForDelete() {
  const id = document.getElementById('ad-id').value;
  try {
    _activeToDelete = await api('GET', `/api/active/${id}`);
    const res = document.getElementById('ad-result');
    const btn = document.getElementById('ad-btn');
    if (!_activeToDelete) {
      res.textContent = 'Componente no encontrado.';
      btn.style.display = 'none';
    } else {
      res.textContent = formatComponent(_activeToDelete, 'active');
      btn.style.display = 'inline-block';
    }
    setMsg('ad-msg', '', true);
  } catch(err) { setMsg('ad-msg', err.message, false); }
}
async function deleteActive() {
  if (!_activeToDelete) return;
  if (!confirm(`\u00bfEliminar componente ID ${_activeToDelete.id} (${_activeToDelete.brand})?`)) return;
  try {
    await api('DELETE', `/api/active/${_activeToDelete.id}`);
    setMsg('ad-msg', 'Componente eliminado correctamente.', true);
    document.getElementById('ad-result').textContent = '';
    document.getElementById('ad-btn').style.display = 'none';
    _activeToDelete = null;
  } catch(err) { setMsg('ad-msg', err.message, false); }
}

// ── ACTIVE LIST ──────────────────────────────────────────────────
let _activeListInterval = null;

async function listActive() {
  const brand = document.getElementById('al-brand').value.trim();
  const pkg   = document.getElementById('al-pkg').value.trim();
  const q = [brand && `brand=${encodeURIComponent(brand)}`, pkg && `packageType=${encodeURIComponent(pkg)}`]
    .filter(Boolean).join('&');
  try {
    const data = await api('GET', '/api/active' + (q ? '?' + q : ''));
    document.getElementById('al-body').innerHTML = data.map(c => `<tr>
      <td>${c.id}</td><td>${c.brand}</td><td>${c.packageType}</td>
      <td>${c.voltage}</td><td>${c.current}</td><td>${c.pinCount}</td>
      <td>${c.gainFactor}</td><td>${(c.pinNames || []).join(', ')}</td>
      <td>${c.registrationDate || 'N/A'}</td>
    </tr>`).join('');
    activeListObserver.onDataLoaded(data.length, !!(brand || pkg));
  } catch(err) { alert(err.message); }
}
function clearAndListActive() {
  document.getElementById('al-brand').value = '';
  document.getElementById('al-pkg').value   = '';
  listActive();
}

// ── Refrescar Automatico al mostrar secciones de Listar ──────────────────
function showSection(id) {
  document.querySelectorAll('.section').forEach(s => s.classList.remove('active'));
  document.getElementById(id).classList.add('active');
  clearInterval(_passiveListInterval);
  clearInterval(_activeListInterval);
  if (id === 'passive-list') {
    listPassive();
    _passiveListInterval = setInterval(listPassive, 3000);
  }
  if (id === 'active-list') {
    listActive();
    _activeListInterval = setInterval(listActive, 3000);
  }
}
