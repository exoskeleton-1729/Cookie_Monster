/* Use this class to represent one of the orphans
 * that Cookie Monster has kidnapped and forced to scout
 * through the grid, ending at endingRow and endingCol.
 * Why do we only need their endingRow, endingCol and cookiesDiscovered? */

public class OrphanScout implements Comparable<OrphanScout> {

	final private int endingRow;
    final private int endingCol;
    final private int cookiesDiscovered;

    public OrphanScout(int r, int c, int t) {
        endingRow = r;
        endingCol = c;
        cookiesDiscovered = t;
    }
    
    public int getEndingRow() {
		return endingRow;
	}

	public int getEndingCol() {
		return endingCol;
	}

	public int getCookiesDiscovered() {
		return cookiesDiscovered;
	}

    /* Comparable */
    public int compareTo(OrphanScout other) {
        return this.cookiesDiscovered - other.cookiesDiscovered;
    }
}
