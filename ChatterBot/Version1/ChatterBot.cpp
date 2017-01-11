#include <iostream>
#include <map>
#include <algorithm>
#include <ctype.h>
// The idea is to ask a question to the computer
// Check if exit or terminate is requested
// Else search for answer in the local map or file on disk
// If the answer is found, display the answer
// Else ask the same question to the user
// Learn or store the answer into local map or any file on disk
// Repeat

using namespace std;
void To_Lower(char& c){
	c=tolower(c);
}

string to_lower(string& s){
	for_each(s.begin(), s.end(), [](char& c){
		if(c>='A' && c<='Z')
			To_Lower(c);
	});
	return s;
}

bool is_exit_requested(const string& s){
	return s=="bye" || s=="exit" || s=="quit" || s=="close";
}
int main(){
	string question, answer;
	map<string, string> learning_map;// A map of questions and answers
	cout<<"Computer: Hello, there!"<<endl;
	while(cin){
		cout<<"You: ";
		getline(cin, question);
		question=to_lower(question); 			// Let's keep the question case insensitive
		auto it=learning_map.find(question);
		if(is_exit_requested(question)){
			cout<<"Computer: Good Bye! see you later "<<endl;
			break;
		}
		if(it==learning_map.end()){	// If there is no answer
			cout<<"Computer(learn): "<<question<<endl<<"You: ";
			getline(cin, answer);
			learning_map[question]=answer;
      // Next version will:
			// Update the persistant db or file
			// If we don't use a map then we might run out of heap or memory
			// So all the learning should be stored in a file
			// Using some standard searching and sorting technequies
			// Because more learning means more questions, so each answer
			// Might delay the response
		}
		else{
			cout<<"Computer: "<<learning_map[question]<<endl;
		}
	}
	return 0;
}
