#ifndef APSTRAKCIJA_H
#define APSTRAKCIJA_H

#include <implementacija.h>

class Apstrakcija
{
    public:
        virtual ~Apstrakcija(){};
        virtual string PokretanjeImplementacija()=0;
    protected:
        IImplementacija *implementacija1, *implementacija2;
        Apstrakcija(IImplementacija *implementacija1, IImplementacija *implementacija2)
        {
            this->implementacija1 = implementacija1;
            this->implementacija2 = implementacija2;
        }
};

class Apstrakcija1 : public Apstrakcija
{
    public:
        Apstrakcija1(IImplementacija *implementacija1, IImplementacija *implementacija2);
        ~Apstrakcija1();
        string PokretanjeImplementacija();
};

class Apstrakcija2 : public Apstrakcija
{
    public:
        Apstrakcija2(IImplementacija *implementacija1, IImplementacija *implementacija2);
        ~Apstrakcija2();
        string PokretanjeImplementacija();
};

#endif // APSTRAKCIJA_H
