# Chess Game

Built as a team project for CSCI4490, the UCA Computer Science Department capstone course. It is a nearly complete implementation of the game of chess
allowing two separate computers to connect to a server, enabling the two players to play against each other on separate computers. It makes use of the
Object Server-Client Framework, a TCP framework that allows socket communication of Java serializable objects. The server connects to a MySQL database
to keep track of players' login information and statistics.

The server project consists of three main packages:
1. The *gamelogic* package represents the game in OOP, processes individual games, finding legal moves, and looking for check, checkmate, and stalemate. 
2. The *playermanager* package controls the logged in players, the waiting room, and the database.
3. The *communication* package prepares, sends, and handles data between client and the former two packages.

The client project is built with the Model-View-Controller design pattern. Three main packages build the GUI, and a fourth package for communications.
1. The *loginscreen* package takes care of both the login screen and create account stages of the client navigation.
2. The *waitingroom* package takes care of the waiting room stage of navigation, including related views and controllers.
3. The *gamescreen* package manages displaying and controlling a player's current game.
4. The *communication* package prepares, sends, and handles data between the server and the different stages of navigation.
