import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {WorkflowStep} from '../_model/workflow-step';

@Injectable()
export class WorkflowService {

  private apiUrl = '/marketplace/workflow';

  constructor(private http: HttpClient) {
  }

  findAllTypes() {
    return this.http.get(`${this.apiUrl}/type`);
  }

  getProcessId(taskId: string){
    return this.http.get(`${this.apiUrl}/processId?taskId=${taskId}`);
  }

  startWorkflow(workflowKey: string, taskId: string) {
    return this.http.post(`${this.apiUrl}/${workflowKey}?taskId=${taskId}`, {});
  }

  getWorkflowSteps(workflowKey: string, processInstanceId: string) {
    return this.http.get(`${this.apiUrl}/${workflowKey}/${processInstanceId}/step`);
  }

  completeWorkflowStep(workflowKey: string, processInstanceId: string, workflowStep: WorkflowStep) {
    return this.http.post(`${this.apiUrl}/${workflowKey}/${processInstanceId}/step`, workflowStep);
  }

  stopWorkflow(workflowKey: string, processInstanceId: string) {
    return this.http.delete(`${this.apiUrl}/${workflowKey}/${processInstanceId}`);
  }
}
