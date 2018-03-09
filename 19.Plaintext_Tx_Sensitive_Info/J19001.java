import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class J19001 {
    String getPassword()  {
        return "secret";
    }
	
    public void bad()  {
        Socket socket = null;
    	try {
            Socket socket = new Socket("taranis", 4444);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			
            String password = getPassword();
            out.write(password);
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundExcetion");
        } catch (UnknownHostException e) {
            System.err.println("UnkownHostExcetion");
        } catch (IOException e) {
            System.err.println("IOExcetion");
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (IOException ie) {
                System.err.println("Socket close error");
            }
        }
    }
}
