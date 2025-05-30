/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.tech4box.cb2xml.sablecc.node;

import com.tech4box.cb2xml.sablecc.analysis.*;

@SuppressWarnings("nls")
public final class AValueValueOrValues extends PValueOrValues
{
    private TValue _value_;
    private TIs _is_;

    public AValueValueOrValues()
    {
        // Constructor
    }

    public AValueValueOrValues(
        @SuppressWarnings("hiding") TValue _value_,
        @SuppressWarnings("hiding") TIs _is_)
    {
        // Constructor
        setValue(_value_);

        setIs(_is_);

    }

    @Override
    public Object clone()
    {
        return new AValueValueOrValues(
            cloneNode(this._value_),
            cloneNode(this._is_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAValueValueOrValues(this);
    }

    public TValue getValue()
    {
        return this._value_;
    }

    public void setValue(TValue node)
    {
        if(this._value_ != null)
        {
            this._value_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._value_ = node;
    }

    public TIs getIs()
    {
        return this._is_;
    }

    public void setIs(TIs node)
    {
        if(this._is_ != null)
        {
            this._is_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._is_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._value_)
            + toString(this._is_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._value_ == child)
        {
            this._value_ = null;
            return;
        }

        if(this._is_ == child)
        {
            this._is_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._value_ == oldChild)
        {
            setValue((TValue) newChild);
            return;
        }

        if(this._is_ == oldChild)
        {
            setIs((TIs) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
