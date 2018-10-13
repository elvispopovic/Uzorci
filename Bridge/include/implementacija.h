#ifndef IMPLEMENTACIJA_H
#define IMPLEMENTACIJA_H

#include <string>
#include <sstream>

using namespace std;

class IImplementacija
{
    public:
        virtual ~IImplementacija(){};
        virtual string aktivnost()=0;
    protected:
        string naziv;
        IImplementacija(string naziv) {this->naziv = naziv;}
};

class Implementacija1 : public IImplementacija
{
    public:
        Implementacija1(string naziv);
        string aktivnost();

};

class Implementacija2 : public IImplementacija
{
    public:
        Implementacija2(string naziv);
        string aktivnost();

};

#endif // IMPLEMENTACIJA_H
