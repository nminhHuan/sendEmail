package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Gửi mail thông qua Google Apps Script (HTTPS, cổng 443).
 * Render chặn SMTP nên không dùng JavaMail được.
 *
 * Cần 2 biến môi trường trên Render:
 *   SCRIPT_URL    = URL Web App của Apps Script (kết thúc bằng /exec)
 *   SCRIPT_SECRET = chuỗi bí mật, trùng với SECRET trong Code.gs
 */
public class MailUtil {

    public static void sendMail(String to, String from, String subject, String body, boolean bodyIsHTML)
            throws IOException {

        String scriptUrl = System.getenv("SCRIPT_URL");
        String secret = System.getenv("SCRIPT_SECRET");
        if (scriptUrl == null || scriptUrl.isEmpty() || secret == null || secret.isEmpty()) {
            throw new IOException("Thiếu biến môi trường SCRIPT_URL hoặc SCRIPT_SECRET");
        }

        String json = "{"
                + "\"secret\":\"" + esc(secret) + "\","
                + "\"to\":\"" + esc(to) + "\","
                + "\"subject\":\"" + esc(subject) + "\","
                + "\"body\":\"" + esc(body) + "\","
                + "\"html\":" + bodyIsHTML + ","
                + "\"name\":\"Huan\""
                + "}";

        HttpURLConnection conn = (HttpURLConnection) new URL(scriptUrl).openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(true);
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(30000);
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes(StandardCharsets.UTF_8));
        }

        int status = conn.getResponseCode();
        InputStream is = (status >= 200 && status < 400) ? conn.getInputStream() : conn.getErrorStream();
        String response = "";
        if (is != null) {
            try (Scanner sc = new Scanner(is, "UTF-8").useDelimiter("\\A")) {
                response = sc.hasNext() ? sc.next() : "";
            }
        }
        conn.disconnect();

        if (!response.contains("\"ok\":true")) {
            throw new IOException("Apps Script trả về lỗi (HTTP " + status + "): " + response);
        }
    }

    private static String esc(String s) {
        if (s == null) return "";
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            switch (c) {
                case '\\': sb.append("\\\\"); break;
                case '"':  sb.append("\\\""); break;
                case '\n': sb.append("\\n");  break;
                case '\r': sb.append("\\r");  break;
                case '\t': sb.append("\\t");  break;
                default:
                    if (c < 0x20) sb.append(String.format("\\u%04x", (int) c));
                    else sb.append(c);
            }
        }
        return sb.toString();
    }
}