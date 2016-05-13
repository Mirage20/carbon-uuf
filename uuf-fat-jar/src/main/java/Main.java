/*
 *  Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import com.google.common.collect.ImmutableSet;
import org.wso2.carbon.uuf.handlebars.HbsRenderableCreator;
import org.wso2.carbon.uuf.internal.DebugAppender;
import org.wso2.carbon.uuf.internal.UUFRegistry;
import org.wso2.carbon.uuf.connector.MicroserviceConnector;
import org.wso2.carbon.uuf.internal.core.create.AppCreator;
import org.wso2.carbon.uuf.internal.core.create.AppDiscoverer;
import org.wso2.carbon.uuf.internal.core.create.ClassLoaderProvider;
import org.wso2.carbon.uuf.internal.io.ArtifactAppDiscoverer;
import org.wso2.carbon.uuf.internal.io.StaticResolver;
import org.wso2.carbon.uuf.spi.RenderableCreator;
import org.wso2.msf4j.MicroservicesRunner;

import java.nio.file.FileSystems;

public class Main {

    public static void main(String[] args) {
        AppDiscoverer appDiscoverer = new ArtifactAppDiscoverer(FileSystems.getDefault().getPath("."));
        RenderableCreator hbsCreator = new HbsRenderableCreator();
        ClassLoaderProvider classLoaderProvider = (cn, cv, cr) -> Main.class.getClassLoader();
        AppCreator appCreator = new AppCreator(ImmutableSet.of(hbsCreator), classLoaderProvider);
        StaticResolver staticResolver = new StaticResolver(FileSystems.getDefault().getPath("."));
        UUFRegistry registry = new UUFRegistry(appDiscoverer, appCreator, staticResolver);
        new MicroservicesRunner().deploy(new MicroserviceConnector(registry)).start();
    }
}
