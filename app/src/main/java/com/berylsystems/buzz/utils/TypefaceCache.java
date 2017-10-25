package com.berylsystems.buzz.utils;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.util.Hashtable;

public class TypefaceCache {

    private static final String BUZZ_FONT_GOTHAM_LIGHT = "fonts/Gotham_Light.ttf";
    private static final String BUZZ_FONT_GOTHAM_BOOK= "fonts/Gotham_Book.ttf";
    private static final String BUZZ_FONT_GOTHAM_MEDIUM = "fonts/Gotham_Medium.ttf";
    private static final String BUZZ_FONT_GOTHAM_BOLD = "fonts/Gotham_Bold.ttf";
    private static final String BUZZ_FONT_GOTHAM_BOOKITALIC = "fonts/Gotham_BookItalic.ttf";
    private static final String BUZZ_FONT_GOTHAM_BLACK = "fonts/Gotham_Black.ttf";



    private static final Hashtable<String, Typeface> CACHE = new Hashtable<String, Typeface>();

    public static Typeface get(AssetManager manager, int typefaceCode) {
        synchronized (CACHE) {
            String typefaceName = getTypefaceName(typefaceCode);
            if (!CACHE.containsKey(typefaceName)) {
                Typeface t = Typeface.createFromAsset(manager, typefaceName);
                CACHE.put(typefaceName, t);
            }
            return CACHE.get(typefaceName);
        }
    }

    private static String getTypefaceName(int typefaceCode) {
        String typefaceTemp = "";
        switch (typefaceCode) {
            case 0:
                typefaceTemp = BUZZ_FONT_GOTHAM_LIGHT;
                break;
            case 1:
                typefaceTemp = BUZZ_FONT_GOTHAM_BOOK;
                break;
            case 2:
                typefaceTemp =BUZZ_FONT_GOTHAM_MEDIUM;
                break;
            case 3:
                typefaceTemp = BUZZ_FONT_GOTHAM_BOLD;
                break;
            case 4:
                typefaceTemp =BUZZ_FONT_GOTHAM_BOOKITALIC;
                break;
            case 5:
                typefaceTemp =BUZZ_FONT_GOTHAM_BLACK;
                break;

            default:
                typefaceTemp = BUZZ_FONT_GOTHAM_LIGHT;
        }
        return typefaceTemp;
    }// End of getTypefaceName method
}// End of TypefaceCache Class
