package com.cypher.activiti.cfg.mybatis;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * mybatis 逆向工程
 */
public class Generate {
	public void generator() throws Exception {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File("src/main/resources/mybatis/generator-cfg.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
	}

	@Ignore
	public static void main(String[] args) throws Exception {
		try {
			Generate generatorSqlmap = new Generate();
			generatorSqlmap.generator();
			System.out.println("自动创建实体类完毕");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
