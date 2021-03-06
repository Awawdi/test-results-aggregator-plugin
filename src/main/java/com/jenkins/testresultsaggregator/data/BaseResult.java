package com.jenkins.testresultsaggregator.data;

import java.io.Serializable;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

import hudson.model.ModelObject;
import hudson.model.Run;
import hudson.tasks.test.AbstractTestResultAction;
import hudson.tasks.test.TabulatedResult;
import hudson.tasks.test.TestResult;
import jenkins.model.Jenkins;

/**
 * Base class that takes care of all the common functionality of the different kinds of test results.
 */
@SuppressWarnings("serial")
@ExportedBean
public abstract class BaseResult extends TabulatedResult implements ModelObject, Serializable {
	
	// owner of this build
	protected Run<?, ?> run;
	// name of this result
	protected final String name;
	// parent result for this result
	protected BaseResult parent;
	
	public BaseResult(String name) {
		this.name = name;
	}
	
	@Exported(visibility = 999)
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public BaseResult getParent() {
		return parent;
	}
	
	public void setParent(BaseResult parent) {
		this.parent = parent;
	}
	
	@Override
	public Run<?, ?> getRun() {
		return run;
	}
	
	public void setRun(Run<?, ?> run) {
		this.run = run;
	}
	
	@Override
	public String getTitle() {
		return getName();
	}
	
	public String getDisplayName() {
		return getName();
	}
	
	public String getUpUrl() {
		Jenkins j = Jenkins.getInstance();
		return j != null ? j.getRootUrl() + run.getUrl() + getId() : "";
	}
	
	@Override
	public Object getDynamic(String token, StaplerRequest req, StaplerResponse rsp) {
		for (TestResult result : this.getChildren()) {
			if (token.equals(result.getName())) {
				return result;
			}
		}
		return null;
	}
	
	@Override
	public AbstractTestResultAction getTestResultAction() {
		Run<?, ?> run = getRun();
		if (run != null) {
			return run.getAction(com.jenkins.testresultsaggregator.TestResultsAggregatorTestResultBuildAction.class);
		}
		return null;
	}
	
	@Override
	public AbstractTestResultAction getParentAction() {
		return getTestResultAction();
	}
	
	@Override
	public TestResult findCorrespondingResult(String id) {
		if (getId().equals(id) || id == null) {
			return this;
		}
		
		int sepIdx = id.indexOf('/');
		if (sepIdx < 0) {
			if (getSafeName().equals(id)) {
				return this;
			}
		} else {
			String currId = id.substring(0, sepIdx);
			if (!getSafeName().equals(currId)) {
				return null;
			}
			
			String childId = id.substring(sepIdx + 1);
			sepIdx = childId.indexOf('/');
			
			for (TestResult result : this.getChildren()) {
				if (sepIdx < 0 && childId.equals(result.getSafeName())) {
					return result;
				} else if (sepIdx > 0 && result.getSafeName().equals(childId.substring(0, sepIdx))) {
					return result.findCorrespondingResult(childId);
				}
			}
		}
		return null;
	}
	
	public long getAge() {
		BaseResult result = (BaseResult) getPreviousResult();
		if (result == null) {
			return 1;
		} else {
			return 1 + result.getAge();
		}
	}
	
}
