package csen703.main.assignment2;

import java.util.Arrays;

@SuppressWarnings("unused")
public class DynamicArena {
    //public static int[] memo;
    public static int ClimbDynamicArenaDP(int[] floors) {
        int[] memo = new int[floors.length + 1]; //passing memo every time works here because of java reference. If it was a primitive type we would have to return it every time to obtain memo.
        Arrays.fill(memo, Integer.MIN_VALUE); //init memo with minimim to compare if it's been visited before
        return recrec(floors, 0, memo);
    }

    public static int recrec(int[] floors, int index, int[] memo) {
        if (index >= floors.length - 1) {
            return 0; //if remaining floors is 1 or 0 then you will not enter because no reward and you pay fee
        }

        if (memo[index] != Integer.MIN_VALUE) { //if we have visited this floor before (memoization)
            return memo[index];
        }

        int profitIfEntered = Integer.MIN_VALUE; //init with minimum since we seek maximum profit
        for (int i = index+1; i < floors.length; i++) {
            profitIfEntered = Math.max(profitIfEntered, floors[i] - floors[index] + recrec(floors, i + 2, memo)); //getting the best outcome if we enter in this floor, which fighting floor is best to exit at
        }
        int profitIfNotEntered = recrec(floors, index + 1, memo); //getting the best outcome if we skip this floor

        memo[index] = Math.max(profitIfEntered, profitIfNotEntered); //compare since we want to decide wether to enter or not
        return memo[index];
    }

    /* Notes:
            - We have an array of ints representing a reward/fee of a floor i.
            - We are moving sequentially on array from 0 to n-1. 
            - If we choose to enter: 1. we pay fee of floor i
                                     2. get reward of any floor after i
                                     3. neither get reward nor pay fee of floor after the one we choose to take reward of
            - If we choose to skip we neither pay nor get reward.
            - At every floor we need to decide wether to skip or enter

            Goal: Maximize outcome (minimum fees and maximum rewards).
    */
}
