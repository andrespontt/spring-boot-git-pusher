package io.github.andrespontt.gitpusher.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GitPusherControllerTest {

    @Test
    public void getRepoName() {
        GitPusherController controller = new GitPusherController();
        String actual = controller.getRepoName("https://github.com/andrespontt/spring-boot-git-pusher.git");
        Assertions.assertEquals("spring-boot-git-pusher.git", actual);
    } 
    
}