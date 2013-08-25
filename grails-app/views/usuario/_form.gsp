<%@ page import="teacher.Usuario" %>



<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'bornDate', 'error')} required">
	<label for="bornDate">
		<g:message code="usuario.bornDate.label" default="Born Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="bornDate" precision="day"  value="${usuarioInstance?.bornDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'country', 'error')} ">
	<label for="country">
		<g:message code="usuario.country.label" default="Country" />
		
	</label>
	<g:textField name="country" value="${usuarioInstance?.country}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="usuario.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${usuarioInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'password', 'error')} ">
	<label for="password">
		<g:message code="usuario.password.label" default="Password" />
		
	</label>
	<g:textField name="password" value="${usuarioInstance?.password}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'userName', 'error')} ">
	<label for="userName">
		<g:message code="usuario.userName.label" default="User Name" />
		
	</label>
	<g:textField name="userName" value="${usuarioInstance?.userName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'usuario', 'error')} required">
	<label for="usuario">
		<g:message code="usuario.usuario.label" default="Usuario" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="usuario" name="usuario.id" from="${teacher.Usuario.list()}" optionKey="id" required="" value="${usuarioInstance?.usuario?.id}" class="many-to-one"/>
</div>

