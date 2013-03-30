package com.github.graph.core.loader;

import android.content.Context;
import com.github.graph.core.ThrowableLoader;
import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.util.List;

public class ContributorsLoader extends ThrowableLoader<List<Contributor>> {

//    @Inject
    private RepositoryService service;

    private Repository repository;

    public ContributorsLoader(Context context, List<Contributor> data, Repository repository) {
        super(context, data);
        this.repository = repository;
        service = new RepositoryService();
    }

    @Override
    public List<Contributor> loadData() throws Exception {
        return service.getContributors(repository, false);
    }
}
