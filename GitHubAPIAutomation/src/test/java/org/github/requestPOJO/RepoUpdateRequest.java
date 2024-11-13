package org.github.requestPOJO;

public class RepoUpdateRequest {
    private String name;
    private String description;
    private boolean privateRepo;

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Setter for description
    public void setDescription(String description) {
        this.description = description;
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

    public boolean isPrivateRepo() {
        return privateRepo;
    }
}

