# Task Manager
The app for managing tasks and notes for Android written in Kotlin

## Features
- Login, register and authenticate user
- Create tasks and notes 
- Set deadline for tasks
- Convert tasks to notes and vice versa
- View tasks in calendar
- Offline mode (if user is logged in)
- Synchronize tasks and notes with backend when user is back online

## Used design patterns
- Clean Architecture
- Event Bus, Repository
- MVVM/MVI

## Used technologies & libraries
- Coroutines & Flows
- Jetpack Compose
- Navigation Compose
- Encrypted Shared Preferences
- Room DB
- Retrofit
- Dagger & Hilt
- WorkManager

## About project structure
- The MainViewModel causes high coupling between all feature modules, I suppose. MainViewModel uses classes from both records and auth which makes core dependant on these modules.
I tried to place some classes into core module, reducing coupling a bit, but I doubt that's the best solution.


