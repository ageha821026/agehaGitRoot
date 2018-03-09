package testbed.unsafe;
import java.io.IOException;

public class J40001 {
    public void f(){
        try{
            g();
        } catch (IOException e){
             
            System.err.println(e.getMessage());
        }
    }
	
    private void g() throws IOException {
    }
}
