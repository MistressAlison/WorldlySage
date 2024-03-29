package WorldlySage.util;

import java.lang.reflect.Field;
import java.util.HashMap;

public class KeywordManager {
    public static HashMap<String, String> keywordMap = new HashMap<>();
    public static String BRACED;
    public static String EXPOSED;
    public static String STAGGER;
    public static String INFUSE;
    public static String GATHER;
    public static String ABILITY;
    public static String PLANT;
    public static String GROWTH;
    public static String WATER;
    public static String HYDRATION;
    public static String MYSTIC_FORCE;
    public static String CRUSH;
    public static String FLOW;
    public static String PHANTOM;
    public static String ADAPTIVE;
    public static String GLYPH;
    public static String ENERGY_GLYPH;
    public static String DRAW_GLYPH;
    public static String PIERCE_GLYPH;
    public static String SHIELD_GLYPH;
    public static String SWAP_GLYPH;
    public static String ACCURACY_GLYPH;

    public static String getKeyword(String ID) {
        return keywordMap.getOrDefault(ID, "");
    }

    public static void setupKeyword(String ID, String key) {
        keywordMap.put(ID, key);
        Field[] fields = KeywordManager.class.getDeclaredFields();
        for (Field f : fields) {
            if (f.getName().toLowerCase().equals(ID)) {
                try {
                    f.set(null, key);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
