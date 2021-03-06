package com.cmpay.common.security.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

public class baseObject {


    public static byte[] des_decrypt(byte[] data,byte[] key) throws Exception{

    	Key k=toKey(key);
    	Cipher cipher=Cipher.getInstance(Constants.DES_CIPHER_ALGORITHM);
    	cipher.init(Cipher.DECRYPT_MODE, k);

		return cipher.doFinal(data);

    }


   public static byte[] des_encrypt(byte[] data,byte[] key) throws Exception{

	   Key k=toKey(key);
	   Cipher cipher=Cipher.getInstance(Constants.DES_CIPHER_ALGORITHM);
	   cipher.init(Cipher.ENCRYPT_MODE, k);

	   return cipher.doFinal(data);

   }

   /**
    * 转换密钥
    * @param key 二进制密钥
    * @return
    * @throws Exception
    */
   private static Key toKey(byte[] key) throws Exception{
	 //实例化DES密钥材料
   	DESedeKeySpec dks=new DESedeKeySpec(key);
   	SecretKeyFactory keyFactory=SecretKeyFactory.getInstance(Constants.DES_KEY_ALGORITHM);

   	return keyFactory.generateSecret(dks);
   }



   public static byte[] rsa_sign(byte[] data,byte[] privateKey) throws Exception{
	   PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(privateKey);
	   KeyFactory keyFactory=KeyFactory.getInstance(Constants.RSA_KEY_ALGORITHM);
	   PrivateKey priKey=keyFactory.generatePrivate(pkcs8KeySpec);
	   Signature signature=Signature.getInstance(Constants.SIGNATURE_ALGORITHM);
	   signature.initSign(priKey);
	   signature.update(data);
	    return signature.sign();

   }



     public static boolean rsa_verify(byte[] data,byte[] publicKey,byte[] sign) throws Exception{
     	X509EncodedKeySpec x509keySpec=new X509EncodedKeySpec(publicKey);
     	KeyFactory keyFactory=KeyFactory.getInstance(Constants.RSA_KEY_ALGORITHM);
     	PublicKey pubKey=keyFactory.generatePublic(x509keySpec);
     	 Signature signature=Signature.getInstance(Constants.SIGNATURE_ALGORITHM);
     	 signature.initVerify(pubKey);
     	 signature.update(data);
 		return signature.verify(sign);
     }



     public static byte[] encodeBase64(byte[] data) throws Exception{
 		return Base64.encodeBase64Chunked(data);

     }

     public static byte[] decodeBase64(byte[] data) throws Exception{
     	return Base64.decodeBase64(new String(data).getBytes());
     }


}
