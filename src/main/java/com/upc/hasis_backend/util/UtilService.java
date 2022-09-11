package com.upc.hasis_backend.util;

import com.upc.hasis_backend.security.Encriptado;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class UtilService {

    private static final ZoneId oldZone = ZoneId.of(TimeZone.getDefault().getID());

    public static LocalDate getNowDate(){
        LocalDateTime oldDateTime = LocalDateTime.now();

        ZoneId newZone = ZoneId.of("America/Lima");

        LocalDateTime newDateTime = oldDateTime.atZone(oldZone)
                .withZoneSameInstant(newZone)
                .toLocalDateTime();

//        System.out.println(newDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE));
//        System.out.println(newDateTime.toLocalDate());

        return newDateTime.toLocalDate();
    }

    public static LocalDate getNextDay(){
        return getNowDate().plusDays(1);
    }

    public static LocalDate getEndDay(Integer days){
        return getNextDay().plusDays(days);
    }
    public static Integer getActualAge(LocalDate birthDate){
        Period age = Period.between(birthDate, getNowDate());
        return  age.getYears();
    }

    public static String randomString() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 5;
        for(int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        String randomString = sb.toString();
        return randomString;
    }

    public static String randomPassword() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxz1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 8;
        for(int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        String randomString = sb.toString();
        return randomString;
    }

    public static String  encriptarContrasena (String contrasena){

        byte[] salt = new String("12345678").getBytes();
        int iterationCount = 40000;
        int keyLength = 128;
        SecretKeySpec key = null;
        try {
            key = Encriptado.createSecretKey("contrasena".toCharArray(), salt, iterationCount, keyLength);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        String nueva = contrasena;
        try {
            nueva = Encriptado.encrypt(contrasena, key);
        } catch (GeneralSecurityException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return nueva;
    }


    public static String  desencriptarContrasena (String contrasena){

        byte[] salt = new String("12345678").getBytes();
        int iterationCount = 40000;
        int keyLength = 128;
        SecretKeySpec key = null;
        try {
            key = Encriptado.createSecretKey("contrasena".toCharArray(), salt, iterationCount, keyLength);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        String nueva = contrasena;
        try {
            nueva = Encriptado.decrypt(contrasena, key);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        return nueva;
    }

    public static String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60000000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return token;
    }
}