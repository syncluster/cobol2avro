/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.tech4box.cb2xml.sablecc.node;

import com.tech4box.cb2xml.sablecc.analysis.*;

@SuppressWarnings("nls")
public final class ABlankWhenZeroClauseClause extends PClause
{
    private PBlankWhenZeroClause _blankWhenZeroClause_;

    public ABlankWhenZeroClauseClause()
    {
        // Constructor
    }

    public ABlankWhenZeroClauseClause(
        @SuppressWarnings("hiding") PBlankWhenZeroClause _blankWhenZeroClause_)
    {
        // Constructor
        setBlankWhenZeroClause(_blankWhenZeroClause_);

    }

    @Override
    public Object clone()
    {
        return new ABlankWhenZeroClauseClause(
            cloneNode(this._blankWhenZeroClause_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseABlankWhenZeroClauseClause(this);
    }

    public PBlankWhenZeroClause getBlankWhenZeroClause()
    {
        return this._blankWhenZeroClause_;
    }

    public void setBlankWhenZeroClause(PBlankWhenZeroClause node)
    {
        if(this._blankWhenZeroClause_ != null)
        {
            this._blankWhenZeroClause_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._blankWhenZeroClause_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._blankWhenZeroClause_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._blankWhenZeroClause_ == child)
        {
            this._blankWhenZeroClause_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._blankWhenZeroClause_ == oldChild)
        {
            setBlankWhenZeroClause((PBlankWhenZeroClause) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
