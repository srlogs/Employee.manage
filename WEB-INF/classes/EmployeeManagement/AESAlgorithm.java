package EmployeeManagement;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class  AESAlgorithm {

    public static SecretKey key;
    public static byte[] decodedkey;
    public static String aeskeygenerate()
    {
      try
      {
        key = KeyGenerator.getInstance("AES").generateKey();
        return Base64.getEncoder().encodeToString(key.getEncoded());
      }
      catch(Exception e)
      {
        e.toString();
      }
      return null;
    }
    public static String deskeygenerate()
    {
      try
      {
        key = KeyGenerator.getInstance("DES").generateKey();
        return Base64.getEncoder().encodeToString(key.getEncoded());
      }
      catch(Exception e)
      {
        e.toString();
      }
      return null;
    }
    public static String Blowfishkeygenerate()
    {
      try
      {
        key = KeyGenerator.getInstance("Blowfish").generateKey();
        return Base64.getEncoder().encodeToString(key.getEncoded());
      }
      catch(Exception e)
      {
        e.toString();
      }
      return null;
    }
    public static String encrypt_aes(String strToEncrypt, String keys)
    {
        try
        {
            decodedkey = Base64.getDecoder().decode(keys);
            key = new SecretKeySpec(decodedkey, 0, decodedkey.length, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt_aes(String strToDecrypt, String keys)
    {
        try
        {
            decodedkey = Base64.getDecoder().decode(keys);
            key = new SecretKeySpec(decodedkey, 0, decodedkey.length, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
    public static String encrypt_des(String strToEncrypt, String keys)
    {
      try
      {
        decodedkey = Base64.getDecoder().decode(keys);
        key = new SecretKeySpec(decodedkey, 0, decodedkey.length, "DES");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return new String(BASE64EncoderStream.encode(cipher.doFinal(strToEncrypt.getBytes("UTF-8"))));
      }
      catch(Exception e)
      {
        System.out.println("Error while encrypting: "+ e.toString());
      }
      return null;
    }
    public static String decrypt_des(String strToDecrypt, String keys)
    {
      try
      {
        decodedkey = Base64.getDecoder().decode(keys);
        key = new SecretKeySpec(decodedkey, 0, decodedkey.length, "DES");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
      }
      catch(Exception e)
      {
        System.out.println("Error while encrypting: "+ e.toString());
      }
      return null;
    }
    public static String encrypt_Blowfish(String strToEncrypt, String  keys)
    {
      try
      {
        decodedkey = Base64.getDecoder().decode(keys);
        key = new SecretKeySpec(decodedkey, 0, decodedkey.length, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return new String(BASE64EncoderStream.encode(cipher.doFinal(strToEncrypt.getBytes("UTF-8"))));
      }
      catch(Exception e)
      {
        System.out.println("Error while encrypting: "+ e.toString());
      }
      return null;
    }
    public static String decrypt_Blowfish(String strToDecrypt, String keys)
    {
      try
      {
        decodedkey = Base64.getDecoder().decode(keys);
        key = new SecretKeySpec(decodedkey, 0, decodedkey.length, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
      }
      catch(Exception e)
      {
        System.out.println("Error while decrypting: "+ e.toString());
      }
      return null;
    }
}
