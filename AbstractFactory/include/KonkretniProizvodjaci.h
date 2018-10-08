#ifndef KONKRETNIPROIZVODJACI_H
#define KONKRETNIPROIZVODJACI_H

#include <IApstraktniProizvodjac.h>

class KonkretniProizvodjac1 : public IApstraktniProizvodjac
{
    public:
        KonkretniProizvodjac1(string& poruka);
        IApstraktniProizvodA* KreirajProizvodA();
        IApstraktniProizvodB* KreirajProizvodB();
        IApstraktniProizvodC* KreirajProizvodC();
};

class KonkretniProizvodjac2 : public IApstraktniProizvodjac
{
    public:
        KonkretniProizvodjac2(string& poruka);
        IApstraktniProizvodA* KreirajProizvodA();
        IApstraktniProizvodB* KreirajProizvodB();
        IApstraktniProizvodC* KreirajProizvodC();
};

#endif // KONKRETNIPROIZVODJACI_H
