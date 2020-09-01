package io.github.andrespontt.gitpusher.controller;

import java.io.File;
import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitPusherController {
    
    private static final Logger log = LoggerFactory.getLogger(GitPusherController.class);

    String getRepoName(String repo) {
        if(repo.contains("/")) {
            return repo.substring(repo.lastIndexOf("/")+1);
        }
        return null;
    }

    @GetMapping(value="/api/git/last-commit")
    public String showLastCommitDefault(@RequestParam(name="repo") String repo) throws InvalidRemoteException, TransportException, GitAPIException, IOException {
        String repoName = getRepoName(repo);
        log.info("Repo name: {}", repoName);
        final File directory = new File(repoName);
        final Git git = Git.cloneRepository().setURI(repo).setDirectory(directory).call();
        final RevCommit latestCommit = git.log().setMaxCount(1).call().iterator().next();
        final String latestCommitHash = latestCommit.getName();
        FileUtils.deleteDirectory(directory);
        log.info("{}", git);
        return latestCommitHash;
    }
    
}