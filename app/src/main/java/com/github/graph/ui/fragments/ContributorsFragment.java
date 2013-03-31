package com.github.graph.ui.fragments;

import android.os.Bundle;
import android.support.v4.content.Loader;
import com.github.graph.R;
import com.github.graph.core.loader.ContributorsLoader;
import com.github.graph.ui.adapters.ContributorAdapter;
import com.github.graph.ui.adapters.SingleTypeAdapter;
import com.github.graph.util.AvatarLoader;
import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;

import java.util.List;

public class ContributorsFragment extends ItemListFragment<Contributor> {

    public static final String FRAGMENT_TAG = "fragment_contributors";

    private Repository stubRepo;

    private AvatarLoader avatars;

    public static ContributorsFragment newInstance() {
        return new ContributorsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final User stubOwner = new User();
        stubOwner.setLogin("startdown");
        stubRepo = new Repository();
        stubRepo.setOwner(stubOwner);
        stubRepo.setName("GithubGraph");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_contributors);
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_cotributors_load;
    }

    @Override
    protected SingleTypeAdapter<Contributor> createAdapter(List<Contributor> items) {
        avatars = new AvatarLoader(getActivity());
        return new ContributorAdapter(getActivity(), items, avatars);
    }

    @Override
    public Loader<List<Contributor>> onCreateLoader(int id, Bundle args) {
        return new ContributorsLoader(getActivity(), items, stubRepo);
    }
}
