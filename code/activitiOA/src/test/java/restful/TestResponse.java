package restful;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.cypher.activiti.core.restful.Response;

public class TestResponse {
	/**
	 * 输出：{"meta":{"message":"success","success":true}}
	 */
	@Test
	public void TestSuccessFunc() {
		System.out.println(JSON.toJSONString((new Response().success())));
	}
	
	/**
	 * 输出：{"data":"aaa","meta":{"message":"success","success":true}}
	 */
	@Test
	public void TestSuccessFuncWithData() {
		System.out.println(JSON.toJSONString(new Response().success("aaa")));
	}
	
	/**
	 * 输出：{"meta":{"message":"success","success":true}}
	 */
	@Test
	public void TestFailureFunc() {
		System.out.println(JSON.toJSONString((new Response().failure())));
	}
	
	/**
	 * 输出：{"meta":{"message":"aaa","success":false}}
	 */
	@Test
	public void TestFailureFuncWithData() {
		Response aResponse = new Response().failure("aaa");
		System.out.println(JSON.toJSONString(aResponse));
	}
	
}
