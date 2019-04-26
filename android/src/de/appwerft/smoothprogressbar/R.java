package de.appwerft.smoothprogressbar;

import org.appcelerator.titanium.util.TiRHelper;
import org.appcelerator.titanium.util.TiRHelper.ResourceNotFoundException;

public final class R {
	public R() {
	}

	public static final class attr {
		public static final int spbStyle = getRes("attr", "spbStyle");

	}
	public static final class bool {
		public static final int spb_default_reversed = getRes("bool", "spb_default_reversed");
		public static final int spb_default_mirror_mode = getRes("bool", "spb_default_mirror_mode");
		public static final int spb_default_progressiveStart_activated = getRes("bool", "spb_default_progressiveStart_activated");
	}
	public static final class color {
		public static final int spb_default_color = getRes("color","spb_default_color");
	}
	public static final class string {
		public static final int spb_default_speed = getRes("string","spb_default_speed");
	}
	public static final class integer {
		public static final int spb_default_sections_count = getRes("integer","spb_default_sections_count");
	}public static final class dimen {
		public static final int spb_default_stroke_separator_length = getRes("dimen","spb_default_stroke_separator_length");
		public static final int spb_default_stroke_width = getRes("dimen","spb_default_stroke_width");
		
	}
	public static final class styleable {
		public static final int SmoothProgressBar_spb_color = getRes("styleable", "SmoothProgressBar_spb_color");
		public static final int SmoothProgressBar_spb_sections_count = getRes("styleable",
				"SmoothProgressBar_spb_sections_count");
		public static final int SmoothProgressBar_spb_stroke_separator_length = getRes("styleable",
				"SmoothProgressBar_spb_stroke_separator_length");
		public static final int SmoothProgressBar_spb_stroke_width = getRes("styleable",
				"SmoothProgressBar_spb_stroke_width");
		public static final int SmoothProgressBar_spb_speed = getRes("styleable", "SmoothProgressBar_spb_speed");
		public static final int SmoothProgressBar_spb_progressiveStart_speed = getRes("styleable",
				"SmoothProgressBar_spb_progressiveStart_speed");
		public static final int SmoothProgressBar_spb_progressiveStop_speed = getRes("styleable",
				"SmoothProgressBar_spb_progressiveStop_speed");
		public static final int SmoothProgressBar_spb_reversed = getRes("styleable", "SmoothProgressBar_spb_reversed");
		public static final int SmoothProgressBar_spb_mirror_mode = getRes("styleable",
				"SmoothProgressBar_spb_mirror_mode");
		public static final int SmoothProgressBar_spb_progressiveStart_activated = getRes("styleable",
				"SmoothProgressBar_spb_progressiveStart_activated");
		public static final int SmoothProgressBar_spb_gradients = getRes("styleable",
				"SmoothProgressBar_spb_gradients");
		public static final int SmoothProgressBar_spb_generate_background_with_colors = getRes("styleable",
				"SmoothProgressBar_spb_generate_background_with_colors");
		public static final int SmoothProgressBar_spb_interpolator = getRes("styleable",
				"SmoothProgressBar_spb_interpolator");
		public static final int SmoothProgressBar_spb_background = getRes("styleable",
				"SmoothProgressBar_spb_background");
		
		public static final int[] SmoothProgressBar = { getRes("styleable", "SmoothProgressBar") };
		
		public static final int SmoothProgressBar_spb_colors =  getRes("styleable", "SmoothProgressBar_spb_colors") ;
	}

	private static int getRes(String a, String b) {
		try {
			return TiRHelper.getApplicationResource(a + "." + b);
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
			return -1;
		}
	}

}