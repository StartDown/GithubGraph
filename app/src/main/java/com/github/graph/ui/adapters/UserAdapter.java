package com.github.graph.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.graph.R;
import org.eclipse.egit.github.core.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yarik
 * Date: 3/30/13
 * Time: 7:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserAdapter extends ArrayAdapter<User> {

    public static class UserHolder {
        ImageView avatar;
        TextView username;
        TextView info;
    }

    private LayoutInflater inflater;

    public UserAdapter(Context context, List<User> objects) {
        super(context, 0, objects);
        inflater = LayoutInflater.from(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserHolder holder;
        if (convertView != null) {
            holder = (UserHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.user_row, parent, false);
            holder = new UserHolder();
            holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            holder.username = (TextView) convertView.findViewById(R.id.username);
            holder.info = (TextView) convertView.findViewById(R.id.info);
            convertView.setTag(holder);
        }

        User user = getItem(position);
        holder.username.setText(user.getName());


        return convertView;
    }
}
