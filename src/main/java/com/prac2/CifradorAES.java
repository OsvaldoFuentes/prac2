package com.prac2;


import com.prac2.Constantes.ModoOperar;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.management.openmbean.InvalidKeyException;


public class CifradorAES {

    
   
    private SecretKeySpec crearClave(String clave) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] key = clave.getBytes("UTF-8");  
        System.out.println("secretkey: "+key.length);
        key = Arrays.copyOf(key, 16); //se hace padding de la llave
        System.out.println("secretkey: "+key.length);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        return secretKey;
    }    


    public byte[] cifrarBytes(ModoOperar modo, byte [] datos, String llave, byte [] iv) 
            throws UnsupportedEncodingException, 
            NoSuchAlgorithmException, InvalidKeyException, 
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException,
            java.security.InvalidKeyException, InvalidAlgorithmParameterException {
        
        SecretKeySpec secretKey = crearClave(llave);

        if (modo == ModoOperar.ECB){
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); 
            cipher.init(Cipher.ENCRYPT_MODE, secretKey); 
         
            return cipher.doFinal(datos);
        } 
        else if (modo == ModoOperar.CBC){
            IvParameterSpec ivParameter = new IvParameterSpec(iv);
            
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameter);
            
            return cipher.doFinal(datos);
        }
        return null;
    }        

    
    public byte[] descifrarBytes(ModoOperar modo, byte [] datos, String llave, byte [] iv) 
            throws UnsupportedEncodingException, 
            NoSuchAlgorithmException, InvalidKeyException, 
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException,
            java.security.InvalidKeyException, InvalidAlgorithmParameterException {
        
        SecretKeySpec secretKey = crearClave(llave);

        if (modo == ModoOperar.ECB){
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); 
            cipher.init(Cipher.DECRYPT_MODE, secretKey); 
     
            return cipher.doFinal(datos); 
        } 
        else if (modo == ModoOperar.CBC){
            IvParameterSpec ivParameter = new IvParameterSpec(iv);
            
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameter);
            
            return cipher.doFinal(datos);
        }
        

        return null;
    }     
    

}
