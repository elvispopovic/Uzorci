#ifndef IAPSTRAKTNIPROIZVODJAC_H
#define IAPSTRAKTNIPROIZVODJAC_H

#include <string>
#include <IApstraktniProizvodi.h>

using namespace std;

class IApstraktniProizvodjac
{
    public:
        virtual ~IApstraktniProizvodjac() {}
        virtual IApstraktniProizvodA* KreirajProizvodA()=0;
        virtual IApstraktniProizvodB* KreirajProizvodB()=0;
        virtual IApstraktniProizvodC* KreirajProizvodC()=0;
};

#endif // IAPSTRAKTNIPROIZVODJAC_H
