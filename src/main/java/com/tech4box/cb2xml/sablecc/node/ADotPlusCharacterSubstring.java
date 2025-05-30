/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.tech4box.cb2xml.sablecc.node;

import com.tech4box.cb2xml.sablecc.analysis.*;

@SuppressWarnings("nls")
public final class ADotPlusCharacterSubstring extends PCharacterSubstring
{
    private TDotPlus _dotPlus_;

    public ADotPlusCharacterSubstring()
    {
        // Constructor
    }

    public ADotPlusCharacterSubstring(
        @SuppressWarnings("hiding") TDotPlus _dotPlus_)
    {
        // Constructor
        setDotPlus(_dotPlus_);

    }

    @Override
    public Object clone()
    {
        return new ADotPlusCharacterSubstring(
            cloneNode(this._dotPlus_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseADotPlusCharacterSubstring(this);
    }

    public TDotPlus getDotPlus()
    {
        return this._dotPlus_;
    }

    public void setDotPlus(TDotPlus node)
    {
        if(this._dotPlus_ != null)
        {
            this._dotPlus_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._dotPlus_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._dotPlus_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._dotPlus_ == child)
        {
            this._dotPlus_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._dotPlus_ == oldChild)
        {
            setDotPlus((TDotPlus) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
