import java.util.*;


public class LatticeSearch extends Search {

	private WordLattice lattice; //Lattice we're searching
	private LM model;
	
	//Accessors which return values
	public WordLattice getLattice() {
		return lattice;
	}
	public LM getModel() {
		return model;
	}
	
	//Constructor
	public LatticeSearch(WordLattice latt, LM bg) {
		lattice = latt;
		model = bg;
		
	}
}
	
