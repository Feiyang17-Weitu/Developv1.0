package safe17.weitudevelop.tool;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;



public class RsaTool {
    /*生成密钥对保存在公钥保存在publickeyfile文件中，私钥保存在privatekeyfile中
     *
     * 首次运行程序先生成密钥对保存在本地一共后续加解密
     * */
    public static void makekeyfile(String publickeyfile,String privatekeyfile)
            throws NoSuchAlgorithmException,FileNotFoundException,IOException{

        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(512);
        //生成密钥对保存在keypair中
        KeyPair keyPair = keyPairGen.generateKeyPair();

        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();


        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(privatekeyfile));
        oos.writeObject(privateKey);
        oos.flush();oos.close();


        oos = new ObjectOutputStream(new FileOutputStream(publickeyfile));
        oos.writeObject(publicKey);
        oos.flush();
        oos.close();

        System.out.println("make key file ok!");
    }
    /**
     *
     * @param k
     * @param data
     * @param encrypt
     *            1 加密 0解密
     * @return
     * @throws NoSuchPaddingException
     * @throws Exception
     */
    public static byte[] handleData(Key k,byte[] data,int encrypt)
            throws Exception{
        if(k != null){
            Cipher cipher = Cipher.getInstance("RSA");

            if(encrypt == 1){
                cipher.init(Cipher.ENCRYPT_MODE, k);
                byte[] resultBytes = cipher.doFinal(data);
                return resultBytes;

            }else if (encrypt == 0) {
                cipher.init(Cipher.DECRYPT_MODE, k);
                byte[] resultBytes = cipher.doFinal(data);
                return resultBytes;
            }else {
                System.out.println("参数只能为：1加密   0解密");
            }
        }
        return null;

    }
}
