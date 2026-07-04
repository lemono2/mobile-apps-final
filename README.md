# Football App

**Languages / ენა:** [English](#english) · [ქართული](#ქართული)

---

# English

A native Android app for football fans. Browse leagues and teams, play a trivia quiz,
and compare your score with others on an online leaderboard.

Built with **Kotlin** and **Jetpack Compose** on an **MVVM + Repository** architecture.
Data comes from **TheSportsDB REST API** (leagues and teams) and **Firebase Realtime
Database** (quiz questions and leaderboard).

## Features

- **Explore** — browse football leagues, view a league's teams with club badges, and open a team's detail page (stadium, league, description).
- **Quiz** — a 5-question football trivia with a progress bar and instant green/red answer feedback; submit your score at the end.
- **Leaderboard** — all scores from Firebase, sorted highest first, with medals for the top three.

## Tech Stack

| Category | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Repository (`UiState`: Loading / Empty / Error / Success) |
| DI | Hilt |
| Networking | Retrofit + Moshi + OkHttp |
| Backend | Firebase Realtime Database |
| Images | Coil |
| Async | Coroutines + Flow (`StateFlow`) |
| Navigation | Navigation Compose |

**SDK:** `minSdk 24`, `targetSdk 36`.

## Run

1. Open the project in Android Studio and let Gradle sync.
2. Make sure `app/google-services.json` is present (Firebase config).
3. Pick an emulator or device and press **Run ▶**.

## Design

A custom **"Night Stadium"** dark theme — deep charcoal background, vivid green
(`#00C853`) primary and lime (`#B2FF59`) accents, built on Material 3.

---

# ქართული

Android-ის ნატიური აპლიკაცია ფეხბურთის მოყვარულთათვის. დაათვალიერე ლიგები და გუნდები,
ითამაშე ტრივია-ვიქტორინა და შეადარე შენი შედეგი სხვებს ონლაინ ლიდერბორდზე.

დაწერილია **Kotlin**-სა და **Jetpack Compose**-ზე, **MVVM + Repository** არქიტექტურით.
მონაცემები მოდის **TheSportsDB REST API**-დან (ლიგები და გუნდები) და **Firebase Realtime
Database**-იდან (ვიქტორინის კითხვები და ლიდერბორდი).

## ფუნქციონალი

- **Explore** — ფეხბურთის ლიგების დათვალიერება, ლიგის გუნდები ლოგოებით და გუნდის დეტალური გვერდი (სტადიონი, ლიგა, აღწერა).
- **Quiz** — 5-კითხვიანი ვიქტორინა პროგრეს-ბარით და მყისიერი მწვანე/წითელი უკუკავშირით; ბოლოს შედეგის ატვირთვა.
- **Leaderboard** — ყველა შედეგი Firebase-იდან, დალაგებული კლებადობით, პირველი სამის მედლებით.

## ტექნოლოგიები

| კატეგორია | ტექნოლოგია |
|---|---|
| ენა | Kotlin |
| UI | Jetpack Compose + Material 3 |
| არქიტექტურა | MVVM + Repository (`UiState`: Loading / Empty / Error / Success) |
| DI | Hilt |
| ქსელი | Retrofit + Moshi + OkHttp |
| Backend | Firebase Realtime Database |
| სურათები | Coil |
| ასინქრონულობა | Coroutines + Flow (`StateFlow`) |
| ნავიგაცია | Navigation Compose |

**SDK:** `minSdk 24`, `targetSdk 36`.

## გაშვება

1. გახსენი პროექტი Android Studio-ში და დაელოდე Gradle sync-ს.
2. დარწმუნდი, რომ `app/google-services.json` ადგილზეა (Firebase-ის კონფიგურაცია).
3. აირჩიე ემულატორი ან მოწყობილობა და დააჭირე **Run ▶**.

## დიზაინი

custom **"Night Stadium"** მუქი თემა — ღრმა ნახშირისფერი ფონი, კაშკაშა მწვანე
(`#00C853`) მთავარი ფერი და ლაიმისფერი (`#B2FF59`) აქცენტები, Material 3-ზე აგებული.
