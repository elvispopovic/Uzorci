#include <iostream>
#include <KonkretniGraditelji.h>
#include <Upravljac.h>

using namespace std;

int main()
{
    string poruka;
    IApstraktniGraditelj *graditeljA, *graditeljB;
    Upravljac *upravljacA, *upravljacB;
    KonkretniProizvod *proizvod;

    cout << "Pokrenuta je aplikacija koja prikazuje uzorak dizajna \"Builder\"" << endl;
    graditeljA = new KonkretniGraditeljA(poruka);
    cout << poruka << endl;
    graditeljB = new KonkretniGraditeljB(poruka);
    cout << poruka << endl;

    upravljacA = new Upravljac(graditeljA, poruka);
    cout << poruka << endl;
    upravljacA->SagradiProizvod();
    proizvod = upravljacA->DajProizvod();
    cout << "Sagradjen je proizvod " << proizvod->DajOpis() << endl;
    delete proizvod;

    upravljacB = new Upravljac(graditeljB, poruka);
    cout << poruka << endl;
    upravljacB->SagradiProizvod();
    proizvod = upravljacB->DajProizvod();
    cout << "Sagradjen je proizvod " << proizvod->DajOpis() << endl;
    delete proizvod;

    delete upravljacA;
    delete upravljacB;
    delete graditeljA;
    delete graditeljB;
    cout << "Unisteni su graditelji i upravljac." << endl;

    return 0;
}
