<%@ page import="teacher.Practice" %>



<div class="fieldcontain ${hasErrors(bean: practiceInstance, field: 'image', 'error')} ">
	<label for="image">
		<g:message code="practice.image.label" default="Image" />
		
	</label>
	<g:select id="image" name="image.id" from="${teacher.Image.list()}" optionKey="id" value="${practiceInstance?.image?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: practiceInstance, field: 'audio', 'error')} ">
	<label for="audio">
		<g:message code="practice.audio.label" default="Audio" />
		
	</label>
	<g:select id="audio" name="audio.id" from="${teacher.Audio.list()}" optionKey="id" value="${practiceInstance?.audio?.id}" class="many-to-one" noSelection="['null': '']"/>
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

