<%@ page import="teacher.Acorde" %>



<div class="fieldcontain ${hasErrors(bean: acordeInstance, field: 'frecuencia', 'error')} required">
	<label for="frecuencia">
		<g:message code="acorde.frecuencia.label" default="Frecuencia" />
		<span class="required-indicator">*</span>
	</label>
	
</div>

<div class="fieldcontain ${hasErrors(bean: acordeInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="acorde.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${acordeInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: acordeInstance, field: 'rango', 'error')} required">
	<label for="rango">
		<g:message code="acorde.rango.label" default="Rango" />
		<span class="required-indicator">*</span>
	</label>
	
</div>

