package Service;

import java.io.FileInputStream;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import dao.NoticeDao;
import vo.Notice;

@Service
public class CustomerService {

	@Autowired
	private SqlSession sqlsession;
	public List<Notice> notices(String pg, String f, String q) throws ClassNotFoundException, SQLException {

		int page = 1;
		String field = "TITLE";
		String query = "%%";

		if (pg != null && pg.equals("")) {
			page = Integer.parseInt(pg);
		}
		if (f != null && f.equals("")) {
			field = f;
		}
		if (q != null && q.equals("")) {
			query = q;
		}

		NoticeDao noticeDao = sqlsession.getMapper(NoticeDao.class);
		List<Notice> list = noticeDao.getNotices(page, field, query);

		return list;
	}
	public Notice noticeDetail(String seq) throws ClassNotFoundException, SQLException{
		 
		 NoticeDao noticeDao = sqlsession.getMapper(NoticeDao.class);
		 Notice notice = noticeDao.getNotice(seq);
		 return notice;
	 }
	
	@Transactional
	public String noticeReg(Notice n, HttpServletRequest request)
			   throws IOException, ClassNotFoundException, SQLException {

		  List<CommonsMultipartFile> files = n.getFiles();
		  List<String> filenames = new ArrayList<String>(); 
		  
		  if(files != null && files.size() > 0 ){ 
			  for(CommonsMultipartFile multipartfile : files ){
				  String fname = multipartfile.getOriginalFilename(); //파일명 얻기
				  String path  = request.getServletContext().getRealPath("/customer/upload");
				  String fullpath = path + "\\" + fname;
				  if(!fname.equals("")){
					  FileOutputStream fs = new FileOutputStream(fullpath);
					  fs.write(multipartfile.getBytes());
					  fs.close();
				  }
				  filenames.add(fname); 
			  }
			  
		  }
	
		  n.setFileSrc(filenames.get(0)); 
		  n.setFileSrc2(filenames.get(1)); 
	
		  NoticeDao noticeDao = sqlsession.getMapper(NoticeDao.class);
		  //begin trans
		  try{
			  noticeDao.insert(n);
			  noticeDao.insertOfMemberPoint("kglim");
			  System.out.println("insertOfMemberPoint");
		  }catch (Exception e) {
			e.printStackTrace();
			throw e; //예외 나면 rollback
		  }
		  //end trans
		  return "redirect:notice.htm";
	}
	public String noticeDel(String seq) throws ClassNotFoundException,
	   SQLException {
	  
	  NoticeDao noticeDao = sqlsession.getMapper(NoticeDao.class);
	  noticeDao.delete(seq);
	  
	  return "redirect:notice.htm"; 
	 }
	public Notice noticeEdit(String seq)
			   throws ClassNotFoundException, SQLException {
		 NoticeDao noticeDao = sqlsession.getMapper(NoticeDao.class);
		 Notice notice = noticeDao.getNotice(seq);
		 return notice;
	}
	public String noticeEdit(Notice n ,HttpServletRequest request) throws ClassNotFoundException,
	   SQLException, IOException {

		 List<CommonsMultipartFile> files = n.getFiles();
		 List<String> filenames = new ArrayList<String>(); 
		  
		  if(files != null && files.size() > 0 ){ 
			  for(CommonsMultipartFile multipartfile : files ){
				  String fname = multipartfile.getOriginalFilename(); 
				  String path  = request.getServletContext().getRealPath("/customer/upload");
				  String fullpath = path + "\\" + fname;
	  
				  if(!fname.equals("")){
					  FileOutputStream fs = new FileOutputStream(fullpath);
					  fs.write(multipartfile.getBytes());
					  fs.close();
				  }
				  filenames.add(fname); 
			  }
			  
		  }
	 n.setFileSrc(filenames.get(0)); 
	 n.setFileSrc2(filenames.get(1)); 
     NoticeDao noticeDao = sqlsession.getMapper(NoticeDao.class);
	 noticeDao.update(n);
	  return "redirect:noticeDetail.htm?seq=" + n.getSeq();
    }
	public void download(String p, String f, HttpServletRequest request,
			   HttpServletResponse response) throws IOException {
		 
		String fname = new String(f.getBytes("euc-kr"), "8859_1");
		response.setHeader("Content-Disposition", "attachment;filename="
		    + fname + ";");
		String fullpath = request.getServletContext().getRealPath("/customer/" + p + "/" + f);
		
		FileInputStream fin = new FileInputStream(fullpath);
		
		  ServletOutputStream sout = response.getOutputStream();
		  byte[] buf = new byte[1024]; // 전체를 다읽지 않고 1204byte씩 읽어서
		  int size = 0;
		  while ((size = fin.read(buf, 0, buf.length)) != -1) // buffer 에 1024byte
		  { 
		   sout.write(buf, 0, size); // 1kbyte씩 출력
		  }
		  fin.close();
		  sout.close();
	}	
}

