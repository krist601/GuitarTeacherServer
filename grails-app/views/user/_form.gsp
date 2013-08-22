<%@ page import="Teacher.User" %>



<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'bornDate', 'error')} required">
	<label for="bornDate">
		<g:message code="user.bornDate.label" default="Born Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="bornDate" precision="day"  value="${userInstance?.bornDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'country', 'error')} ">
	<label for="country">
		<g:message code="user.country.label" default="Country" />
		
	</label>
	<g:textField name="country" value="${userInstance?.country}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="user.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${userInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'score', 'error')} ">
	<label for="score">
		<g:message code="user.score.label" default="Score" />
		
	</label>
	<g:select name="score" from="${Teacher.Score.list()}" multiple="multiple" optionKey="id" size="5" value="${userInstance?.score*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'token', 'error')} ">
	<label for="token">
		<g:message code="user.token.label" default="Token" />
		
	</label>
	<g:textField name="token" value="${userInstance?.token}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'user', 'error')} ">
	<label for="user">
		<g:message code="user.user.label" default="User" />
		
	</label>
	<g:select name="user" from="${Teacher.User.list()}" multiple="multiple" optionKey="id" size="5" value="${userInstance?.user*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'userName', 'error')} ">
	<label for="userName">
		<g:message code="user.userName.label" default="User Name" />
		
	</label>
	<g:textField name="userName" value="${userInstance?.userName}"/>
</div>

