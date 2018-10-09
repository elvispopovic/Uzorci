#ifndef KONKRETNIGRADITELJI_H
#define KONKRETNIGRADITELJI_H

#include <IApstraktniGraditelj.h>
#include <KonkretniProizvod.h>


class KonkretniGraditeljA : public IApstraktniGraditelj
{
    public:
        KonkretniGraditeljA(string &poruka);
        void IzgradiDio_1();
        void IzgradiDio_2();
        void IzgradiDio_3();
        void IzgradiDio_4();
        void IzgradiDio_5();
        string dajIme();
        KonkretniProizvod* DajProizvod();
    private:
        KonkretniProizvod *proizvod;
};

class KonkretniGraditeljB : public IApstraktniGraditelj
{
    public:
        KonkretniGraditeljB(string &poruka);
        void IzgradiDio_1();
        void IzgradiDio_2();
        void IzgradiDio_3();
        void IzgradiDio_4();
        void IzgradiDio_5();
        string dajIme();
        KonkretniProizvod* DajProizvod();
    private:
        KonkretniProizvod *proizvod;
};

#endif // KONKRETNIGRADITELJI_H
