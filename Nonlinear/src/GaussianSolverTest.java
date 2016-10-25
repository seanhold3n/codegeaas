//package ;

import static org.junit.Assert.*;

import java.util.Arrays;

//import static edu.erau.holdens.ma348.gaussian.GaussianSolver.*;

import org.junit.Ignore;
import org.junit.Test;

public class GaussianSolverTest {
	
	/* Maximum difference between test double values */
	private static double DELTA = 1e-6;
	
	@Test
	public void testGaussElim(){
//		double[][] a = {{1,2,1},
//						{3,2,4},
//						{4,4,3}};
//		double[] b = {1,2,3}; //wrong
		
        GaussianSolver solver = new GaussianSolver();

		// Input matrix
		double[][] a = {{3,2,4,4},
						{-1,2,3,1},
						{1,1,3,2}
		};
		
		// Expected results
		double[] x = {2.0/3.0,1.0/3.0,1.0/3.0};
		
		assertArrayEquals(x, solver.gaussian(a), DELTA);
	}
	
	@Test
	public void testUpperTriangle(){

        GaussianSolver solver = new GaussianSolver();

		// Input matrix
		double[][] before = {{1,2,1,5},
				{3,2,4,17},
				{4,4,3,26}
		};
		
		// Input matrix
		double[][] actual = {{1,2,1,5},
					{3,2,4,17},
					{4,4,3,26}
		};
		solver.upperTriangle(actual);
		
		
		// Upper-triangle matrix
		double[][] expected = {{1,2,1,5},
							{0,-4,1,2},
							{0,0,-2,4}};
		
		// Print expected
		System.out.println("Expected:");
		for (double[] row : expected){
			System.out.println(Arrays.toString(row));
		}
		
		// Print actual
		System.out.println("Actual:");
		for (double[] row : actual){
			System.out.println(Arrays.toString(row));
		}
		
		// Compare
		for (int i = 0; i < expected.length; i++) {
			assertArrayEquals(expected[i], actual[i], DELTA);
		}
		
	}

	@Test
	public void testBackSub() {

        GaussianSolver solver = new GaussianSolver();

		// Upper-triangle matrix
		double[][] a = {{1,2,1,5},
						{0,-4,1,2},
						{0,0,-2,4}};
		
		// Solutions
		double[] x = {9,-1,-2};
		
		assertArrayEquals(x, solver.backSub(a), DELTA);
		
		// Do another, for funzies
		double[][] b = {{2,2,4,0},
						{0,-2,1,-3},
						{0,0,0.5,-0.5}};

		// Solutions
		double[] y = {1,1,-1};

		assertArrayEquals(y, solver.backSub(b), DELTA);
	}

}
