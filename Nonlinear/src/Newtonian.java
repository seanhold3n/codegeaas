import java.util.Arrays;

/**
 * Runs through iterations of the Newtonian method for nonlinear systems until
 * each of the three variables change by <0.0001 in an iteration
 * @author Julian Pryde
 *
 */


public class Newtonian {
	
	public static void main(String[] args){
		
		//Make initial guesses, x0
		double[] guesses = {1, 1, 1};
		
		//Initialize delta matrix
		double[] deltas = {1, 1, 1};
		
		//Declare jacobian matrix, rows inside columns
		double[][] jacobian_mat = new double[3][4];
		
		//Declare function matrix
		double[] f_mat = new double[3];
		
		//Loop counter
		int porcupine = 0;
		
		//Loop until delta-x < 0.0001
		while (Math.abs(deltas[0]) > 0.0001 || Math.abs(deltas[1]) > 0.0001 || Math.abs(deltas[2]) > 0.0001){
			
			porcupine += 1;
			
			//find f of the three x0 values
			f_mat = f(guesses);
			
			//TEST
			System.out.println("function 1: " + f_mat[0] + "\nfunction 2: " + f_mat[1] + "\nfunction 3: " + f_mat[2]);
		
			//find the jacobian with the x0 values plugged in
			jacobian_mat = jacobian(guesses);
			
			//TEST
			System.out.println("Jacobian: ");
			System.out.println(Arrays.toString(jacobian_mat[0]));
			System.out.println(Arrays.toString(jacobian_mat[1]));
			System.out.println(Arrays.toString(jacobian_mat[2]));
			
			//Concatenate an element of the f_mat on the end of each row to prepare for gaussian elim
			for (int badger = 0; badger < f_mat.length; badger++){
				jacobian_mat[badger][jacobian_mat[0].length - 1] = f_mat[badger];
			}
			
			//TEST
			System.out.println("Augmented Jacobian: ");
			System.out.println(Arrays.toString(jacobian_mat[0]));
			System.out.println(Arrays.toString(jacobian_mat[1]));
			System.out.println(Arrays.toString(jacobian_mat[2]));

			//call the gaussian elimination method to find the delta-x to x1
			deltas = GaussianSolver.gaussian(jacobian_mat);
			
			//add the delta-x matrix and the x0 matrix to find the x1 matrix
			guesses = mat_sum(guesses, deltas);
			
			System.out.println("delta x: " + deltas[0] + "\ndelta y: " + deltas[1] + "\ndelta z: " + deltas[2]);
			System.out.println("x = " + guesses[0] + "\ny = " + guesses[1] + "\nz = " + guesses[2] + "\nCounter: " + porcupine);
			System.out.println();

		}
		
		System.out.println("x = " + guesses[0] + "\ny = " + guesses[1] + "\nz = " + guesses[2]);
	}
	
	//Returns the sum of 2 1-dimensional matrices
	static double[] mat_sum(double[] mat_1, double[] mat_2){
		
		//sum matrix
		double[] sum = {0, 0, 0};
		
		//add 1st element
		sum[0] = mat_1[0] - mat_2[0];
		
		//add 2nd element
		sum[1] = mat_1[1] - mat_2[1];
		
		//add 3rd element
		sum[2] = mat_1[2] - mat_2[2];
		
		return sum;
	}

	
	//Returns the Jacobian of the given system in a matrix with the x, y and z
	//values plugged in
	static double[][] jacobian(double[] guesses){
		
		//create jacobian matrix
		double[][] jacobian_mat = new double[3][4];
		
		//0, 0 position = 2x^2
		jacobian_mat[0][0] = 2 * Math.pow(guesses[0], 2);
		
		//0, 1 position = -2
		jacobian_mat[0][1] = -2;
		
		//0, 2 position = 0
		jacobian_mat[0][2] = 0;
		
		//1, 0 position = 2x^2
		jacobian_mat[1][0] = 2 * Math.pow(guesses[0], 2);
		
		//1, 1 position = 0
		jacobian_mat[1][1] = 0;
		
		//1, 2 position = -10z
		jacobian_mat[1][2] = -10 * guesses[2];
		
		//2, 0 position = 0
		jacobian_mat[2][0] = 0;
		
		//2, 1 position = z^2
		jacobian_mat[2][1] = guesses[2];
		
		//2, 2 position = 2yz
		jacobian_mat[2][2] = 2 * guesses[1] * guesses[2];
		
		return jacobian_mat;
	}
	
	//Returns a matrix of the results of the three equations with the given x, y,
	//and z plugged in
	static double[] f(double[] guesses){
		
		//returned matrix
		double[] function_matrix = new double[3];
		
		//eq 1: x^3 - 2y - 2
		function_matrix[0] = Math.pow(guesses[0], 3) - 2 * guesses[1] - 2;
		
		//eq 2: x^3 - 5z^2 + 7
		function_matrix[1] = Math.pow(guesses[0], 3) - 5 * Math.pow(guesses[2], 2) + 7;
		
		//eq 3: yz^2 - 1
		function_matrix[2] = guesses[1] * Math.pow(guesses[2], 2) - 1;
		
		return function_matrix;
		
	}
}
