package io.github.andrespontt.gitpusher;

public class Response {

    private String repo;
    private String branch;
    private String hash;

    public String getRepo() {
        return repo;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }
    
    
  
}
