
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/common.jsp"  %>
<body>
<div class="container">

	<c:out value="${member.name}"/>さん　こんにちは！<br>
	<a href="${pageContext.request.contextPath}/book/list">書籍一覧へ</a>
	<a href="${pageContext.request.contextPath}/logout/sessionInvalidate">ログアウト</a>
	<h3>書籍追加画面</h3>
	<div class="span8">
		<div class="row">
		
			<form:form modelAttribute="insertForm" action="${pageContext.request.contextPath}/book/save" method="POST">
			<form:errors path="bookForm.*" />
			<table class="table table-striped">
			  <tr>
			    <th>
			      書籍名
			    </th>
			    <td>
			      <form:input path="name"/>
			    </td>
			  </tr>
			  <tr>
			    <th>
			      著者
			    </th>
			    <td>
			      <form:input path="author"/>
			    </td>
			  </tr>
			  <tr>
			    <th>
			      出版社
			    </th>
			    <td>
			   	<form:input path="publisher"/>
			    </td>
			  </tr>
			  <tr>
			    <th>
			      価格
			    </th>
			    <td>
			    	<form:input path="price"/>
			    </td>
			  </tr>
			  <tr>
			    <th>
			      ISBNコード
			    </th>
			    <td>
			      <form:input path="isbncode"/>
			    </td>
			  </tr>
			  <tr>
			    <th>
			      発売日
			    </th>
			    <td>
			      <form:input path="saledate" type="date"/>
			    </td>
			  </tr>
			  <tr>
			    <th>
			      説明
			    </th>
			    <td> 
			      <form:input path="explanation"/>
			    </td>
			  </tr>
			  <tr>
			    <th>
			      画像
			    </th>
			    <td>
			      <form:input path="image"/>
			    </td>
			  </tr>
		  
			  <tr>
			    <th>
			      在庫数
			    </th>
			    <td>
					<form:input path="stock"/>
			    </td>
			  </tr>
			  
			</table>
			
			<input type="submit" value="本を追加する"/>
			
			</form:form>
			
		</div>
	</div>	

	
</div>
</body>
</html>
