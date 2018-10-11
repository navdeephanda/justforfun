
Java Solution to Project Euler Problem #150
======

## Intro
The java file **euler150_scratchpad.java** contains a simple solution to the question proposed at https://projecteuler.net/problem=150. This was later optimized to MIPS assembly, which explains the procedural approach and certain design choices, such as using a 1D array to represent the triangles rather than a 2D array.

## Approach
The general approach I took for the solution was an optimized brute-force algorithm.
* My algorithm was brute force in the sense that I summed up every triangle to check for which one had the smallest sum but optimized in the sense that no unnecessary summing was done and the minimum subtriangle sum was able to be found in one pass of the triangle.
* I looped through the whole input array, and for each element, I calculated the minimum subtriangle sum by comparing the sums from, in order, from the smallest subtriangle to the biggest legal subtriangle that had the given element at its apex. This allowed me to simply add a row onto each subtriangle. For example, for the first element of a triangle with 4 rows, I'd compute the sum of the subtriangle of size 1 (trivial: I set the sum equal to it), the subtriangle of size 3 (consisting of the two triangle rows directly below it), the subtriangle of size 6, and the subtriangle of size 10 (i.e. the entire triangle), and continued on through the rest of the triangle. Obviously, as you increase the index, the number of subtriangles that you sum gets smaller, until eventually you reach the last row where the only subtriangles are those of size 1.
* As I described in the intro, I was going to code this in assembly later so I needed a clean way to represent a 2D array as a 1D array. The way I chose to approach the problem was to linearly progress through the array by having a counter that incremented for every element but enclose this linear progression in two nested for loops that created a phantom i and j coordinate so that I could index the subtriangles as related to the current element in a logical way. 
* Within this code, I had two more nested for loops that calculated, with the current i and j value given by the outer nested for loop, a way to course through all of the subtriangles for a given coordinate. I did this by setting the sum and the min of the first subtriangle of size 1 of at that index (i.e. sum=min=triangle[i]). 
* I had a nested for loop with an x and a y coordinate corresponding to the row and the column respectively of the element I want to access for addition to the subtriangle, and I had x span the next row (i+1) to the last row (depth-1), and y span the current j index to x = i + j - 1. I also created a function that maps this weird XY coordinate to an index in the linear 1D array. I compared the min on each round of the x coordinate loop, and for each element in the triangle, I stored the resulting min in an array of mins. 
* I found the minimum of this array of mins and printed it as a result. 

## Usage

#### Prerequisites
* Java JDK: https://www.oracle.com/technetwork/java/javase/downloads/index.html

#### Running the Program
* Download the java file **euler150_scratchpad.java** from this repository.
* Open a shell or command prompt.
* Compile the program from the directory this file is in: 
```
C:\AwesomeDir>javac euler_scratchpad.java
```
* Run the compiled program from this same directory:
```
C:\AwesomeDir>java euler_scratchpad
```

## Contact
Questions? Thoughts? Rants?
* Navdeep Handa (navhan@msn.com)
