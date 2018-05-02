package com.androiddesdecero.encriptacionaesandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    private String mensajeAEncriptar = "Apuntate a los cursos de Udemy de AndroidDesdeCero - link abajo";

    private static final String AES = "AES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            encriptar(mensajeAEncriptar);
        } catch (Exception e){

        }
    }

    public static String encriptar(String mensajeAEncriptar) throws Exception{
        //Instancia del Generador de llaves tipo AES
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        //Inicializamos el generador de llaves especificando el tamaño. Como hemos dicho 128bytes
        keyGenerator.init(128);
        //Instanciamos una llave secreta
        SecretKey secretKey = keyGenerator.generateKey();
        //codificamos la llave en bytes
        byte[] bytesSecretKey = secretKey.getEncoded();
        //Construimos una clave secreta indicandole que es de tipo AES
        SecretKeySpec secretKeySpec = new SecretKeySpec(bytesSecretKey, AES);
        //Instanciamos un objeto de cifrado de tipo AES
        Cipher cipher = Cipher.getInstance(AES);
        //Inicializamos el sistema de cifrado en modo Encriptacion con nuestra clave que hemos creado antes
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        //Procedemos a Encriptar el mensaje
        byte[] mensajeEncritado = cipher.doFinal(mensajeAEncriptar.getBytes());
        Log.d("TAG", new String(mensajeEncritado));


        //Iniciamos el sistema de cifrado en modos Desencriptacion con nuestra clave
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        //Obtenemos el array de bytes del mensaje desencriptado
        byte[] mensajeDesEncriptado = cipher.doFinal(mensajeEncritado);
        Log.d("TAG", new String(mensajeDesEncriptado));
        return new String(mensajeDesEncriptado);
    }
}
