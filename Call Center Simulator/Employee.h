#ifndef employee_h
#define employee_h
#include "CommonHeader.h"
// Each employee has rank and availablity, and the call it's assigned to
class Employee{
	protected:
		Rank rank;
		bool avail;
		Call* cl=nullptr;
		string name;
	public:
		Employee(string n="", Rank r=Rank::Responder, bool a=true, Call* c=nullptr):name(n),rank(r), avail(a), cl(c){}
		bool getAvail(){
			return avail;
		}
		void markAvail(){
			avail=true;
		}
		void markUnAvail(){
			avail=false;
		}
		void assignCall(Call* c){
			cl=c;
			markUnAvail();
			cl->setCallStatus(CallStatus::Answered);
		}
		void disconnectCall(){
			if(cl!=nullptr)
				cl->setCallStatus(CallStatus::Resolved);
			markAvail();
		}
		Call* getCallInfo(){
			return cl;
		}
		void setName(string n){
			name=n;
		}
		string getName(){
			return name;
		}
		virtual void escalate(){
			cout<<"Virtual escalate"<<endl;
			return;
		}
};
class Responder:public Employee{
	public:
	Responder(string n=""){
		name=n;
		rank=Rank::Responder;
	}
	void escalate(){
		cout<<"Responder's escalate"<<endl; 
		if(cl!=nullptr)
			cl->escalateCall();
		markAvail();
	}
};
class Manager: public Employee{
	public:
	Manager(string n=""){
		name=n;
		rank=Rank::Manager;
	}
	void escalate(){
		cout<<"***********Manager's escalate********"<<endl; 
		if(cl!=nullptr)
			cl->escalateCall();
		markAvail();
	}
};
class Director: public Employee{
	public:
	Director(string n=""){
		name=n;
		rank=Rank::Director;
	}
	void escalate(){
		cout<<"#############Director's escalate#############"<<endl;
		disconnectCall();
	}
};
#endif
