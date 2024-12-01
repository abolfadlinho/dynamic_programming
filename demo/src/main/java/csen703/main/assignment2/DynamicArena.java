package csen703.main.assignment2;

import java.util.HashMap;
import java.util.HashSet;
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
