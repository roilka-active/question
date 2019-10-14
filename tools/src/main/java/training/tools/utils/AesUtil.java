package training.tools.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class AesUtil {
    // 密匙
    private static final String KEY = "dbe81bc71eb04a44985a1b660127f395";
    // 偏移量
    private static final String OFFSET = "1eb04a44985a1b66";
    // 编码
    private static final String ENCODING = "UTF-8";
    //算法
    private static final String ALGORITHM = "AES";
    // 默认的加密算法
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    static {
        try {
            Class<?> clazz = Class.forName("javax.crypto.JceSecurity");
            Field nameField = clazz.getDeclaredField("isRestricted");

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(nameField, nameField.getModifiers() & ~Modifier.FINAL);

            nameField.setAccessible(true);
            nameField.set(null, Boolean.FALSE);
        } catch (Exception ex) {
        }
    }
    /**
     * 加密
     *
     * @param data
     * @return String
     * @throws Exception
     * @author tyg
     * @date 2018年6月28日下午2:50:35
     */
    public static String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes(ENCODING), ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(OFFSET.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(data.getBytes(ENCODING));
        return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码。
    }

    /**
     * 解密
     *
     * @param data
     * @return String
     * @throws Exception
     * @author tyg
     * @date 2018年6月28日下午2:50:43
     */
    public static String decrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(OFFSET.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] buffer = new BASE64Decoder().decodeBuffer(data);
        byte[] encrypted = cipher.doFinal(buffer);
        return new String(encrypted, ENCODING);//此处使用BASE64做转码。
    }

    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
        String cKey = "1234567890123456";
        // 需要加密的字串
        String cSrc = "www.gowhere.so";
        String cPassword = "wH7cfJSwlR1zjQKgos4DEMKiXs1yE/y7gVoJjK2zX4vS8P6fle64GArvJMKUpIS2p9LSOZMhujVcVd71PKGSGWV2tKIHHD9cL9TSOMylnVKAUgfKgbQOqRtN50CnzH78";
        System.out.println(cSrc);
        // 加密
        //String enString = AesUtil.Encrypt(cSrc, cKey);
        //System.out.println("加密后的字串是：" + enString);

        // 解密
        String DeString = AesUtil.decrypt(cPassword);
        System.out.println("解密后的字串是：" + DeString);
    }
}
