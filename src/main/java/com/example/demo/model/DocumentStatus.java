package com.example.demo.model;

import java.io.Serializable;

public enum DocumentStatus implements Serializable {

    ACT("ACT"),

    CANCELED("CANCELED"),

    REVISION_REQUIRED("REVISION_REQUIRED");

    public String documentStatus;

    private DocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

}


