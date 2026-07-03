# ALICE - Tu Tutora y Asistente de IA Personal

ALICE es una app Android nativa creada con Kotlin, Jetpack Compose, Material 3 y Navigation Compose. La app funciona como tutora y asistente personal para estudiar, organizar tareas, generar flashcards, resumir texto y crear planes de estudio usando Gemini API.

## Tecnologías

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- ViewModel
- Coroutines
- Retrofit
- Gson Converter
- OkHttp
- Gemini API

## Estructura principal

- `MainActivity.kt`: entrada limpia de la app.
- `navigation/`: rutas y navegación principal.
- `data/models/`: modelos de chat, tareas, flashcards, documentos y estados de IA.
- `data/remote/`: cliente REST de Gemini.
- `data/repository/`: repositorio central de funciones de ALICE.
- `data/sample/`: datos simulados para modo presentación.
- `viewmodel/`: estado y lógica de cada pantalla.
- `ui/components/`: componentes reutilizables.
- `ui/screens/`: pantallas de la app.
- `ui/theme/`: paleta oscura y Material Theme.

## Configurar Gemini API Key

La API Key no está escrita en pantallas, ViewModels ni servicios. Debe ir en `local.properties`:

```properties
GEMINI_API_KEY=PEGAR_AQUI_MI_API_KEY
```

El módulo `app` lee esa propiedad y la expone como:

```kotlin
BuildConfig.GEMINI_API_KEY
```

`local.properties` está incluido en `.gitignore`, por lo que no debe subirse a GitHub.

## Ejecutar en Android Studio

1. Abra la carpeta `C:\Users\Usuario\Desktop\aron` en Android Studio.
2. Espere a que Gradle sincronice el proyecto.
3. Pegue su clave real en `local.properties`.
4. Ejecute la app en un emulador o celular Android.
5. Pruebe el chat, flashcards, planes de estudio, resumen de documentos y consejo de tareas.

## Ejecutar por consola

En PowerShell:

```powershell
.\gradlew.bat :app:assembleDebug
```

El APK debug se genera en:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## Funciones con Gemini

- Chat con personalidad de ALICE.
- Generación de flashcards educativas.
- Plan de estudio por tema y duración.
- Resumen de texto/documentos pegados.
- Recomendaciones para organizar tareas.

## Explicación breve para exposición

La app está construida de forma nativa en Android Studio con Kotlin y Jetpack Compose. La interfaz está separada en pantallas y componentes reutilizables. La lógica de cada pantalla vive en ViewModels, mientras que la conexión con Gemini está aislada en `remote` y `repository`. La API Key se maneja desde `local.properties` y se usa mediante `BuildConfig.GEMINI_API_KEY`, sin exponerla en la interfaz ni en logs.

ALICE puede responder preguntas, generar flashcards, resumir texto, crear planes de estudio y ayudar a organizar tareas con recomendaciones claras en español.
