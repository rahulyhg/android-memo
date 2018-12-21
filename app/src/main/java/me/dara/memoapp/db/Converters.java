package me.dara.memoapp.db;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author sardor
 */
public class Converters {

  Gson gson = new Gson();

  @TypeConverter
  public static Map<String, Boolean> toMap(String str) {
    Map<String, Boolean> result = new HashMap<>();
    List<Object> objectList = Arrays.asList((str.split("\\s*,\\s*")));
    List<String> keys = new ArrayList<>();
    List<Boolean> values = new ArrayList<>();
    for (Object obj : objectList) {
      try {
        String value = (String) obj;
        keys.add(value);
      } catch (ClassCastException e) {
        boolean bol = (boolean) obj;
        values.add(bol);
      }
    }
    for (int i = 0, j = 0; i < keys.size(); i++, j++) {
      result.put(keys.get(i), values.get(j));
    }
    return result;
  }

  @TypeConverter
  public static String toString(Map<String, Boolean> maps) {
    Set<String> keys = maps.keySet();
    List<Boolean> values = new ArrayList<>();
    StringBuffer result = new StringBuffer();
    for (String key : keys) {
      result.append(key + ",");
      values.add(maps.get(key));
    }
    int count = values.size();
    for (int i = 0; i < count; i++) {
      if (i != count - 1) {
        result.append(values.get(i) + ",");
      } else {
        result.append(values.get(i));
      }
    }
    return result.toString();
  }
}
