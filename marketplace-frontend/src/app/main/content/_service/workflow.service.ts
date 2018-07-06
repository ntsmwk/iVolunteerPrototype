import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {WorkflowStep} from '../_model/workflow-step';
import {Marketplace} from '../_model/marketplace';

@Injectable({
  providedIn: 'root'
})
export class WorkflowService {

  private endpoint = 'workflow';

  constructor(private http: HttpClient) {
  }

  findAllTypes(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/${this.endpoint}/type`);
  }

  getProcessId(taskId: string, url: string) {
    return this.http.get(`${url}/${this.endpoint}/processId?taskId=${taskId}`);
  }

  startWorkflow(marketplace: Marketplace, workflowKey: string, taskId: string, employeeId: string) {
    return this.http.post(`${marketplace.url}/${this.endpoint}/${workflowKey}?taskId=${taskId}&employeeId=${employeeId}`, {});
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
