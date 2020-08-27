import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


/**
* <h1>TerrainArray</h1>
* This class used to compute the number of basins in a grid as well as their adjacent locations 
* 
* <p>
* 
*
* @author  Cassandra Dacha
* @since   2020-08-25
*/

public class TerrainArray extends  RecursiveTask<ArrayList<String>> {
    static int SEQUENTIAL_THRESHOLD = 1000;

    public int lo;
    public int hi;
    public double[][] basinarr;

    TerrainArray(double[][] a, int l, int h) {
         lo=l; hi=h;
        basinarr = a;
    }

 /**
   * This method is inheritted from RecursiveTask class
   * It is used to break down large piece of data into small chunks  
   * @return a list containing the indexes of the basins in the given grid
   */
 protected ArrayList<String> compute() {
     if(hi - lo <= SEQUENTIAL_THRESHOLD) {
         int ans = 0;
         ArrayList<String> list_of_indexes = new ArrayList<>();
         for(int i=lo; i < hi; ++i){
             for(int j=lo; j < hi; ++j){
                 if (i != 0 &&j != 0 && j != basinarr.length-1 &&i != basinarr.length-1){
                     if ((basinarr[i][j] +0.01)<= basinarr[i - 1][j - 1] && (basinarr[i][j] +0.01) <= basinarr[i - 1][j ] && (basinarr[i][j] +0.01) <= basinarr[i-1][j + 1] && (basinarr[i][j] +0.01) <= basinarr[i][j - 1] && (basinarr[i][j] +0.01) <= basinarr[i][j+1] && (basinarr[i][j] +0.01) <= basinarr[i + 1][j - 1] && (basinarr[i][j] +0.01) <= basinarr[i+1][j] && (basinarr[i][j] +0.01) <= basinarr[i + 1][j + 1]) {
                         String indexes = i + " " + j;
                         list_of_indexes.add(indexes);
                     }
                 }
             }
         }
         return list_of_indexes;
     }
     else {
         int mid = lo + (hi - lo) / 2;
         TerrainArray left = new TerrainArray(basinarr,lo,mid);

         TerrainArray right = new TerrainArray(basinarr,mid,hi);
         left.fork();

         ArrayList <String> rightAns = right.compute();
         ArrayList <String> leftAns = left.join();


         leftAns.addAll(rightAns);
         return leftAns;
     }
 }
}
