package com.equals.accountservice.dto;

import com.equals.accountservice.domain.Statement;

public class Paging {
public static class PagingResponse {
    private final Iterable<Statement> statements;
    private final int page;
    private final int pageSize;

    private final Long totalRecords;

    public PagingResponse(Iterable<Statement> statements, int page, int pageSize, Long totalRecords) {
        this.statements = statements;
        this.page = page;
        this.pageSize = pageSize;
        this.totalRecords = totalRecords;
    }

    public Iterable<Statement> getStatements() {
        return statements;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public  Long getTotalRecords() {
        return  totalRecords;
    }
}}
