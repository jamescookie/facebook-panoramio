<html>
<head>
    <title>Set User Id</title>
</head>
<body>
<div>
<form action="<@s.url action='UserId' method='save'/>" method="post">
    <label for="userId">Enter your user id here</label>
    <input id="userId" type="text" name="userId"/><br/>
    <input type="submit" value="Save" class="inputsubmit"/>
    <#if anyError>
        <p class="error">${errorMessage}</p>
    </#if>
</form>
</div>
<div>
    <p>
        You can find your Panoramio user id by logging onto <a href="http://www.panoramio.com/">Panoramio</a> and clicking 'Your Photos'.
    </p>
    <p>
        In the address bar of your web browser, you will see something like this:
    </p>
    <img src="${fullURL}/url.gif" alt="url"/>
    <p>
        In this case you would enter 344887 in the box above and click 'Save'.
    </p>
</div>
</body>
</html>