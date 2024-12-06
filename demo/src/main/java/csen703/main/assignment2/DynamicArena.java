package csen703.main.assignment2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


class Pair<P, B> {
    public P profit;
    public B banned;

    public Pair(P profit, B banned) {
        this.profit = profit;
        this.banned = banned;
    }
}
// i 1 2 3
//[A,B,C,D] length = 4
//[A,B,C] length = 4
@SuppressWarnings("unused")
public class DynamicArena {

    static class Data {
        int entered;
        Set<Integer> banned;
        int profit;

        Data(int entered, Set<Integer> banned, int profit) {
            this.entered = entered;
            this.banned = banned;
            this.profit = profit;
        }

        @Override
        public String toString() {
            return "Entered: " + entered + ", Banned: " + banned + ", Profit: " + profit + "\n";
        }
    }

    public static int olele(List<Data> dataList) {

        
        int n = dataList.size();
        int[] dp = new int[n];
        dp[0] = dataList.get(0).profit;
        
        for (int i = 1; i < n; i++) {
            Data current = dataList.get(i);
            int maxProfit = current.profit;
            
            for (int j = 0; j < i; j++) {
                Data prev = dataList.get(j);
                if (!prev.banned.contains(current.entered)) {
                    maxProfit = Math.max(maxProfit, dp[j] + current.profit);
                }
            }
            
            dp[i] = maxProfit;
        }
        
        return Arrays.stream(dp).max().getAsInt();
    }

    public static int alolo(int[] floors) {
        List<Data> dataList = new ArrayList<>();

        for (int i = 0; i < floors.length - 1; i++) {
            for (int j = i + 1; j < floors.length; j++) {
                Set<Integer> set = new HashSet<>();
                for (int k = i; k < j+2 && k < floors.length; k++) {
                    set.add(k);
                }
                Data newData = new Data(i,set, floors[j] - floors[i]);
                dataList.add(newData);
            }
        }

        System.out.println(dataList);

        return olele(dataList);
    }

    public static int yemken(int[] floors) {
        int[][] dp = new int[floors.length][floors.length];
        HashMap<String, Set<Integer>> hash = new HashMap<>();

        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }

        for (int i = 0; i < floors.length - 1; i++) {
            for (int j = i + 1; j < floors.length; j++) {
                dp[i][j] = floors[j] - floors[i];
                Set<Integer> set = new HashSet<>();
                for (int k = i; k < j+1 && k < floors.length; k++) {
                    set.add(k);
                }
                hash.put(i + " " + j, set);
                System.out.println("Entered: " + i + ",Banned: " + set + " ,Profit: " + dp[i][j]);
            }
        }

        /*
            dp now looks like this for a 4 floor arena:
            0 b c d e
            0 0 g h i
            0 0 0 j k
            0 0 0 0 l
        */

        HashMap<Set<Integer>, Integer> memo = new HashMap<>();
        return ahh(dp, 0, new HashSet<Integer>(), memo, hash);
    }

    private static int ahh(int[][] dp, int currentProfit, Set<Integer> banned, HashMap<Set<Integer>, Integer> memo, HashMap<String, Set<Integer>> hash) {
        if (memo.containsKey(banned)) {
            return memo.get(banned);
        }

        int maxProfit = currentProfit;

        for (int i = 0; i < dp.length - 1; i++) {

            if(banned.contains(i)) continue;

            for (int j = i+1; j < dp.length;j++) {
                if (banned.contains(j)) continue;

                Set<Integer> newBanned = new HashSet<>(banned);

                newBanned.addAll(hash.get(i + " " + j));

                int profitWithCurrent = ahh(dp, currentProfit + dp[i][j], newBanned, memo, hash); //recursion

                maxProfit = (profitWithCurrent > maxProfit)? profitWithCurrent: maxProfit; //if better profit then replace
            }
        }

        memo.put(banned, maxProfit);

        return maxProfit;
    }

    public static int ClimbDynamicArenaDP(int [] floors) { //that implements a dynamic programming approach to finding the maximum number of floors that a fighter can climb.        
        // fit array in hashmap for better use of data (this is not memoization meaning not all computed values will be needed)
        HashMap<Integer, Pair<Integer, int[]>> map = new HashMap<>();
        for (int i = 0; i < floors.length-1; i++) {
            int[] banned = new int[2];
            banned[0] = -1;
            banned[1] = -1;
            if (i+3 <= floors.length) {
                banned[0] = i+1;
                banned[1] = i+2;
            } else if (i+2 <= floors.length) {
                banned[0] = i+1;
            }
            int profit = floors[i+1] - floors[i];
            Pair<Integer, int[]> pair = new Pair<>(profit, banned);
            map.put(i, pair);
        }
        
        HashMap<Set<Integer>, Integer> memo = new HashMap<>(); //used for memoization to prevent multiple computation of same problem
        //it's key is the banned field because it is the best identifier for maximum profit. cannot be index because subproblem depends on more than index
        int outcome = findMaxProfit(map, 0, new HashSet<Integer>(), memo);
        
        return outcome;
    }

    private static int findMaxProfit(HashMap<Integer, Pair<Integer, int[]>> map, int currentProfit, Set<Integer> banned, HashMap<Set<Integer>, Integer> memo) {
        if (memo.containsKey(banned)) { //if this set is already computed, return the result else compute it.
            return memo.get(banned);
        }

        int maxProfit = currentProfit; //profit so far from previous enters

        for (Map.Entry<Integer, Pair<Integer, int[]>> entry : map.entrySet()) {
            int index = entry.getKey();
            Pair<Integer, int[]> pair = entry.getValue();

            if (banned.contains(index)) continue; //if index is already banned then skip iteration

            //append newly banned to already banned for future computation
            Set<Integer> newBanned = new HashSet<>(banned);
            newBanned.add(index);
            for (int b : pair.banned) {
                newBanned.add(b);
            }

            int profitWithCurrent = findMaxProfit(map, currentProfit + pair.profit, newBanned, memo); //recursion

            maxProfit = (profitWithCurrent>maxProfit)?profitWithCurrent:maxProfit; //if better profit then replace
        }

        memo.put(banned, maxProfit); //store result of subproblem
        return maxProfit;
    }

    public static int ClimbDynamicArenaDP2(int [] floors) {  
        HashMap<boolean[], Integer> memo = new HashMap<>();
        boolean banned[] = new boolean[floors.length];
        int outcome = findMaxProfit2(floors, 0, banned, memo);
        return outcome;
    }

    private static int findMaxProfit2(int[] map, int currentProfit, boolean[] banned, HashMap<boolean[], Integer> memo) {
        if (memo.containsKey(banned)) {
            return memo.get(banned);
        }

        int maxProfit = currentProfit;

        for (int i = 0; i < map.length - 1; i++) {
            if (banned[i] == true) continue;
            boolean[] newBanned = banned;
            newBanned[i] = true; //important for stack overflow
            if(i+1 < map.length) newBanned[i+1] = true;
            if(i+2 < map.length) newBanned[i+2] = true;

            int profitWithCurrent = findMaxProfit2(map, currentProfit + (map[i+1] - map[i]), newBanned, memo);

            maxProfit = (profitWithCurrent>maxProfit)?profitWithCurrent:maxProfit;
        }

        memo.put(banned, maxProfit);
        return maxProfit;
    }

    /* Notes:
            - We have an array of ints representing a reward/fee of a floor i.
            - We are moving sequentially on array from 0 to n-1. 
            - If we choose to enter: 1. we pay fee of floor i
                                     2. get reward of floor i+1
                                     3. neither get reward nor pay fee of floor i+2
            - If we choose to skip we neither pay nor get reward.
            - At every floor we need to decide wether to skip or enter

            Goal: Maximize outcome (minimum fees and maximum rewards).
    */
}
