// ── Backend API Base URL ──
const API_BASE = 'http://localhost:8080/api/quantity';

// ── Unit Definitions ──
const UNITS = {
  length:      ['Millimeter', 'Centimeter', 'Meter', 'Kilometer', 'Inch', 'Foot', 'Yard', 'Mile'],
  weight:      ['Milligram', 'Gram', 'Kilogram', 'Tonne', 'Ounce', 'Pound', 'Stone'],
  temperature: ['Celsius', 'Fahrenheit', 'Kelvin'],
  volume:      ['Milliliter', 'Liter', 'Cubic Meter', 'Teaspoon', 'Tablespoon', 'Cup', 'Pint', 'Gallon'],
};

// ── Conversion Factors to Base Unit (meter / gram / liter) ──
const TO_BASE = {
  length: {
    Millimeter: 0.001, Centimeter: 0.01, Meter: 1, Kilometer: 1000,
    Inch: 0.0254, Foot: 0.3048, Yard: 0.9144, Mile: 1609.344,
  },
  weight: {
    Milligram: 0.000001, Gram: 0.001, Kilogram: 1, Tonne: 1000,
    Ounce: 0.0283495, Pound: 0.453592, Stone: 6.35029,
  },
  volume: {
    Milliliter: 0.001, Liter: 1, 'Cubic Meter': 1000,
    Teaspoon: 0.00492892, Tablespoon: 0.0147868,
    Cup: 0.236588, Pint: 0.473176, Gallon: 3.78541,
  },
};

// ── App State ──
let state = { type: 'length', action: 'comparison', op: 'add' };

// ── DOM References ──
const fromValEl    = document.getElementById('fromVal');
const fromUnitEl   = document.getElementById('fromUnit');
const toUnitEl     = document.getElementById('toUnit');
const resultEl     = document.getElementById('resultBanner');
const toLabelEl    = document.getElementById('toLabel');
const arithSection = document.getElementById('arithSection');

// ── Temperature Conversion ──
function convertTemp(val, from, to) {
  let celsius;
  if (from === 'Celsius')     celsius = val;
  else if (from === 'Fahrenheit') celsius = (val - 32) * 5 / 9;
  else                         celsius = val - 273.15;

  if (to === 'Celsius')     return celsius;
  if (to === 'Fahrenheit')  return celsius * 9 / 5 + 32;
  return celsius + 273.15;
}

// ── General Unit Conversion ──
function convertVal(val, from, to, type) {
  if (type === 'temperature') return convertTemp(val, from, to);
  const base = val * TO_BASE[type][from];
  return base / TO_BASE[type][to];
}

// ── Number Formatter ──
function fmt(n) {
  if (isNaN(n)) return '—';
  if (Math.abs(n) >= 1e9 || (Math.abs(n) < 0.0001 && n !== 0)) return n.toExponential(4);
  return parseFloat(n.toPrecision(7)).toString();
}

// ── Populate Unit Dropdowns ──
function populateUnits() {
  const units = UNITS[state.type];
  [fromUnitEl, toUnitEl].forEach((sel, i) => {
    const prev = sel.value;
    sel.innerHTML = units.map(u => `<option value="${u}">${u}</option>`).join('');
    sel.value = units.includes(prev) ? prev : units[i === 0 ? 0 : 1];
  });
}

// ── Backend API Helpers ──

/**
 * Build the QuantityInputDTO payload expected by the backend.
 */
function buildPayload(val1, unit1, val2, unit2, type) {
  return {
    thisQuantityDTO: { value: val1, unit: unit1, measurementType: type },
    thatQuantityDTO: { value: val2, unit: unit2, measurementType: type },
  };
}

/**
 * POST to a backend endpoint. Returns parsed JSON or throws on non-OK.
 */
async function postToBackend(endpoint, payload) {
  const res = await fetch(`${API_BASE}/${endpoint}`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  });
  if (!res.ok) throw new Error(`HTTP ${res.status}`);
  return res.json();
}

// ── Local Fallback for Comparison ──
function localCompare(val, from, bVal, to, type) {
  const aConv = type === 'temperature'
    ? convertTemp(val, from, 'Celsius')
    : val * (TO_BASE[type]?.[from] ?? 1);
  const bConv = type === 'temperature'
    ? convertTemp(bVal, to, 'Celsius')
    : bVal * (TO_BASE[type]?.[to] ?? 1);
  const rel = aConv > bConv ? 'greater than' : aConv < bConv ? 'less than' : 'equal to';
  return `${fmt(val)} ${from} is ${rel} ${fmt(bVal)} ${to}`;
}

// ── Update Result Banner ──
async function updateResult() {
  const val    = parseFloat(fromValEl.value);
  const from   = fromUnitEl.value;
  const to     = toUnitEl.value;
  const toValEl = document.getElementById('toVal');
  const action = state.action;

  if (action === 'conversion') {
    // ── Local conversion logic (no backend endpoint) ──
    const result = convertVal(val, from, to, state.type);
    toValEl.textContent = fmt(result);
    resultEl.textContent = `${fmt(val)} ${from} = ${fmt(result)} ${to}`;

  } else if (action === 'comparison') {
    const bVal = parseFloat(toValEl.getAttribute('data-raw') || 0);

    // Show loading state
    resultEl.textContent = '⏳ Comparing via backend…';
    try {
      const payload = buildPayload(val, from, bVal, to, state.type);
      const data = await postToBackend('compare', payload);
      resultEl.textContent = data.resultString;
    } catch (err) {
      // Fallback to local calculation if backend is unreachable
      console.warn('Backend unreachable, using local comparison:', err);
      resultEl.textContent = localCompare(val, from, bVal, to, state.type) + ' (offline)';
    }

  } else {
    // ── Arithmetic ──
    const bVal       = parseFloat(toValEl.getAttribute('data-raw') || 0);
    const opSymbol   = { add: '+', sub: '−', mul: '×', div: '÷' }[state.op];

    if (state.op === 'add') {
      // Backend-powered addition
      resultEl.textContent = '⏳ Adding via backend…';
      try {
        const payload = buildPayload(val, from, bVal, to, state.type);
        const data = await postToBackend('add', payload);
        resultEl.textContent =
          `${fmt(val)} ${from} ${opSymbol} ${fmt(bVal)} ${to} = ${fmt(data.resultValue)} ${data.resultUnit}`;
      } catch (err) {
        // Fallback
        console.warn('Backend unreachable, using local add:', err);
        const bConverted = convertVal(bVal, to, from, state.type);
        const result = val + bConverted;
        resultEl.textContent = `${fmt(val)} ${from} ${opSymbol} ${fmt(bVal)} ${to} = ${fmt(result)} ${from} (offline)`;
      }
    } else {
      // Sub / Mul / Div — local JS (no backend endpoint)
      const bConverted = convertVal(bVal, to, from, state.type);
      let result;
      if      (state.op === 'sub') result = val - bConverted;
      else if (state.op === 'mul') result = val * bVal;
      else                          result = bVal !== 0 ? val / bVal : Infinity;
      resultEl.textContent = `${fmt(val)} ${from} ${opSymbol} ${fmt(bVal)} ${to} = ${fmt(result)} ${from}`;
    }
  }
}

// ── Switch Between Editable / Display-only toVal ──
function makeToValEditable() {
  const existing = document.getElementById('toVal');
  if (existing.tagName === 'INPUT') return; // already editable

  const inp = document.createElement('input');
  inp.className = 'value-box';
  inp.id        = 'toVal';
  inp.type      = 'number';
  inp.value     = '1000';
  inp.setAttribute('data-raw', '1000');
  existing.replaceWith(inp);

  inp.addEventListener('input', () => {
    inp.setAttribute('data-raw', inp.value);
    updateResult();
  });
}

function makeToValDisplay() {
  const existing = document.getElementById('toVal');
  if (existing.tagName === 'DIV') return; // already display

  const div = document.createElement('div');
  div.className = 'value-box';
  div.id        = 'toVal';
  div.textContent = '—';
  existing.replaceWith(div);
}

// ── Set Active Action ──
function setAction(action) {
  state.action = action;
  document.querySelectorAll('.tab').forEach(t =>
    t.classList.toggle('active', t.dataset.action === action)
  );

  if (action === 'conversion') {
    makeToValDisplay();
    toLabelEl.textContent = 'To';
    arithSection.style.display = 'none';
  } else if (action === 'comparison') {
    makeToValEditable();
    toLabelEl.textContent = 'To';
    arithSection.style.display = 'none';
  } else {
    makeToValEditable();
    toLabelEl.textContent = 'Value 2';
    arithSection.style.display = 'block';
  }

  updateResult();
}

// ── Event Listeners ──

// Type cards
document.querySelectorAll('.type-card').forEach(card => {
  card.addEventListener('click', () => {
    document.querySelectorAll('.type-card').forEach(c => c.classList.remove('active'));
    card.classList.add('active');
    state.type = card.dataset.type;
    populateUnits();
    updateResult();
  });
});

// Action tabs
document.querySelectorAll('.tab').forEach(tab => {
  tab.addEventListener('click', () => setAction(tab.dataset.action));
});

// Arithmetic operator buttons
document.querySelectorAll('.op-btn').forEach(btn => {
  btn.addEventListener('click', () => {
    document.querySelectorAll('.op-btn').forEach(b => b.classList.remove('active'));
    btn.classList.add('active');
    state.op = btn.dataset.op;
    updateResult();
  });
});

// From value & unit changes
fromValEl.addEventListener('input', updateResult);
fromUnitEl.addEventListener('change', updateResult);
toUnitEl.addEventListener('change', updateResult);

// ── Initialise ──
(function init() {
  makeToValEditable();
  populateUnits();
  fromUnitEl.value = 'Kilometer';
  toUnitEl.value   = 'Meter';
  updateResult();
})();
