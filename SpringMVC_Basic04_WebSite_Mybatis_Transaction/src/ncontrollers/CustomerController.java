package ncontrollers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import Service.CustomerService;
import vo.Notice;
import dao.NoticeDao;


// 폴더 경로가 긴 경우
// /customer/notice.htm => notice.htm
// /customer/noticeDetail.htm
@Controller
@RequestMapping("/customer/")
public class CustomerController {
 
	 @Autowired
	 private CustomerService customerservice;
	 //글목록보기
	@RequestMapping("notice.htm")
	 public String notices(String pg , String f , String q , Model model) throws ClassNotFoundException , SQLException {
		List<Notice> list = customerservice.notices(pg, f, q);
		model.addAttribute("list", list); 
		return "customer.notice";
	}
     //글상세보기
	 @RequestMapping("noticeDetail.htm")
	 public String noticeDetail(String seq , Model model ) throws ClassNotFoundException, SQLException{
		 Notice notice = customerservice.noticeDetail(seq);
		 model.addAttribute("notice", notice);
   	     return "customer.noticeDetail";
	 }
	 // 글등록 화면 처리
	 @RequestMapping(value = "noticeReg.htm", method = RequestMethod.GET)
	 public String noticeReg() {
		 return "customer.noticeReg";
	 }
	 // 글등록 처리(실제 글등록 처리)
	 @RequestMapping(value = "noticeReg.htm", method = RequestMethod.POST)
	 public String noticeReg(Notice n, HttpServletRequest request)
	   throws IOException, ClassNotFoundException, SQLException {
		  
		  String url = "redirect:notice.htm";
		  try{
			  url = customerservice.noticeReg(n, request);
		  }catch(Exception e){
			  System.out.println(e.getMessage());
		  }
		  return url;
	 }
	 // 글삭제하기
	 @RequestMapping("noticeDel.htm")
	 public String noticeDel(String seq) throws ClassNotFoundException,
	   SQLException {
		 String url = customerservice.noticeDel(seq);
		 return url; 
	 }
	 //글수정하기 (두가지 처리 : 화면(select) , 처리(update))
	 @RequestMapping(value = "noticeEdit.htm", method = RequestMethod.GET)
	 public String noticeEdit(String seq, Model model)
	   throws ClassNotFoundException, SQLException {
		 
		 Notice notice = customerservice.noticeEdit(seq);
		 model.addAttribute("notice", notice);
	  return "customer.noticeEdit";
	 }
	 //게시판 실제 수정처리
	 @RequestMapping(value = "noticeEdit.htm", method = RequestMethod.POST)
	 public String noticeEdit(Notice n ,HttpServletRequest request) throws ClassNotFoundException,
	   SQLException, IOException {

		 String url = customerservice.noticeEdit(n, request);
	     return url;
	 }
     //게시판 파일 다운로드	 
	 @RequestMapping("download.htm")
	 public void download(String p, String f, HttpServletRequest request,
	   HttpServletResponse response) throws IOException {
           customerservice.download(p, f, request, response);
	
	 }

}
