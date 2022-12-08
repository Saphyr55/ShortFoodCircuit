package fr.sfc.framework.common;

import java.lang.reflect.Member;

public final class TagManager {


    public static final char SEPARATOR = '.';
    public static String getValue(Member member, Tag tag) {
        if (tag == null || tag.value().isEmpty())
            return member.getName();
        return tag.value();
    }


}
