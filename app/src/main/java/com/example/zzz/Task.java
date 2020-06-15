package com.example.zzz;

public class Task {
    String id;
    String name;
    String description;
    String priority;
    String dueDate;
    String createdBy;
    String status;
    String assignedTo;

    public  Task(){
        //default

    }

    /**
     * Model class for Task
     * Adapter class to store data into database or get data from database
     * @param id
     * @param name
     * @param description
     * @param priority
     * @param dueDate
     * @param createdBy
     * @param status
     * @param assignedTo
     */

    public Task(String id, String name, String description, String priority, String dueDate, String createdBy, String status, String assignedTo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.createdBy = createdBy;
        this.status = status;
        this.assignedTo = assignedTo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
}
