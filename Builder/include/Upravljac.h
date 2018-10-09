#ifndef UPRAVLJAC_H
#define UPRAVLJAC_H

#include <IApstraktniGraditelj.h>

class Upravljac
{
    public:
        Upravljac(IApstraktniGraditelj* graditelj, string& poruka);
        void PromijeniGraditelja(IApstraktniGraditelj* graditelj, string& poruka);
        KonkretniProizvod* DajProizvod();
        void SagradiProizvod();

    private:
        stringstream ss;
        IApstraktniGraditelj *graditelj;
};

#endif // UPRAVLJAC_H
