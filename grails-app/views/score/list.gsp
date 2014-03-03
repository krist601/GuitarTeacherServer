
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
					
						<th><g:message code="score.level.label" default="Level" /></th>
					
						<g:sortableColumn property="live" title="${message(code: 'score.live.label', default: 'Live')}" />
					
						<th><g:message code="score.player.label" default="Player" /></th>
					
						<g:sortableColumn property="score" title="${message(code: 'score.score.label', default: 'Score')}" />
					
						<g:sortableColumn property="state" title="${message(code: 'score.state.label', default: 'State')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${scoreInstanceList}" status="i" var="scoreInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${scoreInstance.id}">${fieldValue(bean: scoreInstance, field: "date")}</g:link></td>
					
						<td>${fieldValue(bean: scoreInstance, field: "level")}</td>
					
						<td>${fieldValue(bean: scoreInstance, field: "live")}</td>
					
						<td>${fieldValue(bean: scoreInstance, field: "player")}</td>
					
						<td>${fieldValue(bean: scoreInstance, field: "score")}</td>
					
						<td>${fieldValue(bean: scoreInstance, field: "state")}</td>
					
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
