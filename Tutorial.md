
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
