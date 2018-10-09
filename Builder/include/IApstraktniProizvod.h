#ifndef IAPSTRAKTNIPROIZVOD_H
#define IAPSTRAKTNIPROIZVOD_H

#include <sstream>
#include <string>

using namespace std;

class IApstraktniProizvod
{
    public:
        virtual ~IApstraktniProizvod() {}
        virtual void PostaviDio_1(string vrsta)=0;
        virtual void PostaviDio_2(string vrsta)=0;
        virtual void PostaviDio_3(string vrsta)=0;
        virtual void PostaviDio_4(string vrsta)=0;
        virtual void PostaviDio_5(string vrsta)=0;
        virtual string DajOpis()=0;
};

#endif // IAPSTRAKTNIPROIZVOD_H
