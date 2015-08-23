package net.sf.rails.game.specific._1844;

import java.util.Set;

import org.jgrapht.graph.SimpleGraph;

import net.sf.rails.algorithms.NetworkEdge;
import net.sf.rails.algorithms.NetworkGraph;
import net.sf.rails.algorithms.NetworkVertex;
import net.sf.rails.common.LocalText;
import net.sf.rails.common.ReportBuffer;
import net.sf.rails.game.Bank;
import net.sf.rails.game.BaseToken;
import net.sf.rails.game.PublicCompany;
import net.sf.rails.game.RailsItem;
import net.sf.rails.game.StockSpace;
import net.sf.rails.game.Stop;
import net.sf.rails.game.specific._1880.Investor_1880;
import net.sf.rails.game.specific._1880.PublicCompany_1880;
import net.sf.rails.game.state.BooleanState;
import net.sf.rails.game.state.Currency;
import net.sf.rails.game.state.Owner;

public class PublicCompany_1844 extends PublicCompany {

    private BooleanState fullyCapitalized = BooleanState.create(this, "fullyCapitalized", false);
    private BooleanState fullCapitalAvailable = BooleanState.create (this, "fullCapitalAvailable", false);
    
    private int extraCapital = 0; // Just one Change at Start of the game, can stay as it is..

    
    public PublicCompany_1844(RailsItem parent, String id) {
        super(parent, id);
        // TODO Auto-generated constructor stub
    }
    
    public void start(StockSpace startSpace) {
         if(this.getType().getId()=="VOR-SBB") {
          extraCapital = 2 * (startSpace.getPrice());   
         } else {
        extraCapital = 5 * (startSpace.getPrice());
         }
        super.start(startSpace);
    }
    
    /**
     * @return the fullyCapitalised
     */
    public boolean isFullyCapitalized() {
        return fullyCapitalized.value();
    }

    /**
     * @param fullyCapitalised the fullyCapitalised to set
     */
    public void setFullyCapitalized(boolean fullyCapitalised) {
        this.fullyCapitalized.set(fullyCapitalised);
    }
    
    public void setFullFundingAvail() {
        this.fullCapitalAvailable.set(true);
        checkToFullyCapitalize();
    }
    
    protected boolean checkToFullyCapitalize() {
        if ((hasFloated() == true) && (isConnectedToDestinationHex()) || (fullCapitalAvailable.value() == true)) {
            fullyCapitalized.set(true);
            Currency.wire(getRoot().getBank(),extraCapital,this);  
            ReportBuffer.add(this, LocalText.getText("ReceivesFullWorkingCapital",
                    this.getLongName(),
                    Bank.format(this, extraCapital) ));
            return true;
        }
        return false;
    }
    
    public boolean isConnectedToDestinationHex() {
        NetworkGraph nwGraph = NetworkGraph.createMapGraph(getRoot());
        NetworkGraph companyGraph =
                NetworkGraph.createRouteGraph(nwGraph, this, true);
        SimpleGraph<NetworkVertex, NetworkEdge> graph =
                companyGraph.getGraph();
        Set<NetworkVertex> verticies = graph.vertexSet();
        for (NetworkVertex vertex : verticies) {
                    if ( vertex.getHex() == this.getDestinationHex()) {
                            return true;
                        }
                    }
        return false;
    }

}
