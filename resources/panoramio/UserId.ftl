<html>
<head>
    <link rel="stylesheet" href="http://static.ak.facebook.com/css/common.css.pkg.php?c=19:12:60635" type="text/css" media="all"/>
</head>
<body>
<form action="<@s.url action='UserId' method='save'/>" method="get">
    <input type="text" name="userId"/> Enter your user Id here
    <input type="submit" value="Save"/>
    <#if anyError>
        <p class="error">${errorMessage}</p>
    </#if>
</form>
</body>
</html>