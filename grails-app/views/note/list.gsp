
<%@ page import="teacher.Note" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'note.label', default: 'Note')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-note" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-note" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="frequency" title="${message(code: 'note.frequency.label', default: 'Frequency')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'note.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="range" title="${message(code: 'note.range.label', default: 'Range')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${noteInstanceList}" status="i" var="noteInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${noteInstance.id}">${fieldValue(bean: noteInstance, field: "frequency")}</g:link></td>
					
						<td>${fieldValue(bean: noteInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: noteInstance, field: "range")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${noteInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
