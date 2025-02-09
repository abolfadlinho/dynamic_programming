# Dynamic Arena Challenge
**CSEN 703 - Analysis and Design of Algorithms, Winter Term 2024**  

### Problem Statement
The **Dynamic Arena** is an unpredictable environment where the arena floors rise and fall dynamically. As a fighter, your goal is to strategically plan your **fighting episodes** to maximize your total rewards. An episode consists of:
1. Entering the arena (paying an entry fee equal to the floor level).
2. Winning a fight (gaining a reward equal to the floor level).

#### Constraints:
- You cannot engage in more than one fight in the same episode.
- Once you fight, you must wait for at least one arena movement before re-entering.
- Your objective is to **maximize** the total reward.

#### Example:
**Input:** `[1,7,5,3,6,4,5,8]`  
**Optimal Strategy:**
- Enter at Floor `1`, fight at Floor `7` → Gain `(7 - 1) = 6`
- Enter at Floor `3`, fight at Floor `6` → Gain `(6 - 3) = 3`
- Enter at Floor `5`, fight at Floor `8` → Gain `(8 - 5) = 3`

**Total Gain:** `6 + 3 + 3 = 12`

## Solution Approach
We solve the **Dynamic Arena Challenge** using **Dynamic Programming (DP)**. The core idea is:
- Use **memoization** to store the maximum profit for each floor.
- Decide at each step whether to **enter the arena** or **skip**.
- If entering, find the best exit floor that maximizes profit.
- The solution runs in **polynomial time**, avoiding exponential complexity.

## Implementation
### Class and Method Structure
The solution is implemented in **Java** as required:
- **Package:** `csen703.main.assignment2`
- **Class:** `DynamicArena`
- **Method:** `public static int ClimbDynamicArenaDP(int[] floors)`

### Code
```java
package csen703.main.assignment2;

import java.util.Arrays;

public class DynamicArena {
    public static int ClimbDynamicArenaDP(int[] floors) {
        int[] memo = new int[floors.length + 1];
        Arrays.fill(memo, Integer.MIN_VALUE);
        return recrec(floors, 0, memo);
    }

    private static int recrec(int[] floors, int index, int[] memo) {
        if (index >= floors.length - 1) {
            return 0;
        }
        if (memo[index] != Integer.MIN_VALUE) {
            return memo[index];
        }
        int profitIfEntered = Integer.MIN_VALUE;
        for (int i = index + 1; i < floors.length; i++) {
            profitIfEntered = Math.max(profitIfEntered, floors[i] - floors[index] + recrec(floors, i + 2, memo));
        }
        int profitIfNotEntered = recrec(floors, index + 1, memo);
        memo[index] = Math.max(profitIfEntered, profitIfNotEntered);
        return memo[index];
    }
}
```

## How to Run
1. Compile the Java file:
   ```sh
   javac -d . DynamicArena.java
   ```
2. Run from another Java program:
   ```java
   import csen703.main.assignment2.DynamicArena;
   public class Main {
       public static void main(String[] args) {
           int[] floors = {1, 7, 5, 3, 6, 4, 5, 8};
           int maxReward = DynamicArena.ClimbDynamicArenaDP(floors);
           System.out.println("Maximum Floors Climbed: " + maxReward);
       }
   }
   ```

## Author
- **Ahmed Abolfadl**
- **German University in Cairo**

