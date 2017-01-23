#include "main.h"
int main(int argc, char** argv) {
	vector<Caller*> callers;
	vector<Call*> calls;
	vector<Responder*> resp(MAX_RESPONDERS);
	vector<Manager*> mangs(MAX_MANAGERS);
	vector<Director*> dirs(MAX_DIRECTORS);
	make_agents(resp);
	make_managers(mangs);
	make_directors(dirs);
	int num_callers=30;
	srand(time(0));
	for(int i=0; i< num_callers; ++i){
		string name="Caller #"+to_string(i+1);
		Call* cl=make_callers(name, callers, calls);
		callHandler(cl,resp, mangs, dirs, calls);
		resolveRandomCalls(calls,resp, mangs, dirs);
	}
	statsBoard(calls);
	return 0;
}

void resolveRandomCalls(vector<Call*>& calls, vector<Responder*>& resp, vector<Manager*>& mngs, vector<Director*>& dirs){
	int random_num=rand();
	try{
		if(calls.size()!=0){
			Call* cl=calls.at(random_num%calls.size());
			if((cl->getCallStatus()=="Resolved") || (cl->getCallStatus()=="Waiting"))
				resolveRandomCalls(calls, resp, mngs, dirs);
			Employee* e=cl->getEmployee();
			if(random_num%2==0){
				if(e!=nullptr)
					e->escalate();
				callHandler(cl, resp, mngs, dirs,calls);
			}
			else{
				if(e!=nullptr)
					e->disconnectCall();
			}
		}
	}
	catch(const std::out_of_range& oor){
		return;
	}
}

void assignCall(Employee*& emp, Call*& cl, vector<Call*>& calls){
	if(cl->getCallStatus()=="Resolved") return;
	if(!emp) return;
	if(emp->getAvail()){
		emp->assignCall(cl);
		cl->setEmployee(emp);
		Call* cll=emp->getCallInfo();
		Caller* clr=cll->getCaller();
		cout<<"Caller is: "<<clr->getCallerName()<<" Assigned to: "<<emp->getName()<<endl;
	}
	else{
		cout<<"Requested Agent: "<<emp->getName()<<", Agent Status: Unavilable. On call with: "<<emp->getCallInfo()->getCaller()->getCallerName()<<endl;
		cl->setCallStatus(CallStatus::Waiting);
		cout<<"Call status changed to: "<<cl->getCallStatus()<<endl;
	}
	statsBoard(calls);
	system("cls");
}
void callHandler(Call*& cl, vector<Responder*>& resp, vector<Manager*>& mngs, vector<Director*>& dirs, vector<Call*>& calls){
	if(cl->getCallStatus()=="Resolved") return;
	Employee* emp=nullptr;
	if(cl->getRank()=="Director"){
		emp=dirs[rand()%dirs.size()];
	}
	else if(cl->getRank()=="Manager"){
		emp=mngs[rand()%mngs.size()];
	}
	else{
		emp=resp[rand()%resp.size()];
	}
	assignCall(emp, cl, calls);
}
void make_agents(vector<Responder*>& res){
	for(int i=0; i<res.size();++i){
		string name="R#"+to_string(i+1);
		res[i]=new Responder(name);
	}
}
void make_managers(vector<Manager*>& mng){
	for(int i=0; i<mng.size();++i){
		string name="M#"+to_string(i+1);
		mng[i]=new Manager(name);
	}
}
void make_directors(vector<Director*>& dir){
	for(int i=0; i<dir.size();++i){
		string name="D#"+to_string(i+1);
		dir[i]=new Director(name);
	}
}
Call* make_call(Caller*& c, vector<Call*>& call){
	Call* cl=new Call(c);
	call.push_back(cl);
	return cl;
}
Call* make_callers(const string& caller_name, vector<Caller*>& caller, vector<Call*>& calls){
	Caller* c=new Caller(caller_name);
	caller.push_back(c);
	return make_call(c, calls);
}

void statsBoard(vector<Call*>& calls){
	int calls_waiting=0, calls_answered=0, calls_resolved=0;
	for(auto it=calls.begin(); it!=calls.end();++it){
		string call_status= (*it)->getCallStatus();
		if(call_status=="Waiting") ++calls_waiting;
		if(call_status=="Answered") ++calls_answered;
		if(call_status=="Resolved"){
			++calls_resolved;
			calls.erase(it);
			--it;
		}
	}
	cout<<endl<<endl;
	cout<<string(38,'*')<<endl;
	cout<<"Calls Arrived: "<<calls.size()+calls_resolved<<". Calls Asnwered: "<<calls_answered+calls_resolved<<endl;
	cout<<"Calls Waiting: "<<calls_waiting<<". Calls Resolved: "<<calls_resolved<<endl;
	cout<<string(38,'*')<<endl;
	std::this_thread::sleep_for(std::chrono::seconds(1));
}
