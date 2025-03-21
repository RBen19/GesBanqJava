package org.beni.gescartebanque.services;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.beni.gescartebanque.HelloApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Properties;
import java.util.Random;


public class UtilsFonction {


   static   Logger logger = LoggerFactory.getLogger(HelloApplication.class);

    public  static String  generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static  int  generateCVV(){
        Random random = new Random();
        return 1000 + random.nextInt(9000);
    }

    public static String hashPassword(String password, String salt) {
        String hashedPassword = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(salt.getBytes());
            byte[] hashedBytes = digest.digest(password.getBytes());

            hashedPassword=Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            logger.error("une erreur est survenue lors du hash du mot de passe {}",e.getMessage());
        }
       return hashedPassword;
    }
    public static boolean sendUsernameAndPasswordByMail(String username,String password,String destinataire) {
        boolean result = false;

        String from = "benirosinard19@gmail.com";
        String host = "smtp.gmail.com";


        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");


        final String hostMail = "benirosinard19@gmail.com";
        final String hostPassword = "qzxc pvye ebtp flbv";


        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(hostMail, hostPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinataire));

            message.setSubject("Test Email via Gmail SMTP");

            message.setText("Votre nom d'utiisateur est "+username+"  votre mot de passe est  : "+password+"");

            Transport.send(message);

            System.out.println("Email envoyé avec succès.");
            result = true;

        } catch (MessagingException mex) {
            logger.error(" l'eereur {} est survenue lors de l'envoie du mail pour "+ destinataire +"",mex.getMessage());

        }


        return result;
    }
    private static int calculateLuhnCheckDigit(String number) {
        int sum = 0;
        boolean alternate = false;

      // c'est la fonction pour calculer le chiffre de control de la carte ( le dernier
        for (int i = number.length() - 1; i >= 0; i--) {
            int n = Character.getNumericValue(number.charAt(i));

            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }

            sum += n;
            alternate = !alternate;
        }


        int mod = sum % 10;
        return (mod == 0) ? 0 : 10 - mod;
    }
    public static String generateCardNumber(String bin) {

        if (bin.length() != 6) {
            throw new IllegalArgumentException("Le BIN doit être composé de 6 chiffres.");
        }


        StringBuilder cardNumber = new StringBuilder(bin);
        Random rand = new Random();

        for (int i = 0; i < 9; i++) {
            cardNumber.append(rand.nextInt(10));  // Ajoute des chiffres aléatoires (0-9)
        }

        // Calculer le dernier chiffre de contrôle avec l'algorithme de Luhn
        String cardNumberWithCheckDigit = cardNumber.toString();
        int checkDigit = calculateLuhnCheckDigit(cardNumberWithCheckDigit);

        cardNumber.append(checkDigit);

        return cardNumber.toString();
    }
}
