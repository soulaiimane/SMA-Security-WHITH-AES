package ma.enset;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ServerAgent extends Agent {
    @Override
    protected void setup() {
        String secret=(String) getArguments()[0];


        addBehaviour(new CyclicBehaviour() {
        @Override
        public void action() {
            ACLMessage receive=receive();
            if (receive!=null){
                    String cryptedEncodedMsg=receive.getContent();

                    byte[] cryptedMessage= Base64.getDecoder().decode(cryptedEncodedMsg);
                try {
                    SecretKey secretKey=new SecretKeySpec(secret.getBytes(),"AES");
                    Cipher cipher=Cipher.getInstance("AES");
                    cipher.init(Cipher.DECRYPT_MODE,secretKey);
                    byte[] decriptMessage = cipher.doFinal(cryptedMessage);
                    System.out.println("le message décripter est :" + new String(decriptMessage));


                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchPaddingException e) {
                    throw new RuntimeException(e);
                } catch (InvalidKeyException e) {
                    throw new RuntimeException(e);
                } catch (IllegalBlockSizeException e) {
                    throw new RuntimeException(e);
                } catch (BadPaddingException e) {
                    throw new RuntimeException(e);
                }

            }else {block();}
        }
    });
    }
}
