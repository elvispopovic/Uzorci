#ifndef IAPSTRAKTNIPROIZVODI_H
#define IAPSTRAKTNIPROIZVODI_H

#include <string>

using namespace std;

class IApstraktniProizvodA
{
    public:
        virtual ~IApstraktniProizvodA(){};
        virtual string ProvjeriProizvod()=0;
};

class IApstraktniProizvodB
{
    public:
        virtual ~IApstraktniProizvodB(){};
        virtual string ProvjeriProizvod()=0;
};

class IApstraktniProizvodC
{
    public:
        virtual ~IApstraktniProizvodC(){};
        virtual string ProvjeriProizvod()=0;
};



#endif // IAPSTRAKTNIPROIZVODI_H
