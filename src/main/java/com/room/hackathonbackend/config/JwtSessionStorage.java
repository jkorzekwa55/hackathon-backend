package com.room.hackathonbackend.config;

import org.springframework.beans.factory.annotation.Value;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class JwtSessionStorage {
    private static String PUBLIC_KEY_PATH = "public.key";
    private static String PRIVATE_KEY_PATH = "private.key";
    private static KeyPair keyPair;

    @Value("${spring.profiles.active}")
    private static String activeProfile;

    public static PrivateKey getPrivateKey() {
        if (keyPair == null) {
            loadKeyPair();
        }
        return keyPair.getPrivate();
    }

    public static PublicKey getPublicKey() {
        if (keyPair == null) {
            loadKeyPair();
        }
        return keyPair.getPublic();
    }

    public static String decryptByPrivateKey(String encrypted) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());

        byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(encrypted));
        return new String(bytes);
    }

    private static void loadKeyPair() {
        loadKeyPair(false);
    }

    private static void loadKeyPair(boolean recursive) {
        File publicKeyFile = new File(PUBLIC_KEY_PATH);
        File privateKeyFile = new File(PRIVATE_KEY_PATH);

        try {


            if(!privateKeyFile.exists() || !publicKeyFile.exists()) {
                throw new NoSuchFileException("");
            }

            byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKeyTemp = keyFactory.generatePublic(publicKeySpec);

            byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());

            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKeyTemp = keyFactory.generatePrivate(privateKeySpec);

            keyPair = new KeyPair(publicKeyTemp, privateKeyTemp);

        } catch(InvalidKeySpecException e) {
            if(publicKeyFile.delete() && privateKeyFile.delete()) {
                loadKeyPair();
            }
            System.out.println("InvalidKeySpecException");
        } catch(NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException");
        } catch(NoSuchFileException e) {
            if((activeProfile == null || !activeProfile.equals("production")) && !recursive) {
                createKeyPair();
                loadKeyPair(true);
            } else {
                throw new RuntimeException("can't load and create files. runtime broken");
            }
            System.out.println("NoSuchFileException");
        } catch(IOException e) {
            System.out.println("IOException");
        }
    }

    private static void createKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();

            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();

            File publicFile = new File(PUBLIC_KEY_PATH);

            File privateFile = new File(PRIVATE_KEY_PATH);

            if(!privateFile.exists() || !publicFile.exists()) {

                if(publicFile.createNewFile() && privateFile.createNewFile()) {

                    try(FileOutputStream publicFos = new FileOutputStream(publicFile, false);
                        FileOutputStream privateFos = new FileOutputStream(privateFile, false)) {
                        publicFos.write(publicKey.getEncoded());
                        privateFos.write(privateKey.getEncoded());
                    } catch(Exception e) {
                        System.out.println("can't write to files");
                    }
                } else {
                    throw new IOException("can't create new file");
                }

            }

        } catch (NoSuchAlgorithmException e) {
            System.out.println("RSA algorithm can't be loaded");
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException on startup");
        } catch (IOException e) {
            System.out.println("Can't save string to file");
        }
    }
}
