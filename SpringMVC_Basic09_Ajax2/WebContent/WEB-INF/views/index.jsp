<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring Jquery Ajax Demo</title>
<style>
Table.GridOne {
	padding: 3px;
	margin: 0;
	background: lightyellow;
	border-collapse: collapse;	
	width:35%;
}
Table.GridOne Td {	
	padding:2px;
	border: 1px solid #ff9900;
	border-collapse: collapse;
}
</style>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript">
     function madeAjaxCall(){
    	 /*  $.ajax({
			type: "post",
			url:  "testJson3.kosta",
			cache: false,				
			data:'firstName=' + $("#firstName").val() + "&lastName=" + $("#lastName").val() + "&email=" + $("#email").val(),
		    success:function(data){ //callback  
		    	console.log(data);
		        $("#menuView").empty();
		        var resv="";  
		          $.each(data,function(index,value){
		          	$("#menuView").append(index+" : " + "First Name:- " + value.firstName +"</br>Last Name:- " + value.lastName  + "</br>Email:- " + value.email + "<br>");
		          }); 
		     },
			error: function(){						
				alert('Error while request..'	);
			}
		});  */
	
		 
   	 $.ajax({
 			type: "post",
 			url:  "response.kosta",
 			cache: false,				
 			data:'firstName=' + $("#firstName").val() + "&lastName=" + $("#lastName").val() + "&email=" + $("#email").val(),
 		    success:function(data){ //callback  
 		    	 console.log(data);
 		    	$("#menuView").empty();
 		        var resv="";  
 		        $("#menuView").append("First Name:- " + data.firstname +"</br>Last Name:- " + data.lastname  + "</br>Email:- " + data.email + "<br>");
 		         
 		     },
 			error: function(){						
 				alert('Error while request..'	);
 			}
 		});
		 
	 }  
 
</script>
</head>
<body>
	<form name="employeeForm" method="post">	
		<table cellpadding="0" cellspacing="0" border="1" class="GridOne">
			<tr>
				<td>First Name</td>
				<td><input type="text" name="firstName" id="firstName" value=""></td>
			</tr>
			<tr>
				<td>Last Name</td>
				<td><input type="text" name="lastName" id="lastName" value=""></td>
			</tr>
			<tr>
				<td>Email</td>
				<td><input type="text" name="email" id="email" value=""></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button" value="Ajax Submit" id = "ajaxBtn" name = "ajaxBtn" onclick="madeAjaxCall();"></td>
			</tr>
		</table>
	</form>
	 <h1>TEST</h1>
	<div id="menuView"></div>
</body>
</html>
 