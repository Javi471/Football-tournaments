# ⚽ Football Tournaments

Sistema informativo su Web per la gestione di tornei di calcio amatoriale.  
Progetto assegnato — SIW (Sistemi Informativi su Web) a.a. 2025/2026.

---

## Tecnologie

| Layer | Tecnologia |
|-------|-----------|
| Backend | Spring Boot 3.5, Java 21 |
| Persistenza | JPA / Hibernate + PostgreSQL |
| Frontend (principale) | Thymeleaf |
| Frontend (classifica) | React 18 + Vite |
| Sicurezza | Spring Security |
| Database dev | Docker + PostgreSQL 15 |

---

## Funzionalità

### Pubblico (senza login)
- Lista di tutti i tornei
- Dettaglio torneo (squadre + calendario partite)
- Dettaglio squadra (rosa giocatori)
- Classifica del torneo (componente React, aggiornamento dinamico)

### Utenti registrati (USER)
- Visualizzazione commenti sulle partite
- Inserimento di un commento su una partita
- Modifica del proprio commento

### Amministratore (ADMIN)
- Creazione e modifica di tornei (con selezione squadre)
- Inserimento e modifica di squadre
- Inserimento e modifica di giocatori
- Registrazione di una partita (con torneo, squadre, arbitro, data)
- Inserimento del risultato di una partita
- Eliminazione di squadre e partite
- Gestione arbitri

---

## Entità del dominio

```
Torneo      ←→  Squadra   (many-to-many)
Squadra      →  Giocatore (one-to-many)
Partita      →  Torneo    (many-to-one)
Partita      →  Squadra   (home + away)
Partita      →  Arbitro   (many-to-one)
Commento     →  Partita   (many-to-one)
Commento     →  User      (many-to-one)
```

---

## Avvio del progetto

### 1. Database (Docker)

```bash
docker-compose up -d
```

Crea il database PostgreSQL su `localhost:5432` con:
- DB: `football_db`
- User: `admin`
- Password: `admin123`

### 2. Backend (Spring Boot)

```bash
./mvnw spring-boot:run
```

Oppure su Windows:
```cmd
mvnw.cmd spring-boot:run
```

L'applicazione parte su **http://localhost:8080**

Utenti pre-caricati al primo avvio:
| Username | Password | Ruolo |
|----------|----------|-------|
| admin | admin123 | ADMIN |
| user1 | user123 | USER |

### 3. Frontend React (sviluppo)

```bash
cd frontend
npm install
npm run dev    # → http://localhost:5173
```

Per il build di produzione (genera il bundle in `static/react/`):

```bash
cd frontend
npm run build
```

---

## Struttura del progetto

```
src/main/java/com/football/tournaments/
├── config/          # SecurityConfig, PasswordConfig, DataInitializer
├── model/           # Entità JPA (Torneo, Squadra, Giocatore, Partita, Arbitro, Commento, User)
├── repository/      # Interfacce Spring Data JPA
├── service/         # Logica di business (@Transactional)
└── controller/      # Controller Thymeleaf + REST (classifica)

src/main/resources/
├── templates/       # Template Thymeleaf
│   ├── torneo/      # Lista, dettaglio, form, classifica
│   ├── squadra/     # Lista, dettaglio, form
│   ├── partita/     # Dettaglio, form, risultato
│   ├── giocatore/   # Form
│   ├── arbitro/     # Lista, form
│   ├── commento/    # Form modifica
│   └── auth/        # Login, register
└── static/
    ├── css/         # Stili globali
    └── react/       # Bundle React compilato (classifica)

frontend/
└── src/
    └── classifica.jsx   # Componente React classifica dinamica
```

---

## Analisi delle prestazioni (requisito 8.2)

Nella classe `PartitaRepository` sono definite query con strategie diverse:
- `findByTorneoOrderByDataOraAsc` → **LAZY** (N+1 potenziale)
- `findByTorneoWithTeamsAndReferee` → **JOIN FETCH** (query singola con join)
- `findByIdWithDetails` → **JOIN FETCH** per partita completa

L'analisi sperimentale (da completare nell'orale) confronta i tempi di esecuzione
tra strategia LAZY e JOIN FETCH su un dataset di 50+ partite.

---

## Sicurezza

- Autenticazione form-based con Spring Security
- Password hashate con BCrypt
- Ruoli: `ROLE_USER`, `ROLE_ADMIN`
- Endpoint admin protetti con `@PreAuthorize("hasRole('ADMIN')")`
- CSRF attivo per pagine Thymeleaf, disabilitato per `/api/**`

---

## Consegna

Inviare a `siw.roma3@gmail.com` entro le ore 18:00 del giorno precedente all'orale.  
Oggetto: `[Giugno/Luglio 2026 PROGETTO DOCENTE] Cognome Matricola`
