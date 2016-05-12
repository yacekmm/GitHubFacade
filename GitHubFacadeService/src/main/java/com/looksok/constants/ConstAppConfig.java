package com.looksok.constants;

import org.springframework.http.MediaType;

public class ConstAppConfig {
    public static class GitHub {
        private static final String URL_BASE = "https://api.github.com/";
        public static final String URL_REPOS = URL_BASE + "repos/";
        public static final MediaType HEADER_ACCEPT_V3 = new MediaType("application", "vnd.github.v3+json");
    }
}
