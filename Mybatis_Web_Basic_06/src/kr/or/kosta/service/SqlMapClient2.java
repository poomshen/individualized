package kr.or.kosta.service;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

//Mybatis 권고하는 좋은 코드

public class SqlMapClient2 {
	//private static SqlSession session;
	private static SqlSessionFactory sqlsessionfactory;
	static{
		String resource = "SqlMapConfig.xml";
		try{
			Reader reader = Resources.getResourceAsReader(resource);
			sqlsessionfactory = new SqlSessionFactoryBuilder().build(reader);
		 	//session = Factory.openSession();
		}catch(Exception e){
			
		}
	}
	public static SqlSessionFactory getSqlSession(){
		return sqlsessionfactory;
	}
	
}



/*public void insert(){
	SqlSession session = SqlMapClient2.getSqlSession().openSession();
	try {
	BlogMapper mapper = session.getMapper(BlogMapper.class);
	// do work
	} finally {
	session.close();
	}

}
*/

