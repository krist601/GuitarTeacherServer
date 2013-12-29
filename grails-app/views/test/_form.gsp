<%@ page import="teacher.Test" %>



<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'image', 'error')} ">
	<label for="image">
		<g:message code="test.image.label" default="Image" />
		
	</label>
	<g:select id="image" name="image.id" from="${teacher.Image.list()}" optionKey="id" value="${testInstance?.image?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'theory', 'error')} ">
	<label for="theory">
		<g:message code="test.theory.label" default="Theory" />
		
	</label>
	<g:select id="theory" name="theory.id" from="${teacher.Theory.list()}" optionKey="id" value="${testInstance?.theory?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'question', 'error')} ">
	<label for="question">
		<g:message code="test.question.label" default="Question" />
		
	</label>
	<g:select id="question" name="question.id" from="${teacher.Question.list()}" optionKey="id" value="${testInstance?.question?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'practice', 'error')} ">
	<label for="practice">
		<g:message code="test.practice.label" default="Practice" />
		
	</label>
	<g:select id="practice" name="practice.id" from="${teacher.Practice.list()}" optionKey="id" value="${testInstance?.practice?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'level', 'error')} required">
	<label for="level">
		<g:message code="test.level.label" default="Level" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="level" name="level.id" from="${teacher.Level.list()}" optionKey="id" required="" value="${testInstance?.level?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'testType', 'error')} required">
	<label for="testType">
		<g:message code="test.testType.label" default="Test Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="testType" name="testType.id" from="${teacher.TestType.list()}" optionKey="id" required="" value="${testInstance?.testType?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="test.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${testInstance?.title}"/>
</div>

