/**
 * API utility for communicating with the microservice gateway.
 * The gateway acts as the entry point for auth-service, user-service, and measurement-service.
 */

const API_BASE_URL = "http://localhost:8080";

/**
 * Checks if the backend gateway is reachable.
 * Useful for the "Offline" status indicator in the UI.
 */
export async function checkBackendHealth() {
  try {
    const response = await fetch(`${API_BASE_URL}/actuator/health`, { method: 'GET', signal: AbortSignal.timeout(1000) });
    return response.ok;
  } catch (error) {
    return false;
  }
}

/**
 * Generic fetch wrapper for API calls.
 * Handles both JSON and plain-text responses from Spring Boot controllers.
 */
async function apiFetch(endpoint, options = {}) {
  const url = `${API_BASE_URL}${endpoint}`;
  try {
    const response = await fetch(url, {
      ...options,
      headers: {
        'Content-Type': 'application/json',
        ...options.headers,
      },
    });

    if (!response.ok) {
      // Try to parse the backend JSON error body first
      let errorMessage = `API Error: ${response.status}`;
      try {
        const text = await response.text();
        // Try parsing as JSON first
        try {
          const errorData = JSON.parse(text);
          if (errorData.message) errorMessage = errorData.message;
          else if (errorData.error) errorMessage = errorData.error;
        } catch {
          // It's plain text (e.g. Spring Boot error message)
          if (text && text.length > 0 && text.length < 500) {
            errorMessage = text;
          }
        }
      } catch (parseError) {
        // Fallback if reading body fails entirely
      }
      throw new Error(errorMessage);
    }

    // Read the response body as text first, then decide how to parse
    const text = await response.text();

    // If the body is empty, return null
    if (!text || text.trim().length === 0) {
      return null;
    }

    // Try parsing as JSON
    try {
      return JSON.parse(text);
    } catch {
      // Not JSON — return as plain text (e.g. "User registered successfully")
      return text;
    }
  } catch (error) {
    // Check for network errors vs business logic errors
    if (error instanceof TypeError && error.message.includes('fetch')) {
      throw new Error("Unable to connect to service. Please ensure the API Gateway on port 8080 is running.");
    }
    throw error;
  }
}

/**
 * Auth APIs
 */
export const authApi = {
  login: (data) => apiFetch('/auth/login', { method: 'POST', body: JSON.stringify(data) }),
  register: (data) => apiFetch('/auth/register', { method: 'POST', body: JSON.stringify(data) }),
};

/**
 * Measurement APIs
 */
export const measurementApi = {
  /**
   * Generic conversion using the backend endpoint.
   */
  convert: (type, fromUnit, toUnit, value) => 
    apiFetch(`/measurement/measure/convert?type=${encodeURIComponent(type)}&fromUnit=${encodeURIComponent(fromUnit)}&toUnit=${encodeURIComponent(toUnit)}&value=${encodeURIComponent(value)}`),

  /**
   * Arithmetic operation using the backend.
   */
  calculate: (type, op, val1, unit1, val2, unit2, targetUnit) =>
    apiFetch(`/measurement/measure/arithmetic?type=${encodeURIComponent(type)}&op=${encodeURIComponent(op)}&val1=${encodeURIComponent(val1)}&unit1=${encodeURIComponent(unit1)}&val2=${encodeURIComponent(val2)}&unit2=${encodeURIComponent(unit2)}&targetUnit=${encodeURIComponent(targetUnit)}`),

  /**
   * Comparison using the backend.
   */
  compare: (type, val1, unit1, val2, unit2) =>
    apiFetch(`/measurement/measure/compare?type=${encodeURIComponent(type)}&val1=${encodeURIComponent(val1)}&unit1=${encodeURIComponent(unit1)}&val2=${encodeURIComponent(val2)}&unit2=${encodeURIComponent(unit2)}`),
};
