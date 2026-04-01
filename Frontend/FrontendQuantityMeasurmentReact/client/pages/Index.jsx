import { useState, useMemo, useEffect, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "@/context/AuthContext.jsx";
import { measurementApi, checkBackendHealth } from "../lib/api";

/**
 * MEASUREMENT_TYPES define the registry for all supported quantity categories.
 * Each category includes an icon, list of units, and conversion factors relative to a base unit.
 */
const MEASUREMENT_TYPES = {
  length: {
    id: "length",
    name: "Length",
    icon: "✏️",
    units: ["Millimeter", "Centimeter", "Meter", "Kilometer", "Mile", "Yard", "Foot", "Inch"],
    conversions: {
      "Millimeter": 1,
      "Centimeter": 10,
      "Meter": 1000,
      "Kilometer": 1000000,
      "Mile": 1609344,
      "Yard": 914.4,
      "Foot": 304.8,
      "Inch": 25.4,
    },
  },
  weight: {
    id: "weight",
    name: "Weight",
    icon: "⚖️",
    units: ["Milligram", "Gram", "Kilogram", "Pound", "Ounce", "Ton"],
    conversions: {
      "Milligram": 1,
      "Gram": 1000,
      "Kilogram": 1000000,
      "Pound": 453592.37,
      "Ounce": 28349.52,
      "Ton": 1000000000,
    },
  },
  temperature: {
    id: "temperature",
    name: "Temperature",
    icon: "🌡️",
    units: ["Celsius", "Fahrenheit", "Kelvin"],
    conversions: {},
  },
  volume: {
    id: "volume",
    name: "Volume",
    icon: "💧",
    units: ["Milliliter", "Liter", "Gallon", "Pint", "Cup"],
    conversions: {
      "Milliliter": 1,
      "Liter": 1000,
      "Gallon": 3785.41,
      "Pint": 473.176,
      "Cup": 236.588,
    },
  },
};

/**
 * TypeSelector component allows users to switch between Length, Weight, Temperature, and Volume.
 */
function TypeSelector({
  selectedType,
  onTypeChange,
}) {
  return (
    <div className="mb-8">
      <p className="text-xs font-semibold text-teal-600 mb-4 tracking-wide">
        CHOOSE TYPE
      </p>
      <div className="grid grid-cols-4 gap-3">
        {Object.keys(MEASUREMENT_TYPES).map((type) => (
          <button
            key={type}
            onClick={() => onTypeChange(type)}
            className={`
              flex flex-col items-center justify-center p-4 rounded-lg
              transition-all duration-300 transform hover:scale-105
              ${
                selectedType === type
                  ? "border-2 border-teal-500 bg-white shadow-md"
                  : "border-2 border-gray-200 bg-gray-50 hover:border-teal-300"
              }
            `}
          >
            <span className="text-3xl mb-2">
              {MEASUREMENT_TYPES[type].icon}
            </span>
            <span className="text-xs font-medium text-gray-700">
              {MEASUREMENT_TYPES[type].name}
            </span>
          </button>
        ))}
      </div>
    </div>
  );
}

function ActionSelector({
  selectedAction,
  onActionChange,
}) {
  const actions = [
    { id: "comparison", name: "Comparison" },
    { id: "conversion", name: "Conversion" },
    { id: "arithmetic", name: "Arithmetic" },
  ];

  return (
    <div className="mb-8">
      <p className="text-xs font-semibold text-teal-600 mb-4 tracking-wide">
        CHOOSE ACTION
      </p>
      <div className="flex gap-3">
        {actions.map((action) => (
          <button
            key={action.id}
            onClick={() => onActionChange(action.id)}
            className={`
              px-6 py-2 rounded-lg font-medium text-sm
              transition-all duration-300 transform hover:scale-105
              ${
                selectedAction === action.id
                  ? "bg-blue-600 text-white shadow-lg"
                  : "bg-gray-200 text-gray-700 hover:bg-gray-300"
              }
            `}
          >
            {action.name}
          </button>
        ))}
      </div>
    </div>
  );
}

/**
 * ConversionInterface handles the core logic for unit conversion, arithmetic, and comparison.
 * It attempts to use the backend API if available, falling back to local calculation if offline.
 * 
 * Uses `key` prop from parent to force re-mount when measurement type changes,
 * ensuring units are properly reset.
 */
function ConversionInterface({
  type,
  action,
  isOffline,
}) {
  const typeInfo = MEASUREMENT_TYPES[type];
  
  // State for input values and selected units
  const [fromValue, setFromValue] = useState("1");
  const [fromUnit, setFromUnit] = useState(typeInfo.units[0]);
  const [toUnit, setToUnit] = useState(
    typeInfo.units.length > 1 ? typeInfo.units[1] : typeInfo.units[0]
  );
  
  // State for arithmetic/comparison operations
  const [arithmeticOp, setArithmeticOp] = useState("add");
  const [secondValue, setSecondValue] = useState("1");
  const [secondUnit, setSecondUnit] = useState(
    typeInfo.units.length > 1 ? typeInfo.units[1] : typeInfo.units[0]
  );
  
  // State for backend-fetched results
  const [backendResult, setBackendResult] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  // Reset units when type changes
  useEffect(() => {
    const units = MEASUREMENT_TYPES[type].units;
    setFromUnit(units[0]);
    setToUnit(units.length > 1 ? units[1] : units[0]);
    setSecondUnit(units.length > 1 ? units[1] : units[0]);
    setFromValue("1");
    setSecondValue("1");
    setBackendResult(null);
  }, [type]);

  // Reset backend result when action changes
  useEffect(() => {
    setBackendResult(null);
  }, [action]);

  /**
   * Effect to fetch result from the backend when inputs change.
   */
  useEffect(() => {
    if (isOffline) {
      setBackendResult(null);
      return;
    }

    const fetchData = async () => {
      setIsLoading(true);
      try {
        const val1 = parseFloat(fromValue) || 0;
        const val2 = parseFloat(secondValue) || 0;
        let result;

        if (action === "conversion") {
          result = await measurementApi.convert(type, fromUnit, toUnit, val1);
        } else if (action === "arithmetic") {
          result = await measurementApi.calculate(type, arithmeticOp, val1, fromUnit, val2, secondUnit, toUnit);
        } else if (action === "comparison") {
          result = await measurementApi.compare(type, val1, fromUnit, val2, secondUnit);
        }

        // result can be a number, boolean, or string — normalize to string
        setBackendResult(String(result));
      } catch (err) {
        console.warn(`Backend ${action} failed, falling back to local math:`, err);
        setBackendResult(null);
      } finally {
        setIsLoading(false);
      }
    };

    const timeoutId = setTimeout(fetchData, 300); // Debounce API calls
    return () => clearTimeout(timeoutId);
  }, [fromValue, fromUnit, toUnit, secondValue, secondUnit, type, action, arithmeticOp, isOffline]);

  /**
   * Local temperature conversion helpers
   */
  const toCelsius = useCallback((val, u) => {
    if (u === "Celsius") return val;
    if (u === "Fahrenheit") return (val - 32) * (5 / 9);
    if (u === "Kelvin") return val - 273.15;
    return val;
  }, []);

  const fromCelsius = useCallback((val, u) => {
    if (u === "Celsius") return val;
    if (u === "Fahrenheit") return val * (9 / 5) + 32;
    if (u === "Kelvin") return val + 273.15;
    return val;
  }, []);

  /**
   * toValue calculates the result. It uses the backendResult if available,
   * otherwise it falls back to local calculation logic.
   */
  const toValue = useMemo(() => {
    // If we have a fresh result from the backend, use it
    if (backendResult !== null) return backendResult;

    const fromNum = parseFloat(fromValue) || 0;
    const secondNum = parseFloat(secondValue) || 0;

    // --- Comparison (local fallback) ---
    if (action === "comparison") {
      let val1InBase, val2InBase;
      if (type === "temperature") {
        val1InBase = toCelsius(fromNum, fromUnit);
        val2InBase = toCelsius(secondNum, secondUnit);
      } else {
        val1InBase = fromNum * (typeInfo.conversions[fromUnit] || 1);
        val2InBase = secondNum * (typeInfo.conversions[secondUnit] || 1);
      }
      return Math.abs(val1InBase - val2InBase) < 0.0001 ? "true" : "false";
    }

    // --- Arithmetic (local fallback) ---
    if (action === "arithmetic") {
      let val1InBase;
      let val2InBase;

      if (type !== "temperature") {
        val1InBase = fromNum * (typeInfo.conversions[fromUnit] || 1);
        // For multiply/divide, second value is a scalar
        if (arithmeticOp === "multiply" || arithmeticOp === "divide") {
          val2InBase = secondNum;
        } else {
          val2InBase = secondNum * (typeInfo.conversions[secondUnit] || 1);
        }
      } else {
        val1InBase = toCelsius(fromNum, fromUnit);
        if (arithmeticOp === "multiply" || arithmeticOp === "divide") {
          val2InBase = secondNum;
        } else {
          val2InBase = toCelsius(secondNum, secondUnit);
        }
      }

      let resultInBase = 0;
      switch (arithmeticOp) {
        case "add":
          resultInBase = val1InBase + val2InBase;
          break;
        case "subtract":
          resultInBase = val1InBase - val2InBase;
          break;
        case "multiply":
          resultInBase = val1InBase * val2InBase;
          break;
        case "divide":
          resultInBase = val2InBase !== 0 ? val1InBase / val2InBase : 0;
          break;
      }

      if (type !== "temperature") {
        const result = resultInBase / (typeInfo.conversions[toUnit] || 1);
        return result.toFixed(4).replace(/\.?0+$/, "");
      } else {
        const result = fromCelsius(resultInBase, toUnit);
        return result.toFixed(2);
      }
    }

    // --- Conversion (local fallback) ---
    if (type === "temperature") {
      const celsius = toCelsius(fromNum, fromUnit);
      const result = fromCelsius(celsius, toUnit);
      return result.toFixed(2);
    }

    const fromInBase = fromNum * (typeInfo.conversions[fromUnit] || 1);
    const result = fromInBase / (typeInfo.conversions[toUnit] || 1);
    return result.toFixed(4).replace(/\.?0+$/, "");
  }, [fromValue, fromUnit, toUnit, type, action, arithmeticOp, secondValue, secondUnit, typeInfo, backendResult, toCelsius, fromCelsius]);

  /**
   * For comparison mode, determine the match result
   */
  const comparisonResult = useMemo(() => {
    if (action !== "comparison") return null;
    // Both backendResult and local fallback return "true"/"false" as strings
    return toValue === "true";
  }, [action, toValue]);

  return (
    <div className="animate-slide-up">
      {action === "arithmetic" && (
        <div className="mb-6 p-4 bg-blue-50 rounded-lg border border-blue-200">
          <p className="text-xs font-semibold text-blue-700 mb-3 tracking-wide">
            ARITHMETIC OPERATION
          </p>
          <div className="flex gap-2">
            {["add", "subtract", "multiply", "divide"].map((op) => (
              <button
                key={op}
                onClick={() => setArithmeticOp(op)}
                className={`
                  px-4 py-2 rounded font-medium text-sm transition-all duration-300
                  ${
                    arithmeticOp === op
                      ? "bg-blue-600 text-white shadow-md"
                      : "bg-white text-gray-700 border border-gray-300 hover:border-blue-600"
                  }
                `}
              >
                {op === "add"
                  ? "+"
                  : op === "subtract"
                    ? "−"
                    : op === "multiply"
                      ? "×"
                      : "÷"}
              </button>
            ))}
          </div>
        </div>
      )}

      <div className="grid grid-cols-2 gap-8">
        {/* FROM / FIRST VALUE Section */}
        <div>
          <p className="text-xs font-semibold text-gray-700 mb-4">
            {action === "conversion" ? "FROM" : "FIRST VALUE"}
          </p>
          <div className="flex flex-col gap-3">
            <input
              type="number"
              value={fromValue}
              onChange={(e) => setFromValue(e.target.value)}
              className="
                text-3xl font-bold text-gray-900 bg-transparent
                border-b-2 border-gray-200 pb-2
                focus:border-blue-600 focus:outline-none
                transition-colors duration-200
              "
              placeholder="0"
            />
            <select
              value={fromUnit}
              onChange={(e) => setFromUnit(e.target.value)}
              className="
                text-sm text-gray-600 bg-transparent
                border border-gray-200 rounded px-2 py-1
                focus:border-blue-600 focus:outline-none
                transition-colors duration-200
              "
            >
              {typeInfo.units.map((unit) => (
                <option key={unit} value={unit}>
                  {unit}
                </option>
              ))}
            </select>
          </div>
        </div>

        {/* TO / SECOND VALUE Section */}
        <div>
          <p className="text-xs font-semibold text-gray-700 mb-4">
            {action === "conversion" ? "TO" : "SECOND VALUE"}
          </p>
          <div className="flex flex-col gap-3">
            {action === "conversion" ? (
              /* Conversion: show result + unit selector */
              <>
                <p className={`text-3xl font-bold text-gray-900 ${isLoading ? 'opacity-50 blur-[2px]' : ''}`}>
                  {toValue}
                </p>
                <select
                  value={toUnit}
                  onChange={(e) => setToUnit(e.target.value)}
                  className="
                    text-sm text-gray-600 bg-transparent
                    border border-gray-200 rounded px-2 py-1
                    focus:border-blue-600 focus:outline-none
                    transition-colors duration-200
                  "
                >
                  {typeInfo.units.map((unit) => (
                    <option key={unit} value={unit}>
                      {unit}
                    </option>
                  ))}
                </select>
              </>
            ) : (
              /* Comparison & Arithmetic: show 2nd value input + 2nd unit selector */
              <>
                <input
                  type="number"
                  value={secondValue}
                  onChange={(e) => setSecondValue(e.target.value)}
                  className="
                    text-3xl font-bold text-gray-900 bg-transparent
                    border-b-2 border-gray-200 pb-2
                    focus:border-blue-600 focus:outline-none
                    transition-colors duration-200
                  "
                  placeholder="0"
                />
                <select
                  value={secondUnit}
                  onChange={(e) => setSecondUnit(e.target.value)}
                  disabled={action === "arithmetic" && (arithmeticOp === "multiply" || arithmeticOp === "divide")}
                  className="
                    text-sm text-gray-600 bg-transparent
                    border border-gray-200 rounded px-2 py-1
                    focus:border-blue-600 focus:outline-none
                    transition-colors duration-200
                    disabled:opacity-50 disabled:bg-gray-100
                  "
                >
                  {typeInfo.units.map((unit) => (
                    <option key={unit} value={unit}>
                      {unit}
                    </option>
                  ))}
                </select>
              </>
            )}
          </div>
        </div>
      </div>

      {/* Arithmetic Result Section */}
      {action === "arithmetic" && (
        <div className="mt-8 p-6 bg-green-50 rounded-lg border border-green-200 shadow-sm">
          <div className="flex justify-between items-end mb-4">
            <div>
              <p className="text-xs font-semibold text-green-700 mb-1">RESULT</p>
              <p className={`text-4xl font-bold text-green-900 ${isLoading ? 'opacity-50 blur-[2px]' : ''}`}>{toValue}</p>
            </div>
            <div className="flex flex-col items-end">
              <p className="text-xs font-semibold text-green-700 mb-2">RESULT UNIT</p>
              <select
                value={toUnit}
                onChange={(e) => setToUnit(e.target.value)}
                className="
                  text-sm font-medium text-green-800 bg-white
                  border border-green-200 rounded px-3 py-1.5
                  focus:border-green-500 focus:outline-none
                  transition-all duration-200 shadow-sm
                "
              >
                {typeInfo.units.map((unit) => (
                  <option key={unit} value={unit}>
                    {unit}
                  </option>
                ))}
              </select>
            </div>
          </div>
          <p className="text-xs text-green-600 italic">
            Computed as: {fromValue} {fromUnit} {
              arithmeticOp === "add" ? "+" :
              arithmeticOp === "subtract" ? "-" :
              arithmeticOp === "multiply" ? "x" : "/"
            } {secondValue} { (arithmeticOp === "add" || arithmeticOp === "subtract") ? secondUnit : "" } → {toUnit}
          </p>
        </div>
      )}

      {/* Comparison Result Section */}
      {action === "comparison" && (
        <div className="mt-8 p-6 bg-blue-50 rounded-lg border border-blue-200 shadow-sm animate-slide-up">
          <p className="text-xs font-semibold text-blue-700 mb-3 tracking-wide">COMPARISON RESULT</p>
          <div className="flex items-center gap-4">
            <div className={`
              px-6 py-3 rounded-xl font-bold text-lg
              ${isLoading ? 'opacity-50 blur-[2px]' : ''}
              ${comparisonResult ? "bg-green-100 text-green-700 border border-green-200" : "bg-red-100 text-red-700 border border-red-200"}
            `}>
              {comparisonResult ? "MATCH! ✅" : "DOES NOT MATCH ❌"}
            </div>
            <p className="text-sm text-blue-600 italic">
              Comparing {fromValue} {fromUnit} with {secondValue} {secondUnit}
            </p>
          </div>
        </div>
      )}
    </div>
  );
}

export default function Index() {
  const navigate = useNavigate();
  const { user, logout } = useAuth();
  const [selectedType, setSelectedType] = useState("length");
  const [selectedAction, setSelectedAction] = useState("comparison");

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  /**
   * Health check logic to monitor backend availability.
   */
  const [isOffline, setIsOffline] = useState(false);

  useEffect(() => {
    const checkConnection = async () => {
      const isReachable = await checkBackendHealth();
      setIsOffline(!isReachable);
    };

    checkConnection();
    const interval = setInterval(checkConnection, 10000); // Check every 10 seconds
    return () => clearInterval(interval);
  }, []);

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header with Offline Indicator */}
      <div className="bg-blue-600 text-white py-6 shadow-lg">
        <div className="max-w-2xl mx-auto px-6 flex items-center justify-between">
          <div className="flex flex-col">
            <h1 className="text-xl font-bold tracking-tight">
              Welcome To Quantity Measurement
            </h1>
            {isOffline && (
              <span className="text-[10px] font-bold bg-white text-red-600 px-2 py-0.5 rounded-full mt-1 w-max animate-pulse">
                OFFLINE MODE
              </span>
            )}
          </div>
          <div className="flex items-center gap-4">
            <span className="text-sm opacity-90">
              {user?.name}
            </span>
            <button
              onClick={handleLogout}
              className="
                px-4 py-2 bg-white bg-opacity-20 rounded-lg
                hover:bg-opacity-30 transition-all duration-300
                text-sm font-medium
              "
            >
              Logout
            </button>
          </div>
        </div>
      </div>

      {/* Main Content */}
      <div className="max-w-2xl mx-auto px-6 py-12">
        <TypeSelector
          selectedType={selectedType}
          onTypeChange={setSelectedType}
        />

        <ActionSelector
          selectedAction={selectedAction}
          onActionChange={setSelectedAction}
        />

        {/* key forces re-mount when type or action changes, resetting all internal state */}
        <ConversionInterface 
          key={`${selectedType}-${selectedAction}`}
          type={selectedType} 
          action={selectedAction} 
          isOffline={isOffline}
        />
      </div>
    </div>
  );
}
