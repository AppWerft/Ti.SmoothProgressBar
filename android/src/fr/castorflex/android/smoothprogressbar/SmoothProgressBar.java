package fr.castorflex.android.smoothprogressbar;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;


/**
 * Created by castorflex on 11/10/13.
 */
public class SmoothProgressBar extends ProgressBar {

  /* was private, now public for usage in module */	
  public static final int INTERPOLATOR_ACCELERATE = 0;
  public static final int INTERPOLATOR_LINEAR = 1;
  public static final int INTERPOLATOR_ACCELERATEDECELERATE = 2;
  public static final int INTERPOLATOR_DECELERATE = 3;

  public SmoothProgressBar(Context context) {
    this(context, null);
  }

  public SmoothProgressBar(Context context, AttributeSet attrs) {
    this(context, attrs, TiR.attr.spbStyle);
  }

  public SmoothProgressBar(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    if (isInEditMode()) {
      setIndeterminateDrawable(new SmoothProgressDrawable.Builder(context, true).build());
      return;
    }

    Resources res = context.getResources();
    TypedArray a = context.obtainStyledAttributes(attrs, TiR.styleable.SmoothProgressBar, defStyle, 0);


	final int color = a.getColor(TiR.styleable.SmoothProgressBar_spb_color, res.getColor(TiR.color.spb_default_color));
    final int sectionsCount = a.getInteger(TiR.styleable.SmoothProgressBar_spb_sections_count, res.getInteger(TiR.integer.spb_default_sections_count));
    final int separatorLength = a.getDimensionPixelSize(TiR.styleable.SmoothProgressBar_spb_stroke_separator_length, res.getDimensionPixelSize(TiR.dimen.spb_default_stroke_separator_length));
    final float strokeWidth = a.getDimension(TiR.styleable.SmoothProgressBar_spb_stroke_width, res.getDimension(TiR.dimen.spb_default_stroke_width));
    final float speed = a.getFloat(TiR.styleable.SmoothProgressBar_spb_speed, Float.parseFloat(res.getString(TiR.string.spb_default_speed)));
    final float speedProgressiveStart = a.getFloat(TiR.styleable.SmoothProgressBar_spb_progressiveStart_speed, speed);
    final float speedProgressiveStop = a.getFloat(TiR.styleable.SmoothProgressBar_spb_progressiveStop_speed, speed);
    final int iInterpolator = a.getInteger(TiR.styleable.SmoothProgressBar_spb_interpolator, -1);
    final boolean reversed = a.getBoolean(TiR.styleable.SmoothProgressBar_spb_reversed, res.getBoolean(TiR.bool.spb_default_reversed));
    final boolean mirrorMode = a.getBoolean(TiR.styleable.SmoothProgressBar_spb_mirror_mode, res.getBoolean(TiR.bool.spb_default_mirror_mode));
    final int colorsId = a.getResourceId(TiR.styleable.SmoothProgressBar_spb_colors, 0);
    final boolean progressiveStartActivated = a.getBoolean(TiR.styleable.SmoothProgressBar_spb_progressiveStart_activated, res.getBoolean(TiR.bool.spb_default_progressiveStart_activated));
    final Drawable backgroundDrawable = a.getDrawable(TiR.styleable.SmoothProgressBar_spb_background);
    final boolean generateBackgroundWithColors = a.getBoolean(TiR.styleable.SmoothProgressBar_spb_generate_background_with_colors, false);
    final boolean gradients = a.getBoolean(TiR.styleable.SmoothProgressBar_spb_gradients, false);
    a.recycle();

    //interpolator
    Interpolator interpolator = null;
    if (iInterpolator == -1) {
      interpolator = getInterpolator();
    }
    if (interpolator == null) {
      switch (iInterpolator) {
        case INTERPOLATOR_ACCELERATEDECELERATE:
          interpolator = new AccelerateDecelerateInterpolator();
          break;
        case INTERPOLATOR_DECELERATE:
          interpolator = new DecelerateInterpolator();
          break;
        case INTERPOLATOR_LINEAR:
          interpolator = new LinearInterpolator();
          break;
        case INTERPOLATOR_ACCELERATE:
        default:
          interpolator = new AccelerateInterpolator();
      }
    }

    int[] colors = null;
    //colors
    if (colorsId != 0) {
      colors = res.getIntArray(colorsId);
    }

    SmoothProgressDrawable.Builder builder = new SmoothProgressDrawable.Builder(context)
        .speed(speed)
        .progressiveStartSpeed(speedProgressiveStart)
        .progressiveStopSpeed(speedProgressiveStop)
        .interpolator(interpolator)
        .sectionsCount(sectionsCount)
        .separatorLength(separatorLength)
        .strokeWidth(strokeWidth)
        .reversed(reversed)
        .mirrorMode(mirrorMode)
        .progressiveStart(progressiveStartActivated)
        .gradients(gradients);

    if (backgroundDrawable != null) {
      builder.backgroundDrawable(backgroundDrawable);
    }

    if (generateBackgroundWithColors) {
      builder.generateBackgroundUsingColors();
    }

    if (colors != null && colors.length > 0)
      builder.colors(colors);
    else
      builder.color(color);

    SmoothProgressDrawable d = builder.build();
    setIndeterminateDrawable(d);
  }

  public void applyStyle(int styleResId) {
    TypedArray a = getContext().obtainStyledAttributes(null, TiR.styleable.SmoothProgressBar, 0, styleResId);

    if (a.hasValue(TiR.styleable.SmoothProgressBar_spb_color)) {
      setSmoothProgressDrawableColor(a.getColor(TiR.styleable.SmoothProgressBar_spb_color, 0));
    }
    if (a.hasValue(TiR.styleable.SmoothProgressBar_spb_colors)) {
      int colorsId = a.getResourceId(TiR.styleable.SmoothProgressBar_spb_colors, 0);
      if (colorsId != 0) {
        int[] colors = getResources().getIntArray(colorsId);
        if (colors != null && colors.length > 0)
          setSmoothProgressDrawableColors(colors);
      }
    }
    if (a.hasValue(TiR.styleable.SmoothProgressBar_spb_sections_count)) {
      setSmoothProgressDrawableSectionsCount(a.getInteger(TiR.styleable.SmoothProgressBar_spb_sections_count, 0));
    }
    if (a.hasValue(TiR.styleable.SmoothProgressBar_spb_stroke_separator_length)) {
      setSmoothProgressDrawableSeparatorLength(a.getDimensionPixelSize(TiR.styleable.SmoothProgressBar_spb_stroke_separator_length, 0));
    }
    if (a.hasValue(TiR.styleable.SmoothProgressBar_spb_stroke_width)) {
      setSmoothProgressDrawableStrokeWidth(a.getDimension(TiR.styleable.SmoothProgressBar_spb_stroke_width, 0));
    }
    if (a.hasValue(TiR.styleable.SmoothProgressBar_spb_speed)) {
      setSmoothProgressDrawableSpeed(a.getFloat(TiR.styleable.SmoothProgressBar_spb_speed, 0));
    }
    if (a.hasValue(TiR.styleable.SmoothProgressBar_spb_progressiveStart_speed)) {
      setSmoothProgressDrawableProgressiveStartSpeed(a.getFloat(TiR.styleable.SmoothProgressBar_spb_progressiveStart_speed, 0));
    }
    if (a.hasValue(TiR.styleable.SmoothProgressBar_spb_progressiveStop_speed)) {
      setSmoothProgressDrawableProgressiveStopSpeed(a.getFloat(TiR.styleable.SmoothProgressBar_spb_progressiveStop_speed, 0));
    }
    if (a.hasValue(TiR.styleable.SmoothProgressBar_spb_reversed)) {
      setSmoothProgressDrawableReversed(a.getBoolean(TiR.styleable.SmoothProgressBar_spb_reversed, false));
    }
    if (a.hasValue(TiR.styleable.SmoothProgressBar_spb_mirror_mode)) {
      setSmoothProgressDrawableMirrorMode(a.getBoolean(TiR.styleable.SmoothProgressBar_spb_mirror_mode, false));
    }
    if (a.hasValue(TiR.styleable.SmoothProgressBar_spb_progressiveStart_activated)) {
      setProgressiveStartActivated(a.getBoolean(TiR.styleable.SmoothProgressBar_spb_progressiveStart_activated, false));
    }
    if (a.hasValue(TiR.styleable.SmoothProgressBar_spb_progressiveStart_activated)) {
      setProgressiveStartActivated(a.getBoolean(TiR.styleable.SmoothProgressBar_spb_progressiveStart_activated, false));
    }
    if (a.hasValue(TiR.styleable.SmoothProgressBar_spb_gradients)) {
      setSmoothProgressDrawableUseGradients(a.getBoolean(TiR.styleable.SmoothProgressBar_spb_gradients, false));
    }
    if (a.hasValue(TiR.styleable.SmoothProgressBar_spb_generate_background_with_colors)) {
      if (a.getBoolean(TiR.styleable.SmoothProgressBar_spb_generate_background_with_colors, false)) {
        setSmoothProgressDrawableBackgroundDrawable(
            SmoothProgressBarUtils.generateDrawableWithColors(checkIndeterminateDrawable().getColors(), checkIndeterminateDrawable().getStrokeWidth()));
      }
    }
    if (a.hasValue(TiR.styleable.SmoothProgressBar_spb_interpolator)) {
      int iInterpolator = a.getInteger(TiR.styleable.SmoothProgressBar_spb_interpolator, -1);
      Interpolator interpolator;
      switch (iInterpolator) {
        case INTERPOLATOR_ACCELERATEDECELERATE:
          interpolator = new AccelerateDecelerateInterpolator();
          break;
        case INTERPOLATOR_DECELERATE:
          interpolator = new DecelerateInterpolator();
          break;
        case INTERPOLATOR_LINEAR:
          interpolator = new LinearInterpolator();
          break;
        case INTERPOLATOR_ACCELERATE:
          interpolator = new AccelerateInterpolator();
          break;
        default:
          interpolator = null;
      }
      if (interpolator != null) {
        setInterpolator(interpolator);
      }
    }
    a.recycle();
  }

  @Override
  protected synchronized void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (isIndeterminate() && getIndeterminateDrawable() instanceof SmoothProgressDrawable &&
        !((SmoothProgressDrawable) getIndeterminateDrawable()).isRunning()) {
      getIndeterminateDrawable().draw(canvas);
    }
  }

  private SmoothProgressDrawable checkIndeterminateDrawable() {
    Drawable ret = getIndeterminateDrawable();
    if (ret == null || !(ret instanceof SmoothProgressDrawable))
      throw new RuntimeException("The drawable is not a SmoothProgressDrawable");
    return (SmoothProgressDrawable) ret;
  }

  @Override
  public void setInterpolator(Interpolator interpolator) {
    super.setInterpolator(interpolator);
    Drawable ret = getIndeterminateDrawable();
    if (ret != null && (ret instanceof SmoothProgressDrawable))
      ((SmoothProgressDrawable) ret).setInterpolator(interpolator);
  }

  public void setSmoothProgressDrawableInterpolator(Interpolator interpolator) {
    checkIndeterminateDrawable().setInterpolator(interpolator);
  }

  public void setSmoothProgressDrawableColors(int[] colors) {
    checkIndeterminateDrawable().setColors(colors);
  }

  public void setSmoothProgressDrawableColor(int color) {
    checkIndeterminateDrawable().setColor(color);
  }

  public void setSmoothProgressDrawableSpeed(float speed) {
    checkIndeterminateDrawable().setSpeed(speed);
  }

  public void setSmoothProgressDrawableProgressiveStartSpeed(float speed) {
    checkIndeterminateDrawable().setProgressiveStartSpeed(speed);
  }

  public void setSmoothProgressDrawableProgressiveStopSpeed(float speed) {
    checkIndeterminateDrawable().setProgressiveStopSpeed(speed);
  }

  public void setSmoothProgressDrawableSectionsCount(int sectionsCount) {
    checkIndeterminateDrawable().setSectionsCount(sectionsCount);
  }

  public void setSmoothProgressDrawableSeparatorLength(int separatorLength) {
    checkIndeterminateDrawable().setSeparatorLength(separatorLength);
  }

  public void setSmoothProgressDrawableStrokeWidth(float strokeWidth) {
    checkIndeterminateDrawable().setStrokeWidth(strokeWidth);
  }

  public void setSmoothProgressDrawableReversed(boolean reversed) {
    checkIndeterminateDrawable().setReversed(reversed);
  }

  public void setSmoothProgressDrawableMirrorMode(boolean mirrorMode) {
    checkIndeterminateDrawable().setMirrorMode(mirrorMode);
  }

  public void setProgressiveStartActivated(boolean progressiveStartActivated) {
    checkIndeterminateDrawable().setProgressiveStartActivated(progressiveStartActivated);
  }

  public void setSmoothProgressDrawableCallbacks(SmoothProgressDrawable.Callbacks listener) {
    checkIndeterminateDrawable().setCallbacks(listener);
  }

  public void setSmoothProgressDrawableBackgroundDrawable(Drawable drawable) {
    checkIndeterminateDrawable().setBackgroundDrawable(drawable);
  }

  public void setSmoothProgressDrawableUseGradients(boolean useGradients) {
    checkIndeterminateDrawable().setUseGradients(useGradients);
  }

  public void progressiveStart() {
    checkIndeterminateDrawable().progressiveStart();
  }

  public void progressiveStop() {
    checkIndeterminateDrawable().progressiveStop();
  }
}
