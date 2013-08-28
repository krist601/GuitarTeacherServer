<%@ page import="teacher.Friend" %>



<div class="fieldcontain ${hasErrors(bean: friendInstance, field: 'friend', 'error')} required">
	<label for="friend">
		<g:message code="friend.friend.label" default="Friend" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="friend" name="friend.id" from="${teacher.Usuario.list()}" optionKey="id" required="" value="${friendInstance?.friend?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: friendInstance, field: 'usuario', 'error')} required">
	<label for="usuario">
		<g:message code="friend.usuario.label" default="Usuario" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="usuario" name="usuario.id" from="${teacher.Usuario.list()}" optionKey="id" required="" value="${friendInstance?.usuario?.id}" class="many-to-one"/>
</div>

