import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// item class
class Item{
    public Integer value;
    public Integer weight;

    public Item() {
        this.value = 0;
        this.weight = 0;
    }

    public void setWeight(Integer weight){
        this.weight = weight;
    }
    public void setValue(Integer value){
        this.value = value;
    }
    public Integer getWeight(){
        return this.weight;
    }
    public Integer getValue(){
        return this.value;
    }

    @Override
    public String toString() {
        return "Item{" +
                "value=" + value +
                ", weight=" + weight +
                '}';
    }
}

public class Knapsack {

    public static void main(String[] args) throws FileNotFoundException {
        // Input for Filename
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter File Name: ");
        String Filename = scanner.nextLine();

        // Filename
        File myFile = new File(Filename);
        Scanner fileReader = new Scanner(myFile);
        ArrayList<ArrayList<Integer>> tempTable = new ArrayList<>();

        // Read the file
        while (fileReader.hasNextLine()) {
            ArrayList<Integer> data = new ArrayList<>();
            String line = fileReader.nextLine();
            String[] temp = line.split(" +");
            for (String s : temp) {
                data.add(Integer.parseInt(s));
            }
            tempTable.add(data);

        }

        // Set the weight and remove it from table
        int W = (int) tempTable.get(0).get(0);
        tempTable.remove(0);

        // Create list of item object
        ArrayList<Item> table = new ArrayList<>();

        // Add items to table
        for (ArrayList<Integer> arrayList : tempTable) {
            Item tempItem = new Item();
            tempItem.setValue((int) arrayList.get(1));
            tempItem.setWeight((int) arrayList.get(2));
            table.add(tempItem);
        }
        // Fill Memoization table with -1
        Integer[][] M = new Integer[(table.size()+1)][W+1];
        boolean [][] BookKeeping = new boolean[(table.size()+1)][W+1];
        for (int i = 0; i < table.size()+1; i++) {
            for (int j = 0; j <= W; j++) {
                M[i][j] = -1;
                BookKeeping[i][j] = false;

            }
        }
        // OPT
        OPT(M, BookKeeping, table, table.size()+1, W);


        // Memoization header
        System.out.println(table);
        System.out.println("Solving Knapsack  weight capacity " + W + " with " + table.size() + " items");
        System.out.println("-----------------------------------------------------------------------------------------");
        for (int k = 0; k < table.size() + 1; k++) {
            // Memoization row iteration
            System.out.println("Memoization Table, Row " + k + " completed ");
            System.out.println("                                           Weights -->");
            System.out.print("                            ");
            // Memoization col index
            for (int j = 0; j < W + 1; j++) {
                System.out.print(j + "       ");
            }
            System.out.println(" ");
            System.out.println("----------------------------------------------------------------------------------------------------------------");

            // Memoization data
            printTable(M,table,W,k);


        }

        // Return results
        System.out.println(" ");
        System.out.println("Knapsack  with weight capacity " + W + " has optimal value:  " + M[table.size()][W-1]);
        System.out.println("_____Knapsack  Contains_____");
        findOptimal(BookKeeping, table, W);
    }

    // This function runs the Optimization
    public static void OPT(Integer[][] M, boolean[][] BookKeeping, ArrayList<Item> table, int n, int W){
        for(int w = 0; w < W+1; w++){
            M[0][w] = 0;
        }
        for(int i = 1; i < n; i++){
            for(int w = 0; w < W+1; w++){
                if(table.get(i-1).getWeight() > w){
                    M[i][w] = M[i-1][w];
                }
                else{
                    M[i][w] = Integer.max(M[i-1][w], table.get(i-1).getValue() + M[i-1][w - table.get(i-1).getWeight()]);
                    int max = Integer.max(M[i-1][w], table.get(i-1).getValue() + M[i-1][w - table.get(i-1).getWeight()]);
                    if(max == table.get(i-1).getValue() + M[i-1][w - table.get(i-1).getWeight()]){
                        BookKeeping[i][w] = true;
                    }
                }
            }

        }
    }

    // This function prints the table
    public static void printTable(Integer[][] M, ArrayList<Item> table, int W, int k) {
        for (int i = 0; i < table.size() + 1; i++) {
            if(i == 0){
                System.out.print("                {}          ");
            }else{
                System.out.print("               {"+ i + "}          ");
            }
            if (i <= k) {
                for (int j = 0; j <= W; j++) {
                    String length = Integer.toString(M[i][j]);
                    if (length.length() == 1) {
                        System.out.print(M[i][j] + "       ");
                    } else {
                        System.out.print(M[i][j] + "      ");
                    }

                }
            }else{
                for (int j = 0; j <= W; j++) {
                    String length = Integer.toString(-1);
                    System.out.print(-1 + "      ");

                }
            }
            System.out.println("\n");
        }

    }

    // This takes the BookKeeping 2-d array and prints the resulting items
    public static void findOptimal(boolean[][] BookKeeping, ArrayList<Item> table, int W){
        ArrayList result =  new ArrayList<String>();
        int max = W;
        for(int i = table.size()-1; i >= 0; i--){
            if(BookKeeping[i][max]){
                int index = table.indexOf(table.get(i));
                max -= table.get(i).getWeight();
                String ResultString = ("Item " + (index + 1) + "(Value = " + table.get(i).getValue() + " Weight = " + table.get(i).getWeight() + ")");
                result.add(ResultString);
            }
        }
        //print
        for(int c = result.size(); c > 0; c--){
            System.out.println(result.get(c-1));
        }
    }
    }


