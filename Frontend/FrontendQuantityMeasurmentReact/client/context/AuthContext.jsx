import React, { createContext, useContext, useState } from "react";
import { authApi } from "../lib/api";

const AuthContext = createContext(undefined);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    const stored = localStorage.getItem("auth_user");
    try {
      return stored ? JSON.parse(stored) : null;
    } catch {
      // In case stored data is corrupted
      localStorage.removeItem("auth_user");
      return null;
    }
  });

  const login = async (email, password) => {
    try {
      // Connect to the backend auth-service via the gateway
      const response = await authApi.login({ email, password });
      
      // The backend returns { token: "..." } as AuthResponse
      const userData = {
        id: response.id || Math.random().toString(36).substr(2, 9),
        email,
        name: response.name || email.split("@")[0],
        token: response.token, // Store the JWT provided by the backend
      };

      setUser(userData);
      localStorage.setItem("auth_user", JSON.stringify(userData));
    } catch (error) {
      console.error("Login failed:", error.message);
      throw error;
    }
  };

  const signup = async (name, email, password) => {
    try {
      // Connect to the backend auth-service via the gateway
      // Note: The register endpoint returns a plain string like "User registered successfully"
      // not a JSON object. Our updated apiFetch handles this correctly now.
      await authApi.register({ name, email, password });
      
      // After successful registration, auto-login to get a JWT token
      try {
        const loginResponse = await authApi.login({ email, password });
        const userData = {
          id: loginResponse.id || Math.random().toString(36).substr(2, 9),
          email,
          name,
          token: loginResponse.token,
        };

        setUser(userData);
        localStorage.setItem("auth_user", JSON.stringify(userData));
      } catch (loginErr) {
        // If auto-login fails after registration, still log user in locally
        console.warn("Auto-login after signup failed, using local session:", loginErr);
        const userData = {
          id: Math.random().toString(36).substr(2, 9),
          email,
          name,
        };

        setUser(userData);
        localStorage.setItem("auth_user", JSON.stringify(userData));
      }
    } catch (error) {
      console.error("Signup failed:", error.message);
      throw error;
    }
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem("auth_user");
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        isAuthenticated: !!user,
        login,
        signup,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error("useAuth must be used within AuthProvider");
  }
  return context;
}
