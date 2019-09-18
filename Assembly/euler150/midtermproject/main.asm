#University of Pittsburgh
#COE-147 Project Euler 150
#Instructor: Kartik Mohanram
#Teaching Assistants: Andrew and Sebastien
#-------------------------------------------------------



#Submitted by: Navdeep Handa, nkh15, 4108425


#------BEGIN--------
#---Uncomment for running the actual problem (Euler 150)---
#.include "euler150_test.asm"
#--------------------------------------------------


.data

#Uncomment ONLY ONE testcase (test and sol) at a time.
#--------------------TEST CASE SUITE---------------------begin
#TEST-1 
test: .word 4, 0, -3, -4, 1, 7, 2, 3, 5, 6, 7
sol: .word -7

#TEST-2 
#test: .word 10, 273519, -153582, 450905, 5108, 288723, -97242, 394845, -488152, 83831, 341882, 301473, 466844, -200869, 366094, -237787, 180048, -408705, 439266, 88809, 499780, -104477, 451830, 381165, -313736, -409465, -17078, -113359, 13804, 455019, -388898, 359349, -355680, 89743, 127922, 30841, 229524, -480269, -345658, 163709, -166968, 310679, 194330, 70849, -516036, -411781, -491602, 523333, 293360, -262753, -235646, -181751, -477980, 275459, 459414, 332301
#sol: .word -1495491

#TEST-3 
#test: .word 7, 273519, -153582, 450905, 5108, 288723, -97242, 394845, -488152, 83831, 341882, 301473, 466844, -200869, 366094, -237787, 180048, -408705, 439266, 88809, 499780, -104477, 451830, 381165, -313736, -409465, -17078, -113359, 13804 
#sol: .word -488152

#TEST-4 
#------Test case for the example given in www.ProjectEuler.net 150---- ---Do not uncomment
#test: .word 6, 15, -14, -7, 20, -13, -5, -3, 8, 23, -26, 1, -4, -5, -18, 5, -16, 31, 2, 9, 28, 3
#sol: .word -42
#--------------------TEST CASE SUITE---------------------end


.text
#-------------Your code goes below this line-----------
# 1) The first word pointed by the 'test' variable is the depth of the triangle
# 2) The words following the 'depth' are the elements of the triangle
# 3) The array carries depth*(depth+1)*0.5 number of elements
# Please direct your questions to pmp30@pitt.edu or shs173@pitt.edu
# Good Luck!

#in this house we M O D U L A R I Z E
#subroutines are below main

#i'm absent of my usual sassy comments bc i've gotten no more than four hours of sleep a night this week. 
#i mainly curse my procrastinative tendencies but also wtf was this

main:

##--INITIALIZING VARIABLES--##
	#$s0: int[] inputArray
	#$s1: int depth
	#$s2: int numElements
	#$s3: int[] minArr
#LOAD TRIANGLE REFERENCE INTO $S0 #int[] inputArray
la $s0, test

#LOAD TRIANGLE DEPTH INTO $S1 #int depth
lw $s1, 0($s0)

#CALCULATE TOTAL NUMBER OF TRIANGLE ELEMENTS (LESS EXPENSIVE THAN LOOPING TO COUNT THEM)
#STORE THIS VALUE IN $S2 #int numElements

#the formula for the total number of elements for the triangle is n*(n+1)/2, where n is the depth of the triangle.

#calculate n+1 (since it's not given explicitly) and store in temp

addi $t0, $s1, 1

mul $s2, $s1, $t0
div $s2, $s2, 2


#ALLOCATE ARRAY FOR STORING THE MINIMUM SUM AT EACH POINT IN THE TRIANGLE.
#THIS IS DONE DYNAMICALLY USING SYSCALL, with the result stored in $v0 after the syscall
#result moved to $S3 #int minArr[]

#since an integer is 4 bytes long, i multiply the total number of elements by 4 (sll by 2 for efficiency) to calculate the correct amount of space to allocate
#do the actual allocation
mul $a0, $s2, 4
li $v0, 9
syscall

#move the reference to the beginning of the minArr into $s3.
move $s3, $v0


##--COMPUTATION OF MINIMUM TRIANGLES--##
##--OK EVERYONE NOW IT'S TIME TO ACTUALLY DO STUFF--##
##--#COMMENTCITY--##

##--LOOP VARIABLES--##
	#$t8: int IJ
	#$t7: currIndexInTri
	#$t6: int currIndexInMinArr
	#$s4: int i
	#$s5: int j
	#$s6: int sum
	#$s7: int min

move $t8, $s0 #set currIndexInTri = 0
move $t6, $s3 #set currIndexInMinArr = 0
xor $s4, $s4, $s4 #set i = 0

forRows: #outer for loop (goes from logical row 0 to logical last row (of "index" depth -1)
beq $s4, $s1, exitMainLoop #for(int i = 0 ; i < depth ; i++) (recall that depth is stored in $s1). If true, exit out of the loop.

xor $s5, $s5, $s5  #set j = 0 after each iteration of inner loop
j forCols #executes the nested for loop.

forCols: #inner for loop
bgt $s5, $s4, updateForRows #goes back to outer loop when j > i

##START IJ LOOP ACTIVITY##

#go to next element in triangle
#int currIndexInTri++
addi $t8, $t8, 4

#get element at this offset IJ and set to sum
lw $s6, 0($t8)

#set local min to this value
move $s7, $s6


######XYLOOPGOESHERE########
##--LOOP VARIABLES--##
	#$t5: int y
	#$t4: int x
	#$t3: int endCondY
	#$t2: int XY

addi $t4, $s4, 1 #x = i+1

forX: #outer for loop
beq $t4, $s1, exitXLoop #for(int x = i+1 ; x < depth ; i++) (recall that depth is stored in $s1). If true, exit out of the loop.

move $t5, $s5 #y = j

#init endCondY = x - i + j
xor $t3, $t3, $t3
add $t3, $t3, $t4 #add x
sub $t3, $t3, $s4 #sub i
add $t3, $t3, $s5 #add j

j forY #executes the nested for loop.

forY: #inner for loop
bgt $t5, $t3, updateForX #for(int y = j; y <= x - i + j; y++) #goes back to outer loop when j > i

#######START XY LOOP ACTIVITY (EACH SUBTRIANGLE)#########

#generate index to get element at (to add to sum)
move $a0, $t4
move $a1, $t5
jal twoDtoOneDIndex
move $t1, $v0

#add the mapped index to the input array's base address so you can access it
sll $t1, $t1, 2
add $t1, $s0, $t1
lw $t9, 0($t1)
add $s6, $s6, $t9

#######END XY LOOP ACTIVITY##############################


addi $t5, $t5, 1 #y++
j forY

updateForX:

#compare current sum to min. if it's smaller, set it to min.
move $a0, $s6
move $a1, $s7
jal getMinOfTwoNumbers
move $s7, $v0

addi $t4, $t4, 1 #x++
j forX #execute back to the outer for loop

exitXLoop:

######ENDXYLOOPGOESHERE########

#store the local min in the array of mins (which has the min at each index)
#int currIndexInMinArr++
sw $s7, 0($t6)
addi $t6, $t6, 4

##END IJ LOOP ACTIVITY##

addi $s5, $s5, 1 #j++
j forCols

updateForRows: 
addi $s4, $s4, 1 #increment i (i++)
j forRows #execute back to the outer for loop

exitMainLoop:

#find minimum sum, move into $a0 for processing
move $a0, $s3
move $a1, $s2
jal getMinOfArr
move $a0, $v0

j exit


##--METHODS--##

##FUNCTION 1
getMinOfArr: 
#inputs: $a0 = address of starting element of array, $a1 = length of array
#output: $v0 = minimum value of input array #i mean in case you couldn't tell from my function name idk tho
#method vars: $t0 = address pointing to last element of array, $t1 = dereferenced pointer to current element of array, $t2 = flag for if curr < min

sll $a1, $a1, 2
add $t0, $a1, $a0
lw $v0, 0($a0) #initialize minimum value to first element. this will be modified as we loop thru array.

loopToFindMin:

beq $a0, $t0, endLoopToFindMin #check if counter is less than length of array. if it is, proceed with rest of loop

lw $t1, 0($a0) #$t1 = *p
addi $a0, $a0, 4 #p++

slt $t2, $t1, $v0
bne $t2, $0, setNewMin


j loopToFindMin

setNewMin:
move $v0, $t1
j loopToFindMin

endLoopToFindMin:
#return
jr $ra

##FUNCTION 2
getMinOfTwoNumbers:
#inputs: $a0 = first number, $a1 = second number
#output: $v0 = minimum value of two numbers #even though this is more obvious than a bad flirt
#method vars: $t0 = flag if $a0 is less than $a1

slt $t0, $a0, $a1
beqz $t0, setSecondArgAsMin
move $v0, $a0
j endGetMinOfTwoNumbers

setSecondArgAsMin:
move $v0, $a1

endGetMinOfTwoNumbers:
jr $ra

##FUNCTION 3
twoDtoOneDIndex:
#inputs: $a0 = row index, $a1 = col index
#output: $v0 = a single int that corresponds to the position in the input array that maps to the constructed triangle

#clear $v0 as safeguard against unwanted values
xor $v0, $v0, $v0
xor $t0, $t0, $t0

#generate row+1 and store in temp
addi $t0, $a0, 1

#compute [(row+1)(row)/2]+col+1
mul $v0, $t0, $a0
div $v0, $v0, 2
add $v0, $v0, $a1
addi $v0, $v0, 1

#return
jr $ra

exit:

##HELPFUL CODE SNIPPETS##
#move $a0, $s0
#move $a1, $s1
#jal getMinOfArr
#move $a0, $v0
#li $v0, 1
#syscall


#Save your final answer in the register $a0
#---------Do NOT modify anything below this line---------------
lw $s0, sol
beq $a0, $s0 pass
fail:
la $a0, fail_msg
li $v0, 4
syscall
j end
pass:
la $a0, pass_msg
li $v0, 4
syscall
end:

.data
pass_msg: .asciiz "PASS"
fail_msg: .asciiz "FAIL"
#-----END------
