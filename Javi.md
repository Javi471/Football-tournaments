# Javi.md — Diario di sviluppo

Registro dei passi effettuati durante lo sviluppo del progetto **Football Tournaments**.

---

## ✅ Step 1 — Setup iniziale del progetto
**Data:** 25/05/2026

- Creato il repository GitHub: https://github.com/Javi471/Football-tournaments.git
- Collegato il progetto locale al repository remoto con `git remote add origin`
- Letto il PDF del progetto assegnato dal docente (pagine 1–6)
- Analizzata la struttura del progetto Arte-main come riferimento per lo skeleton

---

## ✅ Step 2 — Struttura del progetto e configurazione
**Data:** 25/05/2026

- Creato `pom.xml` con dipendenze Spring Boot 3.5, JPA, Security, Thymeleaf, PostgreSQL, Lombok, Dotenv
- Configurato `application.properties` con parametri DB via variabili d'ambiente (`.env`)
- Creato `docker-compose.yml` per il database PostgreSQL 15
- Aggiunto `.gitignore` appropriato per Java/Maven/Node

---

## ✅ Step 3 — Modello dati (entità JPA)
**Data:** 25/05/2026

Entità create nel package `model/`:
- `Torneo` — nome, anno, descrizione → relazione many-to-many con Squadra
- `Squadra` — nome, annoFondazione, città → relazione one-to-many con Giocatore
- `Giocatore` — nome, cognome, dataNascita, ruolo, altezza → belongs to Squadra
- `Partita` — dataOra, luogo, goalsHome, goalsAway, stato (SCHEDULED/PLAYED/CANCELLED)
- `Arbitro` — nome, cognome, codiceArbitrale
- `Commento` — testo, dataCreazione → collegato a User e Partita
- `User` — username, password (BCrypt), role (USER/ADMIN)

---

## ✅ Step 4 — Repository (Persistence Layer)
**Data:** 25/05/2026

Repository creati con Spring Data JPA:
- `TorneoRepository` — query custom con `JOIN FETCH` per squadre e partite
- `SquadraRepository` — `findByIdWithGiocatori` (JOIN FETCH anti-N+1)
- `PartitaRepository` — query multiple: LAZY, JOIN FETCH, EntityGraph (per analisi prestazioni)
- `GiocatoreRepository`, `ArbitroRepository`, `CommentoRepository`, `UserRepository`

---

## ✅ Step 5 — Service Layer (Business Logic)
**Data:** 25/05/2026

Service creati con annotazioni `@Transactional`:
- Operazioni di sola lettura → `@Transactional(readOnly = true)`
- Operazioni di scrittura → `@Transactional`
- `PartitaService` include calcolo classifica (punti, vittorie, pareggi, sconfitte, differenza reti)
- `UserService` implementa `UserDetailsService` per Spring Security

---

## ✅ Step 6 — Controller Layer
**Data:** 25/05/2026

Controller creati:
- `AuthController` — login, registrazione
- `TorneoController` — CRUD tornei (pubblico + admin), integrazione classifica
- `SquadraController` — CRUD squadre (pubblico + admin)
- `GiocatoreController` — CRUD giocatori (solo admin)
- `PartitaController` — registrazione partita, inserimento risultato, dettaglio con commenti
- `ArbitroController` — CRUD arbitri (solo admin)
- `CommentoController` — aggiunta e modifica commenti (utenti registrati)
- `ClassificaRestController` — API REST `/api/tornei/{id}/classifica` per React

---

## ✅ Step 7 — Sicurezza
**Data:** 25/05/2026

- `SecurityConfig` con regole per ruoli USER e ADMIN
- `PasswordConfig` con BCryptPasswordEncoder
- Endpoint pubblici: `/tornei/**`, `/squadre/**`, `/partite/**` (GET), `/api/tornei/**`
- Endpoint protetti: `/commenti/**` (autenticati), `/admin/**`, `/giocatori/**`, `/arbitri/**` (ADMIN)
- CSRF abilitato per Thymeleaf, ignorato per `/api/**`

---

## ✅ Step 8 — Template Thymeleaf (Frontend)
**Data:** 25/05/2026

Template creati:
- `fragments/layout.html` — navbar comune con Thymeleaf Security
- `auth/login.html`, `auth/register.html`
- `torneo/lista.html`, `torneo/dettaglio.html`, `torneo/form.html`, `torneo/classifica.html`
- `squadra/lista.html`, `squadra/dettaglio.html`, `squadra/form.html`
- `giocatore/form.html`
- `partita/dettaglio.html` (con sezione commenti), `partita/form.html`, `partita/risultato.html`
- `arbitro/lista.html`, `arbitro/form.html`
- `commento/form.html`

---

## ✅ Step 9 — Frontend React (Classifica)
**Data:** 25/05/2026

- Creato componente `frontend/src/classifica.jsx`
- La classifica viene caricata dinamicamente tramite fetch su `/api/tornei/{id}/classifica`
- Tabella con: posizione (medaglie), squadra, PG, V, P, S, GF, GS, DR, Punti
- Build con Vite → output in `src/main/resources/static/react/classifica.js`
- Integrazione: il template Thymeleaf monta `#classifica-root` con `data-torneo-id`

---

## ✅ Step 10 — Dati iniziali e GitHub
**Data:** 25/05/2026

- `DataInitializer` carica al primo avvio: utente admin, utente user1, 1 torneo, 4 squadre, 4 giocatori, 1 arbitro, 2 partite (1 giocata, 1 programmata)
- Creato `README.md` con documentazione completa
- Push iniziale su GitHub: https://github.com/Javi471/Football-tournaments

---

## ✅ Step 11 — Design System (Claude Design · Dark/Teal Theme)
**Data:** 26/05/2026

Implementato il design system estratto dal file Claude Design (Landing.html):

- **Palette**: dark ink (`#161A1F`), teal accent (`#2BB7A8`), paper (`#F4F6F7`)
- **Font**: Bebas Neue (titoli display) + Manrope (corpo testo)
- **`style.css`**: riscrittura completa con variabili CSS, tutti i componenti:
  navbar sticky blur, card-grid, table-wrap, badge colorati (teal/green/red/gray),
  form-card, checkbox-grid, alert, section, tag-list, match-hero, comment-card,
  detail-hero, hero-stripe, score-grid, empty-state, footer
- **`index.html`**: landing page con footballer SVG animato (10 keyframes),
  strisce diagonali teal, CTA auth-aware con Thymeleaf Security
- **`layout.html`**: navbar con logo SVG mark, pill admin-link, btn Registrati
- **Tutti i template** riprogettati con il nuovo sistema:
  - List pages → hero-stripe + card-grid con accent-bar
  - Detail pages → detail-hero con breadcrumb + stripe SVG + table-wrap
  - Form pages → form-card centrato con breadcrumb, form-row, error-msg
  - Match detail → match-hero con score display / VS, comment-card list
  - Score entry → score-grid con score-input grande
- **`HomeController.java`**: serve `/` → template `index`
- **`SecurityConfig`**: aggiunto permit per `/` e `/index`

---

## 🔲 TODO — Da completare

- [ ] Analisi sperimentale prestazioni JPA (confronto LAZY vs JOIN FETCH vs EntityGraph)
- [ ] Aggiungere più dati di test (più tornei, squadre, partite)
- [ ] (Bonus) Paginazione sulla lista tornei e squadre
- [ ] (Bonus) Upload immagine per squadra/giocatore
- [ ] (Bonus) Filtri di ricerca per giocatori e partite
- [ ] Test unitari per i service
- [ ] Verifica finale e consegna via email a siw.roma3@gmail.com
