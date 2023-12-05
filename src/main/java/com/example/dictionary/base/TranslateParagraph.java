package com.example.dictionary.base;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
public class TranslateParagraph {
    private static String translateParagraph(String lang1, String lang2, String text) throws IOException {
        StringBuilder response = new StringBuilder();
        String urlStr = "https://script.google.com/macros/s/AKfycby3AOWmhe32TgV9nW-Q0TyGOEyCHQeFiIn7hRgy5m8jHPaXDl2GdToyW_3Ys5MTbK6wjg/exec"
                + "?q="
                + URLEncoder.encode(text, StandardCharsets.UTF_8)
                + "&target="
                + lang2
                + "&source="
                + lang1;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        return response.toString();

    }

    public static String translateParagraphEntoVi(String textInEn) {
        try {
            return translateParagraph("en", "vi", textInEn);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    public static String translateParagraphVitoEn(String textInVi) {
        try {
            return translateParagraph("vi", "en", textInVi);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

}
