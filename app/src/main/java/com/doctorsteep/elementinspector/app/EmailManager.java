package com.doctorsteep.elementinspector.app;

import android.util.Patterns;

public class EmailManager {

    public static boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
