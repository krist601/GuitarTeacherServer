<%@ page import="teacher.Audio" %>



<div class="fieldcontain ${hasErrors(bean: audioInstance, field: 'sound', 'error')} required">
	<label for="sound">
		<g:message code="audio.sound.label" default="Sound" />
		<span class="required-indicator">*</span>
	</label>
	<input type="file" id="sound" name="sound" />
</div>

