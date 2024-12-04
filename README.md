##  Core Features

###  Game Initialization
- **Database Connection**: Connects to the database to retrieve and save game data.
- **User Authentication**: Validates user credentials (username and password).
- **Resume or Start Game**: Resumes the last saved game or initializes a new one.

###  Turn-Based Gameplay
- **Player Actions**:
  - Play a card based on the rules.
  - Draw a card from the deck if no valid moves are available.
  - Exit the game at any time.
- **Card Validation**:
  - Ensures the played card matches the last card based on:
    - **Color**
    - **Number**
    - **Special Rules**
  - Handles special cards like:
    - "Skip"
    - "Change Side"
    - "+2" (draw two cards)
    - "+4" (draw four cards)

###  Database Integration
- **Player Management**:
  - Retrieves player data and statistics.
  - Updates wins, losses, and games played.
- **Game State**:
  - Saves played cards.
  - Tracks the last played card for validation.
  - Retrieves the player's current hand of cards.

###  Dynamic Deck Management
- **Draw Cards**:
  - Automatically draws cards at the start of the game or when necessary.
  - Generates new cards with:
    - Random colors
    - Random numbers or special rules
- **Card Persistence**:
  - Saves newly drawn cards to the database.
  - Removes played cards from the player's hand.

###  Game Termination
- **Win Condition**: Ends the game when the player has no cards left, declaring them the winner.
- **Manual Exit**: Allows players to exit the game at any time.
- **Database Cleanup**:
  - Updates player victories if the game is won.
  - Clears the player's deck in the database after the game ends.

---
