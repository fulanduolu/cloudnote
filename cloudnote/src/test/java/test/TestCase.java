package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tedu.cloudnote.dao.UserDAO;
import com.tedu.cloudnote.entity.User;

public class TestCase {

	@Test
	public void test1(){
		ApplicationContext ac =new ClassPathXmlApplicationContext("conf/*.xml");
		UserDAO dao=ac.getBean("userDAO",UserDAO.class);
		User user=dao.findByName("pc");
		System.out.println(user);
	}
	
	@Test
	public void test2(){
		ApplicationContext ac =new ClassPathXmlApplicationContext("conf/spring-mvc.xml");
		Date date=ac.getBean("date",Date.class);
		System.out.println(date);
	}
	
	@Test
	public void test3() throws SQLException{
		//����Spring����
		String[] conf={"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
		ApplicationContext ac=new ClassPathXmlApplicationContext(conf);
		//��ȡDataSource
		DataSource ds=ac.getBean("dbcp",DataSource.class);
		Connection conn=ds.getConnection();
		System.out.println(conn);
		conn.close();
		SqlSessionFactory ssf=ac.getBean("ssf",SqlSessionFactory.class);
		System.out.println(ssf.openSession());
		UserDAO dao=ac.getBean("userDAO",UserDAO.class);
		User user=dao.findByName("demo1");
		if(user==null){
			System.out.println("û�м�¼");
		}else{
			System.out.println(user.getCn_user_password());
		}
	}
	
}
