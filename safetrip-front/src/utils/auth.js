const AUTH_STORAGE_KEY = "safetrip_auth";

export function getStoredAuth() {
  const raw = localStorage.getItem(AUTH_STORAGE_KEY);

  if (!raw) {
    return null;
  }

  try {
    return JSON.parse(raw);
  } catch {
    localStorage.removeItem(AUTH_STORAGE_KEY);
    return null;
  }
}

export function getAccessToken() {
  return getStoredAuth()?.accessToken || "";
}

export function getCurrentUser() {
  return getStoredAuth()?.user || null;
}

export function saveAuth(authResponse) {
  const payload = {
    accessToken: authResponse.accessToken,
    tokenType: authResponse.tokenType,
    user: authResponse.user,
  };

  localStorage.setItem(AUTH_STORAGE_KEY, JSON.stringify(payload));
  return payload;
}

export function clearAuth() {
  localStorage.removeItem(AUTH_STORAGE_KEY);
}

export function isAuthenticated() {
  return Boolean(getAccessToken());
}

export function isAdminUser() {
  const role = getCurrentUser()?.role;
  return role === "ADMIN" || role === "MODERATOR";
}
