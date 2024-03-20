package com.movies.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocalVarsHelper {
    private String userDirectory;
    private String resourcesDirectory;
    private String localFileSeparator;
    private String staticDirectory;
}
