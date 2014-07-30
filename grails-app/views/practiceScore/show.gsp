
<%@ page import="teacher.PracticeScore" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'practiceScore.label', default: 'PracticeScore')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-practiceScore" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-practiceScore" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list practiceScore">
			
				<g:if test="${practiceScoreInstance?.note}">
				<li class="fieldcontain">
					<span id="note-label" class="property-label"><g:message code="practiceScore.note.label" default="Note" /></span>
					
						<span class="property-value" aria-labelledby="note-label"><g:link controller="note" action="show" id="${practiceScoreInstance?.note?.id}">${practiceScoreInstance?.note?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${practiceScoreInstance?.rhythmic}">
				<li class="fieldcontain">
					<span id="rhythmic-label" class="property-label"><g:message code="practiceScore.rhythmic.label" default="Rhythmic" /></span>
					
						<span class="property-value" aria-labelledby="rhythmic-label"><g:link controller="rhythmic" action="show" id="${practiceScoreInstance?.rhythmic?.id}">${practiceScoreInstance?.rhythmic?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${practiceScoreInstance?.chord}">
				<li class="fieldcontain">
					<span id="chord-label" class="property-label"><g:message code="practiceScore.chord.label" default="Chord" /></span>
					
						<span class="property-value" aria-labelledby="chord-label"><g:link controller="chord" action="show" id="${practiceScoreInstance?.chord?.id}">${practiceScoreInstance?.chord?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${practiceScoreInstance?.score}">
				<li class="fieldcontain">
					<span id="score-label" class="property-label"><g:message code="practiceScore.score.label" default="Score" /></span>
					
						<span class="property-value" aria-labelledby="score-label"><g:fieldValue bean="${practiceScoreInstance}" field="score"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${practiceScoreInstance?.id}" />
					<g:link class="edit" action="edit" id="${practiceScoreInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
