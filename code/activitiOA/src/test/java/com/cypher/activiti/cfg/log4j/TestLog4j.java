package com.cypher.activiti.cfg.log4j;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

@RunWith(JUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc/mvc-dispatcher-servlet.xml") 
public class TestLog4j {

	private static Logger logger = Logger.getLogger(TestLog4j.class);

	@Test
	public void test() {
		logger.debug("我是debug");
		logger.error("我是error");
		logger.info("我是info");
		logger.warn("我是warn");
	}

}
