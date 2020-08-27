
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;


/**
* <h1>Main</h1>
* This is the main class of the application.
* It use ForkJoinPool framework to create a pool that excute tasks from RecursiveTasks classes
* <p>
* 
*
* @author  Cassandra Dacha
* @since   2020-08-25
*/

public class Main
{
    static long startTime = 0;

    /**
     *This method initiate a time for an experiment
     */
    private static void tick(){
        startTime = System.currentTimeMillis();
    }

    /**
     *This method return an execution time
     */
    private static float tock(){
        return (System.currentTimeMillis() - startTime) / 1000.0f;
    }
    static final ForkJoinPool fjPool = new ForkJoinPool();

/**
   * This method is reads data from the input files and converts it into a 2D array.
   */
    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(new BufferedReader(new FileReader("Data/small_in.txt")));
        //int rows = 4;
       // int columns = 4;

        while(sc.hasNextDouble()) {
            int columns = sc.nextInt();
            int rows =sc.nextInt();
            double [][] myArray = new double[rows][columns];
            for (int i=0; i<rows; i++) {
                //String[] line = sc.nextLine().trim().split(" ");
                for (int j=0; j<columns; j++) {
                    myArray[i][j] = sc.nextDouble();
                }
            }
            //System.out.println(Arrays.deepToString(myArray));
           // System.out.println(myArray.length);
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
            fr.close();
            File fileTime = new File("Data/time_text.txt");
            FileWriter x = new FileWriter(file, true);
                x.write("Run took "+ time +" seconds");

            x.close();
            System.out.println(f);


        }

    }
/* This method is used to invoke TerrainArray class.
* 
* @return int[][] return an array of indexes in the grid
*/
    static  ArrayList<String>  basinTerrain(double[][] array)
    {
        return ForkJoinPool.commonPool().invoke(new TerrainArray(array,0,array.length));

    }


}
