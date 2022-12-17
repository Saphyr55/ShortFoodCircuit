package fr.sfc.framework.item;

import java.lang.reflect.Member;

public final class TagManager {


    public static final String DELIMITER = "\\.";
    public static final String ITEM = "item";
    public static final String CONTAINER = "container";

    public static String getValue(Member member, Tag tag) {
        if (tag == null || tag.value().isEmpty())
            return member.getName();
        return tag.value();
    }


}
