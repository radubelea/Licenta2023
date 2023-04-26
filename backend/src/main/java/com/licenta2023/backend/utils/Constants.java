package com.licenta2023.backend.utils;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class Constants {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final Pattern NAME_PATTERN=Pattern.compile("^[A-Z][a-z]{2,20}$");
    public static final Pattern FULLNAME_PATTERN=Pattern.compile("^([a-zA-Z]{2,}\\s[a-zA-Z]{1,}'?-?[a-zA-Z]{2,}\\s?([a-zA-Z]{1,})?)");
    public static final Pattern PHONE_NUMBER_PATTERN=Pattern.compile("^+?[0-9]{6,12}$");

    //length between 6 and 25 characters, all letters and numbers and underscore
    public static final Pattern PASSWORD_PATTERN=Pattern.compile("^[a-zA-Z0-9_]{6,25}$");

    //can contain only ASCII letters and digits, with hyphens, underscores and spaces as internal separators
    public static final Pattern USERNAME_PATTERN=Pattern.compile("^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$");

    public static final Pattern EMAIL_PATTERN=Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\."+
          "[a-zA-Z0-9_+&*-]+)*@" +
          "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
          "A-Z]{2,7}$");
}
