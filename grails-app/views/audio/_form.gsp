<%@ page import="teacher.Audio" %>



<div class="fieldcontain ${hasErrors(bean: audioInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="audio.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${audioInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: audioInstance, field: 'sound', 'error')} required">
	<label for="sound">
		<g:message code="audio.sound.label" default="Sound" />
		<span class="required-indicator">*</span>
	</label>
	<input type="file" id="sound" name="sound" />
</div>

