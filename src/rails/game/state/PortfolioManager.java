package rails.game.state;

/**
 * PortfolioManager stores links to all existing portfolios
 * @author freystef
 */

public final class PortfolioManager extends AbstractItem {

    private final HashMultimapState<Owner, PortfolioMap<?>> portfolios = HashMultimapState.create();
    
    private PortfolioManager() {};
    
    static PortfolioManager create() {
        return new PortfolioManager();
    }
    
    boolean addPortfolio(PortfolioMap<?> p){
        return portfolios.put(p.getOwner(), p);
    }
    
    boolean removePortfolio(PortfolioMap<?> p){
        return portfolios.remove(p.getOwner(), p);
    }
    
}