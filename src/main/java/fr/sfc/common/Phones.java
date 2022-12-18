package fr.sfc.common;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public final class Phones {

    public static String formatPhoneNumberFR(String phone) {
        try {
            Phonenumber.PhoneNumber phoneNumber = PhoneNumberUtil.getInstance().parse(phone, "FR");
            return  PhoneNumberUtil.getInstance().format(phoneNumber,
                    PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
        } catch (NumberParseException e) {
            throw new RuntimeException(e);
        }
    }

}
