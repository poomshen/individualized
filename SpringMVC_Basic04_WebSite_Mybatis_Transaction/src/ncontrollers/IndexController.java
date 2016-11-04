package ncontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
 //index.htm 요청 들어오면 View 페이지 제공
	
	@RequestMapping("index.htm")
	public String index(){
		
		//이전
		//return "index.jsp";
		//Tiles 적용
		return "home.index";
	}
}
