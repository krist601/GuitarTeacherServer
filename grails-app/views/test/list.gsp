
<%@ page import="teacher.Test" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'test.label', default: 'Test')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-test" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-test" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="test.theory.label" default="Theory" /></th>
					
						<th><g:message code="test.question.label" default="Question" /></th>
					
						<th><g:message code="test.nota.label" default="Nota" /></th>
					
						<th><g:message code="test.ritmica.label" default="Ritmica" /></th>
					
						<th><g:message code="test.acorde.label" default="Acorde" /></th>
					
						<th><g:message code="test.level.label" default="Level" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${testInstanceList}" status="i" var="testInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${testInstance.id}">${fieldValue(bean: testInstance, field: "theory")}</g:link></td>
					
						<td>${fieldValue(bean: testInstance, field: "question")}</td>
					
						<td>${fieldValue(bean: testInstance, field: "nota")}</td>
					
						<td>${fieldValue(bean: testInstance, field: "ritmica")}</td>
					
						<td>${fieldValue(bean: testInstance, field: "acorde")}</td>
					
						<td>${fieldValue(bean: testInstance, field: "level")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${testInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
