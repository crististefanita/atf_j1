# Documentația ATF J1

Acest index descrie reactorul J1 cu șase module și cele cinci biblioteci versionate pe care le consumă. Începe cu [Arhitectura](ATF_Architecture.md) pentru modulele locale și scope-ul dependențelor, apoi consultă ghidul relevant pentru detaliile de implementare.

## Pe scurt

| Nevoie | Începe aici |
|---|---|
| Înțelegerea modulelor locale și a bibliotecilor selectate | [Arhitectura](ATF_Architecture.md), [catalogul bibliotecilor](ATF_Workspace_Synchronization.md#consumed-library-catalog) și POM-ul rădăcină |
| Urmărirea unei cereri sau adăugarea unui scenariu API | [Ghidul testelor API](ATF_Api_Test_Guide.md), [ghidul Core](ATF_Core_Consumer.md) și README-ul modulului consumator |
| Înțelegerea ieșirii UI, API și Extent | [Ghidul consumatorului Reporting](ATF_Reporting_Consumer.md), [contractul Reporting](ATF_Reporting_Contract.md) și [contractul de afișare](ATF_Reporting_Display_Contract.md) |
| Alegerea nivelului de test și a suitei | [Ghidul de testare](ATF_Testing_Guide.md) și [adnotările de test](ATF_Test_Infrastructure_Annotations.md) |
| Utilizarea artefactelor de bibliotecă selectate | [Indexul bibliotecilor](../lib/README.md) și [sincronizarea artefactelor](ATF_Workspace_Synchronization.md) |
| Consultarea unei lacune active sau a criteriului de închidere | [Registrul feedback-urilor](feedback/README.md) și documentul ownerului indicat |
| Actualizarea sigură a documentației | [Bune practici pentru documentație](README_Atf_DocumentationBestPractices.md) și [siguranța build-ului](ATF_Build_And_Execution_Safety.md) |

~~~mermaid
flowchart LR
  POM[POM rădăcină] -->|selectează versiunile| Libraries[Cinci perechi JAR/POM]
  POM -->|declară| Modules[Șase module locale J1]
  Modules -->|dețin scenariile și testele| Evidence[Dovezi locale și rapoarte Extent]
  Governance[atf-tests] -.->|verifică fapte, linkuri și limite| Docs[Documentație în engleză și română]
~~~

POM-ul rădăcină este sursa normativă pentru reactor și versiunile dependențelor. POM-urile modulelor definesc scope-urile dependențelor directe; documentația explică utilizarea acestor dependențe și verificarea comportamentului local.

## Ordine de lectură

| Subiect | Documente |
|---|---|
| Arhitectură și context | [Arhitectură](ATF_Architecture.md) · [Atlas de context](ATF_Context_Atlas.md) · [README rădăcină](../README.md) |
| Contractele bibliotecilor | [Core](ATF_Core_Consumer.md) · [Reporting](ATF_Reporting_Library_Atlas.md) · [Console](../lib/README.Console.md) · [Mock](../lib/README.Mock.md) · [Test Kit](../lib/README.TestKit.md) |
| Consumatori | [Practice Shop](../atf-test-practice-shop/README.md) · [AutoEx](../atf-test-auto-ex/README.md) |
| Dovezi și rapoarte randate | [Contractul dovezilor](ATF_Evidence_Capture_Contract.md) · [Consumatorul Reporting](ATF_Reporting_Consumer.md) · [Contractul Reporting](ATF_Reporting_Contract.md) · [Contractul de afișare](ATF_Reporting_Display_Contract.md) |
| Proiectarea și execuția testelor | [Ghid API](ATF_Api_Test_Guide.md) · [Ghid de testare](ATF_Testing_Guide.md) · [adnotări](ATF_Test_Infrastructure_Annotations.md) · [siguranța build-ului](ATF_Build_And_Execution_Safety.md) |
| Guvernanță și feedback | [ATF Tests](../atf-tests/README.md) · [feedback activ](feedback/README.md) · [controale pentru modificarea testelor](ATF_Test_Changes_Baseline.md) |
| Bune practici și propuneri | [Bune practici](README_Atf_BestPractices.md) · [practici de cod](README_Atf_CodeBestPractices.md) · [practici de documentare](README_Atf_DocumentationBestPractices.md) · [designul evaluării AI](ATF_AI_Evaluation_Design.md) |

## Regula de lucru pentru modificări

~~~text
inspectează codul live și feedback-ul → pregătește cea mai mică modificare în staging → rulează verificările relevante de guvernanță și comportament → inspectează dovezile generate → reconciliază EN/RO → sincronizează selectiv
~~~

Păstrează execuțiile active. Rapoartele generate, cache-urile și fișierele temporare de verificare rămân în afara directoarelor de surse. Procedura detaliată este în [Siguranța build-ului și a execuției](ATF_Build_And_Execution_Safety.md).

## Reguli pentru documentație

- README-urile modulelor descriu responsabilitatea locală, utilizarea publică și verificarea modulului.
- Ghidurile bibliotecilor descriu artefactul selectat și contractul vizibil consumatorilor J1.
- Edițiile în engleză și română păstrează aceeași ordine a secțiunilor, tabele, diagrame și destinații relative ale linkurilor.
- Diagramele arată dependențele POM și ownership-ul actual; actualizează-le când se schimbă codul sau limitele modulelor.
- Capabilitățile stabile apar în documentația ownerului. Feedback-ul înregistrează doar lacune deschise, reproductibile, și dovezile de închidere.
- Nu include note de calitate, nume de rapoarte generate sau starea temporară a unei rulări în README-urile normative.
- POM-urile, codul și testele sunt sursele faptice. Nu documenta un comportament pe care testele selectate nu îl verifică.

## Verificarea raportului randat

Pentru un test de componentă sau UI care generează raport, inspectează HTML-ul rezultat în raport cu aserțiunile și scenariul:

1. Confirmă că apar ierarhia testelor și scenariilor așteptate.
2. Extinde testul și grupurile de aserțiuni relevante; verifică prezența tuturor pașilor și mesajelor așteptate.
3. Compară statusul, durata și dovezile vizibile cu rezultatul testului.
4. Pentru comportamentul vizibil în browser, verifică raportul randat și comenzile lui în browser.
5. Înregistrează coordonata bibliotecii selectate și testul local care stabilește comportamentul.

## Termeni

| Termen | Sens în această documentație |
|---|---|
| Reactor J1 | Cele șase module Maven declarate de POM-ul rădăcină |
| Bibliotecă consumată | O pereche JAR/POM versionată din lib, selectată de POM-ul rădăcină |
| Acceptanță a consumatorului | Dovezi produse de un modul J1 pentru scenariile pe care le rulează efectiv |
| Guvernanță | Teste din atf-tests care verifică arhitectura, documentația și identitatea artefactelor locale |
| Feedback | Registru de lacune deschise și reproductibile, cu criterii de închidere |

## Verificare

Rulează verificările pentru edițiile documentației din rădăcina reactorului:

~~~powershell
node atf-tests/tools/documentation-editions.mjs compare <english-root> <romanian-root>
node atf-tests/tools/documentation-editions.test.mjs
~~~

Suita de guvernanță verifică și catalogul bibliotecilor documentate, precum și relațiile directe din documentație. Consultă [README-ul ATF Tests](../atf-tests/README.md) pentru gate-urile exacte și [baseline-ul modificărilor de teste](ATF_Test_Changes_Baseline.md) pentru controalele testelor protejate.