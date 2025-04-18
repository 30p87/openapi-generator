/*
 * Copyright 2019 OpenAPI-Generator Contributors (https://openapi-generator.tech)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openapitools.codegen.haskellservant;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.ParseOptions;
import org.openapitools.codegen.ClientOptInput;
import org.openapitools.codegen.DefaultGenerator;
import org.openapitools.codegen.TestUtils;
import org.openapitools.codegen.languages.HaskellServantCodegen;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HaskellServantCodegenTest {

    @Test
    public void testGenerateRootEndpoint() throws IOException {
        // given
        File output = Files.createTempDirectory("test").toFile().getCanonicalFile();
        output.deleteOnExit();
        String outputPath = output.getAbsolutePath().replace('\\', '/');

        final HaskellServantCodegen codegen = new HaskellServantCodegen();
        codegen.setOutputDir(output.getAbsolutePath());

        OpenAPI openAPI = new OpenAPIParser()
                .readLocation("src/test/resources/3_0/rootOperation.yaml", null, new ParseOptions())
                .getOpenAPI();

        ClientOptInput input = new ClientOptInput();
        input.openAPI(openAPI);
        input.config(codegen);

        // when
        DefaultGenerator generator = new DefaultGenerator();
        generator.setGenerateMetadata(false);
        generator.opts(input).generate();

        // then
        TestUtils.assertFileNotContains(Paths.get(outputPath + "/lib/RootOperation/API.hs"), "\"\" :>");
    }
}
