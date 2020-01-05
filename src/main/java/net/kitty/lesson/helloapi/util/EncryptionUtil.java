package net.kitty.lesson.helloapi.util;

import java.nio.charset.Charset;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

/**
 * 
* @ClassName: EncryptionUtil
* @Description: 常见加密解密算法解析
* @author Kitty
* @date 2019年7月10日 
* MD5,SHA1 签名(网上有一些破解，但只能破解简单的组成，复杂网站会提示收费，需要解密很长时间)
 */
public class EncryptionUtil {
 
  public static void main(String[] args) {
    // MD5加密解密: Hash签名算法，Javamall采用的是MD5加密，解密的方式
    System.out.println("md5(\"123456\")" + md5("123456"));
    System.out.println("md5(\"admin\")" + md5("admin"));


    // Sha1加密解密：Hash签名算法
    System.out.println("sha1(\"123456\")" + sha1("123456"));
    System.out.println("sha1(\"admin\")" + sha1("admin"));

    // Base64编码：纯编码，提供编码和解码方法
    System.out.println("base64Encode(\"123456\")" + base64Encode("123456"));
    System.out.println("base64Decode(\"admin\")" + base64Decode("admin"));
  
    // AES加密解密：对称加密解密，需具备公钥，公钥有长度限制
    String aesKey = "1234567891234567";
    System.out.println(aesKey + "  " + aesKey.length());
    System.out.println(aesEncode("example", aesKey));
    System.out.println(aesDecode("IDcqXMG9R6tp5Vqi1RO92A==", aesKey));
  }

  public static String md5(String value) {
    Hasher hasher = Hashing.md5().newHasher();
    hasher.putString(value, Charset.forName("UTF-8"));
    return hasher.hash().toString();
  }

  public static String sha1(String value) {
    Hasher hasher = Hashing.sha1().newHasher();
    hasher.putString(value, Charset.forName("UTF-8"));
    return hasher.hash().toString();
  }

  public static String sha256(String value) {
    Hasher hasher = Hashing.sha256().newHasher();
    hasher.putString(value, Charset.forName("UTF-8"));
    return hasher.hash().toString();
  }

  public static String base64Encode(String value) {
    return Base64.encodeBase64String(value.getBytes());
  }

  public static String base64Decode(String value) {
    return new String(Base64.decodeBase64(value));
  }

  //也是一种Hash加密算法
  public static String bcrypt(String value) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.encode(value);
  }

  //AES对称加密解密需要输入公钥
  public static String aesEncode(String input, String key) {
    byte[] crypted = null;
    try {
      SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, skey);
      crypted = cipher.doFinal(input.getBytes());
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    return new String(Base64.encodeBase64(crypted));
  }

  public static String aesDecode(String input, String key) {
    byte[] output = null;
    try {
      SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, skey);
      output = cipher.doFinal(Base64.decodeBase64(input));
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    return new String(output);
  }
}
