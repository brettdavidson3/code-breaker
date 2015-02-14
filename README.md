Code Breaker
============

A frantic word completion game.

## Playing the game

Code Breaker prototype is now available.  

### Installation

To play, please make sure you Java 8 installed on your computer, available [here](http://java.com/en/download/).

Next, download the jar file located [here](https://drive.google.com/file/d/0BySgHim9hPrJbEp0TERDVjZqT0E/view?usp=sharing).

#### Mac and PC

Finally, double clicking the jar file will open the game automatically.

#### Linux

If double clicking the jar doesn't open the file, open a terminal and type:
```
java -jar [path to jar]
```

### Objective

Try to complete the words at the bottom of the screen with as little guesses as possible. Letters will fall from the top one by one. Control the letters and place them at your best guess for where letters exist in the mystery word.  As you guess correctly the mystery word will become visible.

Once the incorrect guesses stack up and reach the top, you will lose. Try to beat your high score each time you play.

### Controls

On the title screen use the UP and DOWN arrow keys to choose your difficulty, and press ENTER to start the game.

During the game, use the LEFT and RIGHT arrow keys to move the falling letter to the desired position.  You may hold the DOWN arrow key to make the letter fall faster.

## Development

Code Breaker is written in Java 8 and relies on the [libgdx framework](https://github.com/libgdx/libgdx).
Currently, **only the Desktop version of the game is supported**.

### Install Java 8 SE

To begin development you must first install Java 8 SDK.  You can download and install it  [here](http://www.oracle.com/technetwork/java/javase/downloads/index.html?ssSourceSiteId=ocomen).

### Choose your development environment

Libgdx is designed to work via the command line or in your favorite IDE.  Development in IntelliJ is recommended as
most work to this date has been done in that environment.  If you choose another development environment you may have to iron
out some additional configuration details.

#### IntelliJ Setup

* Follow the instructions for importing a project [here](https://github.com/libgdx/libgdx/wiki/Gradle-and-Intellij-IDEA#importing-your-project).
* Set up the run configuration for the **Desktop** according to that [same page](https://github.com/libgdx/libgdx/wiki/Gradle-and-Intellij-IDEA#running-your-project).
* Test out your run configuration by hitting the play button.
* Depending on your environment, you may have to increase the language level setting for your IntelliJ project.
  * Click File -> Project Structure -> Project and set the Project Language Level to 8
  
#### Other development environments

You can refer to the Libgdx [Getting Started](https://github.com/libgdx/libgdx#getting-started) guide to set up Code Breaker for other environments.
**Warning**: you may have to sort out additional configuration details on your own.
  
