package com.github.graph.core.loader;

import android.content.Context;
import com.github.graph.core.ThrowableLoader;
import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.util.List;

public class CommitsLoader extends ThrowableLoader<List<RepositoryCommit>> {

//    @Inject
    private CommitService service;

    private Repository repository;

    public CommitsLoader(Context context, List<RepositoryCommit> data, Repository repository) {
        super(context, data);
        this.repository = repository;
        service = new CommitService();
    }

    @Override
    public List<RepositoryCommit> loadData() throws Exception {
        return service.getCommits(repository);
    }
}
