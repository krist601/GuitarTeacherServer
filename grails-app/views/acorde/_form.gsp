<%@ page import="teacher.Acorde" %>



<div class="fieldcontain ${hasErrors(bean: acordeInstance, field: 'audio', 'error')} ">
	<label for="audio">
		<g:message code="acorde.audio.label" default="Audio" />
		
	</label>
	<g:select id="audio" name="audio.id" from="${teacher.Audio.list()}" optionKey="id" value="${acordeInstance?.audio?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: acordeInstance, field: 'frecuencia', 'error')} ">
	<label for="frecuencia">
		<g:message code="acorde.frecuencia.label" default="Frecuencia" />
		
	</label>
	<g:textField name="frecuencia" value="${acordeInstance?.frecuencia}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: acordeInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="acorde.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${acordeInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: acordeInstance, field: 'rango', 'error')} ">
	<label for="rango">
		<g:message code="acorde.rango.label" default="Rango" />
		
	</label>
	<g:textField name="rango" value="${acordeInstance?.rango}"/>
</div>

