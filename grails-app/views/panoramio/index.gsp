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
                        doStuff(response.session.uid);
                    } else {
                        $('#connecting').hide();
                        $('#connect').click(connect).show();
                    }
                });

                $('#setUser').live('click', function(ev) {
                    ev.preventDefault();
                    var button = $(this),
                        form = button.parent(),
                        url =  form.attr('action'),
                        inputs = $(':input', form),
                        dataString = "submitted=true";

                    inputs.each(function() {
                        dataString += '&'+this.name +'='+ $(this).val();
                    });

                    $.ajax({
                        type: "POST",
                        url: url,
                        data: dataString,
                        success: function(data) {
                            $('#fb-root').html(data);
                        }
                    });

                    return false;
                });
            },

            connect = function(ev) {
                ev.preventDefault();
                FB.login(function(response) {
                    if (response.session) {
                        $('#connect').hide();
                        $('#connecting').show();
                        doStuff(response.session.uid);
                    }
                });
            },

            doStuff = function(userId) {
                $('#fb-root').load("<g:createLink action="checkUser"/>/"+userId)
            };

            $(init);
        });

    </script>
</head>
<body>
<div id="fb-root">
    <img id="connecting" src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Loading"/>
    <h2 id="connect" style="display:none">Please click here to log onto Facebook</h2>
</div>
</body>
</html>