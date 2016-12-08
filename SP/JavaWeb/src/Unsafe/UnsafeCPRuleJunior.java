package Unsafe;

import java.text.ParseException;
import java.util.logging.Logger;

public class UnsafeCPRuleJunior extends UnsafeCpRule{
	private String Violate_yn;
	public UnsafeCPRuleJunior(){
		//검출 - 11773	오버라이드 메소드를 호출하는 생성자
		super();
		Violate_yn = "true";
	}
	public String u103(int flag){
		
		return Violate_yn;
		
	}
	//검출 - 11697	불필요한 오버라이딩 메소드
	public boolean compareClass(Class a, Class aa) throws ParseException{
		return  super.compareClass(a, aa);
		
	}
	
}
