#include "KonkretniProizvod.h"

KonkretniProizvod::KonkretniProizvod(string naziv)
{
    this->naziv = naziv;
}

void KonkretniProizvod::PostaviDio_1(string vrsta)
{
    dijeloviVrste[0] = vrsta;
}
void KonkretniProizvod::PostaviDio_2(string vrsta)
{
    dijeloviVrste[1] = vrsta;
}
void KonkretniProizvod::PostaviDio_3(string vrsta)
{
    dijeloviVrste[2] = vrsta;
}
void KonkretniProizvod::PostaviDio_4(string vrsta)
{
    dijeloviVrste[3] = vrsta;
}
void KonkretniProizvod::PostaviDio_5(string vrsta)
{
    dijeloviVrste[4] = vrsta;
}
string KonkretniProizvod::DajOpis()
{
    std::stringstream buffer;
    buffer << "\"" << naziv << "\" sa dijelovima:" << endl <<
            "Dio 1: " << dijeloviVrste[0] << endl <<
            "Dio 2: " << dijeloviVrste[1] << endl <<
            "Dio 3: " << dijeloviVrste[2] << endl <<
            "Dio 4: " << dijeloviVrste[3] << endl <<
            "Dio 5: " << dijeloviVrste[4] << endl;
    return buffer.str();
}

