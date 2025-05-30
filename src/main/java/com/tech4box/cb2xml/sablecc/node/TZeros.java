/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.tech4box.cb2xml.sablecc.node;

import com.tech4box.cb2xml.sablecc.analysis.*;

@SuppressWarnings("nls")
public final class TZeros extends Token
{
    public TZeros(String text)
    {
        setText(text);
    }

    public TZeros(String text, int line, int pos)
    {
        setText(text);
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TZeros(getText(), getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTZeros(this);
    }
}
