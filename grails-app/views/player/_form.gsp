<%@ page import="teacher.Player" %>



<div class="fieldcontain ${hasErrors(bean: playerInstance, field: 'nickname', 'error')} ">
	<label for="nickname">
		<g:message code="player.nickname.label" default="Nickname" />
		
	</label>
	<g:textField name="nickname" value="${playerInstance?.nickname}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: playerInstance, field: 'notification', 'error')} ">
	<label for="notification">
		<g:message code="player.notification.label" default="Notification" />
		
	</label>
	<g:textField name="notification" value="${playerInstance?.notification}"/>
</div>

