<%@ page import="teacher.Sample" %>



<div class="fieldcontain ${hasErrors(bean: sampleInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="sample.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${sampleInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: sampleInstance, field: 'sound', 'error')} required">
	<label for="sound">
		<g:message code="sample.sound.label" default="Sound" />
		<span class="required-indicator">*</span>
	</label>
	<input type="file" id="sound" name="sound" />
</div>

<div class="fieldcontain ${hasErrors(bean: sampleInstance, field: 'tonality', 'error')} ">
	<label for="tonality">
		<g:message code="sample.tonality.label" default="Tonality" />
		
	</label>
	<g:textField name="tonality" value="${sampleInstance?.tonality}"/>
</div>

