/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.tech4box.cb2xml.sablecc.node;

import com.tech4box.cb2xml.sablecc.analysis.*;

@SuppressWarnings("nls")
public final class ALeadingLeadingOrTrailing extends PLeadingOrTrailing
{
    private TLeading _leading_;

    public ALeadingLeadingOrTrailing()
    {
        // Constructor
    }

    public ALeadingLeadingOrTrailing(
        @SuppressWarnings("hiding") TLeading _leading_)
    {
        // Constructor
        setLeading(_leading_);

    }

    @Override
    public Object clone()
    {
        return new ALeadingLeadingOrTrailing(
            cloneNode(this._leading_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseALeadingLeadingOrTrailing(this);
    }

    public TLeading getLeading()
    {
        return this._leading_;
    }

    public void setLeading(TLeading node)
    {
        if(this._leading_ != null)
        {
            this._leading_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._leading_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._leading_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._leading_ == child)
        {
            this._leading_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._leading_ == oldChild)
        {
            setLeading((TLeading) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
