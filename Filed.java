<<<<<<< HEAD:src/Main.java
import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

class TerrainArray extends  RecursiveTask<ArrayList<String>> {
    static final int SEQUENTIAL_THRESHOLD = 2000;

    public int rowlo;
    public int rowhi;
    public int colhi;
    public int collo;
    //public int
    public float[][] basinarr;
=======
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class Filed
{
    static long startTime = 0;
>>>>>>> 8d1f4e600eeeedc338a3c8feaf5cdec74083be0e:Filed.java

    TerrainArray(float[][] a, int rowlo, int rowhi,int collo,int colhi) {
        this.rowlo = rowlo;
        this.rowhi=rowhi;
        this.collo = collo;
        this.colhi = colhi;
        basinarr = a;
    }

<<<<<<< HEAD:src/Main.java
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
            TerrainArray Bottomleft = new TerrainArray(basinarr,rowmid,rowhi,collo,colmid);

            TerrainArray Bottomright = new TerrainArray(basinarr,rowmid,rowhi,colmid,colhi);
            Topleft.fork();
            Topright.fork();
            Bottomleft.fork();

            ArrayList <String> rightAns = Topright.compute();
            ArrayList <String> leftAns = Topleft.join();
=======
    /**
     *This method return an execution time
     */
    private static float tock(){
        return (System.currentTimeMillis() - startTime) / 1000.0f;
    }
    static final ForkJoinPool fjPool = new ForkJoinPool();
    public static void main(String[] args) throws Exception{

        Scanner sc = new Scanner(new BufferedReader(new FileReader("Data/large_in.txt")));
        //int rows = 4;
       // int columns = 4;

        while(sc.hasNextDouble()) {
            int columns = sc.nextInt();
            int rows =sc.nextInt();
            float [][] myArray = new float[rows][columns];
            for (int i=0; i<rows; i++) {
                //String[] line = sc.nextLine().trim().split(" ");
                for (int j=0; j<columns; j++) {
                    myArray[i][j] = sc.nextFloat();
                }
            }
            tick();
            ArrayList<String> f = basinTerrain(myArray);
            System.out.println("Basins No. :"+f.size());
            float time = tock();
            System.out.println("Run took "+ time +" seconds");

            File file = new File("Data/large_text.txt");
            FileWriter fr = new FileWriter(file, true);
            for(String line: f){
                String l = line+"\n";
                fr.write(l);
            }
            fr.write("Run took "+ time +" seconds");
            fr.close();
            System.out.println(f);
>>>>>>> 8d1f4e600eeeedc338a3c8feaf5cdec74083be0e:Filed.java


            Topleft.fork();
            Topright.fork();
            Bottomleft.fork();

<<<<<<< HEAD:src/Main.java
            ArrayList <String> BottomrightAns = Bottomright.compute();
            BottomrightAns.addAll(Bottomleft.join());
            BottomrightAns.addAll(Topleft.join());
            BottomrightAns.addAll(Topright.join());
=======
    }
    static  ArrayList<String>  basinTerrain(float[][] array)
    {
        return ForkJoinPool.commonPool().invoke(new TerrainArray(array,0,array.length,0,array[0].length));
>>>>>>> 8d1f4e600eeeedc338a3c8feaf5cdec74083be0e:Filed.java

            //leftAns.addAll(rightAns);
            return BottomrightAns;
        }
    }
<<<<<<< HEAD:src/Main.java
}
=======


}
>>>>>>> 8d1f4e600eeeedc338a3c8feaf5cdec74083be0e:Filed.java
