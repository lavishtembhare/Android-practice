# Android-Practice

A collection of **~26 independent Android (Java) projects**, each a standalone Gradle app exploring one Android concept or widget. There's no shared code between them — every folder is its own complete Android Studio project with its own `build.gradle`/`build.gradle.kts`, Gradle wrapper, and `AndroidManifest.xml`.

## Numbered practice apps

| Project | What it demonstrates |
|---------|------------------------|
| `App1` | Bare default Android Studio template (Edge-to-Edge, no custom logic) |
| `App2` (`bmicalculator`) | BMI calculator |
| `App3` | Navigating between two activities via `Intent` (`MainActivity` → `SecondActivity`) |
| `App4` | Tic-tac-toe style button grid game logic |
| `App5` | Splash screen that delays via `Handler.postDelayed()` then launches `MainActivity` |
| `App6` | View animations — translate, alpha, rotate, scale (`res/anim/*.xml`) |
| `App7` | `ListView`, `Spinner`, and `AutoCompleteTextView` widgets |
| `App8` | `CardView` styling (elevation, corner radius, compat padding) |
| `App9` | `RecyclerView`-based contact list with a custom `ContactModel` and add/update layout |
| `App10` | `SeekBar` with progress-change listeners and `Toast` feedback |
| `App11` | Custom `Toast` layout |
| `App12` | Custom `Dialog` with its own layout and dismiss button |
| `App13` | Local notifications via `NotificationManager`/`NotificationChannel` |
| `App14` | Launching other apps via `Intent` — dialer, SMS, email, share |
| `App15` | Fragments (`AFragment`, `BFragment`, `CFragment`) swapped into one activity |
| `App16` | Bare default Android Studio template (placeholder/unused) |
| `App17` | `ViewPager` + `TabLayout`-style navigation across Home/Profile/Settings fragments |

## Named practice apps

| Project | What it demonstrates |
|---------|------------------------|
| `AlarmManager` | Scheduling alarms with `AlarmManager` + a `BroadcastReceiver`, with a sound (`alarm.wav`) |
| `Bar-Code-Scanner` | Scanning barcodes/QR codes and emailing or opening the scanned result |
| `Bluetooth-Implemetation` | Discovering and listing nearby Bluetooth devices with a custom adapter |
| `Guess-the-Number` | A simple number-guessing game |
| `Media-Player` | Local audio playback |
| `Shared-Preference` | Persisting simple login state with `SharedPreferences` across two activities |
| `Stopwatch` | A working stopwatch with start/stop/reset |
| `Web-Service` | Registering a user against a remote web service |
| `WebView` | Embedding a web page inside the app via `WebView` |

## Running any individual project

Each folder is a **complete, independent Android Studio project** — open the specific subfolder (not the repo root) in Android Studio:

```bash
git clone https://github.com/lavishtembhare/Android-Practice.git
# then, in Android Studio:
# File → Open → select the specific app folder, e.g. Android-Practice/App9
```

Let Gradle sync, then run on an emulator or device. Most projects target a recent `compileSdk`/`minSdk` (check each project's `app/build.gradle[.kts]` for exact versions) and use Java.

## Known Limitations

- These are individual learning exercises, not a cohesive product — expect varying code quality and no shared architecture between folders.
- Some projects need extra setup to run fully (e.g. `Bar-Code-Scanner` needs camera permission granted at runtime; `Bluetooth-Implemetation` needs Bluetooth permissions and a physical device with Bluetooth hardware; `Web-Service` depends on a reachable backend that isn't included in this repo).
- `App1` and `App16` are essentially untouched default templates with no custom logic.

## License

Licensed under **CC0 1.0 Universal** (public domain dedication) — see [LICENSE](LICENSE).
