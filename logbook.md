# January 9, 2023
  * Read through the project outline and the rulebook of the game 
  * Completed adding the dice rolling features 

# January 13, 2023
  * Completed Step 2 in the instructions by adding the bare-bones functions for the game
    * Players now randomly roll and 42 rounds are simulated with the final win rate being printed 

# January 17, 2023
  * Completed fixing the techincal debts 
    * Added the Player class and created for loops to handle the dynamic data structures for players 

# January 18, 2023
  * Added some basic logging data throughout the game which saves to an external file using Log4J

# January 23, 2023 
  * Added the toggalable trace mode with the logger than can be activated using the trace arugment when running the program 
  * Added the new scoring system that takes into account the x-of-a-kind scoring  
  * Added a new custom strategy that utilizes the new scoring system to its advantage when choosing which dice to reroll 

# January 24, 2023
  * Fixed the technical debt associated with the player class and the implementation of player strategies 
  * Implemented easier methods of integrating new player strategies into the game by using an abstract functiona and lambda statements

# January 25, 2023
  * Implemented the card deck features into game
    * Includes NOP cards and sea battle cards
  * Included a new strategy that prioritizes the use of sea battle cards 

# January 26, 2023
  * Implemented the monkey business card into the card deck 
  * Updated the scoring system to account for the use of the monkey business card 

# January 28, 2023 
  * Added the dice cards, captain cards, skull cards, and sorceress cards and updated the scoring system and player strategies accordingly 
  * Introduced the island of skulls mechanism 
  * Integrated PMD into the project and made improvements based on suggestions 

# January 29, 2023
  * Added the commons CLI command line argument parser for identifying custom command line flags 
    * Used to add players, change the number of games, activate trace mode, and choose player strategies 
    * Implemented with error handling 