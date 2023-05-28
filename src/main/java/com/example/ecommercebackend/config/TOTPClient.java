package com.example.ecommercebackend.config;

import org.apache.commons.lang3.RandomStringUtils;
import org.jboss.aerogear.security.otp.Totp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class TOTPClient {
        public static String QR_PREFIX =
                "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
        public static String  APP_NAME = "My e-commerce";
        public static String generateSecretKey() {
            return RandomStringUtils.randomAlphabetic(16);

        }
        public static String getTOTPCode(String secretKey) {
            Totp totp = new Totp(secretKey);
            String otp = totp.now();
            return otp;

        }
        public static boolean validateOTP(String secret,String otp) {
            boolean result = false;
            try {
                Totp totp = new Totp(secret);

                result = totp.verify(otp);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
        public static String getGoogleAuthenticatorBarCode(String secretKey, String account) throws UnsupportedEncodingException {

            try {
                return QR_PREFIX +"otpauth://totp/"
                        + URLEncoder.encode(APP_NAME + ":" + account, "UTF-8").replace("+", "%20")
                        + "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20")
                        + "&issuer=" + URLEncoder.encode(APP_NAME, "UTF-8").replace("+", "%20");
            } catch (UnsupportedEncodingException e) {
                throw new IllegalStateException(e);
            }
        }
}
