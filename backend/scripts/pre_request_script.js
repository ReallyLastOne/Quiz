// add csrf token to header from environmental variable
pm.request.headers.add("X-XSRF-TOKEN: " + pm.environment.get("csrfToken"));

// add access token to Authorization header from environmental variable
pm.request.headers.add("Authorization: Bearer " + pm.environment.get("accessToken"));