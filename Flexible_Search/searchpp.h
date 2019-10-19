/*
Name: Aneri Desai(anerid)
Date: 30th November 2018
File: searchpp.h
class: cpsc2120 003
Assignemnt name: Flexible search
*/
#include<iostream>
#include "find.h"
#include<stdio.h>
#include<dirent.h>
#include<string.h>

using namespace std;

void search(){

    string str;

    //asks the user for the input using the getline
    cout << "enter your query: ";
    getline(cin, str);

    //if string empty then prints empty string
    if(str.empty() ||str == " ")
        cout << endl << "The best match is: " << " " << endl << endl;

    else{

        var search(str);//pass in the user input
        search.openDir();//call the open files function
        search.openText();//call the open textfile function
        search.print();//call the print function

    }

}
