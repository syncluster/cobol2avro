/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.tech4box.cb2xml.sablecc.node;

import java.util.*;
import com.tech4box.cb2xml.sablecc.analysis.*;

@SuppressWarnings("nls")
public final class AOccursClause extends POccursClause
{
    private POccursFixedOrVariable _occursFixedOrVariable_;
    private final LinkedList<PAscendingOrDescendingKeyPhrase> _ascendingOrDescendingKeyPhrase_ = new LinkedList<PAscendingOrDescendingKeyPhrase>();
    private final LinkedList<PIndexedByPhrase> _indexedByPhrase_ = new LinkedList<PIndexedByPhrase>();

    public AOccursClause()
    {
        // Constructor
    }

    public AOccursClause(
        @SuppressWarnings("hiding") POccursFixedOrVariable _occursFixedOrVariable_,
        @SuppressWarnings("hiding") List<?> _ascendingOrDescendingKeyPhrase_,
        @SuppressWarnings("hiding") List<?> _indexedByPhrase_)
    {
        // Constructor
        setOccursFixedOrVariable(_occursFixedOrVariable_);

        setAscendingOrDescendingKeyPhrase(_ascendingOrDescendingKeyPhrase_);

        setIndexedByPhrase(_indexedByPhrase_);

    }

    @Override
    public Object clone()
    {
        return new AOccursClause(
            cloneNode(this._occursFixedOrVariable_),
            cloneList(this._ascendingOrDescendingKeyPhrase_),
            cloneList(this._indexedByPhrase_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAOccursClause(this);
    }

    public POccursFixedOrVariable getOccursFixedOrVariable()
    {
        return this._occursFixedOrVariable_;
    }

    public void setOccursFixedOrVariable(POccursFixedOrVariable node)
    {
        if(this._occursFixedOrVariable_ != null)
        {
            this._occursFixedOrVariable_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._occursFixedOrVariable_ = node;
    }

    public LinkedList<PAscendingOrDescendingKeyPhrase> getAscendingOrDescendingKeyPhrase()
    {
        return this._ascendingOrDescendingKeyPhrase_;
    }

    public void setAscendingOrDescendingKeyPhrase(List<?> list)
    {
        for(PAscendingOrDescendingKeyPhrase e : this._ascendingOrDescendingKeyPhrase_)
        {
            e.parent(null);
        }
        this._ascendingOrDescendingKeyPhrase_.clear();

        for(Object obj_e : list)
        {
            PAscendingOrDescendingKeyPhrase e = (PAscendingOrDescendingKeyPhrase) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._ascendingOrDescendingKeyPhrase_.add(e);
        }
    }

    public LinkedList<PIndexedByPhrase> getIndexedByPhrase()
    {
        return this._indexedByPhrase_;
    }

    public void setIndexedByPhrase(List<?> list)
    {
        for(PIndexedByPhrase e : this._indexedByPhrase_)
        {
            e.parent(null);
        }
        this._indexedByPhrase_.clear();

        for(Object obj_e : list)
        {
            PIndexedByPhrase e = (PIndexedByPhrase) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._indexedByPhrase_.add(e);
        }
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._occursFixedOrVariable_)
            + toString(this._ascendingOrDescendingKeyPhrase_)
            + toString(this._indexedByPhrase_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._occursFixedOrVariable_ == child)
        {
            this._occursFixedOrVariable_ = null;
            return;
        }

        if(this._ascendingOrDescendingKeyPhrase_.remove(child))
        {
            return;
        }

        if(this._indexedByPhrase_.remove(child))
        {
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._occursFixedOrVariable_ == oldChild)
        {
            setOccursFixedOrVariable((POccursFixedOrVariable) newChild);
            return;
        }

        for(ListIterator<PAscendingOrDescendingKeyPhrase> i = this._ascendingOrDescendingKeyPhrase_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PAscendingOrDescendingKeyPhrase) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        for(ListIterator<PIndexedByPhrase> i = this._indexedByPhrase_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PIndexedByPhrase) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        throw new RuntimeException("Not a child.");
    }
}
