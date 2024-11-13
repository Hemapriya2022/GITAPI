package org.github.requestPOJO;


public class RepoCreateRequest {
    private String name;
    private String description;
    private String homepage;
    private boolean privateRepo;

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Setter for description
    public void setDescription(String description) {
        this.description = description;
    }

    // Setter for homepage
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    // Setter for privateRepo
    public void setPrivateRepo(boolean privateRepo) {
        this.privateRepo = privateRepo;
    }

    // Optional: Getters if needed for validation or testing
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getHomepage() {
        return homepage;
    }

    public boolean isPrivateRepo() {
        return privateRepo;
    }
}

