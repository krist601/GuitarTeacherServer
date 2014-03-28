
<%@ page import="teacher.Acorde" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'acorde.label', default: 'Acorde')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-acorde" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-acorde" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list acorde">
			
				<g:if test="${acordeInstance?.audio}">
				<li class="fieldcontain">
					<span id="audio-label" class="property-label"><g:message code="acorde.audio.label" default="Audio" /></span>
					
						<span class="property-value" aria-labelledby="audio-label"><g:link controller="audio" action="show" id="${acordeInstance?.audio?.id}">${acordeInstance?.audio?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${acordeInstance?.frecuencia}">
				<li class="fieldcontain">
					<span id="frecuencia-label" class="property-label"><g:message code="acorde.frecuencia.label" default="Frecuencia" /></span>
					
						<span class="property-value" aria-labelledby="frecuencia-label"><g:fieldValue bean="${acordeInstance}" field="frecuencia"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${acordeInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="acorde.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${acordeInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${acordeInstance?.rango}">
				<li class="fieldcontain">
					<span id="rango-label" class="property-label"><g:message code="acorde.rango.label" default="Rango" /></span>
					
						<span class="property-value" aria-labelledby="rango-label"><g:fieldValue bean="${acordeInstance}" field="rango"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${acordeInstance?.id}" />
					<g:link class="edit" action="edit" id="${acordeInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
