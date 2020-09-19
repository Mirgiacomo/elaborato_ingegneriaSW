## Sviluppo: collaborazione team, progettazione e pattern, scelte progettuali

>Introduzione

L'applicazione che abbiamo sviluppato è un'applicazione java desktop sviluppata tramite la libreria JavaFX e JFoenix.

Per sviluppare il software abbiamo usato [Intellij Idea](https://www.jetbrains.com/idea/) come ambiente di sviluppo e [SceneBuilder](https://gluonhq.com/products/scene-builder/) per aiutarci con lo sviluppo dell'interfaccia grafica.

Il processo di sviluppo è stato organizzato secondo il metodo AGILE e SCRUM.
Il gruppo, in questo caso formato da due persone, si trovava quotidianamente per meeting di media/breve durante, nei quali lo scopo principale era quello di individuare le criticità del sistema testandolo al momento.
Inoltre durante questi *daily scrum* il secondo obiettivo era quello di pianificare il "*daily sprint*" con le modifiche e le nuove implementazioni al sistema.
Il gruppo, vista la situazione attuale legata al COVID-19, ha optato per *scrum-meeting* sulle piattaforme ZOOM ed AnyDesk.
Essendo il progetto stato sviluppato in circa due settimane, si è vista la necessità di organizzarci e discutere anche più di una volta al giorno sull'implementazione e la realizzazione dell'elaborato.
La teoria alla base del metodo utilizzato è quella del **controllo empirico dei processi**, secondo la quale, da un lato, la conoscenza deriva dall’esperienza e, dall’altro lato, le decisioni si basano su ciò che si conosce. Per questo motivo si prevede un processo iterativo con un approccio incrementale che ottimizza, passo dopo passo (e sprint dopo sprint), la prevedibilità ed il controllo del rischio.
Anche se nel caso di questo progetto è stato talvolta questo ragionamento.
Infatti è stato manifestato dagli sviluppatori anche il volere collettivo di sperimentare nuove piattaforme e librerie per ampliare le proprie conoscenze.

### Collaborazione team di sviluppo
> Git & Github

[![N|Solid](https://dpsvdv74uwwos.cloudfront.net/statics/img/drive/4mlufcgifby062lx9zsbpsuebeidold3k1n.jpeg)](https://nodesource.com/products/nsolid)
Per la condivisione, collaborazione e gestione del versionamento del software abbiamo scelto git,
in particolare abbiamo creato una repository ospitata su **Github** 
LINK: https://github.com/Mirgiacomo/elaborato_ingegneriaSW

Il repository è stato sviluppato in modo "incrementale" ovvero, in base al daily scrum e ai '*TODO*' della giornata, venivano creati dei branch relativi alle attività da svolgere (es. strutturazione interfacce, fix layout forms, etc.)
Per questo motivo sono stati creati vari branch, i quali, ogni fine giornata, venivano mergiati in un branch più generale, ovvero quello che contiene le versioni stabili del prodottodi: *test*.
Ogni volta che uno sviluppatore apportava modifiche alla sua branch locale, faceva il push dei
cambiamenti sulla repository online in modo che fossero disponibili anche agli altri sviluppatori.
>  GitHub project

E' stato optato l'uso di un tool di project management web-based semplice, il quale ci ha aiutato a gestire gli sprint, le richieste, i bug e i task del progetto.
Come gestore di questi task, invece di Trello o altri tools, è stato scelto GitHub Project in quanto interconnesso alla repo dell'elaborato, il quale ha permesso di aiutare il team di sviluppo con la gestione dei task, ovvero delle user-story.
Lo scopo di GitHub Project è per l'appunto quello di facilitare l'organizzazione del lavoro, la gestione dei task nel tempo e la comunicazione tra sviluppatori.

# TODO: inserire foto dashboard github

Ciclo di vita di un task:
1. To Do: il punto di partenza, dove vengono segnati i task da sviluppare nell'immediato futuro
2. In Progress: segnala che un task è attualmente in fase di sviluppo
3. Review/Test: quando uno sviluppatore finisce un task lo segnala agli altri sviluppatori che devono fare la "review" del nuovo codice. Se il nuovo codice sviluppato passa la review, allora viene messo in Done, se invece non passa la review si cerca di capire per quali motivi non è andato a buon fine, in modo da identificare eventuali bug.
Questo step è necessario per evitare di mettere in "produzione" codice con errori o
incoerenze
4. Done: il punto di arrivo del task, segnala che il task è stato completato e soprattutto **testato** con successo

> Zoom & AnyDesk

Come accennato precedentemente i daily scrum sono stati svolti sulla piattaforma online Zoom con incontri a cadenza quotidiana per confrontarci, fare il punto della situazione e decidere a cosa dare priorità nella parte di sviluppo.
Inoltre, un altro strumento utilizzato è stato AnyDesk, per fare una sorta di *pair-programming* che risulta essere molto interessante ed importante, in quanto da la possibilità di aiutare gli sviluppatori a vicenda nella scrittura o spiegazione del codice.

### Documentazione
Per quanto riguarda la documentazione, è stata scritta in formato Markdown con l'aiuto di [stackedit.io](https://stackedit.io/app#), un tool esterno per editare Markdown con più facilità. 


### Progettazione e pattern usati
> #### Perchè JAVA?
Il team ha deciso di utilizzare come linguaggio di programmazione per lo sviluppo di tale elaborato, JAVA in quanto, come detto precedentemente, si aveva già una dimestichezza ma al tempo stesso si voleva studiare ed usare nuove piattaforme e/o librerie agganciate a JAVA.
La seconda idea era quella di sviluppare l'intero progetto mediante linguaggio PHP, appoggiandosi al framework Yii, ma poi, vista la poca coerenza con il corso di Programmazione II, è stata scartata tale idea.

> Pattern MVC

La scelta del pattern MVC è stata fortemente condizionata dall'utilizzo del tool SceneBuilder, dato che esso produce una view, ovvero un file FXML il quale viene collegato ad un controller, ovvero una classe java.
Questo mostra come il tool SceneBuilder sia fortemente orientato verso il pattern MVC.
Il team, avendo già avuto modo di studiare precedentemente Java e SeneBuilder, ha deciso di approfondire ulteriormente le proprie conoscenze in Java e lo sviluppo di GUI in Java, per tale motivo ha deciso implementare il pattern MVC.
....

> Pattern DAO

### Scelte progettuali

> #### Perchè JAVA?
Il team ha deciso di utilizzare come linguaggio di programmazione per lo sviluppo di tale elaborato, JAVA in quanto, come detto precedentemente, si aveva già una dimestichezza ma al tempo stesso si voleva studiare ed usare nuove piattaforme e/o librerie agganciate a JAVA.
La seconda idea era quella di sviluppare l'intero progetto mediante linguaggio PHP, appoggiandosi al framework Yii, ma poi, vista la poca coerenza con il corso di Programmazione II, è stata scartata tale idea.


