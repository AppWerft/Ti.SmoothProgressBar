# Ti.SmoothProgressBar

This repo contains source code, module and a [tutorial](Tutorial.md) of Axways's Titanium moduke for realizing a smooth progress bar.

![](https://raw.githubusercontent.com/castorflex/SmoothProgressBar/master/screenshots/SPB_sample.gif)


## Usage

```   
import Progress from 'de.appwerft.smoothprogressbar';
const win = Ti.UI.createWindow();
const progressView = Progress.createView({
})
win.add(progressView);
```