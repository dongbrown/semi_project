package main.com.web.common;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SessionTemplate {
	
	public static SqlSession getSession() {
		SqlSession session = null;
		String fileName = "mybatis-config.xml";
		try (InputStream is = Resources.getResourceAsStream(fileName);){
			session = new SqlSessionFactoryBuilder().build(is) //매개변수 추가로 없으면 default설정된 환경으로 접속("BS")
					.openSession(false); 
		}catch(IOException e) {
			e.printStackTrace();
		}
		return session;
	}

}
