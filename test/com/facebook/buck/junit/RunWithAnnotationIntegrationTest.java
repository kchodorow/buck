/*
 * Copyright 2012-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.facebook.buck.junit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;

import com.facebook.buck.testutil.integration.ProjectWorkspace;
import com.facebook.buck.testutil.integration.ProjectWorkspace.ProcessResult;
import com.facebook.buck.testutil.integration.TestDataHelper;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

public class RunWithAnnotationIntegrationTest {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  @Test
  public void testSimpleSuiteRun2TestCases() throws IOException {
    ProjectWorkspace workspace = TestDataHelper.createProjectWorkspaceForScenario(
        this, "runwith", temporaryFolder);
    workspace.setUp();

    // ExceedsAnnotationTimeoutTest should fail.
    ProcessResult suiteTestResult = workspace.runBuckCommand("test", "//:SimpleSuiteTest");
    assertEquals("Test should pass", 0, suiteTestResult.getExitCode());
    assertThat(suiteTestResult.getStderr(), containsString("2 Passed"));
  }

  @Test
  public void testFailingSuiteRun3TestCasesWith1Failure() throws IOException {
    ProjectWorkspace workspace = TestDataHelper.createProjectWorkspaceForScenario(
        this, "runwith", temporaryFolder);
    workspace.setUp();

    // ExceedsAnnotationTimeoutTest should fail.
    ProcessResult suiteTestResult = workspace.runBuckCommand("test", "//:FailingSuiteTest");
    assertEquals("Test should fail because of one of subtests failure",
        1,
        suiteTestResult.getExitCode());
    assertThat(suiteTestResult.getStderr(), containsString("2 Passed"));
    assertThat(suiteTestResult.getStderr(), containsString("1 Failed"));
  }

}