package com.movies.utils;

import org.springframework.stereotype.Component;

@Component
public class PageableHelper {
    public PageInfo helper (Integer pageNumber, Integer pageSize, Long records){
        System.out.println("In helper=============================================");
        PageInfo pageInfo = new PageInfo();
        if(pageSize < 1){
            pageSize = 1;
        }
        if(pageSize > 50){
            pageSize = 50;
        }
        Double totalPages = Math.ceil((records*1.0) / pageSize);
        System.out.println("totalPages: " + totalPages);
        System.out.println("pageNumber > totalPages:::::: " + String.valueOf(pageNumber > totalPages));
        if(pageNumber > totalPages){
            pageNumber = totalPages.intValue();
            System.out.println("pageNumber" + pageNumber);
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
        System.out.println("Previous page calculation: ");
        if(pageNumber < 1){
            previousPage = totalPages.intValue();
        }else{
            previousPage = pageNumber;
        }
        System.out.println("ppc: " + previousPage);
        pageInfo.setTotalRecords(records);
        pageInfo.setCurrentPage(pageNumber + 1);
        pageInfo.setTotalPages(totalPages.longValue());
        pageInfo.setPageSize(pageSize);
        pageInfo.setNextPage(nextPage);
        pageInfo.setPreviousPage(previousPage);
        return pageInfo;
    }
}