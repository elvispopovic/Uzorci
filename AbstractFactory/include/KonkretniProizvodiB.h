#ifndef KONKRETNIPROIZVODIB_H
#define KONKRETNIPROIZVODIB_H

#include <IApstraktniProizvodi.h>

class KonkretniProizvodB1 : public IApstraktniProizvodB
{
    public:
        string ProvjeriProizvod();
};

class KonkretniProizvodB2 : public IApstraktniProizvodB
{
    public:
        string ProvjeriProizvod();
};

#endif // KONKRETNIPROIZVODIB_H
