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
    <script>
        $(function() {
            var init = function() {
                FB.init({
                    appId  : '6995620803',
                    status : true, // check login status
                    cookie : true, // enable cookies to allow the server to access the session
                    xfbml  : true  // parse XFBML
                });

                FB.getLoginStatus(function(response) {
                    if (response.session) {
                        doStuff();
                    } else {
                        $('#connecting').hide();
                        $('#connect').click(connect).show();
                    }
                });
            },

            connect = function(ev) {
                ev.preventDefault();
                FB.login(function(response) {
                    if (response.session) {
                        $('#connect').hide();
                        $('#connecting').show();
                        doStuff();
                    }
                });
            },

            doStuff = function() {
                console.log('doing stuff')
            };

            $(init);
        });

    </script>
</head>
<body>
<div id="fb-root">
    <img id="connecting" src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Loading"/>
    <h2 id="connect" style="display:none">Please click here to log onto facebook</h2>
</div>
</body>
</html>