package com.example.spedy.model;

import java.util.UUID;

public class Profession {

    private final UUID professionId;

    private int minSalary;

    private int maxSalary;

    private String title;

    public Profession(UUID professionId, int minSalary, int maxSalary, String title) {
        this.professionId = professionId;
        assignArguments(minSalary, maxSalary, title);
    }

    public Profession(int minSalary, int maxSalary, String title) {
        this.professionId = UUID.randomUUID();
        assignArguments(minSalary, maxSalary, title);
    }

    private void assignArguments(int minSalary, int maxSalary, String title) {
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.title = title;
    }

    public UUID getProfessionId() {
        return professionId;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(int minSalary) {
        this.minSalary = minSalary;
    }

    public int getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(int maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profession that = (Profession) o;

        return professionId.equals(that.professionId);
    }

    @Override
    public int hashCode() {
        return professionId.hashCode();
    }

    @Override
    public String toString() {
        return "Profession{" +
                "professionId=" + professionId +
                ", minSalary=" + minSalary +
                ", maxSalary=" + maxSalary +
                ", title='" + title + '\'' +
                '}';
    }
}
