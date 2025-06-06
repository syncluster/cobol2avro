/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.tech4box.cb2xml.sablecc.node;

import com.tech4box.cb2xml.sablecc.analysis.*;

@SuppressWarnings("nls")
public final class ASeparateCharacter extends PSeparateCharacter
{
    private TSeparate _separate_;
    private TCharacter _character_;

    public ASeparateCharacter()
    {
        // Constructor
    }

    public ASeparateCharacter(
        @SuppressWarnings("hiding") TSeparate _separate_,
        @SuppressWarnings("hiding") TCharacter _character_)
    {
        // Constructor
        setSeparate(_separate_);

        setCharacter(_character_);

    }

    @Override
    public Object clone()
    {
        return new ASeparateCharacter(
            cloneNode(this._separate_),
            cloneNode(this._character_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASeparateCharacter(this);
    }

    public TSeparate getSeparate()
    {
        return this._separate_;
    }

    public void setSeparate(TSeparate node)
    {
        if(this._separate_ != null)
        {
            this._separate_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._separate_ = node;
    }

    public TCharacter getCharacter()
    {
        return this._character_;
    }

    public void setCharacter(TCharacter node)
    {
        if(this._character_ != null)
        {
            this._character_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._character_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._separate_)
            + toString(this._character_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._separate_ == child)
        {
            this._separate_ = null;
            return;
        }

        if(this._character_ == child)
        {
            this._character_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._separate_ == oldChild)
        {
            setSeparate((TSeparate) newChild);
            return;
        }

        if(this._character_ == oldChild)
        {
            setCharacter((TCharacter) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
