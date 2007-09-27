<html>
<head>
    <title>Set User Id</title>
</head>
<body>
<form action="<@s.url action='UserId' method='save'/>" method="post">
    <label for="userId">Enter your user Id here</label>
    <input id="userId" type="text" name="userId"/><br/>
    <input type="submit" value="Save"/>
    <#if anyError>
        <p class="error">${errorMessage}</p>
    </#if>
</form>
</body>
</html>