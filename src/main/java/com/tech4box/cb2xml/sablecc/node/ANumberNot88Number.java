/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.tech4box.cb2xml.sablecc.node;

import com.tech4box.cb2xml.sablecc.analysis.*;

@SuppressWarnings("nls")
public final class ANumberNot88Number extends PNumber
{
    private TNumberNot88 _numberNot88_;

    public ANumberNot88Number()
    {
        // Constructor
    }

    public ANumberNot88Number(
        @SuppressWarnings("hiding") TNumberNot88 _numberNot88_)
    {
        // Constructor
        setNumberNot88(_numberNot88_);

    }

    @Override
    public Object clone()
    {
        return new ANumberNot88Number(
            cloneNode(this._numberNot88_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseANumberNot88Number(this);
    }

    public TNumberNot88 getNumberNot88()
    {
        return this._numberNot88_;
    }

    public void setNumberNot88(TNumberNot88 node)
    {
        if(this._numberNot88_ != null)
        {
            this._numberNot88_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._numberNot88_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._numberNot88_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._numberNot88_ == child)
        {
            this._numberNot88_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._numberNot88_ == oldChild)
        {
            setNumberNot88((TNumberNot88) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
