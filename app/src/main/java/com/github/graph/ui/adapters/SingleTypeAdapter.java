package com.github.graph.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Adapter for lists where only a single view type is used
 *
 * @param <V>
 */
public abstract class SingleTypeAdapter<V> extends BaseAdapter {

    private static final Object[] EMPTY = new Object[0];

    private final LayoutInflater inflater;

    private final int layout;

    private Object[] items;

    /**
     * Create adapter
     *
     * @param activity
     * @param layoutResourceId
     */
    public SingleTypeAdapter(final Activity activity, final int layoutResourceId) {
        this(activity.getLayoutInflater(), layoutResourceId);
    }

    /**
     * Create adapter
     *
     * @param context
     * @param layoutResourceId
     */
    public SingleTypeAdapter(final Context context, final int layoutResourceId) {
        this(LayoutInflater.from(context), layoutResourceId);
    }

    /**
     * Create adapter
     *
     * @param inflater
     * @param layoutResourceId
     */
    public SingleTypeAdapter(final LayoutInflater inflater, final int layoutResourceId) {
        this.inflater = inflater;
        this.layout = layoutResourceId;

        items = EMPTY;
    }

    /**
     * Get a list of all items
     *
     * @return list of all items
     */
    @SuppressWarnings("unchecked")
    protected List<V> getItems() {
        List<? extends Object> objList = Arrays.asList(items);
        return (List<V>) objList;
    }

    /**
     * Set items to display
     *
     * @param items
     */
    public void setItems(final Collection<?> items) {
        if (items != null && !items.isEmpty())
            setItems(items.toArray());
        else
            setItems(EMPTY);
    }

    /**
     * Set items to display
     *
     * @param items
     */
    public void setItems(final Object[] items) {
        if (items != null)
            this.items = items;
        else
            this.items = EMPTY;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @SuppressWarnings("unchecked")
    public V getItem(final int position) {
        return (V) items[position];
    }

    @Override
    public long getItemId(final int position) {
        return items[position].hashCode();
    }

    protected LayoutInflater getInflater() {
        return inflater;
    }

    protected Context getContext() {
        return inflater.getContext();
    }

    protected int getLayout() {
        return layout;
    }
}