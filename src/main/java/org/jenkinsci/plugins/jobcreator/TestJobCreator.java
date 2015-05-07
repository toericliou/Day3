package org.jenkinsci.plugins.jobcreator;
import hudson.Launcher;
import hudson.Extension;
import hudson.model.*;
import hudson.util.FormValidation;
import hudson.tasks.Builder;
import jenkins.model.Jenkins;
import hudson.tasks.BuildStepDescriptor;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * Created by eerilio on 5/6/15.
 */

public class TestJobCreator extends Builder {
    private static final String JOB_NAME = "test_job1";

    private FreeStyleProject proj;
    public Jenkins j;

    @DataBoundConstructor
    public TestJobCreator(){
        j = Jenkins.getInstance();
    }

    @Override
    public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) throws IOException {
        if (getDescriptor().getCreateBuild())
            createBuildJob();
        return true;
    }

    public FreeStyleProject createBuildJob() throws IOException {
        if(!checkJobExists()) {
            proj = j.createProject(FreeStyleProject.class, JOB_NAME);
            j.getQueue().schedule(proj);
        }
        else{
            proj = getExistingJob(JOB_NAME);
            j.getQueue().schedule(proj);
        }
        return proj;
    }


    private boolean checkJobExists(){
        return j.getJobNames().contains(JOB_NAME);
    }

    private FreeStyleProject getExistingJob(String name) {
        List<FreeStyleProject> list = j.getAllItems(FreeStyleProject.class);
        for (FreeStyleProject a : list) {
            if (a.getName().equals(name))
                return a;
        }
        return null;
    }

    public FreeStyleProject getProject(){
        return proj;
    }
    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl)super.getDescriptor();
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
        private boolean createJob;
        public DescriptorImpl()
        {
            load();
        }
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }
        public String getDisplayName() {
            return "Create freestyle Job test_job1";
        }
        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            createJob = formData.getBoolean("createJob");
            save();
            return super.configure(req,formData);
        }
        public boolean getCreateBuild() {
            return createJob;
        }
    }
}
