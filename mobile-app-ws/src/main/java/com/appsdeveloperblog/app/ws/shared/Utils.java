package com.appsdeveloperblog.app.ws.shared;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Utils {
    private final Random RANDOM = new SecureRandom();
    private final String ALPHABETH = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generateUserId (int lenght) {
        return generateRandomString(lenght);
    }

    private String generateRandomString(int lenght) {
        StringBuilder returnValue = new StringBuilder(lenght);

        for (int i = 0; i < lenght; i++){
            returnValue.append(ALPHABETH.charAt(RANDOM.nextInt(ALPHABETH.length())));
        }

        return new String(returnValue);
    }
}
