package com.jenkins.testresultsaggregator.data;

import java.util.Collection;
import java.util.List;

import com.jenkins.testresultsaggregator.TestResultsAggregator;
import com.jenkins.testresultsaggregator.helper.Helper;

import hudson.model.Run;
import hudson.tasks.test.TestResult;

public class Aggregated extends BaseResult {
	
	private List<Data> data;
	private Results results;
	private int runningJobs = 0;
	private int successJobs = 0;
	private int fixedJobs = 0;
	private int failedJobs = 0;
	private int keepFailJobs = 0;
	private int unstableJobs = 0;
	private int keepUnstableJobs = 0;
	private int abortedJobs = 0;
	private int disabledJobs = 0;
	private int totalJobs = 0;
	private Long totalDuration = 0L;
	private int totalNumberOfChanges = 0;
	
	private Run<?, ?> owner;
	
	public Aggregated() {
		super(TestResultsAggregator.URL);
	}
	
	public List<Data> getData() {
		return data;
	}
	
	public void setData(List<Data> data) {
		this.data = data;
	}
	
	public Results getResults() {
		return results;
	}
	
	public void setResults(Results results) {
		this.results = results;
	}
	
	public int getRunningJobs() {
		return runningJobs;
	}
	
	public void setRunningJobs(int runningJobs) {
		this.runningJobs = runningJobs;
	}
	
	public int getSuccessJobs() {
		return successJobs;
	}
	
	public void setSuccessJobs(int successJobs) {
		this.successJobs = successJobs;
	}
	
	public int getFailedJobs() {
		return failedJobs;
	}
	
	public void setFailedJobs(int countJobFailures) {
		this.failedJobs = countJobFailures;
	}
	
	public int getUnstableJobs() {
		return unstableJobs;
	}
	
	public void setUnstableJobs(int unstableJobs) {
		this.unstableJobs = unstableJobs;
	}
	
	public int getAbortedJobs() {
		return abortedJobs;
	}
	
	public void setAbortedJobs(int abortedJobs) {
		this.abortedJobs = abortedJobs;
	}
	
	public int getFixedJobs() {
		return fixedJobs;
	}
	
	public void setFixedJobs(int fixedJobs) {
		this.fixedJobs = fixedJobs;
	}
	
	public int getKeepFailJobs() {
		return keepFailJobs;
	}
	
	public void setKeepFailJobs(int keepFailJobs) {
		this.keepFailJobs = keepFailJobs;
	}
	
	public int getKeepUnstableJobs() {
		return keepUnstableJobs;
	}
	
	public void setKeepUnstableJobs(int keepUnstableJobs) {
		this.keepUnstableJobs = keepUnstableJobs;
	}
	
	public Run<?, ?> getRun() {
		return owner;
	}
	
	public void setRun(Run<?, ?> owner) {
		this.owner = owner;
	}
	
	public int getTotalJobs() {
		setTotalJobs(runningJobs + successJobs + fixedJobs + failedJobs + keepFailJobs + unstableJobs + keepUnstableJobs + abortedJobs);
		return totalJobs;
	}
	
	public void setTotalJobs(int totalJobs) {
		this.totalJobs = totalJobs;
	}
	
	@Override
	public Collection<? extends TestResult> getChildren() {
		return null;
	}
	
	@Override
	public boolean hasChildren() {
		return false;
	}
	
	public String calculatePercentage(boolean withColor) {
		if (withColor) {
			return Helper.colorizePercentage(Helper.countPercentage(results));
		}
		return Helper.countPercentage(results).toString();
	}
	
	public String calculateTotalDuration() {
		return Helper.duration(totalDuration);
	}
	
	public Long getTotalDuration() {
		return totalDuration;
	}
	
	public void setTotalDuration(Long totalDuration) {
		this.totalDuration = totalDuration;
	}
	
	public int getTotalNumberOfChanges() {
		return totalNumberOfChanges;
	}
	
	public void setTotalNumberOfChanges(int totalNumberOfChanges) {
		this.totalNumberOfChanges = totalNumberOfChanges;
	}
	
	public int getFailed() {
		return failedJobs + keepFailJobs;
	}
	
	public int getUnstable() {
		return unstableJobs + keepUnstableJobs;
	}

	public int getDisabledJobs() {
		return disabledJobs;
	}

	public void setDisabledJobs(int disabledJobs) {
		this.disabledJobs = disabledJobs;
	}
	
}
