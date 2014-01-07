<%@ page import="teacher.Player" %>



<div class="fieldcontain ${hasErrors(bean: playerInstance, field: 'lastConection', 'error')} required">
	<label for="lastConection">
		<g:message code="player.lastConection.label" default="Last Conection" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="lastConection" precision="day"  value="${playerInstance?.lastConection}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: playerInstance, field: 'nickname', 'error')} ">
	<label for="nickname">
		<g:message code="player.nickname.label" default="Nickname" />
		
	</label>
	<g:textField name="nickname" value="${playerInstance?.nickname}"/>
</div>

