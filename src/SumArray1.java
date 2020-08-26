
import java.util.concurrent.RecursiveTask;

/**
* <h1>SumArray1</h1>
* This class used to calculate average pevailig wind for all air layers
* It has two methods compute and get sum 
* 
* <p>
* 
*
* @author  Cassandra Dacha
* @since   2020-08-25
*/

public class SumArray1 extends RecursiveTask<Vector>  {
	  int lo; // arguments
	  int hi;
	  Vector[][][] vectorArray;
	  static final int SEQUENTIAL_CUTOFF=200;
          CloudData data;

	 
	  SumArray1(Vector[][][] a,  CloudData d, int l, int h) { 
	    lo=l; hi=h; vectorArray=a; data=d;
	  }

   /**
   * This method is inheritted from RecursiveTask class
   * It is used to break down large piece of data into small chunks  
   * @return Vector return a vector that contain mean x and y values
   */
	  protected Vector compute()
	  {// return answer - instead of run
		  if((hi-lo) < SEQUENTIAL_CUTOFF) 
		  {
		      return getSum();
		  }
		  else {
			  SumArray1 left = new SumArray1(vectorArray,data,lo,(hi+lo)/2);
			  SumArray1 right= new SumArray1(vectorArray,data,(hi+lo)/2,hi);
			  
			  left.fork();
			  Vector rightAns = right.compute();
			  Vector leftAns  = left.join();
			  float xc = (leftAns.x+rightAns.x)/2;
			  float yc = (leftAns.y+rightAns.y)/2;
			  return (new Vector (xc,yc));     
		  }
	 }
         
   /**
   * This method is used to calculate the average prevailing wind for all layers  
   * This is the sequential part of the program
   * @return Vector return a vector that contain mean x and y values
   */
	public Vector getSum ()
        {
		float xSum= (float) 0.0;
		float ySum= (float) 0.0;
		int count=0;
		for( int i=0; i<data.dimt; i++)
		{
			for(int x = 0; x < data.dimx; x++)
                        {
				for(int y=lo; y<hi; y++)
				{
					xSum += data.advection[i][x][y].x ;
					ySum += data.advection[i][x][y].y ;
					count++;
				}
			}
		}
		
         //int i=lo; i<hi; i++
		
		return (new Vector ((xSum/count),(ySum/count)));
	}
}


