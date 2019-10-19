/*
 * Name:Aneri Desai
 * Date Submitted:09/28/2018
 * Lab Section:CPSC2121-003 
 * Assignment Name:Breadth-first and Depth-first search
 */

#pragma once

#include <string>
#include <iostream>
#include <map>
#include <vector>
#include <queue>
#include <stack>

using namespace std;

//This class represents a map of all of the cities
//feel free to add to this class but do not remove anything
class worldmap{
private:
    //created mymap data structure with string and vector 
    map<string,vector<pair<string,int>>> mymap;    
    void breath_first_search(string city1, string city2);
    void depth_first_search(string city1, string city2);
public:
    void add_pair_to_map(string city1, string city2, int distance);
    void find_path(string city1, string city2);
};

//Implement all of the functions below

//takes in two cities that are connected, the distance between them,
//and stores the values
void worldmap::add_pair_to_map(string city1, string city2, int distance){
   //making a new pair and assigning its value
    pair<string, int> p;
    p.first = city2;
    p.second = distance;

    pair<string, int> p1;
    p1.first = city1;
    p1.second = distance;
 
    //pushing that pair into the map
    mymap[city1].push_back(p);
    mymap[city2].push_back(p1); 
}

//finds the path between two cities using breath first search
//prints out cities visited during the search
//as well as the total distance traveled
void worldmap::breath_first_search(string city1, string city2){
    //making a new pair of type queue
    queue<pair<string, int> > city;
    
    city.push(make_pair(city1,0)); //pushing the pair into the queue
    cout << "BFS: ";

    int dist = 0;
    //creates a vector to store the cities visited
    vector<string> cityVisited;

    while(city.front().first != city2){
        //getting front of the queue
        pair<string,int> currentCity = city.front();
        
        cout << currentCity.first << ", ";
        dist += currentCity.second;
        
        //pushes that cities into the queue
        cityVisited.push_back(currentCity.first);
        city.pop();
        
        for(unsigned int i = 0;i < mymap[currentCity.first].size();i++){
            bool flag = true;
            //if the new city has already been visited then it wont be pushed into the queue
            for(unsigned int j = 0; j < cityVisited.size(); j++){
                if(mymap[currentCity.first][i].first == cityVisited[j]) {
                    flag = false;
                }
            }
           
            if(flag){
                city.push(mymap[currentCity.first][i]);
            }
        }
    }
    //printing out the cities visited and their total distance
    pair<string,int> lastCity = city.front();
    cout << lastCity.first << "; ";
    dist += lastCity.second;
    cout << "distance traveled: " << dist << endl;
}

//finds the path between two cities using depth first search
//prints out cities visited during the search
//as well as the total distance traveled
void worldmap::depth_first_search(string city1, string city2){
    //making a new pair of type stack 
    stack<pair<string, int> > city;
    //push a pair into the stack
    city.push(make_pair(city1, 0));

    int dist = 0;
    //creates a vector to store the cities visited
    vector<string> cityVisited;
    cout << "DFS: ";

    while(city.top().first != city2){
        //gets top of the stack
        pair<string, int> currentCity = city.top();
        
        cout << currentCity.first << ", ";
        dist += currentCity.second;
        
        //pushes those cities and distance into the stack
        cityVisited.push_back(currentCity.first);
        city.pop();

        for(int i = mymap[currentCity.first].size()-1; i >= 0; i--){
            bool flag = true;
           //if the new city has already been visited then it wont pushed into the queue
            for(unsigned int j = 0; j < cityVisited.size(); j++){
                if(mymap[currentCity.first][i].first == cityVisited[j]){
                    flag = false;
                }
            }

            if(flag){
                city.push(mymap[currentCity.first][i]);
            }
        } 
    }
    //printing out cities visited and their distance
    pair<string, int> firstCity = city.top();
    cout << firstCity.first << "; ";
    dist += firstCity.second;
    cout << "distance travelled: " << dist << endl; 
}

//this function finds the path between two cities
//using breath_first_search first and then depth_first_search
void worldmap::find_path(string city1, string city2){
   //calling both the search funcitons 
    breath_first_search(city1,city2);
    depth_first_search(city1,city2);
}
