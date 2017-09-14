package testService;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BaseTest {

	ApplicationContext ac;
	
	@Before    //ÿ�ε���@Test֮ǰ����ִ��
	public void init(){
		String[] conf={"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
		ac=new ClassPathXmlApplicationContext(conf);
	}
	
}
