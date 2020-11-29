Installing and Running Chess Game

This project is divided into two Eclipse Projects: ChessServer, and ChessClient (two
additional projects for the UML diagrams).

Junit tests are under the ChessServer project.


1. Setup Database and run the SQL file (or use our database server)

There is only one table that is created, that is the players table. Set up your own
database how you like. Execute the SQL file, and the players table should be created.

After the database is set up, change your settings in db.properties, stored under the
top level ChessServer project. You can set user, password, and the url (colons must
be preceded by a backslash). 

If you would rather not use your own database server, or if for some reason it does
not work with our SQL, you can use the values provided, which are currently in 
db.properties. Our SQL server is hosted on a Google VPS and you can log in from
anywhere.



2. Run the server

Change the directory into the ChessServer project home directory. Run chessserver.bat
(or chessserver.sh if on macOS). 



3. Run the client

In a new command prompt, change directory into the ChessClient project home directory,
and run chessclient.bat (or chessclient.sh on macOS). 

If you want to connect to a server not on localhost, edit chessclient.bat. Replace the
localhost argument in the java command with the desired host, appending the port with
a colon. Example for connecting to 34.123.162.65 on port 25565: 34.123.162.65:25565