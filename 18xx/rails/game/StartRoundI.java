/* $Header: /Users/blentz/rails_rcs/cvs/18xx/rails/game/StartRoundI.java,v 1.4 2007/10/05 22:02:28 evos Exp $ */
package rails.game;

import java.util.List;

import rails.game.action.PossibleAction;
import rails.game.model.ModelObject;

public interface StartRoundI extends RoundI
{

	public void start(StartPacket startPacket);

	public List<StartItem> getStartItems ();

	public StartPacket getStartPacket();

	public int getCurrentPlayerIndex();

	public boolean process (PossibleAction action);
	
	public ModelObject getBidModel (int privateIndex, int playerIndex);
	
	public ModelObject getMinimumBidModel (int privateIndex);

	public ModelObject getFreeCashModel (int playerIndex);

	public ModelObject getBlockedCashModel (int playerIndex);
}
