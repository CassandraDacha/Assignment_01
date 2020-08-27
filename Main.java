import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class TerrainArray extends  RecursiveTask<ArrayList<String>> {
    static final int SEQUENTIAL_THRESHOLD = 100;

    public int rowlo;
    public int rowhi;
    public int colhi;
    public int collo;
    //public int
    public float[][] basinarr;

    TerrainArray(float[][] a, int rowlo, int rowhi,int collo,int colhi) {
         this.rowlo = rowlo;
        this.rowhi=rowhi;
        this.collo = collo;
        this.colhi = colhi;
        basinarr = a;
    }

    protected ArrayList<String> compute() {
        if((colhi - collo <= SEQUENTIAL_THRESHOLD) && (rowhi - rowlo <= SEQUENTIAL_THRESHOLD)){
            int ans = 0;
            ArrayList<String> list_of_indexes = new ArrayList<>();
            for(int i=rowlo; i < rowhi; ++i){
                for(int j=collo; j < colhi; ++j){
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
            //int mid = lo + (hi - lo) / 2;
            int rowmid = (rowlo+rowhi)/2;
            int colmid = (collo+colhi)/2;
            TerrainArray Topleft = new TerrainArray(basinarr,rowlo,rowmid,collo,colmid);

            TerrainArray Topright = new TerrainArray(basinarr,rowlo,rowmid,colmid,colhi);
            TerrainArray Bottomleft = new TerrainArray(basinarr,rowmid,rowlo,collo,colmid);

            TerrainArray Bottomright = new TerrainArray(basinarr,rowmid,rowhi,colmid,colhi);
            Topleft.fork();
            Topright.fork();
            Bottomleft.fork();

            ArrayList <String> rightAns = Topright.compute();
            ArrayList <String> leftAns = Topleft.join();


            Topleft.fork();
            Topright.fork();
            Bottomleft.fork();

            ArrayList <String> BottomrightAns = Bottomright.compute();
            BottomrightAns.addAll(Bottomleft.join());
            BottomrightAns.addAll(Topleft.join());
            BottomrightAns.addAll(Topright.join());

            //leftAns.addAll(rightAns);
            return BottomrightAns;
        }
    }
}
