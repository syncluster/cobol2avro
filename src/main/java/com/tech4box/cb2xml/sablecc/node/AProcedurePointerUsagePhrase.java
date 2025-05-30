/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.tech4box.cb2xml.sablecc.node;

import com.tech4box.cb2xml.sablecc.analysis.*;

@SuppressWarnings("nls")
public final class AProcedurePointerUsagePhrase extends PUsagePhrase
{
    private TProcedurePointer _procedurePointer_;

    public AProcedurePointerUsagePhrase()
    {
        // Constructor
    }

    public AProcedurePointerUsagePhrase(
        @SuppressWarnings("hiding") TProcedurePointer _procedurePointer_)
    {
        // Constructor
        setProcedurePointer(_procedurePointer_);

    }

    @Override
    public Object clone()
    {
        return new AProcedurePointerUsagePhrase(
            cloneNode(this._procedurePointer_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAProcedurePointerUsagePhrase(this);
    }

    public TProcedurePointer getProcedurePointer()
    {
        return this._procedurePointer_;
    }

    public void setProcedurePointer(TProcedurePointer node)
    {
        if(this._procedurePointer_ != null)
        {
            this._procedurePointer_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._procedurePointer_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._procedurePointer_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._procedurePointer_ == child)
        {
            this._procedurePointer_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._procedurePointer_ == oldChild)
        {
            setProcedurePointer((TProcedurePointer) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
