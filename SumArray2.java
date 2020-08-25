import java.lang.Object;
import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

/**
* <h1>SumArray2</h1>
* This class used to calculate an integer identification code 
* and assign each point with its identification code
* 
* <p>
* 
*
* @author  Cassandra Dacha
* @since   2020-08-25
*/

public class SumArray2 extends RecursiveTask<int[][][]>  {
	  int lo; // arguments
	  int hi;
	  
          float[][][] liftArray;
	  static final int SEQUENTIAL_CUTOFF=200;
          CloudData data ;


	  float liftSum= (float) 0.0;

	  SumArray2(float[][][] a,CloudData d, int l, int h) 
	  { 
	    lo=l; hi=h; liftArray=a; data=d;
	  }


/**
* This method is inheritted from RecursiveTask class
* It is used to break down large piece of data into small chunks  
* 
*/
	  protected int[][][] compute()
	  {
		// return answer - instead of run
		  if((hi-lo) < SEQUENTIAL_CUTOFF) 
		  {
		      return getSum();
		  }

		  else
		  {
			  SumArray2 left = new SumArray2 (liftArray,data,lo,(hi+lo)/2);
			  SumArray2 right= new SumArray2 (liftArray,data,(hi+lo)/2,hi);

			  left.fork();
			  int[][][] rightAns = right.compute();
			  int[][][] leftAns  = left.join();
			  return leftAns;

			
		  }
	 }



/**
* This method is used to assign an integer identification to each point in a grid
* and array that contain a grid points and their integer codes  
* This is the senquential part of the program
* @return int[][][] This returns an array.
*/
	public int[][][] getSum ()
        {
		for( int i=0; i<data.dimt; i++)
		{
			for(int x = 0; x < data.dimx; x++)
                        {
				for(int y=lo; y<hi; y++)
				{
					int code;
					float lift= (float) data.convection[i][x][y];
                                        if (Math.abs(lift) > meanWind(i,x,y))
                                        {
                                               
						code=0;
						
					}

					else if (meanWind(i,x,y) > 0.2 && meanWind(i,x,y) >= Math.abs(lift) )
                                        {
                                                code=1;
                                             
                                        }
					
					else { code=2; }
					data.classification[i][x][y]=code;

				}
			}
		}

		
		return data.classification;

	}


/**
* This method is used to calculate an aveage wind of all nearest point 
* around a given and then return a magnitude of the average value.  
* @param t time axis value
* @param i x axis value
* @param j y axis value
* @return float magnitude of vector.
*/
     public float meanWind (int t,int i,int j)
     {
        float av1= (float) 0.0;
        float av2= (float) 0.0;
	float sum1= (float) 0.0;
        float sum2= (float) 0.0;

	if (i !=0 && j != 0)
	{
	     if ((i<(data.dimx-1))&&(j<(data.dimy-1)))
	     {
	     sum1 = data.advection[t][i-1][j-1].x+data.advection[t][i-1][j].x+data.advection[t][i-1][j+1].x + data.advection[t][i][j-1].x +data.advection[t][i][j].x
		    + data.advection[t][i][j+1].x +data.advection[t][i+1][j-1].x +data.advection[t][i+1][j].x + data.advection[t][i+1][j+1].x ;
	     av1 = sum1/9;

             sum2 = data.advection[t][i-1][j-1].y+data.advection[t][i-1][j].y+data.advection[t][i-1][j+1].y + data.advection[t][i][j-1].y +data.advection[t][i][j].y
	           + data.advection[t][i][j+1].y +data.advection[t][i+1][j-1].y +data.advection[t][i+1][j].y + data.advection[t][i+1][j+1].y ;
	     av2 = sum2/9;
	     }
	     
	     else if((i==(data.dimx-1))&&(j<(data.dimy-1)))
	     {
	       sum1 = data.advection[t][i-1][j-1].x+data.advection[t][i-1][j].x+data.advection[t][i-1][j+1].x + data.advection[t][i][j-1].x +data.advection[t][i][j].x
                    + data.advection[t][i][j+1].x;
             av1 = sum1/6;

             sum2 = data.advection[t][i-1][j-1].y+data.advection[t][i-1][j].y+data.advection[t][i-1][j+1].y + data.advection[t][i][j-1].y +data.advection[t][i][j].y
                   + data.advection[t][i][j+1].y;
             av2 = sum2/6;

	      
	     }
	     
	     else if ((i<(data.dimx-1))&&(j==(data.dimy-1)))
             {
             sum1 = data.advection[t][i-1][j-1].x+data.advection[t][i-1][j].x + data.advection[t][i][j-1].x +data.advection[t][i][j].x
                    +data.advection[t][i+1][j-1].x +data.advection[t][i+1][j].x;
             av1 = sum1/6;

             sum2 = data.advection[t][i-1][j-1].y+data.advection[t][i-1][j].y + data.advection[t][i][j-1].y +data.advection[t][i][j].y
                    +data.advection[t][i+1][j-1].y +data.advection[t][i+1][j].y ;
             av2 = sum2/6;
             }
             
             else 
             {
                  
             
             sum1 = data.advection[t][i-1][j-1].x+data.advection[t][i-1][j].x + data.advection[t][i][j-1].x +data.advection[t][i][j].x;
             av1 = sum1/4;

             sum2 = data.advection[t][i-1][j-1].y+data.advection[t][i-1][j].y + data.advection[t][i][j-1].y +data.advection[t][i][j].y;
             av2 = sum2/4;
             

             }


       }


        else if (i == 0 && j != 0)
        {
        	
	    if(j < (data.dimy-1))
	    {
	     
             sum1 = data.advection[t][i][j-1].x +data.advection[t][i][j].x+data.advection[t][i][j+1].x +data.advection[t][i+1][j-1].x 
                   +data.advection[t][i+1][j].x + data.advection[t][i+1][j+1].x ;
             av1 = sum1/6;

             sum2 = data.advection[t][i][j-1].y +data.advection[t][i][j].y+data.advection[t][i][j+1].y +data.advection[t][i+1][j-1].y 
                   +data.advection[t][i+1][j].y + data.advection[t][i+1][j+1].y ;
             av2 = sum2/6;
           }
           
           else
           {
	     sum1 = data.advection[t][i][j-1].x +data.advection[t][i][j].x +data.advection[t][i+1][j-1].x 
                   +data.advection[t][i+1][j].x;
             av1 = sum1/4;

             sum2 = data.advection[t][i][j-1].y +data.advection[t][i][j].y+data.advection[t][i+1][j-1].y 
                   +data.advection[t][i+1][j].y ;
             av2 = sum2/4;
           }

       }

        else if (i !=0 && j == 0)
        {
	     if (i < (data.dimx-1))
	     {
             sum1 = data.advection[t][i-1][j].x+data.advection[t][i-1][j+1].x +data.advection[t][i][j].x
                    + data.advection[t][i][j+1].x +data.advection[t][i+1][j].x + data.advection[t][i+1][j+1].x ;
             av1 = sum1/6;

             sum2 = data.advection[t][i-1][j].y+data.advection[t][i-1][j+1].y+data.advection[t][i][j].y
                   + data.advection[t][i][j+1].y+data.advection[t][i+1][j].y + data.advection[t][i+1][j+1].y ;
             av2 = sum2/6;
             }
             
             else
             {
	     sum1 = data.advection[t][i-1][j].x+data.advection[t][i-1][j+1].x +data.advection[t][i][j].x
                    + data.advection[t][i][j+1].x ;
             av1 = sum1/4;

             sum2 = data.advection[t][i-1][j].y+data.advection[t][i-1][j+1].y+data.advection[t][i][j].y
                   + data.advection[t][i][j+1].y ;
             av2 = sum2/4;

             }

       }

        else if (i == 0 && j == 0)
        {
             sum1 = data.advection[t][i][j].x+data.advection[t][i][j+1].x +data.advection[t][i+1][j].x + data.advection[t][i+1][j+1].x ;
             av1 = sum1/4;

             sum2 = data.advection[t][i][j].y+data.advection[t][i][j+1].y+data.advection[t][i+1][j].y + data.advection[t][i+1][j+1].y ;
             av2 = sum2/4;

       }

       else {}
        Vector v = new Vector (av1, av2);
	return v.distance();
     }

   


}



