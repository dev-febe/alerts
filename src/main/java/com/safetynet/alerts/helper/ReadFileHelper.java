package com.safetynet.alerts.helper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadFileHelper {
    private final InputStream inputStream;

    public ReadFileHelper(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public JSONObject readFile()
            throws IOException, ParseException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(this.inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(resultStringBuilder.toString());
    }
}
