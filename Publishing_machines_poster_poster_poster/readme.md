Notes Publishing Machines - Sarah Garcin

Poster Poster Poster Poster Poster: A poster that makes posters
Poster, partly covered in conductive ink and works as hardware, which generates new posters on. A poster to design posters. Paper interface to remix your protest poster designs. Protest away.
Originated from the thought about the situation if you are marching with a poster and then you have a better idea and you need a new poster. (Can you make a new poster with your current poster?)
	Paper interface for the PJ machine using makey makey. We remapped the keys of the makey to match the PJ Machine controls. 

Group: 
Jannete Mark
William Eckerstein
Loes Bogers
James Aspey

Workshop tutorial git: 
https://github.com/sarahgarcin/pj-machine-workshop

Installation
MBlock
http://www.mblock.cc/docs/run-makeblock-ch340-ch341-on-mac-os-sierra/ 
Only if you already installed in and need to update go through the tutorial, otherwise just install.

Node.js
https://nodejs.org/en/ 

See tutorial page for install instructions. 

Starting PJ Machine
In terminal: start the node server
node server.js

Go to local host 1337 in browser
http://localhost:1337/

Tutorial page github
https://github.com/sarahgarcin/pj-machine-workshop

Tips
works only in Chrome, not firefox. If it stops: ctrl + c in terminal and restart the node serverjs

Keyboard events PJ Machine
The PJ Machine has some functions include by default you can use. All functions can be fired by a keypress.
	•	O > Go to previous content
	•	P > Go to next content
	•	A > Move Left
	•	Q > Move Right
	•	W > Move Down
	•	S > Move Up
	•	U > Zoom In
	•	Space Bar > Zoom Out
	•	I > Decrease block width
	•	E > Increase block width
	•	Y > Increase letter spacing
	•	R > Decrease letter spacing
	•	N > Change font (random in an array of fonts)
	•	B > Change color (random)
	•	M > Rotate Clockwise
	•	L > Rotate Counterclockwise
	•	T > Generate PDF


Key mappings to MAKEY (see .h file that comes with arduino makey code).

  's',      // up arrow pad check  >> MOVE UP
  'w',    // down arrow pad check  >> MOVE DOWN
  'a',    // left arrow pad check  >> MOVE LEFT
  'q',   // right arrow pad check  >> MOVE RIGHT
  ' ',               // space button pad check
  'm',        // click button pad check  >> ROTATE CLOCKWISE 
  't',                // pin D5 check  >> GENERATE PDF
  'i',                // pin D4 check  >> DECREASE WIDTH
  'e',                // pin D3 check  >> INCREASE WIDTH
  'y',                // pin D2 check
  'r',                // pin D1 check
  'n',                // pin D0 check  
  'b',         // pin A5 check
  'u',        // pin A4 check
  'o',        // pin A3 check >> PREVIOUS BLOCK
  'p',        // pin A2 check
  'l',        // pin A1 check >> ROTATE COUNTER CLOCKWISE
  's'         // pin A0


Node server will keep crashing
The node server keeps crashing if you run the makey board. To avoid this, go into 
main.js in the main folder and comment out these two sections: 

//  var board = new five.Board();

and

//    board.on("ready", function(socket) { // Call arduino board
//      console.log("--- ARDUINO READY ---- ");
//      
//      // write your arduino functions here
//      arduinoJoystick();
//
//    });

