#ifndef KONKRETNIGRADITELJI_H
#define KONKRETNIGRADITELJI_H

#include <IApstraktniGraditelj.h>
#include <KonkretniProizvod.h>


class KonkretniGraditeljA : public IApstraktniGraditelj
{
    public:
        KonkretniGraditeljA(string &poruka);
        IApstraktniGraditelj* IzgradiDio_1();
        IApstraktniGraditelj* IzgradiDio_2();
        IApstraktniGraditelj* IzgradiDio_3();
        IApstraktniGraditelj* IzgradiDio_4();
        IApstraktniGraditelj* IzgradiDio_5();
        string dajIme();
        KonkretniProizvod* DajProizvod();
    private:
        KonkretniProizvod *proizvod;
};

class KonkretniGraditeljB : public IApstraktniGraditelj
{
    public:
        KonkretniGraditeljB(string &poruka);
        IApstraktniGraditelj* IzgradiDio_1();
        IApstraktniGraditelj* IzgradiDio_2();
        IApstraktniGraditelj* IzgradiDio_3();
        IApstraktniGraditelj* IzgradiDio_4();
        IApstraktniGraditelj* IzgradiDio_5();
        string dajIme();
        KonkretniProizvod* DajProizvod();
    private:
        KonkretniProizvod *proizvod;
};

#endif // KONKRETNIGRADITELJI_H
