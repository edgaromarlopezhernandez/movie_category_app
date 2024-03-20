package com.movies.config;

import com.movies.utils.LocalVarsHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public LocalVarsHelper getLocalSystemHelperVariables() {
        String fs = System.getProperty("file.separator");
        String userDir = System.getProperty("user.dir");
        return LocalVarsHelper.builder()
                .userDirectory(userDir)
                .resourcesDirectory(userDir + fs + "src" + fs + "main" + fs + "resources" + fs)
                .localFileSeparator(fs)
                .staticDirectory(userDir + fs + "src" + fs + "main" + fs + "resources" + fs + "static" + fs)
                .build();
    }
}
