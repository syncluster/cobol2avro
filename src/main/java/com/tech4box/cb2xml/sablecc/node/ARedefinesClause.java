/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.tech4box.cb2xml.sablecc.node;

import com.tech4box.cb2xml.sablecc.analysis.*;

@SuppressWarnings("nls")
public final class ARedefinesClause extends PRedefinesClause
{
    private TRedefines _redefines_;
    private TDataName _dataName_;

    public ARedefinesClause()
    {
        // Constructor
    }

    public ARedefinesClause(
        @SuppressWarnings("hiding") TRedefines _redefines_,
        @SuppressWarnings("hiding") TDataName _dataName_)
    {
        // Constructor
        setRedefines(_redefines_);

        setDataName(_dataName_);

    }

    @Override
    public Object clone()
    {
        return new ARedefinesClause(
            cloneNode(this._redefines_),
            cloneNode(this._dataName_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseARedefinesClause(this);
    }

    public TRedefines getRedefines()
    {
        return this._redefines_;
    }

    public void setRedefines(TRedefines node)
    {
        if(this._redefines_ != null)
        {
            this._redefines_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._redefines_ = node;
    }

    public TDataName getDataName()
    {
        return this._dataName_;
    }

    public void setDataName(TDataName node)
    {
        if(this._dataName_ != null)
        {
            this._dataName_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._dataName_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._redefines_)
            + toString(this._dataName_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._redefines_ == child)
        {
            this._redefines_ = null;
            return;
        }

        if(this._dataName_ == child)
        {
            this._dataName_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._redefines_ == oldChild)
        {
            setRedefines((TRedefines) newChild);
            return;
        }

        if(this._dataName_ == oldChild)
        {
            setDataName((TDataName) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
