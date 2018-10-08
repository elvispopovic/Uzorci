#ifndef KONKRETNIPROIZVODIA_H
#define KONKRETNIPROIZVODIA_H

#include <IApstraktniProizvodi.h>

class KonkretniProizvodA1 : public IApstraktniProizvodA
{
    public:
        string ProvjeriProizvod();
};

class KonkretniProizvodA2 : public IApstraktniProizvodA
{
    public:
        string ProvjeriProizvod();
};

#endif // KONKRETNIPROIZVODIA_H
