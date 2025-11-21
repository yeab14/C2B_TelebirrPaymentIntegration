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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SignatureUtil {

    private final String privateKeyPem;

    public SignatureUtil(@Value("${telebirr.private-key}") String privateKeyPem) {
        this.privateKeyPem = privateKeyPem;
    }

    public String signRequest(Map<String, String> params) throws Exception {
        Map<String, String> sorted = new TreeMap<>(params);

        String data = sorted.entrySet().stream()
                .filter(entry -> entry.getValue() != null && !entry.getKey().equalsIgnoreCase("sign") && !entry.getKey().equalsIgnoreCase("sign_type"))
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(getPrivateKeyFromPem(privateKeyPem));
        signature.update(data.getBytes(StandardCharsets.UTF_8));

        byte[] signedBytes = signature.sign();
        return Base64.getEncoder().encodeToString(signedBytes);
    }

    private PrivateKey getPrivateKeyFromPem(String pem) throws Exception {
        String privateKeyPEM = pem.replace("-----BEGIN PRIVATE KEY-----", "")
                                  .replace("-----END PRIVATE KEY-----", "")
                                  .replaceAll("\\s", "");
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyPEM);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }
}
