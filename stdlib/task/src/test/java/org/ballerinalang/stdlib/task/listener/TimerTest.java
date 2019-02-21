/*
 *  Copyright (c) 2019 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 *
 */

package org.ballerinalang.stdlib.task.listener;

import org.ballerinalang.launcher.util.BCompileUtil;
import org.ballerinalang.launcher.util.BRunUtil;
import org.ballerinalang.launcher.util.CompileResult;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BValue;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

/**
 * Advanced Tests for Ballerina Task Timer service.
 */
@Test
public class TimerTest {
    @Test(description = "Test service parameter passing")
    public void testTimerServiceParameter() {
        CompileResult compileResult = BCompileUtil.compile(
                "listener-test-src/timer/service_parameter.bal");
        BRunUtil.invoke(compileResult, "attachTimer");
        String expectedResult = "Kurt Kobain died at 27";
        await().atMost(5000, TimeUnit.MILLISECONDS).until(() -> {
            BValue[] result = BRunUtil.invokeStateful(compileResult, "getResult");
            Assert.assertEquals(result.length, 1);
            Assert.assertTrue(result[0] instanceof BString);
            return (expectedResult.equals(result[0].stringValue()));
        });
    }
}
