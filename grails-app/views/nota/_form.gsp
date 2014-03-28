<%@ page import="teacher.Nota" %>



<div class="fieldcontain ${hasErrors(bean: notaInstance, field: 'audio', 'error')} ">
	<label for="audio">
		<g:message code="nota.audio.label" default="Audio" />
		
	</label>
	<g:select id="audio" name="audio.id" from="${teacher.Audio.list()}" optionKey="id" value="${notaInstance?.audio?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: notaInstance, field: 'frecuencia', 'error')} ">
	<label for="frecuencia">
		<g:message code="nota.frecuencia.label" default="Frecuencia" />
		
	</label>
	<g:textField name="frecuencia" value="${notaInstance?.frecuencia}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: notaInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="nota.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${notaInstance?.name}"/>
</div>

