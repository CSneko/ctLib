package org.cneko.ctlib.common.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
public class HttpPost {
    public static class HttpPostObject extends HttpBase.HttpBaseObject{
        private String postData;

        public HttpPostObject(String url, String postData) {
            super(url);
            this.postData = postData;
        }

        public HttpPostObject(String url, String postData, String contentType) {
            super(url);
            if (contentType != null) {
                if (headers == null) {
                    headers = new HashMap<>();
                }
                headers.put("Content-Type", contentType);
            }
            this.postData = postData;
        }

        @Override
        public HttpBase.HttpMethod getMethod() {
            return HttpBase.HttpMethod.POST;
        }

        public void setPostData(String postData) {
            this.postData = postData;
        }

        public String getPostData() {
            return postData;
        }

        @Override
        public void connect() throws IOException {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod(method.toString());

            // 应用Cookie
            for (Map.Entry<String, String> entry : this.getCookie().entrySet()) {
                conn.setRequestProperty("Cookie", entry.getKey() + "=" + entry.getValue());
            }

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            // 写入POST数据
            if (postData != null && !postData.isEmpty()) {
                conn.setDoOutput(true);
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);
                    os.write(postDataBytes);
                }
            }

            int responseCode = conn.getResponseCode();
            this.responseCode = responseCode;

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }

                in.close();
                this.response = response.toString();
            } else {
                this.response = null;
            }

            // Save response headers
            Map<String, List<String>> map = conn.getHeaderFields();
            StringBuilder headers = new StringBuilder();
            for (Map.Entry<String, java.util.List<String>> entry : map.entrySet()) {
                headers.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
            }
            this.responseHeaders = headers.toString();
        }
    }
}
