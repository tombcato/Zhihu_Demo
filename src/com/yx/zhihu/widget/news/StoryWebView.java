package com.yx.zhihu.widget.news;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class StoryWebView extends WebView
{
  public StoryWebView(Context paramContext)
  {
    this(paramContext, null);
  }

  public StoryWebView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public StoryWebView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
//    if (b.c(paramContext))
//      setBackgroundColor(Color.argb(1, 0, 0, 0));
  }
  
  @Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;
	}
}

