# Sviluppo: collaborazione team, progettazione e pattern, scelte progettuali

>Introduzione

Il progetto che è stato realizzato è un'applicazione java desktop sviluppata principalmente con le librerie JavaFX e JFoenix.

Per sviluppare tale software è stato usato [Intellij Idea](https://www.jetbrains.com/idea/) come ambiente di sviluppo e [SceneBuilder](https://gluonhq.com/products/scene-builder/) per aiutarci con la progettazione dell'interfaccia grafica.

Il processo di sviluppo è stato coordinato secondo il metodo AGILE e SCRUM.
Il gruppo, in questo caso formato da due persone, si trovava con settimanalmente per meeting di media/breve durante, nei quali lo scopo spaziava dal progettare concettualmente la base di dati e l'app fino ad individuare le criticità del sistema testandolo al momento.
Durante i *daily scrum* l'obbiettivo era anche pianificare non dei "*daily sprint*" ma dei "*weekly sprint*", nel nostro caso, con modifiche, migliorie e le nuove implementazioni del sistema.
Il gruppo, vista la situazione attuale legata al COVID-19, ha optato per *scrum-meeting* sulle piattaforme ZOOM ed AnyDesk.
La teoria alla base del metodo utilizzato è quella del **controllo empirico dei processi**, secondo la quale, da un lato, la conoscenza deriva dall’esperienza e, dall’altro lato, le decisioni si basano su ciò che si conosce. Per questo motivo si prevede un processo iterativo con un approccio incrementale che ottimizza, passo dopo passo (e sprint dopo sprint), la prevedibilità ed il controllo del rischio.
Anche se nel caso di questo progetto è stato talvolta questo ragionamento.
Infatti è stato manifestato dagli sviluppatori anche il volere collettivo di sperimentare nuove piattaforme e librerie per ampliare le proprie conoscenze.

## Collaborazione team di sviluppo
> Git & Github

![N|Solid](https://dpsvdv74uwwos.cloudfront.net/statics/img/drive/4mlufcgifby062lx9zsbpsuebeidold3k1n.jpeg)
Per la condivisione, collaborazione e gestione del versionamento del software abbiamo scelto git, in particolare abbiamo creato una repository ospitata su **Github**
LINK: https://github.com/Mirgiacomo/elaborato_ingegneriaSW

Il repository è stato sviluppato in modo "incrementale" ovvero, in base al weekly scrum e ai '*TODO*' della giornata/settimana, venivano creati dei branch relativi alle attività da svolgere (es. strutturazione interfacce, fix layout forms, etc.)
Per questo motivo sono stati creati vari branch, i quali, ogni fine settimana, venivano mergiati in un branch più generale, ovvero quello che contiene le versioni stabili del prodottodi: *test*.
Inizialmente venivano creati branch per la parte di base di dati, la parte di creazione della view, la parte di controller etc.
Poi una volta preso forma il primo prototipo si è cominciato ad andare a lavorare sulle singole richieste: gestione regioni, gestione comuni, gestione malattie etc.
In modo tale che ogni persona avesse in carico una singola parte dell'applicazione e la portava a termine.
Ogni volta che uno sviluppatore apportava modifiche alla sua branch locale, faceva il push dei cambiamenti sulla repository online in modo che fossero disponibili anche agli altri sviluppatori.
>  GitHub project

E' stato optato l'uso di un tool di project management web-based semplice, il quale ci ha aiutato a gestire gli sprint, le richieste, i bug e i task del progetto.
Come gestore di questi task, invece di Trello o altri tools, è stato scelto GitHub Project in quanto interconnesso alla repo dell'elaborato, il quale ha permesso di aiutare il team di sviluppo con la gestione dei task, ovvero delle user-story.
Lo scopo di GitHub Project è per l'appunto quello di facilitare l'organizzazione del lavoro, la gestione dei task nel tempo e la comunicazione tra sviluppatori.

###### Dashboard fase finale progetto
![N|Solid](https://i.ibb.co/rF1MywC/dashboard-github.png)

Ciclo di vita di un task:
1. To Do: il punto di partenza, dove vengono segnati i task da sviluppare nell'immediato futuro
2. In Progress: segnala che un task è attualmente in fase di sviluppo
3. Refactoring: degli sviluppi/task che sono stati implementati e che hanno bisogno di un refactoring dopo una bozza iniziale.  Usato anche per segnalare bug superficiali da sistemare.
4. Opzionale: usato per abbozzare idee che vengono dagli sviluppatori e/o in fase di briefing. Queste idee vengono implementante graduatamente nella fase finale del progetto, quando si ha un **prototipo completo e funzionante**
5.  Review/Test: quando uno sviluppatore finisce un task lo segnala agli altri sviluppatori che devono fare la "review" del nuovo codice. Se il nuovo codice sviluppato passa la review, allora viene messo in Done, se invece non passa la review si cerca di capire per quali motivi non è andato a buon fine, in modo da identificare eventuali bug.
	Questo step è necessario per evitare di mettere in "produzione" codice con errori o
	incoerenze
6.  Done: il punto di arrivo del task, segnala che il task è stato completato e soprattutto **testato** con successo

> Zoom & AnyDesk

Come accennato precedentemente i weekly scrum sono stati svolti sulla piattaforma online Zoom con incontri a cadenza quotidiana per confrontarci, fare il punto della situazione e decidere a cosa dare priorità nella parte di sviluppo.
Inoltre, un altro strumento utilizzato è stato AnyDesk, per fare una sorta di *pair-programming* che risulta essere molto interessante ed importante, in quanto da la possibilità di aiutare gli sviluppatori a vicenda nella scrittura o spiegazione del codice.

## Documentazione
> StackEdit.io
![N|Solid](https://stackedit.io/res-min/img/logo.svg)

Per quanto riguarda la documentazione, è stata scritta in formato Markdown con l'aiuto di [stackedit.io](https://stackedit.io/app#), un tool esterno per editare Markdown con più facilità.

## Progettazione e pattern usati
> #### Perchè JAVA?
Il team per lo sviluppo di tale elaborato ha deciso di utilizzare come linguaggio di programmazione JAVA in quanto, come detto precedentemente, si aveva già una dimestichezza ma al tempo stesso si voleva studiare ed approfondire nuove piattaforme e/o librerie legate a JAVA.
La seconda idea era quella di sviluppare l'intero progetto mediante linguaggio PHP, appoggiandosi al framework Yii, ma poi, vista la poca coerenza con il corso di Programmazione II, è stata scartata come idea.

> Pattern MVC

La scelta del pattern MVC è stata fortemente condizionata dall'utilizzo del tool SceneBuilder, dato che esso produce una view, ovvero un file FXML il quale viene collegato ad un controller, ovvero una classe java.
Questo mostra come il tool SceneBuilder sia fortemente orientato verso il pattern MVC.
Il team, avendo già avuto modo di studiare precedentemente Java e SeneBuilder, ha deciso di approfondire ulteriormente le proprie conoscenze in Java e lo sviluppo di GUI in Java, per tale motivo ha deciso implementare tale design pattern.

#### TODO: aggiungere altri pattern

> Pattern DAO

Nella realizzazione di questo prototipo è stato implementato anche pattern architetturale DAO con lo scopo di separare le operazioni che permettono l’accesso ai dati di basso livello dalle operazioni di alto livello.
DAO infatti si occupa di dare all'applicazione una serie di metodi per accedere ai dati senza inserire all’interno del nostro codice chiamate dirette ad un DB, favorendo un approccio di tipo MVC.
Ciò implica che se per svariati motivi dovessimo modificare il tipo di memoria persistente utilizzata, non sarà necessario stravolgere il codice della nostra applicazione, basterà bensì modificare il DAO utilizzato.d

#### TODO: aggiungere altri pattern

## Scelte progettuali

> #### Perchè JAVA?
Il team ha deciso di utilizzare come linguaggio di programmazione per lo sviluppo di tale elaborato, JAVA in quanto, come detto precedentemente, si aveva già una dimestichezza ma al tempo stesso si voleva studiare ed usare nuove piattaforme e/o librerie agganciate a JAVA.
La seconda idea era quella di sviluppare l'intero progetto mediante linguaggio PHP, appoggiandosi al framework Yii, ma poi, vista la poca coerenza con il corso di Programmazione II, è stata scartata tale idea.

## Gestione dei dati
> Firebase
![N|Solid](https://felgo.com/doc/images/logo-firebase.png)

Visto l'intento di voler creare un prototipo di applicazione il più dinamico possibile, il team aveva diverse possibilità di salvataggio dati, come ad esempio la serializzazione su file o il salvataggio su database locale.
Dopo varie discussioni si è scelto di utilizzare un sistemi software dove la persistenza dei dati è in generale caratterizzata dal fatto di non utilizzare il modello relazionale, di solito usato dalle basi di dati tradizionali come ad esempio MySQL o PostgreSQL.
La scelta è ricaduta infatti nell'uso di **Firebase**: un **Realtime Database NoSQL** ospitato nel cloud Google che consente di archiviare e sincronizzare i dati tra i tuoi utenti in tempo reale.
Non vi sono tabelle né record, nessuna traccia dell’approccio relazionale. Tutti i dati inseriti in Firebase vanno a costituire un **albero JSON**. Le informazioni al suo interno, infatti, potranno essere strutturate secondo questo formato: si potranno inserire mappe, liste, stringhe, tipi numerici e booleani.

Link alla nostro Dashboard per il [progetto di Firebase](https://console.firebase.google.com/u/0/project/elaborato-ingegneria/firestore/data~2Fusers~2FZ1hRKCsQ5xkWJnRA0HTK)

###### Immagine di come si presenta la struttura dati in Firebase

![N|Solid](https://i.ibb.co/Tc2d8zR/dashboard-firebase.png)

# Organizzazione della GUI
Il prototipo è strutturato in circa 2 sezioni principali:
> ## 1. Login page

![N|Solid](https://i.ibb.co/fqLgPHX/login-app.png)
La prima pagina dell'applicazione è quella di Login: permette l'autenticazione di diversi tipi di utenti (ADMIN, Ricercatori Analisti, Personale dell'ente etc), i quali, una volta loggati, potranno avere accesso alle funzioni che spetta ai loro ruoli.
C'è la possibilità inoltre, di registrare un nuovo utente con un determinato ruolo.

> Per migliorare la sicurezza è stata aggiunta una criptazione della password in fase di registrazione, in modo tale da non avere nessuna password in chiaro su database
> La libreria che è stata usata per fare ciò è: **com.google.guava:guava:18.0**


> ## Dashboard
###### Dashboard iniziale ADMIN
![N|Solid](https://i.ibb.co/1zYjcvP/main-dashboard-app.png)

In base al ruolo dell'utente che effettua il login, verranno caricate dinamicamente lo voci di menu nella dashboard a seconda dei privilegi:

- PERSONALE MONITORAGGIO
	- Inserimento/modifica Regione
	- Inserimento/modifica Provincia
	- Inserimento/modifica Comune
- PERSONALE CONTAGI
	- Inserimento mensile contagi dei comuni di competenza
- PERSONALE DECESSI
	- Inserimento annuale decessi per provincia di competenza
- RICERCATORE ANALISTA
	- Visualizzazione grafici aggregati
	- Export Dati
- ADMIN
	- Visibilità su tutti i moduli dell'applicazione

> ## Provincia/Regioni/Comuni
###### Insert Province/Regioni/Comuni
![N|Solid](https://i.ibb.co/NsXWzsd/province-app.png)
I moduli di province/comuni/regioni permettono di aggiungere una nuova regione, provincia o comune attraverso il pulsante 'inserisci'.
In questo prototipo è stata inserità anche la funzionalità di modifica di queste informazioni attraverso l'apposito pulsante.
Inoltre è stato anche pensato di implementare nella prossima relase la funzionalità di delete di un oggetto.

## Utenti
###### Insert Utente
![N|Solid](https://i.ibb.co/zSWLZHP/registrazione-utente-app.png)
Anche il modulo *utenti* segue la stessa logica di aggiunta/modifica, permettendo inoltre di associare e dissociare i comuni di competenza per ogni personale a contratto.
Nella prossima realease del prototipo è stato già pensato di implementare anche la modifica della password per gli utenti.

## Export
Per i moduli comuni, regioni , province, utenti è stato implementata una funzione di export dati in modo dinamico nei diversi formati: **TXT**, **CSV**, **XLS**.
E' già stato pianificato anche di aggiungere l'export in **JSON** e **XML**.

![N|Solid](https://i.ibb.co/Sv1J4n6/export-app.png)

## Contagi Comuni / Decessi Province
Queste due sezioni, gestite rispettivamente da Personale Contagi e Personale Decessi, permettono di inserire con cadenza settimanel (nel caso dei contagi) o con cadenza annuale (nel caso dei decessi) i dati, e al contempo visualizzarli mediante appositi filtri.

> Le **malattie** e le **complicazioni** della sezione contagi comuni vengono **caricate dinamicamente** da firebase, permettendo una scalabilità dell'applicazione senza necessità modifiche/aggiunte.
> Lo stesso discorso vale anche per i decessi per provincia, dove le cause sono anch'esse caricate dinamicante.

#### TODO: aggiungere quale accenno al caricamento delle tabelle

## Report Decessi

#### TODO: aggiungere questa parte che è da terminare con foto


## Report aggregati

#### TODO: aggiungere questa parte che è da terminare con foto


# Test e validazione
> Introduzione
>
Per verificare la solidità del software prodotto, si sono svolte le seguenti attività:

1.  Ricognizione del documento delle specifiche e confronto con i diagrammi prodotti
2.  Verifica della consistenza tra diagrammi e codice prodotto
3.  Ispezione del codice, verifica della correttezza dei pattern, ricerca di incogruenze  varie
4.  Test degli sviluppatori sul software
5.  Test utente generico sul software

## Ispezione codice e documentazione

In questa fase si è rivisto il documento delle specifiche e lo si è confrontato con i diagrammi UML prodotti, per verificare la correttezza degli use case, activity diagrams e diagramma delle classi.
Una volta finita questa attività si è confrontato il codice (staticamente) ai diagrammi UML, per verificarne la consistenza.
Infine si è data una nuova ispezione del codice per cercare infrazioni, incogruenze e cattivi usi dei pattern.

## Test degli sviluppatori

In questa fase gli sviluppatori hanno immesso nel sistema degli input (sia corretti sia errati) per vedere se la reazione del software fosse quella attesa. I principali test svolti sono:

- **Verifica del corretto funzionamento dell’autenticazione**: i dati errati vengono respinti, ed a fronte dei dati corretti all’utente viene mostrata la schermata iniziale legata alla tipologia di utenza, caricando dinamicamente le corrette informazioni visibili in base al ruolo utente.

- **Verifica del corretto funzionamento della registrazione/modifica di una regione**: se i campi non sono completati nel modo corretto viene segnalato un errore tramite un alert dialog. Controllo anche per la corretta immissione della superficie.

- **Verifica del corretto funzionamento della registrazione/modifica di una provincia**: se i campi non sono completati nel modo corretto viene segnalato un errore tramite un alert dialog. Controllo anche per la corretta immissione della superficie e la corretta selezione della regione di appartenenza.

- **Verifica del corretto funzionamento della registrazione/modifica di un comune**: se i campi non sono completati nel modo corretto viene segnalato un errore tramite un alert dialog. Controllo anche per la corretta immissione della superficie e la corretta selezione della regione di appartenenza.
  Aggiunto un ulteriore controllo per l'immissione di un corretto *codice ISTAT* per il comune attraverso regex:
	-	``Pattern.compile("^[0-9]{6}$", Pattern.CASE_INSENSITIVE); ``

- **Verifica del corretto funzionamento della registrazione/modifica di un utente**: se i campi non sono completati nel modo corretto viene segnalato un errore tramite un alert dialog. Controllo anche per la corretta selezione del ruolo di appartenenza.
  Aggiunto un ulteriore controllo per l'immissione di un corretto *codice fiscale* per il comune attraverso regex:
	-	``Pattern.compile("^[a-zA-Z]{6}[0-9]{2}[abcdehlmprstABCDEHLMPRST][0-9]{2}([a-zA-Z][0-9]{3})[a-zA-Z]$", Pattern.CASE_INSENSITIVE); ``

Per ogni sezione sono stati fatti numerosi test inserndo dati non omogenei, lasciando campi vuoti, di tipologia errata, non congrui per verificare il comportamento dell'applicazione e il sollevamento di eventuali errori o eccezioni.
Verificate eventuali risposte relative all'errore creato.

## Test esterno

Come ultimo test abbiamo deciso di far usare il prototipo a due persone esterne dallo sviluppo in modo tale da trovare eventuali "punti deboli" dell'applicazione e capire meglio quali sono eventuali aspetti desiderati degli utenti comuni in fase di uso, che il team di sviluppo non aveva ancora pensato.

In questa fase non si è cercato in nessun modo di guidare o strutturare l’esperienza, per non influenzare in alcun modo il risultato; piuttosto si è lasciato che il soggetto navigasse liberamente il sistema.

Non è stata data nessuna spiegazione sull’utilizzo del software, se non una generale indicazione dei suoi fini; ci si è limitati a rispondere alle domande, quando sollevate.

L’unico scopo del test era quello di rilevare errori invisibili allo sviluppatore; in realtà, gli utenti a cui è stato mostrato il software hanno anche aiutato ad individuare nuove funzionalità per migliorare l’usabilità generale del sistema.
Forse quest'ultimo test è stato uno dei più utili in quanto è stato talvolta fonte di idee e di discussione tra il team, dando un'idea anche di quali parti siano *user-friendly* e quali no.
