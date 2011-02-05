<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <style type="text/css" media="screen">
        #connect {
            cursor: pointer;
        }
    </style>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script src="http://connect.facebook.net/en_US/all.js"></script>
    <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAbZ4Jaxje4fqwUIzdlhU7jhS746Z0JIdejkzocDyQ_2hnWIfSeBSdho-FRRwP2YcYtt9eNGhFVxmU9A"></script>
    <script src="<g:resource dir="js" file="markermanager.js" />"></script>
    <script src="<g:resource dir="js" file="pano_layer.js" />"></script>
    <script src="<g:resource dir="js" file="site.js" />"></script>
</head>
<body>
<div id="fb-root">
    <img id="connecting" src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Loading"/>
    <h2 id="connect" style="display:none">Please click here to log onto Facebook</h2>
    <a id="checkUser" style="display:none" href="<g:createLink action="checkUser"/>">&nbsp;</a>
</div>
</body>
</html>