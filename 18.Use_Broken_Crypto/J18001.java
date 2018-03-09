package testbed.unsafe;

import java.security.*;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class J18001 {
    public byte[] encrypt(byte[] msg, Key k) {
	byte[] rslt = null;

	try {
	    
	    Cipher c = Cipher.getInstance("DES");
	    c.init(Cipher.ENCRYPT_MODE, k);
	    rslt = c.update(msg);
	} catch (InvalidKeyException e) {
	    System.err.println("Exception occured!");
	} catch (NoSuchAlgorithmException e) {
	    System.err.println("Exception occured!");
	} catch (NoSuchPaddingException e) {
	    System.err.println("Exception occured!");
	}

	return rslt;
    }
}
