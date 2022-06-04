# check if user provided string is palindrome

.data

userInput: .space 64
stringAsArray: .space 256

welcomeMsg: .asciiz "Enter a string: "
calcLengthMsg: .asciiz "Calculated length: "
newline: .asciiz "\n"
yes: .asciiz "The input is a palindrome!"
no: .asciiz "The input is not a palindrome!"
notEqualMsg: .asciiz "Outputs for loop and recursive versions are not equal"

.text

main:

	li $v0, 4
	la $a0, welcomeMsg
	syscall
	la $a0, userInput
	li $a1, 64
	li $v0, 8
	syscall

	li $v0, 4
	la $a0, userInput
	syscall
	
	# convert the string to array format
	la $a1, stringAsArray
	jal string_to_array
	
	addi $a0, $a1, 0
	
	# calculate string length
	jal get_length
	addi $a1, $v0, 0
	
	li $v0, 4
	la $a0, calcLengthMsg
	syscall
	
	li $v0, 1
	addi $a0, $a1, 0
	syscall
	
	li $v0, 4
	la $a0, newline
	syscall
	
	addi $t0, $zero, 0
	addi $t1, $zero, 0
	la $a0, stringAsArray
	
	# Swap a0 and a1
	addi $t0, $a0, 0
	addi $a0, $a1, 0
	addi $a1, $t0, 0
	addi $t0, $zero, 0
	
	# Function call arguments are caller saved
	addi $sp, $sp, -8
	sw $a0, 4($sp)
	sw $a1, 0($sp)
	
	# check if palindrome with loop
	jal is_pali_loop
	
	# Restore function call arguments
	lw $a0, 4($sp)
	lw $a1, 0($sp)
	addi $sp, $sp, 8
	
	addi $s0, $v0, 0
	
	# check if palindrome with recursive calls
	jal is_pali_recursive
	bne $v0, $s0, not_equal
	
	beq $v0, 0, not_palindrome

	li $v0, 4
	la $a0, yes
	syscall
	j end_program

	not_palindrome:
		li $v0, 4
		la $a0, no
		syscall
		j end_program
	
	not_equal:
		li $v0, 4
		la $a0, notEqualMsg
		syscall
		
	end_program:
	li $v0, 10
	syscall
	
string_to_array:	
	add $t0, $a0, $zero
	add $t1, $a1, $zero
	addi $t2, $a0, 64

	
	to_arr_loop:
		lb $t4, ($t0)
		sw $t4, ($t1)
		
		addi $t0, $t0, 1
		addi $t1, $t1, 4
	
		bne $t0, $t2, to_arr_loop
		
	jr $ra


#################################################
#         DO NOT MODIFY ABOVE THIS LINE         #
#################################################
	
get_length:
	lb $t0, newline

	# return value = 0
	add $v0, $zero, $zero
	
	while:
		lw   $t1 0($a0)        # load next element
		beq  $t1, $t0, exit   # if character == '\n' exit loop
		addi $v0 $v0 4
		addi $a0 $a0 4       # point on the next element in $a0 
		j while
	
	exit:
		jr $ra
	
is_pali_loop:
	addi $t0, $a0, 0     # t0 = length
	addi $t1, $a1, 0     # index = 0 
	addi $t5, $a0, -4    # index = length - 1
	add  $t2, $a1, $t5   # $t2 = length - 1
	
	loop:
		lw   $t3, 0($t1)         # t3 = first element
		lw   $t4, 0($t2)         # t4 = last element
		beq  $t3, $t4, True      # if True continue
		bne  $t3, $4, False      # if false exit
	
	False:
		addi $v0, $zero, 0
		jr   $ra
	
	True:
		addi $v0, $zero, 1
		addi $t1, $t1, 4       # t1++
		addi $t2, $t2, -4      # t2 points on predecessor 
		beq  $t2, $a1, end     # if t2 = length end of loop
		j    loop 
		
	end:	
		jr   $ra
	
	
is_pali_recursive:
	addi $t0, $a1, 0             # t0 = 0
	addi $t1, $a0, -4            # index = length - 1
	add  $t2, $a1, $t1           # $t2 = length - 1
	addi $t3, $zero, 4           # used later
	beq  $a0, $zero, recTrue     # if length == 0 return True
	addi $t4, $zero,4
	beq  $a0, $t4, recTrue       # if length == 1 return True
	
	recursion:
		beq  $a0, $zero, recTrue     # if length == 0 return True
		beq  $a0, $t3, recTrue       # if length == 1 return True
		lw   $t5, 0($t0)             # t5 = first char
		lw   $t6, 0($t2)             # t6 = last char
		bne  $t5, $t6, recFalse      # if t5 != t6 return False
		addi $a0, $a0,-8             # a0 = length - 2
		addi $t0, $t0,4              # t0++
		addi $t2, $t2,-4             # t2--
		addi $sp, $sp,-4
		sw   $ra, 0($sp)             
		jal  recursion
		lw   $ra, 0($sp)
		addi $sp, $sp, 4
		jr   $ra
		
	recFalse:
		addi $v0, $zero, 0
		jr   $ra
	
	recTrue:
		addi $v0, $zero, 1
		jr   $ra
		

	
