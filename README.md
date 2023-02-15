# Calorie Tracker App
Multi module app

<img src="https://user-images.githubusercontent.com/65896669/219006126-4c478ff4-3853-448d-ad00-8fc06732bcff.gif" width="200" align="right" hspace="10">

[![Kotlin Version](https://img.shields.io/badge/Kotlin-1.7.10-black.svg)](https://kotlinlang.org)
[![AGP](https://img.shields.io/badge/AGP-7.2.1-black?style=flat)](https://developer.android.com/studio/releases/gradle-plugin)
[![Gradle](https://img.shields.io/badge/Gradle-7.3.3-black?style=flat)](https://gradle.org)
[![Gradle](https://img.shields.io/badge/Compose-1.0.2-black.svg?style=flat)](https://developer.android.com/jetpack/compose)

## The project
The purpose of the project is to create multi module app  

and use Kotlin Script (KTS) for gradle.build files.
------

### Custom Rest Api

The custom rest api is create with Ktor and populated with simple data -                                
computer images and information about their processors.                                

* __Build system__ used is `Gradle Kotlin`.
* __Engine__ is `Netty`
* __Pluggins for specific backend bahaviour__

    * __Rounting__ - to define routes for response
    * __Static Content__ - allow application to call a specific road
    * __Content Negotiation__ - for automatic Json parsing of API response
    * __Call Logging__ - log client request to the server
    * __kotlinx.serialization__ - handles JSON serialization

### Showcase Application

The simple showcase Android application is created with JetPack Compose.                                
The whole app is just main screen with `ImageView`, `TextViews` and a `Button`.                                
When the button is clicked, application performed a call to the Custom Ktor Rest Api                                                                
and retreive the data - information about a random computer. Than just populate                                                                
the `ImageView` and the `TextViews` with the data                                

## Project characteristics and tech-stack

This project takes advantage of best practices, many popular libraries and tools 
in the Android ecosystem. Most of the libraries are in the stable version unless 
there is a good reason to use non-stable dependency.

* Tech-stack
    * [100% Kotlin](https://kotlinlang.org/) + [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operations
    * [Retrofit](https://square.github.io/retrofit/) - networking
    * [Jetpack](https://developer.android.com/jetpack)
        * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) - in-app navigation
        * [Kotlin Flows](https://developer.android.com/kotlin/flow) - notify views about database change
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform an action when lifecycle state changes
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way
        * [Room](https://developer.android.com/jetpack/androidx/releases/room) - store offline cache
    * [Hilt](https://dagger.dev/hilt/) - dependency injection
    * [Coil](https://github.com/coil-kt/coil) - image loading library
* Modern Architecture
    * Clean Architecture (at feature module level)
    * Single activity architecture using [NavController](https://developer.android.com/reference/androidx/navigation/NavController)
    * MVVM (presentation layer)
    * [Android Architecture components](https://developer.android.com/topic/libraries/architecture) ([ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), [LiveData](https://developer.android.com/topic/libraries/architecture/livedata), [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation))
    * [Android KTX](https://developer.android.com/kotlin/ktx) - Jetpack Kotlin extensions
* Testing
    * [Unit Tests](https://en.wikipedia.org/wiki/Unit_testing) ([JUnit 5](https://junit.org/junit5/) via
    [android-junit5](https://github.com/mannodermaus/android-junit5))
    * [UT Tests](https://en.wikipedia.org/wiki/Graphical_user_interface_testing) ([Espresso](https://developer.android.com/training/testing/espresso))
    * [Mockk](https://mockk.io/) - mocking framework
* UI
    * [Material design](https://material.io/design)
    * Reactive UI
    * [Jetpack Compose](https://developer.android.com/jetpack/compose?gclid=Cj0KCQiAorKfBhC0ARIsAHDzslu18iedo1CyRG8nhYJqrrmMQfzxL-nVQa9Ilbea70GvnPZTHTRNOssaAorMEALw_wcB&gclsrc=aw.ds)
* Gradle
    * [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
    * Custom tasks
    * Plugins ([SafeArgs](https://developer.android.com/guide/navigation/navigation-pass-data#Safe-args),
    [android-junit5](https://github.com/mannodermaus/android-junit5))
    * [Dependency locks](https://docs.gradle.org/current/userguide/dependency_locking.html)
    * [Versions catalog](https://docs.gradle.org/7.0-milestone-1/userguide/platforms.html)

## Architecture

Feature related code is placed inside one of the feature modules.
We can think about each feature as the reusable component, equivalent of [microservice](https://en.wikipedia.org/wiki/Microservices) or private library.

The modularized code-base approach provides few benefits:
- better [separation of concerns](https://en.wikipedia.org/wiki/Separation_of_concerns). Each module has a clear API., Feature related classes live in different modules and can't be referenced without explicit module dependency.
- features can be developed in parallel eg. by different teams
- each feature can be developed in isolation, independently from other features
- faster compile time

### Module types and module dependencies

This diagram presents dependencies between project modules (Gradle sub-projects).

![module_dependencies](https://github.com/igorwojda/android-showcase/blob/main/misc/image/module_dependencies.png?raw=true)

Note that due usage of Android `dynamic-feature` module dependencies are reversed (feature modules are depending on `app` module, not another way around).

We have three kinds of modules in the application:

- `app` module - this is the main module. It contains code that wires multiple modules together (dependency injection setup, `NavHostActivity`, etc.) and fundamental application configuration (retrofit configuration, required permissions setup, custom application class, etc.).
- application-specific `library_x` modules that some of the features could depend on. This is helpful if you want to share some assets or code only between few feature modules (currently app has no such modules)
- feature modules - the most common type of module containing all code related to a given feature.

### Feature module structure

`Clean architecture` is the "core architecture" of the application, so each `feature module` contains own set of Clean architecture layers:

![module_dependencies_layers](https://github.com/igorwojda/android-showcase/blob/main/misc/image/module_dependencies_layers.png?raw=true)

> Notice that `app` module and `library_x` modules structure differs a bit from feature module structure.

Each feature module contains non-layer components and 3 layers with distinct set of responsibilities.

![feature_structure](https://github.com/igorwojda/android-showcase/blob/main/misc/image/feature_structure.png?raw=true)

#### Presentation layer

This layer is closest to what the user sees on the screen. The `presentation` layer is a mix of `MVVM` (Jetpack `ViewModel` used to preserve data across activity restart) and
`MVI` (`actions` modify the `common state` of the view and then new state is edited to a view via `LiveData` to be rendered).

> `common state` (for each view) approach derives from
> [Unidirectional Data Flow](https://en.wikipedia.org/wiki/Unidirectional_Data_Flow_(computer_science)) and [Redux
> principles](https://redux.js.org/introduction/three-principles).

Components:
- **View (Fragment)** - presents data on the screen and pass user interactions to View Model. Views are hard to test, so they should be as simple as possible.
- **ViewModel** - dispatches (through `LiveData`) state changes to the view and deals with user interactions (these view models are not simply [POJO classes](https://en.wikipedia.org/wiki/Plain_old_Java_object)).
- **ViewState** - common state for a single view
- **NavManager** - singleton that facilitates handling all navigation events inside `NavHostActivity` (instead of separately, inside each view)

#### Domain layer

This is the core layer of the application. Notice that the `domain` layer is independent of any other layers. This allows to make domain models and business logic independent from other layers.
In other words, changes in other layers will have no effect on `domain` layer eg. changing database (`data` layer) or screen UI (`presentation` layer) ideally will not result in any code change withing `domain` layer.

Components:
- **UseCase** - contains business logic
- **DomainModel** - defines the core structure of the data that will be used within the application. This is the source of truth for application data.
- **Repository interface** - required to keep the `domain` layer independent from the `data layer` ([Dependency inversion](https://en.wikipedia.org/wiki/Dependency_inversion_principle)).

#### Data layer

Manages application data and exposes these data sources as repositories to the `domain` layer. Typical responsibilities of this layer would be to retrieve data from the internet and optionally cache this data locally.

Components:
- **Repository** is exposing data to the `domain` layer. Depending on application structure and quality of the external APIs repository can also merge, filter, and transform the data. The intention of
these operations is to create high-quality data source for the `domain` layer, not to perform any business logic (`domain` layer `use case` responsibility).

- **Mapper** - maps `data model` to `domain model` (to keep `domain` layer independent from the `data` layer).
- **RetrofitService** - defines a set of API endpoints.
- **DataModel** - defines the structure of the data retrieved from the network and contains annotations, so Retrofit (Moshi) understands how to parse this network data (XML, JSON, Binary...) this data into objects.

### Data flow

Below diagram presents application data flow when a user interacts with `album list screen`:

![app_data_flow](https://github.com/igorwojda/android-showcase/blob/main/misc/image/app_data_flow.png?raw=true)

## Dependency management

This project utilizes multiple mechanics to easily share the same versions of dependencies.
### App library dependencies

External dependencies (libraries) are defined using [versions catalog](https://docs.gradle.org/7.0-milestone-1/userguide/platforms.html) feature in the [settings.gradle](./settings.gradle) file. These dynamic library versions are locked using Gradle [locking dependency](https://docs.gradle.org/current/userguide/dependency_locking.html) mechanism - concrete dependency versions are stored in `MODULE_NAME/gradle.lockfile` files.

To update lock files run `./gradlew test lint s --write-locks` command and commit updated `gradle.lockfile` files to
repository.

Each feature module depends on the `app` module, so dependencies are shared without need to add them explicitly in each feature module.

### Gradle plugin dependencies

Gradle plugins are defined in [pluginManagement](https://docs.gradle.org/current/userguide/plugins.html#sec:plugin_management) block ([settings.gradle](./settings.gradle) file).

Dynamic versions aren't supported for Gradle plugins, so [locking dependency](https://docs.gradle.org/current/userguide/dependency_locking.html) mechanism can't be used (like for app library dependencies), and thus versions of some libraries & plugins have to be hardcoded in the [gradle.properties](./gradle.properties) file.

There is no easy way to share id between `pluginManagement` block and `buildSrc` folder, so plugin ids (also used within build scripts), have to be duplicated in the [GradlePluginId](./buildSrc/java/GradlePluginId/kt) file.

### Shared dependencies

Gradle is missing proper built-in mechanism to share dependency versions between app library dependency and Gradle plugin dependency eg. [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started) library uses [Safe Args](https://developer.android.com/guide/navigation/navigation-pass-data#Safe-args) Gradle plugin with the same version.

To enable sharing all versions that are used for both plugins and libraries are defined in [gradle.properties](./gradle.properties).

Unfortunately this technique cannot be applied to older Gradle plugins (added by `classpath`, not by `pluginManagement`), so some version in the [gradle.properties](./gradle.properties) are still duplicated.
## CI pipeline

CI is utilizing [GitHub Actions](https://github.com/features/actions). Complete GitHub Actions config is located in the [.github/workflows](.github/workflows) folder.

### PR Verification

Series of workflows runs (in parallel) for every opened PR and after merging PR to `main` branch:
* `./gradlew lintDebug` - runs Android lint
* `./gradlew detekt` - runs detekt
* `./gradlew ktlintCheck` - runs ktlint
* `./gradlew testDebugUnitTest` - run unit tests
* `./gradlew connectedCheck` - run UI tests
* `./gradlew :app:bundleDebug` - create app bundle

## Dependency updates

The [update-dependencies](.github/workflows/update-dependencies.yml) task run periodically and creates a pull request
containing dependency
updates
(updated gradle .lockfile files used by Gradle’s [dependency locking](https://docs.gradle.org/current/userguide/dependency_locking.html)).

## Design decisions

Read related articles to have a better understanding of underlying design decisions and various trade-offs.

* [Multiple ways of defining Clean Architecture layers](https://proandroiddev.com/multiple-ways-of-defining-clean-architecture-layers-bbb70afa5d4a)
* More coming soon

## Gradle update

`./gradlew wrapper --gradle-version=1.2.3`

## What this project does not cover?

The interface of the app utilizes some of the modern material design components, however, is deliberately kept simple to
focus on application architecture.

## Upcoming improvements

Checklist of all upcoming [enhancements](https://github.com/igorwojda/android-showcase/issues?q=is%3Aissue+is%3Aopen+sort%3Aupdated-desc+label%3Aenhancement).

## Getting started

There are a few ways to open this project.

### Android Studio

1. `Android Studio` -> `File` -> `New` -> `From Version control` -> `Git`
2. Enter `https://github.com/igorwojda/android-showcase.git` into URL field an press `Clone` button

### Command-line + Android Studio

1. Run `git clone https://github.com/igorwojda/android-showcase.git` command to clone project
2. Open `Android Studio` and select `File | Open...` from the menu. Select cloned directory and press `Open` button

## Inspiration

This is project is a sample, to inspire you and should handle most of the common cases, but please take a look at
additional resources.

### Cheat sheet

- [Core App Quality Checklist](https://developer.android.com/quality) - learn about building the high-quality app
- [Android Ecosystem Cheat Sheet](https://github.com/igorwojda/android-ecosystem-cheat-sheet) - board containing 200+ most important tools
- [Kotlin Coroutines - Use Cases on Android](https://github.com/LukasLechnerDev/Kotlin-Coroutine-Use-Cases-on-Android) - most popular coroutine usages

### Android projects

Other high-quality projects will help you to find solutions that work for your project:

- [Iosched](https://github.com/google/iosched) - official Android application from google IO 2019
- [Android Architecture Blueprints v2](https://github.com/googlesamples/android-architecture) - a showcase of various
  Android architecture approaches
- [Android sunflower](https://github.com/googlesamples/android-sunflower) complete `Jetpack` sample covering all
  libraries
- [GithubBrowserSample](https://github.com/googlesamples/android-architecture-components) - multiple small projects
  demonstrating usage of Android Architecture Components
- [Plaid](https://github.com/android/plaid) - a showcase of Android material design
- [Clean Architecture boilerplate](https://github.com/bufferapp/android-clean-architecture-boilerplate) - contains nice
  diagrams of Clean Architecture layers
- [Android samples](https://github.com/android) - official Android samples repository
- [Roxie](https://github.com/ww-tech/roxie) - solid example of `common state` approach together witch very good
  documentation
- [Kotlin Android template](https://github.com/cortinico/kotlin-android-template) - template that lets you create an Android/Kotlin project and be up and running in a few seconds.


## Known issues

- Gradle 7.1.1 is not compatible with GradleJDK 16 (build is failing, so JDK 15 must be used) 
- `ktlint` `import-ordering` rule conflicts with IDE default formatting rule, so it have to be [disabled](.editorconfig). This is partially fixed in AS 4.2 (see [Issue 527](https://github.com/pinterest/ktlint/issues/527) and [Issue KT-10974](https://youtrack.jetbrains.com/issue/KT-10974))
- False positive "Unused symbol" for a custom Android application class referenced in AndroidManifest.xml file ([Issue 27971](https://youtrack.jetbrains.net/issue/KT-27971))
- False positive "Function can be private" ([Issue KT-33610](https://youtrack.jetbrains.com/issue/KT-33610))
- False positive cannot access class ([Issue 16077](https://youtrack.jetbrains.com/issue/KT-44797)). This is fixed in InteliJ IDEA 2021.1 EAP 1 afair.
- Gradle has no way to share dependency versions between library and Gradle plugin or prod and test version of the library ([Issue 16077](https://github.com/gradle/gradle/issues/16077))
- Android lint complains about exceeding access rights to ArchTaskExecutor [Issue 79189568]((https://issuetracker.google.com/u/0/issues/79189568))
- JUnit 5 does not support tests with suspended modifier ([Issue 1914](https://github.com/junit-team/junit5/issues/1914))
- Gradle dependencies can't be easily shared between app libraries and Gradle plugins https://github.com/gradle/gradle/issues/16077

## Author
[![Follow me](https://img.shields.io/twitter/follow/YosifKalchev?style=social)](https://twitter.com/yosifkalchev)
