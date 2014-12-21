word-count-rest
===============

Rest API to count words in store

This project contains 2 text files which work as text store - wordStore1.txt, wordStore2.txt
REST API methods are exposed to query the count of any word in the text store.

Executing Service :
--------------------

1. Deploying on a server :
  ------------------------
	Generate a war by issuing either :-
	
		mvn clean install 
			OR 
		mvn package
		
	This creates the war inside /target. Deploy the war on any web server (tomcat, jetty etc.)
    
	REST endpoint to query for occurences of a word -> /<context-path>/api/wc?query=<word>

2. Running using embedded jetty plugin:
  --------------------------------------
   Project has an embedded jetty plugin which can be used to start the service on command-line
   
   Use command :-  mvn jetty:run
   
   REST endpoint to query for occurences of a word -> /api/wc?query=<word>
