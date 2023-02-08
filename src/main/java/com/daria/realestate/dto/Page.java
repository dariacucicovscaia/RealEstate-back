package com.daria.realestate.dto;

import java.util.List;
//TODO pattern: Builder
public class Page<T> {
    private final List<T> content;
    private final int totalElements;
    private final int currentPage;
    private final int elementsPerPage;

    public Page(List<T> content, int totalElements, int currentPage, int elementsPerPage) {
        this.content = content;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
        this.elementsPerPage = elementsPerPage;
    }

    public List<T> getContent() {
        return content;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public int getElementsPerPage() {
        return elementsPerPage;
    }
}
