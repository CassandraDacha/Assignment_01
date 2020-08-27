
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

public class Filed
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
    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(new BufferedReader(new FileReader("Data/med_in.txt")));
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
            //System.out.println(Arrays.deepToString(myArray));
           // System.out.println(myArray.length);
            tick();
            ArrayList<String> f = basinTerrain(myArray);
            System.out.println("Basins No. :"+f.size());
            float time = tock();
          /* System.out.println("Run took "+ time +" seconds");
            System.out.println("Data at [589,262] is "+ myArray[589][262]);
            System.out.println("Data at [649,507] is "+ myArray[649][507]);
            System.out.println("Data at [752,308] is "+ myArray[752][308]);
            System.out.println("Data at [859,467] is "+ myArray[859][467]);
            System.out.println("Data at [1014,123] is "+ myArray[1014][123]);
            System.out.println("Data at [1022,124] is "+ myArray[1022][124]);*/
            System.out.println("Data at [177,273] is "+ myArray[177][273]);
            System.out.println("Data at [195,343] is "+ myArray[195][343]);
            System.out.println("Data at [298,342] is "+ myArray[298][342]);


            File file = new File("Data/large_text.txt");
            FileWriter fr = new FileWriter(file, true);
            for(String line: f){
                String l = line+"\n";
                fr.write(l);
            }
            fr.write("Run took "+ time +" seconds");
            fr.close();
            System.out.println(f);


        }

    }
    static  ArrayList<String>  basinTerrain(float[][] array)
    {
        return ForkJoinPool.commonPool().invoke(new TerrainArray(array,0,array.length,0,array[0].length));

    }


}