
package csen703.main.assignment2;


import csen703.main.assignment2.DynamicArena;

@SuppressWarnings("unused")
public class Main {
    public static void main(String[] args) {
		int[] floors= {10,7,11, 0, 0, 400};
        int outcome = DynamicArena.ClimbDynamicArenaDP(floors);
        //int outcome = DynamicArena.ololoMemo(floors);
        System.out.println("Outcome: " + outcome);
    }
}