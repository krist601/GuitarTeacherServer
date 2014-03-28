
<%@ page import="teacher.Nota" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'nota.label', default: 'Nota')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-nota" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-nota" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list nota">
			
				<g:if test="${notaInstance?.audio}">
				<li class="fieldcontain">
					<span id="audio-label" class="property-label"><g:message code="nota.audio.label" default="Audio" /></span>
					
						<span class="property-value" aria-labelledby="audio-label"><g:link controller="audio" action="show" id="${notaInstance?.audio?.id}">${notaInstance?.audio?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${notaInstance?.frecuencia}">
				<li class="fieldcontain">
					<span id="frecuencia-label" class="property-label"><g:message code="nota.frecuencia.label" default="Frecuencia" /></span>
					
						<span class="property-value" aria-labelledby="frecuencia-label"><g:fieldValue bean="${notaInstance}" field="frecuencia"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${notaInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="nota.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${notaInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${notaInstance?.id}" />
					<g:link class="edit" action="edit" id="${notaInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
