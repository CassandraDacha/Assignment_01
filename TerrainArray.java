import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
class TerrainArray extends RecursiveTask<Integer> {
    static int SEQUENTIAL_THRESHOLD = 1000;
    
    int lo;
    int hi;
    int[] arr;
    TerrainArray(int[] a, int l, int h) { lo=l; hi=h; arr=a; }
    
    public ArrayList<String> compute() {
    if(hi - lo <= SEQUENTIAL_THRESHOLD) {
        int ans = 0;
        int[][] indexes = new int[1000][1000];
        ArrayList<String> list_of_indexes = new ArrayList<>();
        for(int i=lo; i < hi; ++i)
            for(int j=lo; j < hi; ++j)
                if ( i! = 0|| j! = 0 || j! = numrows||i!= numrows)
                    if (arr[i][j] < arr[i-1][j-1]&&arr[i-1][j]&&arr[i-1][j+1]&&arr[i][j-1]&&arr[i][j+1]&&arr[i+1][j-1]&&arr[i+1][j]&&arr[i+1][j+1])
                    {
                       String indexes = i+","+j;
                       list.add(indexes);
                 
                       
                    }
                   return list_of_indexes;
        } else {
            TerrainArray left = new TerrainArray(arr,lo,(hi+lo)/2);
            TerrainArray right = new TerrainArray(arr,(hi+lo)/2,hi);
            left.fork();
            ArrayList<String> rightAns = right.compute();
            ArrayList<String> leftAns = left.join();
	    rightAns.addAll(leftAns); 
            return rightAns;
        }
     }
 }
class Main {
    static int TerrainArray(int[] array)
{
        return ForkJoinPool.commonPool().invoke(new TerrainArray(array,0,array.length));
        
}
    }
