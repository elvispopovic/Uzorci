#include "Upravljac.h"

Upravljac::Upravljac(IApstraktniGraditelj* graditelj, string& poruka)
{
    ss << "Pokrenut je upravljac sa graditeljem \"" << graditelj->dajIme() << "\".";
    poruka = ss.str();
    this->graditelj = graditelj;
}
void Upravljac::PromijeniGraditelja(IApstraktniGraditelj* graditelj, string& poruka)
{
    ss.str(std::string());
    ss << "Promijenjen je graditelj u upravljacu i sada je \"" << graditelj->dajIme() << "\".";
    poruka = ss.str();
    this->graditelj = graditelj;
}

KonkretniProizvod* Upravljac::DajProizvod()
{
    return graditelj->DajProizvod();
}

void Upravljac::SagradiProizvod()
{
    graditelj->IzgradiDio_1();
    graditelj->IzgradiDio_2();
    graditelj->IzgradiDio_3();
    graditelj->IzgradiDio_4();
    graditelj->IzgradiDio_5();
}


