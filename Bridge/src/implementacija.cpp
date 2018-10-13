#include "implementacija.h"

Implementacija1 :: Implementacija1(string naziv) : IImplementacija(naziv)
{

}

string  Implementacija1 :: aktivnost()
{
    stringstream buffer;
    buffer << "Pokrenuta je aktivnost nad implementacijom \"" << naziv << "\".";
    return buffer.str();
}

Implementacija2 :: Implementacija2(string naziv)  : IImplementacija(naziv)
{

}

string  Implementacija2 :: aktivnost()
{
    stringstream buffer;
    buffer << "Pokrenuta je aktivnost nad implementacijom \"" << naziv << "\".";
    return buffer.str();
}
