package io.github.andrespontt.gitpusher.controller;

import java.io.File;
import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.andrespontt.gitpusher.Response;

@RestController
public class GitPusherController {
    
    private static final Logger log = LoggerFactory.getLogger(GitPusherController.class);

    String getRepoName(String repo) {
        if(repo.contains("/")) {
            return repo.substring(repo.lastIndexOf("/")+1);
        }
        return null;
    }

    @GetMapping(value="/api/git/info/repo/{username}/{repoName}")
    public Response getRepoInfo(@PathVariable String username, @PathVariable String repoName) {
        String template = "https://github.com/%s/%s";
        String repoUrl = String.format(template, username, repoName);
        log.info("repoUrl: {}", repoUrl);
        return checkout(repoUrl);
    }

    @GetMapping(value="/api/git/last-commit")
    public Response getRepoInfo(@RequestParam(name="repo") String repoUrl) {
       return checkout(repoUrl);
    }

    private Response checkout(String repoUrl) {
        try {
            String repoName = getRepoName(repoUrl);
            final File directory = new File(repoName);
            Git git;
            Response response;
            git = Git.cloneRepository().setURI(repoUrl).setDirectory(directory).call();
            final RevCommit latestCommit = git.log().setMaxCount(1).call().iterator().next();
            final String latestCommitHash = latestCommit.getName();
            response = new Response();
            response.setBranch(git.getRepository().getBranch());
            response.setMessage(latestCommit.getFullMessage());
            response.setHash(latestCommitHash);
            FileUtils.deleteDirectory(directory);
            return response;
        } catch (GitAPIException | IOException e) {
           log.error("{}", e);
        }
        return null; 
    }
    
}