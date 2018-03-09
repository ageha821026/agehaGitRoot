import java.security.*;
import java.sql.*;
import javax.crypto.*;

 
public class J24001 {
    String getpassword()  {
        return "secret";
    }

    void bad(String url, String usr)  {
        Connection conn = null;
        try {
            String encryptedPwd = getpassword();
            
            byte[] privateKey = {'6','8','a','f','4','0','4','b','5','1','3','0','7'};
			
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("RSA");
            
            javax.crypto.SecretKey myDesKey = new javax.crypto.spec.SecretKeySpec(privateKey, "DES");

            cipher.init(javax.crypto.Cipher.DECRYPT_MODE,  myDesKey);
            
            byte[] plainTextPwdBytes = cipher.doFinal(encryptedPwd.getBytes());
            
            conn = java.sql.DriverManager.getConnection(url, usr, new String(plainTextPwdBytes));
        } catch (InvalidKeyException e) {
            System.err.println("Error");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error");
        } catch (NoSuchPaddingException e) {
            System.err.println("Error");
        } catch (IllegalBlockSizeException e) {
            System.err.println("Error");
        } catch (BadPaddingException e) {
            System.err.println("Error");
        } catch (SQLException e) {
            System.err.println("Error");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch(SQLException se) {
                System.err.println("SQLException");
            }
        }
    }
}
