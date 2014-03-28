<%@ page import="teacher.Ritmica" %>



<div class="fieldcontain ${hasErrors(bean: ritmicaInstance, field: 'audio', 'error')} ">
	<label for="audio">
		<g:message code="ritmica.audio.label" default="Audio" />
		
	</label>
	<g:select id="audio" name="audio.id" from="${teacher.Audio.list()}" optionKey="id" value="${ritmicaInstance?.audio?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ritmicaInstance, field: 'frecuencia', 'error')} ">
	<label for="frecuencia">
		<g:message code="ritmica.frecuencia.label" default="Frecuencia" />
		
	</label>
	<g:textField name="frecuencia" value="${ritmicaInstance?.frecuencia}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ritmicaInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="ritmica.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${ritmicaInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ritmicaInstance, field: 'rango', 'error')} ">
	<label for="rango">
		<g:message code="ritmica.rango.label" default="Rango" />
		
	</label>
	<g:textField name="rango" value="${ritmicaInstance?.rango}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ritmicaInstance, field: 'tiempo', 'error')} ">
	<label for="tiempo">
		<g:message code="ritmica.tiempo.label" default="Tiempo" />
		
	</label>
	<g:textField name="tiempo" value="${ritmicaInstance?.tiempo}"/>
</div>

