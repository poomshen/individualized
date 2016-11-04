package kr.or.kosta.service;

import java.io.Reader;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;

//최악의 코드 생산 방식
//Mybatis 권고하는 사항 무시
public class SqlMapClient {
	private static SqlSession session;
	static{
		String resource = "SqlMapConfig.xml";
		try{
			Reader reader = Resources.getResourceAsReader(resource);
		 	SqlSessionFactory Factory = new SqlSessionFactoryBuilder().build(reader);
		 	System.out.println("초기자 설정하기");
		 	session = Factory.openSession();
		}catch(Exception e){
			
		}
	}
	public static SqlSession getSqlSession(){
		System.out.println("sqlsession");
		return session;
	}
	
}
