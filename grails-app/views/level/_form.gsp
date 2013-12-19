<%@ page import="teacher.Level" %>



<div class="fieldcontain ${hasErrors(bean: levelInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="level.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${levelInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: levelInstance, field: 'difficulty', 'error')} ">
	<label for="difficulty">
		<g:message code="level.difficulty.label" default="Difficulty" />
		
	</label>
	<g:textField name="difficulty" value="${levelInstance?.difficulty}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: levelInstance, field: 'image', 'error')} required">
	<label for="image">
		<g:message code="level.image.label" default="Image" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="image" name="image.id" from="${teacher.Image.list()}" optionKey="id" required="" value="${levelInstance?.image?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: levelInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="level.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${levelInstance?.name}"/>
</div>

