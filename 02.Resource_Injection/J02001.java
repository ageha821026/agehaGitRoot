 package testbed.unsafe;

import java.util.*;
import java.io.*;
import java.net.*;

public class J02001 {
    public void f() throws IOException {
        int def = 1000;
        ServerSocket serverSocket;
        Properties props = new Properties();
        String fileName = "file_list";
        FileInputStream in = new FileInputStream(fileName);
        props.load(in);

        // 
        String service = props.getProperty("Service No");

        int port = Integer.parseInt(service);
		
        // 
        if (port != 0)
            serverSocket = new ServerSocket(port + 3000);
        else
            serverSocket = new ServerSocket(def + 3000);

        serverSocket.close();
    }
}
