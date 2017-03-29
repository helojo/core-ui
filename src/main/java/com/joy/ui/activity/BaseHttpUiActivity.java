package com.joy.ui.activity;

import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.joy.ui.R;
import com.joy.ui.activity.interfaces.BaseViewNet;
import com.joy.ui.view.JLoadingView;
import com.joy.utils.DeviceUtil;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by KEVIN.DAI on 16/7/3.
 */
public abstract class BaseHttpUiActivity extends BaseUiActivity implements BaseViewNet {

    private ImageView mIvTip;
    private View mLoadingView;
    private int mTipResId;
    private int LOADING_RES_ID = View.NO_ID;
    private int ERROR_RES_ID = R.drawable.ic_tip_error;
    private int EMPTY_RES_ID = R.drawable.ic_tip_empty;

    @Override
    public void resolveThemeAttribute() {
        super.resolveThemeAttribute();
        TypedArray a = obtainStyledAttributes(R.styleable.Theme);
        LOADING_RES_ID = a.getResourceId(R.styleable.Theme_loadingView, View.NO_ID);
        ERROR_RES_ID = a.getResourceId(R.styleable.Theme_errorTip, R.drawable.ic_tip_error);
        EMPTY_RES_ID = a.getResourceId(R.styleable.Theme_emptyTip, R.drawable.ic_tip_empty);
        a.recycle();
    }

    @Override
    public void wrapContentView(FrameLayout contentParent, View contentView) {
        super.wrapContentView(contentParent, contentView);
        addTipView(contentParent);
        addLoadingView(contentParent);
    }

    private void addTipView(FrameLayout contentParent) {
        mIvTip = new ImageView(this);
        mIvTip.setScaleType(ScaleType.CENTER_INSIDE);
        mIvTip.setOnClickListener(v -> onTipViewClick());
        hideImageView(mIvTip);
        contentParent.addView(mIvTip, getTipViewLp());
    }

    @SuppressWarnings("ResourceType")
    private LayoutParams getTipViewLp() {
        LayoutParams lp = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
        if (!isNoTitle() && !isOverlay()) {
            lp.topMargin = isSystemBarTrans() ? STATUS_BAR_HEIGHT + getToolbarHeight() : getToolbarHeight();
        }
        return lp;
    }

    private void addLoadingView(FrameLayout contentParent) {
        mLoadingView = getLoadingView();
        hideView(mLoadingView);
        contentParent.addView(mLoadingView, getLoadingViewLp());
    }

    @SuppressWarnings("ResourceType")
    private LayoutParams getLoadingViewLp() {
        LayoutParams lp = (LayoutParams) mLoadingView.getLayoutParams();
        if (lp == null) {
            lp = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT, Gravity.CENTER);
        }
        if (!isNoTitle() && !isOverlay()) {
            lp.topMargin = isSystemBarTrans() ? (STATUS_BAR_HEIGHT + getToolbarHeight()) / 2 : getToolbarHeight() / 2;
        }
        return lp;
    }

    public View getLoadingView() {
        if (LOADING_RES_ID == View.NO_ID) {
            return JLoadingView.get(this);
        } else {
            ImageView ivLoading = new ImageView(this);
            ivLoading.setLayoutParams(new LayoutParams(WRAP_CONTENT, WRAP_CONTENT, Gravity.CENTER));
            ivLoading.setImageResource(LOADING_RES_ID);
            return ivLoading;
        }
    }

    public void onTipViewClick() {
        if (getTipType() == TipType.ERROR) {
            if (isNetworkEnable()) {
                doOnRetry();
            } else {
                showToast(R.string.toast_common_no_network);
            }
        }
    }

    public abstract void doOnRetry();

    @Override
    public void showLoading() {
        if (mLoadingView instanceof ImageView) {
            ImageView loadingIv = (ImageView) mLoadingView;
            Drawable d = loadingIv.getDrawable();
            if (d instanceof AnimationDrawable) {
                AnimationDrawable ad = (AnimationDrawable) d;
                ad.start();
            }
        }
        showView(mLoadingView);
    }

    @Override
    public void hideLoading() {
        hideView(mLoadingView);
        if (mLoadingView instanceof ImageView) {
            ImageView loadingIv = (ImageView) mLoadingView;
            Drawable d = loadingIv.getDrawable();
            if (d instanceof AnimationDrawable) {
                AnimationDrawable ad = (AnimationDrawable) d;
                ad.stop();
            }
        }
    }

    @Override
    public void showContent() {
        showView(getContentView());
    }

    @Override
    public void hideContent() {
        hideView(getContentView());
    }

    @Override
    public void showErrorTip() {
        mTipResId = ERROR_RES_ID;
        showImageView(mIvTip, mTipResId);
    }

    @Override
    public void showEmptyTip() {
        mTipResId = EMPTY_RES_ID;
        showImageView(mIvTip, mTipResId);
    }

    @Override
    public void hideTipView() {
        hideImageView(mIvTip);
    }

    @NonNull
    @Override
    public ImageView getTipView() {
        return mIvTip;
    }

    @Override
    public TipType getTipType() {
        if (mIvTip.getDrawable() != null) {
            if (mTipResId == EMPTY_RES_ID) {
                return TipType.EMPTY;
            } else if (mTipResId == ERROR_RES_ID) {
                return TipType.ERROR;
            }
        }
        return TipType.NULL;
    }

    @DrawableRes
    public int getErrorTipResId() {
        return ERROR_RES_ID;
    }

    @DrawableRes
    public int getEmptyTipResId() {
        return EMPTY_RES_ID;
    }

    @Override
    public boolean isNetworkEnable() {
        return DeviceUtil.isNetworkEnable(getApplicationContext());
    }
}
