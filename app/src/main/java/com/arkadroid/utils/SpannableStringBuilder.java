package com.arkadroid.utils;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;

/**
 * @author Dwi Setiyono <dwi.setiyono@ovo.id>
 * @since 2018.07.01
 */
public class SpannableStringBuilder {
    private SpannableString mSpanStr;

    public SpannableStringBuilder() {
    }

    public static SpannableStringBuilder init(String s) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.mSpanStr = new SpannableString(s);
        return builder;
    }

    public SpannableStringBuilder makeBold(String ss) {
        return this.makeBold(ss, false);
    }

    public SpannableStringBuilder makeBold(String ss, boolean ignoreCase) {
        if(this.mSpanStr == null) {
            this.mSpanStr = new SpannableString(ss);
        }

        int index;
        if(ignoreCase) {
            index = this.mSpanStr.toString().toLowerCase().indexOf(ss.toLowerCase());
        } else {
            index = this.mSpanStr.toString().indexOf(ss);
        }

        if(index != -1) {
            this.mSpanStr.setSpan(new StyleSpan(1), index, index + ss.length(), 18);
        }

        return this;
    }

    public SpannableStringBuilder makeBold(String ss, boolean ignoreCase, boolean multiple) {
        if(this.mSpanStr == null) {
            this.mSpanStr = new SpannableString(ss);
        }

        String searchedString;
        String keyword;
        if(ignoreCase) {
            searchedString = this.mSpanStr.toString().toLowerCase();
            keyword = ss.toLowerCase();
        } else {
            searchedString = this.mSpanStr.toString();
            keyword = ss;
        }

        int index;
        if(!multiple) {
            index = searchedString.indexOf(keyword);
            if(index != -1) {
                this.mSpanStr.setSpan(new StyleSpan(1), index, index + ss.length(), 18);
            }
        } else {
            index = 0;

            while(index >= 0 && index >= 0 && index < searchedString.length()) {
                index = searchedString.indexOf(keyword, index);
                if(index != -1) {
                    this.mSpanStr.setSpan(new StyleSpan(1), index, index + keyword.length(), 17);
                    index = index + keyword.length();
                }
            }
        }

        return this;
    }

    public SpannableStringBuilder makeItalic(String ss) {
        return this.makeItalic(ss, false);
    }

    public SpannableStringBuilder makeItalic(String ss, boolean ignoreCase) {
        if(this.mSpanStr == null) {
            this.mSpanStr = new SpannableString(ss);
        }

        int index;
        if(ignoreCase) {
            index = this.mSpanStr.toString().toLowerCase().indexOf(ss.toLowerCase());
        } else {
            index = this.mSpanStr.toString().indexOf(ss);
        }

        if(index != -1) {
            this.mSpanStr.setSpan(new StyleSpan(2), index, index + ss.length(), 18);
        }

        return this;
    }

    public SpannableStringBuilder makeBoldItalic(String ss) {
        return this.makeBoldItalic(ss, false);
    }

    public SpannableStringBuilder makeBoldItalic(String ss, boolean ignoreCase) {
        if(this.mSpanStr == null) {
            this.mSpanStr = new SpannableString(ss);
        }

        int index;
        if(ignoreCase) {
            index = this.mSpanStr.toString().toLowerCase().indexOf(ss.toLowerCase());
        } else {
            index = this.mSpanStr.toString().indexOf(ss);
        }

        if(index != -1) {
            this.mSpanStr.setSpan(new StyleSpan(3), index, index + ss.length(), 18);
        }

        return this;
    }

    public SpannableStringBuilder setSpan(String ss, Object o) {
        if(this.mSpanStr == null) {
            this.mSpanStr = new SpannableString(ss);
        }

        int index = this.mSpanStr.toString().indexOf(ss);
        if(index != -1) {
            this.mSpanStr.setSpan(o, index, index + ss.length(), 18);
        }

        return this;
    }

    public SpannableStringBuilder setColor(String ss, @ColorInt int color) {
        return this.setColor(ss, color, false);
    }

    public SpannableStringBuilder setColor(String ss, @ColorInt int color, boolean ignoreCase) {
        if(this.mSpanStr == null) {
            this.mSpanStr = new SpannableString(ss);
        }

        int index;
        if(ignoreCase) {
            index = this.mSpanStr.toString().toLowerCase().indexOf(ss.toLowerCase());
        } else {
            index = this.mSpanStr.toString().indexOf(ss);
        }

        if(index != -1) {
            this.mSpanStr.setSpan(new ForegroundColorSpan(color), index, index + ss.length(), 18);
        }

        return this;
    }

    public SpannableStringBuilder setColor(String ss, @ColorInt int color, boolean ignoreCase, boolean multiple) {
        if(this.mSpanStr == null) {
            this.mSpanStr = new SpannableString(ss);
        }

        String searchedString;
        String keyword;
        if(ignoreCase) {
            searchedString = this.mSpanStr.toString().toLowerCase();
            keyword = ss.toLowerCase();
        } else {
            searchedString = this.mSpanStr.toString();
            keyword = ss;
        }

        int index;
        if(!multiple) {
            index = searchedString.indexOf(keyword);
            if(index != -1) {
                this.mSpanStr.setSpan(new ForegroundColorSpan(color), index, index + ss.length(), 18);
            }
        } else {
            index = 0;

            while(index >= 0 && index >= 0 && index < searchedString.length()) {
                index = searchedString.indexOf(keyword, index);
                if(index != -1) {
                    this.mSpanStr.setSpan(new ForegroundColorSpan(color), index, index + ss.length(), 18);
                    index = index + keyword.length();
                }
            }
        }

        return this;
    }

    public SpannableStringBuilder makeLink(Context context, String span, View.OnClickListener clickListener, @ColorInt int color) {
        return this.makeLink(context, span, clickListener, color, true);
    }

    public SpannableStringBuilder makeLink(Context context, String span, final View.OnClickListener clickListener, @ColorInt final int color, boolean isBold) {
        int index = this.mSpanStr.toString().indexOf(span);
        if(index == -1) {
            return this;
        } else {
            ClickableSpan clickableSpan = new ClickableSpan() {
                public void onClick(View view) {
                    if(clickListener != null) {
                        clickListener.onClick(view);
                    }

                }

                public void updateDrawState(TextPaint textPaint) {
                    textPaint.setColor(color);
                }
            };
            if(isBold) {
                this.makeBold(span);
            }

            this.setSpan(span, clickableSpan);
            return this;
        }
    }

    public SpannableString create() {
        return this.mSpanStr;
    }
}