<html>
<head>
    <title>Invite Friends</title>
</head>
<body>
Invite friends here....
<fb:request-form
    action="start.htm"
    method="POST"
    invite="true"
    type="nutshOt network"
    content="nutshOt network is the best place on Facebook for viewing, sharing and giving
             friends the   highest quality nutshOts.  Join me on the nutshOt network!
	     <fb:req-choice url='http://www.facebook.com/add.php?api_key=${apiKey}' />'
             label='Check out the nutshOt network!' />
    ">

	<fb:multi-friend-selector
		showborder="false"
		actiontext="Invite your friends to the nutshOt network."
		exclude_ids=""
		max="20" />
</fb:request-form>
</body>
</html>
