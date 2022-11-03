package com.example.myapplication.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteUtils {
    public static String dateFromLong(long time) {
        return new SimpleDateFormat("EEE, dd MMM yyyy 'at' hh:mm aaa", Locale.US).format(new Date(time));
    }
}
