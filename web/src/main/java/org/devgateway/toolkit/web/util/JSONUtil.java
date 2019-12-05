package org.devgateway.toolkit.web.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author dbianco
 */
public final class JSONUtil {

    private static final Class<?> JSON_OBJECT = JSONObject.class;
    private static final Class<?> JSON_ARRAY = JSONArray.class;
    private static final Logger logger = LoggerFactory.getLogger(JSONUtil.class);

    private JSONUtil() {
    }

    public static List<Map<String, String>> parseJson(URI uri) {
        return parseJson(uri, "UTF-8");
    }


    public static List<Map<String, String>> parseJson(URI uri, String encoding) {
        List<Map<String, String>> jsonFlat = null;
        try {
            String json = IOUtils.toString(uri, encoding);
            jsonFlat = parseJson(json);
        } catch (IOException e) {
            logger.error("Exception: ", e);
        } catch (Exception ex) {
            logger.error("Exception: ", ex);
        }

        return jsonFlat;
    }

    public static List<Map<String, String>> parseJson(File file) {
        return parseJson(file, "ISO-8859-1");
    }


    public static List<Map<String, String>> parseJson(File file, String encoding) {
        List<Map<String, String>> jsonFlat = null;
        try {
            String json = FileUtils.readFileToString(file, encoding);
            jsonFlat = parseJson(json);
        } catch (IOException e) {
            logger.error("IOException: ", e);
        } catch (Exception ex) {
            logger.error("Exception: ", ex);
        }

        return jsonFlat;
    }


    public static List<Map<String, String>> parseJson(String json) {
        List<Map<String, String>> jsonFlat;

        try {
            JSONObject jsonObject = new JSONObject(json);
            jsonFlat = new ArrayList<>();
            jsonFlat.add(parse(jsonObject));
        } catch (JSONException je) {
            jsonFlat = handleAsArray(json);
        }

        return jsonFlat;
    }

    public static Map<String, String> parse(JSONObject jsonObject) {
        Map<String, String> flatJson = new LinkedHashMap<>();
        flatten(jsonObject, flatJson, "");

        return flatJson;
    }


    public static List<Map<String, String>> parse(JSONArray jsonArray) {
        JSONObject jsonObject;
        List<Map<String, String>> flatJson = new ArrayList<>();
        int length = jsonArray.length();

        for (int i = 0; i < length; i++) {
            jsonObject = jsonArray.getJSONObject(i);
            Map<String, String> stringMap = parse(jsonObject);
            flatJson.add(stringMap);
        }

        return flatJson;
    }

    private static List<Map<String, String>> handleAsArray(String json) {
        List<Map<String, String>> jsonFlat = null;
        try {
            JSONArray jsonArray = new JSONArray(json);
            jsonFlat = parse(jsonArray);
        } catch (Exception e) {
            logger.error("Exception: ", e);
        }

        return jsonFlat;
    }

    private static void flatten(JSONObject obj, Map<String, String> jsonFlat, String prefix) {
        Iterator<?> iterator = obj.keys();
        String newPrefix = !prefix.equals("") ? prefix + "." : "";

        while (iterator.hasNext()) {
            String key = iterator.next().toString();

            if (obj.get(key).getClass() == JSON_OBJECT) {
                JSONObject jsonObject = (JSONObject) obj.get(key);
                flatten(jsonObject, jsonFlat, newPrefix + key);
            } else if (obj.get(key).getClass() == JSON_ARRAY) {
                JSONArray jsonArray = (JSONArray) obj.get(key);

                if (jsonArray.length() < 1) {
                    continue;
                }

                flatten(jsonArray, jsonFlat, newPrefix + key);
            } else {
                String value = obj.get(key).toString();

                if (value != null && !value.equals("null")) {
                    jsonFlat.put(newPrefix + key, value);
                }
            }
        }

    }


    private static void flatten(JSONArray obj, Map<String, String> jsonFlat, String prefix) {
        int length = obj.length();

        for (int i = 0; i < length; i++) {
            if (obj.get(i).getClass() == JSON_ARRAY) {
                JSONArray jsonArray = (JSONArray) obj.get(i);

                // jsonArray is empty
                if (jsonArray.length() < 1) {
                    continue;
                }

                flatten(jsonArray, jsonFlat, prefix + "[" + i + "]");
            } else if (obj.get(i).getClass() == JSON_OBJECT) {
                JSONObject jsonObject = (JSONObject) obj.get(i);
                flatten(jsonObject, jsonFlat, prefix + "[" + (i + 1) + "]");
            } else {
                String value = obj.get(i).toString();

                if (value != null) {
                    jsonFlat.put(prefix + "[" + (i + 1) + "]", value);
                }
            }
        }
    }

    public static String getCSV(List<Map<String, String>> flatJson) {
        return getCSV(flatJson, ",");
    }

    public static String getCSV(List<Map<String, String>> flatJson, String separator) {
        Set<String> headers = collectHeaders(flatJson);
        String csvString = StringUtils.join(headers.toArray(), separator) + "\n";

        for (Map<String, String> map : flatJson) {
            csvString = csvString + getSeperatedColumns(headers, map, separator) + "\n";
        }

        return csvString;
    }

    private static String getSeperatedColumns(Set<String> headers, Map<String, String> map, String separator) {
        List<String> items = new ArrayList<String>();
        for (String header : headers) {
            String value = map.get(header) == null ? ""
                    : map.get(header).replaceAll("[\\,\\;\\r\\n\\t\\s]+", " ");
            items.add(value);
        }

        return StringUtils.join(items.toArray(), separator);
    }

    private static Set<String> collectHeaders(List<Map<String, String>> flatJson) {
        Set<String> headers = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                if (!s1.equals(s2)) {
                    if (s1.equalsIgnoreCase("year") || s1.equalsIgnoreCase("année")) {
                        return -1;
                    }
                    if (s2.equalsIgnoreCase("year") || s2.equalsIgnoreCase("année")) {
                        return 1;
                    }
                }
                return s1.compareToIgnoreCase(s2);
            }
        });

        for (Map<String, String> map : flatJson) {
            headers.addAll(map.keySet());
        }

        return headers;
    }

}