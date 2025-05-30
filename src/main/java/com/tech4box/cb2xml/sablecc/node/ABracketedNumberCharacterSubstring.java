/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.tech4box.cb2xml.sablecc.node;

import com.tech4box.cb2xml.sablecc.analysis.*;

@SuppressWarnings("nls")
public final class ABracketedNumberCharacterSubstring extends PCharacterSubstring
{
    private PBracketedNumber _bracketedNumber_;

    public ABracketedNumberCharacterSubstring()
    {
        // Constructor
    }

    public ABracketedNumberCharacterSubstring(
        @SuppressWarnings("hiding") PBracketedNumber _bracketedNumber_)
    {
        // Constructor
        setBracketedNumber(_bracketedNumber_);

    }

    @Override
    public Object clone()
    {
        return new ABracketedNumberCharacterSubstring(
            cloneNode(this._bracketedNumber_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseABracketedNumberCharacterSubstring(this);
    }

    public PBracketedNumber getBracketedNumber()
    {
        return this._bracketedNumber_;
    }

    public void setBracketedNumber(PBracketedNumber node)
    {
        if(this._bracketedNumber_ != null)
        {
            this._bracketedNumber_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._bracketedNumber_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._bracketedNumber_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._bracketedNumber_ == child)
        {
            this._bracketedNumber_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._bracketedNumber_ == oldChild)
        {
            setBracketedNumber((PBracketedNumber) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
