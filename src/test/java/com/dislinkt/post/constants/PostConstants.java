package com.dislinkt.post.constants;

import java.util.ArrayList;
import java.util.List;

public class PostConstants {
    
    public static String NEW_TEXT = "new post text";
    public static String NEW_URL = "new_img";
    
    public static List<String> NEW_LINKS = new ArrayList<String>(){
        {
            add("link5");
            add("link6");
            add("link7");
        }
    };

    public static int UPDATE_ID = 4;

    public static int DELETE_ID = 5;

    public static int NON_EXISTANT_ID = 99;

}
