package com.github.graph.core;

import com.google.inject.AbstractModule;

public class GitHubGraphModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new ServicesModule());
    }
}
