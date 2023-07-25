# WarwickPlus

## Setup

You will need OpenJDK 11 in order to run this work. For Mac/Homebrew, this can be done through the following:

```zsh
brew install openjdk@11
```

For this project, you **do not** need to install Gradle. This is handled by the `gradlew` script

### Issues

The `gradlew` script requires Java 16 or bellow, so will work with OpenJDK 11 or bellow. If you have already installed the latest version of OpenJDK on your Mac via Homebrew, you can fix this through Homebrew using the following command:

```zsh
brew reinstall openjdk@11
```

## Compiling Warwick+

### Compiling Warwick+ on MacOS and Linux

To compile the code, simply run the following:

```zsh
./gradlew build
```

### Compiling Warwick+ on Windows

To compile the application, simply run the following:

```zsh
./gradlew.bat build
```

## Running Warwick+

### Running Warwick+ on MacOS and Linux

To compile and run the application, simply run the following:

```zsh
./gradlew run
```

### Running Warick+ on Windows

To compile and run the application on Windows, simply run the following:

```zsh
./gradlew.bat run
```

## Running tests

### Running Tests on MacOS and Linux

To compile the code, simply run the following:

```zsh
./gradlew test
```

### Running Tests on Windows

To compile the code, simply run the following:

```zsh
./gradlew.bat test
```
