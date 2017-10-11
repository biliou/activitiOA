package Service;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.alibaba.fastjson.JSON;
import com.cypher.activiti.model.User;
import com.cypher.activiti.service.IUserService;

import log4j.JUnit4ClassRunner;

/**
 * 测试spring整合mybatis
 * 
 * @author bili.ou
 *
 */
@RunWith(JUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/springmvc/spring-mybatis.xml"})
public class TestMyBatis {

	private static Logger logger = Logger.getLogger(TestMyBatis.class);
//	private ApplicationContext ac = null;

	@Autowired
	private IUserService userService = null;

//	@Before
//	public void before() {
//		ac = new ClassPathXmlApplicationContext("/springmvc/spring-mybatis.xml");
//		userService = (IUserService) ac.getBean("userService");
//	}

	@Test
	public void test(){
		User user = userService.getUserInfoById(1);
		System.out.println(JSON.toJSONString(user));
	}
}
