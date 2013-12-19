<%@ page import="teacher.Practice" %>



<div class="fieldcontain ${hasErrors(bean: practiceInstance, field: 'audio', 'error')} required">
	<label for="audio">
		<g:message code="practice.audio.label" default="Audio" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="audio" name="audio.id" from="${teacher.Audio.list()}" optionKey="id" required="" value="${practiceInstance?.audio?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: practiceInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="practice.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${practiceInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: practiceInstance, field: 'type', 'error')} ">
	<label for="type">
		<g:message code="practice.type.label" default="Type" />
		
	</label>
	<g:textField name="type" value="${practiceInstance?.type}"/>
</div>

