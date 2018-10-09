#ifndef IAPSTRAKTNIGRADITELJ_H
#define IAPSTRAKTNIGRADITELJ_H

#include <iostream>
#include <KonkretniProizvod.h>

class IApstraktniGraditelj
{
    public:
        virtual ~IApstraktniGraditelj() {}
        virtual void IzgradiDio_1()=0;
        virtual void IzgradiDio_2()=0;
        virtual void IzgradiDio_3()=0;
        virtual void IzgradiDio_4()=0;
        virtual void IzgradiDio_5()=0;
        virtual string dajIme()=0;
        virtual KonkretniProizvod* DajProizvod()=0;
};

#endif // IAPSTRAKTNIGRADITELJ_H
