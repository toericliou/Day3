package org.jenkinsci.plugins.jobcreator;

import hudson.model.FreeStyleProject;
import jenkins.model.Jenkins;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;


import static org.junit.Assert.*;

/**
 * Created by eerilio on 5/7/15.
 */
public class TestJobCreatorTest {

    @Rule
    public JenkinsRule j = new JenkinsRule();

    @Before
    public void setup() throws Exception {
    }

    @Test
    public void testcreateBuildJob() throws Exception {
        TestJobCreator tester = new TestJobCreator();
        tester.createBuildJob();
        assertTrue(j.getInstance().getJobNames().contains("test_job1"));
    }

}