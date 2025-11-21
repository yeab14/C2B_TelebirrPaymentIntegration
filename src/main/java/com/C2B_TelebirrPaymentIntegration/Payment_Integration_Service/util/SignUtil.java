package com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.util;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SignUtil {


    public static PrivateKey loadPrivateKey(String privateKeyPem) throws Exception {
        String privateKeyContent = privateKeyPem
                .replaceAll("\\n", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "");

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    public static String generateSign(Map<String, Object> params, PrivateKey privateKey) throws Exception {
       
        Map<String, Object> filtered = params.entrySet().stream()
                .filter(e -> e.getValue() != null && !e.getKey().equals("sign") && !e.getKey().equals("sign_type"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<String, Object> sorted = new TreeMap<>(filtered);

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : sorted.entrySet()) {
            sb.append(entry.getKey()).append("=");
            if (entry.getValue() instanceof String) {
                sb.append(entry.getValue());
            } else {
                
                ObjectMapper mapper = new ObjectMapper();
                sb.append(mapper.writeValueAsString(entry.getValue()));
            }
            sb.append("&");
        }
        sb.deleteCharAt(sb.length() - 1); 
      
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(sb.toString().getBytes(StandardCharsets.UTF_8));
        byte[] signedBytes = signature.sign();
        return Base64.getEncoder().encodeToString(signedBytes);
    }
}
