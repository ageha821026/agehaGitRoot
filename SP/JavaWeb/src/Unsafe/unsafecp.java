package Unsafe;
//검출 11821	클래스 명명 규칙
/**
 * @author ageha
 *
 */
//검출 - 11739	Abstract 메소드가 없는 Abstract 클래스
public abstract class unsafecp {

	public void u11750() {
		
	}
//    public abstract void couldBeAbstract();
    //미검출 - 11691	빈 (Empty) Initializer
    //검출 - 11701	빈 (Empty) Static 블럭 사용 금지 
	static{
		
	}
	//검출 - 11691	빈 (Empty) Initializer
	{
		
	}
}
