package rails.game.specific._18AL;

import java.util.ArrayList;
import java.util.List;

import rails.common.LocalText;
import rails.game.*;
import rails.game.action.LayBaseToken;
import rails.game.action.PossibleAction;
import rails.game.state.MoveUtils;

public class OperatingRound_18AL extends OperatingRound {

    public OperatingRound_18AL (GameManagerI gameManager) {
        super (gameManager);
    }

    @Override
    protected void setGameSpecificPossibleActions() {
        // if optimized no need to assign
        if (getGameOption("18ALOptimizeNamedTrains").equalsIgnoreCase("yes")) return;
        
        for (NameTrains stl : getSpecialProperties(NameTrains.class)) {
            List<TrainI> trains =
                    operatingCompany.get().getPortfolio().getTrainList().view();
            if (trains != null && !trains.isEmpty()) {
                possibleActions.add(new AssignNamedTrains(stl, trains));
            }

        }
    }

    @Override
    public boolean processGameSpecificAction(PossibleAction action) {

        if (action instanceof AssignNamedTrains) {

            AssignNamedTrains namingAction = (AssignNamedTrains) action;
            List<NameableTrain> trains = namingAction.getNameableTrains();
            List<NameableTrain> newTrainsPerToken =
                    namingAction.getPostTrainPerToken();
            List<NamedTrainToken> tokens = namingAction.getTokens();

            List<NamedTrainToken> newTokenPerTrain =
                    new ArrayList<NamedTrainToken>(trains.size());

            NameableTrain newTrain;
            NamedTrainToken oldToken, newToken;

            for (int i = 0; i < trains.size(); i++) {
                newTokenPerTrain.add(null);
            }
            for (int i = 0; i < tokens.size(); i++) {
                newTrain = newTrainsPerToken.get(i);
                if (newTrain != null)
                    newTokenPerTrain.set(trains.indexOf(newTrain),
                            tokens.get(i));
            }

            changeStack.start(true);

            for (int i = 0; i < trains.size(); i++) {
                oldToken = trains.get(i).getNameToken();
                newToken = newTokenPerTrain.get(i);
                if (oldToken != newToken) {
                    trains.get(i).setNameToken(newToken);
                    if (newToken != null) {
                        ReportBuffer.add(LocalText.getText("NamesTrain",
                                operatingCompany.get().getId(),
                                trains.get(i).getId(),
                                newToken.getLongName() ));
                    }
                }
            }
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean layBaseToken(LayBaseToken action) {
        if (super.layBaseToken(action)) {
            MapHex hex = action.getChosenHex();
            if (hex == operatingCompany.get().getDestinationHex()) {
                int payout = 100;
                MoveUtils.cashMove(bank, operatingCompany.get(), payout);
                ReportBuffer.add(LocalText.getText("DestinationReachedByToken",
                        operatingCompany.get().getId(),
                        Bank.format(payout),
                        hex.getId() ));
            }
            return true;
        } else {
            return false;
        }

    }
}
