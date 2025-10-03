
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

public class FileEncryptionUtility {

    // Generate AES key
    public static SecretKey generateKey(String keyStr) throws Exception {
        byte[] keyBytes = keyStr.getBytes("UTF-8");
        byte[] key = new byte[16]; // AES 128-bit
        int len = Math.min(keyBytes.length, key.length);
        System.arraycopy(keyBytes, 0, key, 0, len);
        return new SecretKeySpec(key, "AES");
    }

    // Encrypt file
    public static void encryptFile(File inputFile, File outputFile, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[64];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) fos.write(output);
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) fos.write(outputBytes);
        }
    }

    // Decrypt file
    public static void decryptFile(File inputFile, File outputFile, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[64];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) fos.write(output);
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) fos.write(outputBytes);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== File Encryption/Decryption Utility ===");
        System.out.println("1. Encrypt File");
        System.out.println("2. Decrypt File");
        System.out.print("Choose option: ");
        int choice = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.print("Enter path of input file: ");
        String inputPath = sc.nextLine();
        File inputFile = new File(inputPath);

        System.out.print("Enter path of output file: ");
        String outputPath = sc.nextLine();
        File outputFile = new File(outputPath);

        System.out.print("Enter secret key (any string): ");
        String keyStr = sc.nextLine();

        try {
            SecretKey key = generateKey(keyStr);
            if (choice == 1) {
                encryptFile(inputFile, outputFile, key);
                System.out.println("File encrypted successfully!");
            } else if (choice == 2) {
                decryptFile(inputFile, outputFile, key);
                System.out.println("File decrypted successfully!");
            } else {
                System.out.println("Invalid choice!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        sc.close();
    }
}
