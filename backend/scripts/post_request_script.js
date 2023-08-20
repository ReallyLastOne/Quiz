// fetch csrf token from cookie and set as environmental variable
pm.environment.set("csrfToken", pm.cookies.get("XSRF-TOKEN"));

// fetch access/refresh token from response if present and set as environmental variables
if (pm.response != null) {
    var response = pm.response.json();
    if (response.accessToken != null) {
        pm.environment.set("accessToken", response.accessToken);
    }

    if (response.refreshToken != null) {
        pm.environment.set("refreshToken", response.refreshToken);
    }
}