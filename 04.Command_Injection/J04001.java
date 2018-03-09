package testbed.unsafe;

import java.util.*;
import java.io.*;

public class J04001 {
    public void f() throws IOException {
        Properties props = new Properties();
        String fileName = "file_list";
        FileInputStream in = new FileInputStream(fileName);
        props.load(in);

         
        String version = props.getProperty("dir_type");
        String cmd = new String("cmd.exe /K \"rmanDB.bat && cleanup.bat\"");
		
        
        Runtime.getRuntime().exec(cmd + " c:\\prog_cmd\\" + version);
    }
}
