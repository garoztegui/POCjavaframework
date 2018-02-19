package com.proquest.ipa.automation.framework.testData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseFromApi {

    private String start;
    private String length;
    private String totalCount;
    public List<User> users;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {

        private String id;
        private String _id;
        private String creationDate;
        private String lastModified;
        private String discountGroupId;
        private boolean emailSent;
        private String status;
        private String firstName;
        private String lastName;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
        }

        public String getLastModified() {
            return lastModified;
        }

        public void setLastModified(String lastModified) {
            this.lastModified = lastModified;
        }

        public String getDiscountGroupId() {
            return discountGroupId;
        }

        public void setDiscountGroupId(String discountGroupId) {
            this.discountGroupId = discountGroupId;
        }

        public boolean isEmailSent() {
            return emailSent;
        }

        public void setEmailSent(boolean emailSent) {
            this.emailSent = emailSent;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
}