package edu.up.cs301.qwirklegame;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.gameConfiguration.GameConfig;
import edu.up.cs301.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnClickListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * This contains the GUI for the QwirkleHumanPlayer player. The player can see their own hand,
 * their score along with all of the other player's scores, the board that they are placing
 * the tile on, and the buttons that correspond with being able to discard tiles, confirm their
 * selection, and end their turn.
 *
 * This also sends the current tile that is selected to the QwirkleView class, which draws the
 * tile onto the SurfaceView. So it is a controller that has its own view but also a controller
 * for the board view
 *
 * @author Chloe Pham
 * @author Talia Martinez
 * @author Tyler Crosbie
 * @author De'Ante Agleham
 * @author Ryan Murray
 *
 * @version November 11, 2024
 */
public class QwirkleHumanPlayer extends GameHumanPlayer implements OnClickListener, QwirkleView.OnTileTouchListener {

	/* instance variables */
	// Textviews for the human player
	private TextView tilesLeft;
	private TextView playerTurn;
	private TextView playerScore;
	private TextView player2Score;
	private TextView player3Score;
	private TextView player4Score;

	// the most recent game state, as given to us by the QwirkleLocalGame
	private QwirkleState state;
	
	// the android activity that we are running
	private GameMainActivity myActivity;

	// Tile IDs for the tile ImageButtons
	private static final int[] TILE_IDS = {
			R.id.tile1, R.id.tile2, R.id.tile3,
			R.id.tile4, R.id.tile5, R.id.tile6
	};

	// Tile R.drawable resources for the images!
	public static final int[][] TILE_RESOURCES = {
			// RED, BLUE, GREEN, YELLOW, PURPLE, ORANGE
			{R.drawable.tile_red_circle,    R.drawable.tile_blue_circle,    R.drawable.tile_green_circle,    R.drawable.tile_yellow_circle,    R.drawable.tile_purple_circle,    R.drawable.tile_orange_circle},    // CIRCLE
			{R.drawable.tile_red_square,    R.drawable.tile_blue_square,    R.drawable.tile_green_square,    R.drawable.tile_yellow_square,    R.drawable.tile_purple_square,    R.drawable.tile_orange_square},    // SQUARE
			{R.drawable.tile_red_diamond,   R.drawable.tile_blue_diamond,   R.drawable.tile_green_diamond,   R.drawable.tile_yellow_diamond,   R.drawable.tile_purple_diamond,   R.drawable.tile_orange_diamond},   // DIAMOND
			{R.drawable.tile_red_4star,     R.drawable.tile_blue_4star,     R.drawable.tile_green_4star,     R.drawable.tile_yellow_4star,     R.drawable.tile_purple_4star,     R.drawable.tile_orange_4star},     // FOURSTAR
			{R.drawable.tile_red_clover,    R.drawable.tile_blue_clover,    R.drawable.tile_green_clover,    R.drawable.tile_yellow_clover,    R.drawable.tile_purple_clover,    R.drawable.tile_orange_clover},    // CLOVER
			{R.drawable.tile_red_8star,     R.drawable.tile_blue_8star,     R.drawable.tile_green_8star,     R.drawable.tile_yellow_8star,     R.drawable.tile_purple_8star,     R.drawable.tile_orange_8star}      // EIGHTSTAR
	};

	// Array of ImageButtons for all 6 tiles
	private ImageButton[] tileButtons;

	// An instance of the QwirkleView (so we can add a tile to the board view)
	private QwirkleView qwirkleView;

	private QwirkleTile selectedTile;

	private boolean canPlace = true;
	private boolean canDiscard = false;

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
		return myActivity.findViewById(R.id.main);
	}
	
	/**
	 * sets the counter value in the text view
	 */
	protected void updateDisplay() {
		updateHandDisplay();
		// Update text views
		tilesLeft.setText(String.valueOf(state.getTilesLeft()));
		playerTurn.setText(String.valueOf(state.getCurrPlayer() + 1));
		resetTileBackground();
	}

	/**
	 * this method gets called when the user clicks end turn or discard mode or confirm
	 * 
	 * @param button
	 * 		the button that was clicked
	 */
	public void onClick(View button) {
		// if we are not yet connected to a game, ignore
		if (game == null) {
			return;
		}
		// Check if it's the current player's turn
		if (this.playerNum != state.getCurrPlayer()) {
			return;
		}

		// If the button pressed is the end turn
		if (button.getId() == R.id.end_turn) {
			// Create a new end turn action and then update the display
			EndTurnAction end = new EndTurnAction(state, this, state.getNumPlayers());
			game.sendAction(end); // send action to the game
			canPlace = true;
			canDiscard = true;

			// Reset backgrounds for all buttons
			resetTileBackground();
		}
		else if (button.getId() == R.id.discard) {
			if (canDiscard == false) {
				this.flash(0xFFFF4325, 100);
				return;
			}
			canPlace = false;
			// Create a new discard tile action and then update the hand and the bag
			// Make sure a tile is selected
			if (state.getCurrTile() < 0 || state.getCurrTile() >= state.getPlayerHand(state.getCurrPlayer()).size()) {
				return;
			}

			// Get the current player's hand and selected tile
			ArrayList<QwirkleTile> hand = state.getPlayerHand(state.getCurrPlayer());
			selectedTile = hand.get(state.getCurrTile());
			if (selectedTile == null) {
				return;
			}
			DiscardTilesAction discardAction = new DiscardTilesAction(this, selectedTile, state.getCurrTile());
			game.sendAction(discardAction);
		}
		else {
			// Else these are the tile image buttons
			// Check if it's a tile button
			for (int i = 0; i < TILE_IDS.length; i++) {
				if (button.getId() == TILE_IDS[i]) {
					state.setCurrTile(i);	// Set the current tile index
					notifyBoardView();	// Let the board know what tile it is

					// Reset backgrounds for all buttons
					resetTileBackground();

					// Highlight the selected button
					tileButtons[i].setBackgroundResource(R.drawable.tile_highlight);
					return;  // Exit after handling tile
				}
			}
		}
	}// onClick

	/**
	 * If the tile is touched, create a new place tile action
	 * @param x
	 * @param y
	 */
	@Override
	public void onTileTouched(int x, int y) {
		// If we are not yet connected to a game, ignore
		if (game == null) {
			return;
		}

		// Check if it's the current player's turn
		if (this.playerNum != state.getCurrPlayer()) {
			return;
		}

		// Make sure a tile is selected
		if (state.getCurrTile() < 0 || state.getCurrTile() >= state.getPlayerHand(state.getCurrPlayer()).size()) {
			return;
		}
		if (canPlace == false) {
			this.flash(0xFFFF4325, 100);
			return;
		}
		// Get the current player's hand and selected tile
		ArrayList<QwirkleTile> hand = state.getPlayerHand(state.getCurrPlayer());
		selectedTile = hand.get(state.getCurrTile());

		// Create the PlaceTileAction
		PlaceTileAction place = new PlaceTileAction(this, selectedTile, x, y, state.getCurrTile());

		// First check if the move is valid
		if (state.isValid(selectedTile, x, y)) {
			canDiscard = false;
			// Send the action to the game
			game.sendAction(place);
			// Reset backgrounds for all buttons
			resetTileBackground();
		} else {
			// If invalid, flash
			this.flash(0xFFFF4325, 100); // Flash red for invalid move
		}
	}

	public void resetTileBackground() {
		// Reset backgrounds for all buttons
		for (ImageButton tileButton : tileButtons) {
			tileButton.setBackgroundResource(android.R.color.transparent); // Default background
		}
	}
	
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

		playerScore.setText(String.valueOf(state.getPlayersScore()[0]));
		player2Score.setText("Player 2: " + state.getPlayersScore()[1]);

		// Only update player 3 score if there are 3 or more players
		if (state.getPlayersScore().length >= 3) {
			player3Score.setText("Player 3: " + state.getPlayersScore()[2]);
			player3Score.setVisibility(View.VISIBLE);
		} else {
			player3Score.setVisibility(View.GONE);
		}

		// Only update player 4 score if there are 4 players
		if (state.getPlayersScore().length == 4) {
			player4Score.setText("Player 4: " + state.getPlayersScore()[3]);
			player4Score.setVisibility(View.VISIBLE);
		} else {
			player4Score.setVisibility(View.GONE);
		}

		// Update the board view with the current state
		updateHandDisplay();
		updateDisplay();
		qwirkleView.updateFromGameState(state.getBoard());

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
		activity.setContentView(R.layout.qwirkle_human_player);

		// Buttons for the game
		Button endTurnButton = (Button) activity.findViewById(R.id.end_turn);
		endTurnButton.setOnClickListener(this);

		// Discard button for tiles
		Button discardButton = (Button) activity.findViewById(R.id.discard);
		discardButton.setOnClickListener(this);

		// Initialize tile buttons array
		tileButtons = new ImageButton[TILE_IDS.length];

		// Set up all tile buttons in a loop
		for (int i = 0; i < TILE_IDS.length; i++) {
			tileButtons[i] = activity.findViewById(TILE_IDS[i]);
			tileButtons[i].setOnClickListener(this);
		}

		// Set up the textviews that the player sees
		this.tilesLeft = (TextView) activity.findViewById(R.id.tiles_left);
		this.playerTurn = (TextView) activity.findViewById(R.id.player_turn);
		this.playerScore = (TextView) activity.findViewById(R.id.player_score);
		this.player2Score = (TextView) activity.findViewById(R.id.player2);
		this.player3Score = (TextView) activity.findViewById(R.id.player3);
		this.player4Score = (TextView) activity.findViewById(R.id.player4);

		// Get the QwirkleView instance and set the touch listener
		qwirkleView = (QwirkleView) activity.findViewById(R.id.boardView);
		qwirkleView.setOnTileTouchListener(this); // Set this class as the OnTileTouchListener

		if (state != null) {
			receiveInfo(state);
		}
	}

	/**
	 * This notifies the board view of the currently selected tile resource
	 */
	private void notifyBoardView() {
		// Checking for hand bounds
		if (state.getCurrTile() >= 0 && state.getCurrTile() < tileButtons.length) {
			// Get the tile
			QwirkleTile tile = state.getPlayerHand(state.getCurrPlayer()).get(state.getCurrTile());
			if (tile == null) {
				this.flash(0xFFFF4325, 100);
				return;
			}
			// Set the selected tile
			qwirkleView.setSelectedTile(tile.getTileImageFile(tile));
		}
	}

	/**
	 * Updates the hand of the player and set the image tile resources
	 */
	public void updateHandDisplay() {
		if (this.playerNum == state.getCurrPlayer()) {
			// Get the player's hand
			ArrayList<QwirkleTile> hand = state.getPlayerHand(state.getCurrPlayer());
			int imageResource;

			// Update each tile button based on the hand
			for (int i = 0; i < tileButtons.length; i++) {
				QwirkleTile tile = hand.get(i);
				if (tile == null) {
					imageResource = R.drawable.tile_blank;
				}
				else {
					imageResource = tile.getTileImageFile(tile);
				}
				tileButtons[i].setImageResource(imageResource);
			}
		}
	}
}// class QwirkleHumanPlayer

/**
 * External Citation
 *
 * Problem: Getting enum ordinal values
 * Source: https://www.tutorialspoint.com/java/lang/enum_ordinal.htm
 *
 * Date: November 10, 2024
 */

/**
 * External Citation
 *
 * Problem: Highlighting buttons when selected
 * Source: https://stackoverflow.com/questions/8339529/android-using-layer-list-for-button-selector
 *
 * Date: November 25, 2024
 */
