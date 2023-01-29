# A1 - Piraten Karpen

  * Author: < Kalp Shah >
  * Email: < shahk50@mcmaster.ca >

## Build and Execution

  * To clean your working directory:
    * `mvn clean`
  * To compile the project:
    * `mvn compile`
  * To run the project in development mode:
    * `mvn -q exec:java` (here, `-q` tells maven to be _quiet_)
  * To package the project as a turn-key artefact:
    * `mvn package`
  * To run the packaged delivery:
    * `java -jar target/piraten-karpen-jar-with-dependencies.jar` 
  * Choose the number of games to be simulated 
    * `java -jar target/piraten-karpen-jar-with-dependencies.jar -games [number of games to play]`
      * `java -jar target/piraten-karpen-jar-with-dependencies.jar -g`
  * Add players using the **BATTLE**, **COMBO**, or **RANDOM** strategy 
    * `java -jar target/piraten-karpen-jar-with-dependencies.jar -battle [number of players to add]`
      * `java -jar target/piraten-karpen-jar-with-dependencies.jar -b [number of players to add]`
    * `java -jar target/piraten-karpen-jar-with-dependencies.jar -combo [number of players to add]`
      * `java -jar target/piraten-karpen-jar-with-dependencies.jar -c [number of players to add]`
    * `java -jar target/piraten-karpen-jar-with-dependencies.jar -random [number of players to add]`
      * `java -jar target/piraten-karpen-jar-with-dependencies.jar -r [number of players to add]`
  * To run the packaged delivery **IN TRACE MODE**:
    * `java -jar target/piraten-karpen-jar-with-dependencies.jar -trace`
      * `java -jar target/piraten-karpen-jar-with-dependencies.jar -t`
  
  * Ex. `java -jar target/piraten-karpen-jar-with-dependencies.jar -t -g 100 -b 3 -c 5 -r 10`
    * The command above activates trace mode, adds 3 players using the battle strategy, 5 players using the combo strategy, and 10 players using the random strategy, and simulates 100 games 

Remark: **We are assuming here you are using a _real_ shell (e.g., anything but PowerShell on Windows)**

## Feature Backlog

 * Status: 
   * Pending (P), Started (S), Blocked (B), Done (D)
 * Definition of Done (DoD):
   * < Feature compiles and runs without any possible errors. Considers and handles all edge cases. Accomplishes task as intended. >

### Backlog 

| MVP? | Id  | Feature                                                                          | Status  |  Started  | Delivered |
| :-:  | :-: |   ---                                                                            |   :-:   |    :-:    |    :-:    |
| x    | F01 | Calculate Score and output score                                                 |    D    | 01/13/23  | 01/13/23  |
| x    | F02 | Keep score for two sepeterate players and display the scores                     |    D    | 01/13/23  | 01/13/23  |
| x    | F03 | Roll a Die and output the result of the rolled die                               |    D    | 01/13/23  | 01/13/23  |
| x    | F04 | Simluate a game (Player move) and output which player won                        |    D    | 01/13/23  | 01/13/23  |
| x    | F05 | Simulate 42 games and output the win rates of each player                        |    D    | 01/13/23  | 01/13/23  |
| x    | F06 | Calculate score using the x-of-a-kind and output the  win rates for each player  |    D    | 01/23/23  | 01/23/23  |
| x    | F07 | Add combo strategy and output which dice were rolled compared to random strategy |    D    | 01/23/23  | 01/24/23  |
| x    | F08 | Change player strategy and output the score of the 'smarter' player              |    D    | 01/24/23  | 01/24/23  |
| x    | F09 | Let player change the player strategy and display the win rate for each player   |    D    | 01/24/23  | 01/24/23  |
| x    | F10 | Add a deck of cards and output a new card from the deck at the start of each turn|    D    | 01/25/23  | 01/25/23  |
| x    | F11 | Add a new scoring system based on the sea battle card and output the right score |    D    | 01/25/23  | 01/25/23  |
| x    | F12 | Add a new player strategy that takes advantage of the cards and print win rate   |    D    | 01/25/23  | 01/25/23  |
| x    | F13 | Let the user choose the new strategy and print the win rates of the players      |    D    | 01/25/23  | 01/25/23  |
| x    | F14 | Add the Monkey Business card to the deck of cards and simulate a game            |    D    | 01/26/23  | 01/26/23  |
| x    | F14 | Update the scoring system to include the monkey business card and simulate a game|    D    | 01/26/23  | 01/26/23  |
| x    | F15 | Add the dice, captain, sorceress, and skull cards and print win rates            |    D    | 01/28/23  | 01/28/23  |
| x    | F16 | Add the island of skulls mechanism and print the win rates of the players        |    D    | 01/28/23  | 01/28/23  |
| x    | F17 | Add the PMD mvn implementation and output a pmd.xml log file after a game        |    D    | 01/28/23  | 01/28/23  |
| x    | F18 | Add the Commons CLI argument handler and simulate 10 games instead of 42         |    D    | 01/29/23  | 01/29/23  |
| x    | F19 | Use the trace flag to simulate 10 with trace mode activated                      |    D    | 01/29/23  | 01/29/23  |
| x    | F20 | Use the combo, battle, and random flags to add different strategy players        |    D    | 01/29/23  | 01/29/23  |
|      |     | and print the win rate                                                           |         |           |           |
| ...  | ... | ... |

