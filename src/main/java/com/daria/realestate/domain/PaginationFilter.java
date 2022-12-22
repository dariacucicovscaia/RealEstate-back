package com.daria.realestate.domain;

import com.daria.realestate.domain.enums.OrderBy;

public class PaginationFilter {
    private int pageNumber;
    private int nrOfElementsWeWantDisplayed;
    private String columnWeWantOrdered;
    private OrderBy orderBy;
    private String criteria;

    public PaginationFilter(int pageNumber, int nrOfElementsWeWantDisplayed, String columnWeWantOrdered, OrderBy orderBy, String criteria) {
        this.pageNumber = pageNumber;
        this.nrOfElementsWeWantDisplayed = nrOfElementsWeWantDisplayed;
        this.columnWeWantOrdered = columnWeWantOrdered;
        this.orderBy = orderBy;
        this.criteria = criteria;
    }

    public PaginationFilter(int pageNumber, int nrOfElementsWeWantDisplayed, String columnWeWantOrdered, OrderBy orderBy) {
        this.pageNumber = pageNumber;
        this.nrOfElementsWeWantDisplayed = nrOfElementsWeWantDisplayed;
        this.columnWeWantOrdered = columnWeWantOrdered;
        this.orderBy = orderBy;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getNrOfElementsWeWantDisplayed() {
        return nrOfElementsWeWantDisplayed;
    }

    public void setNrOfElementsWeWantDisplayed(int nrOfElementsWeWantDisplayed) {
        this.nrOfElementsWeWantDisplayed = nrOfElementsWeWantDisplayed;
    }

    public String getColumnWeWantOrdered() {
        return columnWeWantOrdered;
    }

    public void setColumnWeWantOrdered(String columnWeWantOrdered) {
        this.columnWeWantOrdered = columnWeWantOrdered;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
}
