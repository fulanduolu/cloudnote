package testService;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tedu.cloudnote.dao.UserDAO;
import com.tedu.cloudnote.service.UserService;
import com.tedu.cloudnote.util.NoteResult;

public class TestZhuCe extends BaseTest{

	@Test
	public void test1(){
		UserService service=ac.getBean("userService",UserService.class);
	 	NoteResult result=service.addUser("fulan", "fulanduolu", "123456");
	 	System.out.println(result.getMsg());
	}
}
