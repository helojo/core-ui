package com.joy.ui.utils;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.internal.SnackbarContentLayout;
import android.support.design.widget.Snackbar;
import android.view.View;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;

/**
 * Created by Daisw on 16/7/18.
 */
public class SnackbarUtil {

    public static final int NO_COLOR = -1;

    @SuppressWarnings("ResourceType")
    public static void showSnackbar(@NonNull View view, @NonNull CharSequence text) {
        showSnackbar(view, text, LENGTH_SHORT);
    }

    public static void showSnackbar(@NonNull View view, @NonNull CharSequence text, @Snackbar.Duration int duration) {
        showSnackbar(view, text, duration, NO_COLOR, NO_COLOR);
    }

    public static void showSnackbar(@NonNull View view, @NonNull CharSequence text, @Snackbar.Duration int duration, @ColorInt int textColor) {
        showSnackbar(view, text, duration, NO_COLOR, textColor);
    }

    @SuppressWarnings("RestrictedApi")
    public static void showSnackbar(@NonNull View view, @NonNull CharSequence text, @Snackbar.Duration int duration, @ColorInt int bgColor, @ColorInt int textColor) {
        Snackbar snackbar = Snackbar.make(view, text, duration);
        Snackbar.SnackbarLayout sLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        if (bgColor != NO_COLOR) {
            sLayout.setBackgroundColor(bgColor);
        }
        try {
            SnackbarContentLayout contentLayout = (SnackbarContentLayout) sLayout.getChildAt(0);
            if (textColor != NO_COLOR) {
                contentLayout.getMessageView().setTextColor(textColor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        snackbar.show();
    }
}
