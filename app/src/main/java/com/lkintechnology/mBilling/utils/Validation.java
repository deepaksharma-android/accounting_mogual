package com.lkintechnology.mBilling.utils;


public class Validation {
    public static boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPwdFormatValid(String pwd) {
        return pwd.matches("^[0-9a-zA-Z\\@\\#\\!\\$\\%\\^\\&\\*\\(\\)\\_\\+\\=\\-\\~\\`\\/\\.]{6,24}$");
    }

    public static boolean isNameFormatValid(String name) {
        return name.matches("^[\\sa-zA-Z]{2,16}$");
    }

    public static boolean isPhoneFormatValid(String number) {
        number = number.replaceAll("[^0-9]", "");
        return number.matches("^[+]?[0-9]{10,11}$");
    }

    public static boolean isCvvValid(String cvv) {
        return cvv.matches("^[+]?[0-9]{3,4}$");
    }

    public static boolean isZipCodeValid(String zipcode) {
        return zipcode.matches("^[+]?[0-9]{5}$");
    }

    public static boolean isExpireDateValid(String expire) {
        return expire.matches("(?:0[1-9]|1[0-2])/[0-9]{2}");
    }

    public static boolean isLicencePlateValid(String info) {
        return info.matches(".{3,8}$");
    }
}
