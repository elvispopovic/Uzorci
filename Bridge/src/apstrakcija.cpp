#include "apstrakcija.h"

Apstrakcija1::Apstrakcija1(IImplementacija *i1, IImplementacija *i2) : Apstrakcija(i1,i2)
{
    //ctor
}

Apstrakcija1::~Apstrakcija1()
{
    //dtor
}

string Apstrakcija1 :: PokretanjeImplementacija()
{
    stringstream buffer;
    buffer << "Pokrenute su implementacije za apstrakciju 1: " << endl;
    buffer << implementacija1->aktivnost() << endl;
    buffer << implementacija2->aktivnost();
    return buffer.str();
}

Apstrakcija2::Apstrakcija2(IImplementacija *i1, IImplementacija *i2) : Apstrakcija(i1,i2)
{
    //ctor
}

Apstrakcija2::~Apstrakcija2()
{
    //dtor
}

string Apstrakcija2 :: PokretanjeImplementacija()
{
    stringstream buffer;
    buffer << "Pokrenute su implementacije za apstrakciju 2: " << endl;
    buffer << implementacija1->aktivnost() << endl;
    buffer << implementacija2->aktivnost();
    return buffer.str();
}
