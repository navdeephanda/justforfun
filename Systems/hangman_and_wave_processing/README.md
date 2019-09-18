
Hangman and Wavedit
======

## Intro
This assignment contains two C files, hangman.c and wavedit.c. Hangman contains a simple command-line program that employs the dictionary specified in dictionary.txt to provide a game of hangman for the user. Wavedit contains a simple command-line utility that can verify the presence of a wav file, set its sample rate, and reverse it.
A full explanation can be in the assignment information file, info.html.

## Usage

#### Prerequisites
* gcc compiler: https://gcc.gnu.org/install/download.html
* The following directions apply directly to a Linux system. Adjust as necessary for Mac or Windows.

#### Compilation
* Download the C files, **hangman.c** and **wavedit.c**, the Hangman dictionary text file, **dictionary.txt**, and the folder of test files for the Wavedit utility, **wavedit.c**, from this repository.
* Open a shell or command prompt.
* Compile the Hangman program from the directory this file is in. Alternatively, run build.sh.
```
\AwesomeDir>gcc -Wall -Werror --std=c99 -o hangman hangman.c
```

* Compile the Wavedit program from the directory this file is in. Alternatively, on a Unix system, run build_w.sh.
```
\AwesomeDir>gcc -Wall -Werror --std=c99 -o wavedit wavedit.c 
```

#### Execution
* Run the compiled Hangman program from this same directory:
```
\AwesomeDir>./hangman
```
* Run the compiled Wavedit program from this same directory. Running it without arguments gives you a usage menu!
```
\AwesomeDir>./wavedit
```

## Contact
Questions? Thoughts? Rants?
* Navdeep Handa (navhan@msn.com)
