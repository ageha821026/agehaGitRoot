package testbed.unsafe;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class J21001 {
    public void target() throws NoSuchAlgorithmException{
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

        
        keyGen.initialize(512);
        KeyPair myKeys = keyGen.generateKeyPair();
		
        System.out.println(myKeys.toString());
    }
}
