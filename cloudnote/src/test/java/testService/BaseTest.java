package testService;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BaseTest {

	ApplicationContext ac;
	
	@Before    //每次调用@Test之前调用执行
	public void init(){
		String[] conf={"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
		ac=new ClassPathXmlApplicationContext(conf);
	}
	
}
