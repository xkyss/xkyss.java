package com.xkyss.quarkus.codegen.runtime;

import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;

import java.util.List;


@ConfigMapping(prefix = "xkyss.codegen")
@ConfigRoot(phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public interface CodegenConfig {

    /**
     * Data source of code generator
     */
    List<SourceConfig> source();

    /**
     * Data target of code generator
     */
    List<TargetConfig> target();

    /**
     * Generators
     */
    List<GenerateConfig> generate();


    interface SourceConfig {

        /**
         * Source name
         */
        String name();

        /**
         * Source kind:
         *  db / sql / insert-sql / json
         */
        String kind();
    }

    interface TargetConfig {

        /**
         * Target name
         */
        String name();

        /**
         * Template name (qute)
         */
        String template();
    }

    interface GenerateConfig {
        /**
         * Source name
         */
        String source();

        /**
         * Target name
         */
        String target();
    }
}
