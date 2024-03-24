package com.movies.utils;

import org.springframework.stereotype.Component;

@Component
public class PageableHelper {
    public PageInfo helper (Integer pageNumber, Integer pageSize, Long records){
        PageInfo pageInfo = new PageInfo();
        if(pageSize < 1){
            pageSize = 1;
        }
        if(pageSize > 30){
            pageSize = 30;
        }
        Double totalPages = Math.ceil((records*1.0) / pageSize);

        if(pageNumber > totalPages){
            pageNumber = totalPages.intValue();
        }

        pageNumber = pageNumber - 1;
        if(pageNumber < 1){
            pageNumber = 0;
        }
        Integer nextPage;
        if(pageNumber >= totalPages -1){
            nextPage = 1;
        }
        else{
            nextPage = pageNumber + 2;
        }
        Integer previousPage;
        if(pageNumber < 1){
            previousPage = totalPages.intValue();
        }else{
            previousPage = pageNumber;
        }
        pageInfo.setTotalRecords(records);
        pageInfo.setCurrentPage(pageNumber + 1);
        pageInfo.setTotalPages(totalPages.longValue());
        pageInfo.setPageSize(pageSize);
        pageInfo.setNextPage(nextPage);
        pageInfo.setPreviousPage(previousPage);
        return pageInfo;
    }
}