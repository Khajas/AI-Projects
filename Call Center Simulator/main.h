#ifndef main_h
#define main_h

#include "CommonHeader.h"
#include "Caller.h"
#include "Call.h"
#include "Employee.h"
#include <ctime>
#include <stdexcept>
#include <thread>
#include <chrono>
#include <conio.h>
#include <stdio.h>

void assignCall(Employee*& emp, Call*& cl, vector<Call*>&);
void callHandler(Call*& cl, vector<Responder*>& resp, vector<Manager*>& mngs, vector<Director*>& dirs, vector<Call*>&);
void make_agents(vector<Responder*>& res);
void make_managers(vector<Manager*>& mng);
void make_directors(vector<Director*>& dir);
Call* make_call(Caller*& c, vector<Call*>& call);
Call* make_callers(const string& caller_name, vector<Caller*>& caller, vector<Call*>& calls);
void statsBoard(vector<Call*>& calls);
void resolveRandomCalls(vector<Call*>& calls, vector<Responder*>& resp, vector<Manager*>& mngs, vector<Director*>& dirs);
#endif
