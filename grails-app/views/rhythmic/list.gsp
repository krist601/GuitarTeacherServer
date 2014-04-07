
<%@ page import="teacher.Rhythmic" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'rhythmic.label', default: 'Rhythmic')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-rhythmic" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-rhythmic" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="frequency" title="${message(code: 'rhythmic.frequency.label', default: 'Frequency')}" />
					
						<th><g:message code="rhythmic.image.label" default="Image" /></th>
					
						<g:sortableColumn property="name" title="${message(code: 'rhythmic.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="range" title="${message(code: 'rhythmic.range.label', default: 'Range')}" />
					
						<g:sortableColumn property="time" title="${message(code: 'rhythmic.time.label', default: 'Time')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${rhythmicInstanceList}" status="i" var="rhythmicInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${rhythmicInstance.id}">${fieldValue(bean: rhythmicInstance, field: "frequency")}</g:link></td>
					
						<td>${fieldValue(bean: rhythmicInstance, field: "image")}</td>
					
						<td>${fieldValue(bean: rhythmicInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: rhythmicInstance, field: "range")}</td>
					
						<td>${fieldValue(bean: rhythmicInstance, field: "time")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${rhythmicInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
