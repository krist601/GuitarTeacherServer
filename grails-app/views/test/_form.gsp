<%@ page import="teacher.Test" %>



<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'image', 'error')} required">
	<label for="image">
		<g:message code="test.image.label" default="Image" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="image" name="image.id" from="${teacher.Image.list()}" optionKey="id" required="" value="${testInstance?.image?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'practice', 'error')} required">
	<label for="practice">
		<g:message code="test.practice.label" default="Practice" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="practice" name="practice.id" from="${teacher.Practice.list()}" optionKey="id" required="" value="${testInstance?.practice?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'question', 'error')} required">
	<label for="question">
		<g:message code="test.question.label" default="Question" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="question" name="question.id" from="${teacher.Question.list()}" optionKey="id" required="" value="${testInstance?.question?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'theory', 'error')} required">
	<label for="theory">
		<g:message code="test.theory.label" default="Theory" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="theory" name="theory.id" from="${teacher.Theory.list()}" optionKey="id" required="" value="${testInstance?.theory?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="test.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${testInstance?.title}"/>
</div>

