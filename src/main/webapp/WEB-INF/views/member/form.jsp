<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../common/common.jsp"  %>
<body>
<div class="container">
	<h3>メンバー登録画面</h3>
	<div class="span8">
		<div class="row">
		<form:form modelAttribute="memberForm" action="${pageContext.request.contextPath}/member/create">
			<table class="table table-striped">
			  <tr>
			    <th>
			     	 氏名
			    </th>
			    <td>
			    	<form:errors path="name" element="div"/>
			    	<form:input path="name"  placeholder="Name"/>
			    </td>
			  </tr>
			  <tr>
			    <th>
			      	メールアドレス
			    </th>
			    <td>
			    	<form:errors path="mailAddress" element="div"/>
			    	<form:input path="mailAddress" placeholder="Email"/>
			    </td>
			  </tr>
			  <tr>
			    <th>
			     	 パスワード
			    </th>
			    <td>
			    	<form:errors path="password" element="div"/>
			    	<form:password path="password" placeholder="Password"/>  	
			    </td>
			   </tr>
			   <tr>
			    <th>
			     	 パスワード確認
			    </th>
			    <td>
			    	<form:errors path="confirm_password" element="div"/>
			    	<form:password path="confirm_password" placeholder="confirm_password"/>
			    </td>
			   
			  </tr>
			  <tr>
			  	<td></td>
			    <td>
					<input class="btn" type="submit" value="登録">
			    </td>
			  </tr>
			</table>
		  </form:form>
		</div>
	</div>
</div>
</body>
</html>