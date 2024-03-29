# Filmi soovitamise programm

## Juhised kaivaitamiseks

Alguses tuleb tööle panna Spring Boot'i. 
See käivitab pordil 8080.
```
http://localhost:8080/
```

Kui backend on käivitatud, siis tuleb liikuda terminalis root-folderist
frontend osale:
```
C:\path\to\project\film-recommendation> cd frontend
```

Edasi tuleb terminalis käivitada käsu:

```
film-recommendation\frontend>npm install
```

See käsk laeb alla vajalikud asjad, et projekti saaks käivitada.
Projekti käivitamiseks sisesta käsu:
```
film-recommendation\frontend>npm run serve
```

Siis Vuejs projekt läheb tööle pordil 8081.
```
http://localhost:8081/
```

Siis saab brauseril avada URli:
```
http://localhost:8081/sessions
```
See on projekti avaleht, kus on seansside info

NB! POST meetodite näided asuvad cotrollertite kirjelduses

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).

## Kokkuvõte projektist:
Kokku projekti tegemisele kuulus umbes 30 tundi.
Tegemata jäi osa seotud vaatamiste ajalooga.
Selle probleemi lahendus oleks selline, et oleks vaja luua tabel andbmebaasis, 
mis hoiab vaadatud filme. Ja kui rakendatakse filtrit vaatamise ajaloo kohta,
siis valitakse need filmid, mille žanrid on samasugused.
Kõige rohkem kuulus aega kohtade soovitamise algoritmile, kuid lõpuks 
tuli rakendamise idee ise pähe.