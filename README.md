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
  * To run the packaged delivery **IN TRACE MODE**:
    * `java -jar target/piraten-karpen-jar-with-dependencies.jar trace`

Remark: **We are assuming here you are using a _real_ shell (e.g., anything but PowerShell on Windows)**

## Feature Backlog

 * Status: 
   * Pending (P), Started (S), Blocked (B), Done (D)
 * Definition of Done (DoD):
   * < Feature compiles and runs without any possible errors. Considers and handles all edge cases. Accomplishes task as intended. >

### Backlog 

| MVP? | Id  | Feature                                    | Status  |  Started  | Delivered |
| :-:  | :-: |   ---                                      |   :-:   |    :-:    |    :-:    |
| x    | F01 | Calculate Score                            |    D    | 01/13/23  | 01/13/23  |
| x    | F02 | Keep score for two sepeterate players      |    D    | 01/13/23  | 01/13/23  |
| x    | F03 | Roll a Die                                 |    D    | 01/13/23  | 01/13/23  |
| x    | F04 | Simluate a game (Player move)              |    D    | 01/13/23  | 01/13/23  |
| x    | F05 | Print out the final percentage of wins     |    D    | 01/13/23  | 01/13/23  |
| ...  | ... | ... |

