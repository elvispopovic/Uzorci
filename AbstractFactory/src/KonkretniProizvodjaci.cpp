#include <KonkretniProizvodjaci.h>
#include <KonkretniProizvodiA.h>
#include <KonkretniProizvodiB.h>
#include <KonkretniProizvodiC.h>

KonkretniProizvodjac1::KonkretniProizvodjac1(string& poruka)
{
    poruka = "Kreiran je proizvodjac broj 1.";
}
IApstraktniProizvodA* KonkretniProizvodjac1::KreirajProizvodA()
{
    return new KonkretniProizvodA1();
}

IApstraktniProizvodB* KonkretniProizvodjac1::KreirajProizvodB()
{
    return new KonkretniProizvodB1();
}

IApstraktniProizvodC* KonkretniProizvodjac1::KreirajProizvodC()
{
    return new KonkretniProizvodC1();
}


KonkretniProizvodjac2::KonkretniProizvodjac2(string& poruka)
{
    poruka = "Kreiran je proizvodjac broj 2.";
}
IApstraktniProizvodA* KonkretniProizvodjac2::KreirajProizvodA()
{
    return new KonkretniProizvodA2();
}

IApstraktniProizvodB* KonkretniProizvodjac2::KreirajProizvodB()
{
    return new KonkretniProizvodB2();
}

IApstraktniProizvodC* KonkretniProizvodjac2::KreirajProizvodC()
{
    return new KonkretniProizvodC2();
}



