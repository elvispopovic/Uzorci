#include <iostream>

#include <apstrakcija.h>
#include <implementacija.h>

using namespace std;

int main()
{
    Apstrakcija *a1, *a2;
    IImplementacija *i1, *i2, *i3, *i4;
    cout << "Pokrenuta je aplikacija koja ilustrira bridge uzorak." << endl;
    i1 = new Implementacija1("Implementacija 1");
    i2 = new Implementacija2("Implementacija 2");
    i3 = new Implementacija1("Implementacija 3");
    i4 = new Implementacija2("Implementacija 4");

    a1 = new Apstrakcija1(i1, i2);
    a2 = new Apstrakcija2(i3, i4);
    cout << a1->PokretanjeImplementacija() << endl;
    cout << a2->PokretanjeImplementacija() << endl;


    delete i1;
    delete i2;
    delete i3;
    delete i4;
    delete a1;
    delete a2;
    return 0;
}
