/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.tech4box.cb2xml.sablecc.node;

import com.tech4box.cb2xml.sablecc.analysis.*;

@SuppressWarnings("nls")
public final class AUsageClause extends PUsageClause
{
    private PUsageIs _usageIs_;
    private PUsagePhrase _usagePhrase_;

    public AUsageClause()
    {
        // Constructor
    }

    public AUsageClause(
        @SuppressWarnings("hiding") PUsageIs _usageIs_,
        @SuppressWarnings("hiding") PUsagePhrase _usagePhrase_)
    {
        // Constructor
        setUsageIs(_usageIs_);

        setUsagePhrase(_usagePhrase_);

    }

    @Override
    public Object clone()
    {
        return new AUsageClause(
            cloneNode(this._usageIs_),
            cloneNode(this._usagePhrase_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAUsageClause(this);
    }

    public PUsageIs getUsageIs()
    {
        return this._usageIs_;
    }

    public void setUsageIs(PUsageIs node)
    {
        if(this._usageIs_ != null)
        {
            this._usageIs_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._usageIs_ = node;
    }

    public PUsagePhrase getUsagePhrase()
    {
        return this._usagePhrase_;
    }

    public void setUsagePhrase(PUsagePhrase node)
    {
        if(this._usagePhrase_ != null)
        {
            this._usagePhrase_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._usagePhrase_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._usageIs_)
            + toString(this._usagePhrase_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._usageIs_ == child)
        {
            this._usageIs_ = null;
            return;
        }

        if(this._usagePhrase_ == child)
        {
            this._usagePhrase_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._usageIs_ == oldChild)
        {
            setUsageIs((PUsageIs) newChild);
            return;
        }

        if(this._usagePhrase_ == oldChild)
        {
            setUsagePhrase((PUsagePhrase) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
