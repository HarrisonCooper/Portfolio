import java.util.ArrayList;


public class LatticeState extends SearchState {

	private WordH wordHypothesis;

	//constructor
	public LatticeState(WordH wordH, int i) {
		wordHypothesis = wordH;
		localCost = i;
	}

	//accessor
	public WordH getWordHypothesis(){
		return wordHypothesis;
	}

	//goalP
	public boolean goalP(Search searcher) {
		LatticeSearch lsearcher = (LatticeSearch) searcher;
		WordLattice wordLattice = lsearcher.getLattice();

		//Takes the end time of the lattice and compares it to the end time of the word. If they're equal, returns true.
		int endWord = wordLattice.getEndTime();
		int endNextWord = wordHypothesis.getEnd();
		if (endNextWord == endWord) {
			return true;
		}
		return false;
	}

	//getSuccessors
	public ArrayList<SearchState> getSuccessors(Search searcher) {
		//makes new array lists for the end times of each word hypothesis and adds them to another array list, succs, which
		//also has the costs associated with each word hypothesis,
		LatticeSearch lsearcher = (LatticeSearch) searcher;
		WordLattice lattice = lsearcher.getLattice();
		ArrayList<WordH> wordHs = lattice.wordsAtT(wordHypothesis.getEnd());
		ArrayList<SearchState> succs = new ArrayList<SearchState>();

		for (WordH l: wordHs){
			succs.add((SearchState)new LatticeState(l,l.getCost()));
		}
		return succs;
	}

	//sameState
	//compares to see whether the word hypothesis is equal to searcher.ß
	public boolean sameState(SearchState n2) {
		LatticeState ln2 = (LatticeState) n2;
		if (wordHypothesis==ln2.getWordHypothesis()) {
			return true;
		}
		return false;
	}

	// toString
	public String toString () {
		return ("Lattice State: "+ wordHypothesis);
	}
}
