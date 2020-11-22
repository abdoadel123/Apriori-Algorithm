/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problem.pkg1;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

/**
 *
 * @author khalifa
 */
public class Problem1 {

    public static ArrayList<Integer> items = new ArrayList<>();
    public static ArrayList<String> transactions = new ArrayList<>();
    public static Set<Integer> itemsSet = new HashSet<Integer>();
    public static ArrayList<Integer> itemsSetList = new ArrayList<Integer>();
    public static HashMap<String, Integer> itemsTable = new HashMap<>();
    public static Scanner input = new Scanner(System.in);

    public static void readFile(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            transactions.add(st);
            String[] line = st.split(" ");
            for (String item : line) {
                items.add(Integer.parseInt(item));
            }
        }
        itemsSet.addAll(items);
        itemsSetList.addAll(itemsSet);
        itemsSet = new HashSet<>();
//        System.out.println("\nTransactions:\n");
//        transactions.forEach(item -> {
//            System.out.println(item);
//        });
    }

    public static void initialTable(int minSub) {
        itemsSetList.forEach((Integer item) -> {
            int freq = Collections.frequency(items, item);
            if (minSub <= freq) {
                itemsTable.put(item + " --> ", freq);
            }
        });
//        itemsTable.entrySet().forEach(entry -> {
//            System.out.println(entry.getKey() + " -- " + entry.getValue());
//        });
    }

    public static int countFreq(String itemsStr) {
        String[] itemes = itemsStr.split(",");
        int count = 0;
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < transactions.size(); i++) {
            temp.addAll(Arrays.asList(transactions.get(i).split(" ")));
            boolean flag = true;
            for (String iteme : itemes) {
                if (!temp.contains(iteme)) {
                    flag = false;
                }
            }
            if (flag) {
                count++;
            }
            temp = new ArrayList<>();
        }
        return count;
    }

    public static ArrayList<Integer> getSet(HashMap<String, Integer> tableCopy) {
        Set<Integer> tempSet = new HashSet<>();
        ArrayList<Integer> tempSetList = new ArrayList<>();
        tableCopy.entrySet().forEach(entry -> {
            String[] itemes = entry.getKey().split(",");
            for (String item : itemes) {
                tempSet.add(Integer.parseInt(item));
            }
        });
        tempSetList.addAll(tempSet);
        return itemsSetList;
    }

    public static void recursiveTable(int minSub, int r) {
        Integer data[] = new Integer[r];
        HashMap<String, Integer> tableCopy = new HashMap<>();
        tableCopy = itemsTable;
        itemsTable = new HashMap<>();
        if (r == 2) {
            combinationUtil(itemsSetList, data, 0, itemsSetList.size() - 1, 0, r, minSub);
        } else {
            ArrayList<Integer> tempSetList = new ArrayList<>();
            tempSetList = getSet(tableCopy);
            combinationUtil(tempSetList, data, 0, tempSetList.size() - 1, 0, r, minSub);
        }
        if (itemsTable.size() > 0) {
            System.out.println("=============================================");
            itemsTable.entrySet().forEach(entry -> {
                System.out.println(entry.getKey() + " -- " + entry.getValue());
            });
            r++;
            recursiveTable(minSub, r);
        } else {
            itemsTable = tableCopy;
        }
    }

    public static void combinationUtil(ArrayList<Integer> arr, Integer data[], int start, int end, int index, int r, int minSub) {
        if (index == r) {
            String line = "";
            for (int j = 0; j < r; j++) {
                line += data[j];
                if (j != r - 1) {
                    line += ",";
                }
            }
            int count = countFreq(line);
            if (count >= minSub) {
                itemsTable.put(line, count);
            }
            return;
        }
        for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
            data[index] = arr.get(i);
            combinationUtil(arr, data, i + 1, end, index + 1, r, minSub);
        }
    }

    public static void main(String[] args) throws IOException {

        System.out.print("Enter Minimum Support: ");
        int minSupp = input.nextInt();

        System.out.print("Enter Minimum Confidence: ");
        int miniConf = input.nextInt();

        readFile("RetailDataSet.txt");

        initialTable(minSupp);

        System.out.println("\nTable Generations:\n");
        recursiveTable(minSupp, 2);
//

        System.out.println("================================================");
        System.out.println(transactions.size());
        System.out.println(items.size());
        System.out.println(itemsSet.size());
        System.out.println(itemsTable.size());

    }

}
