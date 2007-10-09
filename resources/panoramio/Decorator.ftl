<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
    <title>Panoramio - ${page.title}</title>
    <link rel="stylesheet" href="http://static.ak.facebook.com/css/common.css.pkg.php?c=19:12:60635" type="text/css" media="all"/>
    ${page.head}
</head>
<body onload='${page.properties["body.onload"]?default("")}' onunload='${page.properties["body.onunload"]?default("")}'>
    <div style="margin:20px;">
        <div style="margin-bottom:20px;">
            <a href="/facebook/panoramio/UserId.action">Display Map</a> |
            <a href="/facebook/panoramio/UserId!change.action">Set User Id</a> |
            <a href="http://www.panoramio.com/">Visit Panoramio</a>
            <hr style="height:1px; color: #ccc; background-color: #ccc; border: medium none;"/>
        </div>
        ${page.body}
    </div>
</body>
</html>