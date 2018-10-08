#include <iostream>
#include <KonkretniProizvodjaci.h>
#include <IApstraktniProizvodi.h>

using namespace std;

int main()
{
    string poruka;
    cout << "Pokrenuta je aplikacija..." << std::endl;
    IApstraktniProizvodjac *proizvodjac1, *proizvodjac2;
    IApstraktniProizvodA *proizvodA;
    IApstraktniProizvodB *proizvodB;
    IApstraktniProizvodC *proizvodC;

    proizvodjac1 = new KonkretniProizvodjac1(poruka);
    cout << poruka << endl;

    proizvodjac2 = new KonkretniProizvodjac2(poruka);
    cout << poruka << endl;

    proizvodA = proizvodjac1->KreirajProizvodA();
    proizvodB = proizvodjac1->KreirajProizvodB();
    proizvodC = proizvodjac1->KreirajProizvodC();

    cout << "Proizvodjac 1 kreira proizvode A, B i C..." << endl;
    cout << "Test proizvoda A: " << proizvodA->ProvjeriProizvod() << endl;
    cout << "Test proizvoda B: " << proizvodB->ProvjeriProizvod() << endl;
    cout << "Test proizvoda C: " << proizvodC->ProvjeriProizvod() << endl;

    delete proizvodC;
    delete proizvodB;
    delete proizvodA;

    cout << "Proizvodjac 2 kreira proizvode A, B i C..." << endl;
    proizvodA = proizvodjac2->KreirajProizvodA();
    proizvodB = proizvodjac2->KreirajProizvodB();
    proizvodC = proizvodjac2->KreirajProizvodC();

    cout << "Test proizvoda A: " << proizvodA->ProvjeriProizvod() << endl;
    cout << "Test proizvoda B: " << proizvodB->ProvjeriProizvod() << endl;
    cout << "Test proizvoda C: " << proizvodC->ProvjeriProizvod() << endl;

    delete proizvodC;
    delete proizvodB;
    delete proizvodA;

    delete proizvodjac2;
    delete proizvodjac1;
    cout << "Proizvodjaci su unisteni. Aplikacija se zatvara." << endl;
    return 0;
}
