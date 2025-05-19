package util;

import java.util.*;

class JSONParser {
    private final String json;
    private int index;

    public JSONParser(String json) {
        json = json.trim();
        //json = json.replaceAll(" ", "");
        json = json.replaceAll("(\\r|\\n)", "");
        json = removeSpaces(json);

        //System.out.println(json);

        this.json = json;
        this.index = 0;
    }

    @SuppressWarnings("unchecked")
    public <T> T parse() {
        char c = peek();
        if (c == '{') return (T) parseObject();
        if (c == '[') return (T) parseArray();
        throw new RuntimeException("Invalid JSON format");
    }

    private Map<String, Object> parseObject() {
        Map<String, Object> map = new HashMap<>();
        consume('{');
        while (peek() != '}') {
            String key = parseString();
            consume(':');
            Object value = parseValue();
            map.put(key, value);
            if (peek() == ',') consume(',');
        }
        consume('}');
        return map;
    }

    private List<Object> parseArray() {
        List<Object> list = new ArrayList<>();
        consume('[');
        while (peek() != ']') {
            list.add(parseValue());
            if (peek() == ',') consume(',');
        }
        consume(']');
        return list;
    }

    private Object parseValue() {
        char c = peek();
        if (c == '"') return parseString();
        if (Character.isDigit(c) || c == '-') return parseNumber();
        if (c == '{') return parseObject();
        if (c == '[') return parseArray();
        if (json.startsWith("true", index)) {
            index += 4;
            return true;
        }
        if (json.startsWith("false", index)) {
            index += 5;
            return false;
        }
        if (json.startsWith("null", index)) {
            index += 4;
            return null;
        }

        throw new RuntimeException("Invalid value at index " + index);
    }

    private String parseString() {
        consume('"');
        StringBuilder sb = new StringBuilder();
        while (peek() != '"') {
            sb.append(json.charAt(index++));
        }
        consume('"');
        return sb.toString();
    }

    private Number parseNumber() {
        int start = index;
        while (Character.isDigit(peek()) || peek() == '.' || peek() == '-') {
            index++;
        }
        String numStr = json.substring(start, index);
        //return numStr.contains(".") ? Double.parseDouble(numStr) : Integer.parseInt(numStr);
        return Integer.valueOf(numStr);
    }

    private void consume(char expected) {
        //System.out.println(json.charAt(index));
        if (json.charAt(index) != expected) {
            throw new RuntimeException("Expected '" + expected + "' but found '" + json.charAt(index) + "'");
        }
        index++;
    }

    private char peek() {
        return index < json.length() ? json.charAt(index) : '\0';
    }

    public static String removeSpaces(String input) {
        StringBuilder result = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            }

            if (c != ' ' || inQuotes) {
                result.append(c);
            }
        }
        return result.toString();
    }

}
