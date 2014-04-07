
<%@ page import="teacher.Rhythmic" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'rhythmic.label', default: 'Rhythmic')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-rhythmic" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-rhythmic" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list rhythmic">
			
				<g:if test="${rhythmicInstance?.frequency}">
				<li class="fieldcontain">
					<span id="frequency-label" class="property-label"><g:message code="rhythmic.frequency.label" default="Frequency" /></span>
					
						<span class="property-value" aria-labelledby="frequency-label"><g:fieldValue bean="${rhythmicInstance}" field="frequency"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${rhythmicInstance?.image}">
				<li class="fieldcontain">
					<span id="image-label" class="property-label"><g:message code="rhythmic.image.label" default="Image" /></span>
					
						<span class="property-value" aria-labelledby="image-label"><g:link controller="image" action="show" id="${rhythmicInstance?.image?.id}">${rhythmicInstance?.image?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${rhythmicInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="rhythmic.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${rhythmicInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${rhythmicInstance?.range}">
				<li class="fieldcontain">
					<span id="range-label" class="property-label"><g:message code="rhythmic.range.label" default="Range" /></span>
					
						<span class="property-value" aria-labelledby="range-label"><g:fieldValue bean="${rhythmicInstance}" field="range"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${rhythmicInstance?.time}">
				<li class="fieldcontain">
					<span id="time-label" class="property-label"><g:message code="rhythmic.time.label" default="Time" /></span>
					
						<span class="property-value" aria-labelledby="time-label"><g:fieldValue bean="${rhythmicInstance}" field="time"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${rhythmicInstance?.id}" />
					<g:link class="edit" action="edit" id="${rhythmicInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
