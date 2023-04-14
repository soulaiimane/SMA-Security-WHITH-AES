package ma.enset;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

public class ClientAgent extends Agent {
    @Override
    protected void setup() {
        String secret= (String) getArguments()[0];
        String message ="hello Soulaimane <3 ";
        try {
            SecretKey secretKey= new SecretKeySpec(secret.getBytes(),"AES");
            Cipher cipher=Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE,secretKey);
            byte[] cryptedMessage = cipher.doFinal(message.getBytes());
            String cryptedEncodedMsg = Base64.getEncoder().encodeToString(cryptedMessage);
            ACLMessage aclmessage=new ACLMessage(ACLMessage.INFORM);
            aclmessage.addReceiver(new AID("Server",AID.ISLOCALNAME));
            aclmessage.setContent(cryptedEncodedMsg);
            send(aclmessage);
            System.out.println("Crypted Message sous forme de Tableu des bites : "+ Arrays.toString(cryptedMessage));
            System.out.println("Encoded Crypted Message sous forme String : "+cryptedEncodedMsg);




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
    }
}
