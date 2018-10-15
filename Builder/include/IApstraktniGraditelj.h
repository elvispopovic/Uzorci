#ifndef IAPSTRAKTNIGRADITELJ_H
#define IAPSTRAKTNIGRADITELJ_H

#include <iostream>
#include <KonkretniProizvod.h>

class IApstraktniGraditelj
{
    public:
        virtual ~IApstraktniGraditelj() {}
        virtual IApstraktniGraditelj* IzgradiDio_1()=0;
        virtual IApstraktniGraditelj* IzgradiDio_2()=0;
        virtual IApstraktniGraditelj* IzgradiDio_3()=0;
        virtual IApstraktniGraditelj* IzgradiDio_4()=0;
        virtual IApstraktniGraditelj* IzgradiDio_5()=0;
        virtual string dajIme()=0;
        virtual KonkretniProizvod* DajProizvod()=0;
};

#endif // IAPSTRAKTNIGRADITELJ_H
