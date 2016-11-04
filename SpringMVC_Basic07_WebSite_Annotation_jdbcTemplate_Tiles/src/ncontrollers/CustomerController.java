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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import vo.Notice;
import dao.NoticeDao;


// 폴더 경로가 긴 경우
// /customer/notice.htm => notice.htm
// /customer/noticeDetail.htm
@Controller
@RequestMapping("/customer/")
public class CustomerController {
 
	@Autowired
	private NoticeDao noticeDao;
	
	//글목록보기
	@RequestMapping("notice.htm") //    /customer/notice.htm
	public String notices(String pg , String f , String q , Model model) throws ClassNotFoundException , SQLException {
	
		 //************************************************
		 //1단계(일반적인 방법)
		 //HttpServletRequest req
		 //String _page = req.getParameter("pg");
		 
		 //2단계(Spring 방법) 가장 많이 (기본값, 강제사항)
		 //@RequestParam(value="pg" , requried=true) String pg
		 
		 //3단계 
		 //String pg , String f , String q
		 //jsp?pg=100&f=10&q=1
		 //전제조건 단 parameter [넘어오는 변수] 과 [함수안에 변수명]이 같다면
		 //자동 매핑
		 //**************************************************

		//게시판 기본 설정(기본값 처리)/////////////
		int page = 1;
		String field = "TITLE";
		String query ="%%";
		//////////////////////////////////////
		if(pg != null && pg.equals("")){
			page = Integer.parseInt(pg);
		}
		if(f != null && f.equals("")){
			field = f;
		}
		if(q != null && q.equals("")){
			query = q;
		}
		
		System.out.println(page + " / " + field + " / " + query);
		
		List<Notice> list= noticeDao.getNotices(page, field, query);
		
		model.addAttribute("list", list); //자동 forward
		                                  //단 notices(Model model) 함수의 parameter
		
		//return "notice.jsp";
		
		//Tiles
		return "customer.notice";
		
		//VIEW 단에서의 처리 (JSTL & EL)
		//<c:forEach items="${list}" var="n">
			 //<tr>
				//<td>${n.seq}</td>
				//<td>${n.title}</td>
				//<td>${n.writer}</td>
			//</tr>
		//<c:forEach>
		
		//POINT
		//함수의 리턴 타입이 public ModelAndView add(){}
		//함수내부에서 
		//ModelAndView mv = new ModelAndView();
		//mv.addObject("list",list); //view 객체설정
		//mv.setViewName("notice.jsp"); //viewpage 설정
		
		//현재
		//public String notices(){}
		//리턴 타입 String  >> return "notice.jsp" //view이름 문자열로
		//객체를 넘기는 방법 : public String notices(Model model){ }
		//model.addAttribute("list",list) >> view 단에 사용할 객체 생성 가능
	    //mv.addObject("list",list); 역활을  model.addAttribute("list",list)
	}

    //글상세보기
	 @RequestMapping("noticeDetail.htm")
	 public String noticeDetail(String seq , Model model ) throws ClassNotFoundException, SQLException{
		 
		 Notice notice = noticeDao.getNotice(seq);
		 model.addAttribute("notice", notice);
		 //return "noticeDetail.jsp";
		 
		 //Tiles
		  return "customer.noticeDetail";
		 //View
		 //${notice.title}
	 }

	 // 글등록 화면 처리
	 @RequestMapping(value = "noticeReg.htm", method = RequestMethod.GET)
	 public String noticeReg() {
	  System.out.println("글쓰기 매핑");
	  //return "noticeReg.jsp"; // view이름
	  //Tiles
	  return "customer.noticeReg";
	 }

	 // 글등록 처리(실제 글등록 처리)
	 @RequestMapping(value = "noticeReg.htm", method = RequestMethod.POST)
	 public String noticeReg(Notice n, HttpServletRequest request)
	   throws IOException, ClassNotFoundException, SQLException {

		    System.out.println("실제 글 등록 처리"); 
		    System.out.println("n : " + n.getTitle()); 
		    System.out.println("n : " + n.getContent());
		    System.out.println("n : " + n.getFiles().get(0).getName()); //첨부 파일(CommonsMultipartFile > file)
		    System.out.println("n : " + n.getFiles().get(1).getName()); 
		    
		  // 추후 다중 파일 업로드
		  // <input type="file" name="files[0]"
		  // <input type="file" name="files[1]"
		  // files[0] > 1.jpg
		  // files[1] > 2.jpg

		  List<CommonsMultipartFile> files = n.getFiles();
		  List<String> filenames = new ArrayList<String>(); //파일명만 추출
		  
		  if(files != null && files.size() > 0 ){ //업로드한 파일이 하나라도 있다면
			  
			  for(CommonsMultipartFile multipartfile : files ){
				                              
				  String fname = multipartfile.getOriginalFilename(); //파일명 얻기
				  String path  = request.getServletContext().getRealPath("/customer/upload");
				  String fullpath = path + "\\" + fname;
				  
				  System.out.println(fname + " / " + path + " / " + fullpath);
				  
				  if(!fname.equals("")){
					 //서버에 파일 쓰기 작업 
					  FileOutputStream fs = new FileOutputStream(fullpath);
					  fs.write(multipartfile.getBytes());
					  fs.close();
				  }
				  filenames.add(fname); //실 DB Insert 작업시 .. 파일명 
			  }
			  
		  }
		  
		  // DB저장작업
		  // DB 저장할 파일 명
		  n.setFileSrc(filenames.get(0));  // 파일명 1 
		  n.setFileSrc2(filenames.get(1)); // 파일명 2
		  // 실DB저장
		  noticeDao.insert(n);
		  return "redirect:notice.htm";
		  //return "notice.htm";   
		  //http://localhost:8090/SpringMVC_Basic_WebSiteBasic_Annotation_JdbcTemplate_06/customer/notice.htm
		  //요청 주소값 변화
		  
		  
		     // POINT-1***********************************************
			 // public String noticeReg(Notice n , HttpServletRequest request)
			 // Parameter > Notice n 객체 타입
			 // 글쓰기 화면 입력 > 함수(입력한 값)
			 // 전제 : <input 태그 name="" 값하고 Notice 객체가 가지는 memberField명 같은 경우

			 // POINT-2***********************************************
			 // Spring 에서 파일 업로드
			 // 웹 서버 upload 폴더 : 파일올리기 (IO)
			 // DB : 파일명만 가지면 된다

			 //<Form 태그에 속성으로 : enctype="multipart/form-data">
			 
			 // 1.의존 lib 추가 (fileupload , io)
		     // C:\Kosta106th\Spring\Spring_Utils\Framework\3.0.2\spring-framework-3.0.2.RELEASE-dependencies
			 //    \org.apache.commons\com.springsource.org.apache.commons.fileupload
			
			 // 2. VO , DTO 쪽에Spring 제공하는
			 // CommonsMultipartFile 타입을 갖는 멤버 변수 추가하기 (setter, getter)

			 // 3. 전송페이지에서 <form ... enctype="multipart/form-data" 설정
			 // -<input type="file" name="file" 파일명 VO 객체 이름 동일 강제사항

			 // 4. xml container 에
			 // ***CommonsMultipartFile 반드시> id="multipartResolver"***
			 //<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver" >
		     //   <property name="maxUploadSize" value="10485760"/>
		     //</bean>
	 }

	 // 글삭제하기
	 @RequestMapping("noticeDel.htm")
	 public String noticeDel(String seq) throws ClassNotFoundException,
	   SQLException {
	  System.out.println("seq : " + seq);
	  noticeDao.delete(seq);
	  return "redirect:notice.htm"; //리스트 화면 (controller 타서 데이터 출력)
	 }
     
	 //글수정하기 (두가지 처리 : 화면(select) , 처리(update))
	 //글수정하기 (수정하기 화면 , 수정처리)
	 //주소같고 처리(GET , POST) 처리
	 @RequestMapping(value = "noticeEdit.htm", method = RequestMethod.GET)
	 public String noticeEdit(String seq, Model model)
	   throws ClassNotFoundException, SQLException {
	  //noticeEdit.htm?seq=10	 
	  // 수정페이지는 기존 데이터 출력
	  Notice notice = noticeDao.getNotice(seq);
	  model.addAttribute("notice", notice);
	  //return "noticeEdit.jsp";
	  //Tiles
	  return "customer.noticeEdit";
	 }

	 //게시판 실제 수정처리
	 @RequestMapping(value = "noticeEdit.htm", method = RequestMethod.POST)
	 public String noticeEdit(Notice n ,HttpServletRequest request) throws ClassNotFoundException,
	   SQLException, IOException {

		 List<CommonsMultipartFile> files = n.getFiles();
		  List<String> filenames = new ArrayList<String>(); //파일명만 추출
		  
		  if(files != null && files.size() > 0 ){ //업로드한 파일이 하나라도 있다면
			  
			  for(CommonsMultipartFile multipartfile : files ){
				                              
				  String fname = multipartfile.getOriginalFilename(); //파일명 얻기
				  String path  = request.getServletContext().getRealPath("/customer/upload");
				  String fullpath = path + "\\" + fname;
				  
				  System.out.println(fname + " / " + path + " / " + fullpath);
				  
				  if(!fname.equals("")){
					 //서버에 파일 쓰기 작업 
					  FileOutputStream fs = new FileOutputStream(fullpath);
					  fs.write(multipartfile.getBytes());
					  fs.close();
				  }
				  filenames.add(fname); //실 DB Insert 작업시 .. 파일명 
			  }
			  
		  }
		 
	 
	 n.setFileSrc(filenames.get(0));  // 파일명 1 
	 n.setFileSrc2(filenames.get(1)); // 파일명 2;
	
	  noticeDao.update(n);
	  return "redirect:noticeDetail.htm?seq=" + n.getSeq();
    	 
	 }

	 
	 //DAO 단에서 JDBCTemplate 변환 파일 이미지 가지고 온 다음 확인 합시다 	 
	 @RequestMapping("download.htm")
	 public void download(String p, String f, HttpServletRequest request,
	   HttpServletResponse response) throws IOException {

	  /*
	   * //한글 처리 형식 지정 String sEncoding = new
	   * String(filename.getBytes("euc-kr"),"8859_1");
	   * response.setHeader("Content-Disposition","attachment;filename= " +
	   * sEncoding);
	   * //response.setHeader("Content-Disposition","attachment;filename= " +
	   * filename +";");
	   */
	  // 한글 파일명 처리 (Filtter 처리 확인) -> 경우 ...
	  // 한글 파일 깨짐 현상 해결하기
	  // String fname = new String(f.getBytes("ISO8859_1"),"UTF-8");
	  String fname = new String(f.getBytes("euc-kr"), "8859_1");
	  System.out.println(fname);
	  // 다운로드 기본 설정 (브라우져가 read 하지 않고 ... 다운 )
	  // 요청 - 응답 간에 헤더정보에 설정을 강제 다운로드
	  // response.setHeader("Content-Disposition", "attachment;filename=" +
	  // new String(fname.getBytes(),"ISO8859_1"));
	  response.setHeader("Content-Disposition", "attachment;filename="
	    + fname + ";");
	  // 파일명 전송
	  // 파일 내용전송
	  String fullpath = request.getServletContext().getRealPath(
	    "/customer/" + p + "/" + f);
	  System.out.println(fullpath);
	  FileInputStream fin = new FileInputStream(fullpath);
	  // 출력 도구 얻기 :response.getOutputStream()
	  ServletOutputStream sout = response.getOutputStream();
	  byte[] buf = new byte[1024]; // 전체를 다읽지 않고 1204byte씩 읽어서
	  int size = 0;
	  while ((size = fin.read(buf, 0, buf.length)) != -1) // buffer 에 1024byte
	               // 담고
	  { // 마지막 남아있는 byte 담고 그다음 없으면 탈출
	   sout.write(buf, 0, size); // 1kbyte씩 출력
	  }
	  fin.close();
	  sout.close();
	 }

}
