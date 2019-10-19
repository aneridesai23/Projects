/*
Name:Aneri Desai(anerid)
Date: 30th November 2018
File name: Find.h
class:cpsc2120 section003
Asssignment Name: Flexible search
*/
#include <stdio.h>
#include <dirent.h>
#include<iostream>
#include<vector>
#include<fstream>
#include<iomanip>
#include<map>
#include<string>
#include<iterator>
#include<algorithm>
#include<cstring>
#include<cctype>

using namespace std;

class var{
private:
    map<string, vector<string> > directory;
    string query;
    map<int,string> stored;
    string secondQuery;
    string strQueries;
public:
    var(string q);
    void openDir();
    void openText();
    int strWord();
    string ifWordExist(int countExist);
    string completeSearch();
    string misSplWord();
    string strExactSear();
    string bestFile();
    string strFindMap(string strMa);
    string bestStrSear();
    string misSplStri();
    void print();
};

/*
constructor to store a string, removes spaces, and turn it into a lowercase
*/
var::var(string q){
    query = q;
    strQueries = q;

    //removing the empty spaces from the string
    if(strWord() > 1){
        for(unsigned int i = 0; i < strQueries.length(); i++){
            if(strQueries[i] == ' ')
                strQueries.erase(i,1);
        }
    }

    //turning query into lower case
    transform(query.begin(), query.end(), query.begin(), ::tolower);
    transform(strQueries.begin(), strQueries.end(), strQueries.begin(), ::tolower);
}

/*open the content folder and read the .txt files and storing them as string
inside a map of string
*/
void var::openDir(){
    DIR *dp;
    struct dirent *ep;
    string str;

    //open the content folder and search for the files
    dp = opendir("content");
    if(dp != NULL){
        while((ep = readdir(dp)) != NULL) {
            str = ep->d_name;
            if(str == "." || str == "..") //dont store . and .. into the map
                continue;
            directory[str];
        }
        closedir(dp);
    } else {
        cout << "couldn't open the directory!";
    }
}

//open each text file and store the words or lines inside them into a each file string of vector in map
void var::openText(){
    string word;
    map<string,vector<string> >::iterator it = directory.begin();

    while(it != directory.end()){
       string name = it->first;
       if (name == "." || name == "..") {
           it++;
           continue;
       }
       fstream inFile;
       inFile.open("content/"+name);

        while(getline(inFile,word)){            
            directory[name].push_back(word);//store the strings inside vector of strings in map
        }

        inFile.close();
        it++;
    }

}

/*
if the query and a string in map will match exactly then it will return the file
*/
string var::ifWordExist(int countExist){
    //making a iterator to loop through the map
    for(map<string, vector<string> >::iterator it = directory.begin(); it!= directory.end(); it++){

        string word = (*it).first;
        string qry = word; 
        string word1 = word;//this word will check the similarity without the .txt extenstion
        int charCount = word1.length();
        int y, o;

        //turning the filename into the lowercase
        transform(word.begin(), word.end(), word.begin(), ::tolower);
        transform(word1.begin(), word1.end(), word1.begin(), ::tolower);

        //cutting the .txt extention
        charCount = charCount-4;
        word1 = word1.substr(0,charCount);

        //use the find function of string to check if file name exist in query
        int t = query.find(word);
        int j = query.find(word1);

       /*this will find if query word will exist in the file name
         it is only if user enters a string
       */
        if(countExist == 1){
            y = word.find(query);
            o = word1.find(query);
        }

        /*
         if the word is found then it will return the file name
        */
        if(countExist != 1){
            if(t != -1 || j != -1){
                return qry;
            }
        }
        else{
            if( t != -1 || j!= -1 || y != -1 || o != -1)
                return qry;
        }
    }
    return "";//if no similarity is find then return an empty string
}

/*
if query is not exactly matched then it will return the file that is most similar to query
*/
string var::misSplWord(){
    string word;
    string word1;
    vector<char> wrd;
    vector<char>qry;
    int count3;
    int maxCount = 0; 

    //iterator to loop through the map
    for(map<string, vector<string> >::iterator ite = directory.begin(); ite != directory.end(); ite++){

        word = (*ite).first;
        word1 = word;

        //making the vectors empty
        wrd.clear();
        qry.clear();

        int wSize = word.length();
        int qSize = query.length();
        int count = 0;
        int s = 0;
        int p = 0;
        int bStillSame = 0;
        int dStillSame = 0;
        int querySmallCount = wSize-4;

        //removes the .txt extenstion from the file name if query does not have .txt extenstion
        if(query.length() >= 4){
            string sbstr = query.substr(query.length()-4, query.length());
            if(sbstr != ".txt"){
                wSize = wSize-4;
                word = word.substr(0,wSize);
            }
        }

        //push word and query string into vector character by character
        for(int l = 0; l < wSize; l++){
            wrd.push_back(word[l]);
        }

        for(int r = 0; r < qSize; r++){
            qry.push_back(query[r]);
        }

        //loop through the filename and query vector 
        for(int b = 0 , d = 0; b < wSize && d < qSize; ){
             s = b+1;
             p = d+1;
             bStillSame = b;
             dStillSame = d;

             //if character at particular position matches
             if(wrd[b] == qry[d]){
                 count++; //count increases
                 b++;
                 d++;
             }
             else{
                 if(p <= qSize){
                     //character match at original position for filename and next position for query
                     if(wrd[b] == qry[p]){
                         b++;
                         d = p+1;
                         count++;               
                     }
                 }
                 else if(s <= wSize){
                    //character match for next position for filename and original position for query
                     if(wrd[s] == qry[d]){
                         b = s+1;
                         d++;
                         count++;
                     }
                 }
                 //increasing both the query and filename character position by 1
                 else if(s <= wSize && p <= qSize){
                     if(wrd[s] == qry[p]){
                         b = s+1;
                         d = p+1;
                         count++;
                     }
                 }
                 break;
             }
             if(bStillSame == b && dStillSame == d){
                 b = b+2;
                 d = d+2;
             }
         }
         count3 = querySmallCount/2; 

        //insert a filename into a map if the new count is higher then any other count
        if(maxCount < count){
             maxCount = count;
             stored.insert({count,word1});
        }
    }

     //return the stored filename into a map only if similarity between query and word is about half or higher
    if(maxCount >= count3){
        return stored[maxCount];
    }else {
        return "";
    }
}

/*
if the query and string inside a text file is match to match then return the filename it is found in
*/
string var::strExactSear(){

    //calling the bestFile function
    string strngWord = bestFile();
    string strngWord1 = " ";
    string stringWord2 = " ";
    string actualStr = " ";
    string actualStr1 = " ";
    string actualStr2 = " ";

    //checks the strings inside a textfile that bestFile returned
    if(strngWord != ""){
        string wrdInMap2 = strFindMap(strngWord);
        
        //iterator for the vector<string> inside map
        for(auto it1 = directory[wrdInMap2].begin(); it1 != directory[wrdInMap2].end(); ++it1){

            string firstStr = *it1;
            actualStr = firstStr;

            transform(firstStr.begin(), firstStr.end(), firstStr.begin(), ::tolower);

            //if the string will match then return the file name
            if(firstStr.compare(query) == 0){
                secondQuery = actualStr;
                return strngWord;
            } 
        }
        strngWord1 = wrdInMap2;
    }

    //if different results are found for two functions then checks the second file and the strings inside it

    if((ifWordExist(0) != "" && misSplWord() != "") && (ifWordExist(0) != misSplWord())){
       stringWord2 = ifWordExist(1);

       for(auto it3 = directory[stringWord2].begin(); it3 != directory[stringWord2].end(); ++it3){
           string firstSt = *it3;
           actualStr1 = firstSt;

           transform(firstSt.begin(), firstSt.end(), firstSt.begin(), ::tolower);

           if(firstSt.compare(query) == 0){
               secondQuery = actualStr1;
               return stringWord2;
           }
       }
    }

    //go inside whichever textfile is left and compare the strings    
    for(map<string, vector<string> >::iterator it5 = directory.begin(); it5 != directory.end(); it5++){
        
        string chckSimStr = (*it5).first;

        //if the filename matches then skips it
        if(chckSimStr == strngWord1 || chckSimStr == stringWord2)
            it5++;
        else{
            for(auto it8 = directory[chckSimStr].begin(); it8 != directory[chckSimStr].end(); ++it8){
              
                string vectString = *it8;
                actualStr2 = vectString;

                transform(vectString.begin(),vectString.end(), vectString.begin(), ::tolower);

                if(query.compare(vectString) == 0){
                    secondQuery = actualStr2;
                    return chckSimStr;
                }
             }
        }
    }
    return "";//return empty string if no exact string is found
}

/*
just a helper function that will loop over the map of strings
*/
string var::strFindMap(string strMap){

    string wrdInconstMap;
    int strKeepConstCount = 0;

        for(map<string, vector<string> >::iterator it1 = directory.begin(); it1 != directory.end(); it1++){
            string wrdInMap = (*it1).first;
  
            //saves the first filename
            if(strKeepConstCount == 0)
                wrdInconstMap = wrdInMap;

            //if the string passed in matches file name return that file
            if(strMap == wrdInMap){
                return wrdInMap;
            }
            strKeepConstCount++;
        }
        return wrdInconstMap;//return the first filename
}

/*
helper function that will find the textfile that best matches
*/
string var::bestFile(){
  
    if(ifWordExist(0) == "" && misSplWord() == ""){
        return "";
    }
    //if both functions return the file
    else if(ifWordExist(0) != "" && misSplWord() != ""){
        //checks if return values are same
        if(ifWordExist(0) == misSplWord()){
            return ifWordExist(0);
        }
        else{
            return misSplWord();//if not return the result of misSplWord
        }
    }
    //if one of the file return a string and other does not
    else if(ifWordExist(0) != "" && misSplWord() == ""){
        return ifWordExist(0);
    }
    else if(ifWordExist(0) == "" && misSplWord() != ""){
        return misSplWord();
    }
    return "";
}

/*
returns the results according to whether query is word or string
*/
string var::completeSearch(){
    int countNum = strWord();

    if(countNum == 1){
        return bestFile();
    }
    else{
        return bestStrSear();
    }
}

/*
if the string is not exact return the string that is similar
*/
string var::misSplStri(){
    string word;
    string word1;
    vector<char> wrd;
    vector<char>qry;
    int count3;
    int maxCount = 0;
    string newString1 = "";

    //iterator to loop through the map
    for(map<string, vector<string> >::iterator ite = directory.begin(); ite != directory.end(); ite++){

        word = (*ite).first;

        for(auto it11 = directory[word].begin(); it11 != directory[word].end(); ++it11){

            string newString = *it11;
            newString1 = newString;

            transform(newString.begin(), newString.end(), newString.begin(), ::tolower);
            //removes the whitespaces from the strings
            for(unsigned int f = 0; f < newString.length(); f++){
                if(newString[f] == ' ')
                    newString.erase(f,1);
            }

            //making the vectors empty
            wrd.clear();
            qry.clear();

            int wSize = newString.length();
            int qSize = strQueries.length();
            int count = 0;
            int s = 0;
            int p = 0;
            int bStillSame = 0;
            int dStillSame = 0;
            int querySmallCount = wSize-4;

            //removes the .txt extenstion from the file name if query does not have the extenstion
            if(strQueries.length() >= 4){
                string sbstr = strQueries.substr(query.length()-4, strQueries.length());
                if(sbstr != ".txt"){
                    wSize = wSize-4;
                    newString = newString.substr(0,wSize);
                }
            }

            //push word and query string into vector character by character
            for(int l = 0; l < wSize; l++){
                wrd.push_back(newString[l]);
            }

            for(int r = 0; r < qSize; r++){
                qry.push_back(strQueries[r]);
            }

            //loop through the strings and query vector 
            for(int b = 0 , d = 0; b < wSize && d < qSize; ){
                s = b+1;
                p = d+1;
                bStillSame = b;
                dStillSame = d;

                //if character at particular position matches
                if(wrd[b] == qry[d]){
                    count++; //count increases
                    b++;
                    d++;
                }
                else{
                    if(p <= qSize){
                        //character match at original position for strings and next position for query
                        if(wrd[b] == qry[p]){
                            b++;
                            d = p+1;
                            count++;               
                        }
                    }
                    else if(s <= wSize){
                        //character match for next position for strings and original position for query
                        if(wrd[s] == qry[d]){
                            b = s+1;
                            d++;
                            count++;
                        }
                    }
                    //increasing both the query and strings character position by 1
                    else if(s <= wSize && p <= qSize){
                        if(wrd[s] == qry[p]){
                            b = s+1;
                            d = p+1;
                            count++;
                        }
                    }
                    break;
                }
                if(bStillSame == b && dStillSame == d){
                    b = b+2;
                    d = d+2;
                }
            }    
            count3 = querySmallCount/3;
      
            //insert a filename into a map if the new count is higher then any other count
            if(maxCount < count){
                maxCount = count;
                stored.insert({count,word});
                secondQuery = newString1;
            }
        }
    }
    //return the stored filename into a map only if similarity between query and word is *half or higher
    if(maxCount >= count3){        
        return stored[maxCount];
    }else {
        secondQuery = " ";
        return "";
    }
}

/*
return the string that best matches
*/
string var::bestStrSear(){
    if(strExactSear() == "" && misSplStri() == ""){
        return "";
    }
    else if(strExactSear() != "" && misSplStri() != ""){
        if(strExactSear() == misSplStri()){
            return strExactSear();
        }else{
            return misSplStri();
        }
    }
    else if(strExactSear() != "" && misSplStri() == ""){
        return strExactSear();
    }
    else if(strExactSear() == "" && misSplStri() != ""){
        return misSplStri();
    }

    return "";
}
 
//count if string has 1 word or higher
int var::strWord(){
    int count = 0;
    unsigned int i = 0;
    
    //removes the whitespaces from query in the beginning
    while(isspace(query.at(i)))
        i++;

        for(; i < query.length(); i++){
            //checks if query has white space or not 
            if(isspace(query.at(i))){
                count++;

                if(count >= 1){ //return if it finds a whitespace
                    return count+1;
                }
            }
        }
        return count+1; //if no whitespaces are found
}

//print function to print the filename and the query
void var::print(){
    cout << endl;

    cout << "The best match is: " << completeSearch() << endl;
    
    if(completeSearch() == "")
        cout << "Sorry, file does not exist :(" << endl << endl;

    if(strWord() > 1){
        cout << "The phrase that best matches is: " << secondQuery << endl; 

        if(completeSearch() == "")
            cout << "Sorry, the phrase does not exist ;(" << endl;
    }
    cout << endl;
}
