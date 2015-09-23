package com.vaadin.devday.backend.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.vaadin.devday.service.job.Job;
import com.vaadin.devday.service.job.JobService;

@Component
public class JobServiceBean implements JobService {

	private List<JobProcess> jobs;
	private int jobIndex;

	private final Object LOCK = new Object();

	public JobServiceBean() {
		jobs = new ArrayList<JobProcess>();
	}

	@Override
	public List<Job> startNewJob() {
		Random random = new Random();

		synchronized (LOCK) {
			JobProcess process = new JobProcess("Job " + ++jobIndex, random.nextInt(60));
			jobs.add(process);
		}

		return getPendingJobs();
	}

	@Override
	public List<Job> getPendingJobs() {
		synchronized (LOCK) {
			return jobs.stream().map(job -> new Job(job.getId(), job.getName(), job.getProgress()))
					.collect(Collectors.toList());
		}
	}

	@Override
	public void clearJob(Job job) {
		synchronized (LOCK) {

		}
	}
}
