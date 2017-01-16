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

function onRequest(env) {
    if (env.request.method == "POST") {
        var Paths = Java.type('java.nio.file.Paths');
        var System = Java.type('java.lang.System');
        var Files = Java.type('java.nio.file.Files');
        var StandardCopyOption = Java.type('java.nio.file.StandardCopyOption');

        var uploadedFile = env.request.files["file-content"];
        var tempDirPath = System.getProperty('java.io.tmpdir');
        var destination = Paths.get(tempDirPath).resolve(uploadedFile.name);
        var sourcePath = Paths.get(uploadedFile.path);
        var destinationPath = Paths.get(destination);

        //copy the selected file to UUF_HOME/tmp directory overriding any existing content with the same name
        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

        return {
            message: 'You have successfully uploaded the file, ' + uploadedFile.name + ' and copied it to '
                     + tempDirPath + ' directory.'
        };
    }
}