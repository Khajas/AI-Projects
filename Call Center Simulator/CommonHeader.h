#ifndef common_header
#define common_header

#include <iostream>
#include <vector>
#include <algorithm>
#include <cstdlib>
using namespace std;
enum class Rank{Responder, Manager, Director};
enum class CallStatus{Arrived, Waiting, Answered, Resolved};
class Caller;
class Call;
class Employee;
const int MAX_RESPONDERS=10;
const int MAX_MANAGERS=4;
const int MAX_DIRECTORS=2;

#endif
