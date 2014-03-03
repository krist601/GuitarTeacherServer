
<%@ page import="teacher.Practice" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'practice.label', default: 'Practice')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-practice" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-practice" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="practice.image.label" default="Image" /></th>
					
						<th><g:message code="practice.audio.label" default="Audio" /></th>
					
						<g:sortableColumn property="name" title="${message(code: 'practice.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="type" title="${message(code: 'practice.type.label', default: 'Type')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${practiceInstanceList}" status="i" var="practiceInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${practiceInstance.id}">${fieldValue(bean: practiceInstance, field: "image")}</g:link></td>
					
						<td>${fieldValue(bean: practiceInstance, field: "audio")}</td>
					
						<td>${fieldValue(bean: practiceInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: practiceInstance, field: "type")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${practiceInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
