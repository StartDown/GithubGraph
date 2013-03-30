package com.github.graph.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.graph.R;
import com.github.graph.util.AvatarLoader;
import org.eclipse.egit.github.core.Contributor;

import java.util.List;

public class ContributorAdapter extends SingleTypeAdapter<Contributor> {

    public static class UserHolder {
        ImageView avatar;
        TextView username;
        TextView contributions;
    }

    private final AvatarLoader avatars;

    private LayoutInflater inflater;

    public ContributorAdapter(Context context, List<Contributor> objects, AvatarLoader avatars) {
        super(context, R.layout.user_row);
        inflater = LayoutInflater.from(getContext());
        this.avatars = avatars;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserHolder holder;
        if (convertView != null) {
            holder = (UserHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.user_row, parent, false);
            holder = new UserHolder();
            holder.avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            holder.username = (TextView) convertView.findViewById(R.id.tv_login);
            holder.contributions = (TextView) convertView.findViewById(R.id.tv_commits);
            convertView.setTag(holder);
        }

        Contributor user = getItem(position);
        holder.username.setText(user.getLogin());

        holder.contributions.setText(getContext().getString(R.string.user_commits, user.getContributions()));

        avatars.bind(holder.avatar, user);

        return convertView;
    }
}
