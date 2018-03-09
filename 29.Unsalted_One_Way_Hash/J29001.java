import java.security.*;

public class J29001 {
    public byte[] getHash(String password) throws NoSuchAlgorithmException, java.io.UnsupportedEncodingException {
	MessageDigest digest = MessageDigest.getInstance("SHA-256");
	digest.reset();
	return digest.digest(password.getBytes("UTF-8"));
    }
}
