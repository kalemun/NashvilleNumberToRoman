<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Convert nashville number to roman numerals</title>
     <script src="/js/jquery-2.1.4.min.js"></script>
     <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/style/app.css">
     <script type="text/javascript">
     function doAjaxPost() {
     // get the form values
     var nashvilleNumber = $('#nashvilleNumber').val();

     $.ajax({
     type: "POST",
     url: "http://localhost:8080/convert",
     data: "nashvilleNumber=" + nashvilleNumber,
     success: function(response){
    	 console.log(response);
     	// we have the response
         if(response.status == "SUCCESS") {
              $('#romanNumeral').val(response.result.romanNumeral);
              $('#error').hide('slow');
          } else {
              errorInfo = "";
              for(i =0 ; i < response.result.length ; i++){
                  errorInfo += "<br>" + (i + 1) +". " + response.result[i].defaultMessage;
              }
              $('#error').html("Please correct following errors: " + errorInfo);
              $('#error').show('slow');
          }     	
     },
     error: function(e){
     alert('Error: ' + e);
     }
     });
     }
     </script>

</head>
<body>
	
	<h1>Convert nashville number to roman numerals...</h1>
	
	<form id="main">
		<table>
			<tr><td colspan="2"><div id="error" class="error"></div></td><td><springForm:errors path="nashvilleNumber" cssClass="error" /></td></tr>
			<tr><td>Enter the nashville number : </td><td><input type="text" id="nashvilleNumber"> </td></tr>
			<tr><td colspan="2"><input type="button" value="Convert" onclick="doAjaxPost()"><br/></td></tr>
			<tr/><tr/>
			<tr><td>Result : </td><td><input id="romanNumeral" /></td></tr>
		</table>
	</form>
</body>
</html>