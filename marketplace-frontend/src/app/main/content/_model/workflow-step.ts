export class WorkflowStep {
    id: string;
    label: string;
    assignee: string;
    workflowStepType: string;
    params: Map<string, string>;
  }