package com.movies.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PageInfo implements Serializable {
    public Long totalRecords;
    public Long totalPages;
    public Integer pageSize;
    public Integer previousPage;
    public Integer currentPage;
    public Integer nextPage;
}