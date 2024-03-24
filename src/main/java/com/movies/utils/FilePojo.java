package com.movies.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilePojo {
    private byte[] data;
    private String name;
}
