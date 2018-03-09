package testbed.unsafe;

public class J32001 {
    public int factorial(int n) {
	
	return n * factorial(n - 1);
    }
}
