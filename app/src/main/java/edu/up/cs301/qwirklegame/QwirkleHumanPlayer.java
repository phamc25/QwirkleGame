package edu.up.cs301.qwirklegame;

import edu.up.cs301.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
		firstInstance.setNumPlayers(2);

		int currPID = firstInstance.getCurrPlayer();
		ArrayList<QwirkleTile> hand = firstInstance.getPlayerHand(currPID);
		QwirkleTile firstTile = hand.get(0);
		PlaceTileAction pta = new PlaceTileAction(this, firstTile, 0, 0);
		firstInstance.placeTile(pta);
		//testResultsTextView.setText("Two players begin the game");
		//for (int i = 0; i < firstInstance.getSelectedTiles().size(); i++) {
			//int index = firstCopy.getPlayerHand();
		//}
		firstInstance.discardTiles(new DiscardTilesAction(this, firstInstance.getSelectedTiles()));

		// checking
		QwirkleState secondInstance = new QwirkleState();

		// deepcopy for checking
		QwirkleState secondCopy = new QwirkleState(secondInstance);

		String firstString = firstCopy.toString();
		String secondString = secondCopy.toString();

		testResultsTextView.append(firstString + secondString);




		// Construct the action and send it to the game
		// GameAction action = null;
//		if (button.getId() == R.id.plusButton) {
//			// plus button: create "increment" action
//			action = new QwirkleMoveAction(this, true);
//		}
//		else if (button.getId() == R.id.minusButton) {
//			// minus button: create "decrement" action
//			action = new QwirkleMoveAction(this, false);
//		}
		// if (button.getId() == R.id.end_button) {
		//	action = new EndTurnAction(state, action.getPlayer());

		//} else {
			// something else was pressed: ignore
			return;
			//}
		
		//game.sendAction(action); // send action to the game
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

//		Button minusButton = (Button) activity.findViewById(R.id.minusButton);
//		minusButton.setOnClickListener(this);
//		Button endTurnButton = (Button) activity.findViewById(R.id.end_button);
//		endTurnButton.setOnClickListener(this);

		// remember the field that we update to display the counter's value
		//this.counterValueTextView =
		//		(TextView) activity.findViewById(R.id.counterValueTextView);
		
		// if we have a game state, "simulate" that we have just received
		// the state from the game so that the GUI values are updated
		//if (state != null) {
			receiveInfo(state);
		//}
	}

}// class QwirkleHumanPlayer

