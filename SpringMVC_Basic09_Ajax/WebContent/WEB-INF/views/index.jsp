<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#ajaxBtn').click(function(){
				 var array = new Array();
				     array[0] = "a";
				     array[1] = "b";
				 $.ajax(
						 {
							type : "post",
							url  : "json.kosta",
							data : "command=AjaxTest&name=java&arr="+array,
							success : function(data){
								console.log(data);
								$('#menuView').empty();
								var opr="";
								$.each(data.menu,function(index,value){
									console.log(index + "/" + value);
									opr += index + "." + value + "<br>";
								});
								$('#menuView').append(opr);
							} 
						 } 
				       )    
			});
			
			
			$('#ajaxBtn2').click(function(){
				 $.ajax(
						 {
							type : "post",
							url  : "json2.kosta",
							data : "command=AjaxTest2&name=java2",
							success : function(data){
								console.log(data);
								$('#menuView').empty();
								var opr="";
								$.each(data.menu,function(index,obj){
									console.log(index + "/" + obj);
									opr += obj.beer + "<br>";
									opr += obj.food + "<br>";
									opr += index +"<br><hr>"
								});
								$('#menuView').append(opr);
							} 
						 } 
				       )    
			});
			
			
			$('#ajaxBtn3').click(function(){
				 $.ajax(
						 {
							type : "post",
							url  : "json3.kosta",
							success : function(data){
								console.log(data);
								$('#menuView').empty();
								var opr="";
								$.each(data.data,function(index,obj){
									console.log(index + "/" + obj);
									opr += obj.firstname + "<br>";
									opr += obj.lastname + "<br>";
									opr += obj.email + "<br>";
									opr += index +"<br><hr>"
								});
								$('#menuView').append(opr);
							} 
						 } 
				       )    
			});
		});
	
	</script>
</head>
<body>
	<input type="button" value="Spring-json" id="ajaxBtn">
	<input type="button" value="Spring-json" id="ajaxBtn2">
	<input type="button" value="Spring-json" id="ajaxBtn3">
	<hr>
	<span id="menuView"></span>
</body>
</html>