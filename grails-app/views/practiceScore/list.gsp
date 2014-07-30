
<%@ page import="teacher.PracticeScore" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'practiceScore.label', default: 'PracticeScore')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-practiceScore" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-practiceScore" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="practiceScore.note.label" default="Note" /></th>
					
						<th><g:message code="practiceScore.rhythmic.label" default="Rhythmic" /></th>
					
						<th><g:message code="practiceScore.chord.label" default="Chord" /></th>
					
						<g:sortableColumn property="score" title="${message(code: 'practiceScore.score.label', default: 'Score')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${practiceScoreInstanceList}" status="i" var="practiceScoreInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${practiceScoreInstance.id}">${fieldValue(bean: practiceScoreInstance, field: "note")}</g:link></td>
					
						<td>${fieldValue(bean: practiceScoreInstance, field: "rhythmic")}</td>
					
						<td>${fieldValue(bean: practiceScoreInstance, field: "chord")}</td>
					
						<td>${fieldValue(bean: practiceScoreInstance, field: "score")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${practiceScoreInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
