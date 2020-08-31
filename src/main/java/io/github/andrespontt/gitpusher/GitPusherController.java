package io.github.andrespontt.gitpusher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitPusherController {

    @GetMapping(value = "/api/test/{param}", produces = "application/json")
    public Response apiTest(@PathVariable final String param) {
        final Response response = new Response();
        response.setMessage(param);
        response.setValue(System.getenv("GITHUB_TOKEN"));
        return response;
    }

    @GetMapping(value = "/api/git/last-commit")
    public String showLastCommit() throws InvalidRemoteException, TransportException, GitAPIException, IOException {
        final File directory = new File("temp/");
        final String uri = "https://github.com/andrespontt/spring-boot-git-pusher.git";
        Git git = Git.cloneRepository().setURI(uri).setDirectory(directory).call();
       
        final RevCommit latestCommit = git.log().setMaxCount(1).call().iterator().next();
        final String latestCommitHash = latestCommit.getName();
        FileUtils.deleteDirectory(directory);
        return latestCommitHash;
    }

}