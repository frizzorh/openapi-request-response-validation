package com.tweesky.cloudtools.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {

    public static String getPath(String uri) throws URISyntaxException {
        return (new URI(uri)).getPath();
    }

    public static Map<String, String> filterCamelHeaders(Map<String, Object> messageHeaders) {
        HashMap<String, String> result = new HashMap<>();
        for (Map.Entry<String, Object> entry : messageHeaders.entrySet()) {
            if (!entry.getKey().startsWith("Camel")) {
                result.put(entry.getKey(), entry.getValue().toString());
            }
        }
        return result;
    }
}
