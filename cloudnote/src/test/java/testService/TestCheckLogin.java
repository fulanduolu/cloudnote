package testService;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tedu.cloudnote.service.UserService;
import com.tedu.cloudnote.util.NoteResult;

public class TestCheckLogin {

	@Test
	public void test1(){
		String[] conf={"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
		ApplicationContext ac=new ClassPathXmlApplicationContext(conf);
		UserService service=ac.getBean("userService",UserService.class);
		NoteResult result=service.checkLogin("demo", "123");
		System.out.println(result.getStatus());
		System.out.println(result.getMsg());
		System.out.println(result.getData());
	}
	
}
