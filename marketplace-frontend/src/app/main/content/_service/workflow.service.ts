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

  getProcessId(marketplace: Marketplace, taskId: string) {
    return this.http.get(`${marketplace.url}/${this.endpoint}/processId?taskId=${taskId}`);
  }

  startWorkflow(marketplace: Marketplace, workflowKey: string, taskId: string, employeeId: string) {
    return this.http.post(`${marketplace.url}/${this.endpoint}/${workflowKey}?taskId=${taskId}&employeeId=${employeeId}`, {});
  }

  getWorkflowSteps(marketplace: Marketplace, workflowKey: string, processInstanceId: string, participantId: string) {
    return this.http.get(`${marketplace.url}/${this.endpoint}/${workflowKey}/${processInstanceId}/step?participantId=${participantId}`);
  }

  completeWorkflowStep(marketplace: Marketplace, workflowKey: string, processInstanceId: string, workflowStep: WorkflowStep, participantId: string) {
    return this.http.post(`${marketplace.url}/${this.endpoint}/${workflowKey}/${processInstanceId}/step?participantId=${participantId}`, workflowStep);
  }

  stopWorkflow(marketplace: Marketplace, workflowKey: string, processInstanceId: string) {
    return this.http.delete(`${marketplace.url}/${this.endpoint}/${workflowKey}/${processInstanceId}`);
  }
}
