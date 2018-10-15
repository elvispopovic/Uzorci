#include "Upravljac.h"

Upravljac::Upravljac(IApstraktniGraditelj* graditelj, string& poruka)
{
    ss << "Pokrenut je upravljac sa graditeljem \"" << graditelj->dajIme() << "\".";
    poruka = ss.str();
    this->graditelj = graditelj;
}

KonkretniProizvod* Upravljac::DajProizvod()
{
    return graditelj->DajProizvod();
}

void Upravljac::SagradiProizvod()
{
    graditelj->IzgradiDio_1()
            ->IzgradiDio_2()
            ->IzgradiDio_3()
            ->IzgradiDio_4()
            ->IzgradiDio_5();
}


