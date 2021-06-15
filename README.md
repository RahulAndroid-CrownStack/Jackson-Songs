# Jackson-Songs

This project cant be used as a Reference project for Integrating topics like RxJava, Dagger Hilt

Libraries and tools included:
- RecyclerViews
- [Navigation Components](https://developer.android.com/guide/navigation)
- [RxJava](https://github.com/ReactiveX/RxJava)
- [Retrofit 2](http://square.github.io/retrofit/)
- [Hilt](https://developer.android.com/codelabs/android-hilt#0)
- [View Binding](https://developer.android.com/topic/libraries/view-binding)


## Sample

![](https://noctischat.com/gifs/sample.gif)


## Requirements

- JDK 1.8
- [Android SDK](http://developer.android.com/sdk/index.html).
- Android 11 [(API 30) ](http://developer.android.com/tools/revisions/platforms.html).
- Latest Android SDK Tools and build tools.

## Architecture

This project follows MVVM Android architecture guidelines.

## New project setup 

To quickly start a new project from this boilerplate follow the next steps:

* Download this [repository as zip](https://github.com/RahulAndroid-CrownStack/Jackson-Songs/archive/refs/heads/master.zip).
* Change the package name. 
  * Rename packages in main using Android Studio.
  * In `app/build.gradle` file, `packageName`.
  * In `src/main/AndroidManifest.xml`.
* Create a new git repository.
* Replace the example code with your app code following the same architecture.
* Update README with information relevant to the new project.



## Folder structure
```
├── api… Contains envelope data class and JacksonSongApi interface
├── data… Contains Repository and data class
│   ├── SongInfo… Data class to receive api response
│   ├── SongRepository… makes api call
├── di… Dependency Injection Related file
│   ├── AppModule.kt… provides retrofit's instance and JacksonSongApi interface instance
├── ui… Package Contains app's ui classes
│   ├── details…  SongsDetailFragment.kt
│   ├── song_list… Contains classes to rendors song's list, reveived from api
│       ├──SongListAdapter.kt
│       ├──SongListFragment.kt
│       ├──SongViewModel.kt
│   ├── MainActivity.kt… 
│   
├── JacksonSongApplication… Application class to initiate Hilt

```

## Reference
 - RxJava 
    - [Github](https://github.com/ReactiveX/RxJava)
    - [RxJava & RxAndroid for Beginners](https://codingwithmitch.com/courses/rxjava-rxandroid-for-beginners/)
 


