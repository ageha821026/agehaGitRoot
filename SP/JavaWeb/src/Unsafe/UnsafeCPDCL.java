package Unsafe;
//검출 - 11857	Java Lang Error의 Extend 금지 
public final class UnsafeCPDCL extends Error {
	private static UnsafeCPDCL DCL;
	//검출 - 11744	Final 클래스에서 Protected 필드 사용 금지
	protected String aha;
	
	public UnsafeCPDCL(){
		
	}
	//검출 - 11710	불필요한 Final Modifier 사용 금지 
	public final static UnsafeCPDCL getInstance(){
		
		if(DCL == null){
			synchronized (UnsafeCPDCL.class) {
				//검출 - 11690	더블 체크 락킹
				if(DCL==null){
					DCL  = new UnsafeCPDCL();
				}
			}
		}
		return DCL;
	}
	public Object clone(){
		Object oj = new UnsafeCPDCL();
		return oj;
	}
	
}

