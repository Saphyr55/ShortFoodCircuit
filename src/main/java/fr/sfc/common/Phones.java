package fr.sfc.common;

public final class Phones {

    public static String formatPhoneNumberFR(String phone) {
        return phone.replaceAll("(.{2})", "$1 ");
    }

}
