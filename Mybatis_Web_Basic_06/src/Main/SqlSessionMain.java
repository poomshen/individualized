package Main;

import org.apache.ibatis.session.SqlSession;

import kr.or.kosta.service.SqlMapClient;

public class SqlSessionMain {

	public static void main(String[] args) {
		SqlSession sqlsession= SqlMapClient.getSqlSession();
		System.out.println(sqlsession.toString());
		sqlsession.selectList("Emp.getusers");
		
		//연결 닫기 
		//문제는 자원 보호를 위해서 close 하게 되면
		//DB 연결 안됨
		sqlsession.close();
		
		
		SqlSession sqlsession2= SqlMapClient.getSqlSession();
		System.out.println(sqlsession2.toString());
		sqlsession2.selectList("Emp.getusers");
	}

}
