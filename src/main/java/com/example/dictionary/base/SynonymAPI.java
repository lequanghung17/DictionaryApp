package com.example.dictionary.base;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SynonymAPI {


    public static ArrayList<String> getSynonym(String s) {
        ArrayList<String> sym = null;
        try {
            sym = new ArrayList<>();
            URL url = new URL("https://languagetools.p.rapidapi.com/all/" + URLEncoder.encode(s, StandardCharsets.UTF_8).replace("+", "%20"));
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestProperty("x-rapidapi-host", "languagetools.p.rapidapi.com");
            request.setRequestProperty("x-rapidapi-key", "aca2c0c9a3mshdae9b0fd091fb0dp1923ffjsn84863605816e");
            request.setRequestMethod("GET");
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String inputLine;
            while ((inputLine = inputStream.readLine()) != null) {
                sym.add(inputLine);
            }
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sym;
    }
    public static String convert(ArrayList<String> arrayList) {
        StringBuilder sb = new StringBuilder();
        for (String item : arrayList) {
            sb.append(item).append(", ");
        }
        String result = sb.toString();
        result = result.replaceAll("[\"{}\\[\\]]", "");

        result = result.replaceAll("(synonyms|hypernyms|hyponyms|antonyms)", "<br>- $1");

        return result.trim();
    }
}