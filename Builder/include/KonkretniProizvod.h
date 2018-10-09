#ifndef KONKRETNIPROIZVOD_H
#define KONKRETNIPROIZVOD_H

#include <IApstraktniProizvod.h>

class KonkretniProizvod : public IApstraktniProizvod
{
    public:
        KonkretniProizvod(string naziv);
        void PostaviDio_1(string vrsta);
        void PostaviDio_2(string vrsta);
        void PostaviDio_3(string vrsta);
        void PostaviDio_4(string vrsta);
        void PostaviDio_5(string vrsta);
        string DajOpis();

    private:
        string naziv;
        string dijeloviVrste[5];
};

#endif // KONKRETNIPROIZVOD_H
