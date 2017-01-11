// Header file for ChatterBot
// Created: 01/11/17
// Last Modified: 01/11/07
//#ifndef chatterBot_h
//#define chatterBot_h
// System headers
#include <iostream>
#include <map>
#include <algorithm>
#include <ctype.h>
#include <fstream>
using namespace std;

// Function Prototypes

void To_Lower(char&);
string to_lower(string&);
bool is_exit_requested(const string&);
string readFromMap(const map<string, string>&, const string&);
string readFromMemoryDB(ifstream&, const string&);
//#endif
