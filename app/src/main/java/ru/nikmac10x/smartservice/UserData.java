package ru.nikmac10x.smartservice;

import java.util.ArrayList;

public class UserData {

    private static String userName = null;
    private static String userEmail = null;
    private static String translationDescription = null;

    private static final String NS = "http://translation#";
    private static String NAME_URL = NS + "name";
    private static String EMAIL_URL = NS + "email";
    private static String DESCRIPTION_URL = NS + "description";
    private static String USER_URL = NS + "user1";

    static String getUserName(){
        return userName;
    }

    static String getUserEmail(){
        return userEmail;
    }

    static public String getTranslationDescription() {
        return translationDescription;
    }

    public static void setUserName(String userName) {
        UserData.userName = userName;
    }

    public static void setUserEmail(String userEmail) {
        UserData.userEmail = userEmail;
    }

    public static void setTranslationDescription(String translationDescription) {
        UserData.translationDescription = translationDescription;
    }

    public static Boolean isSet() {
        if (userName != null && userEmail != null && translationDescription != null) {
            return true;
        }

        return false;
    }

    public static ArrayList<ArrayList<String>> getPersonTriples() {
        ArrayList<ArrayList<String>> triples = new ArrayList<ArrayList<String>>();

        triples.add(SSAgent.createTriple(USER_URL, NAME_URL, getUserName(), "url", "literal"));
        triples.add(SSAgent.createTriple(USER_URL, EMAIL_URL, getUserEmail(), "url", "literal"));
        triples.add(SSAgent.createTriple(USER_URL, DESCRIPTION_URL, getTranslationDescription(), "url", "literal"));

        return triples;
    }

    public static String getUserURL() {
        return USER_URL;
    }
}
