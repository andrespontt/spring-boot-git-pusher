package io.github.andrespontt.gitpusher.controller;

import java.io.File;
import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitPusherController {

    @GetMapping(value = "/api/git/last-commit")
    public String showLastCommit() throws InvalidRemoteException, TransportException, GitAPIException, IOException {
        final File directory = new File("temp/");
        final String uri = "https://github.com/andrespontt/spring-boot-git-pusher.git";
        final Git git = Git.cloneRepository().setURI(uri).setDirectory(directory).call();
        final RevCommit latestCommit = git.log().setMaxCount(1).call().iterator().next();
        final String latestCommitHash = latestCommit.getName();
        FileUtils.deleteDirectory(directory);
        return latestCommitHash;
    }

}