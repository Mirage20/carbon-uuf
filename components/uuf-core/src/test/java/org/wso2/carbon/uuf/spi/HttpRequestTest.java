/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.uuf.spi;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test cases for HTTP request.
 *
 * @since 1.0.0
 */
public class HttpRequestTest {

    @DataProvider(name = "uris")
    public static Object[][] getUris() {
        return new Object[][]{
                {"/a", true},
                {"/a/b", true},
                {"/a/b/c", true},
                {"/a/b/c/", true},
                {"/a/b.c", true},
                {"/a/b.c.", true},
                {"", false},
                {"a", false},
                {"a/b", false},
                {"//", false},
                {"/a/b///c", false},
                {"/.", false},
                {"/a..", false},
                {"/a/b/../d", false},
        };
    }

    @Test(dataProvider = "uris")
    public void testIsValid(String uri, boolean expectedResult) {
        HttpRequest request = mock(HttpRequest.class, CALLS_REAL_METHODS);
        when(request.getUri()).thenReturn(uri);
        Assert.assertEquals(request.isValid(), expectedResult);
    }
}
