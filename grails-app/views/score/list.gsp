
<%@ page import="teacher.Score" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'score.label', default: 'Score')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-score" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-score" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="date" title="${message(code: 'score.date.label', default: 'Date')}" />
					
						<th><g:message code="score.sample.label" default="Sample" /></th>
					
						<g:sortableColumn property="score" title="${message(code: 'score.score.label', default: 'Score')}" />
					
						<th><g:message code="score.usuario.label" default="Usuario" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${scoreInstanceList}" status="i" var="scoreInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${scoreInstance.id}">${fieldValue(bean: scoreInstance, field: "date")}</g:link></td>
					
						<td>${fieldValue(bean: scoreInstance, field: "sample")}</td>
					
						<td>${fieldValue(bean: scoreInstance, field: "score")}</td>
					
						<td>${fieldValue(bean: scoreInstance, field: "usuario")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${scoreInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
