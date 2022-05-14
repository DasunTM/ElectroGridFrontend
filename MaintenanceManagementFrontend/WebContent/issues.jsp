<%@page import="com.Issue"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="Views/bootstrap.min.css">
		<script src="Components/jquery-3.2.1.min.js"></script>
        <script src="Components/issues.js"></script>
		<title>Issues Management</title>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col">
				<br>
					<h3><center>ElectroGrid</center></h3><br>
					<h1><center>Issues Management</center></h1>
					<br><br><br><form id="formIssue" name="formIssue" >
						Issue code: 
						<input 
							id="issueCode" 
							name="issueCode" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						Allocated Employee : 
						<input 
							id="empAllocated"
							name="empAllocated" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						System ID: 
						<input 
							id="systemID" 
							name="systemID" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						Issue description: 
						<input 
							id="issueDesc" 
							name="issueDesc" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						<input 
							id="btnSave" 
							name="btnSave" 
							type="button" 
							value="Save" 
							class="btn btn-primary"
						>

						<input type="hidden" name="hidIssueIDSave" id="hidIssueIDSave" value="">
					</form>
				
					<br>
					<div id="alertSuccess" class="alert alert-success"></div>
					<div id="alertError" class="alert alert-danger"></div>
					<br>
					<div id="divIssuesGrid">
						<%
							Issue issue = new Issue(); 
							out.print(issue.readIssues());
						%>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>