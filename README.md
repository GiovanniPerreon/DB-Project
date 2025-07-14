README - Applicazione SteamDB
==============================

📦 DESCRIZIONE DELL'APPLICAZIONE
---------------------------------
Questa applicazione è un sistema completo per la compravendita digitale di videogiochi, simile a Steam. Permette agli utenti di registrarsi, consultare e acquistare videogiochi, gestire una wishlist, scrivere recensioni, sbloccare achievement e, se autorizzati, pubblicare o moderare contenuti.

L’applicazione è scritta in Java, utilizza MySQL come database, e presenta una GUI realizzata in Java Swing.

👤 TIPOLOGIE DI UTENTI
-----------------------
- **Utente normale**: può consultare giochi, wishlist, collezione, acquistare giochi e recensirli.
- **Publisher**: può inoltre pubblicare nuovi giochi.
- **Developer**: può inoltre modificare i generi dei giochi che ha sviluppato.
- **Amministratore**: può inoltre bloccare/sbloccare utenti e modificare le categorie dei giochi.
- **Utente bloccato**: può solo visualizzare e acquistare giochi (non recensire o pubblicare).

🎮 ACCOUNT DI TEST DISPONIBILI
-------------------------------
Per facilitare il test del comportamento dell'applicazione, sono stati predisposti degli account con diversi privilegi:

| Ruolo       | Email              | Password  | Note                                 |
|-------------|--------------------|-----------|--------------------------------------|
| Utente      | user@db.test       | user      | Utente normale                       |
| Developer   | dev@db.test        | dev       | Ha sviluppato *The Witcher 3* (ID 1) |
| Publisher   | pub@db.test        | pub       | Può pubblicare giochi                |
| Admin       | admin@db.test      | admin     | Può bloccare/sbloccare utenti        |
| Bloccato    | blocked@db.test    | blocked   | Limitazioni su recensioni/pubblicaz. |

🔧 GUIDA ALL'USO
-----------------

1. **Login / Registrazione**
   - All'avvio, si può accedere con uno degli account di test o registrare un nuovo utente.

2. **Navigazione generale**
   - Dopo il login, il menu principale permette di:
     - Vedere la wishlist (View Wishlist)
     - Vedere la collezione (View Collection)
     - Vedere gli achievement ottenuti (View Achievements)
     - Vedere la cronologia delle transazioni (View Transactions)
     - Sfogliare tutti i videogiochi, con possibilità di ordinare e filtrare (Browse Games)
     - Accedere alle funzioni da Admin / Developer / Publisher se abilitati (Publish Game, View All Users)

3. **Wishlist**
   - Aggiungi o rimuovi giochi che non hai ancora acquistato dalla tua lista dei desideri, direttamente dalla pagina del gioco interessato.

4. **Acquisto di giochi**
   - Clicca su un gioco e seleziona "Buy Game" per aggiungerlo alla collezione. Eventuali sconti saranno applicati automaticamente.

5. **Recensioni**
   - Dopo l’acquisto, è possibile recensire il gioco, cliccando su "Add Review", sempre sulla pagina del gioco.

6. **Funzionalità avanzate**
   - **Developer**: modifica i generi dei propri giochi. (Dalla pagina del gioco interessato, nella sezione lingue e generi)
   - **Publisher**: pubblica un nuovo gioco (funzione “Publish Game”).
   - **Admin**: accede alla lista utenti per bloccare/sbloccare account ("View All Users").
   - **Utenti bloccati**: possono solo acquistare e consultare i giochi, ma **non possono** recensire o pubblicare.

📊 FUNZIONALITÀ DI ANALISI
----------------------------
- Visualizzazione dei giochi più nuovi, più venduti, più costosi, ecc.
- Lista dei developer con giochi al di sotto della media di valutazione.
- Accesso agli achievement personali.

🛠️ REQUISITI TECNICI
----------------------
- Java 8 o superiore
---

Per qualsiasi problema di accesso, verifica che il database sia correttamente configurato e che l’utente scelto esista con le credenziali indicate.

