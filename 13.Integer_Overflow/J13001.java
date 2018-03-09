//
// 
//

import java.io.*;
import java.util.*;

public class J13001 {
    public MyClass f190(Properties prop, MyClass[] classes) throws NumberFormatException {
	String index = prop.getProperty("index");
	int i = new Integer(index).intValue();
	
	return classes[i];
    }

}

class MyClass {
}
