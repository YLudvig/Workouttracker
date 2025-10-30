package com.workouttracker.workouttracker.websocket;

// Tog hjälp av denna guiden för att skapa lobbykoder: 
// https://www.geeksforgeeks.org/java/generate-random-string-of-given-size-in-java/?utm_source=chatgpt.com
public class SessionCodeGenerator {

    public static String getSessionCode(int n){
        // Definierar vilka tecken som är tillåtna
        String sessionCode = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
             + "0123456789"
             + "abcdefghijklmnopqrstuvxyz"; 

        StringBuilder sb = new StringBuilder(n);

        // Beroende på hur många tecken som man ska använda så generas en kod
        for (int i = 0; i < n; i++){
            int index = (int)(sessionCode.length() * Math.random());

            sb.append(sessionCode.charAt(index));
        }

        return sb.toString();

    }

    

}