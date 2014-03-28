
<%@ page import="teacher.Acorde" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'acorde.label', default: 'Acorde')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-acorde" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-acorde" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="acorde.audio.label" default="Audio" /></th>
					
						<g:sortableColumn property="frecuencia" title="${message(code: 'acorde.frecuencia.label', default: 'Frecuencia')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'acorde.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="rango" title="${message(code: 'acorde.rango.label', default: 'Rango')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${acordeInstanceList}" status="i" var="acordeInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${acordeInstance.id}">${fieldValue(bean: acordeInstance, field: "audio")}</g:link></td>
					
						<td>${fieldValue(bean: acordeInstance, field: "frecuencia")}</td>
					
						<td>${fieldValue(bean: acordeInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: acordeInstance, field: "rango")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${acordeInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
