/*
Name: Aneri Desai(anerid)
Course: CPSC2310-001
Program: Assignment-04
Date: 04/26/2019
*/

.file "encode.s"
.global encode

/*
Function name: encode

description: load in the letter from r0 and r2 and check the conditions on
             them and encode or decode the message depending on the
             parameter passed in after that store that number into the
             output string r2

input parameteres:
       r0 - address of input string
       r1 - address of output string
       r2 - address of key string
       r3 - encode/decode with switch

return value:
       r2 - store each encoded or decoded bit in the address provided

other output parameteres - N/A

effect/output - changes the output string/array

method/effect - N/A

typical calling sequence: n/a

*/
encode:

    push {r4-r7, lr}

    mov r4, #0 //counter for input string, i
    mov r5, #0 //counter for key string, j
    mov r6, #0 //key temp
    mov r7, #0 //input temp

    input:
        ldrb r7, [r0, r4] //array letter

        cmp r7, #0 //checking the null symbol
        beq done

    key:
        ldrb r6, [r2, r5] //key letter

        cmp r6, #0 //checking the null symbol
        beq start

        cmp r7, #97
        blt store

        cmp r7, #123
        bge store

        cmp r6, #97 //blank space in key
        blt store

        cmp r6, #123 //other symbols
        bge store

        cmp r3, #0 //encode or decode
        bne decoding

    encoding:

        sub r7, r7, #96

        add r7, r7, r6

        cmp r7, #122 //checking value dont go above
        ble store

        sub r7, r7, #26 //above sub 26

        b store

    decoding:

        sub r6, r6, #96

        sub r7, r7, r6

        cmp r7, #97 //value dont go below
        bge store

        add r7, r7, #26 //below add 26

        b store

   start:

       mov r5, #0 //resetting key counter
       b key

   store:
       strb r7, [r1, r4]

       add r4, r4, #1 //i++
       add r5, r5, #1 //add the key

       bal input

    done:
        mov r7, #0
        strb r7, [r1, r4] //stroing null symbol in the end

    pop {r4-r7, pc}
