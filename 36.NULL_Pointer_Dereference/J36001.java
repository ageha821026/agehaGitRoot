package testbed.unsafe;

public class J36001 {
    public void bad(boolean b) {
	
	String cmd = null;
	
	if (b) cmd = System.getProperty("cmd");

		
	cmd = cmd.trim();
	System.out.println(cmd);
    }
}
