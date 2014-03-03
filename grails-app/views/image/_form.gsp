<%@ page import="teacher.Image" %>



<div class="fieldcontain ${hasErrors(bean: imageInstance, field: 'image', 'error')} required">
	<label for="image">
		<g:message code="image.image.label" default="Image" />
		<span class="required-indicator">*</span>
	</label>
	<input type="file" id="image" name="image" />
</div>

<div class="fieldcontain ${hasErrors(bean: imageInstance, field: 'question', 'error')} ">
	<label for="question">
		<g:message code="image.question.label" default="Question" />
		
	</label>
	<g:select id="question" name="question.id" from="${teacher.Question.list()}" optionKey="id" value="${imageInstance?.question?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: imageInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="image.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${imageInstance?.name}"/>
</div>

