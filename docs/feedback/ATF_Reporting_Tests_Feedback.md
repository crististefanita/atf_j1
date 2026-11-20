# Reporting — dovezile consumerilor J1

## Pe scurt

J1 nu are un modul separat cu teste pe sursa Reporting. Testele consumerului
verifică biblioteca selectată prin adaptoare și scenarii locale; verificările de
prezentare inspectează artefactul J1 generat.

## Înregistrează o problemă reproductibilă de raport

Atașează numele testului, coordonata Maven, hash-urile JAR/POM, calea raportului
și arborele așteptat și observat. Precizează ce scenariu, pas sau aserțiune nu se
extinde și dacă problema se reproduce la navigarea cu tastatura.

Folosește [ghidul de consum Reporting](../ATF_Reporting_Consumer.md) pentru
acceptanța locală și [ghidul de utilizare a bibliotecii](../../lib/README.Reporting.md)
pentru contractul API publicat. Păstrează aserțiunile aplicației lângă testul J1
afectat.
