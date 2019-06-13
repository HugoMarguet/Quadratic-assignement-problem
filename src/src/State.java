import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public class State {

    private Map<Integer, Integer> locationWithFaciilities;
    private List<Pair<Integer, Integer>> listTabou;

    public State(Map<Integer, Integer> locationWithFaciilities, List<Pair<Integer, Integer>> listTabou) {
        this.locationWithFaciilities = locationWithFaciilities;
        this.listTabou = listTabou;
    }

    public Map<Integer, Integer> getLocationWithFaciilities() {
        return locationWithFaciilities;
    }

    public List<Pair<Integer, Integer>> getListTabou() {
        return listTabou;
    }

    public int hashCode() {
        return locationWithFaciilities.hashCode() * 17
                + listTabou.hashCode() * 31;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof State))
            return false;

        final State state = (State) obj;
        final Map<Integer, Integer> locationWithFaciilities = state.getLocationWithFaciilities();
        final List<Pair<Integer, Integer>> listTabou = state.getListTabou();

        for (int i = 0; i < this.locationWithFaciilities.size(); i++)
            if(!this.locationWithFaciilities.get(i).equals(locationWithFaciilities.get(i)))
                return false;
        for (int i = 0; i < this.listTabou.size(); i++)
            if (!this.listTabou.get(i).equals(listTabou.get(i)))
                return false;
        return true;
    }
}
