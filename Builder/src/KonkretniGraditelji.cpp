#include "konkretnigraditelji.h"

KonkretniGraditeljA::KonkretniGraditeljA(string& poruka)
{
    poruka = "Pokrenut je graditelj A.";
    proizvod = new KonkretniProizvod("Proizvod graditelja A");
}

void KonkretniGraditeljA::IzgradiDio_1()
{
    proizvod->PostaviDio_1("Dio 1 vrste A");
}
void KonkretniGraditeljA::IzgradiDio_2()
{
    proizvod->PostaviDio_2("Dio 2 vrste A");
}
void KonkretniGraditeljA::IzgradiDio_3()
{
    proizvod->PostaviDio_3("Dio 3 vrste A");
}
void KonkretniGraditeljA::IzgradiDio_4()
{
    proizvod->PostaviDio_4("Dio 4 vrste A");
}
void KonkretniGraditeljA::IzgradiDio_5()
{
    proizvod->PostaviDio_5("Dio 5 vrste A");
}
string KonkretniGraditeljA::dajIme()
{
    return "graditelj A";
}
KonkretniProizvod* KonkretniGraditeljA::DajProizvod()
{
    return proizvod;
}


KonkretniGraditeljB::KonkretniGraditeljB(string& poruka)
{
    poruka = "Pokrenut je graditelj B.";
    proizvod = new KonkretniProizvod("Proizvod graditelja B");
}

void KonkretniGraditeljB::IzgradiDio_1()
{
    proizvod->PostaviDio_1("Dio 1 vrste B");
}
void KonkretniGraditeljB::IzgradiDio_2()
{
    proizvod->PostaviDio_2("Dio 2 vrste B");
}
void KonkretniGraditeljB::IzgradiDio_3()
{
    proizvod->PostaviDio_3("Dio 3 vrste B");
}
void KonkretniGraditeljB::IzgradiDio_4()
{
    proizvod->PostaviDio_4("Dio 4 vrste B");
}
void KonkretniGraditeljB::IzgradiDio_5()
{
    proizvod->PostaviDio_5("Dio 5 vrste B");
}
string KonkretniGraditeljB::dajIme()
{
    return "graditelj B";
}
KonkretniProizvod* KonkretniGraditeljB::DajProizvod()
{
    return proizvod;
}

