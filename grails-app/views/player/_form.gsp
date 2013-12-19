<%@ page import="teacher.Player" %>



<div class="fieldcontain ${hasErrors(bean: playerInstance, field: 'nickname', 'error')} ">
	<label for="nickname">
		<g:message code="player.nickname.label" default="Nickname" />
		
	</label>
	<g:textField name="nickname" value="${playerInstance?.nickname}"/>
</div>

