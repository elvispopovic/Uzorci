#include <iostream>
#include <KonkretniGraditelji.h>
#include <Upravljac.h>

using namespace std;

int main()
{
    string poruka;
    IApstraktniGraditelj *graditeljA, *graditeljB;
    Upravljac *upravljac;
    KonkretniProizvod *proizvod;

    cout << "Pokrenuta je aplikacija koja prikazuje uzorak dizajna \"Builder\"" << endl;
    graditeljA = new KonkretniGraditeljA(poruka);
    cout << poruka << endl;
    graditeljB = new KonkretniGraditeljB(poruka);
    cout << poruka << endl;

    upravljac = new Upravljac(graditeljA, poruka);
    cout << poruka << endl;
    upravljac->SagradiProizvod();
    proizvod = upravljac->DajProizvod();
    cout << "Sagradjen je proizvod " << proizvod->DajOpis() << endl;
    delete proizvod;

    upravljac->PromijeniGraditelja(graditeljB, poruka);
    cout << poruka << endl;
    upravljac->SagradiProizvod();
    proizvod = upravljac->DajProizvod();
    cout << "Sagradjen je proizvod " << proizvod->DajOpis() << endl;
    delete proizvod;

    delete upravljac;
    delete graditeljA;
    delete graditeljB;
    cout << "Unisteni su graditelji i upravljac." << endl;

    return 0;
}
