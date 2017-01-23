#include "CommonHeader.h"
#ifndef call_h
#define call_h
// Each call has Caller info, escalation Rank, and it's status.
class Call{
	private:
	Caller* caller;
	Rank rank;
	Employee* emp;
	CallStatus cs;
	public:
		Call(Caller* c, Rank r=Rank::Responder, Employee* e=nullptr, CallStatus cStat=CallStatus::Arrived):caller(c), rank(r), emp(e), cs(cStat){}
		Caller* getCaller(){
			return caller;
		}
		void setCallStatus(CallStatus cs){
			this->cs=cs;
			if(cs==CallStatus::Resolved)
				cout<<"Resolved!! caller: "<<caller->getCallerName()<<endl;
		}
		string getCallStatus(){
			if(cs==CallStatus::Arrived) return "Arrived";
			else if(cs==CallStatus::Waiting) return "Waiting";
			else if(cs==CallStatus::Answered) return "Answered";
			else return "Resolved";
		}
		~Call(){}
		string getRank(){
			if(rank==Rank::Responder) return "Responder";
			else if(rank==Rank::Manager) return "Manager";
			else return "Director";
		}
		void escalateCall(){
			if(rank==Rank::Responder) rank=Rank::Manager;
			else rank=Rank::Director;
		}
		void setEmployee(Employee*& e){
			emp=e;
		}
		Employee* getEmployee(){
			return emp;
		}
};
#endif
