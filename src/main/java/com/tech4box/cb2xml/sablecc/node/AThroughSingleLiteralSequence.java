/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.tech4box.cb2xml.sablecc.node;

import com.tech4box.cb2xml.sablecc.analysis.*;

@SuppressWarnings("nls")
public final class AThroughSingleLiteralSequence extends PLiteralSequence
{
    private PLiteral _from_;
    private TThrough _through_;
    private PLiteral _to_;

    public AThroughSingleLiteralSequence()
    {
        // Constructor
    }

    public AThroughSingleLiteralSequence(
        @SuppressWarnings("hiding") PLiteral _from_,
        @SuppressWarnings("hiding") TThrough _through_,
        @SuppressWarnings("hiding") PLiteral _to_)
    {
        // Constructor
        setFrom(_from_);

        setThrough(_through_);

        setTo(_to_);

    }

    @Override
    public Object clone()
    {
        return new AThroughSingleLiteralSequence(
            cloneNode(this._from_),
            cloneNode(this._through_),
            cloneNode(this._to_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAThroughSingleLiteralSequence(this);
    }

    public PLiteral getFrom()
    {
        return this._from_;
    }

    public void setFrom(PLiteral node)
    {
        if(this._from_ != null)
        {
            this._from_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._from_ = node;
    }

    public TThrough getThrough()
    {
        return this._through_;
    }

    public void setThrough(TThrough node)
    {
        if(this._through_ != null)
        {
            this._through_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._through_ = node;
    }

    public PLiteral getTo()
    {
        return this._to_;
    }

    public void setTo(PLiteral node)
    {
        if(this._to_ != null)
        {
            this._to_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._to_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._from_)
            + toString(this._through_)
            + toString(this._to_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._from_ == child)
        {
            this._from_ = null;
            return;
        }

        if(this._through_ == child)
        {
            this._through_ = null;
            return;
        }

        if(this._to_ == child)
        {
            this._to_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._from_ == oldChild)
        {
            setFrom((PLiteral) newChild);
            return;
        }

        if(this._through_ == oldChild)
        {
            setThrough((TThrough) newChild);
            return;
        }

        if(this._to_ == oldChild)
        {
            setTo((PLiteral) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
