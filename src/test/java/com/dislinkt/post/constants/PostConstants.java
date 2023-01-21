package com.dislinkt.post.constants;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public static UUID UPDATE_ID = UUID.fromString("599703c2-9763-11ed-a8fc-0242ac120005");

    public static UUID DELETE_ID = UUID.fromString("599703c2-9763-11ed-a8fc-0242ac120006");

    public static UUID NON_EXISTANT_ID = UUID.fromString("599663c2-9763-11ed-a8fc-0242ac120003");

}
