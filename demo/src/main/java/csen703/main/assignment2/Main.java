
package csen703.main.assignment2;


import csen703.main.assignment2.DynamicArena;

@SuppressWarnings("unused")
public class Main {
    public static void main(String[] args) {
        int[] floors = {1,7,5,3,6,4,5,8};
        int outcome = DynamicArena.ClimbDynamicArenaDP(floors);
        System.out.println(outcome);
    }
}