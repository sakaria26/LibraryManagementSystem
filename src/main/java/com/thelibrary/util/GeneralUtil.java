package com.thelibrary.util;

import java.util.Calendar;
import java.util.Date;

public class GeneralUtil {
    public static String mediaID(String mediatype, String medianame){

        String thefirstfour = medianame.replaceAll("\\s", "");
        String authorsinitials;
        String[] initialsarray = null;
        StringBuilder initials = new StringBuilder();
        char initial;
        switch (mediatype) {
            case "book" -> {
                return ("BOO"+thefirstfour.substring(0, 4)+String.valueOf( (int) (Math.random()*(999999-100000+1)+100000))).toUpperCase();
            }
            case "ebook" -> {
                return ("EBO"+thefirstfour.substring(0, 4)+String.valueOf((int)(Math.random()*(999999-100000+1)+100000))).toUpperCase();
            }
            case "audiobook" -> {
                return ("AUD"+thefirstfour.substring(0, 4)+String.valueOf((int) (Math.random()*(999999-100000+1)+100000))).toUpperCase();
            }
            case "journal" -> {
                return ("JOU"+thefirstfour.substring(0, 4)+String.valueOf((int) (Math.random()*(999999-100000+1)+100000))).toUpperCase();
            }
            case "magazine" -> {
                return ("MAG"+thefirstfour.substring(0, 4)+String.valueOf( (int)(Math.random()*(999999-100000+1)+100000))).toUpperCase();

            }
            default -> {
                return null;
            }
        }
    }

    public static String librarianusername(String name, String surname){
        String initials = name.substring(0,1)+surname.substring(0,1);
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        String dayOfYear = String.format("%03d", cal.get(Calendar.DAY_OF_YEAR));
        String weekOfYear = String.format("%02d", cal.get(Calendar.WEEK_OF_YEAR)) ;

        return initials.toUpperCase()+dayOfYear+weekOfYear;

    }

    public static String memberID(String name, String surname){
        return ("MEM"+name.substring(0,1)+surname.substring(0,1)+String.valueOf( (int)(Math.random()*(99999-10000+1)+10000))).toUpperCase();
    }
}
