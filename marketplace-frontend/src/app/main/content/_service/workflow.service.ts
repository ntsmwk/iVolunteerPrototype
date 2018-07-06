import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {WorkflowStep} from '../_model/workflow-step';

@Injectable()
export class WorkflowService {

  private endpoint = 'workflow';

  constructor(private http: HttpClient) {
  }

  findAllTypes(url: string) {
    return this.http.get(`${url}/${this.endpoint}/type`);
  }

  getProcessId(taskId: string, url: string) {
    return this.http.get(`${url}/${this.endpoint}/processId?taskId=${taskId}`);
  }

  startWorkflow(workflowKey: string, taskId: string, employeeId: string, url: string) {
    return this.http.post(`${url}/${this.endpoint}/${workflowKey}?taskId=${taskId}&employeeId=${employeeId}`, {});
  }

  getWorkflowSteps(workflowKey: string, processInstanceId: string, participantId: string, url: string) {
    return this.http.get(`${url}/${this.endpoint}/${workflowKey}/${processInstanceId}/step?participantId=${participantId}`);
  }

  completeWorkflowStep(workflowKey: string, processInstanceId: string, workflowStep: WorkflowStep, participantId: string, url: string) {
    return this.http.post(`${url}/${this.endpoint}/${workflowKey}/${processInstanceId}/step?participantId=${participantId}`, workflowStep);
  }

  stopWorkflow(workflowKey: string, processInstanceId: string, url: string) {
    return this.http.delete(`${url}/${this.endpoint}/${workflowKey}/${processInstanceId}`);
  }
}