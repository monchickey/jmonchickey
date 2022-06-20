package com.monchickey.net;

import java.util.List;
import java.util.Map;

public class HTTPResponse {
    private Map<String, List<String>> headers;
    private String content;

    HTTPResponse(Map<String, List<String>> headers, String content) {
        this.headers = headers;
        this.content = content;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public String getContent() {
        return content;
    }
}
