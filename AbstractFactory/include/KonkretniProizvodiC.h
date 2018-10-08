#ifndef KONKRETNIPROIZVODIC_H
#define KONKRETNIPROIZVODIC_H

#include <IApstraktniProizvodi.h>

class KonkretniProizvodC1 : public IApstraktniProizvodC
{
    public:
        string ProvjeriProizvod();
};

class KonkretniProizvodC2 : public IApstraktniProizvodC
{
    public:
        string ProvjeriProizvod();
};

#endif // KONKRETNIPROIZVODIC_H
