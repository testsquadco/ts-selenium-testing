package com.testsquad.reporting;

import com.aventstack.extentreports.Status;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;

public class CucumberExtentReporter implements ConcurrentEventListener {
    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, this::scenarioStarted);
        publisher.registerHandlerFor(TestStepStarted.class, this::stepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::stepFinished);
        publisher.registerHandlerFor(TestCaseFinished.class, this::scenarioFinished);
    }

    private void scenarioStarted(TestCaseStarted event) {
        String scenarioName = event.getTestCase().getName();
        ExtentManager.createTest(scenarioName);
    }

    private void stepStarted(TestStepStarted event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
            ExtentManager.getTest().log(Status.INFO, "Starting step: " + step.getStep().getText());
        }
    }

    private void stepFinished(TestStepFinished event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
            switch (event.getResult().getStatus()) {
                case PASSED:
                    ExtentManager.getTest().log(Status.PASS, step.getStep().getText());
                    break;
                case FAILED:
                    ExtentManager.getTest().log(Status.FAIL, step.getStep().getText());
                    ExtentManager.getTest().log(Status.FAIL, event.getResult().getError());
                    break;
                case SKIPPED:
                    ExtentManager.getTest().log(Status.SKIP, step.getStep().getText());
                    break;
                default:
                    ExtentManager.getTest().log(Status.WARNING, "Step status: " + event.getResult().getStatus());
            }
        }
    }

    private void scenarioFinished(TestCaseFinished event) {}
} 