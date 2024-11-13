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
		playerTurn.setText(String.valueOf(state.getCurrPlayer()));
		playerScore.setText(String.valueOf(state.getPlayersScore()[state.getCurrPlayer()]));
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

//			updateHandDisplay();
			game.sendAction(end); // send action to the game
		}
		else {
			// Else these are the tile image buttons
			// Check if it's a tile button
			for (int i = 0; i < TILE_IDS.length; i++) {
				if (button.getId() == TILE_IDS[i]) {
//					selectedTileIndex = i; // Store selected tile index
					state.setCurrTile(i);	// Set the current tile index
					notifyBoardView();	// Let the board know what tile it is!
					return;  // Exit after handling tile
				}
			}
			// Not a tile or recognized button
			return;
		}
	}// onClick

	/**
	 * If the tile is touched, create a new place tile action
	 * @param x
	 * @param y
	 */
	@Override
	public void onTileTouched(int x, int y) {
		// if we are not yet connected to a game, ignore
		if (game == null) {
			return;
		}
		// Check if it's the current player's turn
		if (this.playerNum != state.getCurrPlayer()) {
			this.flash(0xFFFF0000, 200);
		}
		if (state != null) {
			// Create the PlaceTileAction with the current tile index (currTile) and coordinates (x, y)
			ArrayList<QwirkleTile> hand = state.getPlayerHand(state.getCurrPlayer());
			PlaceTileAction place = new PlaceTileAction(this, hand.get(state.getCurrTile()), x, y, state.getCurrTile());
			// If place tile is valid, send the action to local game
			if (state.placeTile(place)) {
				game.sendAction(place);
			}
			// else flash the screen
			else {
				this.flash(0xFFFF0000, 200);
			}
			// Send the PlaceTileAction to the game
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
		updateDisplay();
		updateHandDisplay();
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

		// Get the QwirkleView instance and set the touch listener
		qwirkleView = (QwirkleView) activity.findViewById(R.id.boardView);
		qwirkleView.setOnTileTouchListener(this); // Set this class as the OnTileTouchListener
		
		// if we have a game state, "simulate" that we have just received
		// the state from the game so that the GUI values are updated
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
			// Set the selected tile
			qwirkleView.setSelectedTile(getTileImageFile(tile));
		}
	}

	/**
	 * Updates the hand of the player and set the image tile resources
	 */
	public void updateHandDisplay() {
		if (this.playerNum == state.getCurrPlayer()) {
			// Get the player's hand
			ArrayList<QwirkleTile> hand = state.getPlayerHand(state.getCurrPlayer());

			// Update each tile button based on the hand
			for (int i = 0; i < tileButtons.length; i++) {
				QwirkleTile tile = hand.get(i);
				int imageResource = getTileImageFile(tile);
				tileButtons[i].setImageResource(imageResource);
			}
		}
	}

	/**
	 * Takes a QwirkleTile and matches the image file name to the tile from
	 * the TILE_RESOURCES array
	 *
	 * @param tile
	 * @return
	 */
	public int getTileImageFile(QwirkleTile tile) {
		// No tile, draw blank tile
		if (tile == null) { return R.drawable.tile_blank; }
		// Return the right tile image with the enum values
		return TILE_RESOURCES[tile.getShape().ordinal()][tile.getColor().ordinal()];
	}

	/**
	 * Prints out the board
	 * @param board
	 */
	private void checkBoardState(QwirkleTile[][] board) {
		QwirkleTile[][] boardTiles = state.getBoard().getTiles();
		for (int row = 0; row < boardTiles.length; row++) {
			for (int col = 0; col < boardTiles[row].length; col++) {
				QwirkleTile tile = boardTiles[row][col];
//				if (tile != null) {
//					System.out.println("Tile at (" + row + ", " + col + "): " + tile);
//				} else {
//					System.out.println("No tile at (" + row + ", " + col + ")");
//				}
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
