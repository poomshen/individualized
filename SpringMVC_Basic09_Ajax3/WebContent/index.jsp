<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-2.2.4.js"></script>
</head>
<body>
<!--  
JSON.parse()
인자로 전달된 문자열을 자바스크립트의 데이터(객체)로 변환한다.
[URL 참고] https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/JSON/parse
JSON.stringify()
인자로 전달된 자바스크립트의 데이터를 문자열로 변환한다.
[URL 참고] https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/JSON/stringify
-->
<script type="text/javascript">
$(document).ready(
	function()
	{
		$.getJSON('jsonReport1.do', 
			function(data)
			{  
			    console.log(data);
				console.log(JSON.stringify(data));
			}
		);

		$.getJSON('jsonReport2.do', 
			function(data)
			{
				console.log(data);
				console.log(JSON.stringify(data))
				//alert(JSON.stringify(data));
			}
		);
	}
);
</script>

</body>
</html>