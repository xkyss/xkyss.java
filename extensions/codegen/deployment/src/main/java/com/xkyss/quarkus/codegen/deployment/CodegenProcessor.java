package com.xkyss.quarkus.codegen.deployment;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

class CodegenProcessor {

    private static final String FEATURE = "xkyss-quarkus-codegen";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }
}