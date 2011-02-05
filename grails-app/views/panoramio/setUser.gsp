<div>
    <g:hasErrors bean="${user}">
    <div class="errors">
        <g:renderErrors bean="${user}" as="list" />
    </div>
    </g:hasErrors>
    <g:form>
        <label for="panoramioId">Enter your user id here</label>
        <g:hiddenField name="facebookId" value="${user.facebookId}"/>
        <g:textField name="panoramioId" value="${user.panoramioId}" class="${hasErrors(bean: user, field: 'panoramioId', 'errors')}"/><br/>
        <g:actionSubmit id="setUser" value="Save" action="updateUser"/>
    </g:form>
</div>
<div>
    <p>
        You can find your Panoramio user id by logging onto <a href="http://www.panoramio.com/">Panoramio</a> and clicking 'Your Photos'.
    </p>
    <p>
        In the address bar of your web browser, you will see something like this:
    </p>
    <img src="<g:resource dir="images" file="url.gif"/>" alt="url"/>
    <p>
        In this case you would enter 344887 in the box above and click 'Save'.
    </p>
</div>
