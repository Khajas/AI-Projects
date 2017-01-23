#ifndef caller_header
#define caller_header
#include "CommonHeader.h"
class Caller{
	private:
	string name;
	public:
		Caller(string n="unknown"):name(n){}
		string getCallerName(){
			return name;
		}
};
#endif
