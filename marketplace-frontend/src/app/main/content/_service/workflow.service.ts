import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {Marketplace} from '../_model/marketplace';
import {WorkflowStep} from '../_model/workflow-step';

@Injectable({
  providedIn: 'root'
})
export class WorkflowService {

  constructor(private http: HttpClient) {
  }

  findAllTypes(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/workflow/type`);
  }

  getProcessId(marketplace: Marketplace, taskId: string) {
    return this.http.get(`${marketplace.url}/workflow/processId?taskId=${taskId}`);
  }

  startWorkflow(marketplace: Marketplace, workflowKey: string, taskId: string, helpSeekerId: string) {
    return this.http.post(`${marketplace.url}/workflow/${workflowKey}?taskId=${taskId}&helpSeekerId=${helpSeekerId}`, {});
  }

  getWorkflowSteps(marketplace: Marketplace, workflowKey: string, processInstanceId: string, participantId: string) {
    return this.http.get(`${marketplace.url}/workflow/${workflowKey}/${processInstanceId}/step?participantId=${participantId}`);
  }

  completeWorkflowStep(marketplace: Marketplace, workflowKey: string, processInstanceId: string, workflowStep: WorkflowStep, participantId: string) {
    return this.http.post(`${marketplace.url}/workflow/${workflowKey}/${processInstanceId}/step?participantId=${participantId}`, workflowStep);
  }

  stopWorkflow(marketplace: Marketplace, workflowKey: string, processInstanceId: string) {
    return this.http.delete(`${marketplace.url}/workflow/${workflowKey}/${processInstanceId}`);
  }
}
