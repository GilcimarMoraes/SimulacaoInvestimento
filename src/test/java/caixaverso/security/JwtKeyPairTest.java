package caixaverso.security;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Garante que a chave usada para assinar o JWT existe e combina com a chave
 * publica usada para validar o token. Sem isso, o login autentica a senha mas
 * falha ao gerar o token (500).
 */
class JwtKeyPairTest {

    @Test
    void privateKeyDeveExistirECombaterComPublicKey() throws Exception {
        RSAPrivateCrtKey privateKey = readPrivateKey("privateKey.pem");
        RSAPublicKey publicKey = readPublicKey("publicKey.pem");

        assertEquals(publicKey.getModulus(), privateKey.getModulus(),
                "privateKey.pem nao corresponde a publicKey.pem");
        assertEquals(publicKey.getPublicExponent(), privateKey.getPublicExponent(),
                "expoente publico divergente entre as chaves");
    }

    private static RSAPrivateCrtKey readPrivateKey(String resource) throws Exception {
        byte[] der = decode(readPem(resource), "PRIVATE KEY");
        return (RSAPrivateCrtKey) KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(der));
    }

    private static RSAPublicKey readPublicKey(String resource) throws Exception {
        byte[] der = decode(readPem(resource), "PUBLIC KEY");
        return (RSAPublicKey) KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(der));
    }

    private static String readPem(String resource) throws Exception {
        try (InputStream in = JwtKeyPairTest.class.getClassLoader().getResourceAsStream(resource)) {
            assertNotNull(in, resource + " nao encontrado no classpath");
            return new String(in.readAllBytes(), StandardCharsets.US_ASCII);
        }
    }

    private static byte[] decode(String pem, String type) {
        String base64 = pem
                .replace("-----BEGIN " + type + "-----", "")
                .replace("-----END " + type + "-----", "")
                .replaceAll("\\s", "");
        return Base64.getDecoder().decode(base64);
    }
}
