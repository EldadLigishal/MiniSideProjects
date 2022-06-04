.data

inMsg: .asciiz "Enter a number: "
msg: .asciiz "Calculating F(n) for n = "
fibNum: .asciiz "\nF(n) is: "
.text

main:

	li $v0, 4
	la $a0, inMsg
	syscall

	# take input from user
	li $v0, 5
	syscall
	addi $a0, $v0, 0
	
	jal print_and_run
	
	# exit
	li $v0, 10
	syscall

print_and_run:
	addi $sp, $sp, -4
	sw $ra, ($sp)
	
	add $t0, $a0, $0 

	# print message
	li $v0, 4
	la $a0, msg
	syscall

	# take input and print to screen
	add $a0, $t0, $0
	li $v0, 1
	syscall

	jal fib

	addi $a1, $v0, 0
	li $v0, 4
	la $a0, fibNum
	syscall

	li $v0, 1
	addi $a0, $a1, 0
	syscall
	
	lw $ra, ($sp)
	addi $sp, $sp, 4
	jr $ra

#################################################
#         DO NOT MODIFY ABOVE THIS LINE         #
#################################################	
	
fib: 
	addi $sp, $sp, -4
	sw   $ra, ($sp)
	
	# if n==0 return 0
	beq  $a0, $zero, ReturnZero
	
	# if n==1 return 1
	addi $t0, $zero, 1
	beq  $t0, $a0, ReturnOne

	# fib(n-1)
	addi $a0, $a0, -1
	addi $sp, $sp, -8
	sw   $a0, 0($sp)
	sw   $t0, 4($sp)
	jal  fib
	
	# fib(n-2)
	lw   $a0, 0($sp)
	lw   $t0, 4($sp)
	addi $sp, $sp, 8
	addi $t0, $v0, 0
	addi $a0, $a0, -1
	addi $sp, $sp, -8
	sw   $a0, 0($sp)
	sw   $t0, 4($sp)
	jal  fib
	
	#return fib(n-2) + fib(n-1)
	lw   $a0, 0($sp)
	lw   $t0, 4($sp)
	addi $sp, $sp, 8
	add  $v0, $v0, $t0

	exit: 
		lw $ra, ($sp)
		addi $sp, $sp, 4
		jr $ra
	ReturnZero:
		addi $v0, $zero, 0
		j exit
	ReturnOne:
		addi $v0, $zero, 1
		j exit
