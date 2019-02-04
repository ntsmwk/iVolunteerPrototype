import { FuseUserDefinedTaskTemplateDetailComponent } from './user-defined-task-template-detail.component';
import { FuseUserDefinedTaskTemplateDetailModule } from './user-defined-task-template-detail.module';

describe('PropertyDetailModule', () => {
  let propertyDetailModule: FuseUserDefinedTaskTemplateDetailModule;

  beforeEach(() => {
    propertyDetailModule = new FuseUserDefinedTaskTemplateDetailModule();
  });

  it('should create an instance', () => {
    expect(propertyDetailModule).toBeTruthy();
  });
});
