/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.tech4box.cb2xml.sablecc.node;

import com.tech4box.cb2xml.sablecc.analysis.*;

@SuppressWarnings("nls")
public final class AAlphanumericLiteralLiteral extends PLiteral
{
    private TAlphanumericLiteral _alphanumericLiteral_;

    public AAlphanumericLiteralLiteral()
    {
        // Constructor
    }

    public AAlphanumericLiteralLiteral(
        @SuppressWarnings("hiding") TAlphanumericLiteral _alphanumericLiteral_)
    {
        // Constructor
        setAlphanumericLiteral(_alphanumericLiteral_);

    }

    @Override
    public Object clone()
    {
        return new AAlphanumericLiteralLiteral(
            cloneNode(this._alphanumericLiteral_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAlphanumericLiteralLiteral(this);
    }

    public TAlphanumericLiteral getAlphanumericLiteral()
    {
        return this._alphanumericLiteral_;
    }

    public void setAlphanumericLiteral(TAlphanumericLiteral node)
    {
        if(this._alphanumericLiteral_ != null)
        {
            this._alphanumericLiteral_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._alphanumericLiteral_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._alphanumericLiteral_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._alphanumericLiteral_ == child)
        {
            this._alphanumericLiteral_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._alphanumericLiteral_ == oldChild)
        {
            setAlphanumericLiteral((TAlphanumericLiteral) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
