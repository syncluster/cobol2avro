/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.tech4box.cb2xml.sablecc.node;

import com.tech4box.cb2xml.sablecc.analysis.*;

@SuppressWarnings("nls")
public final class ASynchronizedClauseClause extends PClause
{
    private PSynchronizedClause _synchronizedClause_;

    public ASynchronizedClauseClause()
    {
        // Constructor
    }

    public ASynchronizedClauseClause(
        @SuppressWarnings("hiding") PSynchronizedClause _synchronizedClause_)
    {
        // Constructor
        setSynchronizedClause(_synchronizedClause_);

    }

    @Override
    public Object clone()
    {
        return new ASynchronizedClauseClause(
            cloneNode(this._synchronizedClause_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASynchronizedClauseClause(this);
    }

    public PSynchronizedClause getSynchronizedClause()
    {
        return this._synchronizedClause_;
    }

    public void setSynchronizedClause(PSynchronizedClause node)
    {
        if(this._synchronizedClause_ != null)
        {
            this._synchronizedClause_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._synchronizedClause_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._synchronizedClause_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._synchronizedClause_ == child)
        {
            this._synchronizedClause_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._synchronizedClause_ == oldChild)
        {
            setSynchronizedClause((PSynchronizedClause) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
