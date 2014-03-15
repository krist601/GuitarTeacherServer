<%@ page import="teacher.Level" %>



<div class="fieldcontain ${hasErrors(bean: levelInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="level.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${levelInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: levelInstance, field: 'difficulty', 'error')} required">
	<label for="difficulty">
		<g:message code="level.difficulty.label" default="Difficulty" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="difficulty" type="number" value="${levelInstance.difficulty}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: levelInstance, field: 'disabled', 'error')} required">
	<label for="disabled">
		<g:message code="level.disabled.label" default="Disabled" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="disabled" name="disabled.id" from="${teacher.Image.list()}" optionKey="id" required="" value="${levelInstance?.disabled?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: levelInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="level.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${levelInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: levelInstance, field: 'normal', 'error')} required">
	<label for="normal">
		<g:message code="level.normal.label" default="Normal" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="normal" name="normal.id" from="${teacher.Image.list()}" optionKey="id" required="" value="${levelInstance?.normal?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: levelInstance, field: 'success', 'error')} required">
	<label for="success">
		<g:message code="level.success.label" default="Success" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="success" name="success.id" from="${teacher.Image.list()}" optionKey="id" required="" value="${levelInstance?.success?.id}" class="many-to-one"/>
</div>

