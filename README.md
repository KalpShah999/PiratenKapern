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

Remark: **We are assuming here you are using a _real_ shell (e.g., anything but PowerShell on Windows)**

## Feature Backlog

 * Status: 
   * Pending (P), Started (S), Blocked (B), Review (R), Done (D)
 * Definition of Done (DoD):
   * < Code compiles and runs without any possible errors. Considers and handles all edge cases. >

### Backlog 

| MVP? | Id  | Feature                                    | Status  |  Started  | Delivered |
| :-:  | :-: |   ---                                      |   :-:   |    :-:    |    :-:    |
| x    | F01 | Roll a dice                                |    D    | 01/01/23  | 01/09/23  |
| x    | F02 | Roll eight dices                           |    D    | 01/09/23  | 01/09/23  |
| x    | F03 | Select how many games as command-line arg. |    D    | 01/09/23  | 01/09/23  |
| x    | F04 | end of game with three cranes              |    R    | 01/13/23  | 01/13/23  |
| x    | F05 | Player keeping random dice at their turn   |    R    | 01/13/23  | 01/13/23  |
| x    | F06 | Score points: 3-of-a-kind                  |    S    | 01/13/23  |           |
| ...  | ... | ... |

