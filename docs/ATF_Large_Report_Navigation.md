# Navigarea rapoartelor mari — dovezi păstrate, lucru incremental

## Contract

Rapoartele HTML mari păstrează toate comparațiile furnizate și fiecare PNG
original inclus în raport. Navigarea se schimbă atunci când sunt pregătite
metadatele de layout și timp, nu lista dovezilor publicate și nici evaluarea
verdictului testului.

| Responsabilitate | Invariantă |
|---|---|
| celule de aserțiuni | preview-ul nativ implicit rămâne 240; verdictele capturate separat și corpurile complete furnizate rămân neschimbate |
| tabele ascunse | nu se creează wrapper-e, măsurători intrinseci sau abonări la redimensionare înainte de afișare; vizualizările cunoscute ca ascunse sar și peste citirile geometriei |
| schimbări de layout | rădăcinile afectate se grupează o singură dată per cadru de animație; descendenții unui strămoș deja programat nu declanșează o nouă scanare a subarborilor |
| dialoguri cu capturi | interfața adăugată fără legătură nu rescanează fiecare tabel din document; octeții PNG originali, descrierile locale și contextul de revenire rămân intacte |
| timp | markerul original de execuție al unui card se rezolvă o dată per trecere de agregare; cache-ul se resetează la trecerea următoare, inclusiv când markerul lipsește |
| vizualizări proiectate | indexul sursă-owner/scenariu se capturează o dată per trecere de proiecție și se partajează între Category, Fails și Overview |
| conținut dinamic | scrierile identice pentru class/open sunt ignorate; mutațiile reale se grupează înaintea invalidării conținutului; schimbările reale de copii/text măresc revizia conținutului; tabelele noi/redeschise/redimensionate primesc alocarea nativă a coloanelor; tabelele eliminate își retrag observarea redimensionării |
| scope-ul rendererului | alocatorul de tabele este partajat de Extent și fallback; agregarea duratelor aparține Extent |

Raportul rămâne autonom. Această schimbare nu mută dovezile într-un alt fișier,
nu elimină ownerii prin virtualizare, nu șterge capturi și nu înlocuiește
rândurile de aserțiuni cu observații.

## Verificare permanentă

| Owner | Dovadă |
|---|---|
| LargeNavigationFixture | patru owneri originali, 600 de grupuri de comparație, 12.004 rânduri de comparație capturate și patru checkpoint-uri PNG sintetice complete |
| LargeReportNavigationBrowserComponentTest | comutări repetate între owneri, căi vizate pentru aserțiuni/PNG și revenire; extindere în masă pe fixture-ul neutru sau pentru cel mult doi owneri externi selectați explicit; inventar SHA-256 per owner pentru celulele/corpurile originale și payload-urile/referințele PNG canonice, înainte/după; se refuză o mutație de un singur caracter a payload-ului |
| EvidenceTableVisibilityComponentTest | scrierile identice de prezentare nu declanșează remăsurare; schimbările reale ale stilului celulei o fac; vizualizările cunoscute ca ascunse sar peste geometrie; dovezile închise rămân structural intacte; deschiderea/redeschiderea și mutația directă a nodului text primesc coloane măsurate complet |
| RecordedPublicationTimingComponentTest | execuția și transportul rămân independente de publicare; markerii eliminați/înlocuiți se actualizează la următoarea agregare |
| familii de browser învecinate | tabele UI/API standard, layout fallback, stare disclosure, etichete de sursă și contracte J16 pentru viewport-ul/return-ul capturilor |

- Proprietatea opțională `atf.navigation.input` folosește implicit căi vizate.
- `atf.navigation.expandOwners` acceptă explicit cel mult doi indici owner
  distincți, numerotați de la zero; indecșii duplicați, negativi/în afara
  intervalului și cererile nelimitate sunt refuzate.
- Fiecare owner selectat se extinde o singură dată, cu același inventar canonic
  de dovezi înainte și după.
- Aceasta verifică publicarea/randarea, nu o execuție business sau pe dispozitiv
  proaspătă.

## Măsurare și limite

- Ceasurile pentru readiness-ul browserului, interacțiunea cu ownerul și
  publicare au scope-uri diferite.
- Trecerea contractului capturilor nu certifică o execuție Android nouă și nici
  oracolul ei business.
- Dovezile obligatorii foarte mari consumă în continuare memorie pentru parser,
  layout și imagini; bugetul de conținut nu garantează o limită pentru întregul
  HTML sau heap-ul browserului.
- Observațiile de performanță nu transformă niciodată un FAIL capturat în PASS.

J1 consumă un binar Reporting versionat și consemnează acceptanță numai pentru
verificările consumerului pe care le rulează. Randarea Android arhivată nu
certifică o rulare nouă pe dispozitiv.

## Pornire la rece și readiness

| Lifecycle | Comportament cerut |
|---|---|
| DOMContentLoaded/load | inițializează o singură dată și activează numai ținta hash solicitată; un hash gol deschide Overview |
| încărcarea nativă Spark | reconciliază cele patru href-uri de navigare după rescrierea lor de către vendor; nu reactivează și nu repornește ceasul readiness |
| readiness | publică numai după normalizarea reușită; un eșec controlat rămâne în starea ne-ready până la recuperarea reală |
| încărcarea paginii | înregistrează separat timpul de navigare încheiat; o încărcare nefinalizată rămâne necunoscută, nu zero |
| conținut dinamic | păstrează observerii, invalidarea reviziei și normalizarea programată pentru mutațiile ulterioare |
| deadline-uri | readiness/acțiune 10.000 ms; navigare 45.000 ms; matricile API existente păstrează 5.000 ms |

- Acceptanța consumerului ar trebui să inspecteze destinațiile inițiale, deep
  link-urile de pornire, repararea linkurilor native, reîncărcările repetate și
  recuperarea după eșec în artefactul Reporting selectat.
- Acest document definește contractul vizibil de navigare; dovada consumerului
  datată rămâne alături de coordonatele pachetului și înregistrarea execuției.
- O rulare externă cu `atf.cold.input` pornește trei procese Chromium noi pe același artifact complet.

## Etichete native pentru timeline

| Limită | Invariantă |
|---|---|
| șablon Spark | codifică cheia owner-ului timeline completă ca șir JSON; păstrează ghilimelele, întreruperile de linie, backslash-urile și Unicode |
| date capturate | păstrează exact eticheta callerului și durata originală; fără tăiere sau verdict dedus |
| upgrade de vendor | eșuează explicit când se schimbă expresia cheii native fixate, în loc să publice în tăcere un script invalid |
| dovadă permanentă | browserul execută scriptul codificat și respinge erorile; regresiile timestamp rămân separate |

J1 înregistrează versiunea Reporting selectată și dovezile propriei acceptanțe.
Oracolele aplicațiilor consumer rămân independente; observațiile datate nu sunt
acceptanță nouă.
