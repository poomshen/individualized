package ncontrollers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;

import vo.Member;
import dao.MemberDao;

@Controller
@RequestMapping("/joinus/") //   /joinus/join.htm
public class JoinController {
  
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private View jsonview;
	
	@RequestMapping(value="join.htm" , method=RequestMethod.GET)
	public String join(){
		System.out.println("회원가입 페이지 요청");
		//return "join.jsp";
		//Tiles
		return "joinus.join";
	}
	
	@RequestMapping(value="join.htm" , method=RequestMethod.POST)
	public String join(Member member) throws ClassNotFoundException, SQLException{
		//가입처리 (DAO 단)
		System.out.println("회원가입");
		System.out.println(member.toString());
		memberDao.insert(member);
		//return "redirect:../index.htm"; //수정 요망(주의 사항)
		return "redirect:/index.htm";
	}
	@RequestMapping(value="login.htm",method=RequestMethod.GET)
	public String loing(){
		return "joinus.login";
	}
	
	@RequestMapping(value="idcheck.htm")
	public View idCheck(String userid , ModelMap map){
		System.out.println(userid);
		String data="";
		if(userid.equals("kglim")){
			data ="success";
		}else{
			data = "fail";
		}
		map.addAttribute("userid", data);
		return jsonview;
	}
}





