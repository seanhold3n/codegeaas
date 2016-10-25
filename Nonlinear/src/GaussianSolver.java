/**
  Solves a system of linear equations using Gaussian Elimination
  @author Julian Pryde and Sean Holden
    Originally written in Lua by Julian Pryde, ported to java by Sean
    Holden for easier debugging.
 **/

public class GaussianSolver {

	//function: gaussian()
    //runs the guassian elimination. Calls all functions needed to eliminate
    //the gaussian
	public static double[] gaussian(double[][] eq_matrix){

		upperTriangle(eq_matrix);  //call upper_triangular

		double[] solutions = backSub(eq_matrix);  //call backwards_sub

		return solutions;
	}
	
	/** Converts the matricies to the "upper-triangle" form for Gaussian elimination
	 * @param a NxN+1 matrix
	 */
	public static void upperTriangle(double[][] eq_matrix){

		int i, j, k, N;
		double multiplier;
		
		//System.out.println("I'm not crazy");
		N = eq_matrix.length;
		
//		for (col = 1; col < N; col++) { // Loop through all columns 
//			for (row = 0; row < col; row++) {  // Loop through all rows to be modified for that col
//				// Switch current row with row with largest element in the current column
//				// The reference row is the same number as the column, so the last 
//				// argument is the column number
////				switchRows(eq_matrix, col, row + 1, col, N);
//				
////				System.out.println("Post-flip:");
////				for (double[] rowArr : eq_matrix){
////					System.out.println(Arrays.toString(rowArr));
////				}
//
//				// Find proper multiplier to make the current element zero
//				// To find multiplier: current element divided by the corresponding 
//				// pivot on the diagonal
//				multiplier = eq_matrix[col][row] / eq_matrix[row][row];
//
//				// Correct zeros, wrong non-zeros
//				for (goose = 0; goose < N+1; goose++) { // Change each element in the selected row using multiplier
//					eq_matrix[col][goose] -= multiplier * eq_matrix[row][row];
//				}
////				for (goose = row+1; goose < N; goose++) { // Change each element in the selected row using multiplier
////					eq_matrix[row][goose] = eq_matrix[row][goose] - multiplier * eq_matrix[row][goose];
////				}
//			}
//		}
		
		for (j = 0; j < N; j++) { // Loop through all columns 
			for (i = j+1; i < N; i++) {  // Loop through all rows to be modified for that col
				// Switch current row with row with largest element in the current column
			// The reference row is the same number as the column, so the last 
				// argument is the column number
//				switchRows(eq_matrix, col, row + 1, col, N);
				
//				System.out.println("Post-flip:");
//				for (double[] rowArr : eq_matrix){
//					System.out.println(Arrays.toString(rowArr));
//				}

				// Find proper multiplier to make the current element zero
				// To find multiplier: current element divided by the corresponding 
				// pivot on the diagonal
				multiplier = eq_matrix[i][j] / eq_matrix[j][j];

				// Correct zeros, wrong non-zeros
				for (k = j+1; k < N+1; k++) { // Change each element in the selected row using multiplier
					eq_matrix[i][k] -= multiplier * eq_matrix[j][k];
				}
				
				//Set eq_matrix[i][j] to zero
				eq_matrix[i][j] = 0;
//				for (goose = row+1; goose < N; goose++) { // Change each element in the selected row using multiplier
//					eq_matrix[row][goose] = eq_matrix[row][goose] - multiplier * eq_matrix[row][goose];
//				}
			}
		}
	}
	
	/**
	 * @param a NxN+1 upper-triangle matrix
	 * @return
	 */
	public static double[] backSub(double[][] a){
		final int N_ROWS = a.length;
		final int N_COLS = a[0].length;
		
		if(N_COLS - N_ROWS != 1){
			throw new IllegalArgumentException("Matrix is not properly sized.");
		}
		
		// Create solutions array
		double[] x = new double[N_ROWS];
		
		double xi, aij, xj;
		
//		x[]
		// Get the bottom value
		//x[N_ROWS-1] = a[N_ROWS-1][N_COLS-1] / a[N_ROWS-1][N_COLS-2];
		//System.out.println("Bottom value: " + x[N_ROWS-1]);
		
		// Iterate over equations
		for (int i = N_ROWS-1; i >= 0; i--) {
			x[i] = a[i][N_COLS-1];

//			System.out.println(x[i]);
			// Iterate backwards over each element on the corresponding row of the upper-triangle matrix
			for (int j = N_COLS-2; j > i; j--) {
//				System.out.printf("%.2f ", a[i][j]);
				xi = x[i];
				aij = a[i][j];
				xj = x[j];
				x[i] -= a[i][j]*x[j];
			}
//			System.out.println();
//			System.out.printf("Doing %.2f / %.2f%n", x[i], a[i][i]);
			x[i] /= a[i][i];
		}
		
//		System.out.println(Arrays.toString(x));
		
		return x;
	}
	
	/*
	 * // function: switch_rows()
    // Takes an NxN+1 array of numbers, a base row number, and a column 
    // number to focus on. The function finds the row with the largest number 
    // for the given column and switches that row with the base row. The function
    // only looks through rows at or below the starting row to ignore rows that have
    // already been filled with zeros.

	// parameter: eq_matrix NxN+1 array 
	// parameter: column column number to focus on
	// parameter: starting_row row to start searching at
	// parameter: ref_row row to switch
	// parameter: N number of rows in eq_matrix
	 */
	// TODO untested
	public static void switchRows(double[][] eq_matrix, int focus_column, int starting_row, int ref_row, int N){
		int elephant;  // loop through rows for a given column
		int skunk;  // iterate through all columns and switch elements from two rows
		double largest = Double.NEGATIVE_INFINITY; // largest number in column, starts at minimum double value
		int largest_index = 0;  // index of row with largest element for given column
		double temporary;  // temporary storage of matrix element while rows are being switched

		// Find largest in column for all rows not already in upper-triangular form.
		for (elephant = starting_row; elephant < N; elephant++) {
			if (eq_matrix[focus_column][elephant] > largest) {
				largest = eq_matrix[focus_column][elephant];
				largest_index = elephant;
			}
		}

		// Only switch rows if the found largest number is larger than the one already
		// being looked at.
		// Starting_row - 1 because the starting row is one row below the row currently
		// being looked at for zeroation
		if (largest > eq_matrix[focus_column][ref_row]){
			// Switch rows
			for (skunk = 0; skunk < N+1; skunk++){  // loop through all columns. N+1 to accommodate the solution column
				// copy element from reference row to temp
				temporary = eq_matrix[ref_row][skunk];

				// copy element from largest row to reference row
				eq_matrix[ref_row][skunk] = eq_matrix[largest_index][skunk];

				// copy element from temporary to largest index row
				eq_matrix[largest_index][skunk] = temporary;
			}
		}
	}
}
