
# Tutorial: How build a Titaniummodul

## First steps / setup

### Creating an empty project

In Studio you can create a new mobile module project. 

<img src="https://i.imgur.com/SCXhpUa.png" width=320 /> <img src="https://i.imgur.com/Z3CImFH.png" width=320 />

Alternativly you can create a project on CLI:

```
ti create  -t module  -p android  -n smoothprogressbar --id de.appwerft.smoothprogressbar -d .
```
Et voilà we get this project structure:

<img src="https://i.imgur.com/sIqFh06.png" width=320 />

### Adding thirdparty stuff

There are two ways: usage of aar / jar or source code.

#### jar/aar

In both cases we copy the lib into `android/lib` folder. This folder has two functions: all libs will rawly copied into module and later into app and the IDE can resolve class structur for help in coding. 
##### Potential pitfall
`aar`s will only used in build process. The IDE cannot read it. Solution: you have to extract the `jar` from `aar`, place in a folder (not the android/lib/ folder!) and add to `.classpath`.

<img src="https://i.imgur.com/CPXuEDf.png" width=480 />

In the screenshot the line `mp3agic`.

#### source code
In some cases it make sense to add the source code. The used [library offers source code](https://github.com/castorflex/SmoothProgressBar/tree/master/library/src/main/java/fr/castorflex/android/smoothprogressbar). The simplest way is to clone the  project and copy the folder `fr/castorflex/android/smoothprogressbar/` from `/library/src/main/java/` to our folder `android/src`.

```
cd ~/Desktop
git clone git@github.com:castorflex/SmoothProgressBar.git
cd SmoothProgressBar/library/src/main/java
cp -r fr PROJECT/android/src
rm -rf ~/DesktopSmoothProgressBar
```
Now we see this folder structure:

<img src="https://i.imgur.com/Qt91BL4.png" width=320 />

We see two red problem fields:

##### Usage of R class

Android offen reads styling/configuration as XML from res folder. 
This properties are default values. In [MakeCustomActivity](https://github.com/castorflex/SmoothProgressBar/blob/master/sample/src/main/java/fr/castorflex/android/smoothprogressbar/sample/MakeCustomActivity.java#L157) we cann see, the example app uses setters for manipulating of properties.

We copy this [res](https://github.com/castorflex/SmoothProgressBar/tree/master/sample/src/main/res) folder to `android/platform/android/`

In native Android the class R contains a collection of (int) ids for referencing items in `*.xml` underneath `res`. The result of 

```
R.styleable.SmoothProgressBar_spb_color
```

is an int.

In Titanium we can use this construct:

```
TiRHelper.getApplicationResource("stylable.SmoothProgressBar_spb_color");
```
with the same result. 

_Summary: in native world we use this dot separated notation, in Titanium we use a string with similar syntax_

We _could_ edit the original source code, but we avoid this ugly pattern by creating a (mock) class `TiR` to resolve all dependencies. The TiR class is a static class and is a wrapper to `TiRHelper`. With this trick we can keep the original class, the only modification is to replace `R.` with `TiR`

In [applyStyle](https://github.com/castorflex/SmoothProgressBar/blob/master/library/src/main/java/fr/castorflex/android/smoothprogressbar/SmoothProgressBar.java#L121-L198) the parameters will applied. This is our entry point for module.



We could use this mechanisme, but it is to complicated for end user of module. The aspected interface are properties in createView or some methods at runtime. So we come to next issue: 

##### Usage of `@UiThread`
Android apps runs in different threads. The main thread is the UIthread. If we plan to use methods for modifying colors and other parameters at runtime we have to ensure to be in UIthread. In older Titanium modules a Messenger is using, maybe the annotation helps. First the annotation will not resolved. In head of class we see `import android.support.annotation.UiThread;`. We can resolve it by adding the path to classpath and the red markers will disappears, but the behaviour at runtime of app we don't know and we have to test it.

<img src="https://i.imgur.com/8hseiZT.png" width=420 />

Or manuell in `.classpath`:

```
<classpathentry 
	kind="lib" 
	path="~/Library/Android/sdk/extras/android/support/v4/android-support-v4.jar"/>
```

After this action the first issue (UIThread) seems to be resolved. 

If the annotation makes trouble at app runtime the we have two possibilities of solution:

* avoiding of setters (all properties only in constructor)
* usage of Ti.Messenger ([sample](https://github.com/appcelerator-archive/ti.gigya/blob/master/android/src/ti/gigya/GigyaModule.java))

### Constants

Inspecting of head of all classes show us  which constants could make sense to export. In [SmoothProgressBar.java](blob/master/android/src/fr/castorflex/android/smoothprogressbar/SmoothProgressBar.java) we see four constants for easying. It make sense to export in module for usage in JS parameters. First we have to modify the scope of these vars from `private` to `public` in SmoothProgressBar class.

```java
@Kroll.constant 
public static final int INTERPOLATOR_ACCELERATE = SmoothProgressBar.INTERPOLATOR_ACCELERATE;
@Kroll.constant 
public static final int INTERPOLATOR_ACCELERATEDECELERATE = SmoothProgressBar.INTERPOLATOR_ACCELERATEDECELERATE;
@Kroll.constant 
public static final int INTERPOLATOR_DECELERATE = SmoothProgressBar.INTERPOLATOR_DECELERATE;
@Kroll.constant 
public static final int INTERPOLATOR_LINEAR = SmoothProgressBar.INTERPOLATOR_LINEAR;
```