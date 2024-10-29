package edu.up.cs301.qwirklegame;

import edu.up.cs301.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.util.ArrayList;

/**
 * A GUI of a counter-player. The GUI displays the current value of the counter,
 * and allows the human player to press the '+' and '-' buttons in order to
 * send moves to the game.
 * 
 * Just for fun, the GUI is implemented so that if the player presses either button
 * when the counter-value is zero, the screen flashes briefly, with the flash-color
 * being dependent on whether the player is player 0 or player 1.
 * 
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @version July 2013
 */
public class QwirkleHumanPlayer extends GameHumanPlayer implements OnClickListener {

	/* instance variables */
	
	// The TextView the displays the current counter value
	private TextView testResultsTextView;
	
	// the most recent game state, as given to us by the QwirkleLocalGame
	private QwirkleState state;
	
	// the android activity that we are running
	private GameMainActivity myActivity;
	
	/**
	 * constructor
	 * @param name
	 * 		the player's name
	 */
	public QwirkleHumanPlayer(String name) {
		super(name);
	}

	/**
	 * Returns the GUI's top view object
	 * 
	 * @return
	 * 		the top object in the GUI's view heirarchy
	 */
	public View getTopView() {
		return myActivity.findViewById(R.id.edit_text_file);
	}
	
	/**
	 * sets the counter value in the text view
	 */
	protected void updateDisplay() {
		// set the text in the appropriate widget
		//counterValueTextView.setText("" + state.getCounter());
	}

	/**
	 * this method gets called when the user clicks the '+' or '-' button. It
	 * creates a new QwirkleMoveAction to return to the parent activity.
	 * 
	 * @param button
	 * 		the button that was clicked
	 */
	public void onClick(View button) {
		// if we are not yet connected to a game, ignore
		if (game == null) return;

		// clearing any previous text
		testResultsTextView.setText("");

		// making a new QwirkleState w default constructor
		QwirkleState firstInstance = new QwirkleState();

		// deep copy of firstInstance for player1
		QwirkleState firstCopy = new QwirkleState(firstInstance);

		// method calls, faux gameplay

		// Get the current player and draw tiles for both players
		int currPID = firstInstance.getCurrPlayer();
		firstInstance.drawTiles(currPID, 6);
		firstInstance.drawTiles(1 - currPID, 6);

		// Both hands for players
		ArrayList<QwirkleTile> hand0 = firstInstance.getPlayerHand(currPID);
		ArrayList<QwirkleTile> hand1 = firstInstance.getPlayerHand(1 - currPID);

		// Both scores for players
		int[] playersScore = firstInstance.getPlayersScore();

		// Player 0 gets the first tile and place it on the board (Red circle)
		QwirkleTile redCircle = hand0.get(0);
		firstInstance.setCurrTile(0);
		firstInstance.placeTile(new PlaceTileAction(this, redCircle, 0, 0));
		testResultsTextView.append("Player 0 placed a RED CIRCLE tile at 0,0 \n");

		// Player 0 ends turn
		playersScore[firstInstance.getCurrPlayer()] += 1;	// 1 point for player 1
		firstInstance.endTurn(new EndTurnAction(firstInstance, this, 2));
		testResultsTextView.append("Player 0 ended their turn and got +1 point. A YELLOW EIGHTSTAR was added to their hand \n");

		// Player 1 turn start
		hand1.get(1).setSelected(true);
		ArrayList<QwirkleTile> selected = firstInstance.getSelectedTiles(hand1);
		firstInstance.discardTiles(new DiscardTilesAction(this, selected), hand1);
		testResultsTextView.append("Player 1 discarded a RED EIGHTSTAR tile \n");

		// Player 1 ends their turn
		playersScore[firstInstance.getCurrPlayer()] += 0;	// 0 points for player 2
		firstInstance.endTurn(new EndTurnAction(firstInstance, this, 2));
		testResultsTextView.append("Player 1 ended their turn and got no points. An ORANGE CIRCLE was added to their hand \n");

		// Player 0 turn start
		QwirkleTile redSquare = hand0.get(2);
		QwirkleTile orangeSquare = hand0.get(3);
		firstInstance.setCurrTile(2);
		firstInstance.placeTile(new PlaceTileAction(this,redSquare,1,0));
		testResultsTextView.append("Player 0 placed a RED SQUARE tile at 1,0 \n");
		firstInstance.setCurrTile(3);
		firstInstance.placeTile(new PlaceTileAction(this,orangeSquare,1,1));
		testResultsTextView.append("Player 0 placed a ORANGE SQUARE tile at 1,1 \n");
		playersScore[firstInstance.getCurrPlayer()] += 2;
		firstInstance.endTurn(new EndTurnAction(firstInstance, this, 2));
		testResultsTextView.append("Player 0 ended their turn and got 2 points. A RED EIGHTSTAR was added to their hand \n");

		// Player 1 turn start
		QwirkleTile orangeFStar = hand1.get(2);
		QwirkleTile orangeDiamond = hand1.get(4);
		QwirkleTile orangeCircle = hand1.get(5);
		firstInstance.setCurrTile(5);
		firstInstance.placeTile(new PlaceTileAction(this, orangeCircle,0,1));
		testResultsTextView.append("Player 1 placed a ORANGE CIRCLE tile at 0,1 \n");
		firstInstance.setCurrTile(4);
		firstInstance.placeTile(new PlaceTileAction(this, orangeDiamond,3,1));
		testResultsTextView.append("Player 1 placed a ORANGE DIAMOND tile at 3,1 \n");
		firstInstance.setCurrTile(2);
		firstInstance.placeTile(new PlaceTileAction(this, orangeFStar,2,1));
		testResultsTextView.append("Player 1 placed a ORANGE FOURSTAR tile at 2,1 \n");
		playersScore[firstInstance.getCurrPlayer()] += 3;
		firstInstance.endTurn(new EndTurnAction(firstInstance, this, 2));
		testResultsTextView.append("Player 1 ended their turn and got 3 points. A NO TILES were added to their hand \n");

		// Player 0 turn start
		QwirkleTile orangeClover = hand0.get(1);
		firstInstance.setCurrTile(1);
		firstInstance.placeTile(new PlaceTileAction(this,orangeClover,4,1));
		testResultsTextView.append("Player 0 placed a ORANGE CLOVER tile at 4,1 \n");
		playersScore[firstInstance.getCurrPlayer()] += 1;
		firstInstance.endTurn(new EndTurnAction(firstInstance, this, 2));
		testResultsTextView.append("Player 0 ended their turn and got 1 point. A NO TILES were added to their hand \n");

		// Player 1 turn start
		QwirkleTile blueClover = hand1.get(1);
		firstInstance.setCurrTile(1);
		firstInstance.placeTile(new PlaceTileAction(this,blueClover,4,2));
		testResultsTextView.append("Player 1 placed a BLUE CLOVER tile at 4,2 \n");
		playersScore[firstInstance.getCurrPlayer()] += 1;
		firstInstance.endTurn(new EndTurnAction(firstInstance, this, 2));
		testResultsTextView.append("Player 1 ended their turn and got 1 point. A NO TILES were added to their hand \n");

		// Player 0 turn start
		QwirkleTile greenCircle = hand0.get(0);
		firstInstance.setCurrTile(0);
		firstInstance.placeTile(new PlaceTileAction(this,greenCircle,0,2));
		testResultsTextView.append("Player 0 placed a GREEN CIRCLE tile at 0,2 \n");
		playersScore[firstInstance.getCurrPlayer()] += 1;
		firstInstance.endTurn(new EndTurnAction(firstInstance, this, 2));
		testResultsTextView.append("Player 0 ended their turn and got 1 point. A NO TILES were added to their hand \n");

		// Player 1 turn start
		QwirkleTile blueCircle = hand1.get(0);
		firstInstance.setCurrTile(0);
		firstInstance.placeTile(new PlaceTileAction(this,blueCircle,0,3));
		testResultsTextView.append("Player 1 placed a BLUE CIRCLE tile at 0,3 \n");
		playersScore[firstInstance.getCurrPlayer()] += 1;
		firstInstance.endTurn(new EndTurnAction(firstInstance, this, 2));
		testResultsTextView.append("Player 1 ended their turn and got 1 point. A NO TILES were added to their hand \n");
		testResultsTextView.append("Player 1 has no more tiles, the game ends.\n");

		testResultsTextView.append("\n");

		// Second game state for checking
		QwirkleState secondInstance = new QwirkleState();
		QwirkleState secondCopy = new QwirkleState(secondInstance);	// Deep copy

		// Appending both game state instance toStrings to textview
		testResultsTextView.append(firstInstance.toString(firstCopy) + "\n");
		testResultsTextView.append(firstInstance.toString(secondCopy));
	}// onClick
	
	/**
	 * callback method when we get a message (e.g., from the game)
	 * 
	 * @param info
	 * 		the message
	 */
	@Override
	public void receiveInfo(GameInfo info) {
		// ignore the message if it's not a QwirkleState message
		if (!(info instanceof QwirkleState)) return;
		
		// update our state; then update the display
		this.state = (QwirkleState)info;
		updateDisplay();
	}
	
	/**
	 * callback method--our game has been chosen/rechosen to be the GUI,
	 * called from the GUI thread
	 * 
	 * @param activity
	 * 		the activity under which we are running
	 */
	public void setAsGui(GameMainActivity activity) {
		
		// remember the activity
		this.myActivity = activity;
		
	    // Load the layout resource for our GUI
		activity.setContentView(R.layout.edit_text);

		// intitialize testResultsTextView
		testResultsTextView = activity.findViewById(R.id.editTextTextMultiLine);

		// make this object the listener
		Button editText = (Button) activity.findViewById(R.id.run_test);
		editText.setOnClickListener(this);
        if (state != null) {
            receiveInfo(state);
        }
	}

}// class QwirkleHumanPlayer

