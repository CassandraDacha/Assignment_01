import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class TerrainArray extends  RecursiveTask<ArrayList<String>> {
    static int SEQUENTIAL_THRESHOLD = 1000;

    public int lo;
    public int hi;
    public double[][] basinarr;

    TerrainArray(double[][] a, int l, int h) {
         lo=l; hi=h;
        basinarr = a;
    }

    public ArrayList<String> compute() {
        if(hi - lo <= SEQUENTIAL_THRESHOLD) {
            int ans = 0;

            ArrayList<String> list_of_indexes = new ArrayList<>();
            for(int i=lo; i < hi; ++i){
                for(int j=lo; j < hi; ++j){
                    if (i != 0 &&j != 0 && j != basinarr.length-1 &&i != basinarr.length-1){
                    if (basinarr[i][j] < basinarr[i - 1][j - 1] && basinarr[i][j] < basinarr[i - 1][j ] && basinarr[i][j] < basinarr[i-1][j + 1] && basinarr[i][j] < basinarr[i][j - 1] && basinarr[i][j] < basinarr[i][j+1] && basinarr[i][j] < basinarr[i + 1][j - 1] && basinarr[i][j] < basinarr[i+1][j] && basinarr[i][j] < basinarr[i + 1][j + 1]) {
                        String indexes = i + " " + j;
                        list_of_indexes.add(indexes);
                    }
                    }
                }
            }
            return list_of_indexes;
        }
        else {
            TerrainArray left = new TerrainArray(basinarr,lo,(hi+lo)/2);

            TerrainArray right = new TerrainArray(basinarr,(hi+lo)/2,hi);
            left.fork();

            ArrayList <String> rightAns = right.compute();
            ArrayList <String> leftAns = left.join();


            rightAns.addAll(leftAns);
            return rightAns;
        }
    }
}
