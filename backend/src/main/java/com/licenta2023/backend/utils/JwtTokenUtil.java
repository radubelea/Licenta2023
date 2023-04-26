package com.licenta2023.backend.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.licenta2023.backend.domain.User;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class JwtTokenUtil {

    private static final String JWT_HEADER = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
    private static String SECRET = "oeRaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";
    private static ObjectMapper mapper = new ObjectMapper();


    /**
     * Generates a jwt token with user details as payload
     *
     * @param user - the user for which token is generated
     * @return a jwtToken as a String
     */
    public static String createToken(User user) throws JsonProcessingException {
        String payload = createPayload(user);

        String signature = hmacSha256(encode(JWT_HEADER.getBytes(StandardCharsets.UTF_8)) + "." + encode(payload.getBytes(StandardCharsets.UTF_8)), SECRET);
        String jwtToken = encode(JWT_HEADER.getBytes(StandardCharsets.UTF_8)) + "." + encode(payload.getBytes(StandardCharsets.UTF_8)) + "." + signature;

        return jwtToken;
    }

    /**
     * Converts the given user to a json format and returns it as a String
     *
     * @param user
     * @return the payload
     */
    public static String createPayload(User user) throws JsonProcessingException {
        String payload = mapper.writeValueAsString(user);
        System.out.println("Payload:  " + payload);
        return payload;
    }

    /**
     *      Creates a User object from a given payload
     * @param payload the payload from which the user is recreated
     * @return
     */
    public static User getUserFromPayload(String payload){
        try {
            String decodedPayload = decode(payload);
            JsonNode node = mapper.readTree(decodedPayload);

            String name = node.get("name").asText();
            String password = node.get("password").asText();
            Integer age = node.get("age").asInt();
            String email = node.get("email").asText();
            Long ID = node.get("ID").asLong();

            User user = new User(email,password,age,name);
            user.setID(ID);

            return user;
        } catch (IOException e) {
            System.out.println("Failed to parse JSON to User object!");
            return null;
        }
    }

    private static String encode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }

    /**
     * Creates a signature using a secret and a data
     *
     * @param data   - the data that you want to sign
     * @param secret - secret string
     * @return the coresponding signature
     */
    private static String hmacSha256(String data, String secret) {
        try {

            byte[] hash = secret.getBytes(StandardCharsets.UTF_8);
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
            sha256Hmac.init(secretKey);

            byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            return encode(signedBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            System.out.println("Error generating hmacSha256!!");
            return null;
        }
    }

    public static Object getTokenPayloadAsObject(String token){
        String parts[] = token.strip().split("\\.");
        return getUserFromPayload(parts[1]);
    }

    public static boolean isValid(String token){
        String parts[] = token.strip().split("\\.");
        if(parts.length != 3 ){
            return false;
        }
        String encodedHeader = parts[0];
        String encodedPayload = parts[1];
        String signature = parts[2];
        return signature.equals(hmacSha256(encodedHeader + "." + encodedPayload, SECRET));
    }

}
