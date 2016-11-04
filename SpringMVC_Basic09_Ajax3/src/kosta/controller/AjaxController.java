package kosta.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import kosta.vo.SampleBean;


@Controller
public class AjaxController {
	@Autowired
	private View jsonview;
	
	@RequestMapping( "/jsonReport1.do" )
	public ModelAndView jsonReport( )
	{
		List< Map< String , Object >> pageRanks = new ArrayList< Map< String , Object >>( );
		Map< String , Object > map = new HashMap< String , Object >( );
		map.put( "1" , "하나" );
		Map< String , Object > map1 = new HashMap< String , Object >( );
		map1.put( "2" , "둘" );
		Map< String , Object > map2 = new HashMap< String , Object >( );
		map2.put( "3" , "셋" );
		map2.put( "3" , "넷" );
		map2.put( "3" , "뭐지" );
		map2.put( "name" , map1 );
		pageRanks.add( map1 );
		pageRanks.add( map2 );
		return new ModelAndView( "jsonview" , "report" , pageRanks );
	}

	@RequestMapping( "/jsonReport2.do" )
	public ModelAndView jsonReport2( )
	{
		Map< String , String > map2 = new HashMap< String , String >( );
		map2.put( "name" , "바람소리" );
		map2.put( "tel" , "010-000000" );
		map2.put( "id" , "vishvoice" );
		map2.put( "age" , "30" );
		//
		Map< String , String > map1 = new HashMap< String , String >( );
		map1.put( "job_name" , "what!" );
		map1.put( "office_name" , "xxxxㅌ" );
		/**
		 * 아래처럼 addObject("","");
		 * 이렇 해주면 json {userInfo:{'',''},jobinfo:{'',''}} 이렇게 나온다.
		 */
		ModelAndView modelAndView = new ModelAndView( "jsonview" );
		modelAndView.addObject( "userInfo" , map2 );
		modelAndView.addObject( "jobinfo" , map1 );
		return modelAndView;
	}
	
	@RequestMapping( "/jsonReport3.do" )
	public ModelAndView jsonReport3( )
	{
		SampleBean sb = new SampleBean( );
		sb.setId( "id" );
		sb.setName( "name" );
		return new ModelAndView( "jsonview" , "report" , sb );
	}
}
