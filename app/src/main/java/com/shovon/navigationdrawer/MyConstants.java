package com.shovon.navigationdrawer;

public class MyConstants {
    public static final String BANNER_ID = "ca-app-pub-3940256099942544/6300978111";
    public static final String INTERSTITIAL_ID = "ca-app-pub-3940256099942544/1033173712";
    public static final int KEY_DEFAULT_EXIT = 1;
    public static final int KEY_DEFAULT_SOUND = 3;
    public static final String KEY_INT = "KEY_ADMOB";
    public static final String POLICY = "https://policies.google.com/privacy?hl=en";
    public static Boolean SHOW_BANNER;
    public static Boolean SHOW_INTERSTITIAL;
    public static int TOTAL_SOUND_A = 21;

    static {
        Boolean valueOf = Boolean.valueOf(true);
        SHOW_BANNER = valueOf;
        SHOW_INTERSTITIAL = valueOf;
    }
}
