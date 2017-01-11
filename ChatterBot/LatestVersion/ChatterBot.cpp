#include "ChatterBot.h"

// The idea is to ask a question to the computer
// Check if exit or terminate is requested
// Else search for answer in the local map or file on disk
// If the answer is found, display the answer
// Else ask the same question to the user
// Learn or store the answer into local map or any file on disk
// Repeat

int main(){
	string question, answer;
	map<string, string> learning_map;// A map of questions and answers
	cout<<"Computer: Hello, there!"<<endl;
	ofstream out_file_handler;	// File handler for writing the date to the file
	ifstream in_file_handler;	// File handler for reading the data from the file
	out_file_handler.open("memory_db.txt",fstream::app);
	in_file_handler.open("memory_db.txt",fstream::out);
	while(cin){
		cout<<"You: ";
		getline(cin, question);
		question=to_lower(question); 			// Let's keep the question case insensitive
		if(is_exit_requested(question)){
			cout<<"Computer: Good Bye! see you later "<<endl;
			break;
		}
		answer=readFromMap(learning_map, question);
		if(answer==""){	// If there is no answer in the current buffer read from file: This Priority should be changed later
			in_file_handler.seekg(0, in_file_handler.beg);
			string answer=readFromMemoryDB(in_file_handler,question);
			if(answer==""){
				cout<<"Computer(learn): "<<question<<endl<<"You: ";
				getline(cin, answer);
				learning_map[question]=answer;
				out_file_handler<<question<<(char)30<<answer<<endl;
				// The data is stored in the formate: <question>RS<answer>,
				// character RS(Record Separator) can't be typed from keyboard
			}
			else cout<<"Computer: "<<answer<<endl;
		}
		else{
			cout<<"Computer: "<<answer<<endl;
		}
	}
	out_file_handler.close();
	in_file_handler.close();
	return 0;
}

string readFromMemoryDB(ifstream& in_file_handler, const string& question){
	string line, answer="";
//	cout<<"Reading from memory db ..."<<endl;
	while(in_file_handler){
		getline(in_file_handler,line,(char)30);	// Get the question until character RS(Record Separator, ASCII dec value 30)
		if(!line.empty()){
//			cout<<"Line read: "<<line<<endl;
			if(line==question){
//				cout<<"Question found: "<<line<<endl;
				getline(in_file_handler,answer,'\n');
				return answer;
			}
		}
		in_file_handler.ignore(256,'\n'); // If the current part of line(question is no needed) ignore until newline
	}
	in_file_handler.clear(); // ifstream will reach eof, so reset it so that new question can be read from beginning
//	cout<<"Question not found! "<<endl;
	return "";
}

string readFromMap(const map<string, string>& learning_map, const string& question){
//	cout<<"Reading from map..."<<endl;
	auto it=learning_map.find(question);
	if(it==learning_map.cend()){
//		cout<<"Answer not found"<<endl;
		return "";
	}
	return it->second;
}

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
